// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectObjectRule;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttributeMappingByLinkedObject;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationInboundAttributeMappingByLinkedObject extends EdoIntegrationDocumentWithMappingConfiguration implements IEdoIntegrationInboundAttributeMappingByLinkedObject
{
    private static final long serialVersionUID = -2020542376953917248L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration containerSelectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration containerSelectAttributeStorage;
    @JsonProperty
    private String containerSelectQuery;
    @JsonProperty
    private IDictionaryElementConfiguration objectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration objectIdAttributeStorage;
    @JsonProperty
    private List<IEdoIntegrationDocumentSelectCondition> selectConditions;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("sida_subject_object_type", "ssa_subject_object_type_name", "subject_object_type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationInboundAttributeMappingByLinkedObject::getObjectTypeStorage, (a, b) -> a.setObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("ssa_subject_object_type_manual", "subject_object_type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("ssa_container_select_type", "ssa_container_select_type_name", "select_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationInboundAttributeMappingByLinkedObject::getContainerSelectTypeStorage, (a, b) -> a.setContainerSelectTypeStorage((IDictionaryElementConfiguration)b)));
        ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("sida_container_attribute", "ssa_container_attribute_name", "container_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationInboundAttributeMappingByLinkedObject::getContainerSelectAttributeStorage, (a, b) -> a.setContainerSelectAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("ssa_container_attribute_manual", "container_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("ssa_container_select_query", "container_select_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationSelectObjectRule::getContainerSelectQuery, (a, b) -> a.setContainerSelectQuery(Objects.toString(b, null))));
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("", "ssa_object_id_attribute_name", "object_id_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationSelectObjectRule::getObjectIdAttributeStorage, (a, b) -> a.setObjectIdAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("ssa_object_id_attribute_manual", "object_id_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>("cstm_select_expression_editor", null, "condition", "conditions", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationSelectObjectRule::getSelectConditions, (a, b) -> a.addSelectConditionFromWrapper(b), IEdoIntegrationInboundAttributeMappingByLinkedObject::clearSelectConditions));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> getTypeFieldDefinition() {
        return EdoIntegrationInboundAttributeMappingByLinkedObject.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttributeMappingByLinkedObject>> getFieldDefinition() {
        return EdoIntegrationInboundAttributeMappingByLinkedObject.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundAttributeMappingByLinkedObject>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundAttributeMappingByLinkedObject>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationInboundAttributeMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
        this.selectConditions = new ArrayList<IEdoIntegrationDocumentSelectCondition>();
    }
    
    @Override
    public IDictionaryElementConfiguration getContainerSelectTypeStorage() {
        return this.containerSelectTypeStorage;
    }
    
    @Override
    public void setContainerSelectTypeStorage(final IDictionaryElementConfiguration value) {
        this.containerSelectTypeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getContainerSelectAttributeStorage() {
        return this.containerSelectAttributeStorage;
    }
    
    @Override
    public void setContainerSelectAttributeStorage(final IDictionaryElementConfiguration value) {
        this.containerSelectAttributeStorage = value;
    }
    
    @Override
    public String getContainerSelectQuery() {
        return this.containerSelectQuery;
    }
    
    @Override
    public void setContainerSelectQuery(final String value) {
        this.containerSelectQuery = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getObjectTypeStorage() {
        return this.objectTypeStorage;
    }
    
    @Override
    public void setObjectTypeStorage(final IDictionaryElementConfiguration value) {
        this.objectTypeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getObjectIdAttributeStorage() {
        return this.objectIdAttributeStorage;
    }
    
    @Override
    public void setObjectIdAttributeStorage(final IDictionaryElementConfiguration value) {
        this.objectIdAttributeStorage = value;
    }
    
    @Override
    public String getObjectIdAttribute() {
        return (this.getObjectIdAttributeStorage() != null) ? this.getObjectIdAttributeStorage().getValue() : null;
    }
    
    @Override
    public void clearSelectConditions() {
        this.getSelectConditions().clear();
    }
    
    @Override
    public void addSelectCondition(final IEdoIntegrationDocumentSelectCondition value) {
        this.getSelectConditions().add(value);
    }
    
    @Override
    public void addSelectConditionFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentSelectCondition v = this.createDocumentSelectCondition(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (v != null) {
                this.addSelectCondition(v);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentSelectCondition v = this.createDocumentSelectCondition(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (v != null) {
                this.addSelectCondition(v);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationDocumentSelectCondition> getSelectConditions() {
        return this.selectConditions;
    }
    
    @Override
    public String getContainerSelectType() {
        return (this.getContainerSelectTypeStorage() != null) ? this.getContainerSelectTypeStorage().getValue() : null;
    }
    
    @Override
    public String getContainerSelectAttribute() {
        return (this.getContainerSelectAttributeStorage() != null) ? this.getContainerSelectAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getObjectType() {
        return (this.getObjectTypeStorage() != null) ? this.getObjectTypeStorage().getValue() : null;
    }
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
}
