// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentAttributeMappingQuery extends IEdoIntegrationAttributeMapping
{
    String getQuery();
    
    void setQuery(final String p0);
    
    List<IEdoIntegrationAttributeMapping> getProcedureArgs();
    
    void clearProcedureArgs();
    
    void addProcedureArg(final IEdoIntegrationAttributeMapping p0);
    
    void addProcedureArgFromWrapper(final Object p0);
    
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getAttribute();
    
    IDictionaryElementConfiguration getDisplayAttributeStorage();
    
    void setDisplayAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getLinkedDisplayAttribute();
    
    void setLinkedDisplayAttribute(final String p0);
    
    String getDisplayAttribute();
}
