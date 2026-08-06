// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationInboundDocumentProcessingConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationInboundDocumentProcessingConfiguration
{
    private static final long serialVersionUID = 3137784861789030860L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> fieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> typeFieldDefinition;
    @JsonProperty
    private String localEnvironmentsNames;
    @JsonProperty
    private String dataArgument;
    @JsonProperty
    private IDictionaryElementConfiguration internalType;
    @JsonProperty
    private String globalEnvironmentsNames;
    @JsonProperty
    private String connectionInfoEnvironmentsNames;
    @JsonProperty
    private String postActionsId;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> documentAttributesMapping;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> mappingProcedures;
    @JsonProperty
    private final List<IEdoIntegrationInboundAttachmentProcessingEvent> processorEvents;
    @JsonProperty
    private final List<IEdoIntegrationInboundAttachmentProcessingRule> attachmentProcessingRules;
    @JsonProperty
    private final List<IDictionaryElementConfiguration> sendPrepareErrorsStorage;
    @JsonProperty
    private final List<IDictionaryElementConfiguration> exchangeErrorCategoriesStorage;
    @JsonProperty
    private final List<IDictionaryElementConfiguration> exchangeErrorsStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("sida_title_object_type", "ssa_title_object_type_name", "type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getInternalType, (a, b) -> a.setInternalType((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("ssa_title_object_type_manual", "type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("sba_inactive", "inactive", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)ICstmConfigAdapter::getInactive, (a, b) -> a.setInactive(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("cstm_inbound_attachment_processing_rules_editor", null, "processing_rule", "processing_rules_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getAttachmentProcessingRules, (a, b) -> a.addAttachmentProcessingRuleFromWrapper(b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearAttachmentProcessingRules));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("processor_event_link", null, "processor_event_links", "processor_event_link", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getProcessorEvents, (a, b) -> a.addProcessorEventFromWrapper(b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearProcessorEvents));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("ssa_global_environment_name", "global_environments_names", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getGlobalEnvironmentsNames, (a, b) -> a.setGlobalEnvironmentsNames(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("ssa_environment_name", "local_environments_names", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getLocalEnvironmentsNames, (a, b) -> a.setLocalEnvironmentsNames(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("ssa_connection_info_environment_name", "connection_info_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getConnectionInfoEnvironmentsNames, (a, b) -> a.setConnectionInfoEnvironmentsNames(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("ssa_incoming_package_argument", "incoming_package_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getDataArgument, (a, b) -> a.setDataArgument(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("cstm_inbound_map_attributes_editor", null, "mapping", "attribute_mapping_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getAttributesMapping, (a, b) -> a.addAttributeMappingFromWrapper(b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearAttributesMapping));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("cstm_inbound_procedures_mapping", null, "mapping", "procedures_mapping_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getMappingProcedures, (a, b) -> a.addMappingProceduresFromWrapper(b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearMappingProcedures));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("postactions", "postactions", ICstmConfigAdapter.FieldType.cstm_object_link, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getPostActionsId, (a, b) -> a.setPostActionsId((String)b), (Consumer<IEdoIntegrationInboundDocumentProcessingConfiguration>)null, (a, g, ctx) -> a.unmarshalPostActions(g, ctx)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("rsa_exchange_error_categories", "rsa_exchange_error_categories_names", "exchange_error_category", "exchange_error_categories_list", ICstmConfigAdapter.FieldType.dictionary_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getExchangeErrorCategoriesStorage, (a, b) -> a.addExchangeErrorCategory((IDictionaryElementConfiguration)b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearExchangeErrorCategoriesStorage));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>("rsa_send_prepare_errors", "rsa_send_prepare_errors_names", "send_prepare_error", "send_prepare_errors_list", ICstmConfigAdapter.FieldType.dictionary_list, (Function<IEdoIntegrationInboundDocumentProcessingConfiguration, Object>)IEdoIntegrationInboundDocumentProcessingConfiguration::getSendPrepareErrorsStorage, (a, b) -> a.addSendPrepareError((IDictionaryElementConfiguration)b), IEdoIntegrationInboundDocumentProcessingConfiguration::clearSendPrepareErrorsStorage));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationInboundDocumentProcessingConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundDocumentProcessingConfiguration>> getFieldDefinition() {
        return EdoIntegrationInboundDocumentProcessingConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundDocumentProcessingConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundDocumentProcessingConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationInboundDocumentProcessingConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.documentAttributesMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.mappingProcedures = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.processorEvents = new ArrayList<IEdoIntegrationInboundAttachmentProcessingEvent>();
        this.attachmentProcessingRules = new ArrayList<IEdoIntegrationInboundAttachmentProcessingRule>();
        this.sendPrepareErrorsStorage = new ArrayList<IDictionaryElementConfiguration>();
        this.exchangeErrorCategoriesStorage = new ArrayList<IDictionaryElementConfiguration>();
        this.exchangeErrorsStorage = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    @Override
    public IDictionaryElementConfiguration getInternalType() {
        return this.internalType;
    }
    
    @Override
    public void setInternalType(final IDictionaryElementConfiguration internalType) {
        this.internalType = internalType;
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
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    @Override
    public List<IEdoIntegrationInboundAttachmentProcessingRule> getAttachmentProcessingRules() {
        return this.attachmentProcessingRules;
    }
    
    @Override
    public void addAttachmentProcessingRule(final IEdoIntegrationInboundAttachmentProcessingRule value) {
        this.getAttachmentProcessingRules().add(value);
    }
    
    @Override
    public void clearAttachmentProcessingRules() {
        this.getAttachmentProcessingRules().clear();
    }
    
    @Override
    public void addAttachmentProcessingRuleFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingRule rule = this.createAttachmentProcessingRule(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (rule != null) {
                this.addAttachmentProcessingRule(rule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingRule rule = this.createAttachmentProcessingRule(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (rule != null) {
                this.addAttachmentProcessingRule(rule);
            }
        }
    }
    
    public abstract IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessingRule(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessingRule(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getDataArgument() {
        return this.dataArgument;
    }
    
    @Override
    public void setDataArgument(final String value) {
        this.dataArgument = value;
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
    public void setPostActionsId(final String value) {
        this.postActionsId = value;
    }
    
    @Override
    public String getPostActionsId() {
        return this.postActionsId;
    }
    
    @Override
    public void clearAttributesMapping() {
        this.getAttributesMapping().clear();
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getAttributesMapping() {
        return this.documentAttributesMapping;
    }
    
    @Override
    public void addAttributeMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addAttributeMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addAttributeMapping(mappingRule);
            }
        }
    }
    
    @Override
    public void addAttributeMapping(final IEdoIntegrationAttributeMapping value) {
        this.getAttributesMapping().add(value);
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getMappingProcedures() {
        return this.mappingProcedures;
    }
    
    @Override
    public void addMappingProcedure(final IEdoIntegrationAttributeMapping attribute) {
        this.getMappingProcedures().add(attribute);
    }
    
    @Override
    public void clearMappingProcedures() {
        this.getMappingProcedures().clear();
    }
    
    @Override
    public void addMappingProceduresFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addMappingProcedure(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addMappingProcedure(mappingRule);
            }
        }
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
    
    public abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public List<IDictionaryElementConfiguration> getSendPrepareErrorsStorage() {
        return this.sendPrepareErrorsStorage;
    }
    
    @Override
    public void addSendPrepareError(final IDictionaryElementConfiguration value) {
        this.getSendPrepareErrorsStorage().add(value);
    }
    
    @Override
    public void addSendPrepareErrorFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IDictionaryElementConfiguration v = this.createSendPrepareError(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (v != null) {
                this.addSendPrepareError(v);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IDictionaryElementConfiguration v = this.createSendPrepareError(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (v != null) {
                this.addSendPrepareError(v);
            }
        }
    }
    
    public abstract IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public void clearSendPrepareErrorsStorage() {
        this.getSendPrepareErrorsStorage().clear();
    }
    
    @Override
    public List<String> getSendPrepareErrors() {
        return this.getSendPrepareErrorsStorage().stream().map((Function<? super Object, ? extends String>)IDictionaryElementConfiguration::getValue).toList();
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
    public List<IDictionaryElementConfiguration> getExchangeErrorCategoriesStorage() {
        return this.exchangeErrorCategoriesStorage;
    }
    
    @Override
    public void addExchangeErrorCategory(final IDictionaryElementConfiguration value) {
        this.getExchangeErrorCategoriesStorage().add(value);
    }
    
    @Override
    public void addExchangeErrorCategoryFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IDictionaryElementConfiguration v = this.createExchangeErrorCategory(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (v != null) {
                this.addExchangeErrorCategory(v);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IDictionaryElementConfiguration v = this.createExchangeErrorCategory(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (v != null) {
                this.addExchangeErrorCategory(v);
            }
        }
    }
    
    public abstract IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public void clearExchangeErrorCategoriesStorage() {
        this.getExchangeErrorCategoriesStorage().clear();
    }
    
    @Override
    public List<String> getExchangeErrorCategories() {
        return this.getExchangeErrorCategoriesStorage().stream().map((Function<? super Object, ? extends String>)IDictionaryElementConfiguration::getValue).toList();
    }
    
    static {
        fieldDefinition = initFieldDefinition();
        typeFieldDefinition = initTypeFieldDefinition();
    }
}
