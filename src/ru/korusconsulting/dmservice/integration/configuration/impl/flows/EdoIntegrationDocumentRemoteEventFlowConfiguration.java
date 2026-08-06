// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.remoteevent.EdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class EdoIntegrationDocumentRemoteEventFlowConfiguration extends EdoIntegrationFlowConfiguration
{
    private static final long serialVersionUID = -5862029353317063354L;
    public static final String CONFIG_ID;
    
    public EdoIntegrationDocumentRemoteEventFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration(this);
        config.unmarshal(session, component, generator, ctx);
        return config;
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration(this);
        config.unmarshal(session, element, ctx);
        return config;
    }
    
    @Override
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.REMOTE_EVENT;
    }
    
    static {
        CONFIG_ID = EdoIntegrationFlowConfigurationType.REMOTE_EVENT.name();
    }
}
