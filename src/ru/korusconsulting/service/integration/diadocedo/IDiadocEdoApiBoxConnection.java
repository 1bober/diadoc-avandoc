// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

public interface IDiadocEdoApiBoxConnection
{
    String getAccountMemberId();
    
    String getAccountMemberName();
    
    IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode getNotInvitedCounteragentSendMode();
    
    boolean isAllowSendToRejected();
    
    boolean isDontCheckCounteragentSignCertificate();
    
    boolean isRemoteSignatureMode();
    
    IDiadocEdoConnectionConfig getConfig();
    
    String getFilialName();
    
    String getFilialId();
    
    IDiadocEdoApi getApi();
    
    String getServerId();
    
    String getServerName();
    
    String getAccountId();
    
    String getAccountName();
}
