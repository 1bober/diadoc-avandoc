// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;

public interface IDiadocEdoInboundDocumentMappingProcedure extends IEdoIntegrationAttributeMapping
{
    String getProcedureName();
    
    String getProcedureClass();
    
    void setProcedureName(final String p0);
    
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
    
    public enum SourceType
    {
        document, 
        passport;
    }
    
    public enum DestinationType
    {
        attribute, 
        none;
    }
}
