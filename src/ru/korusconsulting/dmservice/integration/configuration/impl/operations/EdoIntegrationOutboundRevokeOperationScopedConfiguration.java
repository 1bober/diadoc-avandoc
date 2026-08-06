// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationOutboundRevokeOperationScopedConfiguration extends EdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = 7139464818937200756L;
    public static final String CONFIG_ID = "outbound_revoke_operation_scoped";
    
    public EdoIntegrationOutboundRevokeOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
