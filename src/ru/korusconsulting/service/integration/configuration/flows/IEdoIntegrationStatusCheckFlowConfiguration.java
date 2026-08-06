// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.flows;

public interface IEdoIntegrationStatusCheckFlowConfiguration extends IEdoIntegrationFlowConfiguration
{
    String getStatusPath();
    
    void setStatusPath(final String p0);
}
