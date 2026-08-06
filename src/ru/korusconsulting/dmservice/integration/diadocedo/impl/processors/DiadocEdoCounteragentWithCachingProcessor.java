// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoCounteragentInfo;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoExternalCounteragentInfo;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public class DiadocEdoCounteragentWithCachingProcessor extends DiadocEdoCounteragentProcessor
{
    @Override
    protected IDiadocEdoExternalCounteragentInfo getCounteragentBoxGuidImpl(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final String inn, final String kpp, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiConnection = operationContext.getApiConnection(session, this.getService(), ctx);
        if (apiConnection == null) {
            throw new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
        }
        String cacheCheckQuery;
        if (StringUtils.isNotBlank(kpp)) {
            cacheCheckQuery = String.format("select r_object_id from nt_diadoc_edo_counteragent_info where ssa_inn = '%s' and ssa_kpp = '%s' and sida_server='%s' and sba_enabled=true", inn, kpp, apiConnection.getServerId());
        }
        else {
            cacheCheckQuery = String.format("select r_object_id from nt_diadoc_edo_counteragent_info where ssa_inn = '%s' and (ssa_kpp = '' or ssa_kpp is null) and sida_server='%s' and sba_enabled=true", inn, apiConnection.getServerId());
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Run counteragent box cache check query : %s", cacheCheckQuery));
        final String counteragentInfoId = ServiceReg.getQueryService().getFirstValueFromQuery(session, cacheCheckQuery, ctx);
        IDiadocEdoCounteragentInfo counteragentInfo;
        if (StringUtils.isNotBlank(counteragentInfoId)) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found cache object [%s] of diadoc box guid for %s", counteragentInfoId, this.formatCounteragentInfo(inn, kpp)), operationContext.getPropogateLogMode());
            counteragentInfo = (IDiadocEdoCounteragentInfo)ServiceReg.getObjectModelService().getObjectModel(session, counteragentInfoId, ctx);
            if (StringUtils.isNotBlank(counteragentInfo.getBoxGuid())) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found cache object [%s] of diadoc box with guid %s for counteragent for %s", counteragentInfoId, counteragentInfo.getBoxGuid(), this.formatCounteragentInfo(inn, kpp)), operationContext.getPropogateLogMode());
                return new IDiadocEdoExternalCounteragentInfo() {
                    @Override
                    public String getDepartmentId() {
                        return counteragentInfo.getDepartment();
                    }
                    
                    @Override
                    public String getBoxId() {
                        return counteragentInfo.getBoxGuid();
                    }
                    
                    @Override
                    public String getBoxIdGuid() {
                        return counteragentInfo.getBoxGuid();
                    }
                    
                    @Override
                    public String getOrgId() {
                        return counteragentInfo.getOrgId();
                    }
                    
                    @Override
                    public String getOrgName() {
                        return counteragentInfo.getName();
                    }
                    
                    @Override
                    public String getDepartmentName() {
                        return counteragentInfo.getDepartmentName();
                    }
                    
                    @Override
                    public String getFnsParticipantId() {
                        return counteragentInfo.getFnsParticipantId();
                    }
                    
                    @Override
                    public boolean isRoaming() {
                        return counteragentInfo.isRoaming();
                    }
                    
                    @Override
                    public boolean hasCertificateToSign() {
                        return counteragentInfo.hasCertificateToSign();
                    }
                    
                    @Override
                    public boolean IsForeign() {
                        return counteragentInfo.IsForeign();
                    }
                };
            }
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found cache object [%s] of diadoc box has empty guid for counteragent for %s", counteragentInfoId, this.formatCounteragentInfo(inn, kpp)));
        }
        else {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Cache object of diadoc box guid for counteragent for %s not found", this.formatCounteragentInfo(inn, kpp)), operationContext.getPropogateLogMode());
            counteragentInfo = (IDiadocEdoCounteragentInfo)ServiceReg.getObjectModelService().createObjectModel("nt_diadoc_edo_counteragent_info", ctx);
            counteragentInfo.setInn(inn);
            counteragentInfo.setKpp(kpp);
            counteragentInfo.setServerId(apiConnection.getServerId());
            counteragentInfo.setServerName(apiConnection.getServerName());
            counteragentInfo.setEnabled(true);
        }
        final IDiadocEdoExternalCounteragentInfo diadocEdoExternalCounteragentInfo = super.getCounteragentBoxGuidImpl(session, operationContext, api, document, inn, kpp, notInvitedCounteragentSendMode, allowTestCounteragent, ctx);
        if (diadocEdoExternalCounteragentInfo != null) {
            counteragentInfo.setBoxGuid(diadocEdoExternalCounteragentInfo.getBoxIdGuid());
            counteragentInfo.setDepartment(diadocEdoExternalCounteragentInfo.getDepartmentId());
            counteragentInfo.setName(StringUtils.substring(diadocEdoExternalCounteragentInfo.getOrgName(), 128));
            counteragentInfo.setOrgId(diadocEdoExternalCounteragentInfo.getOrgId());
            counteragentInfo.setDepartmentName(StringUtils.substring(diadocEdoExternalCounteragentInfo.getDepartmentName(), 128));
            counteragentInfo.setFnsParticipantId(diadocEdoExternalCounteragentInfo.getFnsParticipantId());
            counteragentInfo.setRoaming(diadocEdoExternalCounteragentInfo.isRoaming());
            counteragentInfo.setHasCertificateToSign(diadocEdoExternalCounteragentInfo.hasCertificateToSign());
            counteragentInfo.setIsForeign(diadocEdoExternalCounteragentInfo.IsForeign());
            final boolean isNew = counteragentInfo.isNew();
            ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)counteragentInfo, ctx);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s cache object [%s] of diadoc box with guid %s for %s", isNew ? "Create new" : "Update", counteragentInfo.getObjectId(), counteragentInfo.getBoxGuid(), this.formatCounteragentInfo(inn, kpp)), LogPropogateMode.all_level);
        }
        return diadocEdoExternalCounteragentInfo;
    }
}
