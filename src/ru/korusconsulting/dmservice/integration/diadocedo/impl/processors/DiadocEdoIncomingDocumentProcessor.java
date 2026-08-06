// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.objectmodel.og.IOgRequestSync;
import java.util.Collections;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.objectmodel.documents.LOCK_TYPE;
import java.util.Date;
import ru.korusconsulting.service.objectmodel.documents.DOCUMENT_STATE;
import java.util.regex.Matcher;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.nio.file.Path;
import ru.korusconsulting.service.IObjectModelService;
import org.apache.commons.lang.StringUtils;
import Diadoc.Api.Proto.Documents.DocumentProtos;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import com.google.protobuf.Message;
import ru.korusconsulting.dmservice.utils.ProtobufToPojoTransformer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.Map;
import java.util.HashMap;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIncomingDocumentRequest;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.diadocedo.configuration.incoming.IDiadocEdoIncomingDocumentReceiveConfiguration;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoIncomingDocumentProcessor;

public class DiadocEdoIncomingDocumentProcessor extends DiadocEdoInboundDocumentProcessor implements IDiadocEdoIncomingDocumentProcessor
{
    public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    
    public IDiadocEdoIncomingDocumentReceiveConfiguration getOperationConfig() {
        return (IDiadocEdoIncomingDocumentReceiveConfiguration)super.getOperationConfig();
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDiadocEdoIncomingDocumentRequest inboundRequest, final SessionContext context) {
        if (this.getOperationConfig() == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Config for processing inbound package not defined. Nothing to do");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.external_id, inboundRequest.getDocumentEdoId());
        operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.box_id, inboundRequest.getDiadocBox());
        IEdoIntegrationPackageProcessingContext processingContext = inboundRequest.getProcessingContext();
        final Map<String, Object> args = new HashMap<String, Object>();
        if (processingContext != null && processingContext.getArguments() != null) {
            args.putAll(args);
        }
        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)this.getOperationConfig(), (Map)args, context);
        Map<String, Object> packageDocument;
        try {
            final DocumentProtos.Document diadoDoc = DiadocEdoApiMethodLibrary.getDocument(session, this.getService(), operationContext, api, inboundRequest.getDiadocBox(), inboundRequest.getMessageId(), inboundRequest.getEntityId(), context);
            packageDocument = ProtobufToPojoTransformer.transform((Message)diadoDoc);
        }
        catch (final DiadocEdoIntegrationException e) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e);
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.preloaded.after(inboundRequest.getRemoteStateProcessingStep()) || processingContext == null) {
            processingContext = this.preprocessRequest(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getOperationConfig(), args, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, packageDocument, null, context);
            if (processingContext == null) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbound package preprocess returns false. Operation delayed.");
                return IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
            }
            inboundRequest.setProcessingContext(processingContext);
            this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.preloaded, context);
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbound package preprocess succeed or not needed.");
        final IDocument document = this.createDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getOperationConfig(), args, inboundRequest, context);
        if (document != null) {
            operationContext.setDocumentId(document.getObjectId());
            operationContext.initDocumentParams(document);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Create document succeed or not needed.");
            if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.archive_downloaded.after(inboundRequest.getRemoteStateProcessingStep())) {
                try {
                    final Map<String, IContentContainer> contentContainerCache = new HashMap<String, IContentContainer>();
                    if (this.processResponseArchive(session, operationContext, api, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getOperationConfig(), args, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, document, contentGroup -> contentContainerCache.computeIfAbsent(contentGroup, a -> (IContentContainer)ServiceReg.getContentService().getContentContainer(session, document.getRootId(), document.getObjectId(), a, context)), context) != IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Process archive content returns false. Operation delayed.");
                        this.getService().commitQueueItemState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, EdoIntegrationProcessingState.RETRY, context);
                        return IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
                    }
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Process archive content succeed or not needed.");
                    this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.all_downloaded, context);
                }
                catch (final DiadocEdoIntegrationException e2) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e2);
                    return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                }
            }
            return this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getOperationConfig(), args, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, document, null, context);
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Create document returns false. Operation delayed.");
        return IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
    }
    
    protected IDocument createDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IDiadocEdoIncomingDocumentRequest inboundRequest, final SessionContext ctx) {
        final IObjectModelService objectModelService = ServiceReg.getObjectModelService();
        IDocument targetDocument = null;
        if (StringUtils.isNotBlank(inboundRequest.getDocumentId())) {
            targetDocument = (IDocument)objectModelService.getObjectModel(session, inboundRequest.getDocumentId(), ctx);
        }
        if (targetDocument != null) {
            ServiceReg.getDocumentRouteService().applyDocumentContext(session, targetDocument, ctx);
            return targetDocument;
        }
        final String sqlAspect = ((IDiadocEdoIncomingDocumentReceiveConfiguration)operationConfig).getFilterConfig();
        if (!StringUtils.isNotBlank(sqlAspect)) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("No aspect query for template search defined in config for document %s, SBIS type is %s", inboundRequest.getObjectId(), inboundRequest.getDiadocType()));
            this.getService().commitQueueItemState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingError.no_template, ctx);
            return null;
        }
        final String queryTemplate = (String)objectModelService.applyXmlAspect(session, sqlAspect, (IObjectModel)inboundRequest, ctx);
        if (!StringUtils.isNotBlank(queryTemplate)) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("No query for template search defined in config for document %s, SBIS type is %s", inboundRequest.getObjectId(), inboundRequest.getDiadocType()));
            this.getService().commitQueueItemState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingError.no_template, ctx);
            return null;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Search template for inbound document for request %s with query [%s]", inboundRequest.getObjectId(), queryTemplate));
        final Object templateId = ServiceReg.getQueryService().getFirstValueFromQuery(session, queryTemplate, ctx);
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("templateId = %s", templateId));
        if (templateId == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to find template for inbound document for request %s with query %s", inboundRequest.getObjectId(), queryTemplate));
            this.getService().commitQueueItemState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingError.no_template, ctx);
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to find template for inbound document for request %s with query %s", inboundRequest.getObjectId(), queryTemplate));
            return null;
        }
        final IDocument templateDoc = (IDocument)objectModelService.getObjectModel(session, templateId.toString(), ctx);
        if (templateDoc == null) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to instantiate template %s for inbound document for request %s", templateId.toString(), inboundRequest.getObjectId()));
            inboundRequest.setProcessingError(IEdoIntegrationInboundDataExchangeItem.ProcessingError.no_template);
            inboundRequest.setProcessingState(EdoIntegrationProcessingState.ERROR);
            objectModelService.commitModel(session, (IObjectModel)inboundRequest, ctx);
            return null;
        }
        targetDocument = this.createDocument(session, null, templateDoc, ctx);
        targetDocument.setExternalId(inboundRequest.getDocumentEdoId());
        ServiceReg.getDocumentRouteService().applyDocumentContext(session, targetDocument, ctx);
        final Map<String, Object> processorArgs = new HashMap<String, Object>();
        processorArgs.putAll(args);
        processorArgs.put("srcobject", templateDoc);
        processorArgs.put("destobject", targetDocument);
        objectModelService.applyXmlAspect(session, "create_document_from_template_init", (IObjectModel)targetDocument, (Map)processorArgs, ctx);
        ServiceReg.getDocumentRouteService().commit(session, targetDocument, ctx);
        inboundRequest.setDocumentId(targetDocument.getObjectId());
        this.saveArgumentsState(processingContext, (Map)args, operationConfig.getGlobalEnvironmentsNames());
        inboundRequest.setProcessingContext(processingContext);
        this.getService().commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.document_created, ctx);
        final IDocument targetDocumentF = targetDocument;
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s created", targetDocumentF.getObjectId()));
        return targetDocument;
    }
    
    protected Map.Entry<String, Path> getArchiveEntry(final Map<String, Path> archiveFilesList, final Pattern sourceRegexPattern) {
        if (sourceRegexPattern == null) {
            throw new IllegalArgumentException("getArchiveEntry must be called with not-null sourceRegexPattern");
        }
        for (final Map.Entry<String, Path> filePath : archiveFilesList.entrySet()) {
            final Matcher m = sourceRegexPattern.matcher(filePath.getKey());
            if (m.find()) {
                return filePath;
            }
        }
        return null;
    }
    
    protected IDocument createDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument templateDoc, final SessionContext ctx) {
        final IObjectModelService modelService = ServiceReg.getObjectModelService();
        final IDocument targetModel = (IDocument)modelService.copyObjectModel(session, (IObjectModel)templateDoc, ctx);
        targetModel.setRootId((String)null);
        targetModel.setProject(ctx.getString("__projectId"));
        targetModel.setProjectName(ctx.getString("__projectName"));
        targetModel.setTemplate(templateDoc.getObjectId());
        targetModel.setTemplateName(templateDoc.getDocumentName());
        targetModel.setOwner(ctx.getString("__orgPositionId"));
        targetModel.setOwnerName(ctx.getString("__orgPositionName"));
        targetModel.setOwnerDep(ctx.getString("__orgPositionDepId"));
        targetModel.setOwnerDepName(ctx.getString("__orgPositionDepName"));
        targetModel.setOwnerPosition(ctx.getString("__orgPositionPositionName"));
        targetModel.setPerformer(ctx.getString("__orgPositionId"));
        targetModel.setPerformerName(ctx.getString("__orgPositionName"));
        targetModel.setPerformerDep(ctx.getString("__orgPositionDepId"));
        targetModel.setPerformerDepName(ctx.getString("__orgPositionDepName"));
        targetModel.setState(DOCUMENT_STATE.PROJECT.asString());
        targetModel.setStateName(ServiceReg.getNLSService().getBundleItem("state_names_bundle", "MSG_PROJECT_STATE", ctx));
        if (templateDoc.getTemplFilial() != null) {
            targetModel.setFilial(templateDoc.getTemplFilial());
            targetModel.setFilialName(templateDoc.getTemplFilialName());
        }
        else {
            targetModel.setFilial(ctx.getString("__filialId"));
            targetModel.setFilialName(ctx.getString("__filialName"));
        }
        targetModel.setModifyDate((Date)null);
        targetModel.setCreationDate((Date)null);
        targetModel.setLockType((LOCK_TYPE)null);
        targetModel.setLockTime((Date)null);
        targetModel.setLockOwner((String)null);
        targetModel.setLockOwnerName((String)null);
        targetModel.setTransaction((String)null);
        targetModel.setCreationDate(new Date());
        return targetModel;
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult processResponseForDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IDocument targetDocument, final IObjectModel counteragent, final SessionContext ctx) {
        if (super.processResponseForDocument(session, operationContext, processingContext, operationConfig, args, inboundRequest, targetDocument, counteragent, ctx) == IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) {
            if (((IDiadocEdoIncomingDocumentReceiveConfiguration)operationConfig).getSendDocumentEvent() != null) {
                final ICstmComponent eventObject = (ICstmComponent)ServiceReg.getObjectModelService().getObjectModel(session, ((IDiadocEdoIncomingDocumentReceiveConfiguration)operationConfig).getSendDocumentEvent().getId(), ctx);
                if (eventObject != null && !this.getService().fireEvent(session, eventObject.getCode(), targetDocument.getObjectId(), targetDocument.getObjectType(), null, Collections.emptyMap(), ctx)) {
                    final String objectIdF = targetDocument.getObjectId();
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. Error running final event %s(%s)", objectIdF, ((IDiadocEdoIncomingDocumentReceiveConfiguration)operationConfig).getSendDocumentEvent().getId(), ((IDiadocEdoIncomingDocumentReceiveConfiguration)operationConfig).getSendDocumentEvent().getValue()));
                }
            }
            this.getService().commitQueueItemRemoteState(session, operationContext, inboundRequest, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed, ctx);
            final Map<String, String> documentParams = new HashMap<String, String>();
            ServiceReg.getIntegrationService().createOgRequestSync(session, targetDocument.getExternalId(), "DIADOCEDO", IOgRequestSync.SYNC_ASPECT.DOCUMENT.name(), targetDocument.getObjectId(), (Map)documentParams, ctx);
            return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
    }
}
