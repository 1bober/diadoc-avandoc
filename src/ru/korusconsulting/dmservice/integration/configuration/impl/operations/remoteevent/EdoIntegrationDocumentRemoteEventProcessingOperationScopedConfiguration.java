// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.remoteevent;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationScopedConfiguration;

public class EdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration extends EdoIntegrationScopedConfiguration implements IEdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration
{
    private static final long serialVersionUID = 7393482207531814676L;
    public static final String CONFIG_ID = "remote_event_operation";
    
    public EdoIntegrationDocumentRemoteEventProcessingOperationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
