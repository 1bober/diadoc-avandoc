// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model.attribute.resolvers;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IEdoIntegrationAttributesByAttributeResolver
{
    void resolve(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> p2, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> p3, final SessionContext p4);
}
