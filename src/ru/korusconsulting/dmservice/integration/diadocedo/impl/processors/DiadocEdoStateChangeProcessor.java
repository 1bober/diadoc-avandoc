// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItemRemoteProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import java.util.List;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoOperationContext;
import java.util.ArrayList;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import java.util.Iterator;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByFilialItem;
import java.util.Collection;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.HashMap;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiAccountConnection;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoStateProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoStateChangeProcessor;

public class DiadocEdoStateChangeProcessor extends DiadocEdoInboundDocumentProcessor implements IDiadocEdoStateChangeProcessor, IDiadocEdoProcessor, IDiadocEdoStateProcessor
{
    private static final int MAX_BATCH_SIZE = 100;
    private final Map<String, DiadocEdoApiAccountConnection> connectionCache;
    
    public DiadocEdoStateChangeProcessor() {
        this.connectionCache = new HashMap<String, DiadocEdoApiAccountConnection>();
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final Collection<IDiadocEdoCombinedByFilialItem> batch, final SessionContext ctx) {
        if (batch.isEmpty()) {
            return IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        }
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        for (final IDiadocEdoCombinedByFilialItem filialGroup : batch) {
            for (final IDiadocEdoCombinedByDocumentItem documentGroup : filialGroup.getItems()) {
                processingResult = processingResult.concat(this.processDocument(session, oContext, documentGroup.getKey(), documentGroup.getAllItems(), ctx));
            }
        }
        return processingResult;
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult processDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final String documentId, final Collection<IEdoIntegrationExchangeItem> exchangeItems, final SessionContext ctx) {
        final IEdoIntegrationSendProcessor.ProcessingResult operationResult = IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED;
        record DocsByFilial(IFilial filial, Map<String, ItemsByDocs> documentsList) {}
        final Map<String, DocsByFilial> m = new HashMap<String, DocsByFilial>();
        record ItemsByDocs(IDocument document, Collection<IEdoIntegrationExchangeItem> itemsList) {}
        for (final IEdoIntegrationExchangeItem exchangeItem : exchangeItems) {
            final IDocument document = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, exchangeItem.getDocumentId(), ctx);
            final DocsByFilial a;
            final DocsByFilial dF = m.computeIfAbsent(exchangeItem.getFilialId(), a -> {
                new DocsByFilial((IFilial)ServiceReg.getObjectModelService().getObjectModel(session, a, ctx), new HashMap<String, ItemsByDocs>());
                return;
            });
            final ItemsByDocs iD = dF.documentsList().computeIfAbsent(exchangeItem.getDocumentId(), a -> {
                new ItemsByDocs(document, new ArrayList<IEdoIntegrationExchangeItem>());
                return;
            });
            iD.itemsList().add(exchangeItem);
        }
        final Iterator<DocsByFilial> iterator2 = m.values().iterator();
        while (iterator2.hasNext()) {
            final DocsByFilial a = iterator2.next();
            try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createFilialContext(session, oContext, a.filial(), ctx)) {
                try (final IDiadocEdoApi api = operationContext.getApiConnection(session, this.getService(), ctx).getApi()) {
                    for (final ItemsByDocs itemsByDocs : a.documentsList().values()) {
                        IEdoIntegrationSendProcessor.ProcessingResult.concat(operationResult, this.processDocument(session, operationContext, api, itemsByDocs.document(), itemsByDocs.itemsList(), ctx));
                    }
                }
                catch (final DiadocEdoIntegrationException e) {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(null, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PROCESS_REMOTE_STATUS_CHANGE_ERROR, null, null);
                    this.getService().writeIntegrationLog(operationContext, IEdoIntegrationSendProcessor.ProcessingResult.FAILED, true, messagesContainer, ctx);
                }
                if (operationContext == null) {
                    continue;
                }
            }
        }
        return operationResult;
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult processDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IDiadocEdoApi api, final IDocument document, final Collection<IEdoIntegrationExchangeItem> exchangeItems, final SessionContext ctx) {
        final Map<String, List<IEdoIntegrationExchangeItem>> exchangeItemsStructured = new HashMap<String, List<IEdoIntegrationExchangeItem>>();
        for (final IEdoIntegrationExchangeItem exchangeItem : exchangeItems) {
            exchangeItemsStructured.computeIfAbsent(exchangeItem.getCounteragentContainerId(), a -> new ArrayList()).add(exchangeItem);
        }
        IEdoIntegrationSendProcessor.ProcessingResult operationResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createDocumentContext(session, (IIntegrationOperationContext)oContext, document, ctx)) {
            try {
                final SessionContext documentContext = ctx.clone();
                ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, documentContext);
                String cumulativeStateSystemCode = null;
                boolean cumulativeState = true;
                boolean hasChanges = false;
                for (final Map.Entry<String, List<IEdoIntegrationExchangeItem>> p : exchangeItemsStructured.entrySet()) {
                    String cumulativeCounteragentStateCode = null;
                    String cumulativeCounteragentStateMessage = null;
                    String cumulativeCounteragentStateSystemCode = null;
                    boolean cumulativeCounteragentState = true;
                    boolean hasCounteragentChanges = false;
                    final IObjectModel counteragentModel = ServiceReg.getObjectModelService().getObjectModel(session, (String)p.getKey(), ctx);
                    for (final IEdoIntegrationExchangeItem itemModelRaw : p.getValue()) {
                        hasChanges = IEdoIntegrationInboundDataExchangeItem.ProcessingStep.inProgress(itemModelRaw.getRemoteStateProcessingStep());
                        final IEdoIntegrationExchangeItemProcessingWrapper itemModel = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemRemoteProcessingWrapper(itemModelRaw);
                        final Map<String, Object> processingArgsParams = new HashMap<String, Object>();
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn.name(), itemModel.getItem().getCounteragentInn());
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp.name(), itemModel.getItem().getCounteragentKpp());
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_name.name(), itemModel.getItem().getCounteragentName());
                        cumulativeCounteragentStateCode = itemModel.getItem().getRemoteState();
                        cumulativeCounteragentStateMessage = itemModel.getItem().getRemoteStateMessage();
                        final String upcomingStateCode = itemModel.getStateMessage();
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.state_code.name(), upcomingStateCode);
                        final String stateSystemCode = itemModel.getStateSystemCode();
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.display_message.name(), itemModel.getStateDisplay());
                        if (cumulativeCounteragentStateSystemCode == null) {
                            cumulativeCounteragentStateSystemCode = stateSystemCode;
                        }
                        else if (!cumulativeCounteragentStateSystemCode.equals(stateSystemCode)) {
                            cumulativeCounteragentState = false;
                        }
                        if (cumulativeStateSystemCode == null) {
                            cumulativeStateSystemCode = stateSystemCode;
                        }
                        else if (!cumulativeStateSystemCode.equals(stateSystemCode)) {
                            cumulativeState = false;
                        }
                        hasChanges = (!IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed.is(itemModel.getRemoteStateProcessingStep()) && !IEdoIntegrationInboundDataExchangeItem.ProcessingStep.skip.is(itemModel.getRemoteStateProcessingStep()));
                        if (hasChanges) {
                            if (itemModel.getItem().getProcessingContext() == null) {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Processing status change %s for document %s/%s has lost processing context. Retring...", itemModel.getItem().getObjectId(), document.getObjectId(), itemModel.getItem().getDocumentEdoId()));
                                itemModel.setStateSystemCode(itemModel.getPrevStateSystemCode());
                                itemModel.setStateMessage(itemModel.getPrevStateMessage());
                                itemModel.setState(itemModel.getPrevState());
                                itemModel.setStateTime(itemModel.getPrevStateTime());
                                itemModel.setStateCategory("simple");
                                this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)itemModel.getItem(), IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued, documentContext);
                                cumulativeState = false;
                                cumulativeCounteragentState = false;
                            }
                            else {
                                if (this.processContentState(session, operationContext, api, this.getService(), itemModel.getItem(), null, document, counteragentModel, stateSystemCode, processingArgsParams, documentContext) < 10) {
                                    cumulativeState = false;
                                    cumulativeCounteragentState = false;
                                }
                                else {
                                    hasCounteragentChanges = true;
                                    hasChanges = true;
                                }
                                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)itemModel.getItem(), ctx);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("After processing status is code: %s, message: %s. System code : %s, remote state processing stage %s", itemModel.getStateSystemCode(), itemModel.getStateMessage(), itemModel.getState(), itemModel.getRemoteStateProcessingStep()));
                            }
                        }
                    }
                    if (hasCounteragentChanges) {
                        if (cumulativeCounteragentState) {
                            final Map<String, Object> processingArgsParams2 = new HashMap<String, Object>();
                            if (cumulativeCounteragentStateMessage != null) {
                                processingArgsParams2.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_message.name(), cumulativeCounteragentStateMessage);
                                processingArgsParams2.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_code.name(), cumulativeCounteragentStateCode);
                            }
                            if (this.processCounteragentState(session, operationContext, this.getService(), document, counteragentModel, cumulativeCounteragentStateSystemCode, processingArgsParams2, documentContext) < 10) {
                                operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                            }
                            else {
                                operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS);
                            }
                        }
                        else if (operationResult == IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED) {
                            operationResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                        }
                        else {
                            if (operationResult != IEdoIntegrationSendProcessor.ProcessingResult.FAILED) {
                                continue;
                            }
                            operationResult = IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
                        }
                    }
                }
                if (hasChanges) {
                    if (cumulativeState) {
                        final Map<String, Object> processingArgsParams3 = new HashMap<String, Object>();
                        if (this.processCummulativeState(session, operationContext, this.getService(), document, cumulativeStateSystemCode, processingArgsParams3, documentContext) < 10) {
                            operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                        }
                        else {
                            operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS);
                        }
                    }
                    else if (operationResult == IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED) {
                        operationResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                    }
                    else if (operationResult == IEdoIntegrationSendProcessor.ProcessingResult.FAILED) {
                        operationResult = IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
                    }
                }
            }
            catch (final Exception e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e);
                operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)new Exception(e2));
                operationResult = operationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PROCESS_DOCUMENTS_REMOTE_STATUS_CHANGE_SUCCESS, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PROCESS_DOCUMENTS_REMOTE_STATUS_CHANGE_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PROCESS_DOCUMENTS_REMOTE_STATUS_CHANGE_WITH_ERRORS, null);
                this.getService().writeIntegrationLog(operationContext, operationResult, true, messagesContainer, ctx);
            }
        }
        return operationResult;
    }
    
    @Override
    public void close() {
        this.connectionCache.clear();
    }
    
    @Override
    protected boolean processInboundAttributeMapping(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final IObjectModel counteragent, final IObjectModel subjectModel, final IEdoIntegrationAttributeMapping mapping, final Map<String, String> env, final Map<String, Object> args, final BiConsumer<String, Object> setter, final SessionContext ctx) {
        if (mapping instanceof final IEdoIntegrationAnswerMappingByCounteragent linkedConfig) {
            if (counteragent != null) {
                final List answerMapping = linkedConfig.getAnswerMapping();
                Objects.requireNonNull(counteragent);
                if (this.processInboundAttributes(session, operationContext, document, counteragent, counteragent, answerMapping, env, args, counteragent::setValue, ctx)) {
                    ServiceReg.getObjectModelService().commitModel(session, counteragent, ctx);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", counteragent.getObjectId(), counteragent.getObjectType()));
                    return false;
                }
            }
            return false;
        }
        return super.processInboundAttributeMapping(session, operationContext, document, counteragent, subjectModel, mapping, env, args, setter, ctx);
    }
    
    public enum CHANGE_TYPE
    {
        all, 
        none, 
        partially, 
        error;
    }
    
    class ResponseWrapper<T>
    {
        private T value;
        
        public T getValue() {
            return this.value;
        }
        
        public void setValue(final T value) {
            this.value = value;
        }
    }
    
    class ExchangeItemToResponseList<T, S>
    {
        private T item;
        private S response;
        
        protected ExchangeItemToResponseList(final T item, final S response) {
            this.item = item;
            this.response = response;
        }
        
        public T getItem() {
            return this.item;
        }
        
        public void setItem(final T value) {
            this.item = value;
        }
        
        public S getResponse() {
            return this.response;
        }
        
        public void setResponse(final S value) {
            this.response = value;
        }
    }
}
