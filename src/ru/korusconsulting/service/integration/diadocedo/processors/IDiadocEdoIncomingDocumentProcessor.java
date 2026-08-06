// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIncomingDocumentRequest;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationInboundDocumentProcessor;

public interface IDiadocEdoIncomingDocumentProcessor extends IEdoIntegrationInboundDocumentProcessor<IDiadocEdoOperationContext>, IDiadocEdoProcessor
{
    IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final IDiadocEdoIncomingDocumentRequest p3, final SessionContext p4);
}
