// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import Diadoc.Api.exceptions.DiadocSdkUnparsableResultException;

public class DiadocEdoIntegrationUnparsableResultException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = -7076418038063661134L;
    
    public DiadocEdoIntegrationUnparsableResultException(final DiadocSdkUnparsableResultException cause) {
        super((Throwable)cause);
    }
    
    public DiadocEdoIntegrationUnparsableResultException(final String message) {
        super(message);
    }
    
    public byte[] getResponse() {
        final Throwable cause = this.getCause();
        byte[] response;
        if (cause instanceof final DiadocSdkUnparsableResultException c) {
            response = c.getResponse();
        }
        else {
            response = null;
        }
        return response;
    }
}
