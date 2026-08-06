// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationRequestProcessingOperationScopedConfiguration extends EdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = -8174281510847057398L;
    public static final String CONFIG_ID = "arbitrary_operation_scoped";
    
    public EdoIntegrationRequestProcessingOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
