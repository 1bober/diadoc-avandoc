// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import java.util.function.BiFunction;
import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationQuerySelectCondition;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationQuerySelectCondition extends CstmConfigAdapter implements IEdoIntegrationQuerySelectCondition
{
    private static final long serialVersionUID = -4022196811084259296L;
    private Logger logger;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationQuerySelectCondition>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration concatinationTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration conditionOperatorStorage;
    @JsonProperty
    private String value;
    @JsonProperty
    private String query;
    public static final String CONFIG_ID = "query_select_condition";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationQuerySelectCondition>> getFieldDefinition() {
        return EdoIntegrationQuerySelectCondition.fieldDefinition;
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
    
    public EdoIntegrationQuerySelectCondition(final ICstmConfigAdapter parent) {
        super(parent);
        this.logger = LogManager.getLogger();
    }
    
    @Override
    public String getQuery() {
        return this.query;
    }
    
    @Override
    public void setQuery(final String value) {
        this.query = value;
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
    public void setConcatinationTypeStorage(final IDictionaryElementConfiguration value) {
        this.concatinationTypeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getConditionOperatorStorage() {
        return this.conditionOperatorStorage;
    }
    
    @Override
    public void setConditionOperatorStorage(final IDictionaryElementConfiguration value) {
        this.conditionOperatorStorage = value;
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
    public Boolean check(final DMSShardsClientSession session, final BiFunction<AttributeValueSourceTypes, String, String> valuesGetter, final Boolean previousComplexCheckResult, final SessionContext ctx) {
        final String concatType = this.getConcatinationType();
        final String conditionType = this.getConditionOperator();
        final String checkQuery = this.getQuery();
        final String attributeValue = this.getValue();
        final String documentAttrValue = StringUtils.trim((String)valuesGetter.apply(AttributeValueSourceTypes.SUBJECT, checkQuery));
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
        if (emptyCondition) {
            lResult = StringUtils.isBlank(documentAttrValue);
            final boolean inverseConditionF = inverseCondition;
            this.logger.trace(() -> String.format("Check is blank result of query %s. Result is %b. Use %s result", checkQuery, StringUtils.isBlank(documentAttrValue), inverseConditionF ? "inverse" : "direct"));
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
                this.logger.trace(() -> String.format("Check is result of query %s value (is [%s]) equals [%s]. Result is %b. Use %s result", checkQuery, documentAttrValue, StringUtils.trim(s), StringUtils.equals(documentAttrValue, StringUtils.trim(s)), inverseConditionF2 ? "inverse" : "direct"));
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
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sida_concatination_type", "ssa_concatination_type_name", "concatination_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<T, Object>)IEdoIntegrationQuerySelectCondition::getConcatinationTypeStorage, (a, b) -> a.setConcatinationTypeStorage((IDictionaryElementConfiguration)b)), new ConfigFieldBaseDescriptionExt("sida_condition_operator", "ssa_condition_operator_name", "condition_operator", ICstmConfigAdapter.FieldType.dictionary_value, (Function<T, Object>)IEdoIntegrationQuerySelectCondition::getConditionOperatorStorage, (a, b) -> a.setConditionOperatorStorage((IDictionaryElementConfiguration)b)), new ConfigFieldBaseDescriptionExt("ssa_query", "query", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationQuerySelectCondition::getQuery, (a, b) -> a.setQuery(Objects.toString(b, ""))), new ConfigFieldBaseDescriptionExt("ssa_value", "value", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationQuerySelectCondition::getValue, (a, b) -> a.setValue(Objects.toString(b, ""))));
    }
}
