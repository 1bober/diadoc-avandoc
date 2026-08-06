// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.function.Function;
import ru.korusconsulting.service.IEventHandlerService;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.pki.implementation.KcCertificate;
import ru.korusconsulting.jna.pki.exceptions.KcInvalidKeyException;
import ru.korusconsulting.jna.pki.exceptions.KcInvalidSignatureException;
import ru.korusconsulting.pki.interfaces.IKcCertificate;
import ru.korusconsulting.jna.pki.exceptions.KcSignatureException;
import javax.xml.bind.DatatypeConverter;
import ru.korusconsulting.pki.interfaces.IKcCryptoProvider;
import ru.korusconsulting.pki.implementation.KcSignature;
import ru.korusconsulting.pki.implementation.KcCryptoProvider;
import org.apache.commons.io.FilenameUtils;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.io.IOException;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.HashMap;
import java.io.FileInputStream;
import java.util.Properties;
import ru.korusconsulting.service.objectmodel.documents.IDocSignature;
import java.util.ArrayList;
import java.util.UUID;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainerStorage;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundDocumentSendOperationConfiguration;
import java.util.Iterator;
import ru.korusconsulting.service.IContentService;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import java.util.Map;
import java.util.List;
import org.apache.logging.log4j.util.Supplier;
import ru.korusconsulting.service.objectmodel.cstm.ICstmContentGroup;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IContent;
import java.util.function.Consumer;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import java.nio.file.FileSystems;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import ru.korusconsulting.service.commons.ApplicationProperties;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import java.nio.file.Path;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationProcessor;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public abstract class EdoIntegrationProcessor<T extends IIntegrationOperationContext> implements IEdoIntegrationProcessor<T>
{
    private static final Logger logger;
    private static Path tempContentDir;
    private IEdoIntegrationOperationConfiguration operationConfig;
    private IEdoIntegrationServiceConfiguration serviceConfig;
    private IEdoIntegrationService service;
    
    protected static Path getTempPath() {
        String tempContentDirString = ApplicationProperties.getProperty("content_dir");
        if (tempContentDirString.startsWith("~")) {
            tempContentDirString = System.getProperty("user.home") + File.separator + StringUtils.substring(tempContentDirString, 1);
        }
        return FileSystems.getDefault().getPath(tempContentDirString, new String[0]);
    }
    
    protected EdoIntegrationProcessor() {
    }
    
    public Logger getLogger() {
        return EdoIntegrationProcessor.logger;
    }
    
    protected EdoIntegrationProcessor(final IEdoIntegrationDocumentOperationConfiguration config) {
        this.operationConfig = config;
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration getOperationConfig() {
        return this.operationConfig;
    }
    
    @Override
    public void setOperationConfig(final IEdoIntegrationOperationConfiguration config) {
        this.operationConfig = config;
    }
    
    @Override
    public IEdoIntegrationServiceConfiguration getServiceConfig() {
        return this.serviceConfig;
    }
    
    @Override
    public void setServiceConfig(final IEdoIntegrationServiceConfiguration serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
    
    @Override
    public IEdoIntegrationService getService() {
        return this.service;
    }
    
    @Override
    public void setService(final IEdoIntegrationService service) {
        this.service = service;
    }
    
    public void fetchContentGroupFiles(final DMSShardsClientSession session, final T operationContext, final IDocument model, final String contentGroupId, final Consumer<IContent> processor, final SessionContext ctx) {
        final IContentService contentService = ServiceReg.getContentService();
        final ICstmContentGroup contentGroup = (ICstmContentGroup)ServiceReg.getObjectModelService().getObjectModel(session, StringUtils.trimToNull(contentGroupId), ctx);
        final String contentGroupName = (contentGroup != null) ? StringUtils.trimToNull(contentGroup.getCode()) : null;
        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Trying to find content for group %s", model.getObjectId(), contentGroupName)), null, operationContext.getPropogateLogMode());
        if (contentGroupName == null || "main".equals(contentGroupName)) {
            if (model.getValue("contents") != null) {
                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has value", model.getObjectId())), null, operationContext.getPropogateLogMode());
                final List<IContent> contents = (List<IContent>)model.getValue("contents");
                for (final IContent content : contents) {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content.getObjectId())), null, operationContext.getPropogateLogMode());
                    processor.accept(content);
                }
            }
            else {
                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has no value. Use getDocumentContent mode", model.getObjectId())), null, operationContext.getPropogateLogMode());
                final List<IContent> contents = contentService.getDocumentContent(session, model.getObjectId(), ctx);
                for (final IContent content : contents) {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content.getObjectId())), null, operationContext.getPropogateLogMode());
                    processor.accept(content);
                }
            }
        }
        else if (model.getValue("content_containers") != null) {
            operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. IContentService.CONTENT_CONTAINERS_OBJECT_MODEL_ATTR has value", model.getObjectId())), null, operationContext.getPropogateLogMode());
            final Map<String, IContentContainer> containers = (Map<String, IContentContainer>)model.getValue("content_containers");
            IContentContainer container = containers.get(contentGroupName);
            if (container == null) {
                container = (IContentContainer)contentService.getContentContainer(session, model.getRootId(), model.getObjectId(), contentGroupName, ctx);
            }
            if (container != null) {
                final IContentContainer containerF = container;
                if (container.getValue("contents") != null) {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Container %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has value", model.getObjectId(), containerF.getObjectId())), null, operationContext.getPropogateLogMode());
                    final List<IContent> contents2 = (List<IContent>)container.getValue("contents");
                    for (final IContent content2 : contents2) {
                        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content2.getObjectId())), null, operationContext.getPropogateLogMode());
                        processor.accept(content2);
                    }
                }
                else {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Container %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has no value. Use getDocumentContent mode", model.getObjectId(), containerF.getObjectId())), null, operationContext.getPropogateLogMode());
                    final List<IContent> contents2 = contentService.getDocumentContent(session, container.getObjectId(), ctx);
                    for (final IContent content2 : contents2) {
                        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content2.getObjectId())), null, operationContext.getPropogateLogMode());
                        processor.accept(content2);
                    }
                }
            }
            else {
                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Content group %s not found", model.getObjectId(), contentGroupName)), null, operationContext.getPropogateLogMode());
            }
        }
        else {
            operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. IContentService.CONTENT_CONTAINERS_OBJECT_MODEL_ATTR has no value. Use getContentContainer mode", model.getObjectId())), null, operationContext.getPropogateLogMode());
            final IDocument container2 = contentService.getContentContainer(session, model.getRootId(), model.getObjectId(), contentGroupName, ctx);
            if (container2 != null) {
                final IDocument containerF2 = container2;
                if (container2.getValue("contents") != null) {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Container %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has value", model.getObjectId(), containerF2.getObjectId())), null, operationContext.getPropogateLogMode());
                    final List<IContent> contents3 = (List<IContent>)container2.getValue("contents");
                    for (final IContent content3 : contents3) {
                        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content3.getObjectId())), null, operationContext.getPropogateLogMode());
                        processor.accept(content3);
                    }
                }
                else {
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Container %s. IContentService.CONTENTS_OBJECT_MODEL_ATTR has no value. Use getDocumentContent mode", model.getObjectId(), containerF2.getObjectId())), null, operationContext.getPropogateLogMode());
                    final List<IContent> contents3 = contentService.getDocumentContent(session, container2.getObjectId(), ctx);
                    for (final IContent content3 : contents3) {
                        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Found content with id %s", model.getObjectId(), content3.getObjectId())), null, operationContext.getPropogateLogMode());
                        processor.accept(content3);
                    }
                }
            }
            else {
                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Content group %s not found", model.getObjectId(), contentGroupName)), null, operationContext.getPropogateLogMode());
            }
        }
    }
    
    public IEdoIntegrationContentContainerStorage getNativeContent(final DMSShardsClientSession session, final T operationContext, final IEdoIntegrationOutboundDocumentSendOperationConfiguration outboundConfiguration, final IDocument model, final SessionContext ctx) {
        final IEdoIntegrationContentContainerStorage storage = new EdoIntegrationContentContainerStorage();
        final Path tempDir = EdoIntegrationProcessor.tempContentDir.resolve(UUID.randomUUID().toString());
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
                operationContext.getLogWriter().error(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Error while fetching signature file %s", model.getObjectId(), docSignature.getObjectId())), null, operationContext.getPropogateLogMode());
            }
        }
        for (final IDictionaryElementConfiguration cc : outboundConfiguration.getContentGroups()) {
            this.fetchContentGroupFiles(session, operationContext, model, cc.getId(), content -> {
                docSignatureStrings.iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final Map<String, SignatureInfo> docSignature2 = iterator2.next();
                    final SignatureInfo signature = docSignature2.get(content.getObjectId());
                    if (signature != null && StringUtils.isNotBlank(signature.getSignature())) {
                        final Map<String, Object> certificateInfo = ServiceReg.getCipfService().getCertificateInfo(Base64.decodeBase64(signature.getCertificate()), ctx);
                        final String certificateThumbprint = certificateInfo.get("Thumbprint");
                        operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Signature for content with id %s found with certificate %s", model.getObjectId(), content.getObjectId(), certificateThumbprint)), null, operationContext.getPropogateLogMode());
                        storage.getContainers(EdoIntegrationContentContainerStorage.EdoIntegrationContentType.simple).add(new EdoIntegrationDocbaseContentContainer(content.getObjectId(), content, Base64.decodeBase64(signature.getSignature()), certificateThumbprint, tempDir));
                        return;
                    }
                }
                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Signature for content with id %s not found", model.getObjectId(), content.getObjectId())), null, operationContext.getPropogateLogMode());
                return;
            }, ctx);
        }
        return storage;
    }
    
    public IEdoIntegrationContentContainerStorage getArchiveContent(final DMSShardsClientSession session, final T operationContext, final IEdoIntegrationOutboundDocumentSendOperationConfiguration outboundConfiguration, final IDocument model, final SessionContext ctx) {
        final IEdoIntegrationContentContainerStorage storage = new EdoIntegrationContentContainerStorage();
        for (final IDictionaryElementConfiguration cc : outboundConfiguration.getArchiveContentGroups()) {
            this.fetchContentGroupFiles(session, operationContext, model, cc.getId(), content -> {
                try {
                    Path tempDir = EdoIntegrationProcessor.tempContentDir.resolve(UUID.randomUUID().toString());
                    if (!Files.exists(tempDir, new LinkOption[0])) {
                        tempDir = Files.createDirectories(tempDir, (FileAttribute<?>[])new FileAttribute[0]);
                    }
                    if (!Files.isDirectory(tempDir, new LinkOption[0])) {
                        new IOException(String.format("Could not create temporary directory: %s", tempDir.toAbsolutePath().toString()));
                        throw;
                    }
                    else {
                        final Path tempFile = Files.createTempFile(tempDir, content.getObjectId(), "zip", (FileAttribute<?>[])new FileAttribute[0]);
                        if (Files.exists(tempFile, new LinkOption[0])) {
                            Files.delete(tempFile);
                        }
                        ServiceReg.getContentService().fetchContent(session, content.getObjectId(), tempFile.toAbsolutePath().toString(), (SessionContext)null);
                        if (Files.isRegularFile(tempFile, new LinkOption[0])) {
                            final List<Path> files = this.unzipArchive(session, operationContext, model, content, tempFile, ctx);
                            final List<IFileContent> fileContents = this.organizeSignatures(session, operationContext, model, files, ctx);
                            fileContents.iterator();
                            final Iterator iterator2;
                            while (iterator2.hasNext()) {
                                final IFileContent fileContent = iterator2.next();
                                storage.getContainers(EdoIntegrationContentContainerStorage.EdoIntegrationContentType.archived).add(new EdoIntegrationRawContentContainer(FilenameUtils.getName(fileContent.getPath().toString()), FilenameUtils.getName(fileContent.getPath().toString()), fileContent.getPath(), fileContent.getSignature(), fileContent.getCertificateThumbprint()));
                            }
                        }
                        else {
                            new IOException(String.format("Failed to get content file to temporary %s", tempFile.toAbsolutePath().toString()));
                            throw;
                        }
                    }
                }
                catch (final IOException e) {
                    new ServiceException(String.format("Document %s. Error while loading archive content", model.getObjectId()), (Throwable)e);
                    throw;
                }
                return;
            }, ctx);
        }
        return storage;
    }
    
    public IEdoIntegrationContentContainerStorage getContent(final DMSShardsClientSession session, final T operationContext, final IDocument model, final SessionContext ctx) {
        final IEdoIntegrationContentContainerStorage storage = new EdoIntegrationContentContainerStorage();
        final IEdoIntegrationOperationConfiguration operationConfig = this.getOperationConfig();
        if (operationConfig instanceof final IEdoIntegrationOutboundDocumentSendOperationConfiguration outboundConfiguration) {
            final ContentGroupMode contentGroupMode = ContentGroupMode.parseValue(StringUtils.trimToNull(outboundConfiguration.getContentGroupMode()));
            if (contentGroupMode == ContentGroupMode.native_only || contentGroupMode == ContentGroupMode.native_first || contentGroupMode == ContentGroupMode.combine) {
                storage.addAll(this.getNativeContent(session, operationContext, outboundConfiguration, model, ctx));
            }
            if (contentGroupMode == ContentGroupMode.archive_only || contentGroupMode == ContentGroupMode.archive_first || contentGroupMode == ContentGroupMode.combine || (contentGroupMode == ContentGroupMode.native_first && storage.isEmpty())) {
                storage.addAll(this.getArchiveContent(session, operationContext, outboundConfiguration, model, ctx));
            }
            if (contentGroupMode == ContentGroupMode.archive_first && storage.isEmpty()) {
                storage.addAll(this.getNativeContent(session, operationContext, outboundConfiguration, model, ctx));
            }
        }
        else {
            operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Config is %s instance, not %s", model.getObjectId(), (this.getOperationConfig() == null) ? "null" : this.getOperationConfig().getClass().getName(), "IEdoIntegrationOutboundDocumentSendOperationConfiguration")), null, operationContext.getPropogateLogMode());
        }
        return storage;
    }
    
    protected List<IFileContent> organizeSignatures(final DMSShardsClientSession session, final T operationContext, final IDocument model, final List<Path> sourceFiles, final SessionContext ctx) throws IOException {
        final List<IFileContent> result = new ArrayList<IFileContent>();
        final List<byte[]> signatures = new ArrayList<byte[]>();
        if (sourceFiles.size() > 1) {
            KcCryptoProvider provider = null;
            try {
                provider = new KcCryptoProvider((String)null, 81);
                provider.acquireForVerify();
                for (final Path sourceFile : sourceFiles) {
                    KcSignature signature = null;
                    try {
                        signature = new KcSignature((IKcCryptoProvider)provider, true);
                        try (final InputStream is = new FileInputStream(sourceFile.toFile())) {
                            try {
                                final long length = sourceFile.toFile().length();
                                if (length == 0L) {
                                    is.close();
                                    continue;
                                }
                                if (length > 1048576L) {
                                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. File %s is content since size is %d more then signature max size %s", model.getObjectId(), sourceFile.toAbsolutePath().toString(), length, 1048576L)), null, operationContext.getPropogateLogMode());
                                    result.add(FileContent.createContent(sourceFile));
                                    is.close();
                                    continue;
                                }
                                int bufferSize = (int)length;
                                byte[] signatureCandidateContent = new byte[bufferSize];
                                bufferSize = is.read(signatureCandidateContent, 0, bufferSize);
                                if (Base64.isBase64(signatureCandidateContent)) {
                                    signatureCandidateContent = DatatypeConverter.parseBase64Binary(new String(signatureCandidateContent));
                                }
                                signature.updateSignature(signatureCandidateContent, signatureCandidateContent.length);
                                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. File %s is signature", model.getObjectId(), sourceFile.toAbsolutePath().toString())), null, operationContext.getPropogateLogMode());
                                signatures.add(signatureCandidateContent);
                            }
                            catch (final KcSignatureException e) {
                                operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. File %s is content since loading as signature failed with error %s", model.getObjectId(), sourceFile.toAbsolutePath().toString(), e.getMessage())), null, operationContext.getPropogateLogMode());
                                result.add(FileContent.createContent(sourceFile));
                            }
                        }
                    }
                    finally {
                        if (signature != null) {
                            signature.release();
                        }
                    }
                }
                for (final IFileContent content : result) {
                    if (content.getPath() == null) {
                        continue;
                    }
                    int idx;
                    for (idx = 0; idx < signatures.size(); ++idx) {
                        KcSignature signature2 = null;
                        try {
                            signature2 = new KcSignature((IKcCryptoProvider)provider, true);
                            final byte[] signBytes = signatures.get(idx);
                            KcCertificate cer = null;
                            try {
                                signature2.updateSignature(signBytes, signBytes.length);
                                cer = signature2.getSignerCertificate(0);
                                if (cer != null) {
                                    final int bufferSize = 1048576;
                                    try (final InputStream fileContentIS = new FileInputStream(content.getPath().toFile())) {
                                        final byte[] dataFileContent = new byte[bufferSize];
                                        int dataFileRead = 1;
                                        do {
                                            dataFileRead = fileContentIS.read(dataFileContent, 0, bufferSize);
                                            signature2.updateData(dataFileContent, dataFileRead, dataFileRead < bufferSize);
                                        } while (dataFileRead >= bufferSize);
                                    }
                                    signature2.verify((IKcCertificate)cer);
                                    content.setSignature(signBytes);
                                    content.setCertificateThumbprint(cer.getCertInfo().getThumbprint());
                                    break;
                                }
                            }
                            catch (final KcInvalidSignatureException | KcInvalidKeyException ex) {}
                            finally {
                                if (cer != null) {
                                    cer.release();
                                }
                            }
                        }
                        finally {
                            if (signature2 != null) {
                                signature2.release();
                            }
                        }
                    }
                    if (idx >= signatures.size()) {
                        continue;
                    }
                    signatures.remove(idx);
                }
            }
            finally {
                if (provider != null) {
                    provider.release();
                }
            }
        }
        else if (sourceFiles.size() == 1) {
            result.add(FileContent.createContent(sourceFiles.get(0)));
        }
        return result;
    }
    
    protected List<Path> unzipArchive(final DMSShardsClientSession session, final T operationContext, final IDocument model, final IContent content, final Path zipFile, final SessionContext ctx) {
        try {
            final List<Path> result = new ArrayList<Path>();
            Path unzipFolder = zipFile.getParent().resolve(content.getObjectId());
            unzipFolder = Files.createDirectories(unzipFolder, (FileAttribute<?>[])new FileAttribute[0]);
            try (final FileInputStream fis = new FileInputStream(zipFile.toFile());
                 final ZipInputStream zis = new ZipInputStream(fis, StandardCharsets.UTF_8)) {
                for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                    final Path newFile = unzipFolder.resolve(ze.getName());
                    try (final FileOutputStream fos = new FileOutputStream(newFile.toFile())) {
                        int length = 0;
                        final byte[] buff = new byte[1024];
                        while ((length = zis.read(buff)) != -1) {
                            fos.write(buff, 0, length);
                        }
                    }
                    final String filename = ze.getName();
                    operationContext.getLogWriter().trace(this.getLogger(), (Supplier<?>)(() -> String.format("Document %s. Unpack %s from archive", operationContext.getDocumentId(), filename)), null, operationContext.getPropogateLogMode());
                    result.add(newFile);
                }
            }
            return result;
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    protected boolean fireEvent(final DMSShardsClientSession session, final T operationContext, final String eventName, final String objectId, final String objectType, final String ownerId, final Map<String, String> params, final SessionContext context) {
        final SessionContext eventCtx = context.clone();
        eventCtx.set("__type", (Object)objectType);
        eventCtx.set("__objectId", (Object)objectId);
        final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
        final Map<String, String> eventParams = new HashMap<String, String>();
        if (params != null) {
            eventParams.putAll(params);
        }
        eventParams.put("__orgPositionId", ownerId);
        if (!eventHandlerService.fireSyncEvent(session, objectId, eventName, (Map)eventParams, eventCtx)) {
            operationContext.getLogWriter().warn(this.getLogger(), (Supplier<String>)(() -> String.format("Failed to create event %s in context %s", eventName, context.toString())));
            return false;
        }
        return true;
    }
    
    protected final void addContentToGroups(final DMSShardsClientSession session, final T operationContext, final IDocument targetModel, final String contentGroup, final String fileId, final Path file, final String fileName, final Function<String, IContentContainer> contentContainerProvider, final SessionContext ctx) {
        if (contentGroup == null || "main".equals(contentGroup)) {
            if (targetModel.getValue("contents") == null) {
                targetModel.setValue("contents", (Object)new ArrayList());
            }
            IContent content = null;
            if (StringUtils.isNotBlank(fileId)) {
                for (final IContent existingContent : (List)targetModel.getValue("contents")) {
                    if (StringUtils.equals(existingContent.getExternalId(), fileId)) {
                        content = existingContent;
                        break;
                    }
                }
            }
            if (content == null) {
                content = ServiceReg.getContentService().createContent(file.toAbsolutePath().toString(), file.toFile().length(), targetModel.getRootId(), targetModel.getObjectId(), ctx);
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Added content %s to group %s", file.toAbsolutePath().toString(), "main")));
                content.setObjectId(UUID.randomUUID().toString());
                content.setFilePath(file.toAbsolutePath().toString());
                content.setExternalId(fileId);
                content.setOwner(targetModel.getOwner());
                content.setOwnerName(targetModel.getOwnerName());
                if (StringUtils.isNotBlank(fileName)) {
                    content.setName(fileName);
                }
                ((List)targetModel.getValue("contents")).add(0, content);
            }
            else {
                final IContent contentF = content;
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("content with %s allready exists %s", fileId, contentF.getObjectId())));
            }
        }
        else {
            final IContentContainer contentContainer = contentContainerProvider.apply(contentGroup);
            List<IContent> listContentRegGroup;
            if (contentContainer.getValue("contents") != null) {
                listContentRegGroup = (List)contentContainer.getValue("contents");
            }
            else {
                listContentRegGroup = new ArrayList<IContent>();
            }
            IContent content2 = null;
            if (fileId != null) {
                for (final IContent existingContent2 : listContentRegGroup) {
                    if (StringUtils.equals(existingContent2.getExternalId(), fileId)) {
                        content2 = existingContent2;
                        break;
                    }
                }
            }
            if (content2 == null) {
                content2 = ServiceReg.getContentService().createContent(file.toAbsolutePath().toString(), file.toFile().length(), contentContainer.getRootId(), contentContainer.getObjectId(), ctx);
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Added content %s to group %s", file.toAbsolutePath().toString(), contentGroup)));
                content2.setObjectId(UUID.randomUUID().toString());
                content2.setExternalId(fileId);
                content2.setFilePath(file.toAbsolutePath().toString());
                content2.setOwner(targetModel.getOwner());
                content2.setOwnerName(targetModel.getOwnerName());
                if (StringUtils.isNotBlank(fileName)) {
                    content2.setName(fileName);
                }
                listContentRegGroup.add(content2);
            }
            else {
                final IContent contentF2 = content2;
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("content with %s allready exists %s", fileId, contentF2.getObjectId())));
            }
            contentContainer.setValue("contents", (Object)listContentRegGroup);
            Map<String, IContentContainer> containersMap;
            if (targetModel.getValue("content_containers") != null) {
                containersMap = (Map)targetModel.getValue("content_containers");
            }
            else {
                containersMap = new HashMap<String, IContentContainer>();
            }
            containersMap.put(contentContainer.getDocumentName(), contentContainer);
            targetModel.setValue("content_containers", (Object)containersMap);
        }
    }
    
    @Override
    public void prepareProcessingEnvironments(final DMSShardsClientSession session, final T operationContext, final IEdoIntegrationProcessingConfiguration config, final Map<String, Object> args, final SessionContext ctx) {
        if (StringUtils.isNotBlank(config.getGlobalEnvironmentsNames())) {
            final String[] split;
            final String[] envNames = split = StringUtils.split(config.getGlobalEnvironmentsNames(), ",;");
            for (final String envName : split) {
                final Map<String, Object> environment = new HashMap<String, Object>();
                args.putIfAbsent(String.format("__%s__environment", envName), environment);
            }
        }
        if (StringUtils.isNotBlank(config.getLocalEnvironmentsNames())) {
            final String[] split2;
            final String[] envNames = split2 = StringUtils.split(config.getLocalEnvironmentsNames(), ",;");
            for (final String envName : split2) {
                final Map<String, Object> environment = new HashMap<String, Object>();
                args.put(String.format("__%s__environment", envName), environment);
            }
        }
        if (StringUtils.isNotBlank(config.getConnectionInfoEnvironmentsNames())) {
            final Map<String, Object> environment2 = new HashMap<String, Object>();
            args.put(String.format("__%s__environment", config.getConnectionInfoEnvironmentsNames()), environment2);
            this.fillConnectionInfoEnvironment(session, operationContext, environment2, ctx);
        }
    }
    
    static {
        logger = LogManager.getLogger();
        EdoIntegrationProcessor.tempContentDir = getTempPath();
    }
    
    public enum ContentGroupMode
    {
        native_only, 
        archive_only, 
        native_first, 
        archive_first, 
        combine;
        
        public static ContentGroupMode parseValue(final String name) {
            if (name != null) {
                for (final ContentGroupMode v : values()) {
                    if (v.name().equalsIgnoreCase(name)) {
                        return v;
                    }
                }
            }
            return ContentGroupMode.native_only;
        }
    }
}
