// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.ITypeDescriptionElementConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;

public abstract class EdoIntegrationDictionaryElementWithCode extends CstmConfigAdapter implements IDictionaryElementConfiguration
{
    private static final long serialVersionUID = -5763459427322233607L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDictionaryElementConfiguration>> fieldDefinition;
    @JsonProperty
    private String value;
    @JsonProperty
    private String label;
    
    private static List<ConfigFieldBaseDescriptionExt<IDictionaryElementConfiguration>> getFieldDefinition() {
        return EdoIntegrationDictionaryElementWithCode.fieldDefinition;
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
    
    protected EdoIntegrationDictionaryElementWithCode(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getId() {
        return this.getValue();
    }
    
    @Override
    public void setId(final String value) {
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    @Override
    public String getLabel() {
        return this.label;
    }
    
    @Override
    public void setLabel(final String value) {
        this.label = value;
    }
    
    public abstract String getConfigType();
    
    @Override
    public ITypeDescriptionElementConfiguration getRootType() {
        if (this.getParent() != null) {
            return this.getParent().getRootType();
        }
        return null;
    }
    
    @Override
    public ITypeDescriptionElementConfiguration getType() {
        if (this.getParent() != null) {
            return this.getParent().getType();
        }
        return null;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_element_code", "value", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IDictionaryElementConfiguration::getValue, (a, b) -> a.setValue(Objects.toString(b, ""))), new ConfigFieldBaseDescriptionExt("ssa_name", "label", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IDictionaryElementConfiguration::getLabel, (a, b) -> a.setLabel(Objects.toString(b, ""))));
    }
}
