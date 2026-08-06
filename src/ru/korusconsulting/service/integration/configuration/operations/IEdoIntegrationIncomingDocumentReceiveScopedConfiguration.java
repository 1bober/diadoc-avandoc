// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;

public interface IEdoIntegrationIncomingDocumentReceiveScopedConfiguration extends IEdoIntegrationScopedConfiguration
{
    List<IDictionaryElementConfiguration> getExternalTypesStorage();
    
    List<String> getExternalTypes();
    
    void addExternalType(final IDictionaryElementConfiguration p0);
    
    void clearExternalTypes();
}
