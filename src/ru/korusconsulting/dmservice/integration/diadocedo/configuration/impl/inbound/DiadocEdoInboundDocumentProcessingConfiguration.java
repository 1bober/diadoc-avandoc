// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationInboundDocumentProcessingConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class DiadocEdoInboundDocumentProcessingConfiguration extends EdoIntegrationInboundDocumentProcessingConfiguration
{
    private static final long serialVersionUID = -7717845853425785963L;
    
    protected DiadocEdoInboundDocumentProcessingConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
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
}
