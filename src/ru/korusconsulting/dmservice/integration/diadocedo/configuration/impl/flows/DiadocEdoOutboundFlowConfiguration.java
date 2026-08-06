// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentSendOperationScopedConfiguration;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationOutboundFlowConfiguration;

public class DiadocEdoOutboundFlowConfiguration extends EdoIntegrationOutboundFlowConfiguration
{
    private static final long serialVersionUID = -7959973560379698299L;
    public static final String CONFIG_ID = "diadocedo_outbound_flows";
    
    private static String getFlowConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "select_rule");
    }
    
    public DiadocEdoOutboundFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration flow = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getFlowConfigGroupId(), component.getComponentType(), xmlTagId);
        if (flow != null) {
            flow.unmarshal(session, component, generator, ctx);
        }
        return flow;
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration flow = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getFlowConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (flow != null) {
            flow.unmarshal(session, el, ctx);
        }
        return flow;
    }
    
    static {
        registerChildConfigTagMapping(getFlowConfigGroupId(), "cstm_integration_diadoc_edo_send_document_operation", "send_operation_scoped", (Class)DiadocEdoOutboundDocumentSendOperationScopedConfiguration.class);
    }
}
