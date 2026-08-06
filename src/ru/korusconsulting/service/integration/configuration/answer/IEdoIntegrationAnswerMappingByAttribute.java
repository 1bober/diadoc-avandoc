// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.answer;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IEdoIntegrationAnswerMappingByAttribute extends IEdoIntegrationAttributeMapping
{
    String getAttribute();
    
    String getAnswerFiled();
    
    void setAnswerFiled(final String p0);
    
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
}
