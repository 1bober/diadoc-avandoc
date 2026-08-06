// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;

public class DiadocEdoCounteragentNotExistsException extends DiadocEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = 5124955661204217232L;
    
    public DiadocEdoCounteragentNotExistsException(final String message) {
        super(message);
    }
    
    public EdoIntegrationSubsystemErrorCode getErrorCode() {
        return EdoIntegrationSubsystemErrorCode.NO_COUNTERAGENT;
    }
}
