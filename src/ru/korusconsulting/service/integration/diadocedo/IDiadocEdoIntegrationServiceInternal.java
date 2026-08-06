// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import ru.korusconsulting.service.integration.model.IEdoIntegrationMappingInfo;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationContentSync;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoCounteragentProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.function.Predicate;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationStateProcessor;

public interface IDiadocEdoIntegrationServiceInternal extends IDiadocEdoIntegrationIntegrationLogWriter, IDiadocEdoIntegrationService, IEdoIntegrationStateProcessor
{
     <T extends ICstmConfigAdapter> T getOperationConfiguration(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final Supplier<IEdoIntegrationFlowConfiguration> p3, final Supplier<T> p4, final Predicate<T> p5, final SessionContext p6);
    
    boolean sendDocument(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final IDocument p3, final IEdoIntegrationExchangeItem p4, final String p5, final Map<String, String> p6, final SessionContext p7) throws DiadocEdoIntegrationException;
    
    IDiadocEdoCounteragentProcessor createCounteragentProcessor(final DMSShardsClientSession p0, final SessionContext p1);
    
    String getCounteragentDetails(final String p0, final String p1, final SessionContext p2);
    
    IEdoIntegrationContentSync creatContentSyncObject(final DMSShardsClientSession p0, final IEdoIntegrationService.EXCHANGE_DIRECTION p1, final IDiadocEdoOperationContext p2, final IDocument p3, final String p4, final String p5, final String p6, final String p7, final SessionContext p8);
    
    void commitQueueItemState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationInboundDataExchangeItem p2, final SessionContext p3);
    
    void commitQueueItemRemoteState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationInboundDataExchangeItem p2, final IEdoIntegrationInboundDataExchangeItem.ProcessingStep p3, final SessionContext p4);
    
    void commitQueueItemState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationInboundDataExchangeItem p2, final IEdoIntegrationInboundDataExchangeItem.ProcessingError p3, final SessionContext p4);
    
    void commitQueueItemState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationInboundDataExchangeItem p2, final EdoIntegrationProcessingState p3, final SessionContext p4);
    
    IEdoIntegrationSendProcessor.ProcessingResult queueDocument(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final IDocument p3, final Map<String, String> p4, final SessionContext p5);
    
    Map<String, String[]> getAvailableOrganizationBoxes(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final String p3, final IDiadocEdoServer p4, final SessionContext p5);
    
    Map<String, String[]> getAvailableOrganizationDepartment(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final String p3, final String p4, final IDiadocEdoServer p5, final SessionContext p6);
    
    List<IDiadocEdoOrganizationDetails> getCounteragentOrganizationDetails(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final String p3, final String p4, final IDiadocEdoServer p5, final SessionContext p6);
    
    IEdoIntegrationSendProcessor.ProcessingResult getRemoteDocumentsState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final Map<String, String> p3, final SessionContext p4);
    
    void processRemoteDocumentsStateChanges(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final Map<String, String> p3, final SessionContext p4);
    
    IEdoIntegrationSendProcessor.ProcessingResult processQueueForSend(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final Map<String, String> p3, final SessionContext p4);
    
    IEdoIntegrationSendProcessor.ProcessingResult createOutgoingRevokeFile(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final IDocument p3, final Map<String, String> p4, final SessionContext p5) throws DiadocEdoIntegrationException;
    
    IEdoIntegrationSendProcessor.ProcessingResult queueOutgoingRevokeFile(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final IDocument p3, final Map<String, String> p4, final SessionContext p5) throws DiadocEdoIntegrationException;
    
    void checkLocalDocumentStatus(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final Map<String, String> p3, final SessionContext p4);
    
    boolean processAnswerAttributes(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IObjectModel p2, final IObjectModel p3, final Object p4, final List<IEdoIntegrationAttributeMapping> p5, final Map<String, String> p6, final SessionContext p7);
    
    List<IDiadocEdoOrganizationDetails> getOursOrganizationsDetails(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final String p3, final String p4, final boolean p5, final IDiadocEdoServer p6, final SessionContext p7);
    
    SessionContext createDocumentSessionContext(final DMSShardsClientSession p0, final IDocument p1, final SessionContext p2);
    
    SessionContext createFilialSessionContext(final DMSShardsClientSession p0, final IFilial p1, final SessionContext p2);
    
    IEdoIntegrationMappingInfo transformRemoteState(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final SessionContext p3);
    
    IDiadocEdoExchangeError transformExchangeError(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final String p2, final String p3, final SessionContext p4);
    
    IEdoIntegrationSendProcessor.ProcessingResult checkIncomingDocuments(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationServiceConfiguration p2, final Map<String, String> p3, final SessionContext p4);
    
    public enum ParamName
    {
        login, 
        box_id, 
        error_message, 
        action_name, 
        external_url, 
        document_id, 
        filial_id, 
        filial_name, 
        new_state, 
        external_id, 
        url, 
        http_code, 
        http_message, 
        filename, 
        document_count, 
        counteragent_box, 
        counteragent_inn, 
        counteragent_name, 
        counteragent_kpp, 
        user_external_id, 
        user_name, 
        user_surname, 
        server_id, 
        server_name, 
        department_out, 
        regulations_out, 
        regulations_kz, 
        department_in, 
        regulations_multi, 
        regulations_in, 
        box_guid, 
        my_box_id, 
        my_box_guid, 
        server_url, 
        message_id, 
        entity_id, 
        web_url, 
        counteragent_org_id, 
        result_list_size, 
        counteragent_fns_id, 
        counteragent_department, 
        old_state, 
        content_id, 
        content_name, 
        state_message, 
        state_code, 
        display_message, 
        unknown, 
        counteragent_details, 
        old_state_code, 
        document_name, 
        list_counteragents_names, 
        entity_ids, 
        my_department_id, 
        content_shelf_id, 
        counteragent_code, 
        counteragent_message, 
        start_check_date;
        
        public static ParamName parse(final String key) {
            if (StringUtils.isBlank(key)) {
                return ParamName.unknown;
            }
            for (final ParamName v : values()) {
                if (v.name().equals(key)) {
                    return v;
                }
            }
            return ParamName.unknown;
        }
    }
}
