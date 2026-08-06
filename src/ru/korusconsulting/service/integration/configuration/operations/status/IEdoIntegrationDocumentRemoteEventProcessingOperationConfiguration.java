// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.status;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.remoteevent.IEdoIntegrationDocumentRemoteEvent;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentEventProcessingOperationConfiguration;

public interface IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration extends IEdoIntegrationDocumentEventProcessingOperationConfiguration
{
    List<IEdoIntegrationDocumentRemoteEvent> getRemoteEvents();
    
    void addRemoteEvent(final IEdoIntegrationDocumentRemoteEvent p0);
    
    void clearRemoteEvents();
    
    IDictionaryElementConfiguration getProcessingEventStorage();
    
    void setProcessingEventStorage(final IDictionaryElementConfiguration p0);
    
    void clearAnswerMapping();
    
    void addAnswerMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAnswerMappingFromWrapper(final Object p0);
    
    void addRemoteEventFromWrapper(final Object p0);
    
    boolean isApplicableForEvent(final String p0);
}
