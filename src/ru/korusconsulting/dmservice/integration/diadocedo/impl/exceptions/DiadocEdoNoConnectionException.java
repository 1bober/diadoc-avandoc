// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;

public class DiadocEdoNoConnectionException extends DiadocEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = -8225757520444034639L;
    
    public DiadocEdoNoConnectionException(final String message) {
        super(message);
    }
    
    public EdoIntegrationSubsystemErrorCode getErrorCode() {
        return EdoIntegrationSubsystemErrorCode.NO_CONNECTION_SETTINGS_ERROR;
    }
}
