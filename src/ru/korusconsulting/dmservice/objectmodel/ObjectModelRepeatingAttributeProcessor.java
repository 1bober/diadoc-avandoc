// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import ru.wstack.net.client.contentserver.IObject;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.function.Supplier;
import java.util.List;
import java.util.function.Consumer;

public class ObjectModelRepeatingAttributeProcessor<T> extends ObjectModelAttributeProcessor<T>
{
    private final Consumer<List<T>> listSetter;
    private final Supplier<List<T>> listGetter;
    
    public ObjectModelRepeatingAttributeProcessor(final String attributeName, final Class<T> attrClass, final int maxLen, final Consumer<List<T>> setter, final Supplier<List<T>> getter) {
        super(attributeName, attrClass, maxLen, null, null);
        this.listSetter = setter;
        this.listGetter = getter;
    }
    
    public Consumer<List<T>> getListSetter() {
        return this.listSetter;
    }
    
    public Supplier<List<T>> getListGetter() {
        return this.listGetter;
    }
    
    @Override
    public void populateAttribute(final IObjectModel om, final IObject po) {
        final int cnt = po.getValueCount(this.getAttributeName());
        if (this.getAttrClass().equals(String.class)) {
            final List<String> values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString(this.getAttributeName(), i));
            }
            om.setValue(this.getAttributeName(), (Object)values);
        }
        else if (this.getAttrClass().equals(Boolean.TYPE)) {
            final List<Boolean> values2 = new ArrayList<Boolean>();
            for (int i = 0; i < cnt; ++i) {
                values2.add(po.getRepeatingBoolean(this.getAttributeName(), i));
            }
            om.setValue(this.getAttributeName(), (Object)values2);
        }
        else if (this.getAttrClass().equals(Integer.TYPE)) {
            final List<Integer> values3 = new ArrayList<Integer>();
            for (int i = 0; i < cnt; ++i) {
                values3.add(po.getRepeatingInt(this.getAttributeName(), i));
            }
            om.setValue(this.getAttributeName(), (Object)values3);
        }
        else if (this.getAttrClass().equals(Date.class)) {
            final List<Date> values4 = new ArrayList<Date>();
            for (int i = 0; i < cnt; ++i) {
                values4.add(po.getRepeatingDate(this.getAttributeName(), i));
            }
            om.setValue(this.getAttributeName(), (Object)values4);
        }
        else if (this.getAttrClass().equals(Double.TYPE)) {
            final List<Double> values5 = new ArrayList<Double>();
            for (int i = 0; i < cnt; ++i) {
                values5.add(po.getRepeatingDouble(this.getAttributeName(), i));
            }
            om.setValue(this.getAttributeName(), (Object)values5);
        }
    }
    
    @Override
    public void commitAttribute(final IObjectModel om, final IObject po) {
        if (this.getAttrClass().equals(String.class)) {
            po.removeAll(this.getAttributeName());
            for (final String addressees : this.getListGetter().get()) {
                po.appendString(this.getAttributeName(), addressees);
            }
        }
        else if (this.getAttrClass().equals(Boolean.TYPE)) {
            po.removeAll(this.getAttributeName());
            for (final Boolean addressees2 : this.getListGetter().get()) {
                po.appendBoolean(this.getAttributeName(), (boolean)addressees2);
            }
        }
        else if (this.getAttrClass().equals(Integer.TYPE)) {
            po.removeAll(this.getAttributeName());
            for (final Integer addressees3 : this.getListGetter().get()) {
                po.appendInt(this.getAttributeName(), (int)addressees3);
            }
        }
        else if (this.getAttrClass().equals(Date.class)) {
            po.removeAll(this.getAttributeName());
            for (final Date addressees4 : this.getListGetter().get()) {
                po.appendDate(this.getAttributeName(), addressees4);
            }
        }
        else if (this.getAttrClass().equals(Double.TYPE)) {
            po.removeAll(this.getAttributeName());
            for (final Double addressees5 : this.getListGetter().get()) {
                po.appendDouble(this.getAttributeName(), (double)addressees5);
            }
        }
    }
}
