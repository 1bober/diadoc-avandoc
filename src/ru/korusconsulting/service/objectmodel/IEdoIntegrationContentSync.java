// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IEdoIntegrationContentSync extends IObjectModel
{
    public static final String ADDITIONAL_INFO_ATTR = "additional_info";
    
    String getContentId();
    
    void setContentId(final String p0);
    
    String getContentName();
    
    void setContentName(final String p0);
    
    String getDocumentId();
    
    void setDocumentId(final String p0);
    
    String getDocumentType();
    
    void setDocumentType(final String p0);
    
    String getIntegration();
    
    void setIntegration(final String p0);
    
    String getDirection();
    
    void setDirection(final String p0);
    
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
    
    String getEdoAccountGuid();
    
    void setEdoAccountGuid(final String p0);
    
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
    
    String getCounteragentEdoAccountGuid();
    
    void setCounteragentEdoAccountGuid(final String p0);
    
    String getExternalContainerGuid();
    
    void setExternalContainerGuid(final String p0);
    
    String getExternalGuid();
    
    void setExternalGuid(final String p0);
    
    String getExternalLink();
    
    void setExternalLink(final String p0);
    
    String getExternalPrintformLink();
    
    void setExternalPrintformLink(final String p0);
    
    boolean isService();
    
    void setIsService(final boolean p0);
    
    void setAdditionalInfo(final IEdoIntegrationAttachmentAdditionalInfo p0);
    
    IEdoIntegrationAttachmentAdditionalInfo getAdditionalInfo();
}
