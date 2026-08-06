// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

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
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMappingByAttribute;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationOutboundAttributeMappingByAttribute extends CstmConfigAdapter implements IEdoIntegrationOutboundAttributeMappingByAttribute
{
    private static final long serialVersionUID = -2608227238558321445L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>> fieldDefinition;
    @JsonProperty
    private boolean required;
    @JsonProperty
    private AttrValueSelectType selectType;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration objectTypeStorage;
    @JsonProperty
    private boolean isParentDocAttribute;
    @JsonProperty
    private String value;
    @JsonProperty
    private String query;
    @JsonProperty
    private String procedure;
    @JsonProperty
    private String attributeFormat;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_src_type", "select_type", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getSelectTypeStr, (a, b) -> a.setSelectType(Objects.toString(b, ""))));
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_remote_attribute_format", "attribute_format", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getAttributeFormat, (a, b) -> a.setAttributeFormat(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("sba_dest_attr_value_required", "dest_attr_value_required", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::isRequired, (a, b) -> a.setRequired(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_calc_proc", "calc_proc", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getProcedure, (a, b) -> a.setProcedure(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_src_query", "src_query", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getQuery, (a, b) -> a.setQuery(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>("ssa_src_value", "src_value", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationOutboundAttributeMappingByAttribute, Object>)IEdoIntegrationOutboundAttributeMappingByAttribute::getProcedure, (a, b) -> a.setValue(Objects.toString(b, null))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationOutboundAttributeMappingByAttribute>> getFieldDefinition() {
        return EdoIntegrationOutboundAttributeMappingByAttribute.fieldDefinition;
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
    
    protected EdoIntegrationOutboundAttributeMappingByAttribute(final ICstmConfigAdapter parent) {
        super(parent);
        this.selectType = AttrValueSelectType.unknown;
    }
    
    @Override
    public boolean isRequired() {
        return this.required;
    }
    
    @Override
    public void setRequired(final boolean value) {
        this.required = value;
    }
    
    public AttrValueSelectType getSelectType() {
        return this.selectType;
    }
    
    @Override
    public String getSelectTypeStr() {
        return (this.selectType != null) ? this.selectType.getValue() : "";
    }
    
    public void setSelectType(final AttrValueSelectType value) {
        this.selectType = value;
    }
    
    @Override
    public void setSelectType(final String value) {
        this.selectType = AttrValueSelectType.parse(value);
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
    public IDictionaryElementConfiguration getObjectTypeStorage() {
        return this.objectTypeStorage;
    }
    
    @Override
    public void setObjectTypeStorage(final IDictionaryElementConfiguration value) {
        this.objectTypeStorage = value;
    }
    
    @Override
    public boolean isParentDocAttribute() {
        return this.isParentDocAttribute;
    }
    
    @Override
    public void setParentDocAttribute(final boolean value) {
        this.isParentDocAttribute = value;
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
    public String getQuery() {
        return this.query;
    }
    
    @Override
    public void setQuery(final String value) {
        this.query = value;
    }
    
    @Override
    public String getProcedure() {
        return this.procedure;
    }
    
    @Override
    public void setProcedure(final String value) {
        this.procedure = value;
    }
    
    @Override
    public String getAttributeFormat() {
        return this.attributeFormat;
    }
    
    @Override
    public void setAttributeFormat(final String value) {
        this.attributeFormat = value;
    }
    
    @Override
    public String getAttribute() {
        return (this.getAttributeStorage() != null) ? this.getAttributeStorage().getValue() : null;
    }
    
    @Override
    public String getAttributeType() {
        return (this.getAttributeStorage() != null) ? ((IAttributeDescriptionElementConfiguration)this.getAttributeStorage()).getAttributeDataType() : null;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
