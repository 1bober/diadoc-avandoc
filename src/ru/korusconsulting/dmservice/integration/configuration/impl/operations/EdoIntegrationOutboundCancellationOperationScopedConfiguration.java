// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationOutboundCancellationOperationScopedConfiguration extends EdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = 7655825733189325303L;
    public static final String CONFIG_ID = "outbound_cancellation_operation_scoped";
    
    public EdoIntegrationOutboundCancellationOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
