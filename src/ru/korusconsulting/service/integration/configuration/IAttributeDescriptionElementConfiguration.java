// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IAttributeDescriptionElementConfiguration extends IDictionaryElementConfiguration
{
    String getAttributeDataType();
    
    boolean isRepeating();
    
    void setRepeating(final boolean p0);
    
    boolean isCstmLoaded();
    
    void setCstmLoaded(final boolean p0);
    
    void setObjectTypeId(final String p0);
    
    String getObjectTypeId();
    
    void setAttributeDataType(final String p0);
    
    void setObjectTypeCode(final String p0);
    
    String getObjectTypeCode();
}
