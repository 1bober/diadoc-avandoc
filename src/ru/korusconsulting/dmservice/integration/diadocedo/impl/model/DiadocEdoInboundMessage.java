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
import java.io.File;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.ws.common.model.IInboundMessage;

public class DiadocEdoInboundMessage extends DiadocEdoInboundObject implements IInboundMessage
{
    private DiadocMessage_GetApiProtos.Message message;
    private File logData;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocEdoInboundMessage(final String boxId, final DiadocMessage_GetApiProtos.Message message) {
        super(boxId);
        Objects.requireNonNull(message, "Incoming message cant be null");
        this.message = message;
        if (message.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : message.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                if (this.getLogger().isTraceEnabled()) {
                    this.getLogger().trace("Incoming document property [{}] = [{}]", (Object)name, (Object)val.toString());
                }
            }
        }
    }
    
    public String getId() {
        return this.message.getMessageId();
    }
    
    protected DiadocMessage_GetApiProtos.Message getMessage() {
        return this.message;
    }
}
