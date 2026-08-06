// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationRemoteAttributeMapping extends IEdoIntegrationAttributeMapping
{
    String getRemoteAttribute();
    
    void setRemoteAttribute(final String p0);
    
    IDictionaryElementConfiguration getRemoteAttributeDatatypeStorage();
    
    void setRemoteAttributeDatatypeStorage(final IDictionaryElementConfiguration p0);
}
