// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;

public interface IDiadocEdoOutboundAttributeMappingByAttribute extends IEdoIntegrationOutboundAttributeMapping
{
    String getRemoteAttribute();
    
    void setRemoteAttribute(final String p0);
    
    IDictionaryElementConfiguration getRemoteAttributeDatatypeStorage();
    
    void setRemoteAttributeDatatypeStorage(final IDictionaryElementConfiguration p0);
}
