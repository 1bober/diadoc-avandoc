// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationOutboundDocumentLocalStatusProcessingOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationFlowConfigurationType;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Consumer;
import java.util.function.Function;
import ru.korusconsulting.service.integration.diadocedo.configuration.flows.IDiadocEdoStatusCheckFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.flows.IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationOutboundDocumentStatusCheckFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoOutboundDocumentStatusCheckFlowConfiguration extends EdoIntegrationOutboundDocumentStatusCheckFlowConfiguration implements IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration
{
    private static final long serialVersionUID = 7623541023622129580L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration contentEventStorage;
    public static final String CONFIG_ID;
    
    private static String getCheckStatusConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentStatusCheckFlowConfiguration.class.getName(), "check_status_operation");
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>)new ConfigFieldBaseDescriptionExt("sida_content_event", "ssa_content_event_name", "content_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoStatusCheckFlowConfiguration::getContentEventStorage, (a, b) -> a.setContentEventStorage((IDictionaryElementConfiguration)b), (Consumer)null));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentCheckStatusFlowConfiguration>> getFieldDefinition() {
        return DiadocEdoOutboundDocumentStatusCheckFlowConfiguration.fieldDefinition;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = (T)super.marshalImpl(session, xmlTag, generator, (Supplier)configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)getFieldDefinition(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), element, ctx);
    }
    
    public DiadocEdoOutboundDocumentStatusCheckFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration rule = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getCheckStatusConfigGroupId(), component.getComponentType(), xmlTagId);
        if (rule != null) {
            rule.unmarshal(session, component, generator, ctx);
        }
        return rule;
    }
    
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration rule = (IEdoIntegrationOperationConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getCheckStatusConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (rule != null) {
            rule.unmarshal(session, el, ctx);
        }
        return rule;
    }
    
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.STATUS_CHECK;
    }
    
    public IDictionaryElementConfiguration getContentEventStorage() {
        return this.contentEventStorage;
    }
    
    public void setContentEventStorage(final IDictionaryElementConfiguration value) {
        this.contentEventStorage = value;
    }
    
    public String getContentEvent() {
        return (this.getContentEventStorage() != null) ? this.getContentEventStorage().getId() : null;
    }
    
    static {
        registerChildConfigTagMapping(getCheckStatusConfigGroupId(), "cstm_integration_diadoc_edo_outbound_remote_status_processing_operation", "remote_status_operation", (Class)EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration.class);
        registerChildConfigTagMapping(getCheckStatusConfigGroupId(), "cstm_integration_diadoc_edo_outbound_cummulative_remote_status_processing_operation", "remote_status_operation", (Class)EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration.class);
        registerChildConfigTagMapping(getCheckStatusConfigGroupId(), "cstm_integration_diadoc_edo_outbound_local_status_processing_operation", "local_status_operation", (Class)EdoIntegrationOutboundDocumentLocalStatusProcessingOperationScopedConfiguration.class);
        registerChildConfigTagMapping(getCheckStatusConfigGroupId(), "cstm_integration_diadoc_edo_outbound_counteragent_remote_status_processing_operation", "remote_status_operation", (Class)EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration.class);
        fieldDefinition = initFieldDefinition();
        CONFIG_ID = EdoIntegrationFlowConfigurationType.STATUS_CHECK.name();
    }
}
