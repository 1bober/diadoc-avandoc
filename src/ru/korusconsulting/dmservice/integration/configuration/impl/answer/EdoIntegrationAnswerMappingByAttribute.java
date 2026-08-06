// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.answer;

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
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByAttribute;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationAnswerMappingByAttribute extends CstmConfigAdapter implements IEdoIntegrationAnswerMappingByAttribute
{
    private static final long serialVersionUID = -4523226318172263655L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>> fieldDefinition;
    @JsonProperty
    private String answerFiled;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    public static final String CONFIG_ID = "mapping_by_attribute";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationAnswerMappingByAttribute, Object>)IEdoIntegrationAnswerMappingByAttribute::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>("ssa_inbound_field", "answer_field", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAnswerMappingByAttribute, Object>)IEdoIntegrationAnswerMappingByAttribute::getAnswerFiled, (a, b) -> a.setAnswerFiled(Objects.toString(b, null))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByAttribute>> getFieldDefinition() {
        return EdoIntegrationAnswerMappingByAttribute.fieldDefinition;
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
    
    public EdoIntegrationAnswerMappingByAttribute(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getAnswerFiled() {
        return this.answerFiled;
    }
    
    @Override
    public void setAnswerFiled(final String value) {
        this.answerFiled = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getAttributeStorage() {
        return this.attributeStorage;
    }
    
    @Override
    public void setAttributeStorage(final IDictionaryElementConfiguration value) {
        this.attributeStorage = value;
    }
    
    @Override
    public String getAttribute() {
        return (this.getAttributeStorage() != null) ? this.getAttributeStorage().getValue() : null;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
