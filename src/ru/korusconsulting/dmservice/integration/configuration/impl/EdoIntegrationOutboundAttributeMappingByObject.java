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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMappingByObject;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundAttributeMappingByObject extends EdoIntegrationSelectObjectRule implements IEdoIntegrationOutboundAttributeMappingByObject
{
    private static final long serialVersionUID = -8150252648826647974L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>> fieldDefinition;
    @JsonProperty
    private String remoteAttribute;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> attributeMapping;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>("ssa_remote_attribute", "remote_attribute", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByObject, Object>)IEdoIntegrationOutboundAttributeMappingByObject::getRemoteAttribute, (a, b) -> a.setRemoteAttribute(Objects.toString(b, ""))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>("cstm_attribute_mapping", null, "mapping_rule", "mapping", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundAttributeMappingByObject, Object>)IEdoIntegrationOutboundAttributeMappingByObject::getAttributeMapping, (a, b) -> a.addAttributeMappingFromWrapper(b), IEdoIntegrationOutboundAttributeMappingByObject::clearAttributeMapping));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByObject>> getFieldDefinition() {
        return EdoIntegrationOutboundAttributeMappingByObject.fieldDefinition;
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
    
    protected EdoIntegrationOutboundAttributeMappingByObject(final ICstmConfigAdapter parent) {
        super(parent);
        this.attributeMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public String getRemoteAttribute() {
        return this.remoteAttribute;
    }
    
    @Override
    public void setRemoteAttribute(final String remoteAttribute) {
        this.remoteAttribute = remoteAttribute;
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getAttributeMapping() {
        return this.attributeMapping;
    }
    
    @Override
    public void clearAttributeMapping() {
        this.getAttributeMapping().clear();
    }
    
    @Override
    public void addAttributeMapping(final IEdoIntegrationAttributeMapping value) {
        this.getAttributeMapping().add(value);
    }
    
    @Override
    public void addAttributeMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping v = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (v != null) {
                this.addAttributeMapping(v);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping v = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (v != null) {
                this.addAttributeMapping(v);
            }
        }
    }
    
    public abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
