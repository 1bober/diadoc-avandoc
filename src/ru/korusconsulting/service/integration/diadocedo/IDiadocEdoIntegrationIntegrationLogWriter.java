// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import ru.korusconsulting.service.integration.processors.IIntegrationLogDocumentInfo;
import java.util.List;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.commons.NLSBundle;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationLogMethod;
import java.util.Arrays;
import java.util.Collections;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import java.util.Iterator;
import java.util.Map;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.StringUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.NamedParam;
import java.util.Collection;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.service.integration.IIntegrationLogEntryWriter;

public interface IDiadocEdoIntegrationIntegrationLogWriter extends IIntegrationLogEntryWriter
{
    default String calculateMessage(final IntegrationLogRecordMessage messageId, final IIntegrationOperationContext operationContext, final Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> namedArgs, final SessionContext ctx) {
        final String message = ServiceReg.getIntegrationLogService().getNLSBundle(ctx).getNLSString(messageId.name());
        return this.calculateMessageS(message, operationContext, namedArgs, ctx);
    }
    
    default String calculateMessageS(String message, final IIntegrationOperationContext operationContext, final Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> namedArgs, final SessionContext ctx) {
        if (namedArgs != null) {
            for (final NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String> namedArg : namedArgs) {
                message = StringUtils.replaceInString(message, "@" + namedArg.getKey() + "@", (namedArg.getValue() == null) ? "" : ((String)namedArg.getValue()));
            }
        }
        if (!(operationContext instanceof IDiadocEdoOperationContext)) {
            return message;
        }
        final IDiadocEdoOperationContext diadocOperationContext = (IDiadocEdoOperationContext)operationContext;
        if (diadocOperationContext.getParams() != null) {
            for (final Map.Entry<IDiadocEdoIntegrationServiceInternal.ParamName, String> namedArg2 : diadocOperationContext.getParams().entrySet()) {
                message = StringUtils.replaceInString(message, "@" + namedArg2.getKey() + "@", (namedArg2.getValue() == null) ? "" : namedArg2.getValue());
            }
        }
        final IIntegrationOperationContext parent = operationContext.getParent();
        if (parent instanceof final IDiadocEdoOperationContext parentOperationContext) {
            return this.calculateMessageS(message, (IIntegrationOperationContext)parentOperationContext, null, ctx);
        }
        return message;
    }
    
    default String calculateMessageS(String eventMessage, String message, final IDiadocEdoOperationContext operationContext, final Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> namedArgs, final SessionContext ctx) {
        if (namedArgs != null) {
            for (final NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String> namedArg : namedArgs) {
                eventMessage = StringUtils.replaceInString(eventMessage, "@" + namedArg.getKey() + "@", (namedArg.getValue() == null) ? "" : ((String)namedArg.getValue()));
                message = StringUtils.replaceInString(message, "@" + namedArg.getKey() + "@", (namedArg.getValue() == null) ? "" : ((String)namedArg.getValue()));
            }
        }
        if (operationContext.getParams() != null) {
            for (final Map.Entry<IDiadocEdoIntegrationServiceInternal.ParamName, String> namedArg2 : operationContext.getParams().entrySet()) {
                eventMessage = StringUtils.replaceInString(eventMessage, "@" + namedArg2.getKey() + "@", (namedArg2.getValue() == null) ? "" : namedArg2.getValue());
                message = StringUtils.replaceInString(message, "@" + namedArg2.getKey() + "@", (namedArg2.getValue() == null) ? "" : namedArg2.getValue());
            }
        }
        final IIntegrationOperationContext parent = operationContext.getParent();
        if (parent instanceof final IDiadocEdoOperationContext parentOperationContext) {
            return this.calculateMessageS(eventMessage, message, parentOperationContext, null, ctx);
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(eventMessage)) {
            return String.format("%s: %s", eventMessage, message);
        }
        return message;
    }
    
    default String calculateMessage(String message, final IDiadocEdoOperationContext operationContext, final Map<String, String> namedArgs, final SessionContext ctx) {
        if (namedArgs != null) {
            for (final Map.Entry<String, String> namedArg : namedArgs.entrySet()) {
                message = StringUtils.replaceInString(message, "@" + namedArg.getKey() + "@", (namedArg.getValue() == null) ? "" : namedArg.getValue());
            }
        }
        if (operationContext.getParams() != null) {
            for (final Map.Entry<IDiadocEdoIntegrationServiceInternal.ParamName, String> namedArg2 : operationContext.getParams().entrySet()) {
                message = StringUtils.replaceInString(message, "@" + namedArg2.getKey() + "@", (namedArg2.getValue() == null) ? "" : namedArg2.getValue());
            }
        }
        return message;
    }
    
    default void writeIntegrationLog(final IDiadocEdoOperationContext operationContext, final IEdoIntegrationSendProcessor.ProcessingResult processingResult, final boolean containerWriteRequired, final IntegrationLogMessagesContainer messagesContainer, final SessionContext ctx) {
        try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
            final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(this.getLogContentDir()).setLogFilePrefix("queue").setLogFilenameTemplate((String)operationContext.getLogFilenameTemplate().orElse(null));
            if (containerWriteRequired && org.apache.commons.lang.StringUtils.isNotBlank(operationContext.getLog())) {
                operationLog.getActiveStage().addFile("operation_log", "txt", operationContext.getLog().getBytes(StandardCharsets.UTF_8), ctx);
            }
            switch (processingResult) {
                case SUCCESS: {
                    if (messagesContainer.success() != null) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, messagesContainer.success(), operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                        break;
                    }
                    break;
                }
                case FAILED: {
                    if (messagesContainer.failure() != null) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.ERROR, messagesContainer.failure(), operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                        break;
                    }
                    break;
                }
                case WITHERRORS: {
                    if (messagesContainer.witherror() != null) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.WARNING, messagesContainer.witherror(), operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                        break;
                    }
                    break;
                }
                default: {
                    if (messagesContainer.skipped() != null) {
                        this.writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, messagesContainer.skipped(), operationLog.getActiveStage().build().getFileAbsolutePath(), (IIntegrationOperationContext)operationContext, ctx);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    default void writeIntegrationLogEntry(final IIntegrationLogService.TYPE type, final IntegrationLogRecordMessage messageId, final String containerPath, final IIntegrationOperationContext operationContext, final SessionContext ctx) {
        final String NLS_STRING_ERROR = "MSG_DIADOCEDO_ERROR_%s";
        final NLSBundle nls = ServiceReg.getIntegrationLogService().getNLSBundle(ctx);
        Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> namedArgs;
        if (operationContext.getLastError() != null) {
            final String errorNLS = String.format("MSG_DIADOCEDO_ERROR_%s", org.apache.commons.lang.StringUtils.upperCase(operationContext.getLastError().name()));
            String errorMessage;
            if (nls.hasNLSString(errorNLS)) {
                errorMessage = this.calculateMessageS(nls.getNLSString(errorNLS), operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            }
            else {
                errorMessage = operationContext.getLastError().name();
            }
            namedArgs = (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)errorMessage));
        }
        else {
            namedArgs = (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList();
        }
        final String message = this.calculateMessage(messageId, operationContext, namedArgs, ctx);
        this.writeIntegrationLogEntry(type, null, message, containerPath, operationContext, ctx);
    }
    
    default void writeIntegrationLogEntry(final IIntegrationLogService.TYPE type, final String message, final String objectId, final String filialId, final String filialName, final String container, final IIntegrationOperationContext operationContext, final SessionContext ctx) {
        final String event = operationContext.getIntegrationLogEvent();
        final IIntegrationLogService.IntegrationLogEntry entry = ServiceReg.getIntegrationLogService().makeIntegrationLogEntry(this.getIntegrationLogAspect(), type.name(), event, message, objectId, filialId, filialName, container, ctx);
        if (entry != null) {
            ServiceReg.getIntegrationLogService().addIntegrationLogEntry(entry, ctx);
            if (IDiadocEdoIntegrationIntegrationLogWriter.INTEGRATION_LOG_LOGGER != null) {
                LogManager.getLogger(IDiadocEdoIntegrationIntegrationLogWriter.INTEGRATION_LOG_LOGGER).trace(() -> String.format("Document %s. Write integration log entry for aspect [%s], type [%s], event [%s], message [%s], file [%s]", entry.getObjectId(), entry.getSystemName(), entry.getTypeName(), entry.getEventName(), entry.getMessage(), entry.getContainer()));
            }
            else {
                this.getLogger().trace(() -> String.format("Document %s. Write integration log entry for aspect [%s], type [%s], event [%s], message [%s], file [%s]", entry.getObjectId(), this.getIntegrationLogAspect(), entry.getTypeName(), entry.getEventName(), entry.getMessage(), entry.getContainer()));
            }
        }
        else {
            if (IDiadocEdoIntegrationIntegrationLogWriter.THROW_IF_INTEGRATION_LOG_ENTRY_NOT_FOUND) {
                throw new ServiceException(String.format("Document %s. Failed to create integration log entry for aspect %s, type %s, event %s, message %s", objectId, this.getIntegrationLogAspect(), type.name(), event, message));
            }
            this.getLogger().warn(() -> String.format("Document %s. Failed to create integration log entry for aspect [%s], type [%s], event [%s], message [%s]", objectId, this.getIntegrationLogAspect(), type.name(), event, message));
        }
    }
    
    default Path getLogContentDir() {
        final String repoPath = DiadocEdoModuleProperties.getProperty("backup_dir");
        if (org.apache.commons.lang.StringUtils.isBlank(repoPath)) {
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
    
    default void writeIntegrationLogEntry(final IIntegrationLogService.TYPE type, final DiadocEdoIntegrationLogMethod method, final String message, final String containerPath, final IIntegrationOperationContext operationContext, final SessionContext ctx) {
        final String NLS_STRING_OPERATION = "MSG_DIADOCEDO_OPERATION_%s";
        final String NLS_STRING_METHOD = "MSG_DIADOCEDO_OPERATION_%s";
        final NLSBundle nls = ServiceReg.getIntegrationLogService().getNLSBundle(ctx);
        final List<String> operationsStackString = operationContext.getOperationsStackString();
        StringBuilder sb = null;
        for (String operation : operationsStackString) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(operation)) {
                final String operationNLS = String.format("MSG_DIADOCEDO_OPERATION_%s", org.apache.commons.lang.StringUtils.upperCase(operation));
                if (nls.hasNLSString(operationNLS)) {
                    operation = this.calculateMessageS(nls.getNLSString(operationNLS), operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                }
                if (!org.apache.commons.lang.StringUtils.isNotBlank(operation)) {
                    continue;
                }
                if (sb == null) {
                    sb = new StringBuilder();
                    sb.append(operation).append(". ");
                }
                else {
                    sb.append(String.format("%s. ", operation));
                }
            }
        }
        if (method != null) {
            final String methodNLS = String.format("MSG_DIADOCEDO_OPERATION_%s", org.apache.commons.lang.StringUtils.upperCase(method.name()));
            String methodStr;
            if (nls.hasNLSString(methodNLS)) {
                methodStr = this.calculateMessageS(nls.getNLSString(methodNLS), operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            }
            else {
                methodStr = method.name();
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(methodStr)) {
                if (sb == null) {
                    sb = new StringBuilder();
                    sb.append(methodStr).append(". ");
                }
                else {
                    sb.append(String.format("%s. ", methodStr));
                }
            }
        }
        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append(message);
        final IIntegrationLogDocumentInfo documentInfo = operationContext.getIntegrationLogContextInfo();
        this.writeIntegrationLogEntry(type, sb.toString(), documentInfo.getObjectId(), documentInfo.getFilialId(), documentInfo.getFilialName(), containerPath, operationContext, ctx);
    }
    
    String getIntegrationLogAspect();
}
