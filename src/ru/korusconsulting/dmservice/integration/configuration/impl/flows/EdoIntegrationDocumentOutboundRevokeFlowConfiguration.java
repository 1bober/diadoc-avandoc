// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundRevokeOperationScopedConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class EdoIntegrationDocumentOutboundRevokeFlowConfiguration extends EdoIntegrationFlowConfiguration
{
    private static final long serialVersionUID = -6590397847488898631L;
    public static final String CONFIG_ID;
    
    public EdoIntegrationDocumentOutboundRevokeFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationOutboundRevokeOperationScopedConfiguration(this);
        config.unmarshal(session, component, generator, ctx);
        return config;
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationOutboundRevokeOperationScopedConfiguration(this);
        config.unmarshal(session, element, ctx);
        return config;
    }
    
    @Override
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.OUTBOUND_REVOKE;
    }
    
    static {
        CONFIG_ID = EdoIntegrationFlowConfigurationType.OUTBOUND_REVOKE.name();
    }
}
