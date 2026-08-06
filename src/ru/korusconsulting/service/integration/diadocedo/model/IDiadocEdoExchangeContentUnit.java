// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.model;

import java.util.Date;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;

public interface IDiadocEdoExchangeContentUnit extends IDiadocEdoExchangeCounteragentUnit
{
    DiadocMessage_PostApiProtos.SignedContent.Builder getSignedContentBuilder();
    
    void setNameOnShelf(final String p0);
    
    void setSignedContentBuilder(final DiadocMessage_PostApiProtos.SignedContent.Builder p0);
    
    String getNameOnShelf();
    
    String getContentUid();
    
    String getDiadocDocumentId();
    
    void setDiadocDocumentId(final String p0);
    
    String getContentId();
    
    void setContentId(final String p0);
    
    String getContentName();
    
    void setContentName(final String p0);
    
    long getDeliveryTimestamp();
    
    void setDeliveryTimestamp(final long p0);
    
    Date getDeliveryTime();
    
    void setDeliveryTime(final Date p0);
}
