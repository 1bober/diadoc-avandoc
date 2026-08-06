// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.incoming;

import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;

public interface IDiadocEdoIncomingDocumentReceiveConfiguration extends IEdoIntegrationInboundDocumentProcessingConfiguration
{
    IDictionaryElementConfiguration getCreateErrorEvent();
    
    void setCreateErrorEvent(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getPostCreatingEvent();
    
    void setPostCreatingEvent(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getSendDocumentEvent();
    
    void setSendDocumentEvent(final IDictionaryElementConfiguration p0);
    
    List<IDictionaryElementConfiguration> getExternalTypesStorage();
    
    List<String> getExternalTypes();
    
    void addExternalType(final IDictionaryElementConfiguration p0);
    
    void clearExternalTypes();
    
    void setFilterConfig(final String p0);
    
    String getFilterConfig();
}
