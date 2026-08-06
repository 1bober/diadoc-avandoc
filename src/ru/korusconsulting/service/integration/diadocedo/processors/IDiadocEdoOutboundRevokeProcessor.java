// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoOutboundRevokeProcessor extends IDiadocEdoProcessor
{
    IEdoIntegrationSendProcessor.ProcessingResult createRevokeFile(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDocument p2, final String p3, final String p4, final Map<String, String> p5, final SessionContext p6) throws DiadocEdoIntegrationException;
    
    IEdoIntegrationSendProcessor.ProcessingResult sendRevokeFile(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDocument p2, final String p3, final String p4, final Map<String, String> p5, final SessionContext p6) throws DiadocEdoIntegrationException;
}
