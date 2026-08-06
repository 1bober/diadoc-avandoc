// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import org.apache.commons.lang.StringUtils;

public class DocumentInfoForEdoIntegrationSent
{
    private final String objectId;
    private final String senderId;
    private final String senderName;
    private final String exchangeItemId;
    
    public DocumentInfoForEdoIntegrationSent(final String objectId, final String senderId, final String senderName, final String exchangeItemId) {
        this.objectId = objectId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.exchangeItemId = exchangeItemId;
    }
    
    public String getObjectId() {
        return this.objectId;
    }
    
    public String getSenderId() {
        return this.senderId;
    }
    
    public String getSenderName() {
        return this.senderName;
    }
    
    public String getExchangeItemId() {
        return this.exchangeItemId;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj != null && obj.getClass().equals(this.getClass()) && StringUtils.equals(((DocumentInfoForEdoIntegrationSent)obj).getObjectId(), this.getObjectId()));
    }
    
    @Override
    public int hashCode() {
        return (this.getObjectId() == null) ? 0 : this.getObjectId().hashCode();
    }
}
