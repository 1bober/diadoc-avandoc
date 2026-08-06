// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

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
import Diadoc.Api.DiadocApiResponce;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.google.protobuf.GeneratedMessage;

public class DiadocEdoApiLoggingGetWrapper<T extends GeneratedMessage> extends DiadocEdoApiLoggingBaseWrapper<T>
{
    public T wrapLogging(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final String logFilePrefix, final String logFileNameTemplate, final DiadocEdoApiSdkCall<T, BiConsumer<String, String>, BiConsumer<String, String>, BiConsumer<String, DiadocApiResponce<T>>> a, final BiFunction<T, IntegrationLogRecord.IStageBuilder, DiadocEdoApiIntegrationLogRecord[]> sucessLogMessageSupplier, final BiFunction<Exception, IntegrationLogRecord.IStageBuilder, DiadocEdoApiIntegrationLogRecord[]> errorLogMessageSupplier, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IntegrationLogRecord.IBuilder logRecord = IntegrationLogRecord.newBuilder();
        try {
            final IntegrationLogRecord.ILogFileBuilder operationLog = logRecord.setStorageDirectory(service.getLogContentDir()).setLogFilePrefix(logFilePrefix).setLogFilenameTemplate(logFileNameTemplate).setLogFilenameTemplate(logFileNameTemplate);
            try {
                if (a == null) {
                    final GeneratedMessage generatedMessage = null;
                    if (logRecord != null) {
                        logRecord.close();
                    }
                    return (T)generatedMessage;
                }
                final T value = a.call((name, url) -> {
                    try {
                        this.writeUrl(name, operationLog, url, ctx);
                    }
                    catch (final Exception e6) {
                        this.getLogger().error(String.format("%s. Error writing integration log data", service.getLogPrefix()), (Throwable)e6);
                    }
                    return;
                }, (name, requestData) -> {
                    if (requestData != null) {
                        try {
                            this.writeRequest(operationLog, requestData, ctx);
                        }
                        catch (final Exception e7) {
                            this.getLogger().error(String.format("%s. Error writing integration log data", service.getLogPrefix()), (Throwable)e7);
                        }
                    }
                    return;
                }, (name, responseData) -> {
                    try {
                        this.writeResponse(operationLog, (Diadoc.Api.DiadocApiResponce<T>)responseData, ctx);
                    }
                    catch (final Exception e8) {
                        this.getLogger().error(String.format("%s. Error writing integration log data", service.getLogPrefix()), (Throwable)e8);
                    }
                    return;
                });
                final DiadocEdoApiIntegrationLogRecord[] integrationLogRecordArray = (DiadocEdoApiIntegrationLogRecord[])((sucessLogMessageSupplier != null) ? ((DiadocEdoApiIntegrationLogRecord[])sucessLogMessageSupplier.apply(value, operationLog.getActiveStage())) : null);
                if (integrationLogRecordArray != null) {
                    for (final DiadocEdoApiIntegrationLogRecord integrationLogRecord : integrationLogRecordArray) {
                        this.writeIntegrationLogEntry(service, integrationLogRecord.getLevel(), integrationLogRecord.getOperation(), integrationLogRecord.getMessage(), operationLog.getActiveStage().build().getFileAbsolutePath(), operationContext, ctx);
                    }
                }
                final GeneratedMessage generatedMessage2 = value;
                if (logRecord != null) {
                    logRecord.close();
                }
                return (T)generatedMessage2;
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
        catch (final Throwable t) {
            if (logRecord != null) {
                try {
                    logRecord.close();
                }
                catch (final Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            throw t;
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
    
    protected void writeRequest(final IntegrationLogRecord.ILogFileBuilder operationLog, final String requestData, final SessionContext ctx) {
    }
}
