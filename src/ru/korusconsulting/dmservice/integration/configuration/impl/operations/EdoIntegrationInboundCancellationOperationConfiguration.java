// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationInboundCancellationOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationInboundCancellationOperationConfiguration extends EdoIntegrationDocumentOperationConfiguration implements IEdoIntegrationInboundCancellationOperationConfiguration
{
    private static final long serialVersionUID = -4521994759640799359L;
    
    protected EdoIntegrationInboundCancellationOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
