// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationCounteragentConfiguration;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationCounteragentFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoCounteragentFlowConfiguration extends EdoIntegrationCounteragentFlowConfiguration
{
    private static final long serialVersionUID = -124757350392023920L;
    public static final String CONFIG_ID = "counteragent_flow";
    
    protected static String getOperationChildType() {
        return String.join(".", DiadocEdoCounteragentFlowConfiguration.class.getName(), "operation");
    }
    
    public DiadocEdoCounteragentFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration flowConfig = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getOperationChildType(), component.getComponentType(), xmlTagId);
        if (flowConfig != null) {
            flowConfig.unmarshal(session, component, generator, ctx);
        }
        return flowConfig;
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration flowConfig = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getOperationChildType(), element.getTagName(), element.getAttributeValue("config_id"));
        if (flowConfig != null) {
            flowConfig.unmarshal(session, element, ctx);
        }
        return flowConfig;
    }
    
    static {
        registerChildConfigTagMapping(getOperationChildType(), "cstm_integration_diadoc_edo_counteragent_type", "counteragent_type", (Class)EdoIntegrationCounteragentConfiguration.class);
    }
}
