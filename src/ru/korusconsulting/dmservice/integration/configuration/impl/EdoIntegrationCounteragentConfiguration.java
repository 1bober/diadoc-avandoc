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
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationCounteragentConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationCounteragentConfiguration
{
    private static final long serialVersionUID = 4442469337275533720L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> typeFieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration counteragentTypeAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration counteragentNameAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration counteragentInnAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration counteragentKppAttributeStorage;
    public static final String CONFIG_ID = "counteragent_type";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("sida_counteragent_type", "ssa_counteragent_type_name", "counteragent_type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationCounteragentConfiguration, Object>)IEdoIntegrationCounteragentConfiguration::getCounteragentTypeAttributeStorage, (a, b) -> a.setCounteragentTypeAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("ssa_counteragent_type_manual", "counteragent_type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>>();
        ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("sida_counteragent_inn_attr", "ssa_counteragent_inn_attr_name", "counteragent_inn_attr", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentConfiguration, Object>)IEdoIntegrationCounteragentConfiguration::getCounteragentInnAttributeStorage, (a, b) -> a.setCounteragentInnAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("ssa_counteragent_inn_attr_manual", "counteragent_inn_attr_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("sida_counteragent_name_attr", "ssa_counteragent_name_attr_name", "counteragent_name_attr", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentConfiguration, Object>)IEdoIntegrationCounteragentConfiguration::getCounteragentNameAttributeStorage, (a, b) -> a.setCounteragentNameAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("ssa_counteragent_name_attr_manual", "counteragent_name_attr_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("sida_counteragent_kpp_attr", "ssa_counteragent_kpp_attr_name", "counteragent_kpp_attr", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationCounteragentConfiguration, Object>)IEdoIntegrationCounteragentConfiguration::getCounteragentKppAttributeStorage, (a, b) -> a.setCounteragentKppAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>("ssa_counteragent_kpp_attr_manual", "counteragent_kpp_attr_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationCounteragentConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentConfiguration>> getFieldDefinition() {
        return EdoIntegrationCounteragentConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationCounteragentConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationCounteragentConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    public EdoIntegrationCounteragentConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IDictionaryElementConfiguration getCounteragentTypeAttributeStorage() {
        return this.counteragentTypeAttributeStorage;
    }
    
    @Override
    public void setCounteragentTypeAttributeStorage(final IDictionaryElementConfiguration value) {
        this.counteragentTypeAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getCounteragentNameAttributeStorage() {
        return this.counteragentNameAttributeStorage;
    }
    
    @Override
    public void setCounteragentNameAttributeStorage(final IDictionaryElementConfiguration value) {
        this.counteragentNameAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getCounteragentInnAttributeStorage() {
        return this.counteragentInnAttributeStorage;
    }
    
    @Override
    public void setCounteragentInnAttributeStorage(final IDictionaryElementConfiguration value) {
        this.counteragentInnAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getCounteragentKppAttributeStorage() {
        return this.counteragentKppAttributeStorage;
    }
    
    @Override
    public void setCounteragentKppAttributeStorage(final IDictionaryElementConfiguration value) {
        this.counteragentKppAttributeStorage = value;
    }
    
    @Override
    public String getCounteragentTypeAttribute() {
        return (this.getCounteragentTypeAttributeStorage() != null) ? this.getCounteragentTypeAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getCounteragentNameAttribute() {
        return (this.getCounteragentNameAttributeStorage() != null) ? this.getCounteragentNameAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getCounteragentInnAttribute() {
        return (this.getCounteragentInnAttributeStorage() != null) ? this.getCounteragentInnAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getCounteragentKppAttribute() {
        return (this.getCounteragentKppAttributeStorage() != null) ? this.getCounteragentKppAttributeStorage().getValue() : null;
    }
    
    static {
        typeFieldDefinition = initTypeFieldDefinition();
        fieldDefinition = initFieldDefinition();
    }
}
