// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IDiadocEdoInboundRevokeProcessingOperationConfiguration extends ICstmConfigAdapter
{
    String getContentGroup();
    
    IDictionaryElementConfiguration getContentGroupStorage();
    
    void setContentGroupStorage(final IDictionaryElementConfiguration p0);
}
