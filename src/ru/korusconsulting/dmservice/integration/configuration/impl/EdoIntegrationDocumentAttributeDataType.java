// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

public enum EdoIntegrationDocumentAttributeDataType
{
    STRING("string"), 
    DATE("date"), 
    MESSAGE("MESSAGE"), 
    BOOL("BOOL"), 
    STRING2("STRING");
    
    private String value;
    
    public String getValue() {
        return this.value;
    }
    
    private EdoIntegrationDocumentAttributeDataType(final String value) {
        this.value = value;
    }
    
    public static EdoIntegrationDocumentAttributeDataType parse(final String val) {
        for (final EdoIntegrationDocumentAttributeDataType v : values()) {
            if (v.getValue().equals(val)) {
                return v;
            }
        }
        return EdoIntegrationDocumentAttributeDataType.STRING;
    }
}
