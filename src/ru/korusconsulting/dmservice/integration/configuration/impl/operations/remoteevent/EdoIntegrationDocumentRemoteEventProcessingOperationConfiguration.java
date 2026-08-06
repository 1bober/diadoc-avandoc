// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.remoteevent;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentEventProcessingOperationConfiguration;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.remoteevent.IEdoIntegrationDocumentRemoteEvent;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

public abstract class EdoIntegrationDocumentRemoteEventProcessingOperationConfiguration extends CstmConfigAdapter implements IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration
{
    private static final long serialVersionUID = -5785954925300185297L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>> fieldDefinition;
    private List<IEdoIntegrationDocumentRemoteEvent> remoteEvents;
    private IDictionaryElementConfiguration processingEventStorage;
    private List<IEdoIntegrationAttributeMapping> answerMappingTable;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>("rsa_remote_events", "ssa_remote_event", "event", "events", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration, Object>)IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration::getRemoteEvents, (a, b) -> a.addRemoteEventFromWrapper(b), IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration::clearRemoteEvents, true));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>("sida_event", "ssa_event_name", "processing_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration, Object>)IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration::getProcessingEventStorage, (a, b) -> a.setProcessingEventStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>("cstm_attribute_mapping", null, "answer_mapping", "answer_mapping_table", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration, Object>)IEdoIntegrationDocumentEventProcessingOperationConfiguration::getAnswerMapping, (a, b) -> a.addAnswerMappingFromWrapper(b), IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration::clearAnswerMapping));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationDocumentRemoteEventProcessingOperationConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEventProcessingOperationConfiguration>> fieldDefs = getFieldDefinition().stream().filter(ConfigFieldBaseDescriptionExt::isCheckerField).toList();
        this.unmarshalFields(session, this, fieldDefs, config, ctx);
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
    
    protected EdoIntegrationDocumentRemoteEventProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.remoteEvents = new ArrayList<IEdoIntegrationDocumentRemoteEvent>();
        this.answerMappingTable = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public List<IEdoIntegrationDocumentRemoteEvent> getRemoteEvents() {
        return this.remoteEvents;
    }
    
    @Override
    public void addRemoteEvent(final IEdoIntegrationDocumentRemoteEvent value) {
        this.getRemoteEvents().add(value);
    }
    
    @Override
    public void clearRemoteEvents() {
        this.getRemoteEvents().clear();
    }
    
    @Override
    public boolean isApplicableForEvent(final String value) {
        return true;
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
    
    public abstract IEdoIntegrationDocumentRemoteEvent createRemoteEvent(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentRemoteEvent createRemoteEvent(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getProcessingEvent() {
        return (this.getProcessingEventStorage() != null) ? this.getProcessingEventStorage().getId() : null;
    }
    
    @Override
    public void addAnswerMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAnswerMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addAnswerMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAnswerMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addAnswerMapping(mappingRule);
            }
        }
    }
    
    @Override
    public void addRemoteEventFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentRemoteEvent newValue = this.createRemoteEvent(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteEvent(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentRemoteEvent newValue = this.createRemoteEvent(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteEvent(newValue);
            }
        }
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
