// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IDiadocEdoSurveyState extends IObjectModel
{
    String getFilialId();
    
    void setFilialId(final String p0);
    
    String getFilialName();
    
    void setFilialName(final String p0);
    
    String getDiadocLogin();
    
    void setDiadocLogin(final String p0);
    
    String getDiadocBox();
    
    void setDiadocBox(final String p0);
    
    String getDepartment();
    
    void setDepartment(final String p0);
    
    String getAccountMemberId();
    
    void setAccountMemberId(final String p0);
    
    String getAccountMemberName();
    
    void setAccountMemberName(final String p0);
    
    Date getFromDate();
    
    void setFromDate(final Date p0);
    
    String getLastProcceed();
    
    void setLastProcceed(final String p0);
    
    String getDirection();
    
    void setDirection(final String p0);
    
    public enum Direction
    {
        Inbound, 
        Outbound;
    }
}
