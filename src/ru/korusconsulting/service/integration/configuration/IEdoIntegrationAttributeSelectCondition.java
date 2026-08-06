// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationAttributeSelectCondition extends IEdoIntegrationDocumentSelectCondition
{
    IDictionaryElementConfiguration getAttributeStorage();
    
    void setAttributeStorage(final IDictionaryElementConfiguration p0);
    
    String getValue();
    
    void setValue(final String p0);
    
    IDictionaryElementConfiguration getConcatinationTypeStorage();
    
    void setConcatinationTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getConditionOperatorStorage();
    
    void setConditionOperatorStorage(final IDictionaryElementConfiguration p0);
    
    String getConcatinationType();
    
    String getConditionOperator();
    
    String getAttribute();
    
    IDictionaryElementConfiguration getAttributeManualDatatypeStorage();
    
    String getAttributeManualDatatype();
    
    void setAttributeManualDatatypeStorage(final IDictionaryElementConfiguration p0);
}
