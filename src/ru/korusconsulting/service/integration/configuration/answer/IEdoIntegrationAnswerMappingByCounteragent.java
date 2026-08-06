// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.answer;

import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IEdoIntegrationAnswerMappingByCounteragent extends IEdoIntegrationAttributeMapping
{
    String getObjectType();
    
    IDictionaryElementConfiguration getObjectTypeStorage();
    
    void setObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    void clearAnswerMapping();
    
    void addAnswerMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAnswerMappingFromWrapper(final Object p0);
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
}
