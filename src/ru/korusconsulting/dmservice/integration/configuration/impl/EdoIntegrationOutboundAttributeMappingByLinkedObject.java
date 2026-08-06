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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentWithMappingConfiguration;
import java.util.Objects;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMappingByLinkedObject;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundAttributeMappingByLinkedObject extends EdoIntegrationDocumentWithMappingConfiguration implements IEdoIntegrationOutboundAttributeMappingByLinkedObject, IEdoIntegrationOutboundMappingByLinkedObject
{
    private static final long serialVersionUID = -881913439922547214L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> fieldDefinition;
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
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> answerMapping;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("sida_subject_object_type", "ssa_subject_object_type_name", "object_type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getObjectTypeStorage, (a, b) -> a.setObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("ssa_object_type_manual", "object_type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("ssa_container_select_type", "ssa_container_select_type_name", "select_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getContainerSelectTypeStorage, (a, b) -> a.setContainerSelectTypeStorage((IDictionaryElementConfiguration)b)));
        ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("sida_container_attribute", "ssa_container_attribute_name", "container_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getContainerSelectAttributeStorage, (a, b) -> a.setContainerSelectAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("ssa_container_attribute_manual", "container_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("ssa_container_select_query", "container_select_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getContainerSelectQuery, (a, b) -> a.setContainerSelectQuery(Objects.toString(b, null))));
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("sida_object_id_attribute", "ssa_object_id_attribute_name", "object_id_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getObjectIdAttributeStorage, (a, b) -> a.setObjectIdAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("ssa_object_id_attribute_manual", "object_id_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("cstm_map_attributes_editor", null, "mapping_rule", "mapping", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationDocumentWithMappingConfiguration::getMapping, (a, b) -> a.addMappingFromWrapper(b), IEdoIntegrationDocumentWithMappingConfiguration::clearMapping));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>("cstm_select_expression_editor", null, "condition", "conditions", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationOutboundAttributeMappingByLinkedObject, Object>)IEdoIntegrationOutboundAttributeMappingByLinkedObject::getSelectConditions, (a, b) -> a.addSelectConditionFromWrapper(b), IEdoIntegrationOutboundAttributeMappingByLinkedObject::clearSelectConditions));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> getTypeFieldDefinition() {
        return EdoIntegrationOutboundAttributeMappingByLinkedObject.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByLinkedObject>> getFieldDefinition() {
        return EdoIntegrationOutboundAttributeMappingByLinkedObject.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundAttributeMappingByLinkedObject>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationOutboundAttributeMappingByLinkedObject>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationOutboundAttributeMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
        this.selectConditions = new ArrayList<IEdoIntegrationDocumentSelectCondition>();
        this.answerMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
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
    public String getObjectIdAttribute() {
        return (this.getObjectIdAttributeStorage() != null) ? this.getObjectIdAttributeStorage().getValue() : null;
    }
    
    @Override
    public void setObjectIdAttributeStorage(final IDictionaryElementConfiguration value) {
        this.objectIdAttributeStorage = value;
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getAnswerMapping() {
        return this.answerMapping;
    }
    
    @Override
    public void clearAnswerMapping() {
        this.getAnswerMapping().clear();
    }
    
    @Override
    public void addAnswerMapping(final IEdoIntegrationAttributeMapping value) {
        this.getAnswerMapping().add(value);
    }
    
    @Override
    public void addAnswerMappingFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAnswerMapping(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addAnswerMapping(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createAnswerMapping(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addAnswerMapping(mappingRule);
            }
        }
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
            final IEdoIntegrationDocumentSelectCondition mappingRule = this.createDocumentSelectCondition(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addSelectCondition(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentSelectCondition mappingRule = this.createDocumentSelectCondition(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addSelectCondition(mappingRule);
            }
        }
    }
    
    @Override
    public List<IEdoIntegrationDocumentSelectCondition> getSelectConditions() {
        return this.selectConditions;
    }
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getContainerSelectType() {
        return (this.getContainerSelectTypeStorage() == null) ? null : this.getContainerSelectTypeStorage().getValue();
    }
    
    @Override
    public String getObjectType() {
        return (this.getObjectTypeStorage() == null) ? null : this.getObjectTypeStorage().getValue();
    }
    
    @Override
    public String getContainerSelectAttribute() {
        return (this.getContainerSelectAttributeStorage() == null) ? null : this.getContainerSelectAttributeStorage().getValue();
    }
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
}
