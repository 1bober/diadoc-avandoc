// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentExecutionArgumentValue extends IEdoIntegrationAttributeMapping
{
    void setExecutionArgument(final String p0);
    
    String getExecutionArgument();
    
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getAttribute();
    
    IDictionaryElementConfiguration getDisplayAttributeStorage();
    
    void setDisplayAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getDisplayAttribute();
    
    String getLinkedDisplayAttribute();
    
    void setLinkedDisplayAttribute(final String p0);
}
