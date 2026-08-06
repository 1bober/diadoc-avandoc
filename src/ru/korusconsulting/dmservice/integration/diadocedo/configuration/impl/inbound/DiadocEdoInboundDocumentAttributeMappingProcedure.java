// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import java.util.Iterator;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
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
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentAttributeMappingProcedure extends CstmConfigAdapter implements IDiadocEdoInboundDocumentMappingProcedure
{
    private static final long serialVersionUID = 5630960373900768204L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>> fieldDefinition;
    @JsonProperty
    private String procedureName;
    @JsonProperty
    private final List<IEdoIntegrationAttributeMapping> procedureArgs;
    @JsonProperty
    private IDictionaryElementConfiguration attributeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration displayAttributeStorage;
    @JsonProperty
    private String linkedDisplayAttribute;
    private String procedureClass;
    public static final String CONFIG_ID = "diadocedo_attribute_mapping_procedure";
    
    private static String getProcedureArgConfigGroupId() {
        return String.join(".", DiadocEdoInboundDocumentAttributeMappingProcedure.class.getName(), "procedure_arg");
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>)new ConfigFieldBaseDescriptionExt("ssa_procedure_name", "procedure_name", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentMappingProcedure::getProcedureName, (a, b) -> a.setProcedureName(Objects.toString(b, null))));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>)new ConfigFieldBaseDescriptionExt("cstm_map_procedure_args_editor", (String)null, "procedure_args", "mapping_procedure_args_list", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function)IDiadocEdoInboundDocumentMappingProcedure::getProcedureArgs, (a, b) -> a.addProcedureArgFromWrapper(b), IDiadocEdoInboundDocumentMappingProcedure::clearProcedureArgs));
        ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure> attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>)new ConfigFieldBaseDescriptionExt("sida_attribute", "ssa_attribute_name", "attribute", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentMappingProcedure::getAttributeStorage, (a, b) -> a.setAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_attribute_manual", "attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("sba_is_title_document_attribute", "is_title_document_attribute", ICstmConfigAdapter.FieldType.title_flag));
        v.add(attributeDesc);
        attributeDesc = (ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>)new ConfigFieldBaseDescriptionExt("sida_display_attribute", "ssa_display_attribute_name", "display_attribute_name", ICstmConfigAdapter.FieldType.cstm_attribute, (Function)IDiadocEdoInboundDocumentMappingProcedure::getDisplayAttributeStorage, (a, b) -> a.setDisplayAttributeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt("ssa_display_attribute_manual", "display_attribute_manual", ICstmConfigAdapter.FieldType.attribute_name));
        v.add(attributeDesc);
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>)new ConfigFieldBaseDescriptionExt("ssa_linked_display_attribute_manual", "linked_display_attribute", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentMappingProcedure::getLinkedDisplayAttribute, (a, b) -> a.setLinkedDisplayAttribute(Objects.toString(b, null))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentMappingProcedure>> getFieldDefinition() {
        return DiadocEdoInboundDocumentAttributeMappingProcedure.fieldDefinition;
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
    
    public DiadocEdoInboundDocumentAttributeMappingProcedure(final ICstmConfigAdapter parent) {
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
        final IEdoIntegrationAttributeMapping config = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getProcedureArgConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationAttributeMapping createProcedureArg(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal AttributeMapping %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationAttributeMapping config = (IEdoIntegrationAttributeMapping)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getProcedureArgConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    public String getProcedureName() {
        return this.procedureName;
    }
    
    public void setProcedureName(final String value) {
        this.procedureName = value;
    }
    
    public String getProcedureClass() {
        return this.procedureClass;
    }
    
    public void setProcedureClass(final String value) {
        this.procedureClass = value;
    }
    
    protected void loadStaticConfiguration(final DMSShardsClientSession session, final SessionContext ctx) {
        super.loadStaticConfiguration(session, ctx);
        final ConfigElement integrationConfig = ServiceReg.getConfigService().lookupElement(new StringBuilder(200).append("integration[id=").append("diadoc_edo_integration").append("]").toString(), new SessionContext());
        if (integrationConfig != null) {
            final ConfigElement inbound = integrationConfig.getFirstChild("inbound");
            if (inbound != null) {
                final ConfigElement documents = inbound.getFirstChild("procedures");
                if (documents != null) {
                    final List<ConfigElement> procs = documents.getChildren("procedure");
                    for (final ConfigElement proc : procs) {
                        if (proc.getAttributeValue("id").equals(this.getProcedureName())) {
                            this.setProcedureClass(proc.getAttributeValue("class"));
                            break;
                        }
                    }
                }
            }
        }
    }
    
    static {
        registerChildConfigTagMapping(getProcedureArgConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_document_attribute_mapping", "procedure_arg_document_mapping", (Class)DiadocEdoInboundDocumentProcedureArgDocumentMapping.class);
        registerChildConfigTagMapping(getProcedureArgConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_fixed_value_mapping", "procedure_arg_fixed", (Class)DiadocEdoInboundDocumentProcedureArgFixedValue.class);
        registerChildConfigTagMapping(getProcedureArgConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_mapping_root", "procedure_arg_env_param", (Class)DiadocEdoInboundDocumentProcedureArgEnvProperty.class);
        registerChildConfigTagMapping(getProcedureArgConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_execution_argument_mapping", "procedure_arg_executing_argument_param", (Class)DiadocEdoInboundDocumentProcedureArgExecutionArgument.class);
        registerChildConfigTagMapping(getProcedureArgConfigGroupId(), "cstm_integration_diadoc_edo_inbound_procedure_args_environment_variable_mapping", "procedure_arg_executing_argument_param", (Class)DiadocEdoInboundDocumentProcedureArgEnvironmentVariable.class);
        fieldDefinition = initFieldDefinition();
    }
}
