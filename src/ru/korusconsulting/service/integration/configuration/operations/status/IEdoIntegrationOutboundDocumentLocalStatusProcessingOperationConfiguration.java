// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.status;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration extends ICstmConfigAdapter
{
    List<IEdoIntegrationDocumentLocalStatus> getLocalStatuses();
    
    void addLocalStatus(final IEdoIntegrationDocumentLocalStatus p0);
    
    void clearLocalStatuses();
    
    void addLocalStatusFromWrapper(final Object p0);
    
    IDictionaryElementConfiguration getProcessingEventStorage();
    
    void setProcessingEventStorage(final IDictionaryElementConfiguration p0);
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
    
    void clearAnswerMapping();
    
    void addAnswerMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAnswerMappingFromWrapper(final Object p0);
    
    boolean isTerminalState();
    
    void setTerminalState(final boolean p0);
    
    IDictionaryElementConfiguration getCoverageStorage();
    
    void setCoverageStorage(final IDictionaryElementConfiguration p0);
    
    boolean isDontMarkAsProceed();
    
    void setDontMarkAsProceed(final boolean p0);
    
    boolean isBreakOperation();
    
    void setBreakOperation(final boolean p0);
    
    int getMaxAttempts();
    
    void setMaxAttempts(final int p0);
    
    String getStatusQuery();
    
    void setStatusQuery(final String p0);
    
    String getStateCategory();
    
    void setStateCategory(final String p0);
    
    String getProcessingEvent();
    
    String getStatusCoverage();
}
