// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl;

import java.util.Objects;
import org.apache.logging.log4j.util.Supplier;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.processors.IIntegrationLogDocumentInfo;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.service.utils.ServiceReg;
import org.apache.commons.lang3.StringUtils;
import ru.korusconsulting.dmservice.integration.tracing.IntegrationTracing;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import java.util.List;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.integration.IOperationLogWriter;
import java.util.Optional;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public abstract class IntegrationOperationContext implements IIntegrationOperationContext
{
    private final StringBuilder log;
    private final Optional<String> logFilenameTemplate;
    private final IOperationLogWriter logWriter;
    private final IIntegrationOperationContext parent;
    private String integrationLogEvent;
    private final IFilial filial;
    private String documentId;
    private String exchangeCategory;
    private String activeDepartment;
    private String activeRegulation;
    private EdoIntegrationSubsystemErrorCode lastError;
    private final Exception creationStack;
    private List<IEdoIntegrationStateMapping> documentRemoteMappingList;
    
    protected IntegrationOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final String integrationLogEvent, final IFilial filial, final IDocument document, final SessionContext ctx) {
        this.log = new StringBuilder();
        this.creationStack = (Boolean.getBoolean(IntegrationTracing.INTEGRATION_CONTEXT_DEBUG_FLAG) ? new Exception() : null);
        this.parent = parent;
        this.filial = this.resovleFilial(session, filial, document, ctx);
        this.documentId = ((document != null) ? document.getObjectId() : null);
        this.integrationLogEvent = integrationLogEvent;
        this.logFilenameTemplate = this.getLogFilenameTemplate(session, parent, ctx);
        this.logWriter = this.initLogWriter();
    }
    
    protected IFilial resovleFilial(final DMSShardsClientSession session, final IFilial filial, final IDocument document, final SessionContext ctx) {
        if (filial != null) {
            return filial;
        }
        if (document != null && StringUtils.isNotBlank((CharSequence)document.getFilial())) {
            return (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, document.getFilial(), ctx);
        }
        return null;
    }
    
    protected abstract Optional<String> getLogFilenameTemplate(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final SessionContext p2);
    
    @Override
    public IIntegrationOperationContext getParent() {
        return this.parent;
    }
    
    @Override
    public IFilial getFilial() {
        if (this.filial == null) {
            return (this.parent == null) ? null : this.parent.getFilial();
        }
        return this.filial;
    }
    
    @Override
    public String getFilialName() {
        if (this.getFilial() != null) {
            return this.getFilial().getFilialName();
        }
        return null;
    }
    
    @Override
    public String getFilialId() {
        if (this.getFilial() != null) {
            return this.getFilial().getObjectId();
        }
        return null;
    }
    
    @Override
    public String getFilialInn() {
        if (this.getFilial() != null) {
            return this.getFilial().getFilialINN();
        }
        return null;
    }
    
    @Override
    public String getFilialKpp() {
        if (this.getFilial() != null) {
            return this.getFilial().getFilialKPP();
        }
        return null;
    }
    
    @Override
    public void setDocumentId(final String value) {
        this.documentId = value;
    }
    
    @Override
    public String getDocumentId() {
        if (StringUtils.isNotBlank((CharSequence)this.documentId)) {
            return this.documentId;
        }
        if (this.getParent() != null) {
            return this.getParent().getDocumentId();
        }
        return null;
    }
    
    @Override
    public Optional<String> getLogFilenameTemplate() {
        return this.logFilenameTemplate;
    }
    
    @Override
    public IOperationLogWriter getLogWriter() {
        return this.logWriter;
    }
    
    @Override
    public void setIntegrationLogEvent(final String value) {
        this.integrationLogEvent = value;
    }
    
    @Override
    public String getIntegrationLogEvent() {
        if (StringUtils.isNotBlank((CharSequence)this.integrationLogEvent)) {
            return this.integrationLogEvent;
        }
        if (this.getParent() != null) {
            return this.getParent().getIntegrationLogEvent();
        }
        return null;
    }
    
    @Override
    public String getExchangeCategory() {
        if (StringUtils.isNotBlank((CharSequence)this.exchangeCategory)) {
            return this.exchangeCategory;
        }
        if (this.getParent() != null) {
            return this.getParent().getExchangeCategory();
        }
        return null;
    }
    
    @Override
    public void setExchangeCategory(final String value) {
        this.exchangeCategory = value;
    }
    
    @Override
    public String getActiveDepartment() {
        if (this.activeDepartment != null) {
            return this.activeDepartment;
        }
        if (this.getParent() != null) {
            return this.getParent().getActiveDepartment();
        }
        return null;
    }
    
    public void setActiveDepartment(final String value) {
        this.activeDepartment = value;
    }
    
    @Override
    public String getActiveRegulation() {
        if (this.activeRegulation != null) {
            return this.activeRegulation;
        }
        if (this.getParent() != null) {
            return this.getParent().getActiveRegulation();
        }
        return null;
    }
    
    public void setActiveRegulation(final String value) {
        this.activeRegulation = value;
    }
    
    @Override
    public LogPropogateMode getPropogateLogMode() {
        return LogPropogateMode.all_level;
    }
    
    @Override
    public String getLog() {
        return this.log.toString();
    }
    
    @Override
    public void log(final Level level, final String message, final Throwable exception, final LogPropogateMode propogateMode) {
        this.log.append(String.format("%n%s: %s", (level == null) ? "" : level.name(), message));
        if (exception != null) {
            this.log.append(String.format("%n%s", ExceptionUtils.getFullStackTrace(exception)));
        }
        if (LogPropogateMode.gt(propogateMode, LogPropogateMode.current) && this.parent != null) {
            this.parent.trace(message, exception, propogateMode);
        }
    }
    
    public List<IEdoIntegrationStateMapping> getDocumentRemoteMappingList() {
        return this.documentRemoteMappingList;
    }
    
    public void setDocumentRemoteMappingList(final List<IEdoIntegrationStateMapping> documentRemoteMappingList) {
        this.documentRemoteMappingList = documentRemoteMappingList;
    }
    
    @Override
    public void setLastError(final EdoIntegrationSubsystemErrorCode value) {
        this.lastError = value;
    }
    
    @Override
    public EdoIntegrationSubsystemErrorCode getLastError() {
        return this.lastError;
    }
    
    @Override
    public IIntegrationLogDocumentInfo getIntegrationLogContextInfo() {
        return new IIntegrationLogDocumentInfo() {
            @Override
            public String getObjectId() {
                return IntegrationOperationContext.this.getDocumentId();
            }
            
            @Override
            public String getFilialId() {
                return IntegrationOperationContext.this.getFilialId();
            }
            
            @Override
            public String getFilialName() {
                return IntegrationOperationContext.this.getFilialName();
            }
        };
    }
    
    @Override
    public void trace(final String message, final Throwable exception, final LogPropogateMode propogateMode) {
        this.log(Level.TRACE, message, exception, propogateMode);
    }
    
    @Override
    public void warn(final String message, final Throwable exception, final LogPropogateMode propogateMode) {
        this.log(Level.WARN, message, exception, propogateMode);
    }
    
    @Override
    public void error(final String message, final Throwable exception, final LogPropogateMode propogateMode) {
        this.log(Level.ERROR, message, exception, propogateMode);
    }
    
    protected final IOperationLogWriter initLogWriter() {
        return new IOperationLogWriter() {
            @Override
            public void warn(final Logger logger, final Supplier<String> messageSupplier) {
                this.warn(logger, messageSupplier, null, IntegrationOperationContext.this.getPropogateLogMode());
            }
            
            @Override
            public void warn(final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode logPropogateMode) {
                this.warn(logger, messageSupplier, null, logPropogateMode);
            }
            
            @Override
            public void warn(final Logger logger, final Supplier<String> messageSupplier, final Throwable exp) {
                this.warn(logger, messageSupplier, exp, IntegrationOperationContext.this.getPropogateLogMode());
            }
            
            @Override
            public void warn(final Logger logger, final Supplier<?> messageSupplier, final Throwable exp, final LogPropogateMode logPropogateMode) {
                if (logger != null) {
                    logger.warn((Supplier)messageSupplier, exp);
                }
                IntegrationOperationContext.this.warn((messageSupplier != null) ? Objects.toString(messageSupplier.get(), "") : "", exp, logPropogateMode);
            }
            
            @Override
            public void trace(final Logger logger, final Supplier<String> messageSupplier) {
                this.trace(logger, messageSupplier, null, IntegrationOperationContext.this.getPropogateLogMode());
            }
            
            @Override
            public void trace(final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode logPropogateMode) {
                this.trace(logger, messageSupplier, null, logPropogateMode);
            }
            
            @Override
            public void trace(final Logger logger, final Supplier<String> messageSupplier, final Throwable exp) {
                this.trace(logger, messageSupplier, exp, IntegrationOperationContext.this.getPropogateLogMode());
            }
            
            @Override
            public void trace(final Logger logger, final Supplier<?> messageSupplier, final Throwable exp, final LogPropogateMode logPropogateMode) {
                if (logger != null) {
                    logger.trace((Supplier)messageSupplier, exp);
                }
                IntegrationOperationContext.this.trace((messageSupplier != null) ? Objects.toString(messageSupplier.get(), "") : "", exp, logPropogateMode);
            }
            
            @Override
            public void error(final Logger logger, final Supplier<String> messageSupplier) {
                this.error(logger, messageSupplier, null, IntegrationOperationContext.this.getPropogateLogMode());
            }
            
            @Override
            public void error(final Logger logger, final Supplier<String> messageSupplier, final LogPropogateMode logPropogateMode) {
                this.error(logger, messageSupplier, null, logPropogateMode);
            }
            
            @Override
            public void error(final Logger logger, final Supplier<String> messageSupplier, final Throwable exp) {
                this.error(logger, messageSupplier, exp, LogPropogateMode.all_level);
            }
            
            @Override
            public void error(final Logger logger, final Supplier<?> messageSupplier, final Throwable exp, final LogPropogateMode logPropogateMode) {
                if (logger != null) {
                    logger.error((Supplier)messageSupplier, exp);
                }
                IntegrationOperationContext.this.error((messageSupplier != null) ? Objects.toString(messageSupplier.get(), "") : "", exp, logPropogateMode);
            }
        };
    }
    
    public enum ConnectionOptions
    {
        dontCheckIncoming;
    }
}
