// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public class CstmOperationWrapper
{
    private final DMSShardsClientSession session;
    private final ICstmComponent component;
    private final SessionContext context;
    private final String xmlGroupTagName;
    private final String xmlTagName;
    private final Generator generator;
    
    public CstmOperationWrapper(final DMSShardsClientSession session, final ICstmComponent component, final String xmlGroupTagName, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        this.session = session;
        this.component = component;
        this.context = ctx;
        this.xmlGroupTagName = xmlGroupTagName;
        this.xmlTagName = xmlTagName;
        this.generator = generator;
    }
    
    public DMSShardsClientSession getSession() {
        return this.session;
    }
    
    public ICstmComponent getComponent() {
        return this.component;
    }
    
    public SessionContext getContext() {
        return this.context;
    }
    
    public String getXmlGroupTagName() {
        return this.xmlGroupTagName;
    }
    
    public String getXmlTagName() {
        return this.xmlTagName;
    }
    
    public Generator getGenerator() {
        return this.generator;
    }
}
