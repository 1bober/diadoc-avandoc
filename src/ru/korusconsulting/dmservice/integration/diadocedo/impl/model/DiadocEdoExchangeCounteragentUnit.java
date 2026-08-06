// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import java.util.Collections;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import java.util.Iterator;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeContentUnit;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.ArrayList;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Map;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import java.util.List;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;
import ru.korusconsulting.dmservice.integration.impl.model.EdoIntegrationExchangeCounteragentUnit;

public class DiadocEdoExchangeCounteragentUnit extends EdoIntegrationExchangeCounteragentUnit implements IDiadocEdoExchangeCounteragentUnit
{
    private static final long serialVersionUID = 1L;
    private final List<IEdoIntegrationExchangeUnit> children;
    private String diadocMessageId;
    private Map<String, Object> messageAsPojo;
    
    public DiadocEdoExchangeCounteragentUnit(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit parent, final SessionContext ctx) {
        super(session, operationContext, parent, ctx);
        this.children = new ArrayList<IEdoIntegrationExchangeUnit>();
    }
    
    public DiadocEdoExchangeCounteragentUnit(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeItem item, final SessionContext ctx) {
        super(session, operationContext, item, ctx);
        this.children = new ArrayList<IEdoIntegrationExchangeUnit>();
    }
    
    public List<IEdoIntegrationExchangeUnit> getChildren() {
        return this.children;
    }
    
    public void addChild(final IEdoIntegrationExchangeUnit value) {
        this.getChildren().add(value);
    }
    
    public void loadParentAttrs(final IEdoIntegrationExchangeUnit itemUp, final boolean preventNullOverride) {
        super.loadParentAttrs(itemUp, preventNullOverride);
        if (itemUp instanceof final IDiadocEdoExchangeCounteragentUnit item) {
            this.setDiadocMessageId(item.getDiadocMessageId());
        }
    }
    
    public void loadAttrs(final IEdoIntegrationExchangeItem item, final boolean preventNullOverride) {
        super.loadAttrs(item, preventNullOverride);
        this.setDiadocMessageId(item.getMessageEdoId());
    }
    
    public void commitAttrs(final IEdoIntegrationExchangeItem item) {
        super.commitAttrs(item);
        item.setMessageEdoId(this.getDiadocMessageId());
    }
    
    public void commit(final DMSShardsClientSession session, final boolean setReady, final SessionContext ctx) {
        IEdoIntegrationExchangeItem item;
        if (this.getItem() == null) {
            final String query = String.format("select r_object_id from nt_edo_integration_exchange_item where ssa_integration = '%s' and ssa_direction = '%s' and ssa_item_type= '%s' and sida_filial = '%s' and sida_document = '%s'\n and sida_counteragent_container = '%s' and sba_not_actual = false and sba_main=true", "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), EdoIntegrationItemType.document.name(), this.getFilialId(), this.getDocumentId(), this.getCounteragentContainerId());
            final String id = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
            if (StringUtils.isNotBlank(id)) {
                item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
            }
            else {
                item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().createObjectModel("nt_edo_integration_exchange_item", ctx);
                item.setStateCategory("simple");
                this.preInit(item);
            }
        }
        else {
            item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, this.getItem().getObjectId(), ctx);
        }
        this.commit(session, item, setReady, ctx);
    }
    
    protected void preInit(final IEdoIntegrationExchangeItem item) {
        item.setIntegration("DIADOCEDO");
        item.setDirection(IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name());
        item.setMain(true);
        item.setItemType((this.getItemType() == null) ? null : this.getItemType().name());
    }
    
    protected void commit(final DMSShardsClientSession session, final IEdoIntegrationExchangeItem item, final boolean setReady, final SessionContext ctx) {
        this.commitAttrs(item);
        item.setItemType((this.getItemType() == null) ? null : this.getItemType().name());
        item.setLocalState((this.getSendState() != null) ? this.getSendState().name() : null);
        if (this.getSendPrepareError() != null) {
            item.setLocalStateSystemCode(this.getSendPrepareError().name());
        }
        if (this.getSendState() == EdoIntegrationProcessingState.QUEUED) {
            item.setQueued(true);
        }
        else if (this.getSendState() == EdoIntegrationProcessingState.COMPLETED) {
            item.setSended(true);
        }
        if (setReady) {
            item.setReady(setReady);
        }
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item, ctx);
        this.setObjectId(item.getObjectId());
        this.setItem(item);
        this.children.forEach(a -> a.commit(session, setReady, ctx));
    }
    
    public IDiadocEdoExchangeContentUnit getChildren(final String key) {
        for (final IEdoIntegrationExchangeUnit u : this.getChildren()) {
            if (u instanceof final IDiadocEdoExchangeContentUnit cU) {
                if (cU.getContentUid().equals(key)) {
                    return cU;
                }
                continue;
            }
        }
        return null;
    }
    
    public void loadState(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final SessionContext ctx) {
        final String query = String.format("select r_object_id from nt_edo_integration_exchange_item where ssa_integration = '%s' and ssa_direction = '%s' and sida_filial = '%s' and sida_document = '%s' and sida_counteragent_container = '%s' and sba_not_actual = false and sba_ready = true and sba_main = true", "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), this.getFilialId(), this.getDocumentId(), this.getCounteragentContainerId());
        final String id = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
        IEdoIntegrationExchangeItem item = null;
        if (StringUtils.isNotBlank(id)) {
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Document %s.Found old exchange item %n%s", this.getDocumentId(), id));
            item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
            this.loadAttrs(item, true);
        }
        if (item == null) {
            item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().createObjectModel("nt_edo_integration_exchange_item", ctx);
            item.setStateCategory("simple");
            this.preInit(item);
            this.commit(session, item, false, ctx);
        }
        this.setObjectId(item.getObjectId());
        this.setItem(item);
    }
    
    public String getDiadocMessageId() {
        return this.diadocMessageId;
    }
    
    public void setDiadocMessageId(final String value) {
        this.diadocMessageId = value;
    }
    
    public void setCounteragentContainerId(final String value) {
        super.setCounteragentContainerId(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setCounteragentContainerId(value);
            }
        });
    }
    
    public void setCounteragentName(final String value) {
        super.setCounteragentName(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setCounteragentName(value);
            }
        });
    }
    
    public void setCounteragentId(final String value) {
        super.setCounteragentId(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setCounteragentId(value);
            }
        });
    }
    
    public void setToBox(final String value) {
        super.setToBox(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setToBox(value);
            }
        });
    }
    
    public void setToBoxGuid(final String value) {
        super.setToBoxGuid(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setToBoxGuid(value);
            }
        });
    }
    
    public void setToDepartment(final String value) {
        super.setToDepartment(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setToDepartment(value);
            }
        });
    }
    
    public void setFromName(final String value) {
        super.setFromName(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromName(value);
            }
        });
    }
    
    public void setFromInn(final String value) {
        super.setFromInn(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromInn(value);
            }
        });
    }
    
    public void setFromKpp(final String value) {
        super.setFromKpp(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromKpp(value);
            }
        });
    }
    
    public void setFromBox(final String value) {
        super.setFromBox(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromBox(value);
            }
        });
    }
    
    public void setFromBoxGuid(final String value) {
        super.setFromBoxGuid(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromBoxGuid(value);
            }
        });
    }
    
    public void setFromDepartment(final String value) {
        super.setFromDepartment(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setFromDepartment(value);
            }
        });
    }
    
    public void setSendPrepareError(final EdoIntegrationSubsystemErrorCode value) {
        super.setSendPrepareError(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setSendPrepareError(value);
            }
        });
    }
    
    public void setServerId(final String value) {
        super.setServerId(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setServerId(value);
            }
        });
    }
    
    public void setServerName(final String value) {
        super.setServerName(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setServerName(value);
            }
        });
    }
    
    public void setAccountId(final String value) {
        super.setAccountId(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setAccountId(value);
            }
        });
    }
    
    public void setAccountName(final String value) {
        super.setAccountName(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setAccountName(value);
            }
        });
    }
    
    public void setAccountMemberId(final String value) {
        super.setAccountMemberId(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setAccountMemberId(value);
            }
        });
    }
    
    public void setAccountMemberName(final String value) {
        super.setAccountMemberName(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setAccountMemberName(value);
            }
        });
    }
    
    public void setCounteragentInn(final String value) {
        super.setCounteragentInn(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setCounteragentInn(value);
            }
        });
    }
    
    public void setCounteragentKpp(final String value) {
        super.setCounteragentKpp(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setCounteragentKpp(value);
            }
        });
    }
    
    public void setSendState(final EdoIntegrationProcessingState value) {
        super.setSendState(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setSendState(value);
            }
        });
    }
    
    public void setToInn(final String value) {
        super.setToInn(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setToInn(value);
            }
        });
    }
    
    public void setToKpp(final String value) {
        super.setToKpp(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setToKpp(value);
            }
        });
    }
    
    public void setUseDictionaryOrganizationDetails(final boolean value) {
        super.setUseDictionaryOrganizationDetails(value);
        this.children.forEach(a -> {
            if (a instanceof final IDiadocEdoExchangeCounteragentUnit aC) {
                aC.setUseDictionaryOrganizationDetails(value);
            }
        });
    }
    
    public void initConnectionInfo(final IDiadocEdoConnectionConfig apiConnectionConfig) {
        if (apiConnectionConfig != null) {
            this.setServerId(apiConnectionConfig.getServerId());
            this.setServerName(apiConnectionConfig.getServerName());
            this.setAccountId(apiConnectionConfig.getAccountId());
            this.setAccountName(apiConnectionConfig.getAccountName());
            this.setAccountMemberId(apiConnectionConfig.getAccountMemberId());
            this.setAccountMemberName(apiConnectionConfig.getAccountMemberName());
            this.setFromBox(apiConnectionConfig.getBoxId());
        }
    }
    
    public void setSendStateInfo(final Map<String, Object> messageAsPojo, final EdoIntegrationProcessingState sendState) {
        this.messageAsPojo = messageAsPojo;
        this.setSendState(sendState);
    }
    
    public Map<String, Object> getMessageAsPojo() {
        return (this.messageAsPojo == null) ? Collections.emptyMap() : this.messageAsPojo;
    }
    
    public IEdoIntegrationPackageProcessingContext getProcessingContext() {
        return null;
    }
    
    public void setProcessingContext(final IEdoIntegrationPackageProcessingContext value) {
    }
    
    public String getRemoteStateProcessingStep() {
        return null;
    }
    
    public void setRemoteStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
    }
    
    public String getLocalStateProcessingStep() {
        return null;
    }
    
    public void setLocalStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
    }
    
    public String getProcessingState() {
        return null;
    }
    
    public void setProcessingState(final EdoIntegrationProcessingState value) {
    }
    
    public String getProcessingError() {
        return null;
    }
    
    public void setProcessingError(final IEdoIntegrationInboundDataExchangeItem.ProcessingError value) {
    }
    
    public String getDocumentEdoId() {
        return null;
    }
    
    public void setDocumentEdoId(final String value) {
    }
    
    public String getMessageEdoId() {
        return null;
    }
    
    public void setMessageEdoId(final String value) {
    }
    
    public String getCounteragentBoxGuid() {
        return null;
    }
    
    public void setErrorCount(final int value) {
    }
    
    public int getErrorCount() {
        return 0;
    }
}
