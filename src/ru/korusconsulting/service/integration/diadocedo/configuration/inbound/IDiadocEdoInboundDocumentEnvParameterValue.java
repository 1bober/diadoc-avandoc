// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentEnvParameterValue extends IEdoIntegrationAttributeMapping
{
    void setEnvProperty(final String p0);
    
    String getEnvProperty();
    
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getAttribute();
    
    IDictionaryElementConfiguration getDisplayAttributeStorage();
    
    void setDisplayAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getDisplayAttribute();
    
    String getLinkedDisplayAttribute();
    
    void setLinkedDisplayAttribute(final String p0);
}
