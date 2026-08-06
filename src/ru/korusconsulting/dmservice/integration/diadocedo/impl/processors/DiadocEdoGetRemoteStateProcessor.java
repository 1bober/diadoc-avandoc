// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.model.IEdoIntegrationMappingInfo;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import Diadoc.Api.exceptions.DiadocSdkException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Objects;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageEmptyProcessingContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import java.util.Calendar;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.utils.PojoSourceNavigator;
import java.util.Arrays;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.dmservice.integration.NamedParam;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItemRemoteProcessingWrapper;
import ru.korusconsulting.dmservice.utils.ProtobufToPojoTransformer;
import java.util.Iterator;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationRetryAfterException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import Diadoc.Api.Proto.DocumentIdProtos;
import Diadoc.Api.Proto.Docflow.DocflowApiProtos;
import Diadoc.Api.Proto.Docflow.DocumentWithDocflowV4Protos;
import java.util.List;
import Diadoc.Api.Proto.Docflow.DocflowApiV4Protos;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.ArrayList;
import java.util.Collections;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import java.util.Collection;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.HashMap;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiAccountConnection;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoStateProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoGetRemoteStateProcessor;

public class DiadocEdoGetRemoteStateProcessor extends DiadocEdoInboundDocumentProcessor implements IDiadocEdoGetRemoteStateProcessor, IDiadocEdoStateProcessor
{
    private static final int MAX_BATCH_SIZE = 100;
    private final Map<String, DiadocEdoApiAccountConnection> connectionCache;
    private String commonEvent;
    private String contentEvent;
    
    public DiadocEdoGetRemoteStateProcessor() {
        this.connectionCache = new HashMap<String, DiadocEdoApiAccountConnection>();
    }
    
    @Override
    public Map<String, Boolean> process(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IDiadocEdoApi api, final Collection<IDiadocEdoCombinedByDocumentItem> batch, final String filialId, final String filialName, final SessionContext ctx) {
        try {
            if (batch.isEmpty()) {
                return Collections.emptyMap();
            }
            final int partitionSize = 100;
            final List<ExchangeItemToResponseList<Map<String, IEdoIntegrationExchangeItem>, ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>>> batchResponseToItems = new ArrayList<ExchangeItemToResponseList<Map<String, IEdoIntegrationExchangeItem>, ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>>>();
            final Collection<IEdoIntegrationExchangeItem> itemListForCheck = batch.stream().map(a -> a.getAllItems()).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
            final Map<String, Map<String, List<ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4>>>> itemToSingleResponses = new HashMap<String, Map<String, List<ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4>>>>();
            ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4> currentResponse = new ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>();
            Map<String, IEdoIntegrationExchangeItem> currentList = new HashMap<String, IEdoIntegrationExchangeItem>();
            batchResponseToItems.add(new ExchangeItemToResponseList<Map<String, IEdoIntegrationExchangeItem>, ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>>(currentList, currentResponse));
            DocflowApiProtos.GetDocflowBatchRequest.Builder docflowBatchRequestBuilder = DocflowApiProtos.GetDocflowBatchRequest.newBuilder();
            int batchRequestSize = 0;
            for (final IEdoIntegrationExchangeItem itemForCheck : itemListForCheck) {
                final DocflowApiProtos.GetDocflowRequest.Builder docflowRequestBuilder = DocflowApiProtos.GetDocflowRequest.newBuilder();
                final DocumentIdProtos.DocumentId.Builder documentIdBuilder = DocumentIdProtos.DocumentId.newBuilder();
                documentIdBuilder.setMessageId(itemForCheck.getMessageEdoId());
                documentIdBuilder.setEntityId(itemForCheck.getDocumentEdoId());
                final String key = String.join(":", itemForCheck.getMessageEdoId(), itemForCheck.getDocumentEdoId());
                currentList.put(key, itemForCheck);
                docflowRequestBuilder.setDocumentId(documentIdBuilder.build());
                docflowBatchRequestBuilder.addRequests(docflowRequestBuilder.build());
                if (++batchRequestSize >= partitionSize) {
                    int z = 0;
                    while (true) {
                        try {
                            final DocflowApiV4Protos.GetDocflowBatchResponseV4 getDocflowBatchResponse = DiadocEdoApiMethodLibrary.getDocflows(session, this.getService(), oContext, api, oContext.getDiadocBoxGuid(session, ctx), docflowBatchRequestBuilder.build(), ctx);
                            currentResponse.setValue(getDocflowBatchResponse);
                            currentList = new HashMap<String, IEdoIntegrationExchangeItem>();
                            currentResponse = new ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>();
                            batchResponseToItems.add(new ExchangeItemToResponseList<Map<String, IEdoIntegrationExchangeItem>, ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>>(currentList, currentResponse));
                        }
                        catch (final DiadocEdoIntegrationRetryAfterException e) {
                            if (e.getRetryAfter() < 10 && z < 10) {
                                try {
                                    Thread.sleep(10000L);
                                }
                                catch (final InterruptedException e2) {
                                    Thread.currentThread().interrupt();
                                }
                                ++z;
                                continue;
                            }
                            throw e;
                        }
                        break;
                    }
                    docflowBatchRequestBuilder = DocflowApiProtos.GetDocflowBatchRequest.newBuilder();
                    batchRequestSize = 0;
                }
            }
            if (batchRequestSize > 0) {
                int z2 = 0;
                while (true) {
                    try {
                        final DocflowApiV4Protos.GetDocflowBatchResponseV4 getDocflowBatchResponse2 = DiadocEdoApiMethodLibrary.getDocflows(session, this.getService(), oContext, api, oContext.getDiadocBoxGuid(session, ctx), docflowBatchRequestBuilder.build(), ctx);
                        currentResponse.setValue(getDocflowBatchResponse2);
                    }
                    catch (final DiadocEdoIntegrationRetryAfterException e3) {
                        if (e3.getRetryAfter() < 10 && z2 < 10) {
                            try {
                                Thread.sleep(10000L);
                            }
                            catch (final InterruptedException e4) {
                                Thread.currentThread().interrupt();
                            }
                            ++z2;
                            continue;
                        }
                        throw e3;
                    }
                    break;
                }
            }
            for (final ExchangeItemToResponseList<Map<String, IEdoIntegrationExchangeItem>, ResponseWrapper<DocflowApiV4Protos.GetDocflowBatchResponseV4>> response : batchResponseToItems) {
                final DocflowApiV4Protos.GetDocflowBatchResponseV4 batchResponse = response.getResponse().getValue();
                if (batchResponse != null) {
                    for (final DocumentWithDocflowV4Protos.DocumentWithDocflowV4 doc : batchResponse.getDocumentsList()) {
                        final String key2 = String.join(":", doc.getDocumentId().getMessageId(), doc.getDocumentId().getEntityId());
                        final IEdoIntegrationExchangeItem iItem = (IEdoIntegrationExchangeItem)response.getItem().get(key2);
                        if (iItem != null) {
                            ((List)itemToSingleResponses.computeIfAbsent(iItem.getDocumentId(), a -> new HashMap()).computeIfAbsent((Object)iItem.getCounteragentContainerId(), b -> new ArrayList())).add(new ExchangeItemToResponseList(iItem, doc));
                        }
                    }
                }
            }
            for (final Map.Entry<String, Map<String, List<ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4>>>> itemToSingleResponseEntry : itemToSingleResponses.entrySet()) {
                final IDocument document = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, (String)itemToSingleResponseEntry.getKey(), ctx);
                IEdoIntegrationSendProcessor.ProcessingResult operationResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
                try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createDocumentContext(session, (IIntegrationOperationContext)oContext, document, ctx)) {
                    try {
                        operationResult = this.processDocument(session, operationContext, api, document, itemToSingleResponseEntry.getValue(), ctx);
                    }
                    catch (final Exception e5) {
                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e5);
                        operationResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                    }
                    catch (final Throwable e6) {
                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)new Exception(e6));
                        operationResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                    }
                    finally {
                        final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_DOCUMENT_REMOTE_STATUS_SUCCESS, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_DOCUMENT_REMOTE_STATUS_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_DOCUMENT_REMOTE_STATUS_PARTIALLY, null);
                        this.getService().writeIntegrationLog(operationContext, operationResult, true, messagesContainer, ctx);
                    }
                    if (operationContext == null) {
                        continue;
                    }
                }
            }
        }
        catch (final Throwable e7) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "System error", e7);
        }
        return Collections.emptyMap();
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult processDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final Map<String, List<ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4>>> counteragentDocflows, final SessionContext ctx) {
        IEdoIntegrationSendProcessor.ProcessingResult overallOperationResult = IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED;
        final SessionContext documentContext = ctx.clone();
        ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, documentContext);
        String cumulativeStateSystemCode = null;
        boolean cumulativeState = true;
        boolean hasChanges = false;
        for (final Map.Entry<String, List<ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4>>> p : counteragentDocflows.entrySet()) {
            String cumulativeCounteragentStateSystemCode = null;
            String cumulativeCounteragentStateCode = null;
            String cumulativeCounteragentStateMessage = null;
            boolean cumulativeCounteragentState = true;
            boolean hasCounteragentChanges = false;
            final IObjectModel counteragentModel = ServiceReg.getObjectModelService().getObjectModel(session, (String)p.getKey(), ctx);
            for (final ExchangeItemToResponseList<IEdoIntegrationExchangeItem, DocumentWithDocflowV4Protos.DocumentWithDocflowV4> p2 : p.getValue()) {
                final Map<String, Object> packageData = ProtobufToPojoTransformer.transform(p2.getResponse().getAllFields());
                final IEdoIntegrationExchangeItemProcessingWrapper itemModel = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemRemoteProcessingWrapper((IEdoIntegrationExchangeItem)p2.getItem());
                final List<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> params = new ArrayList<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>((Collection<? extends NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)itemModel.getItem().getToInn()), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)itemModel.getItem().getToKpp()), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)this.getService().getCounteragentDetails(itemModel.getItem().getToInn(), itemModel.getItem().getToKpp(), ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.old_state_code, (Object)itemModel.getStateSystemCode()), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.old_state, (Object)itemModel.getStateMessage()), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.box_id, (Object)itemModel.getItem().getFromBoxGuid()), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.external_id, (Object)itemModel.getItem().getMessageEdoId())));
                final Map<String, Object> processingArgsParams = new HashMap<String, Object>();
                processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn.name(), itemModel.getItem().getCounteragentInn());
                processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp.name(), itemModel.getItem().getCounteragentKpp());
                processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_name.name(), itemModel.getItem().getCounteragentName());
                final String upcomingStatusText = (String)PojoSourceNavigator.evaluateFirst((Object)packageData, "/Docflow/DocflowStatus/PrimaryStatus/StatusText");
                if (itemModel != null && (!StringUtils.equals(itemModel.getStateMessage(), upcomingStatusText) || IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued.is(itemModel.getRemoteStateProcessingStep()))) {
                    hasCounteragentChanges = true;
                    hasChanges = true;
                    itemModel.getItem().setProcessingContext((IEdoIntegrationPackageProcessingContext)null);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s [%s]. Content %s [%s]. New state is %s", itemModel.getItem().getDocumentId(), itemModel.getItem().getMessageEdoId(), itemModel.getItem().getContentId(), itemModel.getItem().getDocumentEdoId(), upcomingStatusText));
                    final IEdoIntegrationMappingInfo transformatedRemoteState = this.getService().transformRemoteState(session, operationContext, upcomingStatusText, ctx);
                    itemModel.setPrevStateMessage(itemModel.getStateMessage());
                    itemModel.setPrevState(itemModel.getState());
                    itemModel.setPrevStateTime(itemModel.getStateTime());
                    itemModel.setStateTime(Calendar.getInstance().getTime());
                    if (transformatedRemoteState != null) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s [%s]. Content %s [%s]. Found transformation to %s [%s], category %s", itemModel.getItem().getDocumentId(), itemModel.getItem().getMessageEdoId(), itemModel.getItem().getContentId(), itemModel.getItem().getDocumentEdoId(), transformatedRemoteState.getSystemCode(), transformatedRemoteState.getUserMessage(), transformatedRemoteState.getCategory()));
                        itemModel.setStateSystemCode(transformatedRemoteState.getCategory());
                        itemModel.setStateDisplay(StringUtils.trimToEmpty(transformatedRemoteState.getUserMessage()));
                        itemModel.setStateMessage(upcomingStatusText);
                        itemModel.setState(transformatedRemoteState.getSystemCode());
                        itemModel.setStateCategory("simple");
                        itemModel.getItem().setLastCheckTime(Calendar.getInstance().getTime());
                        this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)itemModel.getItem(), IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued, ctx);
                        cumulativeCounteragentStateCode = transformatedRemoteState.getCategory();
                        cumulativeCounteragentStateMessage = transformatedRemoteState.getUserMessage();
                        if (cumulativeCounteragentStateSystemCode == null) {
                            cumulativeCounteragentStateSystemCode = transformatedRemoteState.getCategory();
                        }
                        else if (!cumulativeCounteragentStateSystemCode.equals(transformatedRemoteState.getCategory())) {
                            cumulativeCounteragentState = false;
                        }
                        if (cumulativeStateSystemCode == null) {
                            cumulativeStateSystemCode = transformatedRemoteState.getCategory();
                        }
                        else if (!cumulativeStateSystemCode.equals(transformatedRemoteState.getCategory())) {
                            cumulativeState = false;
                        }
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_message.name(), cumulativeCounteragentStateMessage);
                        processingArgsParams.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_code.name(), cumulativeCounteragentStateCode);
                        try {
                            if (this.processContentState(session, operationContext, api, this.getService(), itemModel.getItem(), packageData, document, counteragentModel, transformatedRemoteState.getCategory(), processingArgsParams, documentContext) < 10) {
                                cumulativeState = false;
                                cumulativeCounteragentState = false;
                            }
                        }
                        catch (final DiadocEdoIntegrationException exp) {
                            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)exp);
                            cumulativeState = false;
                            cumulativeCounteragentState = false;
                        }
                        catch (final Throwable exp2) {
                            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", exp2);
                            cumulativeState = false;
                            cumulativeCounteragentState = false;
                        }
                    }
                    else {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s [%s]. Content %s [%s]. Transformation for %s not found", itemModel.getItem().getDocumentId(), itemModel.getItem().getMessageEdoId(), itemModel.getItem().getContentId(), itemModel.getItem().getDocumentEdoId(), upcomingStatusText));
                        itemModel.setStateSystemCode((String)null);
                        itemModel.setStateDisplay((String)null);
                        itemModel.setStateMessage(upcomingStatusText);
                        itemModel.setState((String)null);
                        final IEdoIntegrationPackageProcessingContext emptyProcessingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageEmptyProcessingContext();
                        itemModel.getItem().setProcessingContext(emptyProcessingContext);
                        cumulativeCounteragentState = false;
                        cumulativeState = false;
                        itemModel.getItem().setLastCheckTime(Calendar.getInstance().getTime());
                        this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)itemModel.getItem(), IEdoIntegrationInboundDataExchangeItem.ProcessingStep.skip, ctx);
                    }
                    params.add((NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>)new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.new_state, (Object)upcomingStatusText));
                    if (this.getCommonEvent() == null) {
                        continue;
                    }
                    final ICstmComponent eventObject = (ICstmComponent)ServiceReg.getObjectModelService().getObjectModel(session, this.getCommonEvent(), documentContext);
                    if (eventObject != null) {
                        if (this.getService().fireEvent(session, eventObject.getCode(), document.getObjectId(), document.getObjectType(), null, (Map<String, String>)params.stream().collect(Collectors.toMap((Function<? super Object, ?>)NamedParam::getKeyAsString, c -> Objects.toString(c.getValue(), ""))), documentContext)) {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Succesfully fired event %s", document.getObjectId(), eventObject.getCode()), operationContext.getPropogateLogMode());
                        }
                        else {
                            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Failed to fire event %s", document.getObjectId(), eventObject.getCode()));
                        }
                    }
                    else {
                        IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Failed to find event with id %s", document.getObjectId(), this.getCommonEvent()));
                    }
                }
                else {
                    IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. State not changed", document.getObjectId()));
                    if (cumulativeCounteragentStateSystemCode == null) {
                        cumulativeCounteragentStateSystemCode = StringUtils.trimToEmpty(itemModel.getStateSystemCode());
                    }
                    else if (!cumulativeCounteragentStateSystemCode.equals(itemModel.getStateSystemCode())) {
                        cumulativeCounteragentState = false;
                    }
                    if (cumulativeStateSystemCode == null) {
                        cumulativeStateSystemCode = StringUtils.trimToEmpty(itemModel.getStateSystemCode());
                    }
                    else {
                        if (cumulativeStateSystemCode.equals(itemModel.getStateSystemCode())) {
                            continue;
                        }
                        cumulativeState = false;
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
                    try {
                        if (this.processCounteragentState(session, operationContext, this.getService(), document, counteragentModel, cumulativeCounteragentStateSystemCode, processingArgsParams2, documentContext) < 10) {
                            overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                        }
                        else {
                            overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS);
                        }
                    }
                    catch (final DiadocEdoIntegrationException e) {
                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e);
                        overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                    }
                    catch (final DiadocSdkException e2) {
                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e2);
                        overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                    }
                }
                else if (overallOperationResult == IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED) {
                    overallOperationResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                }
                else {
                    if (overallOperationResult != IEdoIntegrationSendProcessor.ProcessingResult.FAILED) {
                        continue;
                    }
                    overallOperationResult = IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
                }
            }
        }
        if (hasChanges) {
            if (cumulativeState) {
                try {
                    final Map<String, Object> processingArgsParams3 = new HashMap<String, Object>();
                    if (this.processCummulativeState(session, operationContext, this.getService(), document, cumulativeStateSystemCode, processingArgsParams3, documentContext) < 10) {
                        overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                    }
                    else {
                        overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS);
                    }
                }
                catch (final DiadocEdoIntegrationException e3) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e3);
                    overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                }
                catch (final DiadocSdkException e4) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Unexpected", (Throwable)e4);
                    overallOperationResult = overallOperationResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS);
                }
            }
            else if (overallOperationResult == IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED) {
                overallOperationResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
            }
            else if (overallOperationResult == IEdoIntegrationSendProcessor.ProcessingResult.FAILED) {
                overallOperationResult = IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
            }
        }
        return overallOperationResult;
    }
    
    @Override
    public void close() {
        this.connectionCache.clear();
    }
    
    public void setCommonEvent(final String value) {
        this.commonEvent = value;
    }
    
    public String getCommonEvent() {
        return this.commonEvent;
    }
    
    @Override
    public void setContentEvent(final String value) {
        this.contentEvent = value;
    }
    
    @Override
    public String getContentEvent() {
        return this.contentEvent;
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
