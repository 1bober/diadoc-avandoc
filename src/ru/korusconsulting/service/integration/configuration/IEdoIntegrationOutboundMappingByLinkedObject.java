// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationOutboundMappingByLinkedObject extends IEdoIntegrationSelectObjectRule
{
    String getObjectType();
    
    List<IEdoIntegrationAttributeMapping> getMapping();
}
