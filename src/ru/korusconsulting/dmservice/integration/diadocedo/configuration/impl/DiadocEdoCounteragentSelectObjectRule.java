// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationAttributeSelectCondition;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationCounteragentSelectObjectRule;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoCounteragentSelectObjectRule extends EdoIntegrationCounteragentSelectObjectRule
{
    private static final long serialVersionUID = 4575484639732499773L;
    
    private static String getSelectConditionConfigGroupId() {
        return String.join(".", DiadocEdoCounteragentSelectObjectRule.class.getName(), "select_condition");
    }
    
    public DiadocEdoCounteragentSelectObjectRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationDocumentSelectCondition createSelectCondition(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getSelectConditionConfigGroupId(), component.getComponentType(), xmlTagId);
        if (condition != null) {
            condition.unmarshal(session, component, generator, ctx);
        }
        return condition;
    }
    
    public IEdoIntegrationDocumentSelectCondition createSelectCondition(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationDocumentSelectCondition condition = (IEdoIntegrationDocumentSelectCondition)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getSelectConditionConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (condition != null) {
            condition.unmarshal(session, el, ctx);
        }
        return condition;
    }
    
    static {
        registerChildConfigTagMapping(getSelectConditionConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_object_attribute_filter", "attribute_select_condition", (Class)EdoIntegrationAttributeSelectCondition.class);
    }
}
