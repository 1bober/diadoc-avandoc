// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.HashMap;
import java.util.Map;

class DiadocInboundObjectComplexValue extends DiadocEdoInboundObjectValue
{
    Map<String, DiadocEdoInboundObjectValue> values;
    
    @Override
    public VALUE_TYPE getType() {
        return VALUE_TYPE.COMPLEX;
    }
    
    DiadocInboundObjectComplexValue(final Object value) {
        super(value);
        this.values = new HashMap<String, DiadocEdoInboundObjectValue>();
    }
    
    DiadocInboundObjectComplexValue(final Object value, final IAllFieldsGetter allFieldsGetter) {
        this(value);
        final Map<Descriptors.FieldDescriptor, Object> in = allFieldsGetter.getAllFields();
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : in.entrySet()) {
            final String name = fd.getKey().getName();
            this.values.put(name, DiadocEdoInboundObjectValue.parse(fd.getValue()));
        }
    }
    
    @Override
    public Map<String, DiadocEdoInboundObjectValue> getValue() {
        return this.values;
    }
    
    public DiadocEdoInboundObjectValue getValue(final String attr) {
        return this.values.get(attr);
    }
    
    @Override
    public String toString() {
        return this.values.toString();
    }
    
    public void put(final String key, final DiadocEdoInboundObjectValue value) {
        this.values.put(key, value);
    }
}
