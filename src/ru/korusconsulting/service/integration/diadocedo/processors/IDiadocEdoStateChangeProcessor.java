// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByFilialItem;
import java.util.Collection;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoStateChangeProcessor extends IDiadocEdoProcessor
{
    IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final Collection<IDiadocEdoCombinedByFilialItem> p2, final SessionContext p3);
}
