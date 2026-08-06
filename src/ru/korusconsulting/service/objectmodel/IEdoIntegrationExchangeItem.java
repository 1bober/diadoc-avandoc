// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IEdoIntegrationExchangeItem extends IEdoIntegrationInboundDataExchangeItem, IObjectModel
{
    public static final String ADDITIONAL_INFO_ATTR = "additional_info";
    
    String getIntegration();
    
    void setIntegration(final String p0);
    
    String getDirection();
    
    void setDirection(final String p0);
    
    boolean isMain();
    
    void setMain(final boolean p0);
    
    String getDocumentName();
    
    void setDocumentName(final String p0);
    
    String getContentId();
    
    void setContentId(final String p0);
    
    String getContentUid();
    
    void setContentUid(final String p0);
    
    String getContentName();
    
    void setContentName(final String p0);
    
    String getFilialId();
    
    void setFilialId(final String p0);
    
    String getFilialName();
    
    void setFilialName(final String p0);
    
    String getCounteragentContainerId();
    
    void setCounteragentContainerId(final String p0);
    
    String getCounteragentId();
    
    void setCounteragentId(final String p0);
    
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
    
    String getFromInn();
    
    void setFromInn(final String p0);
    
    String getFromKpp();
    
    void setFromKpp(final String p0);
    
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
    
    boolean isContentSended();
    
    void setContentSended(final boolean p0);
    
    Date getSendTime();
    
    void setSendTime(final Date p0);
    
    Date getDeliveryTime();
    
    void setDeliveryTime(final Date p0);
    
    long getDeliveryTimestamp();
    
    void setDeliveryTimestamp(final long p0);
    
    String getPrevLocalState();
    
    void setPrevLocalState(final String p0);
    
    String getPrevLocalStateMessage();
    
    void setPrevLocalStateMessage(final String p0);
    
    Date getPrevLocalStateTime();
    
    void setPrevLocalStateTime(final Date p0);
    
    String getLocalState();
    
    void setLocalState(final String p0);
    
    String getLocalStateMessage();
    
    void setLocalStateMessage(final String p0);
    
    Date getLocalStateTime();
    
    void setLocalStateTime(final Date p0);
    
    String getPrevRemoteState();
    
    void setPrevRemoteState(final String p0);
    
    String getPrevRemoteStateMessage();
    
    void setPrevRemoteStateMessage(final String p0);
    
    Date getPrevRemoteStateTime();
    
    void setPrevRemoteStateTime(final Date p0);
    
    String getRemoteState();
    
    void setRemoteState(final String p0);
    
    String getRemoteStateMessage();
    
    void setRemoteStateMessage(final String p0);
    
    Date getRemoteStateTime();
    
    void setRemoteStateTime(final Date p0);
    
    boolean isProceed();
    
    void setProceed(final boolean p0);
    
    boolean isLocalProceed();
    
    void setLocalProceed(final boolean p0);
    
    String getState();
    
    void setState(final String p0);
    
    boolean isNotActual();
    
    void setNotActual(final boolean p0);
    
    int getCurrentAttempt();
    
    void setCurrentAttempt(final int p0);
    
    boolean isReady();
    
    void setReady(final boolean p0);
    
    void updateLocalState(final String p0, final String p1);
    
    void updateRemoteState(final String p0, final String p1);
    
    String getReiteratableError();
    
    void setReiteratableError(final String p0);
    
    Date getReiteratableErrorTime();
    
    void setReiteratableErrorTime(final Date p0);
    
    Date getReiteratableErrorOvertime();
    
    void setReiteratableErrorOvertime(final Date p0);
    
    String getReiteratableErrorMessage();
    
    void setReiteratableErrorMessage(final String p0);
    
    String getNameOnShelf();
    
    void setNameOnShelf(final String p0);
    
    void updateFrom(final IEdoIntegrationExchangeItem p0, final boolean p1);
    
    void setDocumentUrl(final String p0);
    
    String getDocumentUrl();
    
    boolean isQueued();
    
    void setQueued(final boolean p0);
    
    String getItemType();
    
    void setItemType(final String p0);
    
    String getItemOperationCode();
    
    void setItemOperationCode(final String p0);
    
    String getDocumentType();
    
    void setDocumentType(final String p0);
    
    String getDocumentTypeName();
    
    void setDocumentTypeName(final String p0);
    
    String getDocumentKindName();
    
    void setDocumentKindName(final String p0);
    
    String getDocumentKindId();
    
    void setDocumentKindId(final String p0);
    
    String getDocumentSubkindName();
    
    void setDocumentSubkindName(final String p0);
    
    String getDocumentSubkindId();
    
    void setDocumentSubkindId(final String p0);
    
    Date getLastCheckTime();
    
    void setLastCheckTime(final Date p0);
    
    String getParentId();
    
    void setParentId(final String p0);
    
    String getLastLocalProceedStatus();
    
    void setLastLocalProceedStatus(final String p0);
    
    String getLastRemoteProceedStatus();
    
    void setLastRemoteProceedStatus(final String p0);
    
    String getLocalStateSystemCode();
    
    void setLocalStateSystemCode(final String p0);
    
    String getRemoteStateSystemCode();
    
    void setRemoteStateSystemCode(final String p0);
    
    String getPrevLocalStateSystemCode();
    
    void setPrevLocalStateSystemCode(final String p0);
    
    String getPrevRemoteStateSystemCode();
    
    void setPrevRemoteStateSystemCode(final String p0);
    
    String getStateCategory();
    
    void setStateCategory(final String p0);
    
    void setRemoteStateDisplay(final String p0);
    
    String getRemoteStateDisplay();
    
    String getRemoteStateCode();
    
    void setRemoteStateCode(final String p0);
    
    String getPrevRemoteStateCode();
    
    void setPrevRemoteStateCode(final String p0);
    
    String getNextItem();
    
    void setNextItem(final String p0);
    
    String getFollowItem();
    
    void setFollowItem(final String p0);
    
    String getLocalStateDisplay();
    
    void setLocalStateDisplay(final String p0);
    
    void setLocalStateProcessingStep(final String p0);
    
    void setRemoteStateProcessingStep(final String p0);
    
    void setProcessingState(final String p0);
    
    void setProcessingError(final String p0);
    
    String getOperationGuid();
    
    void setOperationGuid(final String p0);
    
    Date getQueueTime();
    
    void setQueueTime(final Date p0);
    
    void setRelatedId(final String p0);
    
    String getRelatedId();
}
