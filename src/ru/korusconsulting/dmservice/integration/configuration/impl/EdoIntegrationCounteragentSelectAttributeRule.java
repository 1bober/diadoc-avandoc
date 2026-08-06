// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentSelectAttributeRule;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationCounteragentSelectAttributeRule extends CstmConfigAdapter implements IEdoIntegrationCounteragentSelectAttributeRule
{
    private static final long serialVersionUID = -2113108690157413950L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectAttributeRule>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration objectIdAttributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration sendStatusAttributeStorage;
    public static final String CONFIG_ID = "counteragent_attribute_rule";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectAttributeRule>> getFieldDefinition() {
        return EdoIntegrationCounteragentSelectAttributeRule.fieldDefinition;
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
    
    public EdoIntegrationCounteragentSelectAttributeRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IDictionaryElementConfiguration getObjectIdAttributeStorage() {
        return this.objectIdAttributeStorage;
    }
    
    @Override
    public void setObjectIdAttributeStorage(final IDictionaryElementConfiguration value) {
        this.objectIdAttributeStorage = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getSendStatusAttributeStorage() {
        return this.sendStatusAttributeStorage;
    }
    
    @Override
    public void setSendStatusAttributeStorage(final IDictionaryElementConfiguration value) {
        this.sendStatusAttributeStorage = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sida_object_id_attribute", "ssa_object_id_attribute_name", "object_id_attribute", ICstmConfigAdapter.FieldType.dictionary_value, a -> a.getObjectIdAttributeStorage(), (a, b) -> a.setObjectIdAttributeStorage((IDictionaryElementConfiguration)b)), new ConfigFieldBaseDescriptionExt("sida_send_status_attribute", "ssa_send_status_attribute_name", "send_status_attribute", ICstmConfigAdapter.FieldType.dictionary_value, a -> a.getSendStatusAttributeStorage(), (a, b) -> a.setSendStatusAttributeStorage((IDictionaryElementConfiguration)b)));
    }
}
