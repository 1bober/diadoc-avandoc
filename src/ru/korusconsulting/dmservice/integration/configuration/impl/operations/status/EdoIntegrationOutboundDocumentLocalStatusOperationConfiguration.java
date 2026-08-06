// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.status;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentLocalStatus;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundDocumentLocalStatusOperationConfiguration extends CstmConfigAdapter implements IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = -7764787862186563402L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IEdoIntegrationDocumentLocalStatus> statusStorage;
    @JsonProperty
    private IDictionaryElementConfiguration coverageStorage;
    @JsonProperty
    private IDictionaryElementConfiguration processingEventStorage;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> answerMappingTable;
    @JsonProperty
    private boolean terminalState;
    @JsonProperty
    private int maxAttempts;
    @JsonProperty
    private boolean dontMarkAsProceed;
    @JsonProperty
    private boolean breakOperation;
    @JsonProperty
    private String statusQuery;
    @JsonProperty
    private String stateCategory;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("rsa_statuses", "rsa_statuses_names", "status", "local_statuses", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getLocalStatuses, (a, b) -> a.addLocalStatusFromWrapper(b), IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::clearLocalStatuses));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("ssa_coverage", "ssa_coverage_name", "coverage", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getCoverageStorage, (a, b) -> a.setCoverageStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("sba_dont_mark_as_proceed", "dont_mark_as_proceed", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::isDontMarkAsProceed, (a, b) -> a.setDontMarkAsProceed(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("sba_break_operation", "break_operation", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::isBreakOperation, (a, b) -> a.setBreakOperation(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("sida_processing_event", "ssa_processing_event_name", "processing_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getProcessingEventStorage, (a, b) -> a.setProcessingEventStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("sia_max_attempts", "max_attempts", ICstmConfigAdapter.FieldType.integer, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getMaxAttempts, (a, b) -> a.setMaxAttempts((b == null) ? 0 : ((int)b))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("ssa_query_status", "query_status", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getStatusQuery, (a, b) -> a.setStatusQuery(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("cstm_attribute_mapping", null, "answer_mapping", "answer_mapping_table", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getAnswerMapping, (a, b) -> a.addAnswerMappingFromWrapper(b), IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::clearAnswerMapping));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>("ssa_state_category", "state_category", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration, Object>)IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration::getStateCategory, (a, b) -> a.setStateCategory(Objects.toString(b, ""))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundDocumentLocalStatusProcessingOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundDocumentLocalStatusOperationConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationOutboundDocumentLocalStatusOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.statusStorage = new ArrayList<IEdoIntegrationDocumentLocalStatus>();
        this.answerMappingTable = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public final List<IEdoIntegrationDocumentLocalStatus> getLocalStatuses() {
        return this.statusStorage;
    }
    
    @Override
    public final void addLocalStatus(final IEdoIntegrationDocumentLocalStatus newStatus) {
        this.getLocalStatuses().add(newStatus);
    }
    
    @Override
    public final void clearLocalStatuses() {
        this.getLocalStatuses().clear();
    }
    
    @Override
    public void addLocalStatusFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentLocalStatus newValue = this.createLocalStatus(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addLocalStatus(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentLocalStatus newValue = this.createLocalStatus(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addLocalStatus(newValue);
            }
        }
    }
    
    public abstract IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public IDictionaryElementConfiguration getProcessingEventStorage() {
        return this.processingEventStorage;
    }
    
    @Override
    public void setProcessingEventStorage(final IDictionaryElementConfiguration value) {
        this.processingEventStorage = value;
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getAnswerMapping() {
        return this.answerMappingTable;
    }
    
    @Override
    public void clearAnswerMapping() {
        this.getAnswerMapping().clear();
    }
    
    @Override
    public void addAnswerMapping(final IEdoIntegrationAttributeMapping value) {
        this.getAnswerMapping().add(value);
    }
    
    @Override
    public void addAnswerMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping newValue = this.createAnswerMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addAnswerMapping(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping newValue = this.createAnswerMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addAnswerMapping(newValue);
            }
        }
    }
    
    @Override
    public final boolean isTerminalState() {
        return this.terminalState;
    }
    
    @Override
    public final void setTerminalState(final boolean value) {
        this.terminalState = value;
    }
    
    @Override
    public final IDictionaryElementConfiguration getCoverageStorage() {
        return this.coverageStorage;
    }
    
    @Override
    public final void setCoverageStorage(final IDictionaryElementConfiguration value) {
        this.coverageStorage = value;
    }
    
    @Override
    public final boolean isDontMarkAsProceed() {
        return this.dontMarkAsProceed;
    }
    
    @Override
    public final void setDontMarkAsProceed(final boolean value) {
        this.dontMarkAsProceed = value;
    }
    
    @Override
    public final boolean isBreakOperation() {
        return this.breakOperation;
    }
    
    @Override
    public final void setBreakOperation(final boolean value) {
        this.breakOperation = value;
    }
    
    @Override
    public final int getMaxAttempts() {
        return this.maxAttempts;
    }
    
    @Override
    public final void setMaxAttempts(final int value) {
        this.maxAttempts = value;
    }
    
    @Override
    public final String getStatusQuery() {
        return this.statusQuery;
    }
    
    @Override
    public final void setStatusQuery(final String value) {
        this.statusQuery = value;
    }
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getProcessingEvent() {
        return (this.getProcessingEventStorage() != null) ? this.getProcessingEventStorage().getId() : null;
    }
    
    @Override
    public String getStatusCoverage() {
        return (this.getCoverageStorage() != null) ? this.getCoverageStorage().getValue() : null;
    }
    
    public List<String> getLocalErrors() {
        return this.getLocalStatuses().stream().map((Function<? super Object, ? extends String>)IEdoIntegrationDocumentLocalStatus::getStatusCode).toList();
    }
    
    @Override
    public String getStateCategory() {
        return this.stateCategory;
    }
    
    @Override
    public void setStateCategory(final String value) {
        this.stateCategory = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
