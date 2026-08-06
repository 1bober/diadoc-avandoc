// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationOutboundFlowConfiguration
{
    private static final long serialVersionUID = 7225177781450874808L;
    
    protected EdoIntegrationOutboundFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.OUTBOUND;
    }
}
