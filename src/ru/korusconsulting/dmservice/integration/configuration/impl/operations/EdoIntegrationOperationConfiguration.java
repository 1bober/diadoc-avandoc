// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOperationConfiguration extends CstmConfigAdapter implements IEdoIntegrationOperationConfiguration
{
    private static final long serialVersionUID = 1494592016354406107L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration integrationLogEventStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationOperationConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IDictionaryElementConfiguration getIntegrationLogEventIdStorage() {
        return this.integrationLogEventStorage;
    }
    
    @Override
    public void setIntegrationLogEventIdStorage(final IDictionaryElementConfiguration value) {
        this.integrationLogEventStorage = value;
    }
    
    @Override
    public String getIntegrationLogEventId() {
        return (this.getIntegrationLogEventIdStorage() == null) ? null : this.getIntegrationLogEventIdStorage().getValue();
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_integration_log_event", "ssa_integration_log_event_name", "integration_log_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<T, Object>)IEdoIntegrationOperationConfiguration::getIntegrationLogEventIdStorage, (a, b) -> a.setIntegrationLogEventIdStorage((IDictionaryElementConfiguration)b), null));
    }
}
