// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.event.handler;

import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.event.EVENT_HANDLER_RESULT;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.event.documents.DocumentEventHandler;

public class CheckFilialHasInnKppHandler extends DocumentEventHandler
{
    public EVENT_HANDLER_RESULT handleDocEvent(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        final String filialId = params.get("sida_filial");
        final IFilial f = (IFilial)ServiceReg.getObjectModelService().getObjectModel(session, filialId, ctx);
        if (f == null) {
            final ServiceException validationException = new ServiceException();
            validationException.setParam("__validation_error", (Object)String.format("Failed to resolve filial for id %s", filialId));
            throw validationException;
        }
        if (StringUtils.isBlank(f.getFilialINN())) {
            if (StringUtils.isBlank(f.getFilialKPP())) {
                final ServiceException validationException = new ServiceException();
                validationException.setParam("__validation_error", (Object)String.format("Filial %s has empty INN and KPP", filialId));
                throw validationException;
            }
            final ServiceException validationException = new ServiceException();
            validationException.setParam("__validation_error", (Object)String.format("Filial %s has empty INN", filialId));
            throw validationException;
        }
        else {
            if (StringUtils.isBlank(f.getFilialKPP())) {
                final ServiceException validationException = new ServiceException();
                validationException.setParam("__validation_error", (Object)String.format("Filial %s has empty KPP", filialId));
                throw validationException;
            }
            return EVENT_HANDLER_RESULT.SUCCESS;
        }
    }
    
    public boolean rollback(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        return this.rollbackTask(session, rootId, eventName, (Map)params, ctx);
    }
}
