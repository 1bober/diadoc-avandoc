// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class DiadocEdoOutboundDocumentSendOperationScopedConfiguration extends EdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = -6012930785745122435L;
    public static final String CONFIG_ID = "send_operation_scoped";
    
    public DiadocEdoOutboundDocumentSendOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
