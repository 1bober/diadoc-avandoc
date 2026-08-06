// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IDiadocEdoAccount extends IObjectModel
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    String getServer();
    
    void setServer(final String p0);
    
    String getServerName();
    
    void setServerName(final String p0);
    
    String getLogin();
    
    void setLogin(final String p0);
    
    String getPassword();
    
    void setPassword(final String p0);
    
    String getCurrentSessionKey();
    
    void setCurrentSessionKey(final String p0);
    
    boolean isDontCheckCounteragentSignCertificate();
    
    void setDontCheckCounteragentSignCertificate(final boolean p0);
    
    void setIncDocsCheckOffset(final int p0);
    
    int getIncDocsCheckOffset();
}
