// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundRevokeSendFileOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundRevokeCreateFileOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationFlowConfigurationType;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationOutboundRevokeFlowConfiguration;

public class DiadocEdoOutboundRevokeFlowConfiguration extends EdoIntegrationOutboundRevokeFlowConfiguration
{
    private static final long serialVersionUID = -5336302947637623284L;
    public static final String CONFIG_ID = "diadocedo_outbound_revoke_flows";
    
    private static String getFlowConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "revoke_flow");
    }
    
    public DiadocEdoOutboundRevokeFlowConfiguration(final ICstmConfigAdapter parent) {
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
    
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.OUTBOUND_REVOKE;
    }
    
    static {
        registerChildConfigTagMapping(getFlowConfigGroupId(), "cstm_integration_diadoc_edo_outbound_revoke_create_file", "diadocedo_outbound_revoke_create_file_operation", (Class)DiadocEdoOutboundRevokeCreateFileOperationScopedConfiguration.class);
        registerChildConfigTagMapping(getFlowConfigGroupId(), "cstm_integration_diadoc_edo_outbound_revoke_send_file", "diadocedo_outbound_revoke_send_file_operation", (Class)DiadocEdoOutboundRevokeSendFileOperationScopedConfiguration.class);
    }
}
