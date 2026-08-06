// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationOutboundAttributeMappingByLinkedObject extends IEdoIntegrationDocumentWithMappingConfiguration
{
    IDictionaryElementConfiguration getContainerSelectTypeStorage();
    
    void setContainerSelectTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getContainerSelectAttributeStorage();
    
    void setContainerSelectAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getContainerSelectQuery();
    
    void setContainerSelectQuery(final String p0);
    
    IDictionaryElementConfiguration getObjectTypeStorage();
    
    void setObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getObjectIdAttributeStorage();
    
    void setObjectIdAttributeStorage(final IDictionaryElementConfiguration p0);
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
    
    void clearAnswerMapping();
    
    void addAnswerMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAnswerMappingFromWrapper(final Object p0);
    
    void clearSelectConditions();
    
    void addSelectCondition(final IEdoIntegrationDocumentSelectCondition p0);
    
    void addSelectConditionFromWrapper(final Object p0);
    
    List<IEdoIntegrationDocumentSelectCondition> getSelectConditions();
}
