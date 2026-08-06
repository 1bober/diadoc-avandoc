// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationQuerySelectCondition;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationAttributeSelectCondition;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoOutboundMapping;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationOutboundAttributeMappingByLinkedObject;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoOutboundAttributeMappingByLinkedObject extends EdoIntegrationOutboundAttributeMappingByLinkedObject implements IDiadocEdoOutboundMapping
{
    private static final long serialVersionUID = 3364332420985431050L;
    public static final String CONFIG_ID = "linked_object_mapping";
    
    private static String getConditionsConfigGroupId() {
        return String.join(".", DiadocEdoOutboundAttributeMappingByLinkedObject.class.getName(), "conditions");
    }
    
    public DiadocEdoOutboundAttributeMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getConditionsConfigGroupId(), component.getComponentType(), xmlTagId);
        if (condition != null) {
            condition.unmarshal(session, component, generator, ctx);
        }
        return condition;
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getConditionsConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (condition != null) {
            condition.unmarshal(session, el, ctx);
        }
        return condition;
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, el, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, component, xmlTagId, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    static {
        registerChildConfigTagMapping(getConditionsConfigGroupId(), "cstm_integration_sbis_edo_outbound_container_attribute_filter", "attribute_select_condition", (Class)EdoIntegrationAttributeSelectCondition.class);
        registerChildConfigTagMapping(getConditionsConfigGroupId(), "cstm_integration_sbis_edo_outbound_container_query_filter", "query_select_condition", (Class)EdoIntegrationQuerySelectCondition.class);
    }
}
