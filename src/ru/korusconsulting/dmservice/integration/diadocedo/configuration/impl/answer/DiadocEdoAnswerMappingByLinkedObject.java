// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.answer.EdoIntegrationAnswerMappingByLinkedObject;

public class DiadocEdoAnswerMappingByLinkedObject extends EdoIntegrationAnswerMappingByLinkedObject
{
    private static final long serialVersionUID = -65459695342103324L;
    public static final String CONFIG_ID = "mapped_by_linked";
    
    public DiadocEdoAnswerMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
}
