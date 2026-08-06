// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationOutboundAttributeMappingByObject extends IEdoIntegrationSelectObjectRule
{
    String getRemoteAttribute();
    
    void setRemoteAttribute(final String p0);
    
    List<IEdoIntegrationAttributeMapping> getAttributeMapping();
    
    void clearAttributeMapping();
    
    void addAttributeMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAttributeMappingFromWrapper(final Object p0);
}
