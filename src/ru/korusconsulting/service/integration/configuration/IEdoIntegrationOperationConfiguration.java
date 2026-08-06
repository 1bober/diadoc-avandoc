// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationOperationConfiguration extends ICstmConfigAdapter
{
    IDictionaryElementConfiguration getIntegrationLogEventIdStorage();
    
    void setIntegrationLogEventIdStorage(final IDictionaryElementConfiguration p0);
    
    String getIntegrationLogEventId();
}
