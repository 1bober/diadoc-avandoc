// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.Date;
import ru.wstack.net.client.contentserver.IObject;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.function.Supplier;
import java.util.function.Consumer;
import ru.korusconsulting.service.objectmodel.IObjectModelAttributeProcessor;

public class ObjectModelAttributeProcessor<T> implements IObjectModelAttributeProcessor
{
    private final String attributeName;
    private final int maxLen;
    private final Consumer<T> setter;
    private final Supplier<T> getter;
    private final Class<T> attrClass;
    private final boolean autoCalculated;
    
    public ObjectModelAttributeProcessor(final String attributeName, final Class<T> attrClass, final int maxLen, final Consumer<T> setter, final Supplier<T> getter) {
        this(attributeName, attrClass, maxLen, setter, getter, false);
    }
    
    public ObjectModelAttributeProcessor(final String attributeName, final Class<T> attrClass, final int maxLen, final Consumer<T> setter, final Supplier<T> getter, final boolean autoCalculated) {
        this.attributeName = attributeName;
        this.maxLen = maxLen;
        this.attrClass = attrClass;
        this.setter = setter;
        this.getter = getter;
        this.autoCalculated = autoCalculated;
    }
    
    @Override
    public String getAttributeName() {
        return this.attributeName;
    }
    
    public int getMaxLen() {
        return this.maxLen;
    }
    
    public Consumer<T> getSetter() {
        return this.setter;
    }
    
    public Supplier<T> getGetter() {
        return this.getter;
    }
    
    public Class<T> getAttrClass() {
        return this.attrClass;
    }
    
    @Override
    public boolean isAutoCalculated() {
        return this.autoCalculated;
    }
    
    @Override
    public void populateAttribute(final IObjectModel om, final IObject po) {
        if (this.getAttrClass().equals(String.class)) {
            om.setValue(this.getAttributeName(), (Object)po.getString(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Boolean.TYPE)) {
            om.setValue(this.getAttributeName(), (Object)po.getBoolean(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Boolean.class)) {
            om.setValue(this.getAttributeName(), (Object)po.getBoolean(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Integer.TYPE)) {
            om.setValue(this.getAttributeName(), (Object)po.getInt(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Integer.class)) {
            om.setValue(this.getAttributeName(), (Object)po.getInt(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Date.class)) {
            om.setValue(this.getAttributeName(), (Object)po.getDate(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Double.TYPE)) {
            om.setValue(this.getAttributeName(), (Object)po.getDouble(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Double.class)) {
            om.setValue(this.getAttributeName(), (Object)po.getDouble(this.getAttributeName()));
        }
        else if (this.getAttrClass().equals(Long.TYPE)) {
            om.setValue(this.getAttributeName(), (Object)po.getLong(this.getAttributeName()));
        }
        else {
            if (!this.getAttrClass().equals(Long.class)) {
                throw new ServiceException(String.format("Unsupported type %s for attribute %s", (this.getAttrClass() != null) ? this.getAttrClass().getName() : "null", this.getAttributeName()));
            }
            om.setValue(this.getAttributeName(), (Object)po.getLong(this.getAttributeName()));
        }
    }
    
    @Override
    public void commitAttribute(final IObjectModel om, final IObject po) {
        if (this.getAttrClass().equals(String.class)) {
            final String value = (String)this.getGetter().get();
            final String newValue = StringUtils.substring(value, 0, this.getMaxLen());
            if (!StringUtils.equals(po.getString(this.getAttributeName()), newValue)) {
                po.setString(this.getAttributeName(), newValue);
            }
        }
        else if (this.getAttrClass().equals(Boolean.TYPE)) {
            final boolean newValue2 = (boolean)this.getGetter().get();
            if (po.getBoolean(this.getAttributeName()) != newValue2) {
                po.setBoolean(this.getAttributeName(), newValue2);
            }
        }
        else if (this.getAttrClass().equals(Boolean.class)) {
            final boolean newValue2 = (boolean)this.getGetter().get();
            if (po.getBoolean(this.getAttributeName()) != newValue2) {
                po.setBoolean(this.getAttributeName(), newValue2);
            }
        }
        else if (this.getAttrClass().equals(Integer.TYPE)) {
            final int newValue3 = (int)this.getGetter().get();
            if (po.getInt(this.getAttributeName()) != newValue3) {
                po.setInt(this.getAttributeName(), newValue3);
            }
        }
        else if (this.getAttrClass().equals(Integer.class)) {
            final int newValue3 = (int)this.getGetter().get();
            if (po.getInt(this.getAttributeName()) != newValue3) {
                po.setInt(this.getAttributeName(), newValue3);
            }
        }
        else if (this.getAttrClass().equals(Date.class)) {
            final Date newValue4 = (Date)this.getGetter().get();
            if (!Objects.equals(po.getDate(this.getAttributeName()), newValue4)) {
                po.setDate(this.getAttributeName(), newValue4);
            }
        }
        else if (this.getAttrClass().equals(Double.TYPE)) {
            final double newValue5 = (double)this.getGetter().get();
            if (po.getDouble(this.getAttributeName()) != newValue5) {
                po.setDouble(this.getAttributeName(), newValue5);
            }
        }
        else if (this.getAttrClass().equals(Double.class)) {
            final double newValue5 = (double)this.getGetter().get();
            if (po.getDouble(this.getAttributeName()) != newValue5) {
                po.setDouble(this.getAttributeName(), newValue5);
            }
        }
        else if (this.getAttrClass().equals(Long.TYPE)) {
            final long newValue6 = (long)this.getGetter().get();
            if (po.getLong(this.getAttributeName()) != newValue6) {
                po.setLong(this.getAttributeName(), newValue6);
            }
        }
        else {
            if (!this.getAttrClass().equals(Long.class)) {
                throw new ServiceException(String.format("Unsupported type %s for attribute %s", (this.getAttrClass() != null) ? this.getAttrClass().getName() : "null", this.getAttributeName()));
            }
            final long newValue6 = (long)this.getGetter().get();
            if (po.getLong(this.getAttributeName()) != newValue6) {
                po.setLong(this.getAttributeName(), newValue6);
            }
        }
    }
}
