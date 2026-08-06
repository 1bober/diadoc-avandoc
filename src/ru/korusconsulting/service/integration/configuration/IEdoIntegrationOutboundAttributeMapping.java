// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationOutboundAttributeMapping extends IEdoIntegrationAttributeMapping
{
    String getRemoteAttribute();
    
    String getAttribute();
    
    String getAttributeType();
}
