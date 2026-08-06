// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationCounteragentObjectSelectRule extends IEdoIntegrationSelectObjectRule
{
    IDictionaryElementConfiguration getStateAttributeStorage();
    
    void setStateAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getInnAttributeStorage();
    
    void setInnAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getKppAttributeStorage();
    
    void setKppAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getNameAttributeStorage();
    
    void setNameAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getStateAttribute();
    
    String getNameAttribute();
    
    String getInnAttribute();
    
    String getKppAttribute();
    
    boolean validate();
    
    boolean useDictionaryOrganizationDetails();
    
    void setUseDictionaryOrganizationDetails(final boolean p0);
}
