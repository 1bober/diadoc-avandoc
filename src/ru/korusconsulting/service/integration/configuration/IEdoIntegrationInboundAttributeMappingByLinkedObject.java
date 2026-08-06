// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationInboundAttributeMappingByLinkedObject extends IEdoIntegrationOutboundMappingByLinkedObject
{
    IDictionaryElementConfiguration getContainerSelectTypeStorage();
    
    void setContainerSelectTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getContainerSelectAttributeStorage();
    
    void setContainerSelectAttributeStorage(final IDictionaryElementConfiguration p0);
    
    void setContainerSelectQuery(final String p0);
    
    IDictionaryElementConfiguration getObjectTypeStorage();
    
    void setObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    void setObjectIdAttributeStorage(final IDictionaryElementConfiguration p0);
    
    void clearSelectConditions();
    
    void addSelectCondition(final IEdoIntegrationDocumentSelectCondition p0);
    
    void addSelectConditionFromWrapper(final Object p0);
}
