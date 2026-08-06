// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

class DiadocInboundObjectStringValue extends DiadocEdoInboundObjectValue
{
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.STRING;
    }
    
    DiadocInboundObjectStringValue(final Object value) {
        super(value);
    }
    
    @Override
    public String getValue() {
        return (String)this.getOriginalValue();
    }
}
