// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

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
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoInboundAttributeMappingByAttribute;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationInboundAttributeMappingByAttribute;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundAttributeMappingByAttribute extends EdoIntegrationInboundAttributeMappingByAttribute implements IDiadocEdoInboundAttributeMappingByAttribute
{
    private static final long serialVersionUID = 2979654531073408804L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>> fieldDefinition;
    @JsonProperty
    private String remoteAttribute;
    @JsonProperty
    private IDictionaryElementConfiguration remoteAttributeDatatypeStorage;
    public static final String CONFIG_ID = "attribute_mapping";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>)new ConfigFieldBaseDescriptionExt("ssa_remote_attribute", "remote_attribute", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundAttributeMappingByAttribute::getRemoteAttribute, (a, b) -> a.setRemoteAttribute(Objects.toString(b, ""))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>)new ConfigFieldBaseDescriptionExt("ssa_remote_attribute_datatype", "ssa_remote_attribute_datatype_name", "remote_attribute_datatype", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoInboundAttributeMappingByAttribute::getRemoteAttributeDatatypeStorage, (a, b) -> a.setRemoteAttributeDatatypeStorage((IDictionaryElementConfiguration)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundAttributeMappingByAttribute>> getFieldDefinition() {
        return DiadocEdoInboundAttributeMappingByAttribute.fieldDefinition;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = (T)super.marshalImpl(session, xmlTag, generator, (Supplier)configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)getFieldDefinition(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), element, ctx);
    }
    
    public DiadocEdoInboundAttributeMappingByAttribute(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public final String getRemoteAttribute() {
        return this.remoteAttribute;
    }
    
    public final void setRemoteAttribute(final String value) {
        this.remoteAttribute = value;
    }
    
    public final IDictionaryElementConfiguration getRemoteAttributeDatatypeStorage() {
        return this.remoteAttributeDatatypeStorage;
    }
    
    public final void setRemoteAttributeDatatypeStorage(final IDictionaryElementConfiguration value) {
        this.remoteAttributeDatatypeStorage = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
