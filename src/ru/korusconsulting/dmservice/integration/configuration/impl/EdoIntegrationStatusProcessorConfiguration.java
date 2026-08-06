// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Map;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationStatusProcessorConfiguration;

public abstract class EdoIntegrationStatusProcessorConfiguration extends CstmConfigAdapter implements IEdoIntegrationStatusProcessorConfiguration
{
    private static final long serialVersionUID = -3044270023318671474L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>> fieldDefinition;
    @JsonProperty
    private final List<IEdoIntegrationDocumentRemoteStatus> statusesStorage;
    @JsonProperty
    private IDictionaryElementConfiguration eventStorage;
    @JsonProperty
    private String postActionsId;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> localMapping;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> remoteMapping;
    @JsonProperty
    private boolean deleteRemoteDocument;
    @JsonProperty
    private int maxAttempts;
    @JsonProperty
    private int attemptsInterval;
    @JsonProperty
    private boolean dontMarkAsProceed;
    @JsonProperty
    private IDictionaryElementConfiguration stateCategoryStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("rsa_statuses", "ssa_status_code", "status", "statuses", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getStatusesStorage, (a, b) -> a.addStatusFromWrapper(b), IEdoIntegrationStatusProcessorConfiguration::clearStatusesStorage));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("sida_event", "ssa_event_name", "event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getEventStorage, (a, b) -> a.setEventStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("cstm_local_map_attributes_editor", null, "mapping", "local", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getLocalMapping, IEdoIntegrationStatusProcessorConfiguration::addLocalMappingFromWrapper, IEdoIntegrationStatusProcessorConfiguration::clearLocalMapping));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("cstm_attribute_mapping", null, "mapping", "remote", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getRemoteMapping, IEdoIntegrationStatusProcessorConfiguration::addRemoteMappingFromWrapper, IEdoIntegrationStatusProcessorConfiguration::clearRemoteMapping));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("sba_delete_remote_document", "delete_remote_document", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::isDeleteRemoteDocument, (a, b) -> a.setDeleteRemoteDocument(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("sia_max_attempts", "max_attempts", ICstmConfigAdapter.FieldType.integer, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getMaxAttempts, (a, b) -> a.setMaxAttempts((int)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("sia_attempts_interval", "attempts_interval", ICstmConfigAdapter.FieldType.integer, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getAttemptsInterval, (a, b) -> a.setAttemptsInterval((int)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("sba_dont_mark_as_proceed", "dont_mark_as_proceed", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::isDontMarkAsProceed, (a, b) -> a.setDontMarkAsProceed(b != null && (boolean)b), null));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getStateCategoryStorage, (a, b) -> a.setStateCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>("postactions", "postactions", ICstmConfigAdapter.FieldType.cstm_object_link, (Function<IEdoIntegrationStatusProcessorConfiguration, Object>)IEdoIntegrationStatusProcessorConfiguration::getPostActionsId, (a, b) -> a.setPostActionsId((String)b), (Consumer<IEdoIntegrationStatusProcessorConfiguration>)null, (a, g, ctx) -> a.unmarshalPostActions(g, ctx)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusProcessorConfiguration>> getFieldDefinition() {
        return EdoIntegrationStatusProcessorConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationStatusProcessorConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.statusesStorage = new ArrayList<IEdoIntegrationDocumentRemoteStatus>();
        this.localMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.remoteMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public IDictionaryElementConfiguration getEventStorage() {
        return this.eventStorage;
    }
    
    @Override
    public void setEventStorage(final IDictionaryElementConfiguration value) {
        this.eventStorage = value;
    }
    
    @Override
    public boolean isDeleteRemoteDocument() {
        return this.deleteRemoteDocument;
    }
    
    @Override
    public void setDeleteRemoteDocument(final boolean value) {
        this.deleteRemoteDocument = value;
    }
    
    @Override
    public int getMaxAttempts() {
        return this.maxAttempts;
    }
    
    @Override
    public void setMaxAttempts(final int value) {
        this.maxAttempts = value;
    }
    
    @Override
    public int getAttemptsInterval() {
        return this.attemptsInterval;
    }
    
    @Override
    public void setAttemptsInterval(final int value) {
        this.attemptsInterval = value;
    }
    
    @Override
    public List<IEdoIntegrationDocumentRemoteStatus> getStatusesStorage() {
        return this.statusesStorage;
    }
    
    @Override
    public void clearStatusesStorage() {
        this.getStatusesStorage().clear();
    }
    
    @Override
    public void addStatusFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addStatus(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addStatus(newValue);
            }
        }
        else {
            System.err.println("WTF?");
        }
    }
    
    @Override
    public void addStatus(final IEdoIntegrationDocumentRemoteStatus value) {
        this.getStatusesStorage().add(value);
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getLocalMapping() {
        return this.localMapping;
    }
    
    @Override
    public void clearLocalMapping() {
        this.getLocalMapping().clear();
    }
    
    @Override
    public void addLocalMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addLocalMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addLocalMapping(mappingRule);
            }
        }
    }
    
    @Override
    public void addLocalMapping(final IEdoIntegrationAttributeMapping value) {
        this.getLocalMapping().add(value);
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getRemoteMapping() {
        return this.remoteMapping;
    }
    
    @Override
    public void clearRemoteMapping() {
        this.getRemoteMapping().clear();
    }
    
    @Override
    public void addRemoteMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addLocalMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addLocalMapping(mappingRule);
            }
        }
    }
    
    @Override
    public void addRemoteMapping(final IEdoIntegrationAttributeMapping value) {
        this.getRemoteMapping().add(value);
    }
    
    @Override
    public void setDontMarkAsProceed(final boolean value) {
        this.dontMarkAsProceed = value;
    }
    
    @Override
    public boolean isDontMarkAsProceed() {
        return this.dontMarkAsProceed;
    }
    
    protected abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    protected abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getEvent() {
        return (this.getEventStorage() != null) ? this.getEventStorage().getId() : null;
    }
    
    @Override
    public List<String> getStatuses() {
        return this.getStatusesStorage().stream().map((Function<? super Object, ? extends String>)IEdoIntegrationDocumentRemoteStatus::getStatusCode).toList();
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
    public void setPostActionsId(final String value) {
        this.postActionsId = value;
    }
    
    @Override
    public String getPostActionsId() {
        return this.postActionsId;
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
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
