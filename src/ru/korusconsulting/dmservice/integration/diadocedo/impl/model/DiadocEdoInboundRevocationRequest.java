// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.StringJoiner;
import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.ws.common.model.IInboundRevocationRequest;

public class DiadocEdoInboundRevocationRequest extends DiadocEdoInboundObject implements IInboundRevocationRequest
{
    private DiadocMessage_GetApiProtos.Entity entity;
    private String messageId;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocMessage_GetApiProtos.Entity getEntity() {
        return this.entity;
    }
    
    public DiadocEdoInboundRevocationRequest(final String boxId, final String messageId, final DiadocMessage_GetApiProtos.Entity entity) {
        super(boxId);
        this.entity = entity;
        this.messageId = messageId;
        if (entity.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : entity.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                if (this.getLogger().isTraceEnabled()) {
                    this.getLogger().trace("Incoming document property [{}] = [{}]", (Object)name, (Object)val.toString());
                }
            }
        }
    }
    
    public String getType() {
        return this.getEntity().getAttachmentType().name();
    }
    
    public String getId() {
        return this.getEntity().getEntityId();
    }
    
    public String getDocumentId() {
        return new StringJoiner(":").add(this.boxId).add(this.getParentEntityId()).toString();
    }
    
    private String getMessageId() {
        return this.messageId;
    }
    
    private String getParentEntityId() {
        return this.getEntity().getParentEntityId();
    }
}
