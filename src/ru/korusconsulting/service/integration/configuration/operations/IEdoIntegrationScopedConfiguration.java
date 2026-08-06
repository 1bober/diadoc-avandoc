// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;

public interface IEdoIntegrationScopedConfiguration extends IEdoIntegrationOperationConfiguration
{
    String getComponentName();
    
    IDictionaryElementConfiguration getIntegrationLogEventIdStorage();
    
    void setIntegrationLogEventIdStorage(final IDictionaryElementConfiguration p0);
    
    String getIntegrationLogEventId();
}
