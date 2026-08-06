// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import org.apache.commons.io.FilenameUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import java.util.Arrays;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainer;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.dmservice.integration.impl.model.AttrLoader;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeContentUnit;

public class DiadocEdoExchangeContentUnit extends DiadocEdoExchangeCounteragentUnit implements IDiadocEdoExchangeContentUnit
{
    private static final long serialVersionUID = 1L;
    private EdoIntegrationProcessingState state;
    private final IDiadocEdoExchangeCounteragentUnit parent;
    private DiadocMessage_PostApiProtos.SignedContent.Builder builder;
    private String nameOnShelf;
    private String contentId;
    private String contentName;
    private String contentUid;
    private String diadocDocumentId;
    private long deliveryTimestamp;
    private Date deliveryTime;
    @JsonIgnore
    private final IDiadocEdoExchangeCounteragentUnit counteragentUnit;
    private List<AttrLoader<IEdoIntegrationExchangeItem>> attrs;
    
    public DiadocEdoExchangeContentUnit(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IDiadocEdoExchangeCounteragentUnit parent, final IEdoIntegrationContentContainer contentContainer, final SessionContext ctx) {
        final IEdoIntegrationExchangeUnit parent2 = parent.getParent();
        IEdoIntegrationExchangeOperationUnit parent3;
        if (parent2 instanceof IEdoIntegrationExchangeOperationUnit) {
            final IEdoIntegrationExchangeOperationUnit pO = parent3 = (IEdoIntegrationExchangeOperationUnit)parent2;
        }
        else {
            parent3 = null;
        }
        super(session, operationContext, parent3, ctx);
        this.attrs = Arrays.asList(new AttrLoader(a -> this.setDiadocDocumentId(a), b -> b.getDocumentEdoId()), new AttrLoader(a -> this.setContentUid(a), b -> b.getContentUid()), new AttrLoader(a -> this.setContentId(a), b -> b.getContentId()), new AttrLoader(a -> this.setContentName(a), b -> b.getContentName()));
        this.loadParentAttrs((IEdoIntegrationExchangeUnit)parent, true);
        this.contentUid = contentContainer.getUid();
        this.contentId = contentContainer.getUid();
        this.loadState(session, operationContext, false, ctx);
        this.counteragentUnit = parent;
        this.parent = parent;
        this.setContentName(contentContainer.getName());
    }
    
    @Override
    public DiadocMessage_PostApiProtos.SignedContent.Builder getSignedContentBuilder() {
        return this.builder;
    }
    
    @Override
    public void loadParentAttrs(final IEdoIntegrationExchangeUnit itemUp, final boolean preventNullOverride) {
        super.loadParentAttrs(itemUp, preventNullOverride);
        if (itemUp instanceof final IDiadocEdoExchangeContentUnit item) {
            this.setDiadocDocumentId(item.getDiadocDocumentId());
        }
    }
    
    @Override
    public void loadAttrs(final IEdoIntegrationExchangeItem item, final boolean preventNullOverride) {
        super.loadAttrs(item, preventNullOverride);
        for (final AttrLoader<IEdoIntegrationExchangeItem> attr : this.attrs) {
            final String v = attr.getGetter().apply(item);
            if (!preventNullOverride || StringUtils.isNotBlank(v)) {
                attr.getSetter().accept(v);
            }
        }
        this.setDeliveryTimestamp(item.getDeliveryTimestamp());
        this.setDeliveryTime(item.getDeliveryTime());
    }
    
    @Override
    public void commitAttrs(final IEdoIntegrationExchangeItem item) {
        super.commitAttrs(item);
        item.setDocumentEdoId(this.getDiadocDocumentId());
        item.setContentUid(this.getContentUid());
        item.setContentId(this.getContentId());
        item.setContentName(this.getContentName());
        item.setDeliveryTimestamp(this.getDeliveryTimestamp());
        item.setDeliveryTime(this.getDeliveryTime());
        if (this.getParent() != null) {
            item.setParentId(this.getParent().getObjectId());
        }
    }
    
    @Override
    public void commit(final DMSShardsClientSession session, final boolean setReady, final SessionContext ctx) {
        IEdoIntegrationExchangeItem item;
        if (this.getItem() == null) {
            final StringBuilder sb = new StringBuilder();
            if (this.getParent() != null && StringUtils.isNotBlank(this.getParent().getObjectId())) {
                sb.append(" and sida_parent ='").append(this.getParent().getObjectId()).append("'");
            }
            final String query = String.format("select r_object_id from nt_edo_integration_exchange_item where ssa_integration = '%s' and ssa_direction='%s' and ssa_item_type= '%s'\nand sida_filial= '%s' and sida_document = '%s' and sida_counteragent_container = '%s' and ssa_content_uid = '%s' and sba_not_actual = false and sba_main = false %s", "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), EdoIntegrationItemType.document.name(), this.getFilialId(), this.getDocumentId(), this.getCounteragentContainerId(), this.getContentUid(), sb.toString());
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
    
    @Override
    protected void preInit(final IEdoIntegrationExchangeItem item) {
        super.preInit(item);
        item.setMain(false);
    }
    
    public void queue() {
        if (this.state == null || this.state == EdoIntegrationProcessingState.NONE) {
            this.state = EdoIntegrationProcessingState.QUEUED;
        }
    }
    
    @Override
    public void setNameOnShelf(final String value) {
        this.nameOnShelf = value;
    }
    
    @Override
    public String getNameOnShelf() {
        return this.nameOnShelf;
    }
    
    @Override
    public void setSignedContentBuilder(final DiadocMessage_PostApiProtos.SignedContent.Builder value) {
        this.builder = value;
    }
    
    @Override
    public String getContentUid() {
        return this.contentUid;
    }
    
    public void setContentUid(final String value) {
        this.contentUid = value;
    }
    
    @Override
    public void loadState(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final SessionContext ctx) {
        this.loadState(session, operationContext, false, ctx);
    }
    
    protected void loadState(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final boolean loadAttrs, final SessionContext ctx) {
        final StringBuilder sb = new StringBuilder();
        if (this.getParent() != null && StringUtils.isNotBlank(this.getParent().getObjectId())) {
            sb.append(" and sida_parent ='").append(this.getParent().getObjectId()).append("'");
        }
        final String query = String.format("select r_object_id from nt_edo_integration_exchange_item where ssa_integration = '%s' and ssa_direction = '%s' and sida_filial = '%s' and sida_document = '%s' and sida_counteragent_container = '%s' and ssa_content_uid = '%s' and sba_not_actual = false and sba_ready = true and sba_main = false %s", "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), this.getFilialId(), this.getDocumentId(), this.getCounteragentContainerId(), this.getContentUid(), sb.toString());
        final String id = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
        IEdoIntegrationExchangeItem item = null;
        if (StringUtils.isNotBlank(id)) {
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Document %s.Found old exchange item %n%s", this.getDocumentId(), id));
            item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
            if (loadAttrs) {
                this.loadAttrs(item, true);
            }
            else {
                this.setSendState(EdoIntegrationProcessingState.from(item.getLocalState(), EdoIntegrationProcessingState.QUEUED));
            }
        }
        if (item == null) {
            this.setSendState(EdoIntegrationProcessingState.QUEUED);
            item = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().createObjectModel("nt_edo_integration_exchange_item", ctx);
            item.setStateCategory("simple");
            this.preInit(item);
            this.commit(session, item, false, ctx);
        }
        this.setItem(item);
    }
    
    public IEdoIntegrationExchangeUnit getParent() {
        return (IEdoIntegrationExchangeUnit)this.parent;
    }
    
    @Override
    public String getDiadocDocumentId() {
        return this.diadocDocumentId;
    }
    
    @Override
    public void setDiadocDocumentId(final String value) {
        this.diadocDocumentId = value;
    }
    
    public IDiadocEdoExchangeCounteragentUnit getCounteragentUnit() {
        return this.counteragentUnit;
    }
    
    @Override
    public String getContentId() {
        return this.contentId;
    }
    
    @Override
    public void setContentId(final String value) {
        this.contentId = value;
    }
    
    @Override
    public String getContentName() {
        return this.contentName;
    }
    
    @Override
    public void setContentName(final String value) {
        this.contentName = value;
    }
    
    @Override
    public long getDeliveryTimestamp() {
        return this.deliveryTimestamp;
    }
    
    @Override
    public void setDeliveryTimestamp(final long value) {
        this.deliveryTimestamp = value;
    }
    
    @Override
    public Date getDeliveryTime() {
        return this.deliveryTime;
    }
    
    @Override
    public void setDeliveryTime(final Date value) {
        this.deliveryTime = value;
    }
    
    public final String getName() {
        return FilenameUtils.getName(this.getContentName());
    }
}
