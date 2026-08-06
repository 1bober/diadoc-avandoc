// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationExchangeErrorCategoryConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationExchangeErrorCategoryConfiguration extends EdoIntegrationDictionaryElementWithCode implements IEdoIntegrationExchangeErrorCategoryConfiguration
{
    private static final long serialVersionUID = 5819911196234541319L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationExchangeErrorCategoryConfiguration>> fieldDefinition;
    @JsonProperty
    private boolean reiterable;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationExchangeErrorCategoryConfiguration>> getFieldDefinition() {
        return EdoIntegrationExchangeErrorCategoryConfiguration.fieldDefinition;
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
        config.setAttributeValue("config_type", this.getConfigType());
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), element, ctx);
    }
    
    protected EdoIntegrationExchangeErrorCategoryConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public boolean isReiterable() {
        return this.reiterable;
    }
    
    @Override
    public void setReiterable(final boolean value) {
        this.reiterable = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sba_reiterable", "reiterable", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)IEdoIntegrationExchangeErrorCategoryConfiguration::isReiterable, (a, b) -> a.setReiterable(b != null && (boolean)b)));
    }
}
