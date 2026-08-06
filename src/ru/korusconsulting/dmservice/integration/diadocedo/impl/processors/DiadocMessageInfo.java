// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import java.util.ArrayList;
import java.util.List;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;

public class DiadocMessageInfo
{
    String messageId;
    private long timestampTicks;
    private DiadocMessage_GetApiProtos.Message message;
    private List<DiadocMessage_GetApiProtos.Entity> attachmentEntities;
    private List<DiadocMessage_GetApiProtos.Entity> signatureEntities;
    
    public DiadocMessageInfo() {
        this.attachmentEntities = new ArrayList<DiadocMessage_GetApiProtos.Entity>();
        this.signatureEntities = new ArrayList<DiadocMessage_GetApiProtos.Entity>();
    }
    
    public String getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
    
    public List<DiadocMessage_GetApiProtos.Entity> getAttachmentEntities() {
        return this.attachmentEntities;
    }
    
    public void addAttachmentEntity(final DiadocMessage_GetApiProtos.Entity entity) {
        this.getAttachmentEntities().add(entity);
    }
    
    public List<DiadocMessage_GetApiProtos.Entity> getSignatureEntities() {
        return this.signatureEntities;
    }
    
    public void addSignatureEntity(final DiadocMessage_GetApiProtos.Entity entity) {
        this.getSignatureEntities().add(entity);
    }
    
    public long getTimestampTicks() {
        return this.timestampTicks;
    }
    
    public void setTimestampTicks(final long timestampTicks) {
        this.timestampTicks = timestampTicks;
    }
    
    public DiadocMessage_GetApiProtos.Message getMessage() {
        return this.message;
    }
    
    public void setMessage(final DiadocMessage_GetApiProtos.Message message) {
        this.message = message;
    }
}
