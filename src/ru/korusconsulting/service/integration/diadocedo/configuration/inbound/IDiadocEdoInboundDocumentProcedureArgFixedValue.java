// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentProcedureArgFixedValue extends IEdoIntegrationAttributeMapping, IDiadocEdoInboundDocumentProcedureArgParameter
{
    String getValue();
    
    void setValue(final String p0);
}
