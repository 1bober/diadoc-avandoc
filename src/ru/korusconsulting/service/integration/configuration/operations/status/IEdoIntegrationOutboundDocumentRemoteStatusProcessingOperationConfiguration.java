// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.status;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;

public interface IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration extends IEdoIntegrationInboundDocumentProcessingConfiguration
{
    List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses();
    
    void addRemoteStatus(final IEdoIntegrationDocumentRemoteStatus p0);
    
    void clearRemoteStatuses();
    
    IDictionaryElementConfiguration getProcessingEventStorage();
    
    void setProcessingEventStorage(final IDictionaryElementConfiguration p0);
    
    String getProcessingEvent();
    
    String getCoverage();
    
    void setStatusQuery(final String p0);
    
    String getStatusQuery();
    
    void addRemoteStatusFromWrapper(final Object p0);
    
    void setStateCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getStateCategoryStorage();
    
    String getStateCategory();
    
    boolean isApplicableForStatus(final String p0);
    
    IDictionaryElementConfiguration getContentPostProcessingEventStorage();
    
    void setContentPostProcessingEventStorage(final IDictionaryElementConfiguration p0);
    
    String getContentPostProcessingEvent();
    
    Boolean unmarshalPostActions(final Generator p0, final SessionContext p1);
    
    boolean isDontMarkAsProceed();
    
    int getMaxAttempts();
    
    boolean isBreakOperation();
}
