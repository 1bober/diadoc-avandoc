// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationIncomingDocumentReceiveScopedConfiguration;

public abstract class EdoIntegrationIncomingDocumentReceiveScopedConfiguration extends EdoIntegrationScopedConfiguration implements IEdoIntegrationIncomingDocumentReceiveScopedConfiguration
{
    private static final long serialVersionUID = -7871501051937433414L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentReceiveScopedConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IDictionaryElementConfiguration> externalTypesStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentReceiveScopedConfiguration>> getFieldDefinition() {
        return EdoIntegrationIncomingDocumentReceiveScopedConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationIncomingDocumentReceiveScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.externalTypesStorage = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getExternalTypesStorage() {
        return this.externalTypesStorage;
    }
    
    @Override
    public List<String> getExternalTypes() {
        return this.getExternalTypesStorage().stream().map(a -> a.getValue()).toList();
    }
    
    @Override
    public void addExternalType(final IDictionaryElementConfiguration value) {
        this.getExternalTypesStorage().add(value);
    }
    
    @Override
    public void clearExternalTypes() {
        this.getExternalTypesStorage().clear();
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("rida_external_document_type", "rsa_external_document_type_name", "external_type", "external_types", ICstmConfigAdapter.FieldType.dictionary_list, (Function<T, Object>)IEdoIntegrationIncomingDocumentReceiveScopedConfiguration::getExternalTypesStorage, (a, b) -> a.addExternalType((IDictionaryElementConfiguration)b), IEdoIntegrationIncomingDocumentReceiveScopedConfiguration::clearExternalTypes));
    }
}
