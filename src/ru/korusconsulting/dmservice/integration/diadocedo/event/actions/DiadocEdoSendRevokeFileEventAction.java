// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.event.actions;

import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.event.EventAction;

public class DiadocEdoSendRevokeFileEventAction extends EventAction
{
    public Object execute(final DMSShardsClientSession session, final Map<String, Object> args, final SessionContext ctx) {
        final IDocument document = args.get(this.getLinkConfig().getAttributeValue("documentarg"));
        DiadocEdoServiceReg.getDiadocEdoIntegrationService().queueOutgoingRevokeFile(session, document, this.getEventParams(), ctx);
        return null;
    }
}
