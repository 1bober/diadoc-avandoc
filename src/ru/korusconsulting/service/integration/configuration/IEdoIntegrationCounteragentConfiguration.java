// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationCounteragentConfiguration extends IEdoIntegrationOperationConfiguration
{
    String getCounteragentTypeAttribute();
    
    String getCounteragentNameAttribute();
    
    String getCounteragentInnAttribute();
    
    String getCounteragentKppAttribute();
    
    IDictionaryElementConfiguration getCounteragentTypeAttributeStorage();
    
    void setCounteragentTypeAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getCounteragentNameAttributeStorage();
    
    void setCounteragentNameAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getCounteragentInnAttributeStorage();
    
    void setCounteragentInnAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getCounteragentKppAttributeStorage();
    
    void setCounteragentKppAttributeStorage(final IDictionaryElementConfiguration p0);
}
