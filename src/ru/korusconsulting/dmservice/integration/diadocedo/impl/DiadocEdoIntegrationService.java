// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import java.util.Collections;
import java.util.Collection;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoCounteragentProcessor;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoOrganizationDetails;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordEvent;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.SERVICE_DIADOC_EDO;
import ru.korusconsulting.dmservice.integration.diadocedo.ServiceDiadocEdo;

@ServiceDiadocEdo(name = SERVICE_DIADOC_EDO.DIADOC_EDO_INTEGRATION_SERVICE)
public class DiadocEdoIntegrationService extends DiadocEdoIntegrationServiceInternal
{
    private static final String NULL_CONFIG_ERROR_MESSAGE = "Call getServiceConfiguration returns null";
    
    @Override
    public void getRemoteDocumentState(final DMSShardsClientSession session, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_GET_DOCUMENTS_REMOTE_STATUS, ctx)) {
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                super.getRemoteDocumentsState(session, operationContext, serviceConfig, params, ctx);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("get_remote_document_state").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
    }
    
    @Override
    public List<IDiadocEdoOrganizationDetails> getOursOrganizationsDetails(final DMSShardsClientSession session, final String login, final String password, final String boxId, final boolean loadDepartments, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_GET_ORGANIZATION_BY_INN_KPP, ctx);
        try {
            try {
                final List<IDiadocEdoOrganizationDetails> oursOrganizationsDetails = super.getOursOrganizationsDetails(session, operationContext, login, password, boxId, loadDepartments, serverConfig, ctx);
                if (operationContext != null) {
                    operationContext.close();
                }
                return oursOrganizationsDetails;
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("get_ours_organizations_details").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
        catch (final Throwable t2) {
            if (operationContext != null) {
                try {
                    operationContext.close();
                }
                catch (final Throwable exception2) {
                    t2.addSuppressed(exception2);
                }
            }
            throw t2;
        }
    }
    
    @Override
    public void queueDocument(final DMSShardsClientSession session, final IDocument document, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_SEND_DOCUMENT, DiadocEdoIntegrationOperation.QUEUE_DOCUMENT, document, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                processingResult = super.queueDocument(session, operationContext, serviceConfig, document, params, ctx);
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
    }
    
    @Override
    public void changeDocumentExchangeState(final DMSShardsClientSession session, final IDocument document, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHANGE_DOCUMENT_EXCHANGE_STATE, DiadocEdoIntegrationOperation.QUEUE_DOCUMENT, document, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                processingResult = super.changeDocumentExchangeState(session, operationContext, serviceConfig, document, params, ctx);
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
    }
    
    @Override
    public void processRemoteDocumentStateChanges(final DMSShardsClientSession session, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_PROCESS_DOCUMENT_REMOTE_STATUS_CHANGE, ctx)) {
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                super.processRemoteDocumentsStateChanges(session, operationContext, serviceConfig, params, ctx);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("document_remote_status_change").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
    }
    
    @Override
    public void processQueueForSend(final DMSShardsClientSession session, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_PROCESS_SEND_DOCUMENT_QUEUE, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                processingResult = super.processQueueForSend(session, operationContext, serviceConfig, params, ctx);
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
    }
    
    @Override
    public Map<String, String[]> getAvailableOrganizationDepartment(final DMSShardsClientSession session, final String login, final String password, final String box, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        return null;
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult createOutgoingRevokeFile(final DMSShardsClientSession session, final IDocument document, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_OUTGOING_REVOKE_CREATE_FILE, null, document, ctx)) {
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IEdoIntegrationSendProcessor.ProcessingResult failed = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return failed;
                }
                final IEdoIntegrationSendProcessor.ProcessingResult outgoingRevokeFile = super.createOutgoingRevokeFile(session, operationContext, serviceConfig, document, params, ctx);
                if (operationContext != null) {
                    operationContext.close();
                }
                return outgoingRevokeFile;
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("create_outgoing_revoke_file").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
    }
    
    @Override
    public IEdoIntegrationSendProcessor.ProcessingResult queueOutgoingRevokeFile(final DMSShardsClientSession session, final IDocument document, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_OUTGOING_REVOKE_SEND_FILE, null, document, ctx)) {
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IEdoIntegrationSendProcessor.ProcessingResult failed = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return failed;
                }
                final IEdoIntegrationSendProcessor.ProcessingResult queueOutgoingRevokeFile = super.queueOutgoingRevokeFile(session, operationContext, serviceConfig, document, params, ctx);
                if (operationContext != null) {
                    operationContext.close();
                }
                return queueOutgoingRevokeFile;
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("send_outgoing_revoke_file").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
    }
    
    @Override
    public void checkLocalDocumentStatus(final DMSShardsClientSession session, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHECK_LOCAL_STATUS, ctx)) {
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                super.checkLocalDocumentStatus(session, operationContext, serviceConfig, params, ctx);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("check_local_status").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                }
            }
        }
    }
    
    @Override
    public List<IDiadocEdoOrganizationDetails> getCounteragentOrganizationDetails(final DMSShardsClientSession session, final String inn, final String kpp, final IDiadocEdoServer serverConfig, final SessionContext ctx) {
        return null;
    }
    
    @Override
    public void checkCounteragent(final DMSShardsClientSession session, final String filialId, final String inn, final String kpp, final Map<String, String> params, final SessionContext ctx) {
        final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_GET_COUNTERAGENT_BOX, null, null, filial, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            boolean success = false;
            try {
                final IDiadocEdoApiBoxConnection apiBoxConnection = operationContext.getApiConnection(session, this, ctx);
                try (final IDiadocEdoApi api = apiBoxConnection.getApi()) {
                    final IDiadocEdoCounteragentProcessor processor = this.createCounteragentProcessor(session, ctx);
                    processor.getCounteragentBoxGuid(session, operationContext, api, null, inn, kpp, IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.type_configured, operationContext.getApiConnectionConfig(session, ctx).isAllowTestCounteragent(), ctx);
                }
            }
            catch (final DiadocEdoIntegrationException e) {
                success = false;
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "System error", (Throwable)e, LogPropogateMode.all_level);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("checkCounteragent").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    if (success) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                    else {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                }
            }
        }
    }
    
    @Override
    public Collection<Map<String, String>> getCounteragentsByInn(final DMSShardsClientSession session, final String filialId, final String inn, final Map<String, String> params, final SessionContext ctx) {
        final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_GET_ORGANIZATION_BY_INN_KPP, null, null, filial, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            boolean success = true;
            try {
                final Collection<Map<String, String>> counteragentsByInn = this.getCounteragentsByInn(session, operationContext, filialId, inn, params, ctx);
                if (operationContext != null) {
                    operationContext.close();
                }
                return counteragentsByInn;
            }
            catch (final DiadocEdoIntegrationException e) {
                success = false;
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "System error", (Throwable)e, LogPropogateMode.all_level);
            }
            catch (final Throwable e2) {
                success = false;
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "System error", e2, LogPropogateMode.all_level);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("checkCounteragent").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    if (success) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                    else {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                }
            }
        }
        return (Collection<Map<String, String>>)Collections.emptyList();
    }
    
    @Override
    public Collection<Map<String, String>> getOrganizationsByInn(final DMSShardsClientSession session, final String filialId, final String inn, final Map<String, String> params, final SessionContext ctx) {
        final IFilial filial = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_GET_ORGANIZATION_BY_INN_KPP, null, null, filial, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            boolean success = true;
            try {
                final Collection<Map<String, String>> organizationsByInn = this.getOrganizationsByInn(session, operationContext, filialId, inn, params, ctx);
                if (operationContext != null) {
                    operationContext.close();
                }
                return organizationsByInn;
            }
            catch (final DiadocEdoIntegrationException e) {
                success = false;
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "System error", (Throwable)e, LogPropogateMode.all_level);
            }
            catch (final Throwable e2) {
                success = false;
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "System error", e2, LogPropogateMode.all_level);
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("checkCounteragent").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    if (success) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                    else {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                    }
                }
            }
        }
        return (Collection<Map<String, String>>)Collections.emptyList();
    }
    
    @Override
    public void checkConnection(final DMSShardsClientSession session, final String serverId, final String login, final String password, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext rootOperationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHECK_CONNECTION, ctx)) {
            this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)rootOperationContext, ctx);
            boolean success = false;
            try {
                super.checkConnection2(session, serverId, login, password, ctx);
                success = true;
            }
            finally {
                try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
                    final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("checkCounteragent").setLogFilenameTemplate((String)rootOperationContext.getLogFilenameTemplate().orElse(null));
                    operationLog.getActiveStage().addFile("operation_log", "txt", rootOperationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
                    if (success) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)rootOperationContext, ctx);
                    }
                    else {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)rootOperationContext, ctx);
                    }
                }
            }
        }
    }
    
    @Override
    public void checkIncomingDocuments(final DMSShardsClientSession session, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHECK_INCOMING_DOCUMENTS, ctx)) {
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                processingResult = super.checkIncomingDocuments(session, operationContext, serviceConfig, params, ctx);
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
    }
    
    @Override
    public void processIncomingDocumentsQueue(final DMSShardsClientSession session, final List<String> checkedStatuses, final Map<String, String> params, final SessionContext ctx) {
        try (final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createRootContext(session, IntegrationLogRecordEvent.DIADOCEDO_CHECK_INCOMING_DOCUMENTS, ctx)) {
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
            try {
                final IEdoIntegrationServiceConfiguration serviceConfig = this.getServiceConfiguration(session, operationContext, ctx);
                if (serviceConfig == null) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Call getServiceConfiguration returns null");
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return;
                }
                processingResult = super.processIncomingDocumentsQueue(session, operationContext, serviceConfig, checkedStatuses, params, ctx);
            }
            catch (final ServiceException e) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw e;
                }
            }
            catch (final Throwable e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e2, LogPropogateMode.all_level);
                processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                if (!Boolean.parseBoolean(params.get("ignore_errors"))) {
                    throw new ServiceException("", e2);
                }
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
    }
}
