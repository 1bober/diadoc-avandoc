// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import ru.korusconsulting.service.integration.processors.IIntegrationLogDocumentInfo;
import java.util.LinkedList;
import org.apache.logging.log4j.util.Supplier;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import ru.korusconsulting.service.objectmodel.administration.IFilial;

public interface IIntegrationOperationContext extends AutoCloseable
{
    IIntegrationOperationContext getParent();
    
    IFilial getFilial();
    
    String getFilialName();
    
    String getFilialId();
    
    String getFilialInn();
    
    String getFilialKpp();
    
    void setDocumentId(final String p0);
    
    String getDocumentId();
    
    void trace(final String p0, final Throwable p1, final LogPropogateMode p2);
    
    void warn(final String p0, final Throwable p1, final LogPropogateMode p2);
    
    void error(final String p0, final Throwable p1, final LogPropogateMode p2);
    
    void log(final Level p0, final String p1, final Throwable p2, final LogPropogateMode p3);
    
    String getLog();
    
    void close();
    
    Optional<String> getLogFilenameTemplate();
    
    IOperationLogWriter getLogWriter();
    
    String getExchangeCategory();
    
    void setExchangeCategory(final String p0);
    
    void setLastError(final EdoIntegrationSubsystemErrorCode p0);
    
    EdoIntegrationSubsystemErrorCode getLastError();
    
    default void error(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final Throwable e, final LogPropogateMode propogateMode) {
        if (operationContext != null) {
            operationContext.getLogWriter().error(logger, messageSupplier, e, propogateMode);
        }
        else if (logger != null) {
            logger.error((Supplier)messageSupplier, e);
        }
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        error(operationContext, logger, messageSupplier, null, propogateMode);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final Throwable e) {
        error(operationContext, logger, messageSupplier, e, LogPropogateMode.all_level);
    }
    
    default void error(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier) {
        error(operationContext, logger, messageSupplier, null, LogPropogateMode.all_level);
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final Throwable e, final LogPropogateMode propogateMode) {
        if (operationContext != null) {
            operationContext.getLogWriter().warn(logger, messageSupplier, propogateMode);
        }
        else if (logger != null) {
            logger.warn((Supplier)messageSupplier, e);
        }
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        warn(operationContext, logger, messageSupplier, null, propogateMode);
    }
    
    default void warn(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier) {
        warn(operationContext, logger, messageSupplier, null, LogPropogateMode.all_level);
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final Throwable e, final LogPropogateMode propogateMode) {
        if (operationContext != null) {
            operationContext.getLogWriter().trace(logger, messageSupplier, e, propogateMode);
        }
        else if (logger != null) {
            logger.trace((Supplier)messageSupplier, e);
        }
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode propogateMode) {
        trace(operationContext, logger, messageSupplier, null, propogateMode);
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier, final Throwable e) {
        trace(operationContext, logger, messageSupplier, e, LogPropogateMode.all_level);
    }
    
    default void trace(final IIntegrationOperationContext operationContext, final Logger logger, final Supplier<String> messageSupplier) {
        trace(operationContext, logger, messageSupplier, null, LogPropogateMode.all_level);
    }
    
    LogPropogateMode getPropogateLogMode();
    
    void setIntegrationLogEvent(final String p0);
    
    String getIntegrationLogEvent();
    
    LinkedList<String> getOperationsStackString();
    
    IIntegrationLogDocumentInfo getIntegrationLogContextInfo();
    
    String getActiveDepartment();
    
    String getActiveRegulation();
}
