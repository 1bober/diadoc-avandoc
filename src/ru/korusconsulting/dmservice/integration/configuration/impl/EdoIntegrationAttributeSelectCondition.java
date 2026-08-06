// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import org.apache.commons.lang.StringUtils;
import java.util.function.BiFunction;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeSelectCondition;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationAttributeSelectCondition extends CstmConfigAdapter implements IEdoIntegrationAttributeSelectCondition
{
    private static final long serialVersionUID = 2529645022499534868L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration concatinationTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration attributeManualDatatypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration conditionOperatorStorage;
    @JsonProperty
    private String value;
    public static final String CONFIG_ID = "attribute_select_condition";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("sida_concatination_type", "ssa_concatination_type_name", "concatination_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationAttributeSelectCondition, Object>)IEdoIntegrationAttributeSelectCondition::getConcatinationTypeStorage, (a, b) -> a.setConcatinationTypeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("sida_condition_operator", "ssa_condition_operator_name", "condition_operator", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationAttributeSelectCondition, Object>)IEdoIntegrationAttributeSelectCondition::getConditionOperatorStorage, (a, b) -> a.setConditionOperatorStorage((IDictionaryElementConfiguration)b), null));
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationAttributeSelectCondition, Object>)IEdoIntegrationAttributeSelectCondition::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("ssa_attribute_manual_datatype", "ssa_attribute_manual_datatype_name", "attribute_manual_datatype", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationAttributeSelectCondition, Object>)IEdoIntegrationAttributeSelectCondition::getAttributeManualDatatypeStorage, (a, b) -> a.setAttributeManualDatatypeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>("ssa_value", "value", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationAttributeSelectCondition, Object>)IEdoIntegrationAttributeSelectCondition::getValue, (a, b) -> a.setValue(Objects.toString(b, ""))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeSelectCondition>> getFieldDefinition() {
        return EdoIntegrationAttributeSelectCondition.fieldDefinition;
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
    
    public EdoIntegrationAttributeSelectCondition(final ICstmConfigAdapter parent) {
        super(parent);
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
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getConcatinationTypeStorage() {
        return this.concatinationTypeStorage;
    }
    
    @Override
    public void setConcatinationTypeStorage(final IDictionaryElementConfiguration concatinationType) {
        this.concatinationTypeStorage = concatinationType;
    }
    
    @Override
    public IDictionaryElementConfiguration getConditionOperatorStorage() {
        return this.conditionOperatorStorage;
    }
    
    @Override
    public void setConditionOperatorStorage(final IDictionaryElementConfiguration conditionOperator) {
        this.conditionOperatorStorage = conditionOperator;
    }
    
    @Override
    public String getConcatinationType() {
        return (this.getConcatinationTypeStorage() != null) ? this.getConcatinationTypeStorage().getValue() : null;
    }
    
    @Override
    public String getConditionOperator() {
        return (this.getConditionOperatorStorage() != null) ? this.getConditionOperatorStorage().getValue() : null;
    }
    
    @Override
    public IDictionaryElementConfiguration getAttributeManualDatatypeStorage() {
        return this.attributeManualDatatypeStorage;
    }
    
    @Override
    public String getAttributeManualDatatype() {
        return (this.getAttributeManualDatatypeStorage() != null) ? this.getAttributeManualDatatypeStorage().getValue() : null;
    }
    
    @Override
    public void setAttributeManualDatatypeStorage(final IDictionaryElementConfiguration attributeManualDataType) {
        this.attributeManualDatatypeStorage = attributeManualDataType;
    }
    
    @Override
    public Boolean check(final DMSShardsClientSession session, final BiFunction<AttributeValueSourceTypes, String, String> documentAttributeValuesGetter, final Boolean previousComplexCheckResult, final SessionContext ctx) {
        final String concatType = this.getConcatinationType();
        final String conditionType = this.getConditionOperator();
        final String attributeName = this.getAttribute();
        if (StringUtils.isNotBlank(attributeName)) {
            final String attributeValue = this.getValue();
            final String documentAttrValue = StringUtils.trim((String)documentAttributeValuesGetter.apply(AttributeValueSourceTypes.SUBJECT, attributeName));
            boolean inverseCondition = false;
            boolean listCondition = false;
            boolean emptyCondition = false;
            if (StringUtils.isNotBlank(conditionType)) {
                if (StringUtils.equals(conditionType, "notequal")) {
                    inverseCondition = true;
                }
                else if (StringUtils.equals(conditionType, "listed")) {
                    listCondition = true;
                }
                else if (StringUtils.equals(conditionType, "notlisted")) {
                    listCondition = true;
                    inverseCondition = true;
                }
                else if (StringUtils.equals(conditionType, "empty")) {
                    emptyCondition = true;
                }
                else if (StringUtils.equals(conditionType, "notempty")) {
                    inverseCondition = true;
                    emptyCondition = true;
                }
            }
            boolean lResult = false;
            final String attributeNameF = attributeName;
            if (emptyCondition) {
                lResult = StringUtils.isBlank(documentAttrValue);
                final boolean inverseConditionF = inverseCondition;
                this.getLogger().trace(() -> String.format("Check is blank attribute %s for document %s. Result is %b. Use %s result", attributeNameF, documentAttributeValuesGetter.apply(AttributeValueSourceTypes.SUBJECT, "r_object_id"), StringUtils.isBlank(documentAttrValue), inverseConditionF ? "inverse" : "direct"));
            }
            else {
                String[] values;
                if (listCondition) {
                    values = StringUtils.split(attributeValue, ";");
                }
                else {
                    values = new String[] { attributeValue };
                }
                for (final String s : values) {
                    lResult = StringUtils.equals(documentAttrValue, StringUtils.trim(s));
                    final boolean inverseConditionF2 = inverseCondition;
                    this.getLogger().trace(() -> String.format("Check is attribute %s value (is [%s]) equals [%s] for document %s. Result is %b. Use %s result", attributeNameF, documentAttrValue, StringUtils.trim(s), documentAttributeValuesGetter.apply(AttributeValueSourceTypes.SUBJECT, "r_object_id"), StringUtils.equals(documentAttrValue, StringUtils.trim(s)), inverseConditionF2 ? "inverse" : "direct"));
                    if (!inverseCondition && lResult) {
                        break;
                    }
                }
            }
            if (inverseCondition) {
                lResult = !lResult;
            }
            Boolean complexCheckResult;
            if (previousComplexCheckResult == null) {
                complexCheckResult = lResult;
            }
            else if (StringUtils.equals(concatType, "or")) {
                complexCheckResult = (previousComplexCheckResult || lResult);
            }
            else if (StringUtils.equals(concatType, "or not")) {
                complexCheckResult = (previousComplexCheckResult || !lResult);
            }
            else if (StringUtils.equals(concatType, "and not")) {
                complexCheckResult = (previousComplexCheckResult && !lResult);
            }
            else {
                complexCheckResult = (previousComplexCheckResult && lResult);
            }
            return complexCheckResult;
        }
        return true;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
