// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.status;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationInboundDocumentProcessingConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration extends EdoIntegrationInboundDocumentProcessingConfiguration implements IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = -2339698564621506086L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IEdoIntegrationDocumentRemoteStatus> remoteStatuses;
    @JsonProperty
    private IDictionaryElementConfiguration processingEventStorage;
    @JsonProperty
    private String statusQuery;
    @JsonProperty
    private IDictionaryElementConfiguration stateCategoryStorage;
    @JsonProperty
    private IDictionaryElementConfiguration contentPostProcessingEventStorage;
    @JsonProperty
    private final List<IDictionaryElementConfiguration> postActions;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>("rsa_statuses", "rsa_statuses_names", "status", "remote_statuses", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::getRemoteStatuses, (a, b) -> a.addRemoteStatusFromWrapper(b), IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::clearRemoteStatuses, true));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>("sida_event", "ssa_event_name", "processing_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::getProcessingEventStorage, (a, b) -> a.setProcessingEventStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::getStateCategoryStorage, (a, b) -> a.setStateCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>("ssa_query_status", "query_status", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::getStatusQuery, (a, b) -> a.setStatusQuery(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>("sida_content_post_processing_event", "ssa_content_post_processing_event_name", "content_post_processing_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration::getContentPostProcessingEventStorage, (a, b) -> a.setContentPostProcessingEventStorage((IDictionaryElementConfiguration)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.remoteStatuses = new ArrayList<IEdoIntegrationDocumentRemoteStatus>();
        this.postActions = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    public abstract IEdoIntegrationInboundAttachmentProcessingRule createIncomingAttachmentProcessingRule(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingRule createIncomingAttachmentProcessingRule(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses() {
        return this.remoteStatuses;
    }
    
    @Override
    public void addRemoteStatus(final IEdoIntegrationDocumentRemoteStatus value) {
        this.getRemoteStatuses().add(value);
    }
    
    @Override
    public void clearRemoteStatuses() {
        this.getRemoteStatuses().clear();
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
    public IDictionaryElementConfiguration getProcessingEventStorage() {
        return this.processingEventStorage;
    }
    
    @Override
    public void setProcessingEventStorage(final IDictionaryElementConfiguration value) {
        this.processingEventStorage = value;
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
    public String getStatusQuery() {
        return this.statusQuery;
    }
    
    @Override
    public void setStatusQuery(final String value) {
        this.statusQuery = value;
    }
    
    @Override
    public boolean isDontMarkAsProceed() {
        return false;
    }
    
    @Override
    public int getMaxAttempts() {
        return 1;
    }
    
    @Override
    public boolean isBreakOperation() {
        return false;
    }
    
    @Override
    public IDictionaryElementConfiguration getContentPostProcessingEventStorage() {
        return this.contentPostProcessingEventStorage;
    }
    
    @Override
    public void setContentPostProcessingEventStorage(final IDictionaryElementConfiguration value) {
        this.contentPostProcessingEventStorage = value;
    }
    
    @Override
    public String getContentPostProcessingEvent() {
        return (this.getContentPostProcessingEventStorage() == null) ? null : this.getContentPostProcessingEventStorage().getId();
    }
    
    public Object getParentLinkSideEffectValue(final ICstmComponent parent, final ICstmComponent child, final String baseName, final Object defaultValue) {
        String mainConfigName = String.format("%s_%s", child.getParentLinkType(), baseName);
        if (!parent.getSideEffects().containsKey(mainConfigName)) {
            mainConfigName = baseName;
        }
        final Object value = parent.getSideEffects().get(mainConfigName);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public Object getParentLinkSideEffectValue(final ICstmComponent parent, final ICstmComponent child, final String baseName) {
        return this.getParentLinkSideEffectValue(parent, child, baseName, null);
    }
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getProcessingEvent() {
        return (this.getProcessingEventStorage() != null) ? this.getProcessingEventStorage().getId() : null;
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
    public String getStateCategory() {
        return (this.getStateCategoryStorage() != null) ? this.getStateCategoryStorage().getValue() : null;
    }
    
    @Override
    public void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration>> fieldDefs = getFieldDefinition().stream().filter(ConfigFieldBaseDescriptionExt::isCheckerField).toList();
        this.unmarshalFields(session, this, fieldDefs, config, ctx);
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
