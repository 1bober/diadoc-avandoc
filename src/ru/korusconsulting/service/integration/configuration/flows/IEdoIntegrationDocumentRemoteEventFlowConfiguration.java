// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.flows;

import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationRemoteEventFlowConfiguration;

public interface IEdoIntegrationDocumentRemoteEventFlowConfiguration extends IEdoIntegrationRemoteEventFlowConfiguration
{
    void clearChangeStatusProcessors();
    
    void addChangeStatusProcessorFromWrapper(final Object p0);
    
    void addChangeStatusProcessor(final IEdoIntegrationOperationConfiguration p0);
    
    List<IEdoIntegrationOperationConfiguration> getChangeStatusProcessors();
}
