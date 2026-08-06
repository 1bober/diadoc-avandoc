// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.errors;

import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationStatusProcessorConfiguration;

public class DiadocEdoStatusProcessorConfiguration extends EdoIntegrationStatusProcessorConfiguration
{
    private static final long serialVersionUID = -4393658336889072202L;
    public static final String CONFIG_ID = "diadoc_status_processing";
    
    public DiadocEdoStatusProcessorConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    protected IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createRemoteStatus(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    protected IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createRemoteStatus(session, el, (ICstmConfigAdapter)this, ctx);
    }
}
