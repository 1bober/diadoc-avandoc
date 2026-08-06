// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.event.handler;

import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.event.EVENT_HANDLER_RESULT;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.event.documents.DocumentEventHandler;

public class DiadocEdoCheckAccountEventHandler extends DocumentEventHandler
{
    public EVENT_HANDLER_RESULT handleDocEvent(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        final String serverId = params.get("sida_server");
        final String login = params.get("ssa_login");
        final String password = params.get("ssa_password");
        DiadocEdoServiceReg.getDiadocEdoIntegrationService().checkConnection(session, serverId, login, password, ctx);
        return EVENT_HANDLER_RESULT.SUCCESS;
    }
    
    public boolean rollback(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        return this.rollbackTask(session, rootId, eventName, (Map)params, ctx);
    }
}
