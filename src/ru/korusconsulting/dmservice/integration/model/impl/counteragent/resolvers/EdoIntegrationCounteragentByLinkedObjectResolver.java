// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.model.impl.counteragent.resolvers;

import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.Collection;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectObjectRule;
import java.util.Collections;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentObjectSelectRule;
import ru.korusconsulting.dmservice.integration.model.impl.attribute.resolvers.EdoIntegrationLinkedObjectResolver;

public class EdoIntegrationCounteragentByLinkedObjectResolver extends EdoIntegrationLinkedObjectResolver
{
    public EdoIntegrationCounteragentByLinkedObjectResolver(final IEdoIntegrationCounteragentObjectSelectRule config) {
        super(config, (Collection<IObjectModel>)Collections.emptyList(), null);
    }
    
    public List<IObjectModel> getCounteragents(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final SessionContext ctx) {
        return super.getObjects(session, operationContext, parentAttributeValuesGetter, ctx);
    }
}
