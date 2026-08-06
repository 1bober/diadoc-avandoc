// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.schedule;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.IEventHandlerService;
import java.util.HashMap;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import java.util.Map;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.commons.ApplicationProperties;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.commons.SessionContext;
import org.apache.logging.log4j.Logger;
import ru.wstack.wsscheduler.ScheduledTask;

public class DiadocEdoSendDocumentSchedulerAction extends ScheduledTask
{
    private static final Logger logger;
    
    public void invoke() {
        final SessionContext ctx = new SessionContext();
        try (final DMSShardsClientSession session = new DMSShardsClientSession()) {
            session.initClientSession(ApplicationProperties.getProperty("cslogin"), ApplicationProperties.getProperty("cspassword"));
            new ShardSessionRunner(() -> {
                final String id = this.getParams().get("ID");
                if (StringUtils.isNotBlank(id)) {
                    final IDocument document = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
                    final SessionContext ctx2 = ServiceReg.getDocumentRouteService().applyDocumentContext(session, document, ctx);
                    ctx2.set("__filialId", ctx.get("__docFilialId"));
                    ctx2.set("__filialName", ctx.get("__docFilialName"));
                    ctx2.set("__orgPositionId", (Object)document.getOwner());
                    ctx2.set("__orgPositionName", (Object)document.getOwnerName());
                    if (DiadocEdoServiceReg.getDiadocEdoIntegrationService() != null) {
                        DiadocEdoServiceReg.getDiadocEdoIntegrationService().queueDocument(session, document, this.getParams(), ctx2);
                    }
                    else {
                        DiadocEdoSendDocumentSchedulerAction.logger.trace(() -> String.format("Document %s. Diadoc Edo module not installed", document.getObjectId()));
                    }
                }
                return null;
            }).start(DMSShardsManager.SHARD.DICT, ctx);
        }
        catch (final Exception e) {
            DiadocEdoSendDocumentSchedulerAction.logger.error("Error while checking status", (Throwable)e);
        }
    }
    
    public boolean fireEvent(final DMSShardsClientSession session, final String objectId, final String objectType, final String ownerId, final String eventName, final SessionContext context) {
        final SessionContext eventCtx = context.clone();
        eventCtx.set("__type", (Object)objectType);
        eventCtx.set("__objectId", (Object)objectId);
        if (!StringUtils.isNotEmpty(eventName)) {
            return false;
        }
        final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
        final Map<String, String> eventParams = new HashMap<String, String>();
        eventParams.put("auto_process_action", "true");
        if (StringUtils.isNotBlank(ownerId)) {
            eventParams.put("__orgPositionId", ownerId);
            eventCtx.set("__orgPositionId", (Object)ownerId);
        }
        else {
            eventParams.put("__orgPositionId", (String)context.get("__orgPositionId"));
            eventCtx.set("__orgPositionId", (Object)context.get("__orgPositionId"));
        }
        if (!eventHandlerService.fireSyncEvent(session, objectId, eventName, (Map)eventParams, eventCtx)) {
            DiadocEdoSendDocumentSchedulerAction.logger.warn(() -> String.format("%s. Failed to create event %s in context %s", "Test", eventName, context.toString()));
            return false;
        }
        return true;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
