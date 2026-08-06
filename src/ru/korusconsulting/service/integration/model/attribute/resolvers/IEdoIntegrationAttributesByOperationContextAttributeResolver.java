// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model.attribute.resolvers;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IEdoIntegrationAttributesByOperationContextAttributeResolver
{
    void resolve(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> p2, final SessionContext p3);
}
