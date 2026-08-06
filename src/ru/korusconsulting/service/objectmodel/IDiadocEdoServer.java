// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IDiadocEdoServer extends IObjectModel
{
    String getName();
    
    void setName(final String p0);
    
    String getUrl();
    
    void setUrl(final String p0);
    
    String getApiKey();
    
    void setApiKey(final String p0);
    
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    String getWebUrl();
    
    void setWebUrl(final String p0);
}
