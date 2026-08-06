// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationConditionalOperationConfiguration extends IEdoIntegrationOperationConfiguration
{
    List<IEdoIntegrationDocumentSelectCondition> getConditions();
    
    void clearConditions();
    
    void addCondition(final IEdoIntegrationDocumentSelectCondition p0);
    
    void addConditionFromWrapper(final Object p0);
}
