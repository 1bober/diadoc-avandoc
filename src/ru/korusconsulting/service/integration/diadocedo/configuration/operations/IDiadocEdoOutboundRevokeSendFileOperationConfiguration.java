// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundRequestOperationConfiguration;

public interface IDiadocEdoOutboundRevokeSendFileOperationConfiguration extends IEdoIntegrationOutboundRequestOperationConfiguration
{
    String getContentGroup();
    
    IDictionaryElementConfiguration getContentGroupStorage();
    
    void setContentGroupStorage(final IDictionaryElementConfiguration p0);
}
