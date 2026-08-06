// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;

public interface IEdoDocumentAttributeDesc
{
    String getName();
    
    String getLabel();
    
    EdoIntegrationDocumentAttributeDataType getDataType();
    
    String getFormat();
    
    boolean isRequired();
}
