// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import Diadoc.Api.exceptions.DiadocSdkIncorrectConfigurationException;

public class DiadocEdoConfigurationProblemException extends DiadocEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = 2041966795573726790L;
    
    public DiadocEdoConfigurationProblemException(final String message) {
        super(message);
    }
    
    public DiadocEdoConfigurationProblemException(final String message, final DiadocSdkIncorrectConfigurationException ex) {
        super(message, (Throwable)ex);
    }
    
    public EdoIntegrationSubsystemErrorCode getErrorCode() {
        return EdoIntegrationSubsystemErrorCode.SYSTEM_UNCONFIGURED;
    }
}
