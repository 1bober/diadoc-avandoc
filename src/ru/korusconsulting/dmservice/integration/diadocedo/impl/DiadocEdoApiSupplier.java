// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiAccountConnection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.wstack.net.client.contentserver.ICollection;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccountMember;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccount;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.service.shards.DMSShardsManager;
import java.util.HashMap;
import java.util.function.Function;
import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;
import java.util.Collection;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class DiadocEdoApiSupplier
{
    private Logger log;
    private final Object surveyObjectsMutex;
    private final Object connectionMutex;
    private Map<String, IDiadocEdoConnectionConfig> surveyObjects;
    private DiadocEdoApiAccountConnection lastAccountConnection;
    private DiadocEdoApiBoxConnection lastFilialConnection;
    
    public DiadocEdoApiSupplier() {
        this.log = this.createLogger();
        this.surveyObjectsMutex = new Object();
        this.connectionMutex = new Object();
        this.surveyObjects = null;
    }
    
    protected Logger createLogger() {
        return LogManager.getLogger();
    }
    
    public Logger getLogger() {
        return this.log;
    }
    
    public Map<String, IDiadocEdoConnectionConfig> getActiveConnectionConfigs(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        synchronized (this.surveyObjectsMutex) {
            if (this.surveyObjects == null) {
                this.surveyObjects = this.initActiveConnectionConfigs(session, operationContext, ctx);
            }
        }
        return this.surveyObjects;
    }
    
    public Collection<String> getConnectedFilials(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final Collection<IntegrationOperationContext.ConnectionOptions> options, final SessionContext ctx) {
        if (options.contains(IntegrationOperationContext.ConnectionOptions.dontCheckIncoming)) {
            return (Collection<String>)this.getActiveConnectionConfigs(session, operationContext, ctx).entrySet().stream().filter(a -> !a.getValue().isDontCheckIncoming()).map((Function<? super Object, ?>)Map.Entry::getKey).toList();
        }
        return this.getActiveConnectionConfigs(session, operationContext, ctx).keySet();
    }
    
    protected Map<String, IDiadocEdoConnectionConfig> initActiveConnectionConfigs(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        final Map<String, IDiadocEdoConnectionConfig> connectionConfigs = new HashMap<String, IDiadocEdoConnectionConfig>();
        final IClientSession clientSession = session.getClientSession(DMSShardsManager.getShardNameByContext(ctx));
        try (final ICollection col = clientSession.selectQuery("select distinct am.sida_filial, am.ssa_filial_name, am.r_object_id as member_id, a.r_object_id as account_id, s.r_object_id as server_id from nt_diadoc_edo_account_member am, nt_diadoc_edo_account a, nt_diadoc_edo_server s where am.sida_account=a.r_object_id and a.sida_server=s.r_object_id and am.sba_enabled=true and a.sba_enabled=true and s.sba_enabled=true")) {
            while (col.next()) {
                try {
                    final IDiadocEdoServer server = (IDiadocEdoServer)ServiceReg.getObjectModelService().getObjectModel(session, col.getString("server_id"), ctx);
                    final IDiadocEdoAccount account = (IDiadocEdoAccount)ServiceReg.getObjectModelService().getObjectModel(session, col.getString("account_id"), ctx);
                    final IDiadocEdoAccountMember member = (IDiadocEdoAccountMember)ServiceReg.getObjectModelService().getObjectModel(session, col.getString("member_id"), ctx);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found connection config for filial %s / %s, server %s / %s, account %s / %s, member %s / %s", col.getString("ssa_filial_name"), col.getString("sida_filial"), server.getName(), server.getObjectId(), account.getLogin(), account.getObjectId(), member.getAccountName(), member.getObjectId()));
                    connectionConfigs.put(col.getString("sida_filial"), new DiadocEdoConnectionConfig(session, server, account, member, ctx));
                }
                catch (final Throwable e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to load connection config for filial %s / %s, server %s, account %s, member %s", col.getString("ssa_filial_name"), col.getString("sida_filial"), col.getString("member_id"), col.getString("account_id"), col.getString("server_id")), e);
                }
            }
        }
        return connectionConfigs;
    }
    
    public IDiadocEdoApiBoxConnection getApi(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final String filialId, final SessionContext ctx) throws DiadocEdoIntegrationException {
        synchronized (this.connectionMutex) {
            if (this.lastFilialConnection != null && StringUtils.equals(this.lastFilialConnection.getFilialId(), filialId)) {
                if (!this.lastFilialConnection.getAccountConnection().isInvalid()) {
                    return this.lastFilialConnection;
                }
                this.lastFilialConnection = null;
            }
            final IDiadocEdoConnectionConfig connectionConfig = this.getActiveConnectionConfigs(session, operationContext, ctx).get(filialId);
            if (connectionConfig == null) {
                throw new DiadocEdoNoConnectionException(String.format("No connection settings for filial %s", filialId));
            }
            if (this.lastAccountConnection != null && StringUtils.equals(this.lastAccountConnection.getAccountId(), connectionConfig.getAccountId())) {
                if (!this.lastAccountConnection.isInvalid()) {
                    return this.lastFilialConnection = new DiadocEdoApiBoxConnection(this.lastAccountConnection, connectionConfig);
                }
                this.lastAccountConnection = null;
            }
            this.lastAccountConnection = DiadocEdoApiAccountConnection.getConnection(session, operationContext, service, connectionConfig, ctx);
            return this.lastFilialConnection = new DiadocEdoApiBoxConnection(this.lastAccountConnection, connectionConfig);
        }
    }
    
    public IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        return this.getActiveConnectionConfigs(session, operationContext, ctx).get(operationContext.getFilialId());
    }
}
