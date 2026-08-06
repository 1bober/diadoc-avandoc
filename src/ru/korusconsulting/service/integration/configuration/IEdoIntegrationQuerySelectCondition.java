// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationQuerySelectCondition extends IEdoIntegrationDocumentSelectCondition
{
    String getQuery();
    
    void setQuery(final String p0);
    
    String getValue();
    
    void setValue(final String p0);
    
    IDictionaryElementConfiguration getConcatinationTypeStorage();
    
    void setConcatinationTypeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getConditionOperatorStorage();
    
    void setConditionOperatorStorage(final IDictionaryElementConfiguration p0);
    
    String getConcatinationType();
    
    String getConditionOperator();
}
