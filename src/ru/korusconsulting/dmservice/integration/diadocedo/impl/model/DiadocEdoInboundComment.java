// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.ws.common.model.IInboundComment;

public class DiadocEdoInboundComment extends DiadocEdoInboundObject implements IInboundComment
{
    private DiadocMessage_GetApiProtos.Entity entity;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocMessage_GetApiProtos.Entity getEntity() {
        return this.entity;
    }
    
    public DiadocEdoInboundComment(final String boxId, final DiadocMessage_GetApiProtos.Entity entity) {
        super(boxId);
        this.entity = entity;
        if (entity.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : entity.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                this.getLogger().trace(() -> String.format("Incoming document property [%s] = [%s]", name, val));
            }
        }
    }
    
    public String getType() {
        return this.getEntity().getAttachmentType().name();
    }
    
    public String getId() {
        return this.getEntity().getEntityId();
    }
}
