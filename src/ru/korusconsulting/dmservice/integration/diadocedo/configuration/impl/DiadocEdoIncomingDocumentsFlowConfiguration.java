// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationFlowConfigurationType;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.incoming.DiadocEdoIncomingDocumentReceiveScopedConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoInboundFlowConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationIncomingDocumentsFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoIncomingDocumentsFlowConfiguration extends EdoIntegrationIncomingDocumentsFlowConfiguration implements IDiadocEdoInboundFlowConfiguration
{
    private static final long serialVersionUID = -5223234184001701635L;
    public static final String CONFIG_ID;
    
    public DiadocEdoIncomingDocumentsFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final DiadocEdoIncomingDocumentReceiveScopedConfiguration config = new DiadocEdoIncomingDocumentReceiveScopedConfiguration((ICstmConfigAdapter)this);
        config.unmarshal(session, component, generator, ctx);
        return (IEdoIntegrationOperationConfiguration)config;
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final DiadocEdoIncomingDocumentReceiveScopedConfiguration config = new DiadocEdoIncomingDocumentReceiveScopedConfiguration((ICstmConfigAdapter)this);
        config.unmarshal(session, element, ctx);
        return (IEdoIntegrationOperationConfiguration)config;
    }
    
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.INCOMING;
    }
    
    public IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExternalDocumentTypeConfiguration(session, (ICstmConfigAdapter)this, component, generator, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for component %s ", component.getComponentType()));
        }
        return configuration;
    }
    
    public IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExternalDocumentTypeConfiguration(session, (ICstmConfigAdapter)this, element, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for element with tag %s", element.getTagName()));
        }
        return configuration;
    }
    
    static {
        CONFIG_ID = EdoIntegrationFlowConfigurationType.INCOMING.name();
    }
}
