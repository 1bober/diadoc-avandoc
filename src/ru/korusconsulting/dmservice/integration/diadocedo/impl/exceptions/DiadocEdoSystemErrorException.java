// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;

public class DiadocEdoSystemErrorException extends DiadocEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = -3542286475693881609L;
    
    public DiadocEdoSystemErrorException(final String message) {
        super(message);
    }
    
    public DiadocEdoSystemErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public EdoIntegrationSubsystemErrorCode getErrorCode() {
        return EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR;
    }
}
