// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoDocumentAttributeDesc;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentWithMappingConfiguration;

public interface IEdoIntegrationDocumentOperationConfiguration extends IEdoIntegrationDocumentWithMappingConfiguration, IEdoIntegrationOperationConfiguration
{
    boolean isDontMarkAsProceed();
    
    void setDontMarkAsProceed(final boolean p0);
    
    boolean isBreakOperation();
    
    void setBreakOperation(final boolean p0);
    
    String getEdoDocumentProcessorClassName();
    
    String getEdoDocumentQueueProcessorClassName();
    
    List<IEdoDocumentAttributeDesc> getEdoDocumentAttributes();
    
    List<IEdoIntegrationInboundDocumentProcessingConfiguration> getQueuedStatusProcessing();
    
    List<IEdoIntegrationInboundDocumentProcessingConfiguration> getSendedStatusProcessing();
    
    List<IEdoIntegrationInboundDocumentProcessingConfiguration> getExchangeErrorProcessing();
    
    List<IEdoIntegrationInboundDocumentProcessingConfiguration> getSendPrepareErrorProcessing();
    
    List<IEdoIntegrationInboundDocumentProcessingConfiguration> getDefaultErrorProcessing();
    
    IDictionaryElementConfiguration getIntegrationLogEventIdStorage();
    
    void setIntegrationLogEventIdStorage(final IDictionaryElementConfiguration p0);
    
    String getIntegrationLogEventId();
}
