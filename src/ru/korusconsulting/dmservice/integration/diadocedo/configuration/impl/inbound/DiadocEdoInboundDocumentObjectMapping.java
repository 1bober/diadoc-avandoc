// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentObjectMapping;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentObjectMapping extends CstmConfigAdapter implements IDiadocEdoInboundDocumentObjectMapping
{
    private static final long serialVersionUID = 1434541041263720641L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration containerSelectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration containerAttributeStorage;
    @JsonProperty
    private String createLinkQuery;
    @JsonProperty
    private boolean createIfNotExists;
    @JsonProperty
    private String createObjectQuery;
    @JsonProperty
    private String selectObjectQuery;
    @JsonProperty
    private IDictionaryElementConfiguration objectType;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> mapping;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> mappingIfNew;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> args;
    public static final String CONFIG_ID = "inbound_document_object_mapping";
    
    private static String getAttributeMappingConfigGroupId() {
        return String.join(".", DiadocEdoInboundDocumentObjectMapping.class.getName(), "attribute_mapping");
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>>();
        final ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping> attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("sida_subject_object_type", "ssa_subject_object_type_name", "subject_object_type", ICstmConfigAdapter.FieldType.cstm_type, (Function)IDiadocEdoInboundDocumentObjectMapping::getObjectType, (a, b) -> a.setObjectType((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_subject_object_type_manual", "subject_object_type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("ssa_container_select_type", "ssa_container_select_type_name", "select_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoInboundDocumentObjectMapping::getContainerSelectTypeStorage, (a, b) -> a.setContainerSelectTypeStorage((IDictionaryElementConfiguration)b)));
        final ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping> attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("sida_container_attribute", "ssa_container_attribute_name", "container_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentObjectMapping::getContainerAttributeStorage, (a, b) -> a.setContainerAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_container_attribute_manual", "container_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("ssa_container_link_create_query", "container_link_create_query", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentObjectMapping::getCreateLinkQuery, (a, b) -> a.setCreateLinkQuery(Objects.toString(b, null))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("ssa_object_select_query", "object_select_query", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentObjectMapping::getSelectObjectQuery, (a, b) -> a.setSelectObjectQuery(Objects.toString(b, null))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("sba_create_if_not_exists", "create_if_not_exists", ICstmConfigAdapter.FieldType.bool, (Function)IDiadocEdoInboundDocumentObjectMapping::isCreateIfNotExists, (a, b) -> a.setCreateIfNotExists(b != null && (boolean)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("ssa_create_query", "create_query", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentObjectMapping::getCreateObjectQuery, (a, b) -> a.setCreateObjectQuery(Objects.toString(b, null))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("cstm_map_attributes_editor", (String)null, "mapping", "mapping_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function)IDiadocEdoInboundDocumentObjectMapping::getMapping, (a, b) -> a.addMappingFromWrapper(b), IDiadocEdoInboundDocumentObjectMapping::clearMapping));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>)new ConfigFieldBaseDescriptionExt("cstm_map_attributes_if_new_editor", (String)null, "mapping", "mapping_if_new_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function)IDiadocEdoInboundDocumentObjectMapping::getMappingIfNew, (a, b) -> a.addMappingIfNewFromWrapper(b), IDiadocEdoInboundDocumentObjectMapping::clearMappingIfNew));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> getTypeFieldDefinition() {
        return DiadocEdoInboundDocumentObjectMapping.typeFieldDefinition;
    }
    
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentObjectMapping>> getFieldDefinition() {
        return DiadocEdoInboundDocumentObjectMapping.fieldDefinition;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = (T)super.marshalImpl(session, xmlTag, generator, (Supplier)configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)Stream.concat(getTypeFieldDefinition().stream(), getFieldDefinition().stream()).toList(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)Stream.concat(getTypeFieldDefinition().stream(), getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    public DiadocEdoInboundDocumentObjectMapping(final ICstmConfigAdapter parent) {
        super(parent);
        this.mapping = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.mappingIfNew = new ArrayList<IEdoIntegrationAttributeMapping>();
        this.args = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    public String getSelectObjectQuery() {
        return this.selectObjectQuery;
    }
    
    public void setSelectObjectQuery(final String value) {
        this.selectObjectQuery = value;
    }
    
    public IDictionaryElementConfiguration getContainerSelectTypeStorage() {
        return this.containerSelectTypeStorage;
    }
    
    public void setContainerSelectTypeStorage(final IDictionaryElementConfiguration value) {
        this.containerSelectTypeStorage = value;
    }
    
    public IDictionaryElementConfiguration getContainerAttributeStorage() {
        return this.containerAttributeStorage;
    }
    
    public void setContainerAttributeStorage(final IDictionaryElementConfiguration value) {
        this.containerAttributeStorage = value;
    }
    
    public String getCreateLinkQuery() {
        return this.createLinkQuery;
    }
    
    public void setCreateLinkQuery(final String value) {
        this.createLinkQuery = value;
    }
    
    public boolean isCreateIfNotExists() {
        return this.createIfNotExists;
    }
    
    public void setCreateIfNotExists(final boolean value) {
        this.createIfNotExists = value;
    }
    
    public String getCreateObjectQuery() {
        return this.createObjectQuery;
    }
    
    public void setCreateObjectQuery(final String value) {
        this.createObjectQuery = value;
    }
    
    public IDictionaryElementConfiguration getObjectType() {
        return this.objectType;
    }
    
    public void setObjectType(final IDictionaryElementConfiguration value) {
        this.objectType = value;
    }
    
    public List<IEdoIntegrationAttributeMapping> getMapping() {
        return this.mapping;
    }
    
    public void clearMapping() {
        this.getMapping().clear();
    }
    
    public void addMapping(final IEdoIntegrationAttributeMapping value) {
        this.getMapping().add(value);
    }
    
    public List<IEdoIntegrationAttributeMapping> getMappingIfNew() {
        return this.mappingIfNew;
    }
    
    public void clearMappingIfNew() {
        this.getMappingIfNew().clear();
    }
    
    public void addMappingIfNew(final IEdoIntegrationAttributeMapping value) {
        this.getMappingIfNew().add(value);
    }
    
    public void addMappingIfNewFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addMappingIfNew(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addMappingIfNew(mappingRule);
            }
        }
    }
    
    public void addMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAttributeMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addMapping(mappingRule);
            }
        }
    }
    
    public String getContainerAttribute() {
        return (this.getContainerAttributeStorage() != null) ? this.getContainerAttributeStorage().getValue() : null;
    }
    
    protected IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal AttributeMapping component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationAttributeMapping config = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getAttributeMappingConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal AttributeMapping %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationAttributeMapping config = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getAttributeMappingConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    public String getLinkType() {
        return (this.getContainerSelectTypeStorage() != null) ? this.getContainerSelectTypeStorage().getValue() : null;
    }
    
    public IDiadocEdoInboundDocumentMappingProcedure.SourceType getSourceType() {
        this.getSourceType();
        return IDiadocEdoInboundDocumentMappingProcedure.SourceType.document;
    }
    
    static {
        registerChildConfigTagMapping(getAttributeMappingConfigGroupId(), "cstm_integration_diadoc_edo_inbound_document_attribute_to_attribute_mapping_title", "inbound_document_attribute_mapping", (Class)DiadocEdoInboundDocumentAttributeMapping.class);
        registerChildConfigTagMapping(getAttributeMappingConfigGroupId(), "cstm_integration_diadoc_edo_inbound_document_attribute_to_attribute_mapping", "inbound_document_attribute_mapping", (Class)DiadocEdoInboundDocumentAttributeMapping.class);
        registerChildConfigTagMapping(getAttributeMappingConfigGroupId(), "cstm_integration_diadoc_edo_inbound_fixed_value_to_attribute_mapping_title", String.join(".", "fixed_value_mapping", "remote_status"), (Class)EdoIntegrationAttributeMappingByFixedValue.class);
        registerChildConfigTagMapping(getAttributeMappingConfigGroupId(), "cstm_integration_diadoc_edo_inbound_fixed_value_to_attribute_mapping", String.join(".", "fixed_value_mapping", "local_error"), (Class)EdoIntegrationAttributeMappingByFixedValue.class);
        registerChildConfigTagMapping(getAttributeMappingConfigGroupId(), "cstm_integration_diadoc_edo_inbound_env_parameter_to_attribute_mapping", String.join(".", "inbound_document_env_parameter", "remote_error"), (Class)DiadocEdoInboundDocumentEnvironmentPropertyValue.class);
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
}
