// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundRequestOperationConfiguration;

public interface IDiadocEdoOutboundRevokeCreateFileOperationConfiguration extends IEdoIntegrationOutboundRequestOperationConfiguration, IEdoIntegrationProcessingConfiguration
{
    IDictionaryElementConfiguration getContentGroupStorage();
    
    void setContentGroupStorage(final IDictionaryElementConfiguration p0);
    
    String getContentGroup();
    
    String getReasonVariableName();
    
    void setReasonVariableName(final String p0);
}
