// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentWithMappingConfiguration;

public interface IEdoIntegrationInboundRevokeOperationConfiguration extends IEdoIntegrationDocumentWithMappingConfiguration
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
}
