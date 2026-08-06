// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentProcedureArgEnvProperty extends IEdoIntegrationAttributeMapping, IDiadocEdoInboundDocumentProcedureArgParameter
{
    IDictionaryElementConfiguration getEnvPropertyStorage();
    
    void setEnvPropertyStorage(final IDictionaryElementConfiguration p0);
    
    String getEnvProperty();
}
