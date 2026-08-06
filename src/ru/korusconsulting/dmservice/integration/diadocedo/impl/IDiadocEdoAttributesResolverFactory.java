// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByOperationContextAttributeResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationContextAttribute;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers.DiadocEdoAttributesByLinkedObjectResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByLinkedObjectResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers.DiadocEdoAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers.DiadocEdoAttributesByAttributeResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;

public interface IDiadocEdoAttributesResolverFactory extends IEdoIntegrationAttributesResolverFactory
{
    default IEdoIntegrationAttributesByAttributeResolver createAttributeMappingResolver(final IEdoIntegrationOutboundAttributeMapping rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return (IEdoIntegrationAttributesByAttributeResolver)new DiadocEdoAttributesByAttributeResolver(rule, factory);
    }
    
    default IEdoIntegrationAttributesByFixedValueResolver createFixedValueResolver(final IEdoIntegrationAttributeMappingByFixedValue rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return (IEdoIntegrationAttributesByFixedValueResolver)new DiadocEdoAttributesByFixedValueResolver(rule, factory);
    }
    
    default IEdoIntegrationAttributesByLinkedObjectResolver createByLinkedObjectMappingResolver(final IEdoIntegrationOutboundMappingByLinkedObject rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return (IEdoIntegrationAttributesByLinkedObjectResolver)new DiadocEdoAttributesByLinkedObjectResolver(rule, factory);
    }
    
    default IEdoIntegrationAttributesByOperationContextAttributeResolver createByOperationContextAttributeResolver(final IEdoIntegrationAttributeMappingByOperationContextAttribute rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return null;
    }
}
