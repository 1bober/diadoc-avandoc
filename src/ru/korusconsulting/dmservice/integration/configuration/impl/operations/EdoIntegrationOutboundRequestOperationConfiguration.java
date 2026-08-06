// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.Map;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Consumer;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundRequestOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundRequestOperationConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationOutboundRequestOperationConfiguration
{
    private static final long serialVersionUID = -3610995713215937207L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> fieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> typeFieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration titleObjectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration stateCategoryStorage;
    @JsonProperty
    private String globalEnvironmentsNames;
    @JsonProperty
    private String localEnvironmentsNames;
    @JsonProperty
    private String connectionInfoEnvironmentsNames;
    @JsonProperty
    private String postActionsId;
    @JsonProperty
    private final List<IEdoIntegrationInboundAttachmentProcessingEvent> processorEvents;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("sida_title_object_type", "ssa_title_object_type_name", "type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationOutboundRequestOperationConfiguration::getTitleObjectTypeStorage, (a, b) -> a.setTitleObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("ssa_title_object_type_manual", "type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationOutboundRequestOperationConfiguration::getStateCategoryStorage, (a, b) -> a.setStateCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("ssa_global_environment_name", "global_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getGlobalEnvironmentsNames, (a, b) -> a.setGlobalEnvironmentsNames(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("ssa_environment_name", "local_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getLocalEnvironmentsNames, (a, b) -> a.setLocalEnvironmentsNames(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("ssa_connection_info_environment_name", "connection_info_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getConnectionInfoEnvironmentsNames, (a, b) -> a.setConnectionInfoEnvironmentsNames(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("processor_event_link", null, "processor_event_links", "processor_event_link", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationOutboundRequestOperationConfiguration::getProcessorEvents, (a, b) -> a.addProcessorEventFromWrapper(b), IEdoIntegrationOutboundRequestOperationConfiguration::clearProcessorEvents));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>("postactions", "postactions", ICstmConfigAdapter.FieldType.cstm_object_link, (Function<IEdoIntegrationOutboundRequestOperationConfiguration, Object>)IEdoIntegrationOutboundRequestOperationConfiguration::getPostActionsId, (a, b) -> a.setPostActionsId((String)b), (Consumer<IEdoIntegrationOutboundRequestOperationConfiguration>)null, (a, g, ctx) -> a.unmarshalPostActions(g, ctx)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationOutboundRequestOperationConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundRequestOperationConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundRequestOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundRequestOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    @Override
    public void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRequestOperationConfiguration>> fieldDefs = getFieldDefinition().stream().filter(ConfigFieldBaseDescriptionExt::isCheckerField).toList();
        this.unmarshalFields(session, this, fieldDefs, config, ctx);
    }
    
    @Override
    public Boolean unmarshalPostActions(final Generator generator, final SessionContext ctx) {
        if (this.getSourceComponent() != null) {
            final RootConfigElement mainCmpConfig = new RootConfigElement();
            mainCmpConfig.setLocation(this.getComponentUri());
            mainCmpConfig.setTagName("postactions");
            mainCmpConfig.setAttributeValue("id", this.getComponentUid());
            mainCmpConfig.setAttributeValue("cssess", "true");
            mainCmpConfig.setAttributeValue("async", "true");
            final ConfigElement actionsConfig = new ConfigElement();
            actionsConfig.setTagName("actions");
            generator.addChild((ConfigElement)mainCmpConfig, actionsConfig);
            final Map<String, List<String>> scope = this.getSourceComponent().getSummScope();
            this.getSourceComponent().getSideEffects().put("main_config", generator.addScopeConfig((Map)scope, mainCmpConfig, ctx));
            this.setPostActionsId(this.getComponentUid());
        }
        return true;
    }
    
    protected EdoIntegrationOutboundRequestOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.processorEvents = new ArrayList<IEdoIntegrationInboundAttachmentProcessingEvent>();
    }
    
    @Override
    public IDictionaryElementConfiguration getTitleObjectTypeStorage() {
        return this.titleObjectTypeStorage;
    }
    
    @Override
    public void setTitleObjectTypeStorage(final IDictionaryElementConfiguration value) {
        this.titleObjectTypeStorage = value;
    }
    
    @Override
    public void setStateCategoryStorage(final IDictionaryElementConfiguration value) {
        this.stateCategoryStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getStateCategoryStorage() {
        return this.stateCategoryStorage;
    }
    
    @Override
    public String getStateCategory() {
        return (this.getStateCategoryStorage() != null) ? this.getStateCategoryStorage().getValue() : null;
    }
    
    @Override
    public void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent value) {
        this.getProcessorEvents().add(value);
    }
    
    @Override
    public List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents() {
        return this.processorEvents;
    }
    
    @Override
    public void clearProcessorEvents() {
        this.getProcessorEvents().clear();
    }
    
    @Override
    public void addProcessorEventFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingEvent event = this.createAttachmentProcessorEvent(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (event != null) {
                this.addProcessorEvent(event);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingEvent event = this.createAttachmentProcessorEvent(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (event != null) {
                this.addProcessorEvent(event);
            }
        }
    }
    
    @Override
    public String getGlobalEnvironmentsNames() {
        return this.globalEnvironmentsNames;
    }
    
    @Override
    public void setGlobalEnvironmentsNames(final String value) {
        this.globalEnvironmentsNames = value;
    }
    
    @Override
    public String getLocalEnvironmentsNames() {
        return this.localEnvironmentsNames;
    }
    
    @Override
    public void setLocalEnvironmentsNames(final String value) {
        this.localEnvironmentsNames = value;
    }
    
    @Override
    public String getPostActionsId() {
        return this.postActionsId;
    }
    
    @Override
    public String getConnectionInfoEnvironmentsNames() {
        return this.connectionInfoEnvironmentsNames;
    }
    
    @Override
    public void setConnectionInfoEnvironmentsNames(final String value) {
        this.connectionInfoEnvironmentsNames = value;
    }
    
    @Override
    public void setPostActionsId(final String value) {
        this.postActionsId = value;
    }
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    static {
        fieldDefinition = initFieldDefinition();
        typeFieldDefinition = initTypeFieldDefinition();
    }
}
