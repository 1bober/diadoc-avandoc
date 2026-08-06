// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import java.util.Map;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import java.util.Collection;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationGetRemoteStateProcessor;

public interface IDiadocEdoGetRemoteStateProcessor extends IEdoIntegrationGetRemoteStateProcessor<IDiadocEdoOperationContext>
{
    void setContentEvent(final String p0);
    
    String getContentEvent();
    
    Map<String, Boolean> process(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final Collection<IDiadocEdoCombinedByDocumentItem> p3, final String p4, final String p5, final SessionContext p6);
}
