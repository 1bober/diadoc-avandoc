// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentProcedureArgExecutionArgument extends IEdoIntegrationAttributeMapping, IDiadocEdoInboundDocumentProcedureArgParameter
{
    String getExecutionArgument();
    
    void setExecutionArgument(final String p0);
}
