// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Consumer;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationFlowConfiguration extends CstmConfigAdapter implements IEdoIntegrationFlowConfiguration
{
    private static final long serialVersionUID = -468573611882838194L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>> fieldDefinition;
    private EdoIntegrationFlowConfigurationType configurationType;
    @JsonProperty
    private List<IEdoIntegrationOperationConfiguration> operationConfigurations;
    @JsonProperty
    private IDictionaryElementConfiguration eventStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>("cstm_elements", null, "element", "elements", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationFlowConfiguration, Object>)IEdoIntegrationFlowConfiguration::getOperationConfigurations, (a, b) -> a.addOperationConfigurationFromWrapper(b), IEdoIntegrationFlowConfiguration::clearOperationConfigurations));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>("sida_event", "ssa_event_name", "event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationFlowConfiguration, Object>)IEdoIntegrationFlowConfiguration::getEventStorage, (a, b) -> a.setEventStorage((IDictionaryElementConfiguration)b), null));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationFlowConfiguration>> getFieldDefinition() {
        return EdoIntegrationFlowConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, getFieldDefinition(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), element, ctx);
    }
    
    protected EdoIntegrationFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.operationConfigurations = new ArrayList<IEdoIntegrationOperationConfiguration>();
    }
    
    public EdoIntegrationFlowConfigurationType getConfigurationType() {
        return this.configurationType;
    }
    
    public void setType(final EdoIntegrationFlowConfigurationType value) {
        this.configurationType = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getEventStorage() {
        return this.eventStorage;
    }
    
    @Override
    public void setEventStorage(final IDictionaryElementConfiguration eventStorage) {
        this.eventStorage = eventStorage;
    }
    
    @Override
    public List<IEdoIntegrationOperationConfiguration> getOperationConfigurations() {
        return this.operationConfigurations;
    }
    
    @Override
    public void clearOperationConfigurations() {
        this.operationConfigurations.clear();
    }
    
    @Override
    public void addOperationConfigurationFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationOperationConfiguration mappingRule = this.createOperationConfiguration(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addOperationConfiguration(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationOperationConfiguration mappingRule = this.createOperationConfiguration(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addOperationConfiguration(mappingRule);
            }
        }
    }
    
    public void addOperationConfiguration(final IEdoIntegrationOperationConfiguration configurations) {
        this.operationConfigurations.add(configurations);
    }
    
    @Override
    public String getEvent() {
        return (this.getEventStorage() != null) ? this.getEventStorage().getId() : null;
    }
    
    public abstract IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
