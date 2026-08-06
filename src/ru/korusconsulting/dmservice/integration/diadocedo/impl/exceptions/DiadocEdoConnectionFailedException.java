// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoConnectionFailedException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = 1337034642292740265L;
    
    public DiadocEdoConnectionFailedException(final String message) {
        super(message);
    }
    
    public DiadocEdoConnectionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
