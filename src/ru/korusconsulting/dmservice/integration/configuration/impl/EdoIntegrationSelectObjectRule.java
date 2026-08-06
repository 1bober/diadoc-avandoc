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
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectObjectRule;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationSelectObjectRule extends CstmConfigAdapter implements IEdoIntegrationSelectObjectRule
{
    private static final long serialVersionUID = 4357704009164156252L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration containerSelectTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration containerSelectAttributeStorage;
    @JsonProperty
    private String containerSelectQuery;
    @JsonProperty
    private IDictionaryElementConfiguration objectTypeStorage;
    @JsonProperty
    private List<IEdoIntegrationDocumentSelectCondition> selectExpressions;
    @JsonProperty
    private IDictionaryElementConfiguration objectIdAttributeStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("sida_subject_object_type", "ssa_subject_object_type_name", "object_type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getObjectTypeStorage, (a, b) -> a.setObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("ssa_subject_object_type_manual", "object_type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("ssa_container_select_type", "ssa_container_select_type_name", "container_select_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getContainerSelectTypeStorage, (a, b) -> a.setContainerSelectTypeStorage((IDictionaryElementConfiguration)b)));
        ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("sida_container_attribute", "ssa_container_attribute_name", "container_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getContainerSelectAttributeStorage, (a, b) -> a.setContainerSelectAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("ssa_container_attribute_manual", "container_attribute", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("ssa_container_select_query", "select_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getContainerSelectQuery, (a, b) -> a.setContainerSelectQuery(Objects.toString(b, null))));
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("sida_object_id_attribute", "ssa_object_id_attribute_name", "object_id_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getObjectIdAttributeStorage, (a, b) -> a.setObjectIdAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("ssa_object_id_attribute_manual", "object_id_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>("cstm_select_expression_editor", null, "condition", "conditions", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationSelectObjectRule, Object>)IEdoIntegrationSelectObjectRule::getSelectConditions, (a, b) -> a.addSelectConditionFromWrapper(b), IEdoIntegrationSelectObjectRule::clearSelectConditions));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> getTypeFieldDefinition() {
        return EdoIntegrationSelectObjectRule.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationSelectObjectRule>> getFieldDefinition() {
        return EdoIntegrationSelectObjectRule.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationSelectObjectRule>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationSelectObjectRule>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationSelectObjectRule(final ICstmConfigAdapter parent) {
        super(parent);
        this.selectExpressions = new ArrayList<IEdoIntegrationDocumentSelectCondition>();
    }
    
    @Override
    public IDictionaryElementConfiguration getContainerSelectTypeStorage() {
        return this.containerSelectTypeStorage;
    }
    
    @Override
    public void setContainerSelectTypeStorage(final IDictionaryElementConfiguration containerSelectType) {
        this.containerSelectTypeStorage = containerSelectType;
    }
    
    @Override
    public IDictionaryElementConfiguration getContainerSelectAttributeStorage() {
        return this.containerSelectAttributeStorage;
    }
    
    @Override
    public void setContainerSelectAttributeStorage(final IDictionaryElementConfiguration containerAttribute) {
        this.containerSelectAttributeStorage = containerAttribute;
    }
    
    @Override
    public String getContainerSelectQuery() {
        return this.containerSelectQuery;
    }
    
    @Override
    public void setContainerSelectQuery(final String containerSelectQuery) {
        this.containerSelectQuery = containerSelectQuery;
    }
    
    @Override
    public IDictionaryElementConfiguration getObjectTypeStorage() {
        return this.objectTypeStorage;
    }
    
    @Override
    public void setObjectTypeStorage(final IDictionaryElementConfiguration objectType) {
        this.objectTypeStorage = objectType;
    }
    
    @Override
    public List<IEdoIntegrationDocumentSelectCondition> getSelectConditions() {
        return this.selectExpressions;
    }
    
    @Override
    public void clearSelectConditions() {
        this.getSelectConditions().clear();
    }
    
    @Override
    public void addSelectCondition(final IEdoIntegrationDocumentSelectCondition condition) {
        this.getSelectConditions().add(condition);
    }
    
    @Override
    public void addSelectConditionFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentSelectCondition condition = this.createSelectCondition(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (condition != null) {
                this.addSelectCondition(condition);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentSelectCondition condition = this.createSelectCondition(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (condition != null) {
                this.addSelectCondition(condition);
            }
        }
    }
    
    @Override
    public IDictionaryElementConfiguration getObjectIdAttributeStorage() {
        return this.objectIdAttributeStorage;
    }
    
    @Override
    public void setObjectIdAttributeStorage(final IDictionaryElementConfiguration objectIdAttribute) {
        this.objectIdAttributeStorage = objectIdAttribute;
    }
    
    @Override
    public String getObjectIdAttribute() {
        return (this.getObjectIdAttributeStorage() != null) ? this.getObjectIdAttributeStorage().getValue() : null;
    }
    
    public abstract IEdoIntegrationDocumentSelectCondition createSelectCondition(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentSelectCondition createSelectCondition(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public static String getRuleTypeXMLAttribute() {
        return "object";
    }
    
    @Override
    public String getContainerSelectAttribute() {
        return (this.getContainerSelectAttributeStorage() != null) ? this.getContainerSelectAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getContainerSelectType() {
        return (this.getContainerSelectTypeStorage() != null) ? this.getContainerSelectTypeStorage().getValue() : null;
    }
    
    @Override
    public String getObjectType() {
        return (this.getObjectTypeStorage() != null) ? this.getObjectTypeStorage().getValue() : null;
    }
    
    static {
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
}
