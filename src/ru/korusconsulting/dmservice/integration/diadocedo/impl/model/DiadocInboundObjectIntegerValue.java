// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

class DiadocInboundObjectIntegerValue extends DiadocEdoInboundObjectValue
{
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.INTEGER;
    }
    
    DiadocInboundObjectIntegerValue(final Object value) {
        super(value);
    }
    
    @Override
    public Integer getValue() {
        return (Integer)this.getOriginalValue();
    }
}
