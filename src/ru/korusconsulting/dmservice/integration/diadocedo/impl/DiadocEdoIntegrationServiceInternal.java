// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import java.util.Optional;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoIncomingDocumentProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoIncomingDocumentProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.incoming.DiadocEdoIncomingDocumentReceiveConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.incoming.IDiadocEdoIncomingDocumentReceiveConfiguration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIncomingDocumentRequest;
import ru.korusconsulting.dmservice.common.AdminSessionRunner;
import Diadoc.Api.Proto.DocumentDirectionProtos;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import Diadoc.Api.events.OrderBy;
import Diadoc.Api.events.MessageType;
import Diadoc.Api.events.DocumentDirection;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;
import ru.korusconsulting.service.integration.diadocedo.configuration.incoming.IDiadocEdoIncomingDocumentReceiveScopedConfiguration;
import java.util.HashSet;
import Diadoc.Api.Proto.CounteragentProtos;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import ru.korusconsulting.dmservice.integration.Utils;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import ru.korusconsulting.service.integration.model.IEdoIntegrationMappingInfo;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import java.util.Arrays;
import org.apache.commons.lang.exception.ExceptionUtils;
import ru.korusconsulting.dmservice.integration.tracing.IntegrationTracing;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoOutboundRevokeProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoOutboundRevokeProcessor;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRevokeFlowConfiguration;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationContentSync;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoStateChangeProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoStateChangeProcessor;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByFilialItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoCounteragentWithCachingProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoCounteragentProcessor;
import org.apache.http.client.HttpResponseException;
import java.util.TreeMap;
import ru.korusconsulting.service.IEventHandlerService;
import java.util.HashMap;
import java.nio.file.FileSystems;
import ru.korusconsulting.service.integration.diadocedo.DiadocEdoModuleProperties;
import java.nio.file.Path;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import ru.korusconsulting.dmservice.integration.NamedParam;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByFixedValue;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByLinkedObject;
import com.google.protobuf.Descriptors;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByAttribute;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocMessageInfo;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoGetRemoteStateProcessor;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationStatusCheckFlowConfiguration;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoGetRemoteStateProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import ru.korusconsulting.service.integration.diadocedo.configuration.flows.IDiadocEdoStatusCheckFlowConfiguration;
import java.util.Properties;
import Diadoc.Api.Proto.OrganizationProtos;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoOrganizationDetails;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordEvent;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationProcessableException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.commons.ICollection;
import java.util.List;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.batchselect.IBatchItemsStorage;
import ru.korusconsulting.service.IObjectModelService;
import ru.korusconsulting.service.IQueryService;
import ru.korusconsulting.dmservice.integration.batchselect.BatchItemsAccumulator;
import ru.korusconsulting.service.integration.combine.IItemsStorage;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import java.util.function.Function;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.combine.DiadocEdoItemsCombinerByDocumentId;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.combine.DiadocEdoItemsCombinerByFilialId;
import ru.korusconsulting.dmservice.integration.batchselect.BatchItemsStorage;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoConfigurationProblemException;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.dmservice.common.TransactionRunner;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import java.util.Collection;
import java.util.Collections;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentSendOperationConfiguration;
import java.util.Objects;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import java.lang.reflect.InvocationTargetException;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoSendProcessor;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentOperationConfiguration;
import java.util.Iterator;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationScopedConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.function.Predicate;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoServiceConfiguration;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;

public abstract class DiadocEdoIntegrationServiceInternal implements IDiadocEdoIntegrationServiceInternal
{
    private static final String INTEGRATION_LOG_FILENAME_PREFIX = "diadoc_edo";
    public static final String INTEGRATION_LOG_ASPECT = "DIADOCEDO";
    private static final String RECEIVE_OPERATION_CONFIG_SECTION_NAME = "incoming_document_receive_operation_processing";
    private static final String SEND_OPERATION_CONFIG_SECTION_NAME = "diadoc_edo_send_operation";
    private static final ObjectMapper logObjectMapper;
    private Logger log;
    
    public DiadocEdoIntegrationServiceInternal() {
        this.log = this.createLogger();
    }
    
    protected Logger createLogger() {
        return LogManager.getLogger();
    }
    
    public Logger getLogger() {
        return this.log;
    }
    
    public String getCstmIntegrationId() {
        return "diadoc_edo_integration";
    }
    
    public IEdoIntegrationServiceConfiguration getServiceConfiguration(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        final ConfigElement cstmIntegrationConfig = ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", this.getCstmIntegrationId()), ctx);
        if (cstmIntegrationConfig == null) {
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to find cstm_integration[id=%s] block in config. System unconfigured.", this.getCstmIntegrationId()));
            return null;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found cstm_integration[%s] config %s", this.getCstmIntegrationId(), cstmIntegrationConfig.asString()));
        final DiadocEdoServiceConfiguration serviceConfig = new DiadocEdoServiceConfiguration(session, null, ctx);
        serviceConfig.unmarshal(session, cstmIntegrationConfig, ctx);
        return (IEdoIntegrationServiceConfiguration)serviceConfig;
    }
    
    @Override
    public <T extends ICstmConfigAdapter> T getOperationConfiguration(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String operationType, final Supplier<IEdoIntegrationFlowConfiguration> flowConfigurationSupplier, final Supplier<T> creator, final Predicate<T> matchChecker, final SessionContext ctx) {
        final IEdoIntegrationFlowConfiguration flowConfig = flowConfigurationSupplier.get();
        if (flowConfig != null) {
            for (final IEdoIntegrationOperationConfiguration documentConfig : flowConfig.getOperationConfigurations()) {
                if (documentConfig instanceof final IEdoIntegrationScopedConfiguration scopedConfig) {
                    final ConfigElement config = ServiceReg.getConfigService().lookupElement(String.format("%s[id=%s]", operationType, scopedConfig.getConfigComponentId()), ctx);
                    if (config != null) {
                        this.getLogger().trace(() -> String.format("%s. Found %s[id=%s] in cstm_integration[%s] config %s", this.getLogPrefix(), operationType, scopedConfig.getConfigComponentId(), this.getCstmIntegrationId(), config.asString()));
                        final T result = creator.get();
                        if (matchChecker != null) {
                            result.unmarshalForCheck(session, config, ctx);
                            if (!matchChecker.test(result)) {
                                continue;
                            }
                        }
                        result.unmarshal(session, config, ctx);
                        return result;
                    }
                    continue;
                }
            }
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s. Failed to find any configuration from [%s] in cstm_integration[%s] with context %n%s", this.getLogPrefix(), flowConfig.getOperationConfigurations().stream().map(a -> a.getConfigComponentId()).collect(Collectors.joining(";")), this.getCstmIntegrationId(), this.logContext(ctx)));
        }
        else {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s. Failed to find getOutboundFlowConfiguration in cstm_integration[%s] with context %n%s", this.getLogPrefix(), this.getCstmIntegrationId(), this.logContext(ctx)));
        }
        return null;
    }
    
    public IDiadocEdoSendProcessor createQueueProcessor(final DMSShardsClientSession session, final IDiadocEdoOutboundDocumentOperationConfiguration documentConfig, final SessionContext ctx) {
        final String processorClassName = documentConfig.getEdoDocumentQueueProcessorClassName();
        if (StringUtils.isBlank(processorClassName)) {
            throw new ServiceException("'Send to queue' processor not defined");
        }
        try {
            final IDiadocEdoSendProcessor processorInstance = (IDiadocEdoSendProcessor)Class.forName(processorClassName).getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            processorInstance.setService((IEdoIntegrationService)this);
            processorInstance.setOperationConfig((IEdoIntegrationOperationConfiguration)documentConfig);
            return processorInstance;
        }
        catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            throw new ServiceException(String.format("Failed to initialize 'send to queue' processor class %s", processorClassName), (Throwable)e);
        }
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult queueDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDocument document, final Map<String, String> params, final SessionContext context) {
        final SessionContext ctx = this.createDocumentSessionContext(session, document, context);
        final IEdoIntegrationServiceConfiguration serviceConfigF = (serviceConfig == null) ? this.getServiceConfiguration(session, operationContext, ctx) : serviceConfig;
        if (serviceConfigF == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final String operationType = "diadoc_edo_send_operation";
        final IEdoIntegrationServiceConfiguration obj = serviceConfigF;
        Objects.requireNonNull(obj);
        final IDiadocEdoOutboundDocumentSendOperationConfiguration operationConfig = this.getOperationConfiguration(session, operationContext, operationType, (Supplier<IEdoIntegrationFlowConfiguration>)obj::getOutboundFlowConfiguration, DiadocEdoOutboundDocumentSendOperationConfiguration::new, null, ctx);
        if (operationConfig == null) {
            this.getLogger().warn(() -> String.format("%s. call getOperationConfiguration failed to find %s section. Returns null", this.getLogPrefix(), "diadoc_edo_send_operation"));
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IDiadocEdoSendProcessor processor = this.createQueueProcessor(session, operationConfig, ctx);
        processor.setOperationConfig((IEdoIntegrationOperationConfiguration)operationConfig);
        processor.setServiceConfig(serviceConfigF);
        return processor.process(session, (IIntegrationOperationContext)operationContext, (Object)null, document, (Collection)Collections.emptyList(), ctx);
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult changeDocumentExchangeState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDocument document, final Map<String, String> params, final SessionContext context) {
        final String eligibleLocalStates = StringUtils.trimToNull((String)params.get("local_states"));
        String[] eligibleLocalStateList;
        if (eligibleLocalStates != null) {
            eligibleLocalStateList = StringUtils.split(eligibleLocalStates);
        }
        else {
            eligibleLocalStateList = null;
        }
        final String desiredLocalState = StringUtils.trimToNull((String)params.get("new_local_states"));
        final String desiredStateCategory = StringUtils.trimToNull((String)params.get("new_state_category"));
        if (StringUtils.isBlank(desiredLocalState) && StringUtils.isBlank(desiredStateCategory)) {
            return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
        }
        String eligibleCategoryQuery;
        if (eligibleLocalStateList != null) {
            StringBuilder sb = null;
            for (final String eligibleLocalStateListEl : eligibleLocalStateList) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                else {
                    sb.append(", ");
                }
                sb.append("'").append(eligibleLocalStateListEl).append("'");
            }
            if (sb != null) {
                eligibleCategoryQuery = String.format(" and ssa_local_state in (%s)", sb.toString());
            }
            else {
                eligibleCategoryQuery = "";
            }
        }
        else {
            eligibleCategoryQuery = "";
        }
        String desiredLocalStateAppend;
        if (StringUtils.isNotBlank(desiredLocalState)) {
            desiredLocalStateAppend = String.format("set ssa_local_state = '%s'", desiredLocalState);
        }
        else {
            desiredLocalStateAppend = "";
        }
        String desiredStateCategoryAppend;
        if (StringUtils.isNotBlank(desiredStateCategory)) {
            desiredStateCategoryAppend = String.format("set ssa_state_category = '%s'", desiredStateCategory);
        }
        else {
            desiredStateCategoryAppend = "";
        }
        final String query = String.format("update nt_edo_integration_exchange_item objects %s %s where sida_document = '%s' and sba_not_actual = false %s and ssa_integration = '%s'", desiredLocalStateAppend, desiredStateCategoryAppend, document.getObjectId(), eligibleCategoryQuery, "DIADOCEDO");
        final ShardSessionRunner<Object> shardSessionRunner = new ShardSessionRunner<Object>() {
            public Object run() {
                final TransactionRunner<Object> runner = new TransactionRunner<Object>() {
                    public Object run() {
                        ServiceReg.getQueryService().executeUpdateQuery(session.getClientSession(DMSShardsManager.SHARD.DICT.name()), query, context);
                        return null;
                    }
                };
                runner.start(session.getClientSession(DMSShardsManager.SHARD.DICT.name()));
                return null;
            }
        };
        shardSessionRunner.run();
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    protected SessionContext newSessionContext(final DMSShardsClientSession session, final IDocument document, final SessionContext parent) {
        if (document == null) {
            return (parent == null) ? new SessionContext() : parent.clone();
        }
        return ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, (parent == null) ? new SessionContext() : parent.clone());
    }
    
    @Override
    public boolean sendDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDocument document, final IEdoIntegrationExchangeItem item, final String senderId, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        if (StringUtils.isNotBlank(senderId)) {
            ServiceReg.getOrgStructureService().updateContextWithOrgPosInfo(session, senderId, ctx);
        }
        final String operationType = "diadoc_edo_send_operation";
        Objects.requireNonNull(serviceConfig);
        final IDiadocEdoOutboundDocumentSendOperationConfiguration operationConfig = this.getOperationConfiguration(session, operationContext, operationType, (Supplier<IEdoIntegrationFlowConfiguration>)serviceConfig::getOutboundFlowConfiguration, DiadocEdoOutboundDocumentSendOperationConfiguration::new, null, ctx);
        if (operationConfig != null) {
            final IEdoIntegrationSendProcessor<IDiadocEdoOperationContext, IDiadocEdoApi> processor = this.createSendProcessor(session, operationConfig, ctx);
            processor.setOperationConfig((IEdoIntegrationOperationConfiguration)operationConfig);
            processor.setServiceConfig(serviceConfig);
            return false;
        }
        throw new DiadocEdoConfigurationProblemException(String.format("Not found suitable outbound flows for this document %s with type %s. Document not sended to DIADOC.EDO", document.getObjectId(), document.getObjectType()));
    }
    
    public IEdoIntegrationSendProcessor<IDiadocEdoOperationContext, IDiadocEdoApi> createSendProcessor(final DMSShardsClientSession session, final IDiadocEdoOutboundDocumentOperationConfiguration documentConfig, final SessionContext ctx) {
        final String processorClassName = documentConfig.getEdoDocumentProcessorClassName();
        try {
            final IDiadocEdoSendProcessor processorInstance = (IDiadocEdoSendProcessor)Class.forName(processorClassName).getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            processorInstance.setService((IEdoIntegrationService)this);
            processorInstance.setOperationConfig((IEdoIntegrationOperationConfiguration)documentConfig);
            return (IEdoIntegrationSendProcessor<IDiadocEdoOperationContext, IDiadocEdoApi>)processorInstance;
        }
        catch (final Exception e) {
            throw new ServiceException(String.format("Failed to initialize message parser %s", processorClassName), (Throwable)e);
        }
    }
    
    protected void checkConfig(final ConfigElement integrationDocumentConfig) {
        if (integrationDocumentConfig == null) {
            throw new ServiceException("No config exists");
        }
    }
    
    public void writeIntegrationLogMessage(final DMSShardsClientSession session, final IIntegrationLogService.TYPE messageType, final String event, final String message, final String documentId, final String filialId, final String filialName, final SessionContext ctx) {
        final IIntegrationLogService logService = ServiceReg.getIntegrationLogService();
        final IIntegrationLogService.IntegrationLogEntry entry = logService.makeIntegrationLogEntry("DIADOCEDO", messageType.name(), event, message, documentId, filialId, filialName, (String)null, ctx);
        if (entry != null) {
            ServiceReg.getIntegrationLogService().addIntegrationLogEntry(entry, ctx);
        }
        else {
            this.getLogger().warn(() -> String.format("Failed to create integration log entry for %s, %s, %s, %s", "DIADOCEDO", messageType.name(), event, message));
        }
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult processQueueForSend(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, IEdoIntegrationServiceConfiguration serviceConfig, final Map<String, String> params, final SessionContext ctx) {
        final ConfigElement cstmIntegrationConfig = ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", "diadoc_edo_integration"), ctx);
        if (cstmIntegrationConfig == null) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Failed to find cstm_integration[id=%s] block in config. System unconfigured.", "diadoc_edo_integration"));
            operationContext.setLastError(EdoIntegrationSubsystemErrorCode.SYSTEM_UNCONFIGURED);
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found %s config %s", "external_integration", ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", "diadoc_edo_integration"), new SessionContext()).asString()), LogPropogateMode.current);
        if (serviceConfig == null) {
            serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
        }
        if (serviceConfig == null) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
            operationContext.setLastError(EdoIntegrationSubsystemErrorCode.SYSTEM_UNCONFIGURED);
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IEdoIntegrationServiceConfiguration serviceConfigF = serviceConfig;
        final IQueryService queryService = ServiceReg.getQueryService();
        final IObjectModelService objectModelService = ServiceReg.getObjectModelService();
        final String categoryFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.CATEGORY, "ssa_state_category");
        final String idsFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS, "sida_document");
        final int step = 25;
        final int start = 0;
        final BatchItemsAccumulator<String, DiadocEdoItemsCombinerByFilialId, IEdoIntegrationExchangeItem> exchangeItemAccumulator = new BatchItemsAccumulator<String, DiadocEdoItemsCombinerByFilialId, IEdoIntegrationExchangeItem>(50, new BatchItemsStorage<DiadocEdoItemsCombinerByFilialId, IEdoIntegrationExchangeItem>() {
            protected DiadocEdoItemsCombinerByFilialId createSubstorage(final String key) {
                return new DiadocEdoItemsCombinerByFilialId(key, (Function<String, IDiadocEdoCombinedByDocumentItem>)DiadocEdoItemsCombinerByDocumentId::new);
            }
        }) {
            protected Collection<IEdoIntegrationExchangeItem> fetchNextPage(final DMSShardsClientSession session, final int index, final SessionContext ctx) {
                final List<IEdoIntegrationExchangeItem> result = new ArrayList<IEdoIntegrationExchangeItem>(this.getBatchSize());
                final String query = String.format("""
                                                   select r_object_id,sida_filial,sida_document,ssa_document_name,ssa_operation_guid,ssa_document_type_name,sida_counteragent_container,ssa_counteragent_name,ssa_counteragent_inn,ssa_counteragent_kpp
                                                   from nt_edo_integration_exchange_item where
                                                   ssa_integration = '%s' and ssa_direction = '%s' and ssa_local_state = '%s' and ssa_item_type = '%s'
                                                   %s %s
                                                   and sba_not_actual = false and sba_main = true ENABLE(RETURN_RANGE %d %d 'sida_filial,sida_document,ssa_operation_guid,r_object_id')
                                                   """, "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), EdoIntegrationProcessingState.QUEUED.name(), EdoIntegrationItemType.document.name(), categoryFilter, idsFilter, start + index * step, start + index * step + step - 1);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Run exchange items query %s", query));
                ICollection col = null;
                try {
                    final ICollection colF;
                    col = (colF = queryService.executeReadQuery(session, query, ctx));
                    while (colF.next()) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Found queue item %s for filial %s document %s / %s with type %s to %s %s %s (%s)", colF.getString("r_object_id"), colF.getString("sida_filial"), colF.getString("sida_document"), colF.getString("ssa_document_name"), colF.getString("ssa_document_type_name"), colF.getString("ssa_counteragent_name"), colF.getString("ssa_counteragent_inn"), colF.getString("ssa_counteragent_kpp"), colF.getString("sida_counteragent_container")));
                        result.add((IEdoIntegrationExchangeItem)objectModelService.getObjectModel(session, colF.getString("r_object_id"), ctx));
                    }
                }
                finally {
                    if (col != null) {
                        col.close();
                    }
                }
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Found %s items", result.size()));
                return result;
            }
            
            protected void processBatch(final DMSShardsClientSession session, final String key, final IBatchItemsStorage<String, DiadocEdoItemsCombinerByFilialId, IEdoIntegrationExchangeItem> batch, final SessionContext ctx) {
                DiadocEdoIntegrationServiceInternal.this.sendFilialDocumentsBatch(session, operationContext, serviceConfigF, batch.getItems(), params, ctx);
            }
            
            protected String extractKey(final DMSShardsClientSession session, final IEdoIntegrationExchangeItem item, final SessionContext ctx) {
                return item.getFilialId();
            }
        };
        exchangeItemAccumulator.execute(session, ctx);
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    @Override
    public SessionContext createDocumentSessionContext(final DMSShardsClientSession session, final IDocument document, final SessionContext ctx) {
        final SessionContext context = (ctx == null) ? new SessionContext() : ctx.clone();
        return (document == null) ? context : ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, context);
    }
    
    @Override
    public SessionContext createFilialSessionContext(final DMSShardsClientSession session, final IFilial filial, final SessionContext ctx) {
        final SessionContext context = (ctx == null) ? new SessionContext() : ctx.clone();
        if (filial != null) {
            context.set("__filialId", (Object)filial.getObjectId());
            context.set("__filialName", (Object)filial.getFilialName());
        }
        return context;
    }
    
    public void sendFilialDocumentsBatch(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContextUp, final IEdoIntegrationServiceConfiguration serviceConfig, final Collection<DiadocEdoItemsCombinerByFilialId> batch, final Map<String, String> params, final SessionContext ctxUp) {
        for (final DiadocEdoItemsCombinerByFilialId filialGroup : batch) {
            final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialGroup.getKey(), ctxUp);
            final SessionContext ctx = this.createFilialSessionContext(session, filial, ctxUp);
            try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createFilialContext(session, operationContextUp, filial, DiadocEdoIntegrationOperation.PROCESS_SEND_QUEUE_FOR_FILIAL, ctx)) {
                IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                try {
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
                    final IDiadocEdoConnectionConfig connectionConfig = operationContext.getApiConnectionConfig(session, ctx);
                    if (connectionConfig == null) {
                        processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                        operationContext.setLastError(EdoIntegrationSubsystemErrorCode.NO_CONNECTION_SETTINGS_ERROR);
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SEND_DOCUMENT_ERROR_NO_CONNECTION_DEFINED, null, (IIntegrationOperationContext)operationContext, ctx);
                    }
                    else {
                        try (final IDiadocEdoApi api = operationContext.getApiConnection(session, this, ctx).getApi()) {
                            int success = 0;
                            int error = 0;
                            for (final IDiadocEdoCombinedByDocumentItem item : filialGroup.getItems()) {
                                try {
                                    IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("processing document %s", item.getKey()));
                                    this.sendDocumentsBatch(session, operationContext, api, serviceConfig, item.getKey(), item.getAllItems(), params, ctx);
                                    ++success;
                                }
                                catch (final DiadocEdoIntegrationProcessableException e) {
                                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e);
                                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                                    ++error;
                                }
                                catch (final DiadocEdoIntegrationException e2) {
                                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e2);
                                    ++error;
                                }
                            }
                            if (error == 0) {
                                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                            }
                            else if (success == 0) {
                                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                            }
                            else {
                                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
                            }
                        }
                    }
                }
                catch (final DiadocEdoIntegrationException e3) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e3);
                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                }
                finally {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                }
                if (operationContext == null) {
                    continue;
                }
            }
        }
    }
    
    public void sendDocumentsBatch(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContextUp, final IDiadocEdoApi api, final IEdoIntegrationServiceConfiguration serviceConfig, final String documentId, final Collection<IEdoIntegrationExchangeItem> exchangeItem, final Map<String, String> params, final SessionContext ctxUp) throws DiadocEdoIntegrationException {
        final IDocument document = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, documentId, ctxUp);
        final SessionContext ctx = this.createDocumentSessionContext(session, document, ctxUp);
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createDocumentContext(session, (IIntegrationOperationContext)operationContextUp, document, IntegrationLogRecordEvent.DIADOCEDO_SEND_DOCUMENT, null, ctx)) {
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
            try {
                final IDiadocEdoOperationContext operationContext2 = operationContext;
                final String operationType = "diadoc_edo_send_operation";
                Objects.requireNonNull(serviceConfig);
                final IDiadocEdoOutboundDocumentSendOperationConfiguration operationConfig = this.getOperationConfiguration(session, operationContext2, operationType, (Supplier<IEdoIntegrationFlowConfiguration>)serviceConfig::getOutboundFlowConfiguration, DiadocEdoOutboundDocumentSendOperationConfiguration::new, null, ctx);
                if (operationConfig == null) {
                    throw new DiadocEdoConfigurationProblemException(String.format("Not found suitable outbound flows for this document %s with type %s. Document not sended to DIADOC.EDO", document.getObjectId(), document.getObjectType()));
                }
                final IEdoIntegrationSendProcessor<IDiadocEdoOperationContext, IDiadocEdoApi> processor = this.createSendProcessor(session, operationConfig, ctx);
                processor.setOperationConfig((IEdoIntegrationOperationConfiguration)operationConfig);
                processor.setServiceConfig(serviceConfig);
                processor.process(session, (IIntegrationOperationContext)operationContext, (Object)api, document, (Collection)exchangeItem, ctx);
            }
            catch (final Throwable e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
            }
        }
    }
    
    @Override
    public void checkLocalDocumentStatus(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final Map<String, String> params, final SessionContext ctx) {
    }
    
    @Override
    public List<IDiadocEdoOrganizationDetails> getOursOrganizationsDetails(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String login, final String password, final String boxId, final boolean loadDepartments, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        final String url = serverConfig.getUrl();
        final String apiKey = serverConfig.getApiKey();
        try {
            final List<IDiadocEdoOrganizationDetails> result = new ArrayList<IDiadocEdoOrganizationDetails>();
            final Properties props = IDiadocEdoConnectionConfig.loadProperties(session, serverConfig.getObjectId(), ctx);
            final DiadocEdoApiConnection connection = new DiadocEdoApiConnection(session, this, operationContext, url, apiKey, login, password, props, ctx);
            final OrganizationProtos.OrganizationList list = DiadocEdoApiMethodLibrary.getMyOrganizations(session, this, operationContext, connection.getApi(), ctx);
            for (final OrganizationProtos.Organization org : list.getOrganizationsList()) {
                record DepartmentRecord(String id, String name, String kpp) {}
                final List<DepartmentRecord> departments = new ArrayList<DepartmentRecord>();
                if (loadDepartments) {
                    final OrganizationProtos.Box a;
                    org.getDepartmentsList().forEach(a -> departments.add(new DepartmentRecord(a.getDepartmentId(), a.getName(), a.getKpp())));
                }
                final Iterator iterator2 = org.getBoxesList().iterator();
                while (iterator2.hasNext()) {
                    final OrganizationProtos.Box a = (OrganizationProtos.Box)iterator2.next();
                    for (final DepartmentRecord d : departments) {
                        if (boxId == null || boxId.equalsIgnoreCase(a.getBoxId()) || boxId.equalsIgnoreCase(a.getBoxIdGuid())) {
                            final IDiadocEdoOrganizationDetails details = new IDiadocEdoOrganizationDetails() {
                                public String getOrgId() {
                                    return org.getOrgId();
                                }
                                
                                public String getName() {
                                    return org.getFullName();
                                }
                                
                                public String getInn() {
                                    return org.getInn();
                                }
                                
                                public String getKpp() {
                                    return d.kpp();
                                }
                                
                                @Override
                                public String getBoxId() {
                                    return a.getBoxIdGuid();
                                }
                                
                                @Override
                                public String getDepartmentId() {
                                    return d.id();
                                }
                                
                                @Override
                                public String getDepartmentName() {
                                    return d.name();
                                }
                            };
                            result.add(details);
                        }
                    }
                    final IDiadocEdoOrganizationDetails details2 = new IDiadocEdoOrganizationDetails() {
                        public String getOrgId() {
                            return org.getOrgId();
                        }
                        
                        public String getName() {
                            return org.getFullName();
                        }
                        
                        public String getInn() {
                            return org.getInn();
                        }
                        
                        public String getKpp() {
                            return org.getKpp();
                        }
                        
                        @Override
                        public String getBoxId() {
                            return a.getBoxIdGuid();
                        }
                        
                        @Override
                        public String getDepartmentId() {
                            return null;
                        }
                        
                        @Override
                        public String getDepartmentName() {
                            return null;
                        }
                    };
                    result.add(details2);
                }
            }
            return result;
        }
        catch (final DiadocEdoIntegrationException e) {
            this.getLogger().error(() -> String.format("Error while reading available boxes for login %s in %s", login, serverConfig.getName()), (Throwable)e);
            return Collections.emptyList();
        }
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult getRemoteDocumentsState(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IEdoIntegrationServiceConfiguration serviceConfig, final Map<String, String> params, final SessionContext ctxUp) {
        if (serviceConfig == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Call getRemoteDocumentState with null serviceConfig. Returns null");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IEdoIntegrationStatusCheckFlowConfiguration statusCheckConfigurationU = serviceConfig.getStatusFlowConfiguration();
        if (!(statusCheckConfigurationU instanceof IDiadocEdoStatusCheckFlowConfiguration)) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Diadoc EDO Integration. Not found any status check flow in dynamic integration config. Nothing to do, exiting...");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IDiadocEdoStatusCheckFlowConfiguration statusCheckConfiguration = (IDiadocEdoStatusCheckFlowConfiguration)statusCheckConfigurationU;
        final String commonEventId = statusCheckConfiguration.getEvent();
        final String contentEventId = statusCheckConfiguration.getContentEvent();
        final Collection<String> affectedFilials = oContext.getConnectedFilialsList(session, ctxUp);
        if (affectedFilials.isEmpty()) {
            IIntegrationOperationContext.warn((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Not found any diadoc connection. Nothing to do, exiting...");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        IEdoIntegrationSendProcessor.ProcessingResult commonResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        final String categoryFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.CATEGORY, "ssa_state_category");
        final String idsFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS, "sida_document");
        for (final String filialId : affectedFilials) {
            IEdoIntegrationSendProcessor.ProcessingResult filialResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctxUp);
            final SessionContext ctx = this.createFilialSessionContext(session, filial, ctxUp);
            try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createFilialContext(session, oContext, filial, DiadocEdoIntegrationOperation.GET_REMOTE_STATUS, ctx)) {
                try {
                    final IDiadocEdoApiBoxConnection apiConnection = operationContext.getApiConnection(session, this, ctx);
                    if (apiConnection == null) {
                        throw new DiadocEdoNoConnectionException(String.format("Failed to get api connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
                    }
                    try (final IDiadocEdoApi api = apiConnection.getApi()) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc EDO Integration. Checking filial %s/%s", filial.getFilialName(), filial.getObjectId()));
                        final int step = 25;
                        final int start = 1;
                        final IQueryService queryService = ServiceReg.getQueryService();
                        final IObjectModelService objectModelService = ServiceReg.getObjectModelService();
                        final IDiadocEdoGetRemoteStateProcessor processor = this.createGetRemoteStateProcessor();
                        processor.setService((IEdoIntegrationService)this);
                        processor.setServiceConfig(serviceConfig);
                        processor.setCommonEvent(commonEventId);
                        processor.setContentEvent(contentEventId);
                        final BatchItemsAccumulator<String, IDiadocEdoCombinedByDocumentItem, IEdoIntegrationExchangeItem> exchangeItemAccumulator = new BatchItemsAccumulator<String, IDiadocEdoCombinedByDocumentItem, IEdoIntegrationExchangeItem>(50, new BatchItemsStorage<IDiadocEdoCombinedByDocumentItem, IEdoIntegrationExchangeItem>() {
                            protected IDiadocEdoCombinedByDocumentItem createSubstorage(final String key) {
                                return new DiadocEdoItemsCombinerByDocumentId(key);
                            }
                        }) {
                            protected Collection<IEdoIntegrationExchangeItem> fetchNextPage(final DMSShardsClientSession session, final int index, final SessionContext ctx) {
                                final List<IEdoIntegrationExchangeItem> result = new ArrayList<IEdoIntegrationExchangeItem>();
                                final String query = String.format("""
                                                                   select r_object_id,sida_document from nt_edo_integration_exchange_item
                                                                   where ssa_integration = '%s' and ssa_direction = '%s' and sba_sended = true and sida_filial = '%s' and ssa_item_type = 'document'
                                                                   %s %s and
                                                                   sba_ready = true and sba_not_actual = false and sba_main = false ENABLE(RETURN_RANGE %d %d 'sida_document,r_object_id')
                                                                   """, "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), filialId, categoryFilter, idsFilter, start + index * step, start + index * step + step - 1);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Run exchange items query %s", query));
                                ICollection col = null;
                                try {
                                    col = queryService.executeReadQuery(session, query, ctx);
                                    while (col.next()) {
                                        result.add((IEdoIntegrationExchangeItem)objectModelService.getObjectModel(session, col.getString("r_object_id"), ctx));
                                    }
                                }
                                finally {
                                    if (col != null) {
                                        col.close();
                                    }
                                }
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Found %s items", result.size()));
                                return result;
                            }
                            
                            protected void processBatch(final DMSShardsClientSession session, final String key, final IBatchItemsStorage<String, IDiadocEdoCombinedByDocumentItem, IEdoIntegrationExchangeItem> batch, final SessionContext ctx) {
                                processor.process(session, operationContext, api, batch.getItems(), filial.getObjectId(), filial.getFilialName(), ctx);
                            }
                            
                            protected String extractKey(final DMSShardsClientSession session, final IEdoIntegrationExchangeItem item, final SessionContext ctx) {
                                return item.getDocumentId();
                            }
                        };
                        exchangeItemAccumulator.execute(session, ctx);
                    }
                    filialResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                }
                catch (final Throwable e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e);
                    filialResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                }
                finally {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, filialResult, true, messagesContainer, ctx);
                }
            }
            commonResult = commonResult.concat(filialResult);
        }
        return commonResult;
    }
    
    public void testCheckDocFlows(final DMSShardsClientSession session, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Found %s config %s", "external_integration", Optional.ofNullable(ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", "diadoc_edo_integration"), new SessionContext())).orElse(new ConfigElement()).asString()));
        final List<String> filials = new ArrayList<String>();
        final IClientSession clientSession = session.getClientSession(DMSShardsManager.getShardNameByContext(ctx));
        try (final ru.wstack.net.client.contentserver.ICollection coll = clientSession.selectQuery("select distinct am.sida_filial from nt_diadoc_edo_account_member am, nt_diadoc_edo_account a, nt_diadoc_edo_server s where am.sida_account=a.r_object_id and a.sida_server=s.r_object_id and am.sba_enabled=true and a.sba_enabled=true and s.sba_enabled=true")) {
            while (coll.next()) {
                filials.add(coll.getString("sida_filial"));
            }
        }
        if (filials.isEmpty()) {
            this.getLogger().trace("Diadoc EDO Integration. Not found any diadoc connection. Nothing to do, exiting...");
            return;
        }
        for (final String filialId : filials) {
            this.getLogger().trace(() -> String.format("Diadoc EDO Integration. Checking filial %s", filialId));
            try (final ru.wstack.net.client.contentserver.ICollection coll2 = clientSession.selectQuery(String.format("select distinct sida_document from nt_edo_integration_exchange_item where sba_is_checked = true and sida_filial='%s'", filialId))) {
                while (coll2.next()) {}
            }
            final ArrayList list = new ArrayList();
        }
    }
    
    protected IDiadocEdoGetRemoteStateProcessor createGetRemoteStateProcessor() {
        return new DiadocEdoGetRemoteStateProcessor();
    }
    
    @Override
    public boolean processAnswerAttributes(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IObjectModel object, final IObjectModel counteragentContainer, final Object dataInfo, final List<IEdoIntegrationAttributeMapping> answerMapAttributes, final Map<String, String> params, final SessionContext ctx) {
        final DiadocMessageInfo info = (DiadocMessageInfo)dataInfo;
        boolean updated = false;
        for (final IEdoIntegrationAttributeMapping a : answerMapAttributes) {
            if (a instanceof final IEdoIntegrationAnswerMappingByAttribute mapping) {
                if (info == null) {
                    continue;
                }
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
                if (subObject == null || !this.processAnswerAttributes(session, operationContext, subObject, counteragentContainer, info, linkedConfig.getAnswerMapping(), params, ctx)) {
                    continue;
                }
                ServiceReg.getObjectModelService().commitModel(session, subObject, ctx);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", subObject.getObjectId(), object.getObjectType()));
            }
            else if (a instanceof final IEdoIntegrationAnswerMappingByFixedValue mapping2) {
                if (!StringUtils.isNotBlank(mapping2.getAttribute())) {
                    continue;
                }
                String value = mapping2.getValue();
                if (params != null) {
                    value = this.calculateMessageS(value, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<ParamName, String>>)params.entrySet().stream().map(b -> new NamedParam((Enum)ParamName.parse(b.getKey()), (Object)b.getValue())).toList(), ctx);
                }
                object.setValue(mapping2.getAttribute(), (Object)value);
                final String valueF = value;
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Write %s = %s to object %s with type %s", mapping2.getAttribute(), valueF, object.getObjectId(), object.getObjectType()));
                updated = true;
            }
            else {
                if (!(a instanceof IEdoIntegrationAnswerMappingByCounteragent)) {
                    continue;
                }
                final IEdoIntegrationAnswerMappingByCounteragent linkedConfig2 = (IEdoIntegrationAnswerMappingByCounteragent)a;
                if (!this.processAnswerAttributes(session, operationContext, counteragentContainer, counteragentContainer, info, linkedConfig2.getAnswerMapping(), params, ctx)) {
                    continue;
                }
                ServiceReg.getObjectModelService().commitModel(session, counteragentContainer, ctx);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", counteragentContainer.getObjectId(), object.getObjectType()));
            }
        }
        return updated;
    }
    
    public String getIntegrationLogFilenamePrefix(final IntegrationLogRecordType type) {
        switch (type) {
            case EXTERNAL_REQUEST: {
                return "diadocedo_request";
            }
            case EXTERNAL_RESPONSE: {
                return "diadocedo_response";
            }
            case EXTERNAL_REQUEST_ERROR: {
                return "diadocedo_error";
            }
            default: {
                return "diadoc_edo";
            }
        }
    }
    
    @Override
    public Path getLogContentDir() {
        final String repoPath = DiadocEdoModuleProperties.getProperty("backup_dir");
        if (StringUtils.isBlank(repoPath)) {
            this.getLogger().warn(() -> String.format("DIADOC EDO integration. Config value %s not defined in %s. Writing of integration log container file disabled.", "backup_dir", "/diadoc_edo_connector.properties"));
            return null;
        }
        final Path repo = FileSystems.getDefault().getPath(repoPath, new String[0]);
        if (!repo.toFile().exists()) {
            this.getLogger().warn(() -> String.format("DIADOC EDO integration. Integration backup folder %s not exists. Writing of integration log container file disabled.", repoPath));
            return null;
        }
        return repo;
    }
    
    @Override
    public boolean fireEvent(final DMSShardsClientSession session, final String eventName, final String objectId, final String objectType, final String ownerId, final Map<String, String> params, final SessionContext context) {
        final SessionContext eventCtx = context.clone();
        eventCtx.set("__type", (Object)objectType);
        eventCtx.set("__objectId", (Object)objectId);
        if (!StringUtils.isNotEmpty(eventName)) {
            return false;
        }
        final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
        final Map<String, String> eventParams = new HashMap<String, String>();
        eventParams.put("auto_process_action", "true");
        if (StringUtils.isNotBlank(ownerId)) {
            eventParams.put("__orgPositionId", ownerId);
            eventCtx.set("__orgPositionId", (Object)ownerId);
        }
        else {
            eventParams.put("__orgPositionId", (String)context.get("__orgPositionId"));
            eventCtx.set("__orgPositionId", context.get("__orgPositionId"));
        }
        if (params != null) {
            eventParams.putAll(params);
        }
        if (!eventHandlerService.fireSyncEvent(session, objectId, eventName, (Map)eventParams, eventCtx)) {
            this.getLogger().warn(() -> String.format("%s. Failed to create event %s in context %s", this.getLogPrefix(), eventName, context.toString()));
            return false;
        }
        return true;
    }
    
    public boolean isInstalled() {
        final ConfigElement cstmIntegrationConfig = ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", this.getCstmIntegrationId()), new SessionContext());
        if (cstmIntegrationConfig == null) {
            this.getLogger().debug(() -> String.format("Failed to find cstm_integration[id=%s] in config. Diadoc Edo service is not installed.", this.getCstmIntegrationId()));
        }
        else {
            this.getLogger().debug(() -> String.format("Config cstm_integration[id=%s] found.", this.getCstmIntegrationId()));
        }
        return cstmIntegrationConfig != null;
    }
    
    @Override
    public String getIntegrationLogAspect() {
        return "DIADOCEDO";
    }
    
    @Override
    public Map<String, String[]> getAvailableOrganizationBoxes(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String login, final String password, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        if (serverConfig != null) {
            final String url = serverConfig.getUrl();
            final String apiKey = serverConfig.getApiKey();
            try {
                final Map<String, String[]> result = new TreeMap<String, String[]>();
                final Properties props = IDiadocEdoConnectionConfig.loadProperties(session, serverConfig.getObjectId(), ctx);
                final DiadocEdoApiConnection connection = new DiadocEdoApiConnection(session, this, operationContext, url, apiKey, login, password, props, ctx);
                final OrganizationProtos.OrganizationList list = DiadocEdoApiMethodLibrary.getMyOrganizations(session, this, operationContext, connection.getApi(), ctx);
                for (final OrganizationProtos.Organization org : list.getOrganizationsList()) {
                    if (org.getBoxesCount() == 1) {
                        result.put(org.getBoxes(0).getBoxIdGuid(), new String[] { org.getFullName(), org.getInn(), org.getKpp() });
                    }
                    else {
                        for (final OrganizationProtos.Box a : org.getBoxesList()) {
                            result.put(a.getBoxIdGuid(), new String[] { org.getFullName(), org.getInn(), org.getKpp() });
                        }
                    }
                }
                return result;
            }
            catch (final DiadocEdoIntegrationException e) {
                this.getLogger().error(() -> String.format("Error while reading available boxes for login %s in %s", login, serverConfig.getName()), (Throwable)e);
            }
        }
        return Collections.emptyMap();
    }
    
    public String getLogPrefix() {
        return "Diadoc EDO Integration";
    }
    
    protected String logContext(final SessionContext ctx) {
        try {
            return "";
        }
        catch (final Exception e) {
            this.getLogger().trace(() -> String.format("%s, Logging process error. Failed to deserialize sessioncontext to json string.", this.getLogPrefix()), (Throwable)e);
            return ctx.toString();
        }
    }
    
    @Deprecated
    public void checkConnection2(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String serverId, final String login, final String password, final SessionContext ctx) {
        final IDiadocEdoServer server = (IDiadocEdoServer)ServiceReg.getObjectModelService().getObjectModel(session, serverId, ctx);
        if (server == null) {
            final ServiceException validationException = new ServiceException();
            validationException.setParam("__validation_error", (Object)String.format("Failed to resolve server config for id %s", serverId));
            throw validationException;
        }
        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
            final ServiceException validationException = new ServiceException();
            validationException.setParam("__validation_error", (Object)"Login/password must be defined");
            throw validationException;
        }
        final String url = server.getUrl();
        final String apiKey = server.getApiKey();
        try {
            final Properties props = IDiadocEdoConnectionConfig.loadProperties(session, server.getObjectId(), ctx);
            final DiadocEdoApiConnection connection = new DiadocEdoApiConnection(session, this, operationContext, url, apiKey, login, password, props, ctx);
            DiadocEdoApiMethodLibrary.getMyOrganizations(session, this, operationContext, connection.getApi(), ctx);
        }
        catch (final DiadocEdoIntegrationException e) {
            final ServiceException validationException2 = new ServiceException((Exception)e);
            final Throwable cause = e.getCause();
            if (cause instanceof final HttpResponseException httpError) {
                validationException2.setParam("__validation_error", (Object)String.format("HTTP error with code %d and reason %s", httpError.getStatusCode(), httpError.getReasonPhrase()));
            }
            else if (e.getCause() != null) {
                validationException2.setParam("__validation_error", (Object)e.getCause().getMessage());
            }
            else {
                validationException2.setParam("__validation_error", (Object)e.getMessage());
            }
            throw validationException2;
        }
    }
    
    @Override
    public Map<String, String[]> getAvailableOrganizationDepartment(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String login, final String password, final String boxId, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        final String url = serverConfig.getUrl();
        final String apiKey = serverConfig.getApiKey();
        try {
            final Map<String, String[]> result = new TreeMap<String, String[]>();
            final Properties props = IDiadocEdoConnectionConfig.loadProperties(session, serverConfig.getObjectId(), ctx);
            final DiadocEdoApiConnection connection = new DiadocEdoApiConnection(session, this, operationContext, url, apiKey, login, password, props, ctx);
            final OrganizationProtos.Box box = DiadocEdoApiMethodLibrary.getBox(session, this, operationContext, connection.getApi(), boxId, ctx);
            final OrganizationProtos.Organization org = box.getOrganization();
            for (final OrganizationProtos.Department d : org.getDepartmentsList()) {
                result.put(d.getDepartmentId(), new String[] { d.getName(), d.getKpp() });
            }
            return result;
        }
        catch (final DiadocEdoIntegrationException e) {
            this.getLogger().error(() -> String.format("Error while reading available department for login %s in %s and box %s", login, serverConfig.getName(), boxId), (Throwable)e);
            return Collections.emptyMap();
        }
    }
    
    @Override
    public IDiadocEdoCounteragentProcessor createCounteragentProcessor(final DMSShardsClientSession session, final SessionContext ctx) {
        final IDiadocEdoCounteragentProcessor processorInstance = new DiadocEdoCounteragentWithCachingProcessor();
        processorInstance.setService((IEdoIntegrationService)this);
        return processorInstance;
    }
    
    @Override
    public void processRemoteDocumentsStateChanges(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, IEdoIntegrationServiceConfiguration serviceConfig, final Map<String, String> params, final SessionContext ctx) {
        if (serviceConfig == null) {
            serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
        }
        if (serviceConfig == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call checkSendedDocumentStatus with null serviceConfig. Returns null");
            return;
        }
        final IEdoIntegrationStatusCheckFlowConfiguration statusCheckConfigurationU = serviceConfig.getStatusFlowConfiguration();
        if (statusCheckConfigurationU instanceof IDiadocEdoStatusCheckFlowConfiguration) {
            final String checkedSteps = "'', '" + String.join("', '", IEdoIntegrationInboundDataExchangeItem.ProcessingStep.getInProcessStepList()) + "'";
            final String categoryFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.CATEGORY, "ssa_state_category");
            final String idsFilter = this.createFilterExpression(params, IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.IDS, "sida_document");
            final int step = 25;
            final int start = 1;
            final IQueryService queryService = ServiceReg.getQueryService();
            final IObjectModelService objectModelService = ServiceReg.getObjectModelService();
            final IDiadocEdoStateChangeProcessor processor = this.createStateChangeProcessor();
            processor.setService((IEdoIntegrationService)this);
            processor.setServiceConfig(serviceConfig);
            final BatchItemsAccumulator<String, IDiadocEdoCombinedByFilialItem, IEdoIntegrationExchangeItem> exchangeItemAccumulator = new BatchItemsAccumulator<String, IDiadocEdoCombinedByFilialItem, IEdoIntegrationExchangeItem>(50, new BatchItemsStorage<IDiadocEdoCombinedByFilialItem, IEdoIntegrationExchangeItem>() {
                protected IDiadocEdoCombinedByFilialItem createSubstorage(final String key) {
                    return new DiadocEdoItemsCombinerByFilialId(key, (Function<String, IDiadocEdoCombinedByDocumentItem>)DiadocEdoItemsCombinerByDocumentId::new);
                }
            }) {
                protected Collection<IEdoIntegrationExchangeItem> fetchNextPage(final DMSShardsClientSession session, final int index, final SessionContext ctx) {
                    final List<IEdoIntegrationExchangeItem> result = new ArrayList<IEdoIntegrationExchangeItem>();
                    final String query = String.format("""
                                                       select r_object_id,sida_document,sida_filial from nt_edo_integration_exchange_item
                                                       where ssa_integration = '%s' and ssa_direction='%s' and (ssa_remote_state_processing_step in (%s)  or ssa_remote_state_processing_step is null) and sba_sended = true and ssa_item_type = 'document' %s %s and sba_ready = true and
                                                       sba_not_actual = false and sba_main = false ENABLE(RETURN_RANGE %d %d 'sida_filial,sida_document,r_object_id')
                                                       """, "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), checkedSteps, categoryFilter, idsFilter, start + index * step, start + index * step + step - 1);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Run exchange items query %s", query));
                    ICollection col = null;
                    try {
                        col = queryService.executeReadQuery(session, query, ctx);
                        while (col.next()) {
                            result.add((IEdoIntegrationExchangeItem)objectModelService.getObjectModel(session, col.getString("r_object_id"), ctx));
                        }
                    }
                    finally {
                        if (col != null) {
                            col.close();
                        }
                    }
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, DiadocEdoIntegrationServiceInternal.this.getLogger(), () -> String.format("Found %s items", result.size()));
                    return result;
                }
                
                protected void processBatch(final DMSShardsClientSession session, final String key, final IBatchItemsStorage<String, IDiadocEdoCombinedByFilialItem, IEdoIntegrationExchangeItem> batch, final SessionContext ctx) {
                    processor.process(session, operationContext, batch.getItems(), ctx);
                }
                
                protected String extractKey(final DMSShardsClientSession session, final IEdoIntegrationExchangeItem item, final SessionContext ctx) {
                    return item.getFilialId();
                }
            };
            exchangeItemAccumulator.execute(session, ctx);
        }
        else {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Diadoc EDO Integration. Not found any status check flow in dynamic integration config. Nothing to do, exiting...");
        }
    }
    
    protected String createFilterExpression(final Map<String, String> params, final IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES filterParam, final String filterAttribute) {
        String emptyStr = null;
        StringBuilder sb = null;
        if (params != null) {
            final String filteredParamStr = params.get(filterParam.name());
            if (StringUtils.isNotBlank(filteredParamStr)) {
                final String[] filteredParamsList = StringUtils.split(filteredParamStr, ",;");
                if (filteredParamsList != null && filteredParamsList.length > 0) {
                    for (final String filteredParam : filteredParamsList) {
                        if ("#EMPTY#".equals(filteredParam)) {
                            emptyStr = String.format(" %s is null ", filterAttribute);
                        }
                        else {
                            if (sb == null) {
                                sb = new StringBuilder();
                                sb.append(filterAttribute).append(" in (");
                            }
                            else {
                                sb.append(", ");
                            }
                            sb.append("'").append(filteredParam).append("'");
                        }
                    }
                    if (sb != null) {
                        sb.append(")");
                    }
                }
            }
        }
        if (sb == null && emptyStr == null) {
            return "";
        }
        if (sb != null && emptyStr != null) {
            return "and (" + Objects.toString(sb, "") + " or " + Objects.toString(emptyStr, "") + ")";
        }
        return "and (" + Objects.toString(sb, "") + Objects.toString(emptyStr, "") + ")";
    }
    
    private IDiadocEdoStateChangeProcessor createStateChangeProcessor() {
        return new DiadocEdoStateChangeProcessor();
    }
    
    @Override
    public String getCounteragentDetails(final String inn, final String kpp, final SessionContext ctx) {
        if (StringUtils.isBlank(kpp)) {
            return String.format("%s %s", ServiceReg.getIntegrationLogService().getNLSBundle(ctx).getNLSString("MSG_INN"), inn);
        }
        return String.format("%s %s %s %s", ServiceReg.getIntegrationLogService().getNLSBundle(ctx).getNLSString("MSG_INN"), inn, ServiceReg.getIntegrationLogService().getNLSBundle(ctx).getNLSString("MSG_KPP"), kpp);
    }
    
    @Override
    public IEdoIntegrationContentSync creatContentSyncObject(final DMSShardsClientSession session, final IEdoIntegrationService.EXCHANGE_DIRECTION direction, final IDiadocEdoOperationContext operationContext, final IDocument document, final String contentId, final String contentName, final String externalContainerGuid, final String externalContentGuid, final SessionContext ctx) {
        final IEdoIntegrationContentSync contentSync = (IEdoIntegrationContentSync)ServiceReg.getObjectModelService().createObjectModel(session, "nt_edo_integration_content_sync", ctx);
        contentSync.setContentId(contentId);
        contentSync.setContentName(contentName);
        contentSync.setDocumentId((document == null) ? null : document.getObjectId());
        contentSync.setDocumentType((document == null) ? null : document.getObjectType());
        contentSync.setIntegration("DIADOCEDO");
        contentSync.setDirection(direction.name());
        contentSync.setExternalGuid(externalContentGuid);
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)contentSync, ctx);
        return contentSync;
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult createOutgoingRevokeFile(final DMSShardsClientSession session, final IDiadocEdoOperationContext rootOperationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDocument document, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IEdoIntegrationServiceConfiguration serviceConfigF = (serviceConfig == null) ? this.getServiceConfiguration(session, rootOperationContext, ctx) : serviceConfig;
        if (serviceConfigF == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)rootOperationContext, this.getLogger(), () -> String.format("%s. call getServiceConfiguration returns null", this.getLogPrefix()));
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IEdoIntegrationOutboundRevokeFlowConfiguration statusCheckConfiguration = serviceConfigF.getOutboundRevokeConfiguration();
        if (statusCheckConfiguration == null) {
            IIntegrationOperationContext.warn((IIntegrationOperationContext)rootOperationContext, this.getLogger(), () -> "Not found any outbound revoke flow in dynamic integration config. Nothing to do, exiting...");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final String filialId = document.getFilial();
        final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        final String filialName = filial.getFilialName();
        final SessionContext filialContext = ctx.clone();
        filialContext.set("__filialId", (Object)filialId);
        filialContext.set("__filialName", (Object)filialName);
        IDiadocEdoOutboundRevokeProcessor processor = null;
        processor = this.createOutboundRequestProcessor();
        processor.setService((IEdoIntegrationService)this);
        processor.setServiceConfig(serviceConfigF);
        return processor.createRevokeFile(session, rootOperationContext, document, filialId, filialName, null, filialContext);
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult queueOutgoingRevokeFile(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDocument document, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IEdoIntegrationServiceConfiguration serviceConfigF = (serviceConfig == null) ? this.getServiceConfiguration(session, operationContext, ctx) : serviceConfig;
        if (serviceConfigF == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s. call getServiceConfiguration returns null", this.getLogPrefix()));
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final IEdoIntegrationOutboundRevokeFlowConfiguration statusCheckConfiguration = serviceConfigF.getOutboundRevokeConfiguration();
        if (statusCheckConfiguration == null) {
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Not found any outbound revoke flow in dynamic integration config. Nothing to do, exiting...");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        final String filialId = document.getFilial();
        final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        final String filialName = filial.getFilialName();
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Checking filial %s (%s)", filialName, filialId));
        final SessionContext filialContext = ctx.clone();
        filialContext.set("__filialId", (Object)filialId);
        filialContext.set("__filialName", (Object)filialName);
        IDiadocEdoOutboundRevokeProcessor processor = null;
        processor = this.createOutboundRequestProcessor();
        processor.setService((IEdoIntegrationService)this);
        processor.setServiceConfig(serviceConfigF);
        return processor.sendRevokeFile(session, operationContext, document, filialId, filialName, null, filialContext);
    }
    
    private IDiadocEdoOutboundRevokeProcessor createOutboundRequestProcessor() {
        return new DiadocEdoOutboundRevokeProcessor();
    }
    
    @Override
    public void commitQueueItemRemoteState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IEdoIntegrationInboundDataExchangeItem.ProcessingStep stage, final SessionContext ctx) {
        if (inboundRequest instanceof final IObjectModel model) {
            inboundRequest.setRemoteStateProcessingStep(stage);
            if (stage == IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed) {
                inboundRequest.setProcessingState(EdoIntegrationProcessingState.COMPLETED);
            }
            final StringBuilder sb = new StringBuilder();
            if (Boolean.getBoolean(IntegrationTracing.INTEGRATION_ITEMS_DEBUG_FLAG)) {
                sb.append("\nTrace\n");
                final String[] a = ExceptionUtils.getStackFrames((Throwable)new Exception());
                for (int i = 0; i < 10 && i < a.length; ++i) {
                    sb.append(a[i]).append("\n");
                }
            }
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commit (f1) %s object with id %s. vstamp is %s. Remote state processing step is %s, processingState is %s. %s", model.getObjectType(), model.getObjectId(), model.getVStamp(), inboundRequest.getRemoteStateProcessingStep(), inboundRequest.getProcessingState(), sb.toString()));
            model.setDirty(true);
            ServiceReg.getObjectModelService().commitModel(session, model, ctx);
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("vstamp is %s", model.getVStamp()));
        }
    }
    
    @Override
    public void commitQueueItemState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final EdoIntegrationProcessingState state, final SessionContext ctx) {
        if (inboundRequest instanceof final IObjectModel model) {
            inboundRequest.setProcessingState(state);
            final StringBuilder sb = new StringBuilder();
            if (Boolean.getBoolean(IntegrationTracing.INTEGRATION_ITEMS_DEBUG_FLAG)) {
                sb.append("\nTrace\n");
                final String[] a = ExceptionUtils.getStackFrames((Throwable)new Exception());
                for (int i = 0; i < 10 && i < a.length; ++i) {
                    sb.append(a[i]).append("\n");
                }
            }
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commit (f2) %s object with id %s. vstamp is %s. Remote state processing step is %s, processingState is %s. %s", model.getObjectType(), model.getObjectId(), model.getVStamp(), inboundRequest.getRemoteStateProcessingStep(), inboundRequest.getProcessingState(), sb.toString()));
            model.setDirty(true);
            ServiceReg.getObjectModelService().commitModel(session, model, ctx);
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("vstamp is %s", model.getVStamp()));
        }
    }
    
    @Override
    public void commitQueueItemState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IEdoIntegrationInboundDataExchangeItem.ProcessingError error, final SessionContext ctx) {
        if (inboundRequest instanceof final IObjectModel model) {
            inboundRequest.setProcessingError(error);
            inboundRequest.setProcessingState(EdoIntegrationProcessingState.ERROR);
            final StringBuilder sb = new StringBuilder();
            if (Boolean.getBoolean(IntegrationTracing.INTEGRATION_ITEMS_DEBUG_FLAG)) {
                sb.append("\nTrace\n");
                final String[] a = ExceptionUtils.getStackFrames((Throwable)new Exception());
                for (int i = 0; i < 10 && i < a.length; ++i) {
                    sb.append(a[i]).append("\n");
                }
            }
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commit (f3) %s object with id %s. vstamp is %s. Remote state processing step is %s, processingState is %s. %s", model.getObjectType(), model.getObjectId(), model.getVStamp(), inboundRequest.getRemoteStateProcessingStep(), inboundRequest.getProcessingState(), sb.toString()));
            model.setDirty(true);
            ServiceReg.getObjectModelService().commitModel(session, model, ctx);
            IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("vstamp is %s", model.getVStamp()));
        }
    }
    
    @Override
    public void commitQueueItemState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final SessionContext ctx) {
        if (inboundRequest instanceof final IObjectModel model) {
            if (model.isDirty()) {
                final StringBuilder sb = new StringBuilder();
                if (Boolean.getBoolean(IntegrationTracing.INTEGRATION_ITEMS_DEBUG_FLAG)) {
                    sb.append("\nTrace\n");
                    final String[] a = ExceptionUtils.getStackFrames((Throwable)new Exception());
                    for (int i = 0; i < 10 && i < a.length; ++i) {
                        sb.append(a[i]).append("\n");
                    }
                }
                IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commit (f4) %s object with id %s. vstamp is %s. Remote state processing step is %s, processingState is %s. %s", model.getObjectType(), model.getObjectId(), model.getVStamp(), inboundRequest.getRemoteStateProcessingStep(), inboundRequest.getProcessingState(), sb.toString()));
                model.setDirty(true);
                ServiceReg.getObjectModelService().commitModel(session, model, ctx);
                IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("vstamp is %s", model.getVStamp()));
            }
            else {
                IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commit (f4) not needed (isDirty = false) for %s object with id %s. vstamp is %s. Remote state processing step is %s, processingState is %s", model.getObjectType(), model.getObjectId(), model.getVStamp(), inboundRequest.getRemoteStateProcessingStep(), inboundRequest.getProcessingState()));
            }
        }
    }
    
    @Override
    public List<IDiadocEdoOrganizationDetails> getCounteragentOrganizationDetails(final DMSShardsClientSession session, final IDiadocEdoOperationContext parentOperationContext, final IDiadocEdoApi api, final String inn, final String kpp, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_QUEUE_DOCUMENT, ctx);
        try {
            final String url = serverConfig.getUrl();
            final String apiKey = serverConfig.getApiKey();
            try {
                final List<IDiadocEdoOrganizationDetails> result = new ArrayList<IDiadocEdoOrganizationDetails>();
                final OrganizationProtos.OrganizationList listA = DiadocEdoApiMethodLibrary.getOrganizationsByInnKpp(session, this, operationContext, api, inn, kpp, ctx);
                if (listA != null && listA.getOrganizationsCount() > 0) {
                    for (final OrganizationProtos.Organization org : listA.getOrganizationsList()) {
                        if (org == null) {
                            continue;
                        }
                        if (!org.getIsActive() || !StringUtils.equals(inn, org.getInn()) || !StringUtils.equals(kpp, org.getKpp())) {
                            continue;
                        }
                        for (int i = 0; i < org.getBoxesCount(); ++i) {
                            final OrganizationProtos.Box box = org.getBoxes(i);
                            result.add(new IDiadocEdoOrganizationDetails() {
                                public String getName() {
                                    return org.getFullName();
                                }
                                
                                public String getKpp() {
                                    return org.getKpp();
                                }
                                
                                public String getInn() {
                                    return org.getInn();
                                }
                                
                                public String getOrgId() {
                                    return org.getOrgId();
                                }
                                
                                @Override
                                public String getDepartmentId() {
                                    return null;
                                }
                                
                                @Override
                                public String getDepartmentName() {
                                    return null;
                                }
                                
                                @Override
                                public String getBoxId() {
                                    return box.getBoxIdGuid();
                                }
                            });
                        }
                    }
                }
                else if (StringUtils.isNotBlank(kpp)) {
                    final OrganizationProtos.OrganizationList listB = DiadocEdoApiMethodLibrary.getOrganizationsByInn(session, this, operationContext, api, inn, ctx);
                    if (listB != null && listB.getOrganizationsCount() > 0) {
                        for (final OrganizationProtos.Organization org2 : listB.getOrganizationsList()) {
                            if (org2.getIsActive() && StringUtils.equals(inn, org2.getInn()) && org2.getBoxesCount() > 0) {
                                final boolean nonRoaming = StringUtils.startsWith(org2.getFnsParticipantId(), "2BM-") && !org2.getIsRoaming();
                                if (nonRoaming) {
                                    final OrganizationProtos.Organization organization = DiadocEdoApiMethodLibrary.getOrganization(session, this, operationContext, api, org2.getOrgId(), ctx);
                                    if (organization.getDepartmentsList() == null) {
                                        continue;
                                    }
                                    for (final OrganizationProtos.Department dep : organization.getDepartmentsList()) {
                                        if (dep != null && StringUtils.equals(kpp, org2.getKpp())) {
                                            for (int j = 0; j < org2.getBoxesCount(); ++j) {
                                                final OrganizationProtos.Box box2 = org2.getBoxes(j);
                                                result.add(new IDiadocEdoOrganizationDetails() {
                                                    public String getName() {
                                                        return org2.getFullName();
                                                    }
                                                    
                                                    public String getKpp() {
                                                        return org2.getKpp();
                                                    }
                                                    
                                                    public String getInn() {
                                                        return org2.getInn();
                                                    }
                                                    
                                                    public String getOrgId() {
                                                        return org2.getOrgId();
                                                    }
                                                    
                                                    @Override
                                                    public String getDepartmentId() {
                                                        return dep.getDepartmentId();
                                                    }
                                                    
                                                    @Override
                                                    public String getDepartmentName() {
                                                        return dep.getName();
                                                    }
                                                    
                                                    @Override
                                                    public String getBoxId() {
                                                        return box2.getBoxIdGuid();
                                                    }
                                                });
                                            }
                                            break;
                                        }
                                    }
                                }
                                else {
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Allready found roaming diadoc edo organization. Trying to find non-roaming");
                                }
                            }
                        }
                    }
                    else {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc edo organization with INN %s not found", inn));
                    }
                }
                final List<IDiadocEdoOrganizationDetails> list = result;
                if (operationContext != null) {
                    operationContext.close();
                }
                return list;
            }
            catch (final DiadocEdoIntegrationException e) {
                e.printStackTrace();
                final List<Object> emptyList = Collections.emptyList();
                if (operationContext != null) {
                    operationContext.close();
                }
                return (List<IDiadocEdoOrganizationDetails>)emptyList;
            }
        }
        catch (final Throwable t) {
            if (operationContext != null) {
                try {
                    operationContext.close();
                }
                catch (final Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            throw t;
        }
    }
    
    @Deprecated
    public void checkConnection2(final DMSShardsClientSession session, final String serverId, final String login, final String password, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHECK_LOCAL_STATUS, ctx)) {
            boolean success = false;
            final List<NamedParam<ParamName, String>> params = Arrays.asList(new NamedParam((Enum)ParamName.login, (Object)login));
            try {
                this.checkConnection2(session, operationContext, serverId, login, password, ctx);
                success = true;
            }
            catch (final Exception e) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error connect to server %s with login %s", serverId, login), (Throwable)e, LogPropogateMode.all_level);
                throw new ServiceException(e);
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error connect to server %s with login %s", serverId, login), e2, LogPropogateMode.all_level);
                throw new ServiceException(new Exception(e2));
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("check_connection").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                }
            }
        }
    }
    
    @Override
    public IEdoIntegrationMappingInfo transformRemoteState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String remoteState, final SessionContext ctx) {
        final List<IEdoIntegrationStateMapping> remoteMappingList = operationContext.getDocumentRemoteStateMappingList(session, ctx);
        if (StringUtils.isNotBlank(remoteState)) {
            for (final IEdoIntegrationStateMapping stateMapping : remoteMappingList) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Check is %s configured value [%s] match to external [%s]", stateMapping.isRegexp() ? "regexp" : "", stateMapping.getExternalState(), remoteState));
                if (stateMapping.isCaseInsensitiveCheck()) {
                    if (StringUtils.equalsIgnoreCase(remoteState, stateMapping.getExternalState())) {
                        return (IEdoIntegrationMappingInfo)new IEdoIntegrationMappingInfo() {
                            final /* synthetic */ IEdoIntegrationStateMapping val$stateMapping;
                            
                            public String getSystemCode() {
                                return this.val$stateMapping.getStateCode();
                            }
                            
                            public String getUserMessage() {
                                return this.val$stateMapping.getStateDisplayString();
                            }
                            
                            public String getCategory() {
                                return this.val$stateMapping.getSystemStateCode();
                            }
                        };
                    }
                }
                else if (StringUtils.equals(remoteState, stateMapping.getExternalState())) {
                    return (IEdoIntegrationMappingInfo)new IEdoIntegrationMappingInfo() {
                        final /* synthetic */ IEdoIntegrationStateMapping val$stateMapping;
                        
                        public String getSystemCode() {
                            return stateMapping.getStateCode();
                        }
                        
                        public String getUserMessage() {
                            return stateMapping.getStateDisplayString();
                        }
                        
                        public String getCategory() {
                            return stateMapping.getSystemStateCode();
                        }
                    };
                }
                if (!stateMapping.isRegexp()) {
                    continue;
                }
                try {
                    final Pattern pattern = Pattern.compile(stateMapping.getExternalState(), stateMapping.isCaseInsensitiveCheck() ? 2 : 0);
                    final Matcher matcher = pattern.matcher(remoteState);
                    if (matcher.find()) {
                        return (IEdoIntegrationMappingInfo)new IEdoIntegrationMappingInfo() {
                            public String getSystemCode() {
                                return stateMapping.getStateCode();
                            }
                            
                            public String getUserMessage() {
                                return stateMapping.getStateDisplayString();
                            }
                            
                            public String getCategory() {
                                return stateMapping.getSystemStateCode();
                            }
                        };
                    }
                    continue;
                }
                catch (final Throwable e) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s is  not correct regexp value", stateMapping.getExternalState()), e);
                }
            }
        }
        return null;
    }
    
    @Override
    public IDiadocEdoExchangeError transformExchangeError(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String errorCode, final String errorMessage, final SessionContext ctx) {
        final List<IDiadocEdoExchangeError> remoteMappingList = operationContext.getExchangeErrors(session, ctx);
        if (StringUtils.isNotBlank(errorCode)) {
            IDiadocEdoExchangeError lastMatch = null;
            final int lastMatchCost = 0;
            for (final IDiadocEdoExchangeError stateMapping : remoteMappingList) {
                int currentMatchCost = 0;
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Check is %s configured value [%s] match to external [%s]", stateMapping.isRegexp() ? "regexp" : "", stateMapping.getErrorCode(), errorCode));
                Label_0253: {
                    if (StringUtils.isNotBlank(stateMapping.getErrorCode()) && !"*".equals(StringUtils.trimToNull(stateMapping.getErrorCode()))) {
                        Label_0153: {
                            if (stateMapping.isCaseInsensitiveCheck()) {
                                if (!StringUtils.equalsIgnoreCase(errorCode, stateMapping.getErrorCode())) {
                                    break Label_0153;
                                }
                            }
                            else if (!StringUtils.equals(errorCode, stateMapping.getErrorCode())) {
                                break Label_0153;
                            }
                            ++currentMatchCost;
                            break Label_0253;
                        }
                        if (stateMapping.isRegexp()) {
                            try {
                                final Pattern pattern = Pattern.compile(stateMapping.getErrorCode(), stateMapping.isCaseInsensitiveCheck() ? 2 : 0);
                                final Matcher matcher = pattern.matcher(errorCode);
                                if (matcher.find()) {
                                    ++currentMatchCost;
                                }
                                break Label_0253;
                            }
                            catch (final Throwable e) {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s is  not correct regexp value", stateMapping.getErrorCode()), e);
                                continue;
                            }
                        }
                        if (!Utils.compareWithMask(errorCode, stateMapping.getErrorCode(), 'X')) {
                            continue;
                        }
                        ++currentMatchCost;
                    }
                }
                Label_0419: {
                    if (StringUtils.isNotBlank(stateMapping.getErrorMessage()) && !"*".equals(StringUtils.trimToNull(stateMapping.getErrorMessage()))) {
                        Label_0334: {
                            if (stateMapping.isCaseInsensitiveCheck()) {
                                if (!StringUtils.equalsIgnoreCase(errorMessage, stateMapping.getErrorMessage())) {
                                    break Label_0334;
                                }
                            }
                            else if (!StringUtils.equals(errorMessage, stateMapping.getErrorMessage())) {
                                break Label_0334;
                            }
                            ++currentMatchCost;
                            break Label_0419;
                        }
                        if (stateMapping.isRegexp()) {
                            try {
                                final Pattern pattern = Pattern.compile(stateMapping.getErrorMessage(), stateMapping.isCaseInsensitiveCheck() ? 2 : 0);
                                final Matcher matcher = pattern.matcher(errorMessage);
                                if (matcher.find()) {
                                    ++currentMatchCost;
                                }
                                break Label_0419;
                            }
                            catch (final Throwable e) {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s is  not correct regexp value", stateMapping.getErrorMessage()), e);
                                continue;
                            }
                        }
                        currentMatchCost = 0;
                    }
                }
                if (currentMatchCost > lastMatchCost) {
                    lastMatch = stateMapping;
                }
            }
            if (lastMatch != null) {
                return lastMatch;
            }
        }
        return null;
    }
    
    public Collection<Map<String, String>> getOrganizationsByInn(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String filialId, final String inn, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiBoxConnection = operationContext.getApiConnection(session, this, ctx);
        try (final IDiadocEdoApi api = apiBoxConnection.getApi()) {
            final OrganizationProtos.OrganizationList oL = DiadocEdoApiMethodLibrary.getOrganizationsByInn(session, this, operationContext, api, inn, ctx);
            final List<Map<String, String>> orgs = new ArrayList<Map<String, String>>();
            oL.getOrganizationsList().forEach(a -> {
                final Map<String, String> b = new HashMap<String, String>();
                b.put("orgId", a.getOrgId());
                b.put("orgName", a.getFullName());
                b.put("inn", a.getInn());
                b.put("kpp", a.getKpp());
                b.put("departments", (String)a.getDepartmentsList().stream().map(c -> c.getName() + " kpp : " + c.getKpp()).collect(Collectors.joining("; ")));
                orgs.add(b);
                return;
            });
            return orgs;
        }
    }
    
    public Collection<Map<String, String>> getCounteragentsByInn(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String filialId, final String inn, final Map<String, String> params, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiBoxConnection = operationContext.getApiConnection(session, this, ctx);
        try (final IDiadocEdoApi api = apiBoxConnection.getApi()) {
            final List<CounteragentProtos.CounteragentList> oL = DiadocEdoApiMethodLibrary.getCounteragentList(session, this, operationContext, api, apiBoxConnection.getConfig().getBoxId(), inn, ctx);
            final List<Map<String, String>> orgs = new ArrayList<Map<String, String>>();
            oL.forEach(d -> d.getCounteragentsList().forEach(aL -> {
                final Map<String, String> b = new HashMap<String, String>();
                final OrganizationProtos.Organization a = aL.getOrganization();
                b.put("orgId", a.getOrgId());
                b.put("orgName", a.getFullName());
                b.put("inn", a.getInn());
                b.put("kpp", a.getKpp());
                b.put("departments", (String)a.getDepartmentsList().stream().map(c -> c.getName() + " kpp : " + c.getKpp()).collect(Collectors.joining("; ")));
                orgs.add(b);
            }));
            return orgs;
        }
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult checkIncomingDocuments(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IEdoIntegrationServiceConfiguration serviceConfig, final Map<String, String> params, final SessionContext ctx) {
        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_CHECK_INBOUND_DOCUMENTS_STARTS, null, (IIntegrationOperationContext)oContext, ctx);
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        try {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Inbox checking. Start at %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            final IEdoIntegrationServiceConfiguration serviceConfigF = (serviceConfig == null) ? this.getServiceConfiguration(session, oContext, ctx) : serviceConfig;
            if (serviceConfigF == null) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Configuration not installed. Exiting...");
                return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            final Set<String> externalTypes = new HashSet<String>();
            if (serviceConfigF.getIncomingDocumentConfiguration() == null) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Configuration for incoming documents not installed. Exiting...");
                return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            for (final IEdoIntegrationOperationConfiguration documentConfig : serviceConfigF.getIncomingDocumentConfiguration().getOperationConfigurations()) {
                if (documentConfig instanceof final IDiadocEdoIncomingDocumentReceiveScopedConfiguration scopedConfig) {
                    final List<String> addExternalTypes = scopedConfig.getExternalTypes();
                    externalTypes.addAll(addExternalTypes);
                }
            }
            if (externalTypes.isEmpty()) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "No found external document types for check. Exiting...");
                return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            final Collection<String> connectedFilialsList = oContext.getConnectedFilialsList(session, Arrays.asList(IntegrationOperationContext.ConnectionOptions.dontCheckIncoming), ctx);
            if (connectedFilialsList.isEmpty()) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Inbox checking. No connection setting found.");
                return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            for (final String filialId : connectedFilialsList) {
                final Date startTime = Calendar.getInstance().getTime();
                final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
                try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createFilialContext(session, oContext, filial, ctx)) {
                    IEdoIntegrationSendProcessor.ProcessingResult loopProcessingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
                    int documentCount = 0;
                    final IDiadocEdoApiBoxConnection connection = operationContext.getApiConnection(session, this, ctx);
                    try (final IDiadocEdoApi api = connection.getApi()) {
                        final IDiadocEdoConnectionConfig connectionConfig = operationContext.getApiConnectionConfig(session, ctx);
                        operationContext.initConnectionParams(connectionConfig);
                        LocalDate checkStartDate = null;
                        final String startDateOverride = params.get(IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.CHECK_START_DATE.name());
                        if (StringUtils.isNotBlank(startDateOverride)) {
                            try {
                                checkStartDate = LocalDate.parse(startDateOverride, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            }
                            catch (final DateTimeParseException e) {
                                throw new IllegalArgumentException(String.format("Format of %s param is %s", IEdoIntegrationService.EDO_OPERATION_PARAMS_NAMES.CHECK_START_DATE.name(), "yyyy-MM-dd"));
                            }
                        }
                        checkStartDate = LocalDate.now();
                        if (checkStartDate == null) {
                            final Date incomingDocumentsDateCheckFrom = connectionConfig.getIncomingDocumentsDateCheckFrom(session, ctx);
                            if (incomingDocumentsDateCheckFrom == null) {
                                throw new ServiceException("Start date of incoming check must be defined in Diadoc.EDO Account Member configuration");
                            }
                            int checkOffset = connectionConfig.getCheckOffset();
                            if (checkOffset <= 0) {
                                checkOffset = 1;
                            }
                            checkStartDate = incomingDocumentsDateCheckFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minus((long)checkOffset, (TemporalUnit)ChronoUnit.DAYS);
                        }
                        final String checkStartDateString = checkStartDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Connecting to Diadoc.EDO with user %s and account number %s from %s. Start at %s", connectionConfig.getLogin(), connectionConfig.getBoxId(), checkStartDateString, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                        operationContext.setParam(ParamName.start_check_date, checkStartDateString);
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_CHECK_INBOUND_DOCUMENTS_FOR_FILIAL_STARTS, null, (IIntegrationOperationContext)operationContext, ctx);
                        int pageSize = connectionConfig.getPageSize();
                        if (pageSize <= 0) {
                            pageSize = 25;
                        }
                        try {
                            String inn = StringUtils.trimToNull(connectionConfig.getInn());
                            String kpp = StringUtils.trimToNull(connectionConfig.getKpp());
                            if (inn == null) {
                                inn = StringUtils.trimToNull(filial.getFilialINN());
                            }
                            if (kpp == null) {
                                kpp = StringUtils.trimToNull(filial.getFilialKPP());
                            }
                            final String department = operationContext.getInboundDepartment(session, null, ctx);
                            String afterIndexKey = operationContext.getIncomingAfterIndexKey(session, connectionConfig.getAccountMemberId(), department, ctx);
                            try {
                                while (true) {
                                    final DiadocMessage_GetApiProtos.BoxEventList list = DiadocEdoApiMethodLibrary.getNewEvents(session, this, operationContext, api, connectionConfig.getBoxId(), null, afterIndexKey, department, null, null, DocumentDirection.Inbound, null, null, null, null, null, ctx);
                                    if (list.getEventsList().isEmpty()) {
                                        break;
                                    }
                                    class EntityInfo
                                    {
                                        private final String messageId = messageId;
                                        private final String entityId = entityId;
                                        private final String documentType = entity.getDocumentInfo().getDocumentType().name();
                                        private boolean exists;
                                        
                                        public EntityInfo(final String entityId, final String documentType) {
                                        }
                                        
                                        public boolean isExists() {
                                            return this.exists;
                                        }
                                        
                                        public void setExists(final boolean exists) {
                                            this.exists = exists;
                                        }
                                        
                                        public String getMessageId() {
                                            return this.messageId;
                                        }
                                        
                                        public String getEntityId() {
                                            return this.entityId;
                                        }
                                        
                                        public String getDocumentType() {
                                            return this.documentType;
                                        }
                                    }
                                    final Map<String, EntityInfo> entitiesList = new HashMap<String, EntityInfo>();
                                    for (final DiadocMessage_GetApiProtos.BoxEvent boxEvent : list.getEventsList()) {
                                        if (boxEvent.hasMessage()) {
                                            final String eventId = boxEvent.getEventId();
                                            final String messageId = boxEvent.getMessage().getMessageId();
                                            for (final DiadocMessage_GetApiProtos.Entity entity : boxEvent.getMessage().getEntitiesList()) {
                                                final String entityId = entity.getEntityId();
                                                if (entity.getEntityType() == DiadocMessage_GetApiProtos.EntityType.Attachment) {
                                                    if (entity.hasDocumentInfo()) {
                                                        if (entity.getDocumentInfo().getDocumentDirection() == DocumentDirectionProtos.DocumentDirection.Inbound) {
                                                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("EventId %s. Found document info for messageId %s and entity Id %s", eventId, messageId, entityId));
                                                            entitiesList.put(String.join(":", messageId, entityId), new EntityInfo(entityId));
                                                        }
                                                        else {
                                                            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("EventId %s. MessageId %s. Entity %s has % direction. Skipped", eventId, messageId, entityId, (entity.getDocumentInfo().getDocumentDirection() == null) ? "null" : entity.getDocumentInfo().getDocumentDirection().name()));
                                                        }
                                                    }
                                                    else {
                                                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("EventId %s. MessageId %s. Entity %s hasn't document info. Skipped", eventId, messageId, entityId));
                                                    }
                                                }
                                                else {
                                                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("EventId %s. MessageId %s. Entity %s is %s type. Skipped", eventId, messageId, entityId, (entity.getDocumentInfo().getDocumentDirection() == null) ? "null" : entity.getDocumentInfo().getDocumentDirection().name()));
                                                }
                                            }
                                        }
                                        afterIndexKey = boxEvent.getIndexKey();
                                    }
                                    final StringBuilder sb = new StringBuilder();
                                    sb.append("select r_object_id, ssa_message_id, ssa_entity_id, ssa_key from nt_diadoc_edo_incoming_doc_req where ssa_key in (");
                                    entitiesList.keySet().forEach(a -> sb.append("'").append(a).append("'"));
                                    sb.append(")");
                                    new AdminSessionRunner<Object>() {
                                        public Object run() {
                                            final ShardSessionRunner<Object> shardSessionRunner = new ShardSessionRunner<Object>() {
                                                public Object run() {
                                                    final DMSShardsClientSession session = AdminSessionRunner.this.getAdminSession();
                                                    try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, sb.toString(), ctx)) {
                                                        while (col.next()) {
                                                            final EntityInfo ei = entitiesList.get(col.getString("ssa_key"));
                                                            if (ei != null) {
                                                                ei.setExists(true);
                                                            }
                                                        }
                                                        return null;
                                                    }
                                                }
                                            };
                                            return shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
                                        }
                                    }.start((DMSShardsClientSession)null);
                                    final IObjectModelService omService = ServiceReg.getObjectModelService();
                                    for (final Map.Entry<String, EntityInfo> ei : entitiesList.entrySet()) {
                                        if (!ei.getValue().isExists()) {
                                            final IDiadocEdoIncomingDocumentRequest inboundRequest = (IDiadocEdoIncomingDocumentRequest)omService.createObjectModel("nt_diadoc_edo_incoming_doc_req", ctx);
                                            inboundRequest.setKey(ei.getKey());
                                            inboundRequest.setMessageId(ei.getValue().getMessageId());
                                            inboundRequest.setEntityId(ei.getValue().getEntityId());
                                            inboundRequest.setDiadocType(ei.getValue().getDocumentType());
                                            inboundRequest.setFilialId(connectionConfig.getFilialId());
                                            inboundRequest.setFilialName(connectionConfig.getFilialName());
                                            inboundRequest.setServerId(connectionConfig.getServerId());
                                            inboundRequest.setServerName(connectionConfig.getServerName());
                                            inboundRequest.setDiadocAccount(connectionConfig.getAccountId());
                                            inboundRequest.setDiadocAccountName(connectionConfig.getAccountName());
                                            inboundRequest.setDiadocLogin(connectionConfig.getLogin());
                                            inboundRequest.setDiadocBox(connectionConfig.getBoxId());
                                            inboundRequest.setDiadocDepartment(department);
                                            inboundRequest.setProcessingState(EdoIntegrationProcessingState.QUEUED);
                                            inboundRequest.setRemoteStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued);
                                            inboundRequest.setProcessingError((IEdoIntegrationInboundDataExchangeItem.ProcessingError)null);
                                            ++documentCount;
                                            ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)inboundRequest, ctx);
                                        }
                                    }
                                    operationContext.commitIncomingAfterIndexKey(session, connectionConfig.getAccountMemberId(), department, afterIndexKey, ctx);
                                }
                            }
                            catch (final Exception e2) {
                                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Login %s and account %s. Unexpected error", connectionConfig.getLogin(), connectionConfig.getBoxId()), (Throwable)e2);
                                loopProcessingResult = IEdoIntegrationSendProcessor.ProcessingResult.concat(loopProcessingResult, IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                            }
                        }
                        catch (final Exception e3) {
                            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Login %s and account %s. Unexpected error", connectionConfig.getLogin(), connectionConfig.getBoxId()), (Throwable)e3);
                            loopProcessingResult = IEdoIntegrationSendProcessor.ProcessingResult.concat(loopProcessingResult, IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                        }
                        if (loopProcessingResult == IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) {
                            connectionConfig.commitIncomingDocumentsDateCheckFrom(session, startTime, ctx);
                        }
                    }
                    catch (final Throwable e4) {
                        IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbox checking. Operation fails unexpectly.", e4);
                        loopProcessingResult = IEdoIntegrationSendProcessor.ProcessingResult.concat(loopProcessingResult, IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                    }
                    finally {
                        operationContext.setParam(ParamName.document_count, String.valueOf(documentCount));
                        final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_CHECK_INBOUND_SUCCESS, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_CHECK_INBOUND_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                        this.writeIntegrationLog(operationContext, loopProcessingResult, true, messagesContainer, ctx);
                        processingResult = IEdoIntegrationSendProcessor.ProcessingResult.concat(processingResult, loopProcessingResult);
                    }
                    if (operationContext == null) {
                        continue;
                    }
                }
                catch (final DiadocEdoIntegrationException e5) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Inbox checking. Operation fails unexpectly.", (Throwable)e5);
                    processingResult = processingResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                }
            }
        }
        catch (final ServiceException e6) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Inbox checking. Operation fails unexpectly.", (Throwable)e6);
            processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        finally {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Inbox checking. Ends at %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        return processingResult;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processIncomingDocumentsQueue(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IEdoIntegrationServiceConfiguration serviceConfiguration, final List<String> checkedStatuses, final Map<String, String> params, final SessionContext ctx) {
        return this.processIncomingDocumentsQueueImpl(session, oContext, serviceConfiguration, (checkedStatuses != null) ? checkedStatuses.stream().map((Function<? super Object, ? extends EdoIntegrationProcessingState>)EdoIntegrationProcessingState::valueOf).toList() : null, params, ctx);
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult processIncomingDocumentsQueueImpl(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IEdoIntegrationServiceConfiguration serviceConfig, final List<EdoIntegrationProcessingState> checkedStatuses, final Map<String, String> params, final SessionContext ctx) {
        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENTS_QUEUE_PROCESSING_STARTS, null, (IIntegrationOperationContext)oContext, ctx);
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Inbox event processing. Start at %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        final IEdoIntegrationServiceConfiguration serviceConfigF = (serviceConfig == null) ? this.getServiceConfiguration(session, oContext, ctx) : serviceConfig;
        if (serviceConfigF == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Configuration not installed. Exiting...");
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        try {
            final Collection<String> connectedFilialsList = oContext.getConnectedFilialsList(session, Arrays.asList(IntegrationOperationContext.ConnectionOptions.dontCheckIncoming), ctx);
            if (connectedFilialsList.isEmpty()) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Inbox checking. No connection setting found.");
                return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            }
            for (final String filialId : connectedFilialsList) {
                final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
                final SessionContext fCxt = this.createFilialSessionContext(session, filial, ctx);
                processingResult = processingResult.concat(this.processIncomingDocumentsQueueForFilial(session, oContext, serviceConfigF, filial, checkedStatuses, fCxt));
            }
        }
        catch (final Throwable e) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Inbox queue processing. Operation fails unexpectly.", e);
            processingResult = processingResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
        }
        finally {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> String.format("Inbox queue processing. Ends at %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        return processingResult;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processIncomingDocumentsQueueForFilial(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext2, final IEdoIntegrationServiceConfiguration serviceConfig, final IFilial filial, final List<EdoIntegrationProcessingState> checkedStatus, final SessionContext ctx) {
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
        boolean needLogRecord = true;
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createFilialContext(session, oContext2, filial, ctx)) {
            try {
                final IDiadocEdoApiBoxConnection connection = operationContext.getApiConnection(session, this, ctx);
                try (final IDiadocEdoApi api = connection.getApi()) {
                    final int idx = 0;
                    final int len = 100;
                    final int bad = 0;
                    boolean hasMore = false;
                    final String lastDocumentType = null;
                    do {
                        final List<QueueItemRecord> queueItems = this.getQueueItems(session, operationContext, filial.getObjectId(), checkedStatus, idx, len, ctx);
                        queueItems.addAll(this.getHangsQueueItems(session, operationContext, filial.getObjectId(), idx, len, ctx));
                        hasMore = (queueItems.size() == len);
                        needLogRecord = !queueItems.isEmpty();
                        if (needLogRecord) {
                            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENTS_FILIAL_QUEUE_PROCESSING_STARTS, null, (IIntegrationOperationContext)operationContext, ctx);
                        }
                        for (final QueueItemRecord queueItem : queueItems) {
                            processingResult = processingResult.concat(this.processQueueItemCreateRequest(session, operationContext, serviceConfig, api, queueItem, ctx));
                        }
                    } while (hasMore);
                }
            }
            catch (final DiadocEdoIntegrationException e1) {
                processingResult = processingResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error processing incoming items for filial %s %s", filial.getFilialName(), filial.getObjectId()), (Throwable)e1);
            }
            finally {
                if (needLogRecord) {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENTS_FILIAL_QUEUE_PROCESSING_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENTS_FILIAL_QUEUE_PROCESSING_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENTS_FILIAL_QUEUE_PROCESSING_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                }
            }
        }
        return processingResult;
    }
    
    protected List<QueueItemRecord> getQueueItems(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String filial, final List<EdoIntegrationProcessingState> checkedStatus, final int start, final int len, final SessionContext ctx) {
        String statusString;
        if (checkedStatus == null || checkedStatus.isEmpty()) {
            statusString = String.join("', '", EdoIntegrationProcessingState.QUEUED.name());
        }
        else {
            statusString = checkedStatus.stream().map(a -> (a == null) ? "ignore" : a.name()).collect((Collector<? super Object, ?, String>)Collectors.joining("', '"));
        }
        final String query = String.format("select r_object_id, sida_document, sida_filial, ssa_filial_name from nt_diadoc_edo_incoming_doc_req where sida_filial='%s' and ssa_processing_state in ('%s') enable (RETURN_RANGE %d %d 'r_object_id')", filial, statusString, start, len);
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Checking existing document queue with query %s", query));
        return (List)new AdminSessionRunner<List<QueueItemRecord>>() {
            public List<QueueItemRecord> run() {
                final ShardSessionRunner<List<QueueItemRecord>> shardSessionRunner = new ShardSessionRunner<List<QueueItemRecord>>() {
                    public List<QueueItemRecord> run() {
                        final DMSShardsClientSession session = AdminSessionRunner.this.getAdminSession();
                        try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, query, ctx)) {
                            final List<QueueItemRecord> existing = new ArrayList<QueueItemRecord>(100);
                            while (col.next()) {
                                existing.add(new QueueItemRecord(col.getString("r_object_id"), col.getString("sida_document"), null, col.getString("sida_filial"), col.getString("ssa_filial_name")));
                            }
                            return existing;
                        }
                    }
                };
                return (List)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
            }
        }.start((DMSShardsClientSession)null);
    }
    
    protected List<QueueItemRecord> getHangsQueueItems(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final String filial, final int start, final int len, final SessionContext ctx) {
        LocalDateTime lt = LocalDateTime.now();
        lt = lt.minus(1L, (TemporalUnit)ChronoUnit.DAYS);
        final String query = String.format("select r_object_id, sida_document, sida_filial, ssa_filial_name from nt_diadoc_edo_incoming_doc_req where sida_filial='%s' and ssa_processing_state in ('%s') and sdta_status_change_time < date('%s', 'dd.MM.yyyy hh:mm:ss') enable (RETURN_RANGE %d %d 'r_object_id')", filial, String.join("', '", EdoIntegrationProcessingState.INWORK.name()), lt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), start, len);
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Checking hangs document queue with query %s", query));
        final List<QueueItemRecord> res = (List<QueueItemRecord>)new AdminSessionRunner<List<QueueItemRecord>>() {
            public List<QueueItemRecord> run() {
                final ShardSessionRunner<List<QueueItemRecord>> shardSessionRunner = new ShardSessionRunner<List<QueueItemRecord>>() {
                    public List<QueueItemRecord> run() {
                        final DMSShardsClientSession session = AdminSessionRunner.this.getAdminSession();
                        try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, query, ctx)) {
                            final List<QueueItemRecord> existing = new ArrayList<QueueItemRecord>(100);
                            while (col.next()) {
                                existing.add(new QueueItemRecord(col.getString("r_object_id"), col.getString("sida_document"), null, col.getString("sida_filial"), col.getString("ssa_filial_name")));
                            }
                            return existing;
                        }
                    }
                };
                return (List)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
            }
        }.start((DMSShardsClientSession)null);
        if (res.isEmpty()) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbox checking. Hangs document not found");
        }
        else {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Inbox checking. Found %d hangs document", res.size()));
        }
        return res;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processQueueItemCreateRequest(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationServiceConfiguration serviceConfig, final IDiadocEdoApi api, final QueueItemRecord queueItem, final SessionContext ctx) {
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        try (final IDiadocEdoOperationContext dOperationContext = DiadocEdoOperationContext.createDocumentContext(session, (IIntegrationOperationContext)operationContext, null, ctx)) {
            dOperationContext.setDocumentInfo(session, queueItem.documentId(), queueItem.documentName(), queueItem.filialId(), queueItem.filialName(), ctx);
            try {
                final IDiadocEdoIncomingDocumentRequest inboundRequest = (IDiadocEdoIncomingDocumentRequest)ServiceReg.getObjectModelService().getObjectModel(session, queueItem.id(), ctx);
                final SessionContext documentContext = ctx.clone();
                documentContext.set("__diadocedoartifact", (Object)inboundRequest.getDiadocType());
                documentContext.set("DIADOC_TYPE", (Object)inboundRequest.getDiadocType());
                documentContext.set("__filialId", (Object)inboundRequest.getFilialId());
                documentContext.set("__filialName", (Object)inboundRequest.getFilialName());
                final IDiadocEdoOperationContext operationContext2 = dOperationContext;
                final String operationType = "incoming_document_receive_operation_processing";
                Objects.requireNonNull(serviceConfig);
                final IDiadocEdoIncomingDocumentReceiveConfiguration operationConfig = this.getOperationConfiguration(session, operationContext2, operationType, (Supplier<IEdoIntegrationFlowConfiguration>)serviceConfig::getIncomingDocumentConfiguration, DiadocEdoIncomingDocumentReceiveConfiguration::new, testConfig -> testConfig.getExternalTypes().contains(inboundRequest.getDiadocType()), documentContext);
                if (operationConfig != null) {
                    this.commitQueueItemState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)inboundRequest, EdoIntegrationProcessingState.INWORK, documentContext);
                    processingResult = processingResult.concat(this.processIncomingDocument(session, dOperationContext, api, serviceConfig, operationConfig, inboundRequest, documentContext));
                }
                else {
                    IIntegrationOperationContext.warn((IIntegrationOperationContext)dOperationContext, this.getLogger(), () -> "Not found any incoming document flow in dynamic integration config. Nothing to do, exiting...");
                }
            }
            catch (final Throwable e) {
                processingResult = processingResult.concat(IEdoIntegrationSendProcessor.ProcessingResult.FAILED);
                IIntegrationOperationContext.error((IIntegrationOperationContext)dOperationContext, this.getLogger(), () -> String.format("Inbox queue %s processing. Operation fails unexpectly.", queueItem), e);
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENT_REQUEST_PROCESSING_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENT_REQUEST_PROCESSING_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_INCOMING_DOCUMENT_REQUEST_PROCESSING_WITH_ERRORS, null);
                this.writeIntegrationLog(dOperationContext, processingResult, true, messagesContainer, ctx);
            }
        }
        return processingResult;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult processIncomingDocument(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationServiceConfiguration serviceConfig, final IDiadocEdoIncomingDocumentReceiveConfiguration operationConfig, final IDiadocEdoIncomingDocumentRequest inboundRequest, final SessionContext ctx) {
        final IDiadocEdoIncomingDocumentProcessor processor = this.createIncomingDocumentDocumentProcessor(session, operationConfig, ctx);
        processor.setOperationConfig((IEdoIntegrationOperationConfiguration)operationConfig);
        processor.setService((IEdoIntegrationService)this);
        processor.setServiceConfig(serviceConfig);
        return processor.process(session, operationContext, api, inboundRequest, ctx);
    }
    
    protected IDiadocEdoIncomingDocumentProcessor createIncomingDocumentDocumentProcessor(final DMSShardsClientSession session, final IDiadocEdoIncomingDocumentReceiveConfiguration operationConfig, final SessionContext ctx) {
        return new DiadocEdoIncomingDocumentProcessor();
    }
    
    static {
        logObjectMapper = new ObjectMapper();
    }
    
    private enum IntegrationLogRecordType
    {
        EXTERNAL_REQUEST, 
        EXTERNAL_RESPONSE, 
        EXTERNAL_REQUEST_ERROR;
    }
    
    public enum IntegrationLogRecordStage
    {
        ERROR, 
        RESPONSE, 
        REQUEST;
    }
    
    record QueueItemRecord(String id, String documentId, String documentName, String filialId, String filialName) {}
}
