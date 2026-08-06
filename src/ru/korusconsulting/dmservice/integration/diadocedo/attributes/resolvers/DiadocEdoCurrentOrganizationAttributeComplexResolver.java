// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.attributes.resolvers;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoBoxDesc;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.ws.common.model.IInboundDocument;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.integration.attributes.resolvers.AbstractComplexResolver;

public class DiadocEdoCurrentOrganizationAttributeComplexResolver extends AbstractComplexResolver
{
    protected Logger createLogger() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public Object resolve(final DMSShardsClientSession session, final String key, final IDocument document, final IInboundDocument incoming, final ConfigElement attrConfig, final ConfigElement systemConfig, final String inboundAttr, final SessionContext context) {
        final DiadocEdoBoxDesc box = (DiadocEdoBoxDesc)context.get("box");
        return box.getSpitnikOrgId();
    }
}
