// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundRequestOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationOutboundRequestOperationScopedConfiguration extends EdoIntegrationScopedConfiguration implements IEdoIntegrationOutboundRequestOperationScopedConfiguration
{
    private static final long serialVersionUID = 6555040649091399353L;
    public static final String CONFIG_ID = "outbound_request_operation_scoped";
    
    public EdoIntegrationOutboundRequestOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
