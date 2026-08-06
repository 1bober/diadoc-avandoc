// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import java.util.Collection;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationService;

public interface IDiadocEdoIntegrationService extends IEdoIntegrationService
{
    public static final String INTEGRATION_ID = "diadoc_edo_integration";
    public static final String DIADOCEDO_INTEGRATION_MARK = "DIADOCEDO";
    
    void processQueueForSend(final DMSShardsClientSession p0, final Map<String, String> p1, final SessionContext p2);
    
    void getRemoteDocumentState(final DMSShardsClientSession p0, final Map<String, String> p1, final SessionContext p2);
    
    boolean fireEvent(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final String p4, final Map<String, String> p5, final SessionContext p6);
    
    List<IDiadocEdoOrganizationDetails> getOursOrganizationsDetails(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final boolean p4, final IDiadocEdoServer p5, final SessionContext p6);
    
    void queueDocument(final DMSShardsClientSession p0, final IDocument p1, final Map<String, String> p2, final SessionContext p3);
    
    void changeDocumentExchangeState(final DMSShardsClientSession p0, final IDocument p1, final Map<String, String> p2, final SessionContext p3);
    
    void checkLocalDocumentStatus(final DMSShardsClientSession p0, final Map<String, String> p1, final SessionContext p2);
    
    void checkCounteragent(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final Map<String, String> p4, final SessionContext p5);
    
    void processRemoteDocumentStateChanges(final DMSShardsClientSession p0, final Map<String, String> p1, final SessionContext p2);
    
    IEdoIntegrationSendProcessor.ProcessingResult createOutgoingRevokeFile(final DMSShardsClientSession p0, final IDocument p1, final Map<String, String> p2, final SessionContext p3);
    
    IEdoIntegrationSendProcessor.ProcessingResult queueOutgoingRevokeFile(final DMSShardsClientSession p0, final IDocument p1, final Map<String, String> p2, final SessionContext p3);
    
    List<IDiadocEdoOrganizationDetails> getCounteragentOrganizationDetails(final DMSShardsClientSession p0, final String p1, final String p2, final IDiadocEdoServer p3, final SessionContext p4);
    
    Map<String, String[]> getAvailableOrganizationDepartment(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final IDiadocEdoServer p4, final SessionContext p5);
    
    void checkConnection(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final SessionContext p4);
    
    Collection<Map<String, String>> getCounteragentsByInn(final DMSShardsClientSession p0, final String p1, final String p2, final Map<String, String> p3, final SessionContext p4);
    
    Collection<Map<String, String>> getOrganizationsByInn(final DMSShardsClientSession p0, final String p1, final String p2, final Map<String, String> p3, final SessionContext p4);
    
    void checkIncomingDocuments(final DMSShardsClientSession p0, final Map<String, String> p1, final SessionContext p2);
    
    void processIncomingDocumentsQueue(final DMSShardsClientSession p0, final List<String> p1, final Map<String, String> p2, final SessionContext p3);
}
