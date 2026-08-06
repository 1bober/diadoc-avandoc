// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.event.handler;

import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.event.EVENT_HANDLER_RESULT;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.event.EventHandler;

public class FillAttributeFromParentEventHandler extends EventHandler
{
    public EVENT_HANDLER_RESULT handleEvent(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        try {
            final String srcType = this.getParam("srctype");
            String parentId = null;
            for (SessionContext pctx = ctx; pctx != null; pctx = pctx.getParentCtx()) {
                if (StringUtils.equals(pctx.getString("__type"), srcType)) {
                    parentId = pctx.getString("__objectId");
                    break;
                }
                if (pctx == pctx.getParentCtx()) {
                    break;
                }
            }
            if (StringUtils.isBlank(parentId)) {
                throw new ServiceException(String.format("Failed to with object with type %s in parent context", srcType));
            }
            final IObjectModel model = ServiceReg.getObjectModelService().getObjectModel(session, (String)params.get("__objectId"), ctx);
            IObjectModel parent = null;
            String strAttrName = StringUtils.trimToNull(this.getParam("srcattr"));
            String destAttrName = StringUtils.trimToNull(this.getParam("destattr"));
            if (strAttrName != null && destAttrName != null) {
                if ("r_object_id".equals(strAttrName)) {
                    model.setValue(destAttrName, (Object)parentId);
                }
                else {
                    parent = ServiceReg.getObjectModelService().getObjectModel(session, parentId, ctx);
                    model.setValue(destAttrName, parent.getValue(strAttrName));
                }
            }
            final String attrTransferRule = StringUtils.trimToNull(this.getParam("attribute_tranfser"));
            if (attrTransferRule != null) {
                final String[] split;
                final String[] attrTransferRuleList = split = StringUtils.split(attrTransferRule, ",");
                for (final String elem : split) {
                    final String[] attrTransferRuleDecomposition = StringUtils.split(elem, "=");
                    if (attrTransferRuleDecomposition.length == 2) {
                        destAttrName = StringUtils.trimToNull(attrTransferRuleDecomposition[0]);
                        strAttrName = StringUtils.trimToNull(attrTransferRuleDecomposition[1]);
                        if (destAttrName != null && strAttrName != null) {
                            if ("r_object_id".equals(strAttrName)) {
                                model.setValue(destAttrName, (Object)parentId);
                            }
                            else {
                                if (parent == null) {
                                    parent = ServiceReg.getObjectModelService().getObjectModel(session, parentId, ctx);
                                }
                                model.setValue(destAttrName, parent.getValue(strAttrName));
                            }
                        }
                    }
                }
            }
            ServiceReg.getObjectModelService().commitModel(session, model, ctx);
            return EVENT_HANDLER_RESULT.SUCCESS;
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public boolean validate(final DMSShardsClientSession session, final String rootId, final String eventName, final Map<String, String> params, final SessionContext ctx) {
        return true;
    }
}
