// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.ws.common.model.IInboundObject;

public class DiadocEdoInboundObject implements IInboundObject
{
    private Logger log;
    private Map<String, DiadocEdoInboundObjectValue> fields;
    protected final String boxId;
    
    protected Logger createLog() {
        return LogManager.getLogger();
    }
    
    protected Logger getLogger() {
        return this.log;
    }
    
    public DiadocEdoInboundObject(final String boxId) {
        this.log = this.createLog();
        this.fields = new HashMap<String, DiadocEdoInboundObjectValue>();
        this.boxId = boxId;
    }
    
    protected void addValue(final String key, final DiadocEdoInboundObjectValue value) {
        this.fields.put(key, value);
    }
    
    public Object getValue(final String attribute) {
        if (this.fields.containsKey(attribute)) {
            this.getLogger().trace(() -> String.format("Incoming document property [%s] = [%s]", attribute, this.fields.get(attribute).toString()));
            return this.fields.get(attribute).getValue();
        }
        final String[] splittedAttr = StringUtils.split(attribute, ".");
        if (splittedAttr.length == 1) {
            if (this.getLogger().isTraceEnabled()) {
                this.getLogger().trace("Incoming document property [{}] not found", (Object)attribute);
            }
            return null;
        }
        int i = splittedAttr.length - 1;
        while (i >= 0) {
            final String checkedAttr = Arrays.stream(splittedAttr, 0, i).collect(Collectors.joining("."));
            if (this.fields.containsKey(checkedAttr)) {
                this.getLogger().trace(() -> String.format("Incoming document property [%s] = [%s]", checkedAttr, this.fields.get(checkedAttr).toString()));
                final DiadocEdoInboundObjectValue fieldValue = this.fields.get(checkedAttr);
                if (fieldValue.getType() == DiadocEdoInboundObjectValue.VALUE_TYPE.COMPLEX) {
                    return ((DiadocInboundObjectComplexValue)fieldValue).getValue(Arrays.stream(splittedAttr, i, splittedAttr.length - i + 1).collect(Collectors.joining(".")));
                }
                return fieldValue.getValue();
            }
            else {
                this.getLogger().trace(() -> String.format("Incoming document property [%s] not found", checkedAttr));
                --i;
            }
        }
        return null;
    }
    
    public String getBoxId() {
        return this.boxId;
    }
}
