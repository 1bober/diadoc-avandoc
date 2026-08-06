// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IEdoIntegrationLogRecord extends IObjectModel
{
    String getIntegration();
    
    String getDirection();
    
    String getMessage();
    
    String getDocumentType();
    
    String getDocumentId();
    
    String getContainerId();
    
    String getCounteragentId();
    
    String getCounteragentName();
    
    String getContentId();
    
    String getSenderId();
    
    String getSenderName();
    
    String getFilialId();
    
    String getFilialName();
    
    String getServer();
    
    String getServerName();
    
    String getFromInn();
    
    String getFromKpp();
    
    String getFromEdoBox();
    
    String getFromEdoFilial();
    
    String getFromEdoDepartment();
    
    String getToName();
    
    String getToInn();
    
    String getToKpp();
    
    String getToEdoBox();
    
    String getToEdoDepartment();
    
    String getToProxyName();
    
    String getToProxyInn();
    
    String getToProxyKpp();
    
    String getToProxyEdoBox();
    
    String getToProxyEdoDepartment();
    
    String getEdoMessageId();
    
    String getEdoDocumentId();
    
    double getDeliveryTimestamp();
    
    Date getCreationDate();
    
    String getLocalState();
    
    String getLocalStatus();
    
    String getRemoteState();
    
    String getRemoteStatus();
    
    int getCurrentAttempt();
    
    void getIntegration(final String p0);
    
    void setDirection(final String p0);
    
    void setMessage(final String p0);
    
    void setDocumentType(final String p0);
    
    void setDocumentId(final String p0);
    
    void setContainerId(final String p0);
    
    void setCounteragentId(final String p0);
    
    void setCounteragentName(final String p0);
    
    void setContentId(final String p0);
    
    void setSenderId(final String p0);
    
    void setSenderName(final String p0);
    
    void setFilialId(final String p0);
    
    void setFilialName(final String p0);
    
    void setServer(final String p0);
    
    void setServerName(final String p0);
    
    void setFromInn(final String p0);
    
    void setFromKpp(final String p0);
    
    void setFromEdoBox(final String p0);
    
    void setFromEdoFilial(final String p0);
    
    void setFromEdoDepartment(final String p0);
    
    void setToName(final String p0);
    
    void setToInn(final String p0);
    
    void setToKpp(final String p0);
    
    void setToEdoBox(final String p0);
    
    void setToEdoDepartment(final String p0);
    
    void setToProxyName(final String p0);
    
    void setToProxyInn(final String p0);
    
    void setToProxyKpp(final String p0);
    
    void setToProxyEdoBox(final String p0);
    
    void setToProxyEdoDepartment(final String p0);
    
    void setEdoMessageId(final String p0);
    
    void setEdoDocumentId(final String p0);
    
    void setDeliveryTimestamp(final double p0);
    
    void setCreationDate(final Date p0);
    
    void setLocalState(final String p0);
    
    void setLocalStatus(final String p0);
    
    void setRemoteState(final String p0);
    
    void setRemoteStatus(final String p0);
    
    void setCurrentAttempt(final int p0);
    
    void setResultCode(final String p0);
    
    String getResultCode();
    
    String getOperation();
    
    void setOperation(final String p0);
    
    String getAccount();
    
    void setAccount(final String p0);
    
    String getAccountName();
    
    void setAccountName(final String p0);
    
    String getDocumentName();
    
    void setDocumentName(final String p0);
    
    String getContentName();
    
    void setContentName(final String p0);
}
