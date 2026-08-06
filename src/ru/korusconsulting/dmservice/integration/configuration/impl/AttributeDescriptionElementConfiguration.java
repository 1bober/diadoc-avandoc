// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IAttributeDescriptionElementConfiguration;

public class AttributeDescriptionElementConfiguration extends DictionaryElementConfiguration implements IAttributeDescriptionElementConfiguration
{
    private static final long serialVersionUID = 603685575035545359L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IAttributeDescriptionElementConfiguration>> fieldDefinition;
    @JsonProperty
    private String objectTypeCode;
    @JsonProperty
    private String attributeDataType;
    @JsonProperty
    private String objectTypeId;
    @JsonProperty
    private boolean isRepeating;
    @JsonProperty
    private boolean cstmLoaded;
    public static final String ATTR_CONFIG_TYPE = "attribute";
    
    private static List<ConfigFieldBaseDescriptionExt<IAttributeDescriptionElementConfiguration>> getFieldDefinition() {
        return AttributeDescriptionElementConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
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
    
    public AttributeDescriptionElementConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public boolean isCstmLoaded() {
        return this.cstmLoaded;
    }
    
    @Override
    public void setCstmLoaded(final boolean value) {
        this.cstmLoaded = value;
    }
    
    @Override
    public String getObjectTypeCode() {
        return this.objectTypeCode;
    }
    
    @Override
    public void setObjectTypeCode(final String value) {
        this.objectTypeCode = value;
    }
    
    @Override
    public String getAttributeDataType() {
        return this.attributeDataType;
    }
    
    @Override
    public void setAttributeDataType(final String value) {
        this.attributeDataType = value;
    }
    
    @Override
    public String getObjectTypeId() {
        return this.objectTypeId;
    }
    
    @Override
    public void setObjectTypeId(final String value) {
        this.objectTypeId = value;
    }
    
    @Override
    public boolean isRepeating() {
        return this.isRepeating;
    }
    
    @Override
    public void setRepeating(final boolean value) {
        this.isRepeating = value;
    }
    
    @Override
    public String getConfigType() {
        return "attribute";
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("cstm_loaded", "cstm_loaded", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)IAttributeDescriptionElementConfiguration::isCstmLoaded, (a, b) -> a.setCstmLoaded(b != null && (boolean)b)), new ConfigFieldBaseDescriptionExt("repeating", "repeating", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)IAttributeDescriptionElementConfiguration::isRepeating, (a, b) -> a.setRepeating(b != null && (boolean)b)), new ConfigFieldBaseDescriptionExt("attribute_data_type", "attribute_data_type", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IAttributeDescriptionElementConfiguration::getAttributeDataType, (a, b) -> a.setAttributeDataType(Objects.toString(b, ""))), new ConfigFieldBaseDescriptionExt("for_type_code", "for_type_code", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IAttributeDescriptionElementConfiguration::getObjectTypeCode, (a, b) -> a.setObjectTypeCode(Objects.toString(b, ""))), new ConfigFieldBaseDescriptionExt("for_type_id", "for_type_id", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IAttributeDescriptionElementConfiguration::getObjectTypeId, (a, b) -> a.setObjectTypeId(Objects.toString(b, ""))));
    }
}
