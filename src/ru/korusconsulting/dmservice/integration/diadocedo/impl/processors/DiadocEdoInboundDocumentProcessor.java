// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.event.IEventAction;
import ru.korusconsulting.service.IEventHandlerService;
import ru.korusconsulting.dmservice.event.actions.documents.BreakResult;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import Diadoc.Api.Proto.Documents.DocumentZipProtos;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationRetryAfterException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import java.nio.file.StandardCopyOption;
import java.nio.file.CopyOption;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import org.apache.commons.io.FilenameUtils;
import ru.korusconsulting.dmservice.utils.JsonToPojoTransformer;
import ru.korusconsulting.dmservice.utils.XmlToPojoTransformer;
import java.nio.file.LinkOption;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentAttributeMappingQuery;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentObjectMapping;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentExecutionArgumentValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentEnvironmentVariableValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentEnvParameterValue;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByFixedValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvProperty;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgExecutionArgument;
import java.util.Objects;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable;
import java.util.function.Function;
import java.util.stream.Collectors;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgFixedValue;
import java.util.Iterator;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.utils.ServiceReg;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationAttachmentFileProcessingContext;
import ru.korusconsulting.dmservice.utils.PojoSourceNavigator;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import java.io.IOException;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;
import java.io.File;
import ru.korusconsulting.service.commons.ApplicationProperties;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationInboundDocumentProcessor;

public class DiadocEdoInboundDocumentProcessor extends EdoIntegrationInboundDocumentProcessor<IDiadocEdoOperationContext> implements IDiadocEdoProcessor
{
    private Path tempDirectory;
    
    protected DiadocEdoInboundDocumentProcessor() {
        this.tempDirectory = FileSystems.getDefault().getPath(ApplicationProperties.getProperty("content_dir") + File.separator + UUID.randomUUID(), new String[0]);
        try {
            Files.createDirectories(this.tempDirectory, (FileAttribute<?>[])new FileAttribute[0]);
        }
        catch (final IOException e) {
            throw new ServiceException((Exception)e);
        }
    }
    
    protected Path getTempDirectory() {
        return this.tempDirectory;
    }
    
    public IDiadocEdoIntegrationServiceInternal getService() {
        return (IDiadocEdoIntegrationServiceInternal)super.getService();
    }
    
    public IEdoIntegrationPackageProcessingContext preprocessRequest(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final Map<String, Object> packageData, final IDocument document, final SessionContext ctx) {
        if (processingContext != null) {
            return processingContext;
        }
        final String documentId = String.join(":", (String)PojoSourceNavigator.evaluateFirst((Object)packageData, "/DocumentId/MessageId"), (String)PojoSourceNavigator.evaluateFirst((Object)packageData, "/DocumentId/EntityId"));
        final IEdoIntegrationFileProcessingContext fapc = (IEdoIntegrationFileProcessingContext)new EdoIntegrationAttachmentFileProcessingContext(documentId, (String)null, (String)null, false);
        processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext(fapc);
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Processing inbound package %s with stage [%s]", inboundRequest.getObjectId(), inboundRequest.getRemoteStateProcessingStep()));
        final Map<String, Object> processorArgs = new HashMap<String, Object>();
        processorArgs.putAll(args);
        if (StringUtils.isNotBlank(operationConfig.getDataArgument())) {
            processorArgs.put(operationConfig.getDataArgument(), packageData);
        }
        operationConfig.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)document, processorArgs, ctx));
        this.saveArgumentsState(processingContext, (Map)args, operationConfig.getGlobalEnvironmentsNames());
        return processingContext;
    }
    
    protected boolean processInboundAttributes(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final IObjectModel counteragent, final IObjectModel subjectModel, final List<IEdoIntegrationAttributeMapping> mappingList, final Map<String, String> env, final Map<String, Object> args, final BiConsumer<String, Object> setter, final SessionContext ctx) {
        boolean result = false;
        for (final IEdoIntegrationAttributeMapping mapping : mappingList) {
            if (this.processInboundAttributeMapping(session, operationContext, document, counteragent, subjectModel, mapping, env, args, setter, ctx)) {
                result = true;
            }
        }
        return result;
    }
    
    protected boolean processInboundAttributeMapping(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final IObjectModel counteragent, final IObjectModel subjectModel, final IEdoIntegrationAttributeMapping mapping, final Map<String, String> env, final Map<String, Object> args, final BiConsumer<String, Object> setter, final SessionContext ctx) {
        if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgFixedValue fixedMapping) {
            final String attribute = fixedMapping.getProcArgument();
            final Map.Entry<String, List<Object>> a;
            final Map<String, String> rArgs = args.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends String>)Map.Entry::getKey, a -> {
                final Object patt12942$temp = a.getValue();
                Object o;
                if (patt12942$temp instanceof String) {
                    final String aS = (String)(o = patt12942$temp);
                }
                else {
                    o = "";
                }
                return o;
            }));
            final String value = this.getService().calculateMessage(fixedMapping.getValue(), operationContext, rArgs, ctx);
            if (StringUtils.isNotBlank(attribute)) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value), operationContext.getPropogateLogMode());
                setter.accept(attribute, value);
                return true;
            }
        }
        else {
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable environmentVariable) {
                final String environmentName = environmentVariable.getEnvironmentName();
                final String environmentVariableName = environmentVariable.getEnvironmentVariable();
                final Map<String, Object> environment = args.get(String.format("__%s__environment", environmentName));
                final String value2 = (environment != null) ? Objects.toString(environment.get(environmentVariableName), null) : null;
                final String attribute2 = environmentVariable.getProcArgument();
                setter.accept(attribute2, value2);
                return true;
            }
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgExecutionArgument executingArgument) {
                final String executingArgumentName = executingArgument.getExecutionArgument();
                final String attribute3 = executingArgument.getProcArgument();
                setter.accept(attribute3, args.get(executingArgumentName));
                return true;
            }
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgEnvProperty envParameter) {
                final String attribute = envParameter.getProcArgument();
                setter.accept(attribute, env.get(envParameter.getEnvProperty()));
                return true;
            }
            if (mapping instanceof final IEdoIntegrationAnswerMappingByFixedValue fixedMapping2) {
                final String attribute = fixedMapping2.getAttribute();
                final Map.Entry<String, List<Object>> a;
                final Map<String, String> rArgs = args.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends String>)Map.Entry::getKey, a -> {
                    final Object patt14898$temp = a.getValue();
                    Object o2;
                    if (patt14898$temp instanceof String) {
                        final String aS2 = (String)(o2 = patt14898$temp);
                    }
                    else {
                        o2 = "";
                    }
                    return o2;
                }));
                final String value = this.getService().calculateMessage(fixedMapping2.getValue(), operationContext, rArgs, ctx);
                if (StringUtils.isNotBlank(attribute)) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value), operationContext.getPropogateLogMode());
                    setter.accept(attribute, value);
                    return true;
                }
            }
            else if (mapping instanceof final IDiadocEdoInboundDocumentEnvParameterValue envParameter2) {
                final String attribute = envParameter2.getAttribute();
                final String value3 = env.get(envParameter2.getEnvProperty());
                setter.accept(attribute, value3);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value3), operationContext.getPropogateLogMode());
                if (StringUtils.isNotBlank(envParameter2.getDisplayAttribute()) && StringUtils.isNotBlank(envParameter2.getLinkedDisplayAttribute())) {
                    if (!StringUtils.isNotBlank(value3)) {
                        setter.accept(envParameter2.getDisplayAttribute(), null);
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), null), operationContext.getPropogateLogMode());
                        return true;
                    }
                    final IObjectModel model = ServiceReg.getObjectModelService().getObjectModel(session, value3, ctx);
                    if (model != null) {
                        setter.accept(envParameter2.getDisplayAttribute(), model.getValue(envParameter2.getLinkedDisplayAttribute()));
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), model.getValue(envParameter2.getLinkedDisplayAttribute())), operationContext.getPropogateLogMode());
                        return true;
                    }
                    setter.accept(envParameter2.getDisplayAttribute(), null);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), null), operationContext.getPropogateLogMode());
                    return true;
                }
            }
            else {
                if (mapping instanceof final IDiadocEdoInboundDocumentEnvironmentVariableValue environmentVariable2) {
                    final String environmentName = environmentVariable2.getEnvironmentName();
                    final String environmentVariableName = environmentVariable2.getEnvironmentVariable();
                    final Map<String, Object> environment = args.get(String.format("__%s__environment", environmentName));
                    final String value2 = (environment != null) ? Objects.toString(environment.get(environmentVariableName), null) : null;
                    final String attribute2 = environmentVariable2.getAttribute();
                    setter.accept(attribute2, value2);
                    return true;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentExecutionArgumentValue executingArgument2) {
                    final String executingArgumentName = executingArgument2.getExecutionArgument();
                    final String attribute3 = executingArgument2.getAttribute();
                    setter.accept(attribute3, args.get(executingArgumentName));
                    return true;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentObjectMapping objectMapping) {
                    if (objectMapping.getLinkType() != null) {}
                    return true;
                }
                Map<String, List<Object>> params = null;
                Map.Entry<String, List<Object>> a = null;
                if (mapping instanceof final IDiadocEdoInboundDocumentAttributeMappingQuery queryMapping) {
                    params = new HashMap<String, List<Object>>();
                    final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList = queryMapping.getProcedureArgs();
                    this.processInboundAttributes(session, null, document, counteragent, subjectModel, procedureMappingArgsList, env, args, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
                    String query = queryMapping.getQuery();
                    final Iterator<Map.Entry<String, List<Object>>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        a = iterator.next();
                        query = ru.korusconsulting.service.integration.StringUtils.replaceInString(query, (String)a.getKey(), (a.getValue() != null && a.getValue().size() > 0) ? a.getValue().get(0).toString() : "");
                    }
                    final String queryF = query;
                    final ShardSessionRunner<String> shardSessionRunner = new ShardSessionRunner<String>() {
                        public String run() {
                            return ServiceReg.getQueryService().getFirstValueFromQuery(session, queryF, ctx);
                        }
                    };
                    final String value4 = (String)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
                    final String attribute4 = queryMapping.getAttribute();
                    if (StringUtils.isNotBlank(attribute4)) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute4, value4), operationContext.getPropogateLogMode());
                        setter.accept(attribute4, value4);
                        if (StringUtils.isNotBlank(queryMapping.getDisplayAttribute()) && StringUtils.isNotBlank(queryMapping.getLinkedDisplayAttribute())) {
                            if (StringUtils.isNotBlank(value4)) {
                                final IObjectModel model2 = ServiceReg.getObjectModelService().getObjectModel(session, value4, ctx);
                                if (model2 != null) {
                                    setter.accept(queryMapping.getDisplayAttribute(), model2.getValue(queryMapping.getLinkedDisplayAttribute()));
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), model2.getValue(queryMapping.getLinkedDisplayAttribute())), operationContext.getPropogateLogMode());
                                }
                                else {
                                    setter.accept(queryMapping.getDisplayAttribute(), null);
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), null));
                                }
                            }
                            else {
                                setter.accept(queryMapping.getDisplayAttribute(), null);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), null));
                            }
                        }
                    }
                    return false;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentMappingProcedure procedureMapping) {
                    final String procedureClass = procedureMapping.getProcedureClass();
                    final String attribute3 = procedureMapping.getAttribute();
                    final Map<String, List<Object>> params2 = new HashMap<String, List<Object>>();
                    final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList2 = procedureMapping.getProcedureArgs();
                    this.processInboundAttributes(session, operationContext, document, counteragent, subjectModel, procedureMappingArgsList2, env, args, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
                    return true;
                }
                if (mapping != null) {
                    if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                        throw new IllegalArgumentException(String.format("Unimplemented mapping type %s", mapping.getClass().getName()));
                    }
                    IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Unimplemented mapping type %s", mapping.getClass().getName()));
                }
            }
        }
        return false;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processResponseArchive(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IDocument document, final Function<String, IContentContainer> contentContainerProvider, final SessionContext ctx) throws DiadocEdoIntegrationException {
        class ArchiveProcessingCache
        {
            private Path file;
            private Path extracted;
            private Map<String, Path> filesList;
            final /* synthetic */ DiadocEdoInboundDocumentProcessor this$0 = DiadocEdoInboundDocumentProcessor.this;
            
            ArchiveProcessingCache() {
                this.filesList = null;
            }
            
            public Path getFile() {
                return this.file;
            }
            
            public void setFile(final Path file) {
                this.file = file;
            }
            
            public Path getExtracted() {
                return this.extracted;
            }
            
            public void setExtracted(final Path extracted) {
                this.extracted = extracted;
            }
            
            public Map<String, Path> getFilesList() {
                return this.filesList;
            }
            
            public void setFilesList(final Map<String, Path> filesList) {
                this.filesList = filesList;
            }
            
            public void addFileToList(final String name, final Path path) {
                this.getFilesList().put(name, path);
            }
        }
        class ArchiveProcessingCacheStore
        {
            private final ArchiveProcessingCache[] store;
            
            ArchiveProcessingCacheStore() {
                this.store = new ArchiveProcessingCache[] { new ArchiveProcessingCache(), new ArchiveProcessingCache() };
            }
            
            public ArchiveProcessingCache get(final boolean full) {
                return this.store[!full];
            }
        }
        final ArchiveProcessingCacheStore store = new ArchiveProcessingCacheStore();
        boolean success = true;
        if (!processingContext.getArchiveFile().isProceed()) {
            try {
                for (final IEdoIntegrationInboundAttachmentProcessingRule attachmentProcessingRule : operationConfig.getAttachmentProcessingRules()) {
                    final boolean isFullDocflow = attachmentProcessingRule.isFullDocflow();
                    switch (attachmentProcessingRule.getAttachmentType()) {
                        case archive: {
                            if (store.get(isFullDocflow).getFile() == null || !Files.isRegularFile(store.get(isFullDocflow).getFile(), new LinkOption[0]) || !Files.isReadable(store.get(isFullDocflow).getFile())) {
                                store.get(isFullDocflow).setFile(null);
                                final Path archiveFile = this.downloadFile(session, operationContext, api, inboundRequest.getMessageEdoId(), inboundRequest.getDocumentEdoId(), document.getObjectId(), document.getFilial(), document.getFilialName(), this.getTempDirectory(), isFullDocflow ? "archive_full.zip" : "archive.zip", isFullDocflow, ctx);
                                store.get(isFullDocflow).setFile(archiveFile);
                            }
                            if (store.get(isFullDocflow).getFile() != null) {
                                final Map<String, Object> environment = new HashMap<String, Object>();
                                final Map<String, Object> processorArgs = new HashMap<String, Object>();
                                processorArgs.putAll(args);
                                if (StringUtils.isNotBlank(attachmentProcessingRule.getEnvironmentName())) {
                                    processorArgs.put(String.format("__%s__environment", attachmentProcessingRule.getEnvironmentName()), environment);
                                }
                                if (StringUtils.isNotBlank(attachmentProcessingRule.getIncomingFileNameArgument())) {
                                    processorArgs.put(attachmentProcessingRule.getIncomingFileNameArgument(), store.get(isFullDocflow).getFile().getFileName().toString());
                                }
                                final Map.Entry<String, Path> a;
                                attachmentProcessingRule.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)null, processorArgs, ctx));
                                if (!StringUtils.isNotBlank(attachmentProcessingRule.getTargetContentGroup())) {
                                    continue;
                                }
                                String fileName = null;
                                if (StringUtils.isNotBlank(attachmentProcessingRule.getTargetFileNameArgument())) {
                                    fileName = environment.get(attachmentProcessingRule.getTargetFileNameArgument());
                                }
                                if (StringUtils.isBlank(fileName)) {
                                    fileName = store.get(isFullDocflow).getFile().getFileName().toString();
                                }
                                this.addContentToGroups(session, (IIntegrationOperationContext)operationContext, document, attachmentProcessingRule.getTargetContentGroup(), String.join(":", isFullDocflow ? "full" : "nofull", inboundRequest.getMessageEdoId(), inboundRequest.getDocumentEdoId()), store.get(isFullDocflow).getFile(), fileName, (Function)contentContainerProvider, ctx);
                                continue;
                            }
                            success = false;
                            continue;
                        }
                        case archive_file: {
                            if (store.get(isFullDocflow).getExtracted() == null || !Files.isDirectory(store.get(isFullDocflow).getExtracted(), new LinkOption[0]) || !Files.isReadable(store.get(isFullDocflow).getExtracted())) {
                                store.get(isFullDocflow).setExtracted(null);
                                if (store.get(isFullDocflow).getFile() == null || !Files.isRegularFile(store.get(isFullDocflow).getFile(), new LinkOption[0]) || !Files.isReadable(store.get(isFullDocflow).getFile())) {
                                    store.get(isFullDocflow).setFile(null);
                                    final Path archiveFile = this.downloadFile(session, operationContext, api, inboundRequest.getMessageEdoId(), inboundRequest.getDocumentEdoId(), document.getObjectId(), document.getFilial(), document.getFilialName(), this.getTempDirectory(), isFullDocflow ? "archive_full.zip" : "archive.zip", isFullDocflow, ctx);
                                    store.get(isFullDocflow).setFile(archiveFile);
                                }
                            }
                            if (store.get(isFullDocflow).getFile() != null) {
                                Map<String, PathInfo> archiveFiles = new HashMap<String, PathInfo>();
                                Map.Entry<String, Path> a = null;
                                if (StringUtils.isNotBlank(attachmentProcessingRule.getPathRegexp()) && !"*".equals(StringUtils.trimToNull(attachmentProcessingRule.getPathRegexp()))) {
                                    if (store.get(isFullDocflow).getFilesList() == null) {
                                        store.get(isFullDocflow).setFilesList(this.listFilesInsideZip(store.get(isFullDocflow).getFile()));
                                    }
                                    final Iterator<Map.Entry<String, Path>> iterator2 = store.get(isFullDocflow).getFilesList().entrySet().iterator();
                                    while (iterator2.hasNext()) {
                                        a = iterator2.next();
                                        if (this.checkRuleApplicability(session, (IIntegrationOperationContext)operationContext, document, attachmentProcessingRule, (String)a.getKey(), ctx)) {
                                            final PathInfo pi = new PathInfo(a.getValue());
                                            archiveFiles.put(a.getKey(), pi);
                                        }
                                    }
                                }
                                else if (store.get(isFullDocflow).getFilesList() != null) {
                                    final Iterator<Map.Entry<String, Path>> iterator3 = store.get(isFullDocflow).getFilesList().entrySet().iterator();
                                    while (iterator3.hasNext()) {
                                        a = iterator3.next();
                                        archiveFiles.put(a.getKey(), new PathInfo(a.getValue()));
                                    }
                                }
                                if (!archiveFiles.isEmpty() && archiveFiles.values().iterator().next().getPath() == null) {
                                    final Path extractedArchivePath = this.getTempDirectory().resolve("archive");
                                    store.get(isFullDocflow).setFilesList(this.unzipSafeWithEncoding(operationContext, store.get(isFullDocflow).getFile(), extractedArchivePath));
                                    final Map<String, PathInfo> retrievedArchiveFiles = new HashMap<String, PathInfo>();
                                    for (final Map.Entry<String, PathInfo> filePath : archiveFiles.entrySet()) {
                                        final Path p = store.get(isFullDocflow).getFilesList().get(filePath.getKey());
                                        if (p != null) {
                                            final PathInfo pi2 = new PathInfo(p);
                                            retrievedArchiveFiles.put(filePath.getKey(), pi2);
                                        }
                                    }
                                    archiveFiles = retrievedArchiveFiles;
                                }
                                for (final Map.Entry<String, PathInfo> filePath2 : archiveFiles.entrySet()) {
                                    final Map<String, Object> environment2 = new HashMap<String, Object>();
                                    final Map<String, Object> processorArgs2 = new HashMap<String, Object>();
                                    processorArgs2.putAll(args);
                                    if (StringUtils.isNotBlank(attachmentProcessingRule.getEnvironmentName())) {
                                        processorArgs2.put(String.format("__%s__environment", attachmentProcessingRule.getEnvironmentName()), environment2);
                                    }
                                    if (StringUtils.isNotBlank(attachmentProcessingRule.getIncomingFileArgument())) {
                                        if ("xml".equals(attachmentProcessingRule.getFileType())) {
                                            processorArgs2.put(attachmentProcessingRule.getIncomingFileArgument(), XmlToPojoTransformer.transformFile(filePath2.getValue().getPath()));
                                        }
                                        else if ("json".equals(attachmentProcessingRule.getFileType())) {
                                            processorArgs2.put(attachmentProcessingRule.getIncomingFileArgument(), JsonToPojoTransformer.transformFile(filePath2.getValue().getPath()));
                                        }
                                    }
                                    if (StringUtils.isNotBlank(attachmentProcessingRule.getIncomingFileNameArgument())) {
                                        processorArgs2.put(attachmentProcessingRule.getIncomingFileNameArgument(), filePath2.getKey());
                                    }
                                    final Map<String, Object> processorArgs;
                                    attachmentProcessingRule.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)null, processorArgs, ctx));
                                    if (StringUtils.isNotBlank(attachmentProcessingRule.getTargetContentGroup())) {
                                        String fileName2 = null;
                                        if (StringUtils.isNotBlank(attachmentProcessingRule.getTargetFileNameArgument())) {
                                            fileName2 = environment2.get(attachmentProcessingRule.getTargetFileNameArgument());
                                        }
                                        if (StringUtils.isBlank(fileName2)) {
                                            fileName2 = FilenameUtils.getName((String)filePath2.getKey());
                                        }
                                        this.addContentToGroups(session, (IIntegrationOperationContext)operationContext, document, attachmentProcessingRule.getTargetContentGroup(), String.join(":", fileName2, inboundRequest.getMessageEdoId(), inboundRequest.getDocumentEdoId()), filePath2.getValue().getPath(), fileName2, (Function)contentContainerProvider, ctx);
                                    }
                                }
                                continue;
                            }
                            continue;
                        }
                    }
                }
            }
            catch (final Exception e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Exception while processign archive file %s", processingContext.getArchiveFile().getId()), (Throwable)e);
                success = false;
            }
        }
        if (success) {
            ServiceReg.getContentService().commitMainContentIntoDocbase(session, document, ctx);
            processingContext.getArchiveFile().setProceed(success);
            inboundRequest.setProcessingContext(processingContext);
            this.getService().commitQueueItemState(session, operationContext, inboundRequest, ctx);
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    public Map<String, Path> listFilesInsideZip(final Path zipPath) throws IOException {
        final Map<String, Path> filePaths = new LinkedHashMap<String, Path>();
        final Charset charset = Charset.forName("CP866");
        try (final ZipFile zipFile = new ZipFile(zipPath.toFile(), charset)) {
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = (ZipEntry)entries.nextElement();
                if (!entry.isDirectory()) {
                    filePaths.put(entry.getName(), null);
                }
            }
        }
        return filePaths;
    }
    
    public Map<String, Path> unzipSafeWithEncoding(final IDiadocEdoOperationContext operationContext, final Path zipFilePath, final Path destDir) throws IOException {
        final Map<String, Path> result = new LinkedHashMap<String, Path>();
        final Charset cp866 = Charset.forName("CP866");
        final Path resolvedDestDir = destDir.toAbsolutePath().normalize();
        try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath.toFile()), cp866)) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                final Path entryPath = resolvedDestDir.resolve(entry.getName()).normalize();
                if (!entryPath.startsWith(resolvedDestDir)) {
                    final String errorEntryPath = entry.getName();
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Zip slip error. File %s out of target archive decompress path", errorEntryPath));
                }
                else {
                    if (entry.isDirectory()) {
                        Files.createDirectories(entryPath, (FileAttribute<?>[])new FileAttribute[0]);
                    }
                    else {
                        Files.createDirectories(entryPath.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
                        Files.copy(zis, entryPath, StandardCopyOption.REPLACE_EXISTING);
                        result.put(entry.getName(), entryPath);
                    }
                    zis.closeEntry();
                    entry = zis.getNextEntry();
                }
            }
        }
        return result;
    }
    
    protected Path downloadFile(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String messageGuid, final String documentGuid, final String documentId, final String filialId, final String filialName, final Path tempDir, final String defaultFilename, final boolean fullDocflow, final SessionContext ctx) throws DiadocEdoIntegrationException, IOException {
        int z = 0;
        while (true) {
            try {
                final DocumentZipProtos.DocumentZipGenerationResult documentZipGenerationResult = DiadocEdoApiMethodLibrary.generateZip(session, this.getService(), operationContext, api, operationContext.getDiadocBoxGuid(session, ctx), messageGuid, documentGuid, fullDocflow, ctx);
                final String zipFileNameOnShelf = documentZipGenerationResult.getZipFileNameOnShelf();
                final byte[] zipOnShelfData = DiadocEdoApiMethodLibrary.shelfDownload(session, this.getService(), operationContext, api, zipFileNameOnShelf, ctx);
                String tmpFilename = zipFileNameOnShelf;
                String[] zipFileNameOnShelfSplitted = StringUtils.split(zipFileNameOnShelf, "/");
                if (zipFileNameOnShelfSplitted.length > 0) {
                    tmpFilename = zipFileNameOnShelfSplitted[zipFileNameOnShelfSplitted.length - 1];
                }
                zipFileNameOnShelfSplitted = StringUtils.split(tmpFilename, "\\");
                if (zipFileNameOnShelfSplitted.length > 0) {
                    tmpFilename = zipFileNameOnShelfSplitted[zipFileNameOnShelfSplitted.length - 1];
                }
                if (StringUtils.isEmpty(tmpFilename)) {
                    tmpFilename = "archive.zip";
                }
                if (tmpFilename.length() > 64) {
                    tmpFilename = String.join("_", tmpFilename.substring(0, 34), tmpFilename.substring(tmpFilename.length() - 20));
                }
                Path archiveFile = tempDir.resolve(tmpFilename);
                Label_0319: {
                    if (Files.exists(archiveFile, new LinkOption[0])) {
                        final String baseName = FilenameUtils.getBaseName(tmpFilename);
                        final String extension = FilenameUtils.getExtension(tmpFilename);
                        for (int j = 0; j != 2147483646; ++j) {
                            final String newFileName = baseName + "(" + String.valueOf(j) + ")" + "." + extension;
                            archiveFile = tempDir.resolve(newFileName);
                            if (!Files.exists(archiveFile, new LinkOption[0])) {
                                break Label_0319;
                            }
                        }
                        archiveFile = Files.createTempFile(tempDir, baseName, extension, (FileAttribute<?>[])new FileAttribute[0]);
                    }
                }
                try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archiveFile.toFile()))) {
                    bos.write(zipOnShelfData, 0, zipOnShelfData.length);
                }
                return archiveFile;
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
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processResponseForDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IDocument document, final IObjectModel counteragent, final SessionContext ctx) {
        final Map<String, String> env = new HashMap<String, String>();
        if (inboundRequest != null) {
            env.put("filial_id", inboundRequest.getFilialId());
            env.put("filial_name", inboundRequest.getFilialName());
            env.put("document_guid", inboundRequest.getDocumentEdoId());
        }
        else {
            env.put("filial_id", document.getFilial());
            env.put("filial_name", document.getFilialName());
        }
        final int pageCount = 0;
        env.put("page_count", String.valueOf(pageCount));
        final int appCount = 0;
        env.put("apps_count", String.valueOf(appCount - 1));
        final String signer = null;
        env.put("signer", signer);
        final List attributesMapping;
        final List<IEdoIntegrationAttributeMapping> mappingList = attributesMapping = operationConfig.getAttributesMapping();
        final Map<String, String> env2 = env;
        Objects.requireNonNull(document);
        this.processInboundAttributes(session, operationContext, document, counteragent, (IObjectModel)document, attributesMapping, env2, args, document::setValue, ctx);
        final List<IEdoIntegrationAttributeMapping> mappingProceduresList = operationConfig.getMappingProcedures();
        for (final IEdoIntegrationAttributeMapping mapping : mappingProceduresList) {
            if (mapping instanceof final IDiadocEdoInboundDocumentMappingProcedure procedureMapping) {
                final String procedureClass = procedureMapping.getProcedureClass();
                final Map<String, List<Object>> params = new HashMap<String, List<Object>>();
                final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList = procedureMapping.getProcedureArgs();
                this.processInboundAttributes(session, operationContext, document, counteragent, (IObjectModel)document, procedureMappingArgsList, env, args, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
            }
        }
        ServiceReg.getDocumentRouteService().commit(session, document, ctx);
        final String postActionsConfigId = operationConfig.getPostActionsId();
        if (StringUtils.isNotBlank(postActionsConfigId)) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Postactions %s call requested", postActionsConfigId));
            final ConfigElement postActionsConfig = ServiceReg.getConfigService().lookupElement("postactions[id=" + postActionsConfigId, ctx);
            if (postActionsConfig != null) {
                final Map<String, Object> newArgs = new HashMap<String, Object>();
                newArgs.putAll(args);
                newArgs.putIfAbsent("document", document);
                newArgs.put("auto_process_action", "true");
                final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
                final ConfigElement actionsCfg = postActionsConfig.getFirstChild("actions");
                if (actionsCfg != null) {
                    final Map<String, String> eventParams = new HashMap<String, String>();
                    for (final String key : ctx.getAttributeNames()) {
                        eventParams.put(key, ctx.getString(key));
                    }
                    eventParams.put("auto_process_action", "true");
                    for (final ConfigElement actionCfg : actionsCfg.getChildren()) {
                        final IEventAction action = eventHandlerService.createAction(actionCfg.getAttributeValue("name"), ctx);
                        if (action != null) {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Call event action %s", actionCfg.getAttributeValue("name")));
                            action.setLevel(0);
                            if ("true".equals(action.getConfig().getAttributeValue("useaslinkconfig"))) {
                                action.setLinkConfig(action.getConfig());
                            }
                            else {
                                action.setLinkConfig(actionCfg);
                            }
                            action.setEventName("diadoc_inbound_post_action");
                            action.setEventParams((Map)eventParams);
                            final Object result = action.tryExecute(session, (Map)newArgs, ctx);
                            if (result instanceof BreakResult) {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Action %s is BreakResult actions. Stop actions list processing", actionCfg.getAttributeValue("name")));
                                break;
                            }
                            continue;
                        }
                        else {
                            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("event action %s is not found", actionCfg.getAttributeValue("name")));
                        }
                    }
                }
                else {
                    IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Actions list for postactions %s not found", postActionsConfigId));
                }
            }
            else {
                IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Postactions %s not found", postActionsConfigId));
            }
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    public void close() {
    }
    
    class PathInfo
    {
        private Path path;
        
        public Path getPath() {
            return this.path;
        }
        
        public void setPath(final Path value) {
            this.path = value;
        }
        
        protected PathInfo(final Path path) {
            this.path = path;
        }
    }
}
