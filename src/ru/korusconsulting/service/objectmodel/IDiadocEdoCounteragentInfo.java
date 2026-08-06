// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IDiadocEdoCounteragentInfo extends IObjectModel
{
    void setName(final String p0);
    
    String getName();
    
    void setInn(final String p0);
    
    String getInn();
    
    void setKpp(final String p0);
    
    String getKpp();
    
    void setBoxGuid(final String p0);
    
    String getBoxGuid();
    
    void setDepartment(final String p0);
    
    String getDepartment();
    
    void setDepartmentName(final String p0);
    
    String getDepartmentName();
    
    void setServerId(final String p0);
    
    String getServerId();
    
    void setServerName(final String p0);
    
    String getServerName();
    
    void setAccountId(final String p0);
    
    String getAccountId();
    
    void setAccountName(final String p0);
    
    String getAccountName();
    
    void setAccountMemberId(final String p0);
    
    String getAccountMemberId();
    
    void setAccountMemberName(final String p0);
    
    String getAccountMemberName();
    
    String getOrgId();
    
    void setOrgId(final String p0);
    
    String getFnsParticipantId();
    
    void setFnsParticipantId(final String p0);
    
    boolean isRoaming();
    
    void setRoaming(final boolean p0);
    
    void setEnabled(final boolean p0);
    
    boolean isEnabled();
    
    Date getNextValidateDate();
    
    void setNextValidateDate(final Date p0);
    
    Date getLastValidateDate();
    
    void setLastValidateDate(final Date p0);
    
    boolean hasCertificateToSign();
    
    void setHasCertificateToSign(final boolean p0);
    
    Date getCreateDate();
    
    Date getModifyDate();
    
    boolean IsForeign();
    
    void setIsForeign(final boolean p0);
}
