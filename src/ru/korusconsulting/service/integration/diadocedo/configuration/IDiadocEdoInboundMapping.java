// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IDiadocEdoInboundMapping extends ICstmConfigAdapter
{
    default IEdoIntegrationAttributeMapping createDocumentMappingImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    default IEdoIntegrationAttributeMapping createDocumentMappingImpl(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
}
