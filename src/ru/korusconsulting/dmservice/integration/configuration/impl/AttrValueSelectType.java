// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

public enum AttrValueSelectType
{
    attribute("attribute"), 
    query("query"), 
    unknown("");
    
    private String value;
    
    private AttrValueSelectType(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public static AttrValueSelectType parse(final String value) {
        for (final AttrValueSelectType v : values()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return AttrValueSelectType.unknown;
    }
}
