// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.event.actions;

import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import java.util.HashMap;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.event.EventAction;

public class DiadocEdoChangeExchangeStateEventAction extends EventAction
{
    private Logger logger;
    
    public DiadocEdoChangeExchangeStateEventAction() {
        this.logger = LogManager.getLogger();
    }
    
    public Object execute(final DMSShardsClientSession session, final Map<String, Object> args, final SessionContext ctx) {
        String documentId = null;
        try {
            final String eligibleLocalStates = StringUtils.trimToNull(this.getLinkConfig().getAttributeValue("local_states"));
            final String desiredLocalState = StringUtils.trimToNull(this.getLinkConfig().getAttributeValue("new_local_states"));
            final String desiredStateCategory = StringUtils.trimToNull(this.getLinkConfig().getAttributeValue("new_state_category"));
            final IDocument document = args.get(this.getLinkConfig().getAttributeValue("documentarg"));
            if (document != null) {
                documentId = document.getObjectId();
            }
            final Map<String, String> callArgs = new HashMap<String, String>();
            callArgs.putAll(this.getEventParams());
            callArgs.put("local_states", eligibleLocalStates);
            callArgs.put("new_local_states", desiredLocalState);
            callArgs.put("new_state_category", desiredStateCategory);
            if (DiadocEdoServiceReg.getDiadocEdoIntegrationService() != null) {
                DiadocEdoServiceReg.getDiadocEdoIntegrationService().changeDocumentExchangeState(session, document, callArgs, ctx);
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
