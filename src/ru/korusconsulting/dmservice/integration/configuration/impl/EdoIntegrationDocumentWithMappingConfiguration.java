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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentWithMappingConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationDocumentWithMappingConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationDocumentWithMappingConfiguration
{
    private static final long serialVersionUID = 979049341871546682L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> mapping;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>("cstm_map_attributes_editor", null, "mapping_rule", "mapping", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationDocumentWithMappingConfiguration, Object>)IEdoIntegrationDocumentWithMappingConfiguration::getMapping, (a, b) -> a.addMappingFromWrapper(b), IEdoIntegrationDocumentWithMappingConfiguration::clearMapping));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentWithMappingConfiguration>> getFieldDefinition() {
        return EdoIntegrationDocumentWithMappingConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationDocumentWithMappingConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.mapping = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getMapping() {
        return this.mapping;
    }
    
    @Override
    public void clearMapping() {
        this.getMapping().clear();
    }
    
    @Override
    public void addMapping(final IEdoIntegrationAttributeMapping attributeMapping) {
        this.getMapping().add(attributeMapping);
    }
    
    @Override
    public void addMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createDocumentMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createDocumentMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addMapping(mappingRule);
            }
        }
    }
    
    public abstract IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
