// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.event.actions;

import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.event.EventAction;

public class DiadocEdoLogEventAction extends EventAction
{
    private Logger logger;
    
    public DiadocEdoLogEventAction() {
        this.logger = LogManager.getLogger();
    }
    
    public Object execute(final DMSShardsClientSession session, final Map<String, Object> args, final SessionContext ctx) {
        final IDocument document = args.get(this.getLinkConfig().getAttributeValue("documentarg"));
        System.err.println(String.format("Message from LogDiadocEdoEventAction for document %s", document.getObjectId()));
        this.logger.error(() -> String.format("Message from LogDiadocEdoEventAction %s", document.getObjectId()));
        return null;
    }
}
