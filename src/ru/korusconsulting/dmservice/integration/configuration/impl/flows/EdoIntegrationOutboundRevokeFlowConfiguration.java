// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRevokeFlowConfiguration;

public abstract class EdoIntegrationOutboundRevokeFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationOutboundRevokeFlowConfiguration
{
    private static final long serialVersionUID = -7709642967822042728L;
    
    protected EdoIntegrationOutboundRevokeFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
