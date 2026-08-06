// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoSendProcessor;
import com.google.protobuf.Message;
import ru.korusconsulting.dmservice.utils.ProtobufToPojoTransformer;
import Diadoc.Api.Proto.LockModeProtos;
import java.util.LinkedHashMap;
import java.util.Optional;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoCounteragentDontHaveCertificateException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoCounteragentNotExistsException;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoCounteragentProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoExternalCounteragentInfo;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.HashMap;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItemLocalProcessingWrapper;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationProcessableException;
import ru.korusconsulting.service.integration.exceptions.IProcessableException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.dmservice.integration.impl.exceptions.IEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoOperationContext;
import java.util.Collections;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.Collection;
import java.util.Arrays;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByFixedValue;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByLinkedObject;
import com.google.protobuf.Descriptors;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByAttribute;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.Date;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationUnparsableResultException;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import java.io.InputStream;
import java.util.Iterator;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainerStorage;
import java.io.IOException;
import ru.korusconsulting.service.exceptions.ServiceException;
import com.google.protobuf.ByteString;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoExchangeContentUnit;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainer;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeContentUnit;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.google.protobuf.GeneratedMessage;

public abstract class DiadocEdoSendDocumentProcessor<T extends GeneratedMessage.Builder<T>> extends DiadocEdoSendDocumentBaseProcessors
{
    private final long TICKS_AT_EPOCH = 621355968000000000L;
    private final long TICKS_PER_MILLISECOND = 10000L;
    
    protected List<IDiadocEdoExchangeContentUnit> getContentUnits(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationExchangeUnit exchangeUnit, final IDocument document, final boolean testMode, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final List<IDiadocEdoExchangeContentUnit> result = new ArrayList<IDiadocEdoExchangeContentUnit>();
        if (exchangeUnit instanceof final IDiadocEdoExchangeCounteragentUnit counteragentUnit) {
            final IEdoIntegrationContentContainerStorage contentListStorage = this.getContent(session, (IIntegrationOperationContext)operationContext, document, ctx);
            for (final IEdoIntegrationContentContainer a : contentListStorage.getContainers()) {
                IDiadocEdoExchangeContentUnit contentUnit = counteragentUnit.getChildren(a.getUid());
                if (contentUnit == null) {
                    contentUnit = new DiadocEdoExchangeContentUnit(session, (IIntegrationOperationContext)operationContext, counteragentUnit, a, ctx);
                    counteragentUnit.addChild((IEdoIntegrationExchangeUnit)contentUnit);
                }
                try (final InputStream fio = a.getContentStream(session)) {
                    final byte[] f = new byte[fio.available()];
                    final int len = fio.read(f);
                    if (len > 0) {
                        final String fileId = DiadocEdoApiMethodLibrary.uploadFileToShelf(session, this.getService(), operationContext, api, f, ctx);
                        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.content_name, a.getName());
                        this.getService().creatContentSyncObject(session, IEdoIntegrationService.EXCHANGE_DIRECTION.OUT, operationContext, document, a.getUid(), a.getName(), null, fileId, ctx);
                        final DiadocMessage_PostApiProtos.SignedContent.Builder sc = DiadocMessage_PostApiProtos.SignedContent.newBuilder();
                        sc.setNameOnShelf(fileId);
                        contentUnit.setNameOnShelf(fileId);
                        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.content_shelf_id, fileId);
                        this.getService().writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UPLOAD_DOCUMENT_TO_SHELF_SUCCESS, null, (IIntegrationOperationContext)operationContext, ctx);
                        if (!testMode) {
                            sc.setSignature(ByteString.copyFrom(a.getSignature()));
                        }
                        contentUnit.setSignedContentBuilder(sc);
                        contentUnit.commit(session, true, ctx);
                        result.add(contentUnit);
                    }
                    if (fio == null) {
                        continue;
                    }
                }
                catch (final IOException e) {
                    throw new ServiceException((Exception)e);
                }
            }
        }
        return result;
    }
    
    protected DiadocMessageInfo postDocumentMessage(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final List<IDiadocEdoExchangeCounteragentUnit> exchangeUnit, final List<IDiadocEdoExchangeContentUnit> contentUnit, final DiadocMessage_PostApiProtos.MessageToPost.Builder messageBuilder, final SessionContext ctx) throws DiadocEdoIntegrationException {
        messageBuilder.setFromBoxId(operationContext.getDiadocBoxGuid(session, ctx));
        if (StringUtils.isNotBlank(operationContext.getActiveDepartment())) {
            messageBuilder.setFromDepartmentId(operationContext.getActiveDepartment());
        }
        IEdoIntegrationExchangeCounteragentUnit mainExchangeUnit;
        IEdoIntegrationExchangeCounteragentUnit proxyExchangeUnit;
        if (exchangeUnit.size() > 1) {
            mainExchangeUnit = (IEdoIntegrationExchangeCounteragentUnit)exchangeUnit.get(1);
            proxyExchangeUnit = (IEdoIntegrationExchangeCounteragentUnit)exchangeUnit.get(0);
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_box, String.join(", ", mainExchangeUnit.getToBox(), proxyExchangeUnit.getToBox()));
        }
        else {
            mainExchangeUnit = (IEdoIntegrationExchangeCounteragentUnit)exchangeUnit.get(0);
            proxyExchangeUnit = null;
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_box, mainExchangeUnit.getToBox());
        }
        messageBuilder.setToBoxId(mainExchangeUnit.getToBox());
        if (StringUtils.isNotBlank(mainExchangeUnit.getToDepartment())) {
            messageBuilder.setToDepartmentId(mainExchangeUnit.getToDepartment());
        }
        if (proxyExchangeUnit != null) {
            messageBuilder.setProxyBoxId(proxyExchangeUnit.getToBox());
            if (StringUtils.isNotBlank(proxyExchangeUnit.getToDepartment())) {
                messageBuilder.setProxyDepartmentId(proxyExchangeUnit.getToDepartment());
            }
        }
        try {
            final DiadocMessageInfo info = this.postDiadocMessage(session, operationContext, api, messageBuilder.build(), ctx);
            if (info.getAttachmentEntities().isEmpty()) {
                throw new DiadocEdoIntegrationUnparsableResultException("Empty answer from diadoc api");
            }
            if (StringUtils.isNotBlank(info.messageId)) {
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.message_id, info.messageId);
            }
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < info.getAttachmentEntities().size(); ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                else {
                    operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.entity_id, info.getAttachmentEntities().get(i).getEntityId());
                }
                sb.append(info.getAttachmentEntities().get(i).getEntityId());
                final String customDocumentId = info.getAttachmentEntities().get(i).getDocumentInfo().getCustomDocumentId();
                for (final IDiadocEdoExchangeContentUnit cu : contentUnit) {
                    if (StringUtils.equals(customDocumentId, cu.getContentUid())) {
                        cu.setDiadocDocumentId(info.getAttachmentEntities().get(i).getEntityId());
                        cu.setDiadocMessageId(info.messageId);
                        cu.setDeliveryTimestamp(info.getTimestampTicks());
                        cu.setDeliveryTime(this.convertTicksToDate(info.getTimestampTicks()));
                        cu.setSendState(EdoIntegrationProcessingState.COMPLETED);
                    }
                }
            }
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.entity_ids, sb.toString());
            return info;
        }
        catch (final DiadocEdoIntegrationException e) {
            throw e;
        }
    }
    
    protected Date convertTicksToDate(final long value) {
        return new Date((value - 621355968000000000L) / 10000L);
    }
    
    protected boolean processAnswerAttributes(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IObjectModel object, final IObjectModel counteragentContainer, final DiadocMessageInfo info, final List<IEdoIntegrationAttributeMapping> answerMapAttributes, final SessionContext ctx) {
        boolean updated = false;
        for (final IEdoIntegrationAttributeMapping a : answerMapAttributes) {
            if (a instanceof final IEdoIntegrationAnswerMappingByAttribute mapping) {
                final String fieldName = mapping.getAnswerFiled();
                if (!StringUtils.isNotBlank(fieldName) || !StringUtils.isNotBlank(mapping.getAttribute())) {
                    continue;
                }
                for (final Map.Entry<Descriptors.FieldDescriptor, Object> p : info.getMessage().getAllFields().entrySet()) {
                    if (StringUtils.equals(p.getKey().getFullName(), fieldName)) {
                        object.setValue(mapping.getAttribute(), (Object)p.getValue().toString());
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Write %s = %s to object %s with type %s", mapping.getAttribute(), p.getValue().toString(), object.getObjectId(), object.getObjectType()));
                        updated = true;
                        break;
                    }
                }
            }
            else if (a instanceof final IEdoIntegrationAnswerMappingByLinkedObject linkedConfig) {
                final IObjectModel subObject = null;
                if (subObject == null || !this.processAnswerAttributes(session, operationContext, subObject, counteragentContainer, info, linkedConfig.getAnswerMapping(), ctx)) {
                    continue;
                }
                ServiceReg.getObjectModelService().commitModel(session, subObject, ctx);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", subObject.getObjectId(), object.getObjectType()), operationContext.getPropogateLogMode());
            }
            else {
                if (a instanceof final IEdoIntegrationAnswerMappingByFixedValue mapping2) {
                    if (StringUtils.isNotBlank(mapping2.getAttribute())) {
                        object.setValue(mapping2.getAttribute(), (Object)mapping2.getValue());
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Write %s = %s to object %s with type %s", mapping2.getAttribute(), mapping2.getValue(), object.getObjectId(), object.getObjectType()));
                        updated = true;
                        continue;
                    }
                }
                if (a instanceof final IEdoIntegrationAnswerMappingByCounteragent linkedConfig2) {
                    if (this.processAnswerAttributes(session, operationContext, counteragentContainer, counteragentContainer, info, linkedConfig2.getAnswerMapping(), ctx)) {
                        ServiceReg.getObjectModelService().commitModel(session, counteragentContainer, ctx);
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", counteragentContainer.getObjectId(), object.getObjectType()), operationContext.getPropogateLogMode());
                        continue;
                    }
                }
                if (a == null) {
                    continue;
                }
                if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                    throw new IllegalArgumentException(String.format("Unimplemented mapping type %s", a.getClass().getName()));
                }
                IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Unimplemented mapping type %s", a.getClass().getName()));
            }
        }
        return updated;
    }
    
    protected DiadocMessageInfo postDiadocMessage(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final DiadocMessage_PostApiProtos.MessageToPost messageToPost, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocMessageInfo info = new DiadocMessageInfo();
        final DiadocMessage_GetApiProtos.Message m = DiadocEdoApiMethodLibrary.postMessage(session, this.getService(), operationContext, api, messageToPost, ctx);
        info.setMessage(m);
        info.setTimestampTicks(m.getTimestampTicks());
        info.setMessageId(m.getMessageId());
        for (final DiadocMessage_GetApiProtos.Entity entity : m.getEntitiesList()) {
            if (entity.hasEntityType()) {
                switch (entity.getEntityType()) {
                    case Attachment: {
                        if (entity.getAttachmentType() != DiadocMessage_GetApiProtos.AttachmentType.AttachmentComment) {
                            info.addAttachmentEntity(entity);
                            continue;
                        }
                        continue;
                    }
                    case Signature: {
                        info.addSignatureEntity(entity);
                    }
                    case UnknownEntityType: {
                        continue;
                    }
                }
            }
        }
        return info;
    }
    
    @Override
    protected IEdoIntegrationSendProcessor.ProcessingResult sendCounteragentItem(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final SessionContext ctx) throws DiadocEdoIntegrationException {
        this.sendDocumentToCounteragents(session, operationContext, api, document, Arrays.asList((IDiadocEdoExchangeCounteragentUnit)counteragentUnit), remoteSignatureMode, ctx);
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    @Override
    protected void prepareCounteragentItemForSending(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IDiadocEdoExchangeCounteragentUnit counteragentUnit, final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        super.prepareCounteragentItemForSending(session, operationContext, api, document, counteragentUnit, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, allowTestCounteragent, ctx);
        this.enrichCounteragentItemInfo(session, operationContext, api, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, counteragentUnit.getCounteragentInn(), counteragentUnit.getCounteragentKpp(), notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, allowTestCounteragent, ctx);
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final Collection<IEdoIntegrationExchangeItem> exchangeItem, final SessionContext ctx) {
        final List<IEdoIntegrationExchangeOperationUnit> operationUnitsForSend = this.getOperations(session, operationContext, document, false, ctx);
        this.logCounteragent((IIntegrationOperationContext)operationContext, "Found all counteragents", document, (List)operationUnitsForSend);
        if (operationUnitsForSend.isEmpty()) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Not found counteragents need sended", operationContext.getPropogateLogMode());
            exchangeItem.forEach(a -> this.markExchangeItemAs(session, a, EdoIntegrationProcessingState.CANCELLED, ctx));
            return IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED;
        }
        this.logCounteragent((IIntegrationOperationContext)operationContext, "Found need sending counteragents", document, (List)operationUnitsForSend);
        final IDiadocEdoConnectionConfig connectionConfig = operationContext.getApiConnectionConfig(session, ctx);
        final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode = IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.parse(this.getOperationConfig().getNotInvitedCounteragentSendMode());
        final boolean ignoreCounteragentCertificateState = connectionConfig.isDontCheckCounteragentSignCertificate() || this.getOperationConfig().isIgnoreCounteragentPkiState();
        final boolean remoteSignatureMode = connectionConfig.isRemoteSignatureMode() || this.getOperationConfig().isRemoteSignatureMode();
        final boolean allowTestCounteragent = connectionConfig.isAllowTestCounteragent();
        int succeedCount = 0;
        for (final IEdoIntegrationExchangeOperationUnit operationUnit : operationUnitsForSend) {
            try (final IDiadocEdoOperationContext oContext = DiadocEdoOperationContext.createOutboundOperationContext(session, (IIntegrationOperationContext)operationContext, DiadocEdoIntegrationOperation.SEND_DOCUMENT, document, true, this.getOperationConfig().getExchangeCategory(), Collections.emptyList(), ctx)) {
                IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                try {
                    this.sendToCounteragentsList(session, oContext, api, operationUnit, document, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, allowTestCounteragent, ctx);
                    ++succeedCount;
                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                    ++succeedCount;
                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                }
                catch (final DiadocEdoIntegrationSubsystemErrorException e) {
                    oContext.setLastError(e.getErrorCode());
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, (IEdoIntegrationSubsystemErrorException)e, ctx);
                }
                catch (final DiadocEdoIntegrationProcessableException e2) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e2);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, (IProcessableException)e2, ctx);
                }
                catch (final DiadocEdoIntegrationException e3) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e3);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, EdoIntegrationSubsystemErrorCode.INTEGRATION_SYSTEM_ERROR, ctx);
                }
                catch (final Throwable e4) {
                    oContext.setLastError(EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", e4);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR, ctx);
                }
                finally {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(null, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_SEND_DOCUMENT_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_SEND_DOCUMENT_WITH_ERRORS, null);
                    this.getService().writeIntegrationLog(oContext, processingResult, true, messagesContainer, ctx);
                    oContext.setLastError((EdoIntegrationSubsystemErrorCode)null);
                }
                if (oContext == null) {
                    continue;
                }
            }
        }
        if (succeedCount == 0) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        if (succeedCount == operationUnitsForSend.size()) {
            return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
    }
    
    protected void processSuccess(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final SessionContext ctx) {
        operationUnit.commit(session, true, ctx);
        for (final IEdoIntegrationExchangeUnit childUnit : operationUnit.getChildren()) {
            if (childUnit instanceof final IEdoIntegrationExchangeCounteragentUnit counteragentUnit) {
                if (!StringUtils.isNotBlank(counteragentUnit.getObjectId())) {
                    continue;
                }
                final IEdoIntegrationExchangeItem itemRaw = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                if (itemRaw == null) {
                    continue;
                }
                final IEdoIntegrationExchangeItemProcessingWrapper item = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw);
                final IObjectModel counteragentContainer = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                final List<IEdoIntegrationInboundDocumentProcessingConfiguration> processors = this.isQueueProcessor() ? this.getOperationConfig().getQueuedStatusProcessing() : this.getOperationConfig().getSendedStatusProcessing();
                if (processors != null && !processors.isEmpty()) {
                    for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : processors) {
                        if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                            final Map<String, Object> args = new HashMap<String, Object>();
                            this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)configuration, (Map)args, ctx);
                            final IEdoIntegrationPackageProcessingContext processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
                            if (!this.processResponseForDocument(session, (IIntegrationOperationContext)operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)configuration, args, (IEdoIntegrationInboundDataExchangeItem)counteragentUnit, document, counteragentContainer, ctx)) {
                                continue;
                            }
                            item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                            item.setStateCategory(configuration.getStateCategory());
                        }
                    }
                }
                else {
                    item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.skip);
                }
                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item.getItem(), ctx);
            }
        }
    }
    
    protected IDiadocEdoExternalCounteragentInfo getCounteragentBoxGuid(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final String inn, final String kpp, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        try (final IDiadocEdoCounteragentProcessor counteragentProcessor = this.getService().createCounteragentProcessor(session, ctx)) {
            return counteragentProcessor.getCounteragentBoxGuid(session, operationContext, api, document, inn, kpp, notInvitedCounteragentSendMode, allowTestCounteragent, ctx);
        }
    }
    
    protected void enrichCounteragentItemInfo(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IDocument document, final String inn, final String kpp, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, this.getService().getCounteragentDetails(inn, kpp, ctx));
        final IDiadocEdoExternalCounteragentInfo info = this.getCounteragentBoxGuid(session, operationContext, api, document, inn, kpp, notInvitedCounteragentSendMode, allowTestCounteragent, ctx);
        if (info == null) {
            throw new DiadocEdoCounteragentNotExistsException(String.format("Failed to find Diadoc.EDO counteragent with inn %s and kpp %s", inn, kpp));
        }
        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_box, info.getBoxId());
        if (info.isRoaming() || info.hasCertificateToSign() || ignoreCounteragentCertificateState) {
            if (info.hasCertificateToSign()) {}
            counteragentUnit.setToBox(info.getBoxId());
            counteragentUnit.setToBoxGuid(info.getBoxIdGuid());
            counteragentUnit.setToDepartment(info.getDepartmentId());
            return;
        }
        throw new DiadocEdoCounteragentDontHaveCertificateException(inn, kpp);
    }
    
    public final void close() {
    }
    
    protected void fillMessageAttachmentAttributes(final IDiadocEdoOperationContext operationContext, final IDocument document, final IDiadocEdoExchangeContentUnit contentUnit, final GeneratedMessage.Builder<T> diadocDoc, final Map<String, Optional<Object>> outgoingAttributes) {
        for (final Map.Entry<String, Optional<Object>> a : outgoingAttributes.entrySet()) {
            if (a.getValue().isPresent()) {
                final Object aV = a.getValue().get();
                final Descriptors.FieldDescriptor fd = diadocDoc.getDescriptorForType().findFieldByName((String)a.getKey());
                if (fd != null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s, Content %s. Set attribute %s value %s", document.getObjectId(), contentUnit.getName(), a.getKey(), a.getValue().orElseGet(() -> "<no value>").toString()));
                    if (fd.getType() == Descriptors.FieldDescriptor.Type.BOOL) {
                        if (aV instanceof final String bS) {
                            diadocDoc.setField(fd, (Object)Boolean.parseBoolean(bS));
                        }
                        else if (aV instanceof final Boolean bB) {
                            diadocDoc.setField(fd, (Object)bB);
                        }
                        else {
                            if (!(aV instanceof List)) {
                                continue;
                            }
                            final List bL = (List)aV;
                            if (bL.isEmpty()) {
                                continue;
                            }
                            final String value = bL.get(0);
                            if (value instanceof String) {
                                final String bS2 = value;
                                diadocDoc.setField(fd, (Object)Boolean.parseBoolean(bS2));
                            }
                            else {
                                final Boolean value2 = bL.get(0);
                                if (!(value2 instanceof Boolean)) {
                                    continue;
                                }
                                final Boolean bB2 = value2;
                                diadocDoc.setField(fd, (Object)bB2);
                            }
                        }
                    }
                    else if (aV instanceof final List aL) {
                        if (aL.isEmpty()) {
                            continue;
                        }
                        diadocDoc.setField(fd, aL.get(0));
                    }
                    else {
                        diadocDoc.setField(fd, aV);
                    }
                }
                else {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Attribute %s not found in diadoc outgoing document", document.getObjectId(), a.getKey()));
                }
            }
        }
    }
    
    protected abstract T getMessageBuilder();
    
    protected abstract void addMessageAttachment(final IDiadocEdoOperationContext p0, final IDocument p1, final IDiadocEdoExchangeContentUnit p2, final Map<String, Optional<Object>> p3, final DiadocMessage_PostApiProtos.MessageToPost.Builder p4, final boolean p5);
    
    public final void sendDocumentToCounteragents(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final List<IDiadocEdoExchangeCounteragentUnit> exchangeUnits, final boolean testSignatureMode, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocMessage_PostApiProtos.MessageToPost.Builder builder = DiadocMessage_PostApiProtos.MessageToPost.newBuilder();
        final List<IDiadocEdoExchangeContentUnit> contentUnits = this.getContentUnits(session, operationContext, api, (IEdoIntegrationExchangeUnit)exchangeUnits.get(0), document, testSignatureMode, ctx);
        final Map<String, Object> args = new HashMap<String, Object>();
        final Map<String, Optional<Object>> outgoingAttributes = new LinkedHashMap<String, Optional<Object>>();
        this.processOutgoingAttributes(session, (IIntegrationOperationContext)operationContext, document, (IEdoIntegrationExchangeCounteragentUnit)null, (a, b, c) -> {
            outgoingAttributes.put(a, Optional.ofNullable(c));
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Prepare outgoing attribute %s with value %s", document.getObjectId(), a, c.toString()));
        }, (Map)args, ctx);
        for (final IDiadocEdoExchangeContentUnit contentUnit : contentUnits) {
            this.addMessageAttachment(operationContext, document, contentUnit, outgoingAttributes, builder, testSignatureMode);
        }
        switch (this.getOperationConfig().getLockMode()) {
            case Full: {
                builder.setLockMode(LockModeProtos.LockMode.Full);
                break;
            }
            case Send: {
                builder.setLockMode(LockModeProtos.LockMode.Send);
                break;
            }
            default: {
                builder.setLockMode(LockModeProtos.LockMode.None);
                break;
            }
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s, all content prepared to send in LockMode.%s mode", document.getObjectId(), builder.getLockMode().name()));
        final DiadocMessageInfo info = this.postDocumentMessage(session, operationContext, api, document, exchangeUnits, contentUnits, builder, ctx);
        final Map<String, Object> messageAsPojo = (info == null || info.getMessage() == null) ? Collections.emptyMap() : ProtobufToPojoTransformer.transform((Message)info.getMessage());
        exchangeUnits.forEach(a -> a.setSendStateInfo(messageAsPojo, EdoIntegrationProcessingState.COMPLETED));
    }
    
    public List<IDiadocEdoSendProcessor.AttrDesc> getFieldNames() {
        final T diadocDoc = this.getMessageBuilder();
        return diadocDoc.getDescriptorForType().getFields().stream().map(a -> new IDiadocEdoSendProcessor.AttrDesc(a.getName(), a.getType().name(), "dd.MM.yyyy", a.isRequired())).toList();
    }
    
    public static class CounteragentBoxInfo
    {
        private final String boxGuid;
        private final String department;
        
        public CounteragentBoxInfo(final String boxGuid, final String department) {
            this.boxGuid = boxGuid;
            this.department = department;
        }
        
        public String getBoxGuid() {
            return this.boxGuid;
        }
        
        public String getDepartment() {
            return this.department;
        }
    }
}
