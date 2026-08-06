// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.model.impl.attribute.resolvers;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;

public abstract class EdoIntegrationAttributesByFixedValueResolver implements IEdoIntegrationAttributesByFixedValueResolver
{
    private final IEdoIntegrationInboundAttributeMappingByFixedValue config;
    
    protected EdoIntegrationAttributesByFixedValueResolver(final IEdoIntegrationInboundAttributeMappingByFixedValue config) {
        this.config = config;
    }
    
    public IEdoIntegrationInboundAttributeMappingByFixedValue getConfig() {
        return this.config;
    }
}
