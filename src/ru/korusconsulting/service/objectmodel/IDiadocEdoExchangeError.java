// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IDiadocEdoExchangeError extends IObjectModel
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    String getErrorType();
    
    void setErrorType(final String p0);
    
    String getErrorTypeName();
    
    void setErrorTypeName(final String p0);
    
    String getErrorCode();
    
    void setErrorCode(final String p0);
    
    String getErrorMessage();
    
    void setErrorMessage(final String p0);
    
    boolean isCaseInsensitiveCheck();
    
    void setCaseInsensitiveCheck(final boolean p0);
    
    boolean isRegexp();
    
    void setIsRegexp(final boolean p0);
    
    String getCategory();
    
    void setCategory(final String p0);
    
    String getCategoryName();
    
    void setCategoryName(final String p0);
    
    String getCreateMode();
    
    void setCreateMode(final String p0);
    
    String getCounteragentStateCode();
    
    void setCounteragentStateCode(final String p0);
    
    String getCounteragentStateMessage();
    
    void setCounteragentStateMessage(final String p0);
    
    String getCounteragentStateCodeL2();
    
    void setCounteragentStateCodeL2(final String p0);
    
    String getCounteragentStateMessageL2();
    
    void setCounteragentStateMessageL2(final String p0);
    
    String getCategoryL2();
    
    void setCategoryL2(final String p0);
    
    String getCategoryNameL2();
    
    void setCategoryNameL2(final String p0);
    
    String getSystemErrorCode();
    
    void setSystemErrorCode(final String p0);
    
    String getOperationState();
    
    void setOperationState(final String p0);
    
    String getOperationStateL2();
    
    void setOperationStateL2(final String p0);
}
