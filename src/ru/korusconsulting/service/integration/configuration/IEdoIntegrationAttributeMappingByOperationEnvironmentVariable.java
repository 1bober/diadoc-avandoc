// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationAttributeMappingByOperationEnvironmentVariable extends IEdoIntegrationRemoteAttributeMapping
{
    String getEnvironmentName();
    
    void setEnvironmentName(final String p0);
    
    String getEnvironmentVariable();
    
    void setEnvironmentVariable(final String p0);
}
