// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentObjectSelectRule;

public abstract class EdoIntegrationCounteragentSelectObjectRule extends EdoIntegrationSelectObjectRule implements IEdoIntegrationCounteragentObjectSelectRule
{
    private static final long serialVersionUID = -7581437508123222473L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration stateAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration innAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration kppAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration nameAttributeStorage;
    @JsonProperty
    private boolean useDictionaryOrganizationDetails;
    public static final String CONFIG_ID = "counteragent_object_rule";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>>();
        ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("sida_state_attribute", "ssa_state_attribute_name", "state_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentObjectSelectRule, Object>)IEdoIntegrationCounteragentObjectSelectRule::getStateAttributeStorage, (a, b) -> a.setStateAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("ssa_state_attribute_manual", "state_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("sida_inn_attribute", "ssa_inn_attribute_name", "inn_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentObjectSelectRule, Object>)IEdoIntegrationCounteragentObjectSelectRule::getInnAttributeStorage, (a, b) -> a.setInnAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("ssa_inn_attribute_manual", "inn_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("sida_kpp_attribute", "ssa_kpp_attribute_name", "kpp_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentObjectSelectRule, Object>)IEdoIntegrationCounteragentObjectSelectRule::getKppAttributeStorage, (a, b) -> a.setKppAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("ssa_kpp_attribute_manual", "kpp_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("sida_name_attribute", "ssa_name_attribute_name", "name_attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentObjectSelectRule, Object>)IEdoIntegrationCounteragentObjectSelectRule::getNameAttributeStorage, (a, b) -> a.setNameAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("ssa_name_attribute_manual", "name_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>("sba_use_dictionary_organization_details", "use_dictionary_organization_details", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationCounteragentObjectSelectRule, Object>)IEdoIntegrationCounteragentObjectSelectRule::useDictionaryOrganizationDetails, (a, b) -> a.setUseDictionaryOrganizationDetails(b != null && (boolean)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentObjectSelectRule>> getFieldDefinition() {
        return EdoIntegrationCounteragentSelectObjectRule.fieldDefinition;
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
    
    protected EdoIntegrationCounteragentSelectObjectRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IDictionaryElementConfiguration getStateAttributeStorage() {
        return this.stateAttributeStorage;
    }
    
    @Override
    public void setStateAttributeStorage(final IDictionaryElementConfiguration value) {
        this.stateAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getInnAttributeStorage() {
        return this.innAttributeStorage;
    }
    
    @Override
    public void setInnAttributeStorage(final IDictionaryElementConfiguration value) {
        this.innAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getKppAttributeStorage() {
        return this.kppAttributeStorage;
    }
    
    @Override
    public void setKppAttributeStorage(final IDictionaryElementConfiguration value) {
        this.kppAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getNameAttributeStorage() {
        return this.nameAttributeStorage;
    }
    
    @Override
    public void setNameAttributeStorage(final IDictionaryElementConfiguration value) {
        this.nameAttributeStorage = value;
    }
    
    @Override
    public String getStateAttribute() {
        return (this.getStateAttributeStorage() != null) ? this.getStateAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getInnAttribute() {
        return (this.getInnAttributeStorage() != null) ? this.getInnAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getKppAttribute() {
        return (this.getKppAttributeStorage() != null) ? this.getKppAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getNameAttribute() {
        return (this.getNameAttributeStorage() != null) ? this.getNameAttributeStorage().getValue() : null;
    }
    
    @Override
    public boolean useDictionaryOrganizationDetails() {
        return this.useDictionaryOrganizationDetails;
    }
    
    @Override
    public void setUseDictionaryOrganizationDetails(final boolean value) {
        this.useDictionaryOrganizationDetails = value;
    }
    
    @Override
    public String getObjectType() {
        return this.getObjectType();
    }
    
    @Override
    public boolean validate() {
        if (StringUtils.isBlank(this.getStateAttribute())) {
            throw new ServiceException("For counteragent select rule send status attribute must be defined");
        }
        return true;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
