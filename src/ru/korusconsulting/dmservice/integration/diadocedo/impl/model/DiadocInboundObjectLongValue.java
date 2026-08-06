// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

class DiadocInboundObjectLongValue extends DiadocEdoInboundObjectValue
{
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.LONG;
    }
    
    DiadocInboundObjectLongValue(final Object value) {
        super(value);
    }
    
    @Override
    public Long getValue() {
        return (Long)this.getOriginalValue();
    }
}
