// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoIntegrationException extends Exception
{
    private static final long serialVersionUID = -2306165773374435412L;
    
    public DiadocEdoIntegrationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public DiadocEdoIntegrationException(final String message) {
        super(message);
    }
    
    public DiadocEdoIntegrationException(final Throwable cause) {
        super(cause);
    }
}
