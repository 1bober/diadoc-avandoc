// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.flows;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;

public interface IEdoIntegrationIncomingDocumentsFlowConfiguration extends IEdoIntegrationFlowConfiguration
{
    List<IDictionaryElementConfiguration> getExternalDocumentTypes();
    
    void clearExternalDocumentTypes();
    
    void addExternalDocumentType(final IDictionaryElementConfiguration p0);
    
    void addExternalDocumentTypeFromWrapper(final Object p0);
}
