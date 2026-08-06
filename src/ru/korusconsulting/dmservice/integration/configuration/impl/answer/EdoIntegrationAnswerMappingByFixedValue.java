// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.answer;

import ru.korusconsulting.service.integration.configuration.IAttributeDescriptionElementConfiguration;
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
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByFixedValue;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationAnswerMappingByFixedValue extends CstmConfigAdapter implements IEdoIntegrationAnswerMappingByFixedValue
{
    private static final long serialVersionUID = 5470369697319542232L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>> fieldDefinition;
    @JsonProperty
    private String value;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    public static final String CONFIG_ID = "mapping_by_fixed";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>("ssa_value", "value", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAnswerMappingByFixedValue, Object>)IEdoIntegrationAnswerMappingByFixedValue::getValue, (a, b) -> a.setValue(Objects.toString(b, null))));
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationAnswerMappingByFixedValue, Object>)IEdoIntegrationAnswerMappingByFixedValue::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAnswerMappingByFixedValue>> getFieldDefinition() {
        return EdoIntegrationAnswerMappingByFixedValue.fieldDefinition;
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
    
    public EdoIntegrationAnswerMappingByFixedValue(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String answerFiled) {
        this.value = answerFiled;
    }
    
    @Override
    public IDictionaryElementConfiguration getAttributeStorage() {
        return this.attributeStorage;
    }
    
    @Override
    public void setAttributeStorage(final IDictionaryElementConfiguration attribute) {
        this.attributeStorage = attribute;
    }
    
    @Override
    public String getAttribute() {
        return (this.getAttributeStorage() != null) ? this.getAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getAttributeDataType() {
        final IDictionaryElementConfiguration attributeStorage = this.getAttributeStorage();
        String attributeDataType;
        if (attributeStorage instanceof final IAttributeDescriptionElementConfiguration aS) {
            attributeDataType = aS.getAttributeDataType();
        }
        else {
            attributeDataType = null;
        }
        return attributeDataType;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
