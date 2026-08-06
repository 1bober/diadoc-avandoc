// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoAccessDeniedException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = 8431087217099989135L;
    
    public DiadocEdoAccessDeniedException(final String message) {
        super(message);
    }
    
    public DiadocEdoAccessDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
