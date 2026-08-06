// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.answer;

import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IEdoIntegrationAnswerMappingByLinkedObject extends IEdoIntegrationAttributeMapping
{
    IDictionaryElementConfiguration getContainerLinkTypeStorage();
    
    void setContainerLinkTypeStorage(final IDictionaryElementConfiguration p0);
    
    String getContainerSelectQuery();
    
    void setContainerSelectQuery(final String p0);
    
    String getContainerCreateQuery();
    
    void setContainerCreateQuery(final String p0);
    
    IDictionaryElementConfiguration getObjectTypeStorage();
    
    void setObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    void clearAnswerMapping();
    
    void addAnswerMapping(final IEdoIntegrationAttributeMapping p0);
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
    
    void getAnswerMappingFromWrapper(final Object p0);
    
    String getContainerLinkType();
}
