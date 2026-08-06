// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

class DiadocInboundObjectBooleanValue extends DiadocEdoInboundObjectValue
{
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.BOOLEAN;
    }
    
    DiadocInboundObjectBooleanValue(final Object value) {
        super(value);
    }
    
    @Override
    public Boolean getValue() {
        return (Boolean)this.getOriginalValue();
    }
}
