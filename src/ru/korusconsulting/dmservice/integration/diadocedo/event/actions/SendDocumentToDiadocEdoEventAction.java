// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.event.actions;

import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.event.EventAction;

public class SendDocumentToDiadocEdoEventAction extends EventAction
{
    private Logger logger;
    
    public SendDocumentToDiadocEdoEventAction() {
        this.logger = LogManager.getLogger();
    }
    
    public Object execute(final DMSShardsClientSession session, final Map<String, Object> args, final SessionContext ctx) {
        String documentId = null;
        try {
            final IDocument document = args.get(this.getLinkConfig().getAttributeValue("documentarg"));
            if (document != null) {
                documentId = document.getObjectId();
            }
            if (DiadocEdoServiceReg.getDiadocEdoIntegrationService() != null) {
                DiadocEdoServiceReg.getDiadocEdoIntegrationService().queueDocument(session, document, this.getEventParams(), ctx);
            }
            else {
                this.logger.trace(() -> String.format("Document %s. Diadoc Edo module not installed", document.getObjectId()));
            }
        }
        catch (final Throwable e) {
            this.logger.error(String.format("Exception while queue document %s", documentId), e);
        }
        return null;
    }
}
