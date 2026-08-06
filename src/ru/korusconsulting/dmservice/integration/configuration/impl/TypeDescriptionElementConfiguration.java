// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.cstm.ICstmType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.ITypeDescriptionElementConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class TypeDescriptionElementConfiguration extends DictionaryElementConfiguration implements ITypeDescriptionElementConfiguration
{
    private static final long serialVersionUID = 3067386164900472372L;
    private static final transient List<ConfigFieldBaseDescriptionExt<ITypeDescriptionElementConfiguration>> fieldDefinition;
    @JsonProperty
    private boolean cstmLoaded;
    private ICstmType cstmType;
    public static final String TYPE_CONFIG_TYPE = "type";
    
    private static List<ConfigFieldBaseDescriptionExt<ITypeDescriptionElementConfiguration>> getFieldDefinition() {
        return TypeDescriptionElementConfiguration.fieldDefinition;
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
    
    public TypeDescriptionElementConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public boolean isCstmLoaded() {
        return this.cstmLoaded;
    }
    
    @Override
    public void setCstmLoaded(final boolean value) {
        this.cstmLoaded = value;
    }
    
    @Override
    public ICstmType getCstmType() {
        return this.cstmType;
    }
    
    @Override
    public void setCstmType(final ICstmType value) {
        this.cstmType = value;
    }
    
    @Override
    public String getConfigType() {
        return "type";
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("cstm_loaded", "cstm_loaded", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)ITypeDescriptionElementConfiguration::isCstmLoaded, (a, b) -> a.setCstmLoaded(b != null && (boolean)b)));
    }
}
