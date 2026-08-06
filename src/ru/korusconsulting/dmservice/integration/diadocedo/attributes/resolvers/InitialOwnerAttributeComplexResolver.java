// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.attributes.resolvers;

import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoBoxDesc;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.ws.common.model.IInboundDocument;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.integration.attributes.resolvers.AbstractComplexResolver;

public class InitialOwnerAttributeComplexResolver extends AbstractComplexResolver
{
    protected Logger createLogger() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public Object resolve(final DMSShardsClientSession session, final String key, final IDocument document, final IInboundDocument incoming, final ConfigElement attrConfig, final ConfigElement systemConfig, final String inboundAttr, final SessionContext context) {
        final String departmentExternalCode = (String)incoming.getValue(inboundAttr);
        final List<ConfigElement> queriesElement = attrConfig.getChildren("queries");
        if (queriesElement.isEmpty()) {
            this.getLogger().warn(() -> String.format("Failed to find %s tag in config for attribute %s", "queries", attrConfig.getAttributeValue("id")));
        }
        String departmentQuery = null;
        String userQuery = null;
        for (final ConfigElement queryElement : queriesElement.get(0).getChildren("query")) {
            if (StringUtils.equals("department_query", queryElement.getAttributeValue("key"))) {
                departmentQuery = queryElement.getElementValue();
                if (userQuery != null) {
                    break;
                }
                continue;
            }
            else {
                if (!StringUtils.equals("user_query", queryElement.getAttributeValue("key"))) {
                    continue;
                }
                userQuery = queryElement.getElementValue();
                if (departmentQuery != null) {
                    break;
                }
                continue;
            }
        }
        if (StringUtils.isEmpty(departmentQuery)) {
            this.getLogger().warn("Failed to find [{}] tag with id {} in config for attribute {}", (Object)"queries.query", (Object)"department_query", (Object)attrConfig.getAttributeValue("id"));
            return null;
        }
        if (StringUtils.isEmpty(userQuery)) {
            this.getLogger().warn("Failed to find [{}] tag with id {} in config for attribute {}", (Object)"queries.query", (Object)"user_query", (Object)attrConfig.getAttributeValue("id"));
            return null;
        }
        String departmentId;
        if ("00000000-0000-0000-0000-000000000000".equals(departmentExternalCode)) {
            final DiadocEdoBoxDesc boxDesc = (DiadocEdoBoxDesc)context.get("box");
            departmentId = boxDesc.getSpitnikOrgId();
        }
        else {
            final String dQuery = String.format(departmentQuery, departmentExternalCode);
            this.getLogger().trace(() -> String.format("Running department query [%s]", dQuery));
            departmentId = (String)new ShardSessionRunner(() -> ServiceReg.getQueryService().getFirstValueFromQuery(session, dQuery, context)).start(DMSShardsManager.SHARD.DICT, context);
            this.getLogger().warn(() -> String.format("Failed to find department with diadoc code [%s] ", departmentExternalCode));
        }
        final String uQuery = String.format(userQuery, departmentId);
        this.getLogger().trace(() -> String.format("Running user query [%s]", uQuery));
        return new ShardSessionRunner(() -> ServiceReg.getQueryService().getFirstValueFromQuery(session, uQuery, context)).start(DMSShardsManager.SHARD.DICT, context);
    }
}
