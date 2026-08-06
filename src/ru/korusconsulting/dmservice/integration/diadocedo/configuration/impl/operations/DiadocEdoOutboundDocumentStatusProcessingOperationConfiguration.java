// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoExchangeCategoryConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoSendPrepareErrorConfiguration;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.status.StateConfigCoverage;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration extends EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration implements IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = 80989571323513201L;
    public static final String CONFIG_ID = "diadocedo_status_processing";
    public static final String XML_TAG = "status_processing";
    
    public DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration() {
        super((ICstmConfigAdapter)null);
    }
    
    public DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public void init() {
        super.init();
        this.setXmlTag("status_processing");
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createRemoteStatus(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createRemoteStatus(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule createIncomingAttachmentProcessingRule(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttachmentProcessingRule(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule createIncomingAttachmentProcessingRule(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttachmentProcessingRule(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessingRule(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttachmentProcessingRule(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessingRule(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttachmentProcessingRule(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public String getCoverage() {
        return StateConfigCoverage.any.name();
    }
    
    public IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        final DiadocEdoSendPrepareErrorConfiguration type = new DiadocEdoSendPrepareErrorConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, component, generator, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final DiadocEdoSendPrepareErrorConfiguration type = new DiadocEdoSendPrepareErrorConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, config, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        final DiadocEdoExchangeCategoryConfiguration type = new DiadocEdoExchangeCategoryConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, component, generator, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final DiadocEdoExchangeCategoryConfiguration type = new DiadocEdoExchangeCategoryConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, config, ctx);
        return (IDictionaryElementConfiguration)type;
    }
}
