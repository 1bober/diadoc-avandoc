// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IEdoIntegrationOperationLog extends IObjectModel
{
    String getIntegration();
    
    void setIntegration(final String p0);
    
    String getDirection();
    
    void setDirection(final String p0);
    
    boolean isMain();
    
    void setMain(final boolean p0);
    
    String getDocumentId();
    
    void setDocumentId(final String p0);
    
    String getDocumentName();
    
    void setDocumentName(final String p0);
    
    String getContentId();
    
    void setContentId(final String p0);
    
    String getContentName();
    
    void setContentName(final String p0);
    
    String getFilial();
    
    void setFilial(final String p0);
    
    String getFilialName();
    
    void setFilialName(final String p0);
    
    String getCounteragentContainer();
    
    void setCounteragentContainer(final String p0);
    
    String getCounteragent();
    
    void setCounteragent(final String p0);
    
    String getCounteragentName();
    
    void setCounteragentName(final String p0);
    
    String getCounteragentInn();
    
    void setCounteragentInn(final String p0);
    
    String getCounteragentKpp();
    
    void setCounteragentKpp(final String p0);
    
    String getSenderId();
    
    void setSenderId(final String p0);
    
    String getSenderName();
    
    void setSenderName(final String p0);
    
    String getSenderInn();
    
    void setSenderInn(final String p0);
    
    String getSenderKpp();
    
    void setSenderKpp(final String p0);
    
    String getServerId();
    
    void setServerId(final String p0);
    
    String getServerName();
    
    void setServerName(final String p0);
    
    String getAccountId();
    
    void setAccountId(final String p0);
    
    String getAccountName();
    
    void setAccountName(final String p0);
    
    String getAccountMemberId();
    
    void setAccountMemberId(final String p0);
    
    String getAccountMemberName();
    
    void setAccountMemberName(final String p0);
    
    String getFromBox();
    
    void setFromBox(final String p0);
    
    String getFromBoxGuid();
    
    void setFromBoxGuid(final String p0);
    
    String getFromDepartment();
    
    void setFromDepartment(final String p0);
    
    String getFromRegulations();
    
    void setFromRegulations(final String p0);
    
    String getToName();
    
    void setToName(final String p0);
    
    String getToInn();
    
    void setToInn(final String p0);
    
    String getToKpp();
    
    void setToKpp(final String p0);
    
    String getToBox();
    
    void setToBox(final String p0);
    
    String getToBoxGuid();
    
    void setToBoxGuid(final String p0);
    
    String getToDepartment();
    
    void setToDepartment(final String p0);
    
    String getProxyName();
    
    void setProxyName(final String p0);
    
    String getProxyInn();
    
    void setProxyInn(final String p0);
    
    String getProxyKpp();
    
    void setProxyKpp(final String p0);
    
    String getProxyBox();
    
    void setProxyBox(final String p0);
    
    String getProxyBoxGuid();
    
    void setProxyBoxGuid(final String p0);
    
    String getProxyDepartment();
    
    void setProxyDepartment(final String p0);
    
    boolean isSended();
    
    void setSended(final boolean p0);
    
    Date getSendTime();
    
    void setSendTime(final Date p0);
    
    String getPrevLocalState();
    
    void setPrevLocalState(final String p0);
    
    Date getPrevLocalStateTime();
    
    void setPrevLocalStateTime(final Date p0);
    
    String getLocalState();
    
    void setLocalState(final String p0);
    
    Date getLocalStateTime();
    
    void setLocalStateTime(final Date p0);
    
    String getMessageGuid();
    
    void setMessageGuid(final String p0);
    
    String getDocumentGuid();
    
    void setDocumentGuid(final String p0);
    
    String getPrevRemoteState();
    
    void setPrevRemoteState(final String p0);
    
    Date getPrevRemoteStateTime();
    
    void setPrevRemoteStateTime(final Date p0);
    
    String getRemoteState();
    
    void setRemoteState(final String p0);
    
    Date getRemoteStateTime();
    
    void setRemoteStateTime(final Date p0);
    
    boolean isProceed();
    
    void setProceed(final boolean p0);
    
    boolean isFullProceed();
    
    void setFullProceed(final boolean p0);
    
    String getState();
    
    void setState(final String p0);
    
    boolean isNotActual();
    
    void setNotActual(final boolean p0);
    
    int getCurrentAttempt();
    
    void setCurrentAttempt(final int p0);
}
