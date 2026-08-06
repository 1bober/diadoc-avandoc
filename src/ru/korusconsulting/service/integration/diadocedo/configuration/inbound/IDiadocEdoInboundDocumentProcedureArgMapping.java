// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

public interface IDiadocEdoInboundDocumentProcedureArgMapping extends IDiadocEdoInboundValueMapping, IDiadocEdoInboundDocumentProcedureArgParameter
{
    String getDiadocAttribute();
    
    void setDiadocAttribute(final String p0);
    
    String getDiadocAttributeExpression();
    
    void setDiadocAttributeExpression(final String p0);
}
