// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundRevokeOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundRevokeOperationConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationOutboundRevokeOperationConfiguration
{
    private static final long serialVersionUID = 7401698196961498117L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> fieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> typeFieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration titleObjectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration stateCategoryStorage;
    @JsonProperty
    private List<IEdoIntegrationDocumentRemoteStatus> remoteStatuses;
    @JsonProperty
    private List<IDictionaryElementConfiguration> contentGroups;
    @JsonProperty
    private final List<IEdoIntegrationInboundAttachmentProcessingEvent> processorEvents;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("sida_title_object_type", "ssa_title_object_type_name", "type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationOutboundRevokeOperationConfiguration, Object>)IEdoIntegrationOutboundRevokeOperationConfiguration::getTitleObjectTypeStorage, (a, b) -> a.setTitleObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("ssa_title_object_type_manual", "type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("rsa_statuses", "rsa_statuses_names", "status", "remote_statuses", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationOutboundRevokeOperationConfiguration, Object>)IEdoIntegrationOutboundRevokeOperationConfiguration::getRemoteStatuses, (a, b) -> a.addRemoteStatusFromWrapper(b), IEdoIntegrationOutboundRevokeOperationConfiguration::clearRemoteStatuses, true));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("rida_content_group", "content_group", "content_group", "content_groups", ICstmConfigAdapter.FieldType.content_group_list, (Function<IEdoIntegrationOutboundRevokeOperationConfiguration, Object>)IEdoIntegrationOutboundRevokeOperationConfiguration::getContentGroups, (a, b) -> a.addContentGroup((IDictionaryElementConfiguration)b), IEdoIntegrationOutboundRevokeOperationConfiguration::clearContentGroups));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundRevokeOperationConfiguration, Object>)IEdoIntegrationOutboundRevokeOperationConfiguration::getStateCategoryStorage, (a, b) -> a.setStateCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>("processor_event_link", null, "processor_event_links", "processor_event_link", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundRevokeOperationConfiguration, Object>)IEdoIntegrationOutboundRevokeOperationConfiguration::getProcessorEvents, (a, b) -> a.addProcessorEventFromWrapper(b), IEdoIntegrationOutboundRevokeOperationConfiguration::clearProcessorEvents));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationOutboundRevokeOperationConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundRevokeOperationConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundRevokeOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundRevokeOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    @Override
    public void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundRevokeOperationConfiguration>> fieldDefs = getFieldDefinition().stream().filter(ConfigFieldBaseDescriptionExt::isCheckerField).toList();
        this.unmarshalFields(session, this, fieldDefs, config, ctx);
    }
    
    protected EdoIntegrationOutboundRevokeOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.remoteStatuses = new ArrayList<IEdoIntegrationDocumentRemoteStatus>();
        this.contentGroups = new ArrayList<IDictionaryElementConfiguration>();
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
    public List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses() {
        return this.remoteStatuses;
    }
    
    @Override
    public void addRemoteStatus(final IEdoIntegrationDocumentRemoteStatus value) {
        this.getRemoteStatuses().add(value);
    }
    
    @Override
    public void addRemoteStatusFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteStatus(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteStatus(newValue);
            }
        }
    }
    
    @Override
    public void clearRemoteStatuses() {
        this.getRemoteStatuses().clear();
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
    public boolean isApplicableForStatus(final String value) {
        if (this.getRemoteStatuses() == null) {
            return false;
        }
        for (final IEdoIntegrationDocumentRemoteStatus a : this.getRemoteStatuses()) {
            if (StringUtils.equals(a.getResponseStatus(), value)) {
                return true;
            }
        }
        return false;
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
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
        typeFieldDefinition = initTypeFieldDefinition();
    }
}
