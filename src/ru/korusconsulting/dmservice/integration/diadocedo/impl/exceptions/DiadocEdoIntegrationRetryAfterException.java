// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import Diadoc.Api.exceptions.DiadocSdkRetryAfterException;

public class DiadocEdoIntegrationRetryAfterException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = -5906140753117897984L;
    private final int retryAfter;
    
    public DiadocEdoIntegrationRetryAfterException(final DiadocSdkRetryAfterException cause) {
        super("", (Throwable)cause);
        this.retryAfter = ((cause != null) ? cause.getRetryAfter() : 1);
    }
    
    public int getRetryAfter() {
        return this.retryAfter;
    }
}
