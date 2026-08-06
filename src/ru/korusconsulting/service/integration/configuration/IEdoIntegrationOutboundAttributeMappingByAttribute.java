// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationOutboundAttributeMappingByAttribute extends ICstmConfigAdapter
{
    boolean isRequired();
    
    void setRequired(final boolean p0);
    
    void setSelectType(final String p0);
    
    String getSelectTypeStr();
    
    void setAttributeFormat(final String p0);
    
    String getAttributeFormat();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setQuery(final String p0);
    
    String getQuery();
    
    void setProcedure(final String p0);
    
    String getProcedure();
    
    void setValue(final String p0);
    
    String getValue();
    
    boolean isParentDocAttribute();
    
    void setParentDocAttribute(final boolean p0);
    
    String getAttribute();
    
    String getAttributeType();
    
    IDictionaryElementConfiguration getObjectTypeStorage();
    
    void setObjectTypeStorage(final IDictionaryElementConfiguration p0);
}
