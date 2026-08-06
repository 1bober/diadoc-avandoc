// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationLogMethod;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import java.util.Iterator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.JsonNode;
import Diadoc.Api.exceptions.DiadocSdkException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import Diadoc.Api.exceptions.DiadocSdkIncorrectConfigurationException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoConfigurationProblemException;
import Diadoc.Api.exceptions.DiadocSdkUnparsableResultException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationUnparsableResultException;
import Diadoc.Api.exceptions.DiadocSdkErrorWithCodeException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationProcessableException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang.exception.ExceptionUtils;
import Diadoc.Api.exceptions.DiadocSdkRetryAfterException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationRetryAfterException;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.Logger;

public class DiadocEdoApiLoggingPrimitiveVoidCallWrapper
{
    private static final Logger logger;
    
    protected Logger getLogger() {
        return DiadocEdoApiLoggingPrimitiveVoidCallWrapper.logger;
    }
    
    public void wrapLogging(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final String logFilePrefix, final String logFileNameTemplate, final DiadocEdoApiSdkVoidCall<BiConsumer<String, String>> a, final BiFunction<Exception, IntegrationLogRecord.IStageBuilder, DiadocEdoApiIntegrationLogRecord[]> errorLogMessageSupplier, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) throws DiadocEdoIntegrationException {
        try (final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder()) {
            final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(service.getLogContentDir()).setLogFilePrefix(logFilePrefix).setLogFilenameTemplate(logFileNameTemplate);
            try {
                if (a == null) {
                    if (logRecord != null) {
                        logRecord.close();
                    }
                    return;
                }
                a.call((name, url) -> {
                    try {
                        this.writeUrl(name, operationLog, url, ctx);
                    }
                    catch (final Exception e6) {
                        this.getLogger().error(String.format("%s. Error writing integration log data", service.getLogPrefix()), (Throwable)e6);
                    }
                    return;
                });
            }
            catch (final DiadocSdkRetryAfterException e) {
                throw new DiadocEdoIntegrationRetryAfterException(e);
            }
            catch (final DiadocSdkErrorWithCodeException e2) {
                if (e2.getResponse() != null && e2.getResponse().length > 0) {
                    operationLog.getActiveStage().addFile("response", "txt", e2.getResponse(), ctx);
                }
                operationLog.getActiveStage().addFile("call_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e2).getBytes(StandardCharsets.UTF_8), ctx);
                final DiadocEdoApiIntegrationLogRecord[] integrationLogRecordArray = (DiadocEdoApiIntegrationLogRecord[])((errorLogMessageSupplier != null) ? ((DiadocEdoApiIntegrationLogRecord[])errorLogMessageSupplier.apply((Exception)e2, operationLog.getActiveStage())) : null);
                if (integrationLogRecordArray != null) {
                    for (final DiadocEdoApiIntegrationLogRecord integrationLogRecord : integrationLogRecordArray) {
                        this.writeIntegrationLogEntry(service, integrationLogRecord.getLevel(), integrationLogRecord.getOperation(), integrationLogRecord.getMessage(), operationLog.getActiveStage().build().getFileAbsolutePath(), operationContext, ctx);
                    }
                }
                throw new DiadocEdoIntegrationProcessableException(e2);
            }
            catch (final DiadocSdkUnparsableResultException e3) {
                if (e3.getResponse() != null && e3.getResponse().length > 0) {
                    operationLog.getActiveStage().addFile("response", "txt", e3.getResponse(), ctx);
                }
                operationLog.getActiveStage().addFile("call_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e3).getBytes(StandardCharsets.UTF_8), ctx);
                final DiadocEdoApiIntegrationLogRecord[] integrationLogRecordArray = (DiadocEdoApiIntegrationLogRecord[])((errorLogMessageSupplier != null) ? ((DiadocEdoApiIntegrationLogRecord[])errorLogMessageSupplier.apply((Exception)e3, operationLog.getActiveStage())) : null);
                if (integrationLogRecordArray != null) {
                    for (final DiadocEdoApiIntegrationLogRecord integrationLogRecord : integrationLogRecordArray) {
                        this.writeIntegrationLogEntry(service, integrationLogRecord.getLevel(), integrationLogRecord.getOperation(), integrationLogRecord.getMessage(), operationLog.getActiveStage().build().getFileAbsolutePath(), operationContext, ctx);
                    }
                }
                throw new DiadocEdoIntegrationUnparsableResultException(e3);
            }
            catch (final DiadocSdkIncorrectConfigurationException e4) {
                operationLog.getActiveStage().addFile("call_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e4).getBytes(StandardCharsets.UTF_8), ctx);
                final DiadocEdoApiIntegrationLogRecord[] integrationLogRecordArray = (DiadocEdoApiIntegrationLogRecord[])((errorLogMessageSupplier != null) ? ((DiadocEdoApiIntegrationLogRecord[])errorLogMessageSupplier.apply((Exception)e4, operationLog.getActiveStage())) : null);
                if (integrationLogRecordArray != null) {
                    for (final DiadocEdoApiIntegrationLogRecord integrationLogRecord : integrationLogRecordArray) {
                        this.writeIntegrationLogEntry(service, integrationLogRecord.getLevel(), integrationLogRecord.getOperation(), integrationLogRecord.getMessage(), operationLog.getActiveStage().build().getFileAbsolutePath(), operationContext, ctx);
                    }
                }
                throw new DiadocEdoConfigurationProblemException(e4.getMessage(), e4);
            }
            catch (final DiadocSdkException e5) {
                operationLog.getActiveStage().addFile("call_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e5).getBytes(StandardCharsets.UTF_8), ctx);
                final DiadocEdoApiIntegrationLogRecord[] integrationLogRecordArray = (DiadocEdoApiIntegrationLogRecord[])((errorLogMessageSupplier != null) ? ((DiadocEdoApiIntegrationLogRecord[])errorLogMessageSupplier.apply((Exception)e5, operationLog.getActiveStage())) : null);
                if (integrationLogRecordArray != null) {
                    for (final DiadocEdoApiIntegrationLogRecord integrationLogRecord : integrationLogRecordArray) {
                        this.writeIntegrationLogEntry(service, integrationLogRecord.getLevel(), integrationLogRecord.getOperation(), integrationLogRecord.getMessage(), operationLog.getActiveStage().build().getFileAbsolutePath(), operationContext, ctx);
                    }
                }
                throw new DiadocEdoIntegrationException((Throwable)e5);
            }
        }
    }
    
    public void change(final JsonNode parent, final String fieldName, final String newValue) {
        if (parent.has(fieldName)) {
            final JsonNode n = parent.get(fieldName);
            if (n instanceof final TextNode tn) {
                final int len = StringUtils.length(tn.textValue());
                if (len > 100) {
                    ((ObjectNode)parent).put(fieldName, String.format(newValue, 100));
                }
            }
        }
        for (final JsonNode child : parent) {
            this.change(child, fieldName, newValue);
        }
    }
    
    protected final void writeIntegrationLogEntry(final IDiadocEdoIntegrationServiceInternal service, final IIntegrationLogService.TYPE level, final DiadocEdoIntegrationLogMethod operation, final String message, final String containerPath, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        try {
            service.writeIntegrationLogEntry(level, operation, message, containerPath, (IIntegrationOperationContext)operationContext, ctx);
        }
        catch (final Exception e) {
            this.getLogger().error(String.format("%s. Document %s. Error writing integration log data", service.getLogPrefix(), operationContext.getDocumentId()), (Throwable)e);
        }
    }
    
    protected void writeUrl(final String name, final IntegrationLogRecord.ILogFileBuilder operationLog, final String url, final SessionContext ctx) {
        if (StringUtils.isNotBlank(url)) {
            operationLog.getActiveStage().addFile(StringUtils.isBlank(name) ? "request" : name, "url", url.getBytes(StandardCharsets.UTF_8), ctx);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
