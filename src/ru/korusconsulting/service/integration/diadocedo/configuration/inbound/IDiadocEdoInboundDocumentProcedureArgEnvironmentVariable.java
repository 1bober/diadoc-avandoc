// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable extends IEdoIntegrationAttributeMapping, IDiadocEdoInboundDocumentProcedureArgParameter
{
    String getEnvironmentName();
    
    void setEnvironmentName(final String p0);
    
    String getEnvironmentVariable();
    
    void setEnvironmentVariable(final String p0);
}
