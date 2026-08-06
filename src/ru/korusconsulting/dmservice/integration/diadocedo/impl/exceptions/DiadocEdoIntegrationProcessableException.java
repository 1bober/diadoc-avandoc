// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import Diadoc.Api.exceptions.DiadocException;
import Diadoc.Api.exceptions.KonturHttpException;
import Diadoc.Api.exceptions.DiadocSdkErrorWithCodeException;
import ru.korusconsulting.service.integration.exceptions.IProcessableException;

public class DiadocEdoIntegrationProcessableException extends DiadocEdoIntegrationException implements IProcessableException
{
    private static final long serialVersionUID = -4994400734939365613L;
    private final int code;
    private final String reason;
    private final String traceId;
    
    public DiadocEdoIntegrationProcessableException(final DiadocSdkErrorWithCodeException ex) {
        super((Throwable)ex);
        this.code = ex.getStatusCode();
        this.reason = ex.getReasonPhrase();
        this.traceId = ex.getTraceId();
    }
    
    public DiadocEdoIntegrationProcessableException(final String message, final KonturHttpException cause) {
        super(message, (Throwable)cause);
        if (cause != null) {
            this.code = cause.getStatusCode();
            this.reason = cause.getReasonPhrase();
            this.traceId = cause.getTraceId();
        }
        else {
            this.code = -1;
            this.reason = null;
            this.traceId = null;
        }
    }
    
    public DiadocEdoIntegrationProcessableException(final String message, final DiadocException cause) {
        super(message, (Throwable)cause);
        if (cause != null) {
            this.code = cause.getHttpStatusCode();
            this.reason = cause.getErrorCode();
            this.traceId = cause.getTraceId();
        }
        else {
            this.code = -1;
            this.reason = null;
            this.traceId = null;
        }
    }
    
    public int getCode() {
        return this.code;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public String getTraceId() {
        return this.traceId;
    }
}
