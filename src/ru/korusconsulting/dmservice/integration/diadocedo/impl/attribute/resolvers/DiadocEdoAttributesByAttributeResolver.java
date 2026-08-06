// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.attribute.resolvers;

import java.util.Iterator;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.korusconsulting.service.integration.configuration.IEdoDocumentAttributeDesc;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoOutboundAttributeMappingByAttribute;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;

public class DiadocEdoAttributesByAttributeResolver implements IEdoIntegrationAttributesByAttributeResolver
{
    Logger logger;
    private final IEdoIntegrationOutboundAttributeMapping config;
    private final IEdoIntegrationAttributesResolverFactory factory;
    
    public DiadocEdoAttributesByAttributeResolver(final IEdoIntegrationOutboundAttributeMapping config, final IEdoIntegrationAttributesResolverFactory factory) {
        this.logger = LogManager.getLogger();
        this.config = config;
        this.factory = factory;
    }
    
    public IEdoIntegrationOutboundAttributeMapping getConfig() {
        return this.config;
    }
    
    protected IEdoIntegrationAttributesResolverFactory getFactory() {
        return this.factory;
    }
    
    public void resolve(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> targetWriter, final SessionContext ctx) {
        final IEdoIntegrationOutboundAttributeMapping config = this.getConfig();
        if (config instanceof final IDiadocEdoOutboundAttributeMappingByAttribute resolverConfig) {
            if (resolverConfig.getRemoteAttribute() != null) {
                final IEdoIntegrationDocumentOperationConfiguration externalDocumentConfig = this.getConfig().getDocumentOperationConfig();
                if (externalDocumentConfig instanceof final IDiadocEdoOutboundDocumentOperationConfiguration diadocDocumentConfig) {
                    for (final IEdoDocumentAttributeDesc attr : diadocDocumentConfig.getEdoDocumentAttributes()) {
                        this.logger.trace(() -> String.format("Desired diadoc attribute is %s, checked attribute is %s", resolverConfig.getRemoteAttribute(), attr.getName()));
                        if (resolverConfig.getRemoteAttribute().equals(attr.getName())) {
                            final Object tmp = parentAttributeValuesGetter.apply((Object)AttributeValueSourceTypes.SUBJECT, (Object)this.getConfig().getAttribute(), (Object)this.getConfig().getAttributeType());
                            if (tmp != null) {
                                if (attr.getDataType() == EdoIntegrationDocumentAttributeDataType.DATE && tmp instanceof Date) {
                                    final Date tmpDate = (Date)tmp;
                                    targetWriter.accept((Object)attr.getName(), (Object)attr.getDataType(), (Object)new SimpleDateFormat("dd.MM.yyyy").format(tmpDate));
                                }
                                else if (attr.getDataType() == EdoIntegrationDocumentAttributeDataType.BOOL) {
                                    if (tmp instanceof final Boolean tmpB) {
                                        targetWriter.accept((Object)attr.getName(), (Object)attr.getDataType(), (Object)tmpB);
                                    }
                                    else if (tmp instanceof final String tmpS) {
                                        targetWriter.accept((Object)attr.getName(), (Object)attr.getDataType(), (Object)Boolean.valueOf(tmpS));
                                    }
                                }
                                else {
                                    targetWriter.accept((Object)attr.getName(), (Object)attr.getDataType(), (Object)String.valueOf(tmp));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
