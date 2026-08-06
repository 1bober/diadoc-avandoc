// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;

public interface IIntegrationLogEntryWriter
{
    public static final boolean THROW_IF_INTEGRATION_LOG_ENTRY_NOT_FOUND = Boolean.getBoolean("tracing.integrationlog.throw_if_not_found");
    public static final String INTEGRATION_LOG_LOGGER = StringUtils.trimToNull(System.getProperty("tracing.integrationlog.logger_category"));
    public static final String DEFAULT_LOG_FILENAME = "operation_log";
    public static final String DEFAULT_LOG_FILEEXT = "txt";
    public static final LogPropogateMode defaultTraceLogMode = LogPropogateMode.all_level;
    public static final LogPropogateMode defaultWarnLogMode = LogPropogateMode.all_level;
    public static final LogPropogateMode defaultErrorLogMode = LogPropogateMode.all_level;
    
    default void traceP(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception) {
        this.log(operationContext, Level.TRACE, oLogger, Level.TRACE, recordPrefix, messageSupplier, exception, IIntegrationLogEntryWriter.defaultTraceLogMode);
    }
    
    default void traceP(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier) {
        this.log(operationContext, Level.TRACE, oLogger, Level.TRACE, recordPrefix, messageSupplier, null, IIntegrationLogEntryWriter.defaultTraceLogMode);
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.TRACE, oLogger, Level.TRACE, recordPrefix, messageSupplier, null, propogateMode);
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.TRACE, oLogger, Level.TRACE, recordPrefix, messageSupplier, exception, propogateMode);
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier) {
        this.log(operationContext, Level.WARN, oLogger, Level.WARN, recordPrefix, messageSupplier, null, IIntegrationLogEntryWriter.defaultWarnLogMode);
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.WARN, oLogger, Level.WARN, recordPrefix, messageSupplier, null, propogateMode);
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.WARN, oLogger, Level.WARN, recordPrefix, messageSupplier, exception, propogateMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier) {
        this.log(operationContext, Level.ERROR, oLogger, Level.ERROR, recordPrefix, messageSupplier, null, IIntegrationLogEntryWriter.defaultErrorLogMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.ERROR, oLogger, Level.ERROR, recordPrefix, messageSupplier, null, propogateMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception) {
        this.log(operationContext, Level.ERROR, oLogger, Level.ERROR, recordPrefix, messageSupplier, exception, IIntegrationLogEntryWriter.defaultErrorLogMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.ERROR, oLogger, Level.ERROR, recordPrefix, messageSupplier, exception, propogateMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final Level loggingLevel, final String recordPrefix, final Supplier<String> messageSupplier) {
        this.log(operationContext, Level.ERROR, oLogger, loggingLevel, recordPrefix, messageSupplier, null, IIntegrationLogEntryWriter.defaultErrorLogMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final Level loggingLevel, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception) {
        this.log(operationContext, Level.ERROR, oLogger, loggingLevel, recordPrefix, messageSupplier, exception, IIntegrationLogEntryWriter.defaultErrorLogMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger oLogger, final Level loggingLevel, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception, final LogPropogateMode propogateMode) {
        this.log(operationContext, Level.ERROR, oLogger, loggingLevel, recordPrefix, messageSupplier, exception, propogateMode);
    }
    
    default void log(final IIntegrationOperationContext operationContext, final Level operationContextLoggingLevel, final Logger oLogger, final Level loggingLevel, final String recordPrefix, final Supplier<String> messageSupplier, final Exception exception, final LogPropogateMode propogateMode) {
        final String message = (messageSupplier != null) ? messageSupplier.get() : "";
        if (operationContext != null) {
            operationContext.log(operationContextLoggingLevel, message, exception, propogateMode);
        }
        if (oLogger != null) {
            oLogger.log(loggingLevel, () -> String.format("%s. %s", recordPrefix, message), (Throwable)exception);
        }
        else {
            this.getLogger().log(loggingLevel, () -> String.format("%s. %s", recordPrefix, message), (Throwable)exception);
        }
    }
    
    Logger getLogger();
    
    String getLogPrefix();
}
