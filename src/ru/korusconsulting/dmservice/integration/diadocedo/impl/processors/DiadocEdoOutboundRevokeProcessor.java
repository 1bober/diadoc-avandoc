// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.objectmodel.documents.IContent;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.io.InputStream;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationDocbaseContentContainer;
import org.apache.commons.codec.binary.Base64;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.FileInputStream;
import java.util.Properties;
import ru.korusconsulting.service.objectmodel.documents.IDocSignature;
import ru.korusconsulting.dmservice.integration.impl.processors.SignatureInfo;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationContentContainerStorage;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoApi;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainerStorage;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import com.google.protobuf.ByteString;
import org.apache.commons.io.IOUtils;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainer;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundRevokeSendFileOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundRevokeSendFileOperationConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.event.IEventAction;
import ru.korusconsulting.service.IEventHandlerService;
import Diadoc.Api.httpClient.GeneratedFile;
import java.util.List;
import java.util.Iterator;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.dmservice.event.actions.documents.BreakResult;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import java.io.FileOutputStream;
import Diadoc.Api.Proto.Invoicing.RevocationRequestInfoProtos;
import java.util.Objects;
import ru.korusconsulting.service.objectmodel.IIntegrationExchangeItemBasic;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationRevokeOperationConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundRevokeCreateFileOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundRevokeCreateFileOperationConfiguration;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.UUID;
import java.io.File;
import ru.korusconsulting.service.commons.ApplicationProperties;
import java.nio.file.FileSystems;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiAccountConnection;
import java.util.Map;
import java.nio.file.Path;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoOutboundRevokeProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationProcessor;

public class DiadocEdoOutboundRevokeProcessor extends EdoIntegrationProcessor<IDiadocEdoOperationContext> implements IDiadocEdoOutboundRevokeProcessor
{
    public static final String REVOKE_CREATE_FILE_OPERATION_CONFIG_SECTION_NAME = "outbound_request_create_file_operation";
    public static final String REVOKE_SEND_FILE_OPERATION_CONFIG_SECTION_NAME = "outbound_request_send_file_operation";
    private Path tempDirectory;
    private final Map<String, DiadocEdoApiAccountConnection> connectionCache;
    
    public DiadocEdoOutboundRevokeProcessor() {
        this.tempDirectory = FileSystems.getDefault().getPath(ApplicationProperties.getProperty("content_dir") + File.separator + UUID.randomUUID(), new String[0]);
        this.connectionCache = new HashMap<String, DiadocEdoApiAccountConnection>();
    }
    
    protected Path getTempDirectory() {
        if (!Files.exists(this.tempDirectory, new LinkOption[0])) {
            try {
                Files.createDirectories(this.tempDirectory, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return this.tempDirectory;
    }
    
    public void close() {
    }
    
    public IDiadocEdoIntegrationServiceInternal getService() {
        return (IDiadocEdoIntegrationServiceInternal)super.getService();
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult createRevokeFile(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final String filialId, final String filialName, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection connection = operationContext.getApiConnection(session, this.getService(), ctx);
        if (connection == null) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error while creating revoke file", new Object[0]), (Throwable)new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId())));
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final SessionContext documentContext = ctx.clone();
        ServiceReg.getObjectModelService().applyObjectContext((IObjectModel)document, documentContext);
        Map.Entry<String, String> a = null;
        final IDiadocEdoOutboundRevokeCreateFileOperationConfiguration config = this.getService().getOperationConfiguration(session, operationContext, "outbound_request_create_file_operation", () -> this.getServiceConfig().getOutboundRevokeConfiguration(), DiadocEdoOutboundRevokeCreateFileOperationConfiguration::new, a -> a.getRevokeOperationType() == IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType.CREATE_FILE, documentContext);
        if (config == null) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final Map<String, Object> environment = new HashMap<String, Object>();
        final Map<String, Object> processorArgs = new HashMap<String, Object>();
        if (params != null) {
            final Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                a = iterator.next();
                processorArgs.put(a.getKey(), a.getValue());
            }
        }
        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)config, (Map)processorArgs, ctx);
        config.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)document, processorArgs, documentContext));
        final List<IIntegrationExchangeItemBasic> exchangeItemsList = new ArrayList<IIntegrationExchangeItemBasic>();
        if (exchangeItemsList.isEmpty()) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        for (final IIntegrationExchangeItemBasic exchangeItems : exchangeItemsList) {
            try {
                final Map<String, Object> args = new HashMap<String, Object>();
                args.putAll(processorArgs);
                final String reason = Objects.toString(environment.get(config.getReasonVariableName()), "");
                final RevocationRequestInfoProtos.RevocationRequestInfo revocationInfo = RevocationRequestInfoProtos.RevocationRequestInfo.newBuilder().setComment(reason).build();
                final GeneratedFile generatedFile = connection.getApi().getGenerateClientCustom().generateRevocationRequestXml(exchangeItems.getCounteragentBoxGuid(), exchangeItems.getMessageEdoId(), exchangeItems.getDocumentEdoId(), revocationInfo);
                final byte[] contentToSign = generatedFile.getContent();
                final String fileName = generatedFile.getFileName();
                final Path file = this.getTempDirectory().resolve(fileName);
                try (final FileOutputStream fos = new FileOutputStream(file.toFile())) {
                    fos.write(contentToSign);
                }
                final Map<String, IContentContainer> contentContainerCache = new HashMap<String, IContentContainer>();
                if (StringUtils.isNotBlank(config.getContentGroup())) {
                    this.addContentToGroups(session, (IIntegrationOperationContext)operationContext, document, config.getContentGroup(), exchangeItems.getDocumentEdoId(), file, fileName, contentGroup -> contentContainerCache.computeIfAbsent(contentGroup, a -> (IContentContainer)ServiceReg.getContentService().getContentContainer(session, document.getRootId(), document.getObjectId(), a, ctx)), documentContext);
                }
                if (!StringUtils.isNotBlank(config.getStateCategory()) || !(exchangeItems instanceof IEdoIntegrationExchangeItem)) {
                    continue;
                }
                final IEdoIntegrationExchangeItem eItem = (IEdoIntegrationExchangeItem)exchangeItems;
                eItem.setStateCategory(config.getStateCategory());
                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)eItem, documentContext);
            }
            catch (final Exception e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Error while creating revoke file", (Throwable)e);
            }
        }
        ServiceReg.getContentService().commitMainContentIntoDocbase(session, document, ctx);
        final String postActionsConfigId = config.getPostActionsId();
        if (StringUtils.isNotBlank(postActionsConfigId)) {
            final ConfigElement postActionsConfig = ServiceReg.getConfigService().lookupElement("postactions[id=" + postActionsConfigId, documentContext);
            if (postActionsConfig != null) {
                final Map<String, Object> newArgs = new HashMap<String, Object>();
                newArgs.putAll(processorArgs);
                newArgs.putIfAbsent("document", document);
                final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
                final ConfigElement actionsCfg = postActionsConfig.getFirstChild("actions");
                if (actionsCfg != null) {
                    final Map<String, String> eventParams = new HashMap<String, String>();
                    for (final String key : documentContext.getAttributeNames()) {
                        eventParams.put(key, documentContext.getString(key));
                    }
                    for (final ConfigElement actionCfg : actionsCfg.getChildren()) {
                        final IEventAction action = eventHandlerService.createAction(actionCfg.getAttributeValue("name"), documentContext);
                        if (action == null) {
                            this.getLogger().error(() -> String.format("event action %s is not found", actionCfg.getAttributeValue("name")));
                        }
                        else {
                            action.setLevel(0);
                            if ("true".equals(action.getConfig().getAttributeValue("useaslinkconfig"))) {
                                action.setLinkConfig(action.getConfig());
                            }
                            else {
                                action.setLinkConfig(actionCfg);
                            }
                            action.setEventName("sbis_request_post_action");
                            action.setEventParams((Map)eventParams);
                            final Object result = action.tryExecute(session, (Map)newArgs, documentContext);
                            if (result instanceof BreakResult) {
                                break;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult sendRevokeFile(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final String filialId, final String filialName, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection connection = operationContext.getApiConnection(session, this.getService(), ctx);
        if (connection == null) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error while sending revoke message patch", new Object[0]), (Throwable)new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId())));
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final SessionContext documentContext = ctx.clone();
        ServiceReg.getObjectModelService().applyObjectContext((IObjectModel)document, documentContext);
        Map.Entry<String, String> a = null;
        final IDiadocEdoOutboundRevokeSendFileOperationConfiguration config = this.getService().getOperationConfiguration(session, operationContext, "outbound_request_send_file_operation", () -> this.getServiceConfig().getOutboundRevokeConfiguration(), DiadocEdoOutboundRevokeSendFileOperationConfiguration::new, a -> a.getRevokeOperationType() == IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType.SEND_FILE, documentContext);
        if (config == null) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final Map<String, Object> processorArgs = new HashMap<String, Object>();
        if (params != null) {
            final Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                a = iterator.next();
                processorArgs.put(a.getKey(), a.getValue());
            }
        }
        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)config, (Map)processorArgs, ctx);
        config.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)document, processorArgs, documentContext));
        final Map<String, IIntegrationExchangeItemBasic> exchangeItemsList = new HashMap<String, IIntegrationExchangeItemBasic>();
        if (exchangeItemsList.isEmpty()) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IEdoIntegrationContentContainerStorage content = this.getNativeContent(session, operationContext, config, document, ctx);
        for (final IEdoIntegrationContentContainer c : content.getContainers()) {
            final IIntegrationExchangeItemBasic exchangeItems = exchangeItemsList.get(c.getExternalId());
            if (exchangeItems != null) {
                try {
                    final DiadocEdoApi api = null;
                    final DiadocMessage_PostApiProtos.RevocationRequestAttachment revocationAttachment = DiadocMessage_PostApiProtos.RevocationRequestAttachment.newBuilder().setParentEntityId(c.getUid()).setSignedContent(DiadocMessage_PostApiProtos.SignedContent.newBuilder().setContent(ByteString.copyFrom(IOUtils.toByteArray(c.getContentStream(session)))).setSignature(ByteString.copyFrom(c.getSignature())).build()).build();
                    final DiadocMessage_PostApiProtos.MessagePatchToPostV2 patchToPost = DiadocMessage_PostApiProtos.MessagePatchToPostV2.newBuilder().setBoxId(exchangeItems.getCounteragentBoxGuid()).setMessageId(exchangeItems.getMessageEdoId()).addRevocationRequests(revocationAttachment).build();
                    DiadocEdoApiMethodLibrary.postMessagePatchV4(session, this.getService(), operationContext, api, patchToPost, ctx);
                    return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                }
                catch (final IOException e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error while sending revoke message patch", new Object[0]), (Throwable)e);
                    throw new DiadocEdoIntegrationException(e);
                }
            }
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
    }
    
    public IEdoIntegrationContentContainerStorage getNativeContent(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoOutboundRevokeSendFileOperationConfiguration outboundConfiguration, final IDocument model, final SessionContext ctx) {
        final IEdoIntegrationContentContainerStorage storage = (IEdoIntegrationContentContainerStorage)new EdoIntegrationContentContainerStorage();
        final Path tempDir = getTempPath().resolve(UUID.randomUUID().toString());
        final List<IDocSignature> docSignatures = ServiceReg.getCipfService().getDocSignatures(session, model.getObjectId(), model.getCorrectionsCountForSign(), (String)null, ctx);
        docSignatures.sort((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate()));
        final List<Map<String, SignatureInfo>> docSignatureStrings = new ArrayList<Map<String, SignatureInfo>>();
        for (int i = 0; i < docSignatures.size(); ++i) {
            final IDocSignature docSignature = docSignatures.get(i);
            Map<String, SignatureInfo> docSignatureRaw = null;
            final File f = ServiceReg.getCipfService().fetchDocSignatureFile(session, docSignature, ctx);
            if (f != null && f.exists()) {
                final Properties info = new Properties();
                try (final InputStream is = new FileInputStream(f)) {
                    info.load(is);
                    int idx = 0;
                    while (true) {
                        final String contentId = info.getProperty(String.format("%s.%d", "CONTENT_ID", idx));
                        if (StringUtils.isBlank(contentId)) {
                            break;
                        }
                        if (docSignatureRaw == null) {
                            docSignatureRaw = new HashMap<String, SignatureInfo>();
                        }
                        docSignatureRaw.put(contentId, new SignatureInfo(info.getProperty(String.format("%s.%d", "CONTENT_SIGN", idx)), info.getProperty("USER_CERT")));
                        ++idx;
                    }
                    if (docSignatureRaw != null) {
                        docSignatureStrings.add(docSignatureRaw);
                    }
                }
                catch (final IOException e) {
                    throw new ServiceException(String.format("Document %s. Error while loading signature %s", model.getObjectId(), docSignature.getObjectId()), (Throwable)e);
                }
                finally {
                    f.delete();
                }
            }
            else {
                operationContext.getLogWriter().error(this.getLogger(), () -> String.format("Document %s. Error while fetching signature file %s", model.getObjectId(), docSignature.getObjectId()), (Throwable)null, operationContext.getPropogateLogMode());
            }
        }
        final IDictionaryElementConfiguration cc = outboundConfiguration.getContentGroupStorage();
        if (cc != null) {
            this.fetchContentGroupFiles(session, (IIntegrationOperationContext)operationContext, model, cc.getId(), content -> {
                docSignatureStrings.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Map<String, SignatureInfo> docSignature2 = iterator.next();
                    final SignatureInfo signature = docSignature2.get(content.getObjectId());
                    if (signature != null && StringUtils.isNotBlank(signature.getSignature())) {
                        final Map<String, Object> certificateInfo = ServiceReg.getCipfService().getCertificateInfo(Base64.decodeBase64(signature.getCertificate()), ctx);
                        final String certificateThumbprint = certificateInfo.get("Thumbprint");
                        operationContext.getLogWriter().trace(this.getLogger(), () -> String.format("Document %s. Signature for content with id %s found with certificate %s", model.getObjectId(), content.getObjectId(), certificateThumbprint), (Throwable)null, operationContext.getPropogateLogMode());
                        storage.getContainers(EdoIntegrationContentContainerStorage.EdoIntegrationContentType.simple).add(new EdoIntegrationDocbaseContentContainer(content.getObjectId(), content, Base64.decodeBase64(signature.getSignature()), certificateThumbprint, tempDir));
                        return;
                    }
                }
                operationContext.getLogWriter().trace(this.getLogger(), () -> String.format("Document %s. Signature for content with id %s not found", model.getObjectId(), content.getObjectId()), (Throwable)null, operationContext.getPropogateLogMode());
                return;
            }, ctx);
        }
        return storage;
    }
}
