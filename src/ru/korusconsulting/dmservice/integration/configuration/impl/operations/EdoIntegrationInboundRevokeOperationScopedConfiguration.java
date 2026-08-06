// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationInboundRevokeOperationScopedConfiguration extends EdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = -109360472738546491L;
    public static final String CONFIG_ID = "inbound_revoke_operation_scoped";
    
    public EdoIntegrationInboundRevokeOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
