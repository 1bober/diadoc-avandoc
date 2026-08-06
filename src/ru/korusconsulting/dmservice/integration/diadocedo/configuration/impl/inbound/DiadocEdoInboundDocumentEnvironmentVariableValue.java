// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

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
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentEnvironmentVariableValue;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentEnvironmentVariableValue extends CstmConfigAdapter implements IDiadocEdoInboundDocumentEnvironmentVariableValue
{
    private static final long serialVersionUID = -1494598805866628352L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>> fieldDefinition;
    @JsonProperty
    private String environmentName;
    @JsonProperty
    private String environmentVariable;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration displayAttributeStorage;
    @JsonProperty
    private String linkedDisplayAttribute;
    public static final String CONFIG_ID = "diadocedo_environment_variable";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>)new ConfigFieldBaseDescriptionExt("ssa_environment_name", "environment_name", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentEnvironmentVariableValue::getEnvironmentName, (a, b) -> a.setEnvironmentName(Objects.toString(b, ""))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>)new ConfigFieldBaseDescriptionExt("ssa_environment_variable", "environment_variable", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentEnvironmentVariableValue::getEnvironmentVariable, (a, b) -> a.setEnvironmentVariable(Objects.toString(b, ""))));
        ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue> attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>)new ConfigFieldBaseDescriptionExt("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentEnvironmentVariableValue::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>)new ConfigFieldBaseDescriptionExt("sida_display_attribute", "ssa_display_attribute_name", "display_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentEnvironmentVariableValue::getDisplayAttributeStorage, (a, b) -> a.setDisplayAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_display_attribute_manual", "display_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>)new ConfigFieldBaseDescriptionExt("ssa_linked_display_attribute_manual", "linked_display_attribute", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentEnvironmentVariableValue::getLinkedDisplayAttribute, (a, b) -> a.setLinkedDisplayAttribute(Objects.toString(b, null))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentEnvironmentVariableValue>> getFieldDefinition() {
        return DiadocEdoInboundDocumentEnvironmentVariableValue.fieldDefinition;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = (T)super.marshalImpl(session, xmlTag, generator, (Supplier)configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)getFieldDefinition(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), element, ctx);
    }
    
    public DiadocEdoInboundDocumentEnvironmentVariableValue(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IDictionaryElementConfiguration getDisplayAttributeStorage() {
        return this.displayAttributeStorage;
    }
    
    public void setDisplayAttributeStorage(final IDictionaryElementConfiguration displayAttributeStorage) {
        this.displayAttributeStorage = displayAttributeStorage;
    }
    
    public String getDisplayAttribute() {
        return (this.getDisplayAttributeStorage() != null) ? this.getDisplayAttributeStorage().getValue() : null;
    }
    
    public String getLinkedDisplayAttribute() {
        return this.linkedDisplayAttribute;
    }
    
    public void setLinkedDisplayAttribute(final String linkedDisplayAttribute) {
        this.linkedDisplayAttribute = linkedDisplayAttribute;
    }
    
    public String getEnvironmentName() {
        return this.environmentName;
    }
    
    public void setEnvironmentName(final String value) {
        this.environmentName = value;
    }
    
    public String getEnvironmentVariable() {
        return this.environmentVariable;
    }
    
    public void setEnvironmentVariable(final String value) {
        this.environmentVariable = value;
    }
    
    public IDictionaryElementConfiguration getAttributeStorage() {
        return this.attributeStorage;
    }
    
    public void setAttributeStorage(final IDictionaryElementConfiguration attribute) {
        this.attributeStorage = attribute;
    }
    
    public String getAttribute() {
        return (this.getAttributeStorage() != null) ? this.getAttributeStorage().getValue() : null;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
