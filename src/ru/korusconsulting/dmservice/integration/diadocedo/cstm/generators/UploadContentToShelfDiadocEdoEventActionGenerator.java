// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadContentToShelfDiadocEdoEventActionGenerator extends DiadocEdoTemplateEventActionGenerator
{
    private Logger log;
    
    public UploadContentToShelfDiadocEdoEventActionGenerator() {
        this.log = LogManager.getLogger((Class)UploadContentToShelfDiadocEdoEventActionGenerator.class);
    }
    
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        super.gen(session, cstmObject, configElement, ctx);
    }
}
