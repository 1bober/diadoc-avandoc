// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

class DiadocInboundObjectUnknownValue extends DiadocEdoInboundObjectValue
{
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.UNKNOWN;
    }
    
    DiadocInboundObjectUnknownValue(final Object value) {
        super(value);
    }
    
    @Override
    public Object getValue() {
        return this.getOriginalValue();
    }
}
