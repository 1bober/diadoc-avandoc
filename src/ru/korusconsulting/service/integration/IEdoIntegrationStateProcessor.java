// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IEdoIntegrationStateProcessor
{
    default IEdoIntegrationStateDescription enrichStateInfo(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final String stateCode, final String state, final SessionContext ctx) {
        return null;
    }
    
    default IEdoIntegrationErrorDescription enrichErrorInfo(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final int httpCode, final String stateCode, final String state, final SessionContext ctx) {
        return null;
    }
}
