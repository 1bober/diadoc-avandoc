// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Diadoc.Api.Proto.CounteragentProtos;

public class DiadocEdoCounteragent extends DiadocEdoInboundObject
{
    private CounteragentProtos.Counteragent counteragent;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocEdoCounteragent(final String boxId, final CounteragentProtos.Counteragent counteragent) {
        super(boxId);
        Objects.requireNonNull(counteragent, "counteragent cant be null");
        this.counteragent = counteragent;
        if (counteragent.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : counteragent.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                this.getLogger().trace(() -> String.format("Incoming document property [%s] = [%s]", name, val));
            }
        }
    }
    
    public CounteragentProtos.Counteragent getCounteragent() {
        return this.counteragent;
    }
}
