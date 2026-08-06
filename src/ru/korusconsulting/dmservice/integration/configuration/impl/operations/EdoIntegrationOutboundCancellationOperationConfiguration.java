// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundCancellationOperationConfiguration;

public abstract class EdoIntegrationOutboundCancellationOperationConfiguration extends EdoIntegrationDocumentOperationConfiguration implements IEdoIntegrationOutboundCancellationOperationConfiguration
{
    private static final long serialVersionUID = -1634560789920818587L;
    
    protected EdoIntegrationOutboundCancellationOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
