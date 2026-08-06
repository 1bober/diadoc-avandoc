// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public class DiadocEdoChangeExchangeStateEventActionGenerator extends DiadocEdoTemplateEventActionGenerator
{
    public String processEventTemplate(final DMSShardsClientSession session, final String templateSrc, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        String template = super.processEventTemplate(session, templateSrc, cstmObject, configElement, ctx);
        final String eligibleLocalState = StringUtils.trimToEmpty(cstmObject.getValueAsString("ssa_local_state"));
        final String desiredLocalState = StringUtils.trimToEmpty(cstmObject.getValueAsString("ssa_new_local_state"));
        final String desiredStateCategory = StringUtils.trimToEmpty(cstmObject.getValueAsString("ssa_state_category"));
        template = template.replace("%local_states%", eligibleLocalState);
        template = template.replace("%new_local_state%", desiredLocalState);
        template = template.replace("%new_state_category%", desiredStateCategory);
        return template;
    }
}
