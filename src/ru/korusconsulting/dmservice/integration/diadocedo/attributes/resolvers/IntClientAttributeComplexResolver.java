// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.attributes.resolvers;

import Diadoc.Api.Proto.CounteragentProtos;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoCounteragent;
import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import ru.korusconsulting.service.utils.ServiceReg;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoBoxDesc;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.ws.common.model.IInboundDocument;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.integration.attributes.resolvers.AbstractComplexResolver;

public class IntClientAttributeComplexResolver extends AbstractComplexResolver
{
    protected Logger createLogger() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public Object resolve(final DMSShardsClientSession session, final String key, final IDocument document, final IInboundDocument incoming, final ConfigElement attrConfig, final ConfigElement systemConfig, final String inboundAttr, final SessionContext context) {
        final DiadocEdoBoxDesc boxDesc = (DiadocEdoBoxDesc)context.get("box");
        if (boxDesc == null) {
            return null;
        }
        String counteragentQuery = null;
        final List<ConfigElement> queriesElement = attrConfig.getChildren("queries");
        if (queriesElement.isEmpty()) {
            this.getLogger().warn(() -> String.format("Failed to find [%s] tag in config for attribute %s", "queries", attrConfig.getAttributeValue("id")));
        }
        for (final ConfigElement queryElement : queriesElement.get(0).getChildren("query")) {
            if (StringUtils.equals("counteragent_query", queryElement.getAttributeValue("key"))) {
                counteragentQuery = queryElement.getElementValue();
                break;
            }
        }
        if (StringUtils.isEmpty(counteragentQuery)) {
            this.getLogger().warn(() -> String.format("Failed to find [%s] tag with id %s in config for attribute %s", "queries.query", "counteragent_query", attrConfig.getAttributeValue("id")));
            return null;
        }
        final String counteragentBoxId = (String)incoming.getValue(inboundAttr);
        final DiadocEdoCounteragent cnt = boxDesc.getCounteragentByBoxId(counteragentBoxId);
        final CounteragentProtos.Counteragent c = cnt.getCounteragent();
        final String fullName = c.getOrganization().getFullName();
        final String shortName = c.getOrganization().getShortName();
        final String inn = c.getOrganization().getInn();
        final String kpp = c.getOrganization().getKpp();
        final String query = String.format(counteragentQuery, fullName, shortName, inn, kpp);
        final String s = (String)new ShardSessionRunner(() -> ServiceReg.getQueryService().getFirstValueFromQuery(session, query, context)).start(DMSShardsManager.SHARD.DICT, context);
        this.getLogger().trace(() -> String.format("Resolved %s as %s", inboundAttr, s));
        return s;
    }
}
