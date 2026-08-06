// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentWithMappingConfiguration;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByOperationContextAttributeResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;
import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationContextAttribute;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public abstract class EdoIntegrationDocumentAttributesMappingProcessor<T extends IIntegrationOperationContext> extends EdoIntegrationProcessor<T> implements IEdoIntegrationAttributesResolverFactory
{
    public void processAttributes(final DMSShardsClientSession session, final T operationContext, final IDocument document, final IEdoIntegrationExchangeOperationUnit exchangeUnit, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> targetWriter, final Map<String, Object> executionArguments, final SessionContext ctx) {
        if (this.getOperationConfig() != null) {
            final List<IEdoIntegrationAttributeMapping> mappingRules = this.getOperationConfig().getMapping();
            for (final IEdoIntegrationAttributeMapping mappingRule : mappingRules) {
                if (mappingRule instanceof final IEdoIntegrationOutboundAttributeMapping rule) {
                    final IEdoIntegrationAttributesByAttributeResolver resolver = this.createAttributeMappingResolver(rule, this);
                    resolver.resolve(session, operationContext, (documentSpec, attrName, attrType) -> {
                        if ("cstm_attr_type_date_time".equals(attrType)) {
                            return document.getValueAsDate(attrName);
                        }
                        else {
                            return document.getValueAsString(attrName);
                        }
                    }, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationAttributeMappingByFixedValue rule2) {
                    final IEdoIntegrationAttributesByFixedValueResolver resolver2 = this.createFixedValueResolver(rule2, this);
                    resolver2.resolve(session, operationContext, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationOutboundMappingByLinkedObject rule3) {
                    this.createByLinkedObjectMappingResolver(rule3, this);
                }
                else {
                    if (!(mappingRule instanceof IEdoIntegrationAttributeMappingByOperationContextAttribute)) {
                        throw new ServiceException(String.format("Unknown rule type %s", mappingRule.getClass().getName()));
                    }
                    final IEdoIntegrationAttributeMappingByOperationContextAttribute rule4 = (IEdoIntegrationAttributeMappingByOperationContextAttribute)mappingRule;
                    final IEdoIntegrationAttributesByOperationContextAttributeResolver resolver3 = this.createByOperationContextAttributeResolver(rule4, this);
                    resolver3.resolve(session, operationContext, targetWriter, ctx);
                }
            }
        }
    }
    
    @Override
    public IEdoIntegrationDocumentWithMappingConfiguration getOperationConfig() {
        return (IEdoIntegrationDocumentWithMappingConfiguration)super.getOperationConfig();
    }
}
