// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentLocalStatus;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationOutboundDocumentLocalStatusOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration extends EdoIntegrationOutboundDocumentLocalStatusOperationConfiguration implements IDiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = 6297788297768082887L;
    public static final String CONFIG_ID = "diadocedo_local_status_processing_operation";
    public static final String XML_TAG = "local_status_processing_operation";
    
    public DiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public void init() {
        super.init();
        this.setXmlTag("local_status_processing_operation");
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createLocalStatus(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createLocalStatus(session, el, (ICstmConfigAdapter)this, ctx);
    }
}
