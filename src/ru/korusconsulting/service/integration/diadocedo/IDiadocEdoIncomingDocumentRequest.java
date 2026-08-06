// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import java.util.Date;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;

public interface IDiadocEdoIncomingDocumentRequest extends IEdoIntegrationInboundDataExchangeItem, IObjectModel
{
    void setErrorAttachmentCount(final int p0);
    
    int getErrorAttachmentCount();
    
    void setErrorCount(final int p0);
    
    int getErrorCount();
    
    void setKey(final String p0);
    
    String getKey();
    
    void setRecipientKpp(final String p0);
    
    String getRecipientKpp();
    
    void setRecipientInn(final String p0);
    
    String getRecipientInn();
    
    Date getCreationDate();
    
    void setDocumentId(final String p0);
    
    String getDocumentId();
    
    void setDocumentName(final String p0);
    
    String getDocumentName();
    
    void setDiadocAccount(final String p0);
    
    String getDiadocAccount();
    
    void setDiadocAccountName(final String p0);
    
    String getDiadocAccountName();
    
    void setDiadocLogin(final String p0);
    
    String getDiadocLogin();
    
    String getDiadocBox();
    
    void setDiadocBox(final String p0);
    
    String getDiadocDepartment();
    
    void setDiadocDepartment(final String p0);
    
    String getServerId();
    
    void setServerId(final String p0);
    
    String getServerName();
    
    void setServerName(final String p0);
    
    String getDiadocType();
    
    void setDiadocType(final String p0);
    
    String getMessageId();
    
    void setMessageId(final String p0);
    
    String getEntityId();
    
    void setEntityId(final String p0);
}
