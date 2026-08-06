// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers;

import java.util.Iterator;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import java.util.Arrays;
import ru.korusconsulting.service.integration.configuration.IEdoDocumentAttributeDesc;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;

public class DiadocEdoAttributesByFixedValueResolver implements IEdoIntegrationAttributesByFixedValueResolver
{
    private final IEdoIntegrationAttributeMappingByFixedValue config;
    private final IEdoIntegrationAttributesResolverFactory factory;
    
    public DiadocEdoAttributesByFixedValueResolver(final IEdoIntegrationAttributeMappingByFixedValue config, final IEdoIntegrationAttributesResolverFactory factory) {
        this.config = config;
        this.factory = factory;
    }
    
    public IEdoIntegrationAttributeMappingByFixedValue getConfig() {
        return this.config;
    }
    
    public IEdoIntegrationAttributesResolverFactory getFactory() {
        return this.factory;
    }
    
    public void resolve(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> targetWriter, final SessionContext ctx) {
        final IEdoIntegrationDocumentOperationConfiguration externalDocumentConfig = this.getConfig().getDocumentOperationConfig();
        if (externalDocumentConfig instanceof final IDiadocEdoOutboundDocumentOperationConfiguration diadocDocumentConfig) {
            for (final IEdoDocumentAttributeDesc attr : diadocDocumentConfig.getEdoDocumentAttributes()) {
                if (this.getConfig().getRemoteAttribute().equals(attr.getName())) {
                    if (attr.getDataType() == EdoIntegrationDocumentAttributeDataType.BOOL) {
                        targetWriter.accept((Object)attr.getName(), (Object)attr.getDataType(), (Object)Boolean.valueOf(this.getConfig().getValue()));
                    }
                    else {
                        targetWriter.accept((Object)this.getConfig().getRemoteAttribute(), (Object)attr.getDataType(), (Object)Arrays.asList(this.getConfig().getValue()));
                    }
                }
            }
        }
    }
}
