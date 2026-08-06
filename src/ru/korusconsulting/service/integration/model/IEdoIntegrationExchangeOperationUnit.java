// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model;

import java.util.List;

public interface IEdoIntegrationExchangeOperationUnit extends IEdoIntegrationExchangeUnit
{
    List<IEdoIntegrationExchangeUnit> getChildren();
    
    void addChild(final IEdoIntegrationExchangeUnit p0);
    
    String getOperationGuid();
    
    void setOperationGuid(final String p0);
}
