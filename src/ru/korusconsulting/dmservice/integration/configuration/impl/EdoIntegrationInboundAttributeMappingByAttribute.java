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
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttributeMappingByAttribute;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationInboundAttributeMappingByAttribute extends CstmConfigAdapter implements IEdoIntegrationInboundAttributeMappingByAttribute
{
    private static final long serialVersionUID = 8695290501633151848L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationInboundAttributeMappingByAttribute, Object>)IEdoIntegrationInboundAttributeMappingByAttribute::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByAttribute>> getFieldDefinition() {
        return EdoIntegrationInboundAttributeMappingByAttribute.fieldDefinition;
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
    
    protected EdoIntegrationInboundAttributeMappingByAttribute(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public final IDictionaryElementConfiguration getAttributeStorage() {
        return this.attributeStorage;
    }
    
    @Override
    public final void setAttributeStorage(final IDictionaryElementConfiguration value) {
        this.attributeStorage = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
