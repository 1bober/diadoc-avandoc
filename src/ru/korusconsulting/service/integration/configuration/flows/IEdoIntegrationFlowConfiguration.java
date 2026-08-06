// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.flows;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import java.util.List;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationFlowConfigurationType;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationFlowConfiguration extends ICstmConfigAdapter
{
    EdoIntegrationFlowConfigurationType getFlowType();
    
    List<IEdoIntegrationOperationConfiguration> getOperationConfigurations();
    
    void clearOperationConfigurations();
    
    void addOperationConfigurationFromWrapper(final Object p0);
    
    IDictionaryElementConfiguration getEventStorage();
    
    void setEventStorage(final IDictionaryElementConfiguration p0);
    
    String getEvent();
}
