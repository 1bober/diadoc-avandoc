// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;

public interface IEdoIntegrationStatusProcessorConfiguration extends ICstmConfigAdapter
{
    IDictionaryElementConfiguration getEventStorage();
    
    void setEventStorage(final IDictionaryElementConfiguration p0);
    
    boolean isDeleteRemoteDocument();
    
    void setDeleteRemoteDocument(final boolean p0);
    
    int getMaxAttempts();
    
    void setMaxAttempts(final int p0);
    
    int getAttemptsInterval();
    
    void setAttemptsInterval(final int p0);
    
    List<IEdoIntegrationDocumentRemoteStatus> getStatusesStorage();
    
    void clearStatusesStorage();
    
    void addStatusFromWrapper(final Object p0);
    
    void addStatus(final IEdoIntegrationDocumentRemoteStatus p0);
    
    List<IEdoIntegrationAttributeMapping> getLocalMapping();
    
    void clearLocalMapping();
    
    void addLocalMappingFromWrapper(final Object p0);
    
    void addLocalMapping(final IEdoIntegrationAttributeMapping p0);
    
    List<IEdoIntegrationAttributeMapping> getRemoteMapping();
    
    void clearRemoteMapping();
    
    void addRemoteMappingFromWrapper(final Object p0);
    
    void addRemoteMapping(final IEdoIntegrationAttributeMapping p0);
    
    void setDontMarkAsProceed(final boolean p0);
    
    boolean isDontMarkAsProceed();
    
    String getEvent();
    
    List<String> getStatuses();
    
    void setStateCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getStateCategoryStorage();
    
    String getStateCategory();
    
    void setPostActionsId(final String p0);
    
    String getPostActionsId();
    
    Boolean unmarshalPostActions(final Generator p0, final SessionContext p1);
}
