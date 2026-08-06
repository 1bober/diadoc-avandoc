// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.model.impl.attribute.resolvers;

import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByOperationContextAttributeResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;
import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationContextAttribute;
import java.util.Arrays;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.Collection;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectObjectRule;
import java.util.Collections;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByLinkedObjectResolver;

public class EdoIntegrationAttributesByLinkedObjectResolver extends EdoIntegrationLinkedObjectResolver implements IEdoIntegrationAttributesByLinkedObjectResolver
{
    public EdoIntegrationAttributesByLinkedObjectResolver(final IEdoIntegrationOutboundMappingByLinkedObject config, final IEdoIntegrationAttributesResolverFactory factory) {
        super(config, (Collection<IObjectModel>)Collections.emptyList(), factory);
    }
    
    public EdoIntegrationAttributesByLinkedObjectResolver(final IEdoIntegrationOutboundMappingByLinkedObject config, final Collection<IObjectModel> linked, final IEdoIntegrationAttributesResolverFactory factory) {
        super(config, linked, factory);
    }
    
    @Override
    public IEdoIntegrationOutboundMappingByLinkedObject getConfig() {
        return (IEdoIntegrationOutboundMappingByLinkedObject)super.getConfig();
    }
    
    @Override
    public void resolve(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> targetWriter, final SessionContext ctx) {
        final List<IObjectModel> objects = this.getObjects(session, operationContext, parentAttributeValuesGetter, ctx);
        for (final IObjectModel subjectObject : objects) {
            final List<IEdoIntegrationAttributeMapping> mappingRules = this.getConfig().getMapping();
            for (final IEdoIntegrationAttributeMapping mappingRule : mappingRules) {
                if (mappingRule instanceof final IEdoIntegrationOutboundAttributeMapping rule) {
                    final IEdoIntegrationAttributesByAttributeResolver resolver = this.getFactory().createAttributeMappingResolver(rule, this.getFactory());
                    resolver.resolve(session, operationContext, (documentSpec, attrName, attrType) -> {
                        if ("cstm_attr_type_date_time".equals(attrType)) {
                            return subjectObject.getValueAsDate(attrName);
                        }
                        else {
                            return subjectObject.getValueAsString(attrName);
                        }
                    }, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationAttributeMappingByFixedValue rule2) {
                    final IEdoIntegrationAttributesByFixedValueResolver resolver2 = this.getFactory().createFixedValueResolver(rule2, this.getFactory());
                    resolver2.resolve(session, operationContext, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationOutboundMappingByLinkedObject rule3) {
                    final IEdoIntegrationAttributesByLinkedObjectResolver resolver3 = this.getFactory().createByLinkedObjectMappingResolver(rule3, this.getFactory());
                    resolver3.resolve(session, operationContext, (documentSpec, attrName, attrType) -> {
                        if (documentSpec == AttributeValueSourceTypes.SUBJECT) {
                            return Arrays.asList(subjectObject.getValueAsString(attrName));
                        }
                        else {
                            return parentAttributeValuesGetter.apply(documentSpec, attrName, attrType);
                        }
                    }, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationAttributeMappingByOperationContextAttribute rule4) {
                    final IEdoIntegrationAttributesByOperationContextAttributeResolver resolver4 = this.getFactory().createByOperationContextAttributeResolver(rule4, this.getFactory());
                    resolver4.resolve(session, operationContext, targetWriter, ctx);
                }
                else {
                    if (mappingRule != null) {
                        throw new ServiceException(String.format("Unknown mapping rule type %s", mappingRule.getClass().getName()));
                    }
                    continue;
                }
            }
        }
    }
}
