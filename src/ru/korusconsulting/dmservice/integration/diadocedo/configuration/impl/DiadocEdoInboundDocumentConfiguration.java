// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentConfiguration;

public abstract class DiadocEdoInboundDocumentConfiguration extends EdoIntegrationDocumentConfiguration
{
    private static final long serialVersionUID = 9118295225560003946L;
    private boolean inbound;
    
    protected DiadocEdoInboundDocumentConfiguration(final IEdoIntegrationFlowConfiguration parent) {
        super((ICstmConfigAdapter)parent);
    }
    
    public boolean isInbound() {
        return this.inbound;
    }
    
    public void setInbound(final boolean inbound) {
        this.inbound = inbound;
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return null;
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return null;
    }
    
    public IEdoIntegrationFlowConfiguration getParent() {
        return null;
    }
}
