// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRequestFlowConfiguration;

public abstract class EdoIntegrationOutboundRequestFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationOutboundRequestFlowConfiguration
{
    private static final long serialVersionUID = -4775111559523263040L;
    
    protected EdoIntegrationOutboundRequestFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
