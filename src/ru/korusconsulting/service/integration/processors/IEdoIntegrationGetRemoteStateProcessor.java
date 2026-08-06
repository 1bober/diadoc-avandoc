// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.processors;

import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public interface IEdoIntegrationGetRemoteStateProcessor<T extends IIntegrationOperationContext> extends IEdoIntegrationProcessor<T>
{
    void setCommonEvent(final String p0);
    
    String getCommonEvent();
}
