// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.answer;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
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
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByLinkedObject;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationAnswerMappingByLinkedObject extends CstmConfigAdapter implements IEdoIntegrationAnswerMappingByLinkedObject
{
    private static final long serialVersionUID = -3003997912975860930L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration containerLinkTypeStorage;
    @JsonProperty
    private String containerSelectQuery;
    @JsonProperty
    private String containerCreateQuery;
    @JsonProperty
    private IDictionaryElementConfiguration objectTypeStorage;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> answerMapping;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("ssa_link_type", "ssa_link_type_name", "link_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationAnswerMappingByLinkedObject, Object>)IEdoIntegrationAnswerMappingByLinkedObject::getContainerLinkTypeStorage, (a, b) -> a.setContainerLinkTypeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("ssa_container_select_query", "container_select_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAnswerMappingByLinkedObject, Object>)IEdoIntegrationAnswerMappingByLinkedObject::getContainerSelectQuery, (a, b) -> a.setContainerSelectQuery(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("ssa_create_object_query", "create_object_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAnswerMappingByLinkedObject, Object>)IEdoIntegrationAnswerMappingByLinkedObject::getContainerCreateQuery, (a, b) -> a.setContainerCreateQuery(Objects.toString(b, null))));
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("sida_subject_object_type", "ssa_subject_object_type_name", "object_type", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationAnswerMappingByLinkedObject, Object>)IEdoIntegrationAnswerMappingByLinkedObject::getObjectTypeStorage, (a, b) -> a.setObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("ssa_subject_object_type_manual", "subject_object_type_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>("cstm_map_attributes_editor", null, "mapping_rule", "mapping", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationAnswerMappingByLinkedObject, Object>)IEdoIntegrationAnswerMappingByLinkedObject::getAnswerMapping, (a, b) -> a.getAnswerMappingFromWrapper(b), IEdoIntegrationAnswerMappingByLinkedObject::clearAnswerMapping));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByLinkedObject>> getFieldDefinition() {
        return EdoIntegrationAnswerMappingByLinkedObject.fieldDefinition;
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
    
    protected EdoIntegrationAnswerMappingByLinkedObject(final ICstmConfigAdapter parent) {
        super(parent);
        this.answerMapping = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    @Override
    public IDictionaryElementConfiguration getContainerLinkTypeStorage() {
        return this.containerLinkTypeStorage;
    }
    
    @Override
    public void setContainerLinkTypeStorage(final IDictionaryElementConfiguration value) {
        this.containerLinkTypeStorage = value;
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
    public String getContainerCreateQuery() {
        return this.containerCreateQuery;
    }
    
    @Override
    public void setContainerCreateQuery(final String value) {
        this.containerCreateQuery = value;
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
    public void clearAnswerMapping() {
        this.getAnswerMapping().clear();
    }
    
    @Override
    public void addAnswerMapping(final IEdoIntegrationAttributeMapping value) {
        this.getAnswerMapping().add(value);
    }
    
    @Override
    public List<IEdoIntegrationAttributeMapping> getAnswerMapping() {
        return this.answerMapping;
    }
    
    @Override
    public void getAnswerMappingFromWrapper(final Object value) {
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
    public String getContainerLinkType() {
        return (this.getContainerLinkTypeStorage() != null) ? this.getContainerLinkTypeStorage().getValue() : null;
    }
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
