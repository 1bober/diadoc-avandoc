// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.impl.exceptions.IEdoIntegrationSubsystemErrorException;

public abstract class DiadocEdoIntegrationSubsystemErrorException extends DiadocEdoIntegrationException implements IEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = 3362577886521087911L;
    
    protected DiadocEdoIntegrationSubsystemErrorException(final String message) {
        super(message);
    }
    
    protected DiadocEdoIntegrationSubsystemErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public IntegrationLogRecordMessage getIntegrationLogMessage() {
        return null;
    }
}
