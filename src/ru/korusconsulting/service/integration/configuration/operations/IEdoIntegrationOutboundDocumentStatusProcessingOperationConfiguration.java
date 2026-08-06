// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationOutboundDocumentStatusProcessingOperationConfiguration extends ICstmConfigAdapter
{
    String getProcessingEvent();
    
    String getStatusQuery();
    
    String getStateCategory();
}
