// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.answer.EdoIntegrationAnswerMappingByCounteragent;

public class DiadocEdoAnswerMappingByCounteragent extends EdoIntegrationAnswerMappingByCounteragent
{
    private static final long serialVersionUID = -6915298401207663420L;
    public static final String CONFIG_ID = "mapped_by_counteragent";
    
    public DiadocEdoAnswerMappingByCounteragent(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    protected IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    protected IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, element, (ICstmConfigAdapter)this, ctx);
    }
}
