// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoAuthenticationFailedException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = -3490706115268074640L;
    
    public DiadocEdoAuthenticationFailedException(final String message, final DiadocEdoAccountsNotAvailable e) {
        super(message, e);
    }
}
