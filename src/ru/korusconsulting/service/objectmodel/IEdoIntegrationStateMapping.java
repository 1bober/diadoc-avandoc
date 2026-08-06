// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IEdoIntegrationStateMapping extends IObjectModel
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    String getSystemStateCode();
    
    void setSystemStateCode(final String p0);
    
    String getStateDisplayString();
    
    void setStateDisplayString(final String p0);
    
    String getExternalState();
    
    void setExternalState(final String p0);
    
    boolean isCaseInsensitiveCheck();
    
    void setCaseInsensitiveCheck(final boolean p0);
    
    boolean isRegexp();
    
    void setIsRegexp(final boolean p0);
    
    String getStateCode();
    
    void setStateCode(final String p0);
}
