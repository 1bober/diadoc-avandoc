// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IEdoIntegrationTokenStore extends IObjectModel
{
    String getIntegration();
    
    void setIntegration(final String p0);
    
    String getLogin();
    
    void setLogin(final String p0);
    
    String getBox();
    
    void setBox(final String p0);
    
    String getToken();
    
    void setToken(final String p0);
    
    Date getUpdateTime();
    
    void setUpdateTime(final Date p0);
    
    void setServerId(final String p0);
    
    String getServerId();
}
