// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationCounteragentFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationCounteragentFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationCounteragentFlowConfiguration
{
    private static final long serialVersionUID = -3152527698214538987L;
    
    protected EdoIntegrationCounteragentFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.COUNTERAGENT;
    }
}
