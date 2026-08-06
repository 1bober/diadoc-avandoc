// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationProcessingConfiguration
{
    String getLocalEnvironmentsNames();
    
    void setLocalEnvironmentsNames(final String p0);
    
    String getGlobalEnvironmentsNames();
    
    void setGlobalEnvironmentsNames(final String p0);
    
    String getConnectionInfoEnvironmentsNames();
    
    void setConnectionInfoEnvironmentsNames(final String p0);
}
