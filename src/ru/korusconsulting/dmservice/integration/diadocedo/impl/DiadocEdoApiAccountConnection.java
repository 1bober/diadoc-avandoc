// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoAccountsNotAvailable;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoServiceUrlNotDefinedException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoApiKeyNotDefinedException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import java.util.HashMap;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Properties;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import Diadoc.Api.Proto.OrganizationProtos;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiAccountConnection;

public class DiadocEdoApiAccountConnection extends DiadocEdoApiConnection implements IDiadocEdoApiAccountConnection
{
    private static Logger log;
    private Map<String, OrganizationInfo> organizationInfos;
    private final String url;
    private final String webUrl;
    private final String serverId;
    private final String serverName;
    private final String accountId;
    private final String accountName;
    private OrganizationProtos.Box defaultBox;
    boolean invalid;
    
    public Logger getLogger() {
        return DiadocEdoApiAccountConnection.log;
    }
    
    protected DiadocEdoApiAccountConnection(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext oContext, final IDiadocEdoConnectionConfig config, final Properties props, final SessionContext ctx) throws DiadocEdoIntegrationException {
        super(session, service, oContext, config.getUrl(), config.getApiKey(), config.getLogin(), config.getPassword(), props, ctx);
        this.organizationInfos = new HashMap<String, OrganizationInfo>();
        this.url = config.getUrl();
        this.webUrl = config.getWebUrl();
        this.serverId = config.getServerId();
        this.serverName = config.getServerName();
        this.accountId = config.getAccountId();
        this.accountName = config.getAccountName();
        final OrganizationProtos.OrganizationList ol = DiadocEdoApiMethodLibrary.getMyOrganizations(session, service, oContext, this.getApi(), ctx);
        for (int i = 0; i < ol.getOrganizationsCount(); ++i) {
            final OrganizationProtos.Organization myOrg = ol.getOrganizations(i);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Checking organization %s with id %s", myOrg.getFullName(), myOrg.getOrgId()));
            final Map<String, OrganizationProtos.Box> boxesList = new HashMap<String, OrganizationProtos.Box>();
            final Map<String, OrganizationProtos.Department> departmentsList = new HashMap<String, OrganizationProtos.Department>();
            for (int j = 0; j < myOrg.getDepartmentsCount(); ++j) {
                final OrganizationProtos.Department dep = myOrg.getDepartments(j);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Found department %s with id %s", dep.getDepartmentId(), dep.getName()));
                departmentsList.put(dep.getDepartmentId(), dep);
            }
            final OrganizationInfo oi = new OrganizationInfo(myOrg, boxesList, departmentsList);
            for (int k = 0; k < myOrg.getBoxesCount(); ++k) {
                final OrganizationProtos.Box cB = myOrg.getBoxes(k);
                if (i == 0 && k == 0) {
                    this.defaultBox = cB;
                }
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Found box id %s with guid %s", cB.getBoxId(), cB.getBoxIdGuid()));
                boxesList.put(cB.getBoxId(), cB);
                boxesList.put(cB.getBoxIdGuid(), cB);
                this.organizationInfos.put(cB.getBoxId(), oi);
                this.organizationInfos.put(cB.getBoxIdGuid(), oi);
            }
        }
    }
    
    @Override
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public String getWebUrl() {
        return this.webUrl;
    }
    
    public static DiadocEdoApiAccountConnection getConnection(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoConnectionConfig config, final SessionContext ctx) throws DiadocEdoIntegrationException {
        if (StringUtils.isBlank(config.getApiKey())) {
            throw new DiadocEdoApiKeyNotDefinedException();
        }
        if (StringUtils.isBlank(config.getUrl())) {
            throw new DiadocEdoServiceUrlNotDefinedException();
        }
        final Properties props = IDiadocEdoConnectionConfig.loadProperties(session, config.getServerId(), ctx);
        return new DiadocEdoApiAccountConnection(session, service, oContext, config, props, ctx);
    }
    
    @Override
    public boolean isInvalid() {
        return this.invalid;
    }
    
    @Override
    public void markInvalid() {
        this.invalid = true;
    }
    
    @Override
    public String getServerId() {
        return this.serverId;
    }
    
    @Override
    public String getServerName() {
        return this.serverName;
    }
    
    @Override
    public String getAccountId() {
        return this.accountId;
    }
    
    @Override
    public String getAccountName() {
        return this.accountName;
    }
    
    @Override
    public OrganizationProtos.Box getDefaultBox() {
        return this.defaultBox;
    }
    
    @Override
    public OrganizationProtos.Box getBox(final String boxGuid) throws DiadocEdoAccountsNotAvailable {
        final OrganizationInfo oi = this.organizationInfos.get(boxGuid);
        if (oi != null) {
            return oi.box().get(boxGuid);
        }
        throw new DiadocEdoAccountsNotAvailable(String.format("Failed to find box %s", boxGuid));
    }
    
    static {
        DiadocEdoApiAccountConnection.log = LogManager.getLogger();
    }
}
