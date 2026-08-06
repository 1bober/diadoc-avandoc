// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoDocumentAttributeDesc;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectRule;
import ru.korusconsulting.service.objectmodel.cstm.ICstmType;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundDocumentSendOperationConfiguration;

public abstract class EdoIntegrationOutboundDocumentSendOperationConfiguration extends EdoIntegrationDocumentOperationConfiguration implements IEdoIntegrationOutboundDocumentSendOperationConfiguration
{
    private static final long serialVersionUID = -4014685805088905554L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration titleObjectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration sendSignatureTypesStorage;
    @JsonProperty
    private IDictionaryElementConfiguration notInvitedCounteragentProcessModeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration contentGroupModeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration counteragentModeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration exchangeCategoryStorage;
    @JsonProperty
    private boolean onlyLinkedCounteragent;
    @JsonProperty
    private boolean ignoreCounteragentPkiState;
    @JsonProperty
    private boolean remotesignaturemode;
    @JsonProperty
    private String stateCategory;
    @JsonProperty
    private String globalEnvironmentsNames;
    @JsonProperty
    private String localEnvironmentsNames;
    @JsonProperty
    private String connectionInfoEnvironmentsNames;
    @JsonProperty
    private String preProcessingDocumentArgument;
    @JsonProperty
    private final List<IEdoIntegrationInboundDocumentProcessingConfiguration> sendPrepareErrorProcessing;
    @JsonProperty
    private final List<IEdoIntegrationInboundDocumentProcessingConfiguration> exchangeErrorProcessing;
    @JsonProperty
    private final List<IEdoIntegrationInboundDocumentProcessingConfiguration> queuedStatusProcessing;
    @JsonProperty
    private final List<IEdoIntegrationInboundDocumentProcessingConfiguration> sendedStatusProcessing;
    @JsonProperty
    private final List<IEdoIntegrationInboundDocumentProcessingConfiguration> defaultErrorProcessing;
    private ICstmType documentType;
    @JsonProperty
    private List<IEdoIntegrationSelectRule> counteragentSelectRules;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> answerMapping;
    @JsonProperty
    private List<IDictionaryElementConfiguration> contentGroups;
    @JsonProperty
    private List<IDictionaryElementConfiguration> archiveContentGroups;
    @JsonProperty
    private Map<String, List<String>> configurationScope;
    @JsonProperty
    private IDictionaryElementConfiguration targetTypeIdStorage;
    @JsonProperty
    private IDictionaryElementConfiguration sendSignatureTypes;
    @JsonProperty
    private List<IEdoDocumentAttributeDesc> edoDocumentAttributes;
    private String edoDocumentProcessorClassName;
    private String edoDocumentQueueProcessorClassName;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("sida_title_object_type", "ssa_title_object_type_name", "type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getTitleObjectTypeStorage, (a, b) -> a.setTitleObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_title_object_type_manual", "type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("rida_content_group", "content_group", "content_group", "content_groups", ICstmConfigAdapter.FieldType.content_group_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getContentGroups, (a, b) -> a.addContentGroup((IDictionaryElementConfiguration)b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearContentGroups));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("rida_archive_content_group", "archive_content_group", "archive_content_group", "archive_content_groups", ICstmConfigAdapter.FieldType.content_group_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getArchiveContentGroups, (a, b) -> a.addArchiveContentGroup((IDictionaryElementConfiguration)b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearArchiveContentGroups));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_content_group_mode", "ssa_content_group_mode_name", "content_group_mode", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getContentGroupModeStorage, (a, b) -> a.setContentGroupModeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_counteragent_mode", "ssa_counteragent_mode_name", "counteragent_mode", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getCounteragentModeStorage, (a, b) -> a.setCounteragentModeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_target_document_type", "ssa_target_document_typename", "target_document_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getTargetTypeIdStorage, (a, b) -> a.setTargetTypeIdStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_counteragent_select_rule", null, "rule", "counteragent_select_rules", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getCounteragentSelectRules, (a, b) -> a.addCounteragentSelectRulesFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearCounteragentSelectRules));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("sba_ignore_counteragent_pki_state", null, "ignore_counteragent_pki_state", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::isIgnoreCounteragentPkiState, (a, b) -> a.setIgnoreCounteragentPkiState(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("sba_remote_signature_mode", null, "remote_signature_mode", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::isRemoteSignatureMode, (a, b) -> a.setRemoteSignatureMode(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("sba_only_linked_counteragent", null, "only_linked_counteragent", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::isOnlyLinkedCounteragent, (a, b) -> a.setOnlyLinkedCounteragent(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_send_signature_types", "ssa_send_signature_types_name", "send_signature_types", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getSendSignatureTypesStorage, (a, b) -> a.setSendSignatureTypesStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_non_invited_counteragent_send_mode", "ssa_non_invited_counteragent_send_mode_name", "non_invited_counteragent_send_mode", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getNotInvitedCounteragentSendModeStorage, (a, b) -> a.setNotInvitedCounteragentSendModeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_queued_status_processing", null, "queued_status_processing", "queued_status_processing", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationDocumentOperationConfiguration::getQueuedStatusProcessing, (a, b) -> a.addQueuedStatusProcessingFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearQueuedStatusProcessing));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_sended_status_processing", null, "sended_status_processing", "sended_status_processing", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationDocumentOperationConfiguration::getSendedStatusProcessing, (a, b) -> a.addSendedStatusProcessingFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearSendedStatusProcessing));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_exchange_error_processing", null, "exchange_error_processing", "exchange_error_processing", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationDocumentOperationConfiguration::getExchangeErrorProcessing, (a, b) -> a.addExchangeErrorProcessingFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearExchangeErrorProcessing));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_send_prepare_error_processing", null, "send_prepare_error_processing", "send_prepare_error_processing", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationDocumentOperationConfiguration::getSendPrepareErrorProcessing, (a, b) -> a.addSendPrepareErrorProcessingFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearSendPrepareErrorProcessing));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("cstm_default_error_processing", null, "default_error_processing", "default_error_processing", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationDocumentOperationConfiguration::getDefaultErrorProcessing, (a, b) -> a.addDefaultErrorProcessingFromWrapper(b), IEdoIntegrationOutboundDocumentSendOperationConfiguration::clearDefaultErrorProcessing));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_exchange_category", "ssa_exchange_category_name", "exchange_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getExchangeCategoryStorage, (a, b) -> a.setExchangeCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_global_environment_name", "global_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getGlobalEnvironmentsNames, (a, b) -> a.setGlobalEnvironmentsNames(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_environment_name", "local_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getLocalEnvironmentsNames, (a, b) -> a.setLocalEnvironmentsNames(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_connection_info_environment_name", "connection_info_environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationProcessingConfiguration::getConnectionInfoEnvironmentsNames, (a, b) -> a.setConnectionInfoEnvironmentsNames(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_document_argument", "document_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getPreProcessingDocumentArgument, (a, b) -> a.setPreProcessingDocumentArgument(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentSendOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentSendOperationConfiguration::getStateCategory, (a, b) -> a.setStateCategory(Objects.toString(b, ""))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationOutboundDocumentSendOperationConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentSendOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundDocumentSendOperationConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundDocumentSendOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundDocumentSendOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationOutboundDocumentSendOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.sendPrepareErrorProcessing = new ArrayList<IEdoIntegrationInboundDocumentProcessingConfiguration>();
        this.exchangeErrorProcessing = new ArrayList<IEdoIntegrationInboundDocumentProcessingConfiguration>();
        this.queuedStatusProcessing = new ArrayList<IEdoIntegrationInboundDocumentProcessingConfiguration>();
        this.sendedStatusProcessing = new ArrayList<IEdoIntegrationInboundDocumentProcessingConfiguration>();
        this.defaultErrorProcessing = new ArrayList<IEdoIntegrationInboundDocumentProcessingConfiguration>();
        this.counteragentSelectRules = new ArrayList<IEdoIntegrationSelectRule>();
        this.answerMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.contentGroups = new ArrayList<IDictionaryElementConfiguration>();
        this.archiveContentGroups = new ArrayList<IDictionaryElementConfiguration>();
        this.edoDocumentAttributes = new ArrayList<IEdoDocumentAttributeDesc>();
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
    public IDictionaryElementConfiguration getCounteragentModeStorage() {
        return this.counteragentModeStorage;
    }
    
    @Override
    public void setCounteragentModeStorage(final IDictionaryElementConfiguration value) {
        this.counteragentModeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getSendSignatureTypesStorage() {
        return this.sendSignatureTypesStorage;
    }
    
    @Override
    public void setSendSignatureTypesStorage(final IDictionaryElementConfiguration value) {
        this.sendSignatureTypesStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getNotInvitedCounteragentSendModeStorage() {
        return this.notInvitedCounteragentProcessModeStorage;
    }
    
    @Override
    public void setNotInvitedCounteragentSendModeStorage(final IDictionaryElementConfiguration value) {
        this.notInvitedCounteragentProcessModeStorage = value;
    }
    
    @Override
    public String getNotInvitedCounteragentSendMode() {
        return (this.getNotInvitedCounteragentSendModeStorage() != null) ? this.getNotInvitedCounteragentSendModeStorage().getValue() : null;
    }
    
    @Override
    public List<IEdoIntegrationInboundDocumentProcessingConfiguration> getSendPrepareErrorProcessing() {
        return this.sendPrepareErrorProcessing;
    }
    
    @Override
    public void clearSendPrepareErrorProcessing() {
        this.getSendPrepareErrorProcessing().clear();
    }
    
    @Override
    public void addSendPrepareErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration value) {
        this.getSendPrepareErrorProcessing().add(value);
    }
    
    @Override
    public boolean isIgnoreCounteragentPkiState() {
        return this.ignoreCounteragentPkiState;
    }
    
    @Override
    public void setIgnoreCounteragentPkiState(final boolean value) {
        this.ignoreCounteragentPkiState = value;
    }
    
    @Override
    public boolean isRemoteSignatureMode() {
        return this.remotesignaturemode;
    }
    
    @Override
    public void setRemoteSignatureMode(final boolean value) {
        this.remotesignaturemode = value;
    }
    
    @Override
    public boolean isOnlyLinkedCounteragent() {
        return this.onlyLinkedCounteragent;
    }
    
    @Override
    public void setOnlyLinkedCounteragent(final boolean value) {
        this.onlyLinkedCounteragent = value;
    }
    
    @Override
    public void addSendPrepareErrorProcessingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createPrepareErrorProcessor(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addSendPrepareErrorProcessing(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createPrepareErrorProcessor(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addSendPrepareErrorProcessing(mappingRule);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationInboundDocumentProcessingConfiguration> getExchangeErrorProcessing() {
        return this.exchangeErrorProcessing;
    }
    
    @Override
    public void clearExchangeErrorProcessing() {
        this.getExchangeErrorProcessing().clear();
    }
    
    @Override
    public void addExchangeErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration value) {
        this.getExchangeErrorProcessing().add(value);
    }
    
    @Override
    public void addExchangeErrorProcessingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createExchangeErrorProcessor(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addExchangeErrorProcessing(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createExchangeErrorProcessor(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addExchangeErrorProcessing(mappingRule);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationInboundDocumentProcessingConfiguration> getDefaultErrorProcessing() {
        return this.defaultErrorProcessing;
    }
    
    @Override
    public void clearDefaultErrorProcessing() {
        this.getDefaultErrorProcessing().clear();
    }
    
    @Override
    public void addDefaultErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration value) {
        this.getDefaultErrorProcessing().add(value);
    }
    
    @Override
    public void addDefaultErrorProcessingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createDefaultErrorProcessor(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addDefaultErrorProcessing(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createDefaultErrorProcessor(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addDefaultErrorProcessing(mappingRule);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationInboundDocumentProcessingConfiguration> getQueuedStatusProcessing() {
        return this.queuedStatusProcessing;
    }
    
    @Override
    public void clearQueuedStatusProcessing() {
        this.getQueuedStatusProcessing().clear();
    }
    
    @Override
    public void addQueuedStatusProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration value) {
        this.getQueuedStatusProcessing().add(value);
    }
    
    @Override
    public IDictionaryElementConfiguration getExchangeCategoryStorage() {
        return this.exchangeCategoryStorage;
    }
    
    @Override
    public void setExchangeCategoryStorage(final IDictionaryElementConfiguration exchangeCategoryStorage) {
        this.exchangeCategoryStorage = exchangeCategoryStorage;
    }
    
    @Override
    public void addQueuedStatusProcessingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createQueuedStatusProcessor(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addQueuedStatusProcessing(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createQueuedStatusProcessor(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addQueuedStatusProcessing(mappingRule);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationInboundDocumentProcessingConfiguration> getSendedStatusProcessing() {
        return this.sendedStatusProcessing;
    }
    
    @Override
    public void clearSendedStatusProcessing() {
        this.getSendedStatusProcessing().clear();
    }
    
    @Override
    public void addSendedStatusProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration value) {
        this.getSendedStatusProcessing().add(value);
    }
    
    @Override
    public void addSendedStatusProcessingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createSendedStatusProcessor(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addSendedStatusProcessing(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundDocumentProcessingConfiguration mappingRule = this.createSendedStatusProcessor(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addSendedStatusProcessing(mappingRule);
            }
        }
    }
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createQueuedStatusProcessor(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createQueuedStatusProcessor(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createSendedStatusProcessor(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createSendedStatusProcessor(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createPrepareErrorProcessor(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createPrepareErrorProcessor(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createExchangeErrorProcessor(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createExchangeErrorProcessor(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createDefaultErrorProcessor(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationInboundDocumentProcessingConfiguration createDefaultErrorProcessor(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public final List<IEdoDocumentAttributeDesc> getEdoDocumentAttributes() {
        return this.edoDocumentAttributes;
    }
    
    public final void addEdoDocumentAttribute(final IEdoDocumentAttributeDesc value) {
        this.edoDocumentAttributes.add(value);
    }
    
    public final void clearEdoDocumentAttribute() {
        this.edoDocumentAttributes.clear();
    }
    
    @Override
    public IDictionaryElementConfiguration getTargetTypeIdStorage() {
        return this.targetTypeIdStorage;
    }
    
    @Override
    public void setTargetTypeIdStorage(final IDictionaryElementConfiguration value) {
        this.targetTypeIdStorage = value;
    }
    
    public IDictionaryElementConfiguration getSendSignatureTypes() {
        return this.sendSignatureTypes;
    }
    
    public void setSendSignatureTypes(final IDictionaryElementConfiguration value) {
        this.sendSignatureTypes = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getContentGroupModeStorage() {
        return this.contentGroupModeStorage;
    }
    
    @Override
    public void setContentGroupModeStorage(final IDictionaryElementConfiguration value) {
        this.contentGroupModeStorage = value;
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getContentGroups() {
        return this.contentGroups;
    }
    
    @Override
    public void clearContentGroups() {
        this.contentGroups.clear();
    }
    
    @Override
    public void addContentGroup(final IDictionaryElementConfiguration value) {
        this.getContentGroups().add(value);
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getArchiveContentGroups() {
        return this.archiveContentGroups;
    }
    
    @Override
    public void clearArchiveContentGroups() {
        this.archiveContentGroups.clear();
    }
    
    @Override
    public void addArchiveContentGroup(final IDictionaryElementConfiguration value) {
        this.getArchiveContentGroups().add(value);
    }
    
    @Override
    public List<IEdoIntegrationSelectRule> getCounteragentSelectRules() {
        return this.counteragentSelectRules;
    }
    
    @Override
    public void clearCounteragentSelectRules() {
        this.getCounteragentSelectRules().clear();
    }
    
    @Override
    public void addCounteragentSelectRule(final IEdoIntegrationSelectRule value) {
        this.getCounteragentSelectRules().add(value);
    }
    
    @Override
    public void addCounteragentSelectRulesFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationSelectRule rule = this.createCounteragentSelectRule(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (rule != null) {
                this.addCounteragentSelectRule(rule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationSelectRule rule = this.createCounteragentSelectRule(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (rule != null) {
                this.addCounteragentSelectRule(rule);
            }
        }
    }
    
    @Override
    public String getTargetTypeId() {
        return (this.getTargetTypeIdStorage() != null) ? this.getTargetTypeIdStorage().getValue() : null;
    }
    
    public List<IEdoIntegrationAttributeMapping> getAnswerMapping() {
        return this.answerMapping;
    }
    
    public void addAnswerMapAttributes(final IEdoIntegrationAttributeMapping value) {
        this.getAnswerMapping().add(value);
    }
    
    public void clearReverseMapAttributes() {
        this.getAnswerMapping().clear();
    }
    
    public Map<String, List<String>> getConfigurationScope() {
        return this.configurationScope;
    }
    
    public void setConfigurationScope(final Map<String, List<String>> scope) {
        this.configurationScope = scope;
    }
    
    public ICstmType getDocumentType() {
        return this.documentType;
    }
    
    public abstract IEdoIntegrationSelectRule createCounteragentSelectRule(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationSelectRule createCounteragentSelectRule(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final Generator p2, final SessionContext p3);
    
    protected abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getEdoDocumentProcessorClassName() {
        return this.edoDocumentProcessorClassName;
    }
    
    public void setEdoDocumentProcessorClassName(final String value) {
        this.edoDocumentProcessorClassName = value;
    }
    
    @Override
    public String getEdoDocumentQueueProcessorClassName() {
        return this.edoDocumentQueueProcessorClassName;
    }
    
    public void setEdoDocumentQueueProcessorClassName(final String value) {
        this.edoDocumentQueueProcessorClassName = value;
    }
    
    @Override
    public String getContentGroupMode() {
        return (this.getContentGroupModeStorage() == null) ? null : this.getContentGroupModeStorage().getValue();
    }
    
    @Override
    public String getExchangeCategory() {
        return (this.getExchangeCategoryStorage() == null) ? null : this.getExchangeCategoryStorage().getValue();
    }
    
    @Override
    public String getStateCategory() {
        return this.stateCategory;
    }
    
    @Override
    public void setStateCategory(final String value) {
        this.stateCategory = value;
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
    public String getConnectionInfoEnvironmentsNames() {
        return this.connectionInfoEnvironmentsNames;
    }
    
    @Override
    public void setConnectionInfoEnvironmentsNames(final String value) {
        this.connectionInfoEnvironmentsNames = value;
    }
    
    @Override
    public String getPreProcessingDocumentArgument() {
        return this.preProcessingDocumentArgument;
    }
    
    @Override
    public void setPreProcessingDocumentArgument(final String value) {
        this.preProcessingDocumentArgument = value;
    }
    
    static {
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
    
    public enum CounteragentMode
    {
        single, 
        sequental, 
        multi;
    }
}
