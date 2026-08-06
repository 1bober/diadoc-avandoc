// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationDocumentWithMappingConfiguration extends IEdoIntegrationAttributeMapping, IEdoIntegrationOperationConfiguration
{
    List<IEdoIntegrationAttributeMapping> getMapping();
    
    void clearMapping();
    
    void addMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addMappingFromWrapper(final Object p0);
}
