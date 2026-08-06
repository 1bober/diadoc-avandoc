// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers;

import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.dmservice.integration.model.impl.attribute.resolvers.EdoIntegrationAttributesByLinkedObjectResolver;

public class DiadocEdoAttributesByLinkedObjectResolver extends EdoIntegrationAttributesByLinkedObjectResolver
{
    public DiadocEdoAttributesByLinkedObjectResolver(final IEdoIntegrationOutboundMappingByLinkedObject config, final IEdoIntegrationAttributesResolverFactory factory) {
        super(config, factory);
    }
}
