// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationOutboundRevokeOperationConfiguration extends ICstmConfigAdapter, IEdoIntegrationOperationConfiguration
{
    IDictionaryElementConfiguration getTitleObjectTypeStorage();
    
    void setTitleObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses();
    
    void addRemoteStatus(final IEdoIntegrationDocumentRemoteStatus p0);
    
    void clearRemoteStatuses();
    
    void addRemoteStatusFromWrapper(final Object p0);
    
    List<IDictionaryElementConfiguration> getContentGroups();
    
    void clearContentGroups();
    
    void addContentGroup(final IDictionaryElementConfiguration p0);
    
    boolean isApplicableForStatus(final String p0);
    
    void setStateCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getStateCategoryStorage();
    
    String getStateCategory();
    
    void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent p0);
    
    List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents();
    
    void clearProcessorEvents();
    
    void addProcessorEventFromWrapper(final Object p0);
}
