// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.incoming;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.diadocedo.configuration.incoming.IDiadocEdoIncomingDocumentReceiveScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationIncomingDocumentReceiveScopedConfiguration;

public class DiadocEdoIncomingDocumentReceiveScopedConfiguration extends EdoIntegrationIncomingDocumentReceiveScopedConfiguration implements IDiadocEdoIncomingDocumentReceiveScopedConfiguration
{
    private static final long serialVersionUID = 3127379201136244160L;
    public static final String CONFIG_ID = "diadoc_edo_inbound_scoped";
    
    public DiadocEdoIncomingDocumentReceiveScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
