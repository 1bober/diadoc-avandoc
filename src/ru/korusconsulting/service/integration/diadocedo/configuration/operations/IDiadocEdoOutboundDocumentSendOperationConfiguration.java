// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundDocumentSendOperationConfiguration;

public interface IDiadocEdoOutboundDocumentSendOperationConfiguration extends IEdoIntegrationOutboundDocumentSendOperationConfiguration, IDiadocEdoOutboundDocumentOperationConfiguration
{
    IDictionaryElementConfiguration getDiadocEdoBoxMode();
    
    void setDiadocEdoBoxMode(final IDictionaryElementConfiguration p0);
    
    String getDiadocEdoBoxSelectMode();
    
    boolean getUseDiadocEdoShelf();
    
    void setUseDiadocEdoShelf(final boolean p0);
    
    IDictionaryElementConfiguration getLockModeStorage();
    
    void setLockModeStorage(final IDictionaryElementConfiguration p0);
}
