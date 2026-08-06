// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationCounteragentSelectAttributeRule extends ICstmConfigAdapter
{
    IDictionaryElementConfiguration getObjectIdAttributeStorage();
    
    void setObjectIdAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getSendStatusAttributeStorage();
    
    void setSendStatusAttributeStorage(final IDictionaryElementConfiguration p0);
}
