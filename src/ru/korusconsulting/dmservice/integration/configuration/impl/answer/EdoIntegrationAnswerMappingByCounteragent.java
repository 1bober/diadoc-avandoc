// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.answer;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationAnswerMappingByCounteragent extends CstmConfigAdapter implements IEdoIntegrationAnswerMappingByCounteragent
{
    private static final long serialVersionUID = -3225185529018701184L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> fieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> typeFieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration objectTypeStorage;
    @JsonProperty
    private List<IEdoIntegrationAttributeMapping> mapping;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>("sida_subject_object_type", "ssa_subject_object_type_name", "subject_object_type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationAnswerMappingByCounteragent, Object>)IEdoIntegrationAnswerMappingByCounteragent::getObjectTypeStorage, (a, b) -> a.setObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>("ssa_subject_object_type_manual", "subject_object_type_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>("cstm_map_attributes_editor", null, "element", "mapping_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationAnswerMappingByCounteragent, Object>)IEdoIntegrationAnswerMappingByCounteragent::getAnswerMapping, (a, b) -> a.addAnswerMappingFromWrapper(b), IEdoIntegrationAnswerMappingByCounteragent::clearAnswerMapping));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> getFieldDefinition() {
        return EdoIntegrationAnswerMappingByCounteragent.fieldDefinition;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByCounteragent>> getTypeFieldDefinition() {
        return EdoIntegrationAnswerMappingByCounteragent.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationAnswerMappingByCounteragent>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationAnswerMappingByCounteragent>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationAnswerMappingByCounteragent(final ICstmConfigAdapter parent) {
        super(parent);
        this.mapping = new ArrayList<IEdoIntegrationAttributeMapping>();
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
    public List<IEdoIntegrationAttributeMapping> getAnswerMapping() {
        return this.mapping;
    }
    
    protected abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    protected abstract IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public String getObjectType() {
        return (this.getObjectTypeStorage() != null) ? this.getObjectTypeStorage().getValue() : null;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
        typeFieldDefinition = initTypeFieldDefinition();
    }
}
