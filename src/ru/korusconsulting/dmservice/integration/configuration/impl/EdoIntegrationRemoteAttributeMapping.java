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
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationRemoteAttributeMapping;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationRemoteAttributeMapping extends CstmConfigAdapter implements IEdoIntegrationRemoteAttributeMapping
{
    private static final long serialVersionUID = -4263715320993085848L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationRemoteAttributeMapping>> fieldDefinition;
    @JsonProperty
    private String remoteAttribute;
    @JsonProperty
    private IDictionaryElementConfiguration remoteAttributeDatatypeStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationRemoteAttributeMapping>> getFieldDefinition() {
        return EdoIntegrationRemoteAttributeMapping.fieldDefinition;
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
    
    protected EdoIntegrationRemoteAttributeMapping(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public final String getRemoteAttribute() {
        return this.remoteAttribute;
    }
    
    @Override
    public final void setRemoteAttribute(final String value) {
        this.remoteAttribute = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getRemoteAttributeDatatypeStorage() {
        return this.remoteAttributeDatatypeStorage;
    }
    
    @Override
    public void setRemoteAttributeDatatypeStorage(final IDictionaryElementConfiguration value) {
        this.remoteAttributeDatatypeStorage = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_remote_attribute", "remote_attribute", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationRemoteAttributeMapping::getRemoteAttribute, (a, b) -> a.setRemoteAttribute((b == null) ? null : ((String)b))), new ConfigFieldBaseDescriptionExt("ssa_remote_attribute_datatype", "ssa_remote_attribute_datatype_name", "remote_attribute_datatype", ICstmConfigAdapter.FieldType.dictionary_value, (Function<T, Object>)IEdoIntegrationRemoteAttributeMapping::getRemoteAttributeDatatypeStorage, (a, b) -> a.setRemoteAttributeDatatypeStorage((IDictionaryElementConfiguration)b)));
    }
}
