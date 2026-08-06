// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;

public interface IDiadocEdoInboundDocumentObjectMapping extends IDiadocEdoInboundValueMapping
{
    IDictionaryElementConfiguration getContainerSelectTypeStorage();
    
    void setContainerSelectTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getContainerAttributeStorage();
    
    void setContainerAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getCreateLinkQuery();
    
    void setCreateLinkQuery(final String p0);
    
    boolean isCreateIfNotExists();
    
    void setCreateIfNotExists(final boolean p0);
    
    String getCreateObjectQuery();
    
    void setCreateObjectQuery(final String p0);
    
    IDictionaryElementConfiguration getObjectType();
    
    void setObjectType(final IDictionaryElementConfiguration p0);
    
    List<IEdoIntegrationAttributeMapping> getMapping();
    
    void clearMapping();
    
    void addMapping(final IEdoIntegrationAttributeMapping p0);
    
    List<IEdoIntegrationAttributeMapping> getMappingIfNew();
    
    void clearMappingIfNew();
    
    void addMappingIfNew(final IEdoIntegrationAttributeMapping p0);
    
    void addMappingIfNewFromWrapper(final Object p0);
    
    void addMappingFromWrapper(final Object p0);
    
    String getContainerAttribute();
    
    String getLinkType();
    
    String getSelectObjectQuery();
    
    void setSelectObjectQuery(final String p0);
}
