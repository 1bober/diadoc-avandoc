// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.Iterator;
import com.google.protobuf.Descriptors;
import Diadoc.Api.Proto.OrganizationProtos;
import Diadoc.Api.Proto.DocumentIdProtos;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;
import java.util.Collection;
import com.google.protobuf.AbstractMessage;
import Diadoc.Api.Proto.ContentProtos;
import org.apache.logging.log4j.Logger;

public abstract class DiadocEdoInboundObjectValue
{
    private static final Logger logger;
    Object originalValue;
    
    protected DiadocEdoInboundObjectValue(final Object value) {
        this.originalValue = value;
    }
    
    public abstract VALUE_TYPE getType();
    
    public abstract Object getValue();
    
    public Object getOriginalValue() {
        return this.originalValue;
    }
    
    @Override
    public String toString() {
        return this.originalValue.toString();
    }
    
    public static DiadocEdoInboundObjectValue parse(final Object originalValue) {
        if (originalValue instanceof String) {
            return new DiadocInboundObjectStringValue(originalValue);
        }
        if (originalValue instanceof Long) {
            return new DiadocInboundObjectLongValue(originalValue);
        }
        if (originalValue instanceof Boolean) {
            return new DiadocInboundObjectBooleanValue(originalValue);
        }
        if (originalValue instanceof Integer) {
            return new DiadocInboundObjectIntegerValue(originalValue);
        }
        if (originalValue instanceof ContentProtos.Content) {
            return new DiadocInboundObjectContentValue(originalValue);
        }
        if (originalValue instanceof final AbstractMessage abstractMessage) {
            return new DiadocInboundObjectComplexValue(originalValue, () -> abstractMessage.getAllFields());
        }
        if (originalValue instanceof Collection) {
            final DiadocInboundObjectComplexValue newVal = new DiadocInboundObjectComplexValue(originalValue);
            for (final Object val : (Collection)originalValue) {
                if (val instanceof final DiadocMessage_PostApiProtos.MetadataItem metadataItemValue) {
                    final String name = metadataItemValue.getKey();
                    final String value = metadataItemValue.getValue();
                    newVal.put(name, parse(value));
                }
                else {
                    if (val instanceof DocumentIdProtos.DocumentId) {
                        continue;
                    }
                    if (val instanceof OrganizationProtos.Box) {
                        continue;
                    }
                    DiadocEdoInboundObjectValue.logger.error(val.getClass().getName());
                }
            }
            return newVal;
        }
        if (originalValue instanceof Descriptors.EnumValueDescriptor) {
            final DiadocInboundObjectComplexValue newVal = new DiadocInboundObjectComplexValue(originalValue);
            newVal.put("fullname", new DiadocInboundObjectStringValue(((Descriptors.EnumValueDescriptor)originalValue).getFullName()));
            newVal.put("name", new DiadocInboundObjectStringValue(((Descriptors.EnumValueDescriptor)originalValue).getName()));
            newVal.put("index", new DiadocInboundObjectIntegerValue(((Descriptors.EnumValueDescriptor)originalValue).getIndex()));
            newVal.put("number", new DiadocInboundObjectIntegerValue(((Descriptors.EnumValueDescriptor)originalValue).getIndex()));
            return newVal;
        }
        return new DiadocInboundObjectUnknownValue(originalValue);
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public enum VALUE_TYPE
    {
        STRING, 
        BOOLEAN, 
        INTEGER, 
        LONG, 
        COMPLEX, 
        CONTENT, 
        UNKNOWN;
    }
}
