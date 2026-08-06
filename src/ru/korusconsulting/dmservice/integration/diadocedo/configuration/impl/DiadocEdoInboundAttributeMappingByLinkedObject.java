// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationQuerySelectCondition;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationAttributeSelectCondition;
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
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationInboundAttributeMappingByLinkedObject;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundAttributeMappingByLinkedObject extends EdoIntegrationInboundAttributeMappingByLinkedObject implements IDiadocEdoOutboundMapping
{
    private static final long serialVersionUID = 3500181321173909039L;
    public static final String CONFIG_ID = "cstm_select_expression_editor";
    
    private static String getSelectRuleConfigGroupId() {
        return String.join(".", DiadocEdoInboundAttributeMappingByLinkedObject.class.getName(), "select_rule");
    }
    
    public DiadocEdoInboundAttributeMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getSelectRuleConfigGroupId(), component.getComponentType(), xmlTagId);
        if (condition != null) {
            condition.unmarshal(session, component, generator, ctx);
        }
        return condition;
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getSelectRuleConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (condition != null) {
            condition.unmarshal(session, el, ctx);
        }
        return condition;
    }
    
    public String getObjectType() {
        return (this.getObjectTypeStorage() != null) ? this.getObjectTypeStorage().getValue() : null;
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, component, xmlTagId, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, el, ctx);
    }
    
    static {
        registerChildConfigTagMapping(getSelectRuleConfigGroupId(), "cstm_integration_sbis_edo_outbound_container_attribute_filter", "attribute_select_condition", (Class)EdoIntegrationAttributeSelectCondition.class);
        registerChildConfigTagMapping(getSelectRuleConfigGroupId(), "cstm_integration_sbis_edo_outbound_container_query_filter", "query_select_condition", (Class)EdoIntegrationQuerySelectCondition.class);
    }
}
