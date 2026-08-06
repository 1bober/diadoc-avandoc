// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.schedule;

import java.io.Reader;
import java.io.InputStream;
import ru.korusconsulting.dmservice.objectmodel.documents.JoinFTTypes;
import ru.korusconsulting.dmservice.objectmodel.documents.XmlDocumentTypes;
import ru.korusconsulting.dmservice.objectmodel.XmlTypes;
import ru.korusconsulting.dmservice.config.alt.ConfigBase;
import java.io.File;
import ru.korusconsulting.dmservice.ServiceAndTypeInitializer;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import ru.korusconsulting.service.IEventHandlerService;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.dmservice.common.TransactionRunner;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.commons.ApplicationProperties;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.wstack.wsscheduler.ScheduledTask;

public class DiadocEdoInquiryProcessor extends ScheduledTask
{
    private Logger logger;
    
    public DiadocEdoInquiryProcessor() {
        this.logger = LogManager.getLogger((Class)DiadocEdoInquiryProcessor.class);
    }
    
    public void invoke() {
        final SessionContext ctx = new SessionContext();
        if (DiadocEdoServiceReg.getDiadocEdoIntegrationService() == null) {
            throw new ServiceException("Diadoc EDO service not installed");
        }
        try (final DMSShardsClientSession session = new DMSShardsClientSession()) {
            session.initClientSession(ApplicationProperties.getProperty("cslogin"), ApplicationProperties.getProperty("cspassword"));
            new ShardSessionRunner(() -> {
                final String operations = this.getParams().get("COMMAND");
                final List<IEdoIntegrationService.EDO_OPERATION_NAMES> operationList = null;
                List<IEdoIntegrationService.EDO_OPERATION_NAMES> operationList2;
                IEdoIntegrationService.EDO_OPERATION_NAMES b = null;
                if (!StringUtils.isBlank(operations)) {
                    final String[] list = StringUtils.split(operations, ";,");
                    operationList2 = new ArrayList<IEdoIntegrationService.EDO_OPERATION_NAMES>();
                    final String[] array;
                    int k = 0;
                    for (int length = array.length; k < length; ++k) {
                        final String a = array[k];
                        final String aTr = StringUtils.trimToNull(a);
                        if (aTr != null) {
                            b = IEdoIntegrationService.EDO_OPERATION_NAMES.parseValue(aTr);
                            if (b == null) {
                                this.logger.error(String.format("Failed to parse operation key value %s. Known values is %s", a, Arrays.asList(IEdoIntegrationService.EDO_OPERATION_NAMES.values()).stream().map((Function<? super Object, ?>)Enum::name).collect((Collector<? super Object, ?, String>)Collectors.joining(", "))));
                            }
                            else {
                                operationList2.add(b);
                            }
                        }
                    }
                }
                else {
                    operationList2 = Arrays.asList(IEdoIntegrationService.EDO_OPERATION_NAMES.values()).stream().filter(IEdoIntegrationService.EDO_OPERATION_NAMES::isNonServiceOperation).toList();
                }
                operationList2.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final IEdoIntegrationService.EDO_OPERATION_NAMES a2 = iterator.next();
                    final String operationNamePrefix = a2.name() + ".";
                    final HashMap<Object, String> operationParams = new HashMap<Object, String>();
                    this.getParams().entrySet().forEach(b -> {
                        if (StringUtils.startsWith((String)b.getKey(), operationNamePrefix) && b.getKey().length() > operationNamePrefix.length()) {
                            operationParams.put(StringUtils.substring((String)b.getKey(), operationNamePrefix.length()), b.getValue());
                        }
                        if (!operationParams.containsKey(b.getKey())) {
                            operationParams.put(b.getKey(), b.getValue());
                        }
                        return;
                    });
                    switch (a2) {
                        case QUEUE_TEST_DOCUMENT: {
                            final String testDocumentId = this.getParams().get("ID");
                            if (StringUtils.isNotBlank(testDocumentId)) {
                                final IDocument document = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, testDocumentId, ctx);
                                final SessionContext ctx2 = ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, ctx);
                                ctx2.set("__filialId", ctx.get("__docFilialId"));
                                ctx2.set("__filialName", ctx.get("__docFilialName"));
                                ctx2.set("__orgPositionId", (Object)document.getOwner());
                                ctx2.set("__orgPositionName", (Object)document.getOwnerName());
                                final String inn = this.getParams().get("INN");
                                final String kpp = this.getParams().get("KPP");
                                final String[] preQuery = new String[2];
                                if (StringUtils.isBlank(inn) && StringUtils.isBlank(kpp)) {
                                    preQuery[0] = String.format("update nt_external_addressee object set ssa_state='INIT' set ssa_state_name='' where sida_document in ('%s')", testDocumentId);
                                    preQuery[1] = String.format("update nt_contract_cntg object set ssa_state='INIT' set ssa_state_name='' where sida_parent in ('%s')", testDocumentId);
                                }
                                else if (StringUtils.isBlank(inn)) {
                                    preQuery[0] = String.format("update nt_external_addressee object set ssa_state='INIT' set ssa_state_name='' set ssa_kpp='%s' where sida_document in ('%s')", kpp, testDocumentId);
                                    preQuery[1] = String.format("update nt_contract_cntg object set ssa_state='INIT' set ssa_state_name='' set ssa_kpp='%s' where sida_parent in ('%s')", kpp, testDocumentId);
                                }
                                else if (StringUtils.isBlank(kpp)) {
                                    preQuery[0] = String.format("update nt_external_addressee object set ssa_state='INIT' set ssa_state_name='' set ssa_inn='%s' where sida_document in ('%s')", inn, testDocumentId);
                                    preQuery[1] = String.format("update nt_contract_cntg object set ssa_state='INIT' set ssa_state_name='' set ssa_inn='%s' where sida_parent in ('%s')", inn, testDocumentId);
                                }
                                else {
                                    preQuery[0] = String.format("update nt_external_addressee object set ssa_state='INIT' set ssa_state_name='' set ssa_inn='%s' set ssa_kpp='%s' where sida_document in ('%s')", inn, kpp, testDocumentId);
                                    preQuery[1] = String.format("update nt_contract_cntg object set ssa_state='INIT' set ssa_state_name='' set ssa_inn='%s' set ssa_kpp='%s' where sida_parent in ('%s')", inn, kpp, testDocumentId);
                                }
                                final ShardSessionRunner<Object> shardSessionRunner = new ShardSessionRunner<Object>() {
                                    final /* synthetic */ String[] val$preQuery;
                                    final /* synthetic */ DMSShardsClientSession val$session;
                                    final /* synthetic */ SessionContext val$ctx;
                                    
                                    public Object run() {
                                        final TransactionRunner<Object> runner = new TransactionRunner<Object>() {
                                            public Object run() {
                                                for (final String s : ShardSessionRunner.this.val$preQuery) {
                                                    ServiceReg.getQueryService().executeUpdateQuery(ShardSessionRunner.this.val$session.getClientSession(DMSShardsManager.SHARD.DICT.name()), s, ShardSessionRunner.this.val$ctx);
                                                }
                                                return null;
                                            }
                                        };
                                        runner.start(this.val$session.getClientSession(DMSShardsManager.SHARD.DICT.name()));
                                        return null;
                                    }
                                };
                                shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
                                if (DiadocEdoServiceReg.getDiadocEdoIntegrationService() != null) {
                                    DiadocEdoServiceReg.getDiadocEdoIntegrationService().queueDocument(session, document, this.getParams(), ctx2);
                                }
                                else {
                                    this.logger.trace(() -> String.format("Document %s. Diadoc Edo module not installed", document.getObjectId()));
                                }
                            }
                            else {
                                continue;
                            }
                            continue;
                        }
                        case PROCESS_FOR_SEND_QUEUE: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().processQueueForSend(session, this.getParams(), ctx);
                            continue;
                        }
                        case CHECK_STATUS: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().getRemoteDocumentState(session, this.getParams(), ctx);
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().checkLocalDocumentStatus(session, this.getParams(), ctx);
                            continue;
                        }
                        case CHECK_LOCAL_STATUS: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().checkLocalDocumentStatus(session, this.getParams(), ctx);
                            continue;
                        }
                        case GET_REMOTE_STATES: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().getRemoteDocumentState(session, this.getParams(), ctx);
                            continue;
                        }
                        case PROCESS_REMOTE_STATE_CHANGES: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().processRemoteDocumentStateChanges(session, this.getParams(), ctx);
                            continue;
                        }
                        case CHECK_REMOTE_STATUS: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().getRemoteDocumentState(session, this.getParams(), ctx);
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().processRemoteDocumentStateChanges(session, this.getParams(), ctx);
                            continue;
                        }
                        case CHECK_INCOMING_DOCUMENTS: {
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().checkIncomingDocuments(session, (Map<String, String>)operationParams, ctx);
                            continue;
                        }
                        case PROCESS_INCOMING_DOCUMENTS: {
                            ArrayList<String> checkedStatus = new ArrayList<String>();
                            final String statusesString = (String)operationParams.get(IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.STATUSES.name());
                            if (!StringUtils.isBlank(statusesString)) {
                                final String[] list2 = StringUtils.split(statusesString, ",;");
                                checkedStatus = new ArrayList<String>();
                                final String[] array2;
                                int l = 0;
                                for (int length2 = array2.length; l < length2; ++l) {
                                    final String state = array2[l];
                                    final EdoIntegrationProcessingState stateV = null;
                                    try {
                                        final EdoIntegrationProcessingState stateV2 = EdoIntegrationProcessingState.valueOf(StringUtils.upperCase(state));
                                        checkedStatus.add(stateV2.name());
                                    }
                                    catch (final Exception e2) {
                                        this.logger.error(String.format("Failed to parse operation key value %s. Known values is %s", a2, Arrays.asList(IEdoIntegrationService.EDO_OPERATION_NAMES.values()).stream().map((Function<? super Object, ?>)Enum::name).collect((Collector<? super Object, ?, String>)Collectors.joining(", "))));
                                    }
                                }
                            }
                            DiadocEdoServiceReg.getDiadocEdoIntegrationService().processIncomingDocumentsQueue(session, checkedStatus, (Map<String, String>)operationParams, ctx);
                            continue;
                        }
                        case CHECK_COUNTERAGENT: {
                            final String inn2 = this.getParams().get("INN");
                            String kpp2 = this.getParams().get("KPP");
                            if ("NONE".equals(kpp2)) {
                                kpp2 = null;
                            }
                            final String filialId = this.getParams().get("FILIAL");
                            try {
                                DiadocEdoServiceReg.getDiadocEdoIntegrationService().checkCounteragent(session, filialId, inn2, kpp2, this.getParams(), ctx);
                                continue;
                            }
                            catch (final Exception e3) {
                                this.logger.error(String.format("Error while checking counteragent with inn %s kpp %s", inn2, kpp2), (Throwable)e3);
                                throw e3;
                            }
                        }
                        case GET_COUNTERAGENT_BY_INN: {
                            final String inn3 = this.getParams().get("INN");
                            final String filialId2 = this.getParams().get("FILIAL");
                            Collection<Map<String, String>> res;
                            try {
                                res = DiadocEdoServiceReg.getDiadocEdoIntegrationService().getCounteragentsByInn(session, filialId2, inn3, (Map<String, String>)operationParams, ctx);
                            }
                            catch (final Exception e4) {
                                this.logger.error(String.format("Error while checking counteragent with inn %s", inn3), (Throwable)e4);
                                throw e4;
                            }
                            if (res != null) {
                                final StringBuilder sb = new StringBuilder();
                                sb.append("Founded orgs: \n");
                                res.iterator();
                                final Iterator iterator2;
                                while (iterator2.hasNext()) {
                                    final Map<String, String> b2 = iterator2.next();
                                    b2.entrySet().iterator();
                                    final Iterator iterator3;
                                    while (iterator3.hasNext()) {
                                        final Map.Entry<String, String> c = iterator3.next();
                                        sb.append(c.getKey()).append(" : ").append(c.getValue()).append("\n");
                                    }
                                    sb.append("\n");
                                }
                                throw new ServiceException(sb.toString());
                            }
                            else {
                                throw new ServiceException("Not found");
                            }
                            break;
                        }
                        case GET_ORGANIZATIONS_BY_INN: {
                            final String inn4 = this.getParams().get("INN");
                            final String filialId3 = this.getParams().get("FILIAL");
                            Collection<Map<String, String>> res2;
                            try {
                                res2 = DiadocEdoServiceReg.getDiadocEdoIntegrationService().getOrganizationsByInn(session, filialId3, inn4, (Map<String, String>)operationParams, ctx);
                            }
                            catch (final Exception e5) {
                                this.logger.error(String.format("Error while checking counteragent with inn %s", inn4), (Throwable)e5);
                                throw e5;
                            }
                            if (res2 != null) {
                                final StringBuilder sb2 = new StringBuilder();
                                sb2.append("Founded orgs: \n");
                                res2.iterator();
                                final Iterator iterator4;
                                while (iterator4.hasNext()) {
                                    final Map<String, String> b3 = iterator4.next();
                                    b3.entrySet().iterator();
                                    final Iterator iterator5;
                                    while (iterator5.hasNext()) {
                                        final Map.Entry<String, String> c2 = iterator5.next();
                                        sb2.append(c2.getKey()).append(" : ").append(c2.getValue()).append("\n");
                                    }
                                    sb2.append("\n");
                                }
                                throw new ServiceException(sb2.toString());
                            }
                            else {
                                throw new ServiceException("Not found");
                            }
                            break;
                        }
                        case SLEEP: {
                            long interval = 5L;
                            final String intervalS = this.getParams().get("INTERVAL");
                            if (StringUtils.isNotBlank(intervalS)) {
                                try {
                                    interval = Long.valueOf(intervalS);
                                }
                                catch (final NumberFormatException e6) {
                                    this.logger.info(String.format("Failed to parse interval value %s", intervalS), (Throwable)e6);
                                }
                            }
                            try {
                                Thread.sleep(1000L * interval);
                            }
                            catch (final InterruptedException e7) {
                                Thread.currentThread().interrupt();
                            }
                            continue;
                        }
                        case PROCESS_REMOTE_STATUS: {
                            continue;
                        }
                        case FIRE_SERVER_EVENT: {
                            final String testDocumentIdsStr = (String)operationParams.get(IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS.name());
                            if (StringUtils.isNotBlank(testDocumentIdsStr)) {
                                final String[] split;
                                final String[] testDocumentIdsList = split = StringUtils.split(testDocumentIdsStr, ";,");
                                int n = 0;
                                for (int length3 = split.length; n < length3; ++n) {
                                    final String testDocumentId2 = split[n];
                                    final IObjectModel model = ServiceReg.getObjectModelService().getObjectModel(session, testDocumentId2, ctx);
                                    SessionContext ctx3;
                                    if (model instanceof final IDocument document2) {
                                        ctx3 = ServiceReg.getDocumentRouteService().applyDocumentContext(session, document2, ctx);
                                    }
                                    else {
                                        ctx3 = ctx.clone();
                                    }
                                    final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
                                    final HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("skipTx", "true");
                                    eventHandlerService.fireSyncEvent(session, (String)operationParams.get("src"), (String)operationParams.get("event"), (Map)params, ctx3);
                                }
                            }
                            else {
                                continue;
                            }
                            continue;
                        }
                        case CHECK_EVENTS: {
                            continue;
                        }
                        case CREATE_REVOKE: {
                            final String testDocumentIdsStr2 = (String)operationParams.get(IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS.name());
                            if (StringUtils.isNotBlank(testDocumentIdsStr2)) {
                                final String[] split2;
                                final String[] testDocumentIdsList2 = split2 = StringUtils.split(testDocumentIdsStr2, ";,");
                                int n2 = 0;
                                for (int length4 = split2.length; n2 < length4; ++n2) {
                                    final String testDocumentId3 = split2[n2];
                                    final IDocument document3 = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, testDocumentId3, ctx);
                                    final HashMap<String, String> params2 = new HashMap<String, String>();
                                    final String paramNames = (String)operationParams.get("PARAM_NAMES");
                                    final String paramValues = (String)operationParams.get("PARAM_VALUES");
                                    if (StringUtils.isNotBlank(paramNames)) {
                                        final String[] paramNamesL = StringUtils.split(paramNames, ";");
                                        final String[] paramValuesL = StringUtils.split(paramValues, ";");
                                        for (int i = 0; i < paramNamesL.length && i < paramValuesL.length; ++i) {
                                            params2.put(paramNamesL[i], paramValuesL[i]);
                                        }
                                    }
                                    DiadocEdoServiceReg.getDiadocEdoIntegrationService().createOutgoingRevokeFile(session, document3, params2, ctx);
                                }
                            }
                            else {
                                continue;
                            }
                            continue;
                        }
                        case SEND_REVOKE: {
                            final String testDocumentIdsStr3 = (String)operationParams.get(IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS.name());
                            if (StringUtils.isNotBlank(testDocumentIdsStr3)) {
                                final String[] split3;
                                final String[] testDocumentIdsList3 = split3 = StringUtils.split(testDocumentIdsStr3, ";,");
                                int n3 = 0;
                                for (int length5 = split3.length; n3 < length5; ++n3) {
                                    final String testDocumentId4 = split3[n3];
                                    final IDocument document4 = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, testDocumentId4, ctx);
                                    final HashMap<String, String> params3 = new HashMap<String, String>();
                                    final String paramNames2 = (String)operationParams.get("PARAM_NAMES");
                                    final String paramValues2 = (String)operationParams.get("PARAM_VALUES");
                                    if (StringUtils.isNotBlank(paramNames2)) {
                                        final String[] paramNamesL2 = StringUtils.split(paramNames2, ";");
                                        final String[] paramValuesL2 = StringUtils.split(paramValues2, ";");
                                        for (int j = 0; j < paramNamesL2.length && j < paramValuesL2.length; ++j) {
                                            params3.put(paramNamesL2[j], paramValuesL2[j]);
                                        }
                                    }
                                    DiadocEdoServiceReg.getDiadocEdoIntegrationService().queueOutgoingRevokeFile(session, document4, params3, ctx);
                                }
                            }
                            else {
                                continue;
                            }
                            continue;
                        }
                        case INFO: {
                            final StringBuilder sb3 = new StringBuilder();
                            sb3.append("Available commands: \n");
                            IEdoIntegrationService.EDO_OPERATION_NAMES.values();
                            final IEdoIntegrationService.EDO_OPERATION_NAMES[] array3;
                            int n4 = 0;
                            for (int length6 = array3.length; n4 < length6; ++n4) {
                                final IEdoIntegrationService.EDO_OPERATION_NAMES b4 = array3[n4];
                                if (!b4.isServiceOperation()) {
                                    sb3.append(b4.name());
                                    if (b4.getParamNames().length > 0) {
                                        sb3.append("\nParams:\n");
                                        boolean first = true;
                                        b4.getParamNames();
                                        final IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES[] array4;
                                        int n5 = 0;
                                        for (int length7 = array4.length; n5 < length7; ++n5) {
                                            final IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES c3 = array4[n5];
                                            if (!first) {
                                                sb3.append(", ");
                                            }
                                            sb3.append(c3.name());
                                            first = false;
                                        }
                                    }
                                    sb3.append("\n\n");
                                }
                            }
                            throw new ServiceException(sb3.toString());
                        }
                        default: {
                            if (a2 != null) {
                                this.logger.error(String.format("Unsupported command %s", a2.name()));
                                continue;
                            }
                            else {
                                continue;
                            }
                            break;
                        }
                    }
                }
                return null;
            }).start(DMSShardsManager.SHARD.DICT, ctx);
        }
        catch (final Exception e) {
            this.logger.error("Error while checking status", (Throwable)e);
            throw new ServiceException(e);
        }
    }
    
    public static void main(final String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("Need config root path as param");
            return;
        }
        final String appPath = args[0];
        final Properties appProperties = new Properties();
        try (final InputStream in = ApplicationProperties.class.getResourceAsStream("/app.properties");
             final Reader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            appProperties.load(r);
        }
        ApplicationProperties.init(appProperties);
        DMSShardsManager.initShards();
        DMSShardsClientSession.syncShards = false;
        ServiceAndTypeInitializer.init();
        ru.korusconsulting.dmservice.integration.diadocedo.ServiceAndTypeInitializer.init();
        final String[] configAppPaths = { "scheduler/config/base", "scheduler/config/og", "modules/sbis-edo/sbis-edo-commons/src/main/resources" };
        final List<String> realConfigAppPaths = new ArrayList<String>();
        final String[] array = configAppPaths;
        for (int length = array.length, i = 0; i < length; ++i) {
            final String configAppPath = array[i];
            realConfigAppPaths.add(new File(appPath + "/" + configAppPath).getAbsolutePath());
        }
        final ConfigBase cb = new ConfigBase();
        cb.init((String[])realConfigAppPaths.toArray(new String[0]));
        ServiceReg.getConfigService().setInternalConfig((Object)cb);
        XmlTypes.initTypes(new SessionContext());
        XmlDocumentTypes.initRootDocumentTypes(new SessionContext());
        JoinFTTypes.initTypes(new SessionContext());
        XmlTypes.initAspects(new SessionContext());
        ServiceReg.getIntegrationLogService().initFromConfig(new SessionContext());
        ServiceReg.getSystemLogService().initWorkers(new SessionContext());
        ServiceReg.getIntegrationLogService().initWorkers(new SessionContext());
        final DiadocEdoInquiryProcessor exec = new DiadocEdoInquiryProcessor();
        exec.init();
        exec.invoke();
        Thread.sleep(5000L);
        System.exit(0);
    }
}
