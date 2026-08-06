// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.administration;

import org.apache.logging.log4j.LogManager;
import java.util.Iterator;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.commons.ICollection;
import java.util.HashMap;
import ru.korusconsulting.service.IQueryService;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import java.io.IOException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.StringUtils;
import java.util.EnumMap;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.integration.administration.IEdoAccountMember;
import java.util.Map;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.List;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.event.EventAction;

public abstract class ImportAccountMembersEventAction extends EventAction
{
    private static Logger log;
    
    public void checkRequired(final List<IMPORT_ATTRIBUTE> attrs) {
        for (final IMPORT_ATTRIBUTE requiredAttr : IMPORT_ATTRIBUTE.values()) {
            if (requiredAttr.isRequired() && !attrs.contains(requiredAttr)) {
                throw new ServiceException(String.format("%s is required", requiredAttr.name()));
            }
        }
    }
    
    protected abstract Map<String, List<IEdoAccountMember>> loadExistingMembers(final DMSShardsClientSession p0, final SessionContext p1);
    
    protected abstract IEdoAccountMember createObject(final DMSShardsClientSession p0, final FilialDesc p1, final IObjectModel p2, final Map<IMPORT_ATTRIBUTE, String> p3, final boolean p4, final SessionContext p5);
    
    public Object execute(final DMSShardsClientSession session, final Map<String, Object> args, final SessionContext ctx) {
        final IObjectModel account = ServiceReg.getObjectModelService().getObjectModel(session, (String)args.get("__objectId"), ctx);
        if (account == null) {
            return String.format("Failed to located edo account object with id %s", args.get("__objectId"));
        }
        final StringBuilder createdActive = new StringBuilder();
        final StringBuilder createdNotActive = new StringBuilder();
        final StringBuilder notCreated = new StringBuilder();
        final StringBuilder logInfo = new StringBuilder();
        int createdActiveCount = 0;
        int createdNotActiveCount = 0;
        int existingCount = 0;
        int failedCount = 0;
        final IQueryService queryService = ServiceReg.getQueryService();
        final List<IMPORT_ATTRIBUTE> attrs = new ArrayList<IMPORT_ATTRIBUTE>();
        final Map<String, List<Map<IMPORT_ATTRIBUTE, String>>> incomingData = new LinkedHashMap<String, List<Map<IMPORT_ATTRIBUTE, String>>>();
        final List<File> files = args.get("files");
        BufferedReader bre = null;
        boolean success = false;
        try {
            bre = new BufferedReader(new InputStreamReader(new FileInputStream(files.get(0))));
            String line = bre.readLine();
            if (line != null) {
                String[] fileValues = line.split(";", -1);
                for (int i = 0; i < fileValues.length; ++i) {
                    final IMPORT_ATTRIBUTE attr = IMPORT_ATTRIBUTE.parse(fileValues[i]);
                    if (attr != null) {
                        attr.setIndex(i);
                        attrs.add(attr);
                    }
                }
                this.checkRequired(attrs);
                while ((line = bre.readLine()) != null) {
                    fileValues = line.split(";", -1);
                    final Map<IMPORT_ATTRIBUTE, String> attrValues = new EnumMap<IMPORT_ATTRIBUTE, String>(IMPORT_ATTRIBUTE.class);
                    if (attrs.indexOf(IMPORT_ATTRIBUTE.INN) < attrs.size() && attrs.indexOf(IMPORT_ATTRIBUTE.KPP) < attrs.size() && attrs.get(attrs.indexOf(IMPORT_ATTRIBUTE.INN)).getIndex() < fileValues.length && attrs.get(attrs.indexOf(IMPORT_ATTRIBUTE.KPP)).getIndex() < fileValues.length) {
                        final String key = String.join(".", StringUtils.trimToEmpty(fileValues[attrs.get(attrs.indexOf(IMPORT_ATTRIBUTE.INN)).getIndex()]), StringUtils.trimToEmpty(fileValues[attrs.get(attrs.indexOf(IMPORT_ATTRIBUTE.KPP)).getIndex()]));
                        for (final IMPORT_ATTRIBUTE attr2 : attrs) {
                            if (attr2.getIndex() < fileValues.length) {
                                final String value = StringUtils.trimToNull(fileValues[attr2.getIndex()].trim());
                                if (value == null) {
                                    continue;
                                }
                                attrValues.put(attr2, value);
                            }
                        }
                        incomingData.computeIfAbsent(key, a -> new ArrayList()).add(attrValues);
                    }
                    else {
                        final String logRow = String.format("Row dosn't  contains filial INN and/or KPP. Row [%s] ignored.%n", line);
                        notCreated.append(logRow);
                        ++failedCount;
                    }
                }
                success = true;
            }
        }
        catch (final Exception e) {
            ImportAccountMembersEventAction.log.error(String.format("Error while parsing file %s", files.get(0)), (Throwable)e);
            logInfo.append(String.format("Error while parsing file %s with exception %n%s%n", files.get(0), ExceptionUtils.getFullStackTrace((Throwable)e)));
            return logInfo.toString();
        }
        finally {
            if (bre != null) {
                try {
                    bre.close();
                }
                catch (final IOException e2) {
                    ImportAccountMembersEventAction.log.warn("Error while closing reader", (Throwable)e2);
                }
            }
        }
        if (incomingData.isEmpty()) {
            success = false;
        }
        Throwable exp = null;
        if (success) {
            final ShardSessionRunner<Map<String, List<FilialDesc>>> shardSessionRunner = new ShardSessionRunner<Map<String, List<FilialDesc>>>() {
                public Map<String, List<FilialDesc>> run() {
                    final Map<String, List<FilialDesc>> result = new HashMap<String, List<FilialDesc>>();
                    ICollection coll = null;
                    try {
                        coll = queryService.executeReadQuery(session, "select r_object_id, ssa_name, ssa_inn, ssa_kpp from nt_filial where ssa_inn is not null", ctx);
                        while (coll.next()) {
                            final String key = String.join(".", StringUtils.trimToEmpty(coll.getString("ssa_inn")), StringUtils.trimToEmpty(coll.getString("ssa_kpp")));
                            result.computeIfAbsent(key, a -> new ArrayList()).add(new FilialDesc(coll.getString("r_object_id"), coll.getString("ssa_name"), coll.getString("ssa_inn"), coll.getString("ssa_kpp")));
                        }
                    }
                    finally {
                        if (coll != null) {
                            coll.close();
                        }
                    }
                    return result;
                }
            };
            final Map<String, List<FilialDesc>> filialsByInnKpp = (Map<String, List<FilialDesc>>)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
            final Map<String, List<IEdoAccountMember>> membersByInnKPP = this.loadExistingMembers(session, ctx);
            try {
                for (final Map.Entry<String, List<Map<IMPORT_ATTRIBUTE, String>>> incomingDataRow : incomingData.entrySet()) {
                    final List<FilialDesc> filialByInnKpp = filialsByInnKpp.get(incomingDataRow.getKey());
                    if (filialByInnKpp == null || filialByInnKpp.isEmpty()) {
                        for (final Map<IMPORT_ATTRIBUTE, String> a : incomingDataRow.getValue()) {
                            final String logRow2 = String.format("Failed to locate filial with INN %s and KPP %s. Records for box %s and department %s not created.%n", a.get(IMPORT_ATTRIBUTE.INN), a.get(IMPORT_ATTRIBUTE.KPP), a.get(IMPORT_ATTRIBUTE.BOX), a.get(IMPORT_ATTRIBUTE.DEPARTMENT));
                            notCreated.append(logRow2);
                            ++failedCount;
                        }
                    }
                    else if (filialByInnKpp.size() > 1) {
                        for (final Map<IMPORT_ATTRIBUTE, String> a : incomingDataRow.getValue()) {
                            final String logRow2 = String.format("Exists more then one filial with INN %s and KPP %s. Records for box %s and department %s not created.%n", a.get(IMPORT_ATTRIBUTE.INN), a.get(IMPORT_ATTRIBUTE.KPP), a.get(IMPORT_ATTRIBUTE.BOX), a.get(IMPORT_ATTRIBUTE.DEPARTMENT));
                            notCreated.append(logRow2);
                            ++failedCount;
                        }
                    }
                    else {
                        final List<IEdoAccountMember> existing = membersByInnKPP.computeIfAbsent((String)incomingDataRow.getKey(), a -> new ArrayList());
                        final List<Map<IMPORT_ATTRIBUTE, String>> newValuesArray = incomingDataRow.getValue();
                        boolean createActive = existing.isEmpty();
                        for (final Map<IMPORT_ATTRIBUTE, String> newValues : newValuesArray) {
                            boolean proceed = false;
                            final String edoAddress = String.join(":", newValues.get(IMPORT_ATTRIBUTE.BOX), newValues.get(IMPORT_ATTRIBUTE.DEPARTMENT));
                            for (final IEdoAccountMember m : existing) {
                                if (StringUtils.equals(m.getComplexEdoAddress(), edoAddress)) {
                                    final String logRow3 = String.format("Record for filial with INN %s and KPP %s and box %s and department %s allready exists.%n", newValues.get(IMPORT_ATTRIBUTE.INN), newValues.get(IMPORT_ATTRIBUTE.KPP), newValues.get(IMPORT_ATTRIBUTE.BOX), newValues.get(IMPORT_ATTRIBUTE.DEPARTMENT));
                                    notCreated.append(logRow3);
                                    ++existingCount;
                                    proceed = true;
                                    break;
                                }
                            }
                            if (!proceed) {
                                existing.add(this.createObject(session, filialByInnKpp.get(0), account, newValues, createActive, ctx));
                                final String logRow4 = String.format("Record for filial with INN %s and KPP %s and box %s and department %s created. Linked to %s (%s)%n", newValues.get(IMPORT_ATTRIBUTE.INN), newValues.get(IMPORT_ATTRIBUTE.KPP), newValues.get(IMPORT_ATTRIBUTE.BOX), newValues.get(IMPORT_ATTRIBUTE.DEPARTMENT), filialByInnKpp.get(0).getName(), filialByInnKpp.get(0).getId());
                                if (createActive) {
                                    createdActive.append(logRow4);
                                    createActive = false;
                                    ++createdActiveCount;
                                }
                                else {
                                    createdNotActive.append(logRow4);
                                    ++createdNotActiveCount;
                                }
                            }
                        }
                    }
                }
            }
            catch (final Throwable e3) {
                success = false;
                exp = e3;
            }
            logInfo.append("Overall status:").append(String.format("%n", new Object[0]));
            if (!success) {
                logInfo.append("Import execution failed").append(String.format("%n", new Object[0]));
            }
            logInfo.append(String.format("%d enabled account member(s) created %n", createdActiveCount));
            logInfo.append(String.format("%d disabled account member(s) created %n", createdNotActiveCount));
            logInfo.append(String.format("Skipped %d existing account member(s) %n", existingCount));
            logInfo.append(String.format("Skipped %d failed account member(s) %n", failedCount));
            if (createdActiveCount > 0) {
                logInfo.append(String.format("%n%nCreated account members%n", new Object[0]));
                logInfo.append((CharSequence)createdActive);
            }
            if (createdNotActiveCount > 0) {
                logInfo.append(String.format("%n%nCreated disabled account members%n", new Object[0]));
                logInfo.append((CharSequence)createdNotActive);
            }
            if (existingCount > 0 || failedCount > 0) {
                logInfo.append(String.format("%n%nSkipped rows%n", new Object[0]));
                logInfo.append((CharSequence)notCreated);
            }
            if (exp != null) {
                logInfo.append(String.format("Import failed with error %n%s%n", ExceptionUtils.getFullStackTrace(exp)));
            }
        }
        else {
            logInfo.append(String.format("No records to process. Check file format %n", new Object[0]));
        }
        return logInfo.toString();
    }
    
    static {
        ImportAccountMembersEventAction.log = LogManager.getLogger((Class)ImportAccountMembersEventAction.class);
    }
    
    public enum IMPORT_ATTRIBUTE
    {
        INN(true), 
        KPP(true), 
        BOX(true), 
        DEPARTMENT(true), 
        REGLAMENT_INCOMING(false), 
        REGLAMENT_OUTGOING(false), 
        REGLAMENT_KZ(false), 
        REGLAMENT_MULTI(false);
        
        private int index;
        private final boolean required;
        
        private IMPORT_ATTRIBUTE(final boolean required) {
            this.required = required;
        }
        
        public static IMPORT_ATTRIBUTE parse(final String value) {
            for (final IMPORT_ATTRIBUTE v : values()) {
                if (v.name().equalsIgnoreCase(StringUtils.trimToNull(value))) {
                    return v;
                }
            }
            return null;
        }
        
        void setIndex(final int value) {
            this.index = value;
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public boolean isRequired() {
            return this.required;
        }
    }
}
