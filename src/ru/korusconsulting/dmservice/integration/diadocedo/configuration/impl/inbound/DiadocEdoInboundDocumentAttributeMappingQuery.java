// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentAttributeMappingQuery;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentAttributeMappingQuery extends CstmConfigAdapter implements IDiadocEdoInboundDocumentAttributeMappingQuery
{
    private static final long serialVersionUID = -4958242252354675966L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>> fieldDefinition;
    @JsonProperty
    private String query;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> procedureArgs;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration displayAttributeStorage;
    @JsonProperty
    private String linkedDisplayAttribute;
    public static final String CONFIG_ID = "diadocedo_inbound_document_attribute_mapping_query";
    
    private static String getProcedureConfigGroupId() {
        return String.join(".", DiadocEdoInboundDocumentAttributeMappingQuery.class.getName(), "inbound_procedure_args_mapping");
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>)new ConfigFieldBaseDescriptionExt("ssa_query", "query", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentAttributeMappingQuery::getQuery, (a, b) -> a.setQuery((b == null) ? "" : b.toString())));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>)new ConfigFieldBaseDescriptionExt("cstm_map_procedure_args_editor", (String)null, "procedure_args", "mapping_procedure_args_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function)IDiadocEdoInboundDocumentAttributeMappingQuery::getProcedureArgs, (a, b) -> a.addProcedureArgFromWrapper(b), IDiadocEdoInboundDocumentAttributeMappingQuery::clearProcedureArgs));
        ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery> attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>)new ConfigFieldBaseDescriptionExt("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentAttributeMappingQuery::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>)new ConfigFieldBaseDescriptionExt("sida_display_attribute", "ssa_display_attribute_name", "display_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentAttributeMappingQuery::getDisplayAttributeStorage, (a, b) -> a.setDisplayAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_display_attribute_manual", "display_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>)new ConfigFieldBaseDescriptionExt("ssa_linked_display_attribute_manual", "linked_display_attribute", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentAttributeMappingQuery::getLinkedDisplayAttribute, (a, b) -> a.setLinkedDisplayAttribute((b == null) ? "" : b.toString())));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentAttributeMappingQuery>> getFieldDefinition() {
        return DiadocEdoInboundDocumentAttributeMappingQuery.fieldDefinition;
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
    
    public DiadocEdoInboundDocumentAttributeMappingQuery(final ICstmConfigAdapter parent) {
        super(parent);
        this.procedureArgs = new ArrayList<IEdoIntegrationAttributeMapping>();
    }
    
    public IDictionaryElementConfiguration getDisplayAttributeStorage() {
        return this.displayAttributeStorage;
    }
    
    public String getDisplayAttribute() {
        return (this.getDisplayAttributeStorage() != null) ? this.getDisplayAttributeStorage().getValue() : null;
    }
    
    public void setDisplayAttributeStorage(final IDictionaryElementConfiguration displayAttributeStorage) {
        this.displayAttributeStorage = displayAttributeStorage;
    }
    
    public String getLinkedDisplayAttribute() {
        return this.linkedDisplayAttribute;
    }
    
    public void setLinkedDisplayAttribute(final String linkedDisplayAttribute) {
        this.linkedDisplayAttribute = linkedDisplayAttribute;
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
    
    public List<IEdoIntegrationAttributeMapping> getProcedureArgs() {
        return this.procedureArgs;
    }
    
    public void clearProcedureArgs() {
        this.getProcedureArgs().clear();
    }
    
    public void addProcedureArg(final IEdoIntegrationAttributeMapping value) {
        this.getProcedureArgs().add(value);
    }
    
    public void addProcedureArgFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createProcedureArg(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (mappingRule != null) {
                this.addProcedureArg(mappingRule);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationAttributeMapping mappingRule = this.createProcedureArg(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (mappingRule != null) {
                this.addProcedureArg(mappingRule);
            }
        }
    }
    
    protected IEdoIntegrationAttributeMapping createProcedureArg(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal AttributeMapping component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationAttributeMapping rule = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getProcedureConfigGroupId(), component.getComponentType(), xmlTagId);
        if (rule != null) {
            rule.unmarshal(session, component, generator, ctx);
        }
        return rule;
    }
    
    protected IEdoIntegrationAttributeMapping createProcedureArg(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping rule = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getProcedureConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (rule != null) {
            rule.unmarshal(session, el, ctx);
        }
        return rule;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public void setQuery(final String value) {
        this.query = value;
    }
    
    static {
        registerChildConfigTagMapping(getProcedureConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_document_attribute_mapping", "procedure_arg_document_mapping", (Class)DiadocEdoInboundDocumentProcedureArgDocumentMapping.class);
        registerChildConfigTagMapping(getProcedureConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_fixed_value_mapping", "procedure_arg_fixed", (Class)DiadocEdoInboundDocumentProcedureArgFixedValue.class);
        registerChildConfigTagMapping(getProcedureConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_mapping_root", "procedure_arg_env_param", (Class)DiadocEdoInboundDocumentProcedureArgEnvProperty.class);
        registerChildConfigTagMapping(getProcedureConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_execution_argument_mapping", "procedure_arg_executing_argument_param", (Class)DiadocEdoInboundDocumentProcedureArgExecutionArgument.class);
        registerChildConfigTagMapping(getProcedureConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_environment_variable_mapping", "procedure_arg_executing_argument_param", (Class)DiadocEdoInboundDocumentProcedureArgEnvironmentVariable.class);
        fieldDefinition = initFieldDefinition();
    }
}
