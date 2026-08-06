// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundAttributeMappingByFixedValue extends CstmConfigAdapter implements IEdoIntegrationAttributeMappingByFixedValue
{
    private static final long serialVersionUID = 737968932739544582L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>> fieldDefinition;
    @JsonProperty
    private String value;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>("ssa_src_value", "src_value", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAttributeMappingByFixedValue, Object>)IEdoIntegrationAttributeMappingByFixedValue::getValue, (a, b) -> a.setValue(Objects.toString(b, ""))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByFixedValue>> getFieldDefinition() {
        return EdoIntegrationOutboundAttributeMappingByFixedValue.fieldDefinition;
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
    
    protected EdoIntegrationOutboundAttributeMappingByFixedValue(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
