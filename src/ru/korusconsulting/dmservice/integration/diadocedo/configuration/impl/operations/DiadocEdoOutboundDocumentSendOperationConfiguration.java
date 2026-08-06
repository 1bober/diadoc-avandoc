// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoCounteragentSelectObjectRule;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationCounteragentSelectQueryRule;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationCounteragentSelectAttributeRule;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentOperationConfiguration;
import java.util.Iterator;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectRule;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoBoxRule;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoDocumentAttributeDesc;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoOutboundMapping;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundDocumentSendOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoOutboundDocumentSendOperationConfiguration extends EdoIntegrationOutboundDocumentSendOperationConfiguration implements IDiadocEdoOutboundDocumentSendOperationConfiguration, IDiadocEdoOutboundMapping
{
    private static final long serialVersionUID = 2341108931350042462L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration diadocEdoBoxMode;
    @JsonProperty
    private boolean useDiadocEdoShelf;
    @JsonProperty
    private IDictionaryElementConfiguration lockModeStorage;
    @JsonProperty
    private List<IEdoDocumentAttributeDesc> edoDocumentAttributes;
    public static final String CONFIG_ID = "diadocedo_send_operation";
    public static final String XML_TAG = "send_operation";
    
    private static String getSelectRuleConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "counteragent_select");
    }
    
    private static String getQueuedStatusProcessorConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "queued_status_processor");
    }
    
    private static String getSendedStatusProcessorConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "sended_status_processor");
    }
    
    private static String getPrepareErrorProcessorConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "prepare_error_processor");
    }
    
    private static String getExchangeErrorProcessorConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "exchange_error_processor");
    }
    
    private static String getDefaultErrorProcessorConfigGroupId() {
        return String.join(".", DiadocEdoOutboundDocumentSendOperationConfiguration.class.getName(), "default_error_processor");
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>)new ConfigFieldBaseDescriptionExt("sida_diadoc_edo_box_mode", "ssa_diadoc_edo_box_mode_name", "box_mode", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoOutboundDocumentSendOperationConfiguration::getDiadocEdoBoxMode, (a, b) -> a.setDiadocEdoBoxMode((IDictionaryElementConfiguration)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>)new ConfigFieldBaseDescriptionExt("ssa_lock_mode", "ssa_lock_mode_name", "lock_mode", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoOutboundDocumentSendOperationConfiguration::getLockModeStorage, (a, b) -> a.setLockModeStorage((IDictionaryElementConfiguration)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>)new ConfigFieldBaseDescriptionExt("sba_use_shelf", "use_shelf", ICstmConfigAdapter.FieldType.bool, (Function)IDiadocEdoOutboundDocumentSendOperationConfiguration::getUseDiadocEdoShelf, (a, b) -> a.setUseDiadocEdoShelf(b != null && (boolean)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundDocumentSendOperationConfiguration>> getFieldDefinition() {
        return DiadocEdoOutboundDocumentSendOperationConfiguration.fieldDefinition;
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
    
    public DiadocEdoOutboundDocumentSendOperationConfiguration() {
        super((ICstmConfigAdapter)null);
        this.edoDocumentAttributes = new ArrayList<IEdoDocumentAttributeDesc>();
    }
    
    public void init() {
        super.init();
        this.setXmlTag("send_operation");
    }
    
    public boolean isUseDiadocEdoShelf() {
        return this.useDiadocEdoShelf;
    }
    
    public IDictionaryElementConfiguration getDiadocEdoBoxMode() {
        return this.diadocEdoBoxMode;
    }
    
    public void setDiadocEdoBoxMode(final IDictionaryElementConfiguration diadocEdoBoxMode) {
        this.diadocEdoBoxMode = diadocEdoBoxMode;
    }
    
    public void setUseDiadocEdoShelf(final boolean useDiadocShelf) {
        this.useDiadocEdoShelf = useDiadocShelf;
    }
    
    public boolean getUseDiadocEdoShelf() {
        return this.useDiadocEdoShelf;
    }
    
    public String getDiadocEdoBoxSelectMode() {
        return (this.getDiadocEdoBoxMode() != null) ? this.getDiadocEdoBoxMode().getId() : DiadocEdoBoxRule.DOCUMENT_FILIAL.name();
    }
    
    public IDictionaryElementConfiguration getLockModeStorage() {
        return this.lockModeStorage;
    }
    
    public void setLockModeStorage(final IDictionaryElementConfiguration lockMode) {
        this.lockModeStorage = lockMode;
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, component, xmlTagId, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createDocumentMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return this.createDocumentMappingImpl(session, el, ctx);
    }
    
    public IEdoIntegrationSelectRule createCounteragentSelectRule(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationSelectRule rule = (IEdoIntegrationSelectRule)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getSelectRuleConfigGroupId(), component.getComponentType(), xmlTagId);
        if (rule != null) {
            rule.unmarshal(session, component, generator, ctx);
        }
        return rule;
    }
    
    public IEdoIntegrationSelectRule createCounteragentSelectRule(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationSelectRule rule = (IEdoIntegrationSelectRule)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getSelectRuleConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (rule != null) {
            rule.unmarshal(session, el, ctx);
        }
        return rule;
    }
    
    protected void loadStaticConfiguration(final DMSShardsClientSession session, final SessionContext ctx) {
        super.loadStaticConfiguration(session, ctx);
        this.clearEdoDocumentAttribute();
        final ConfigElement integrationConfig = ServiceReg.getConfigService().lookupElement(new StringBuilder(200).append("integration[id=").append("diadoc_edo_integration").append("]").toString(), new SessionContext());
        final ConfigElement outbound = integrationConfig.getFirstChild("outbound");
        final ConfigElement documents = outbound.getFirstChild("documents");
        if (documents != null) {
            final List<ConfigElement> procs = documents.getChildren("document");
            for (final ConfigElement proc : procs) {
                if (proc.getAttributeValue("id").equals(this.getTargetTypeId())) {
                    final String processorClassName = proc.getAttributeValue("class");
                    this.setEdoDocumentProcessorClassName(processorClassName);
                    this.setEdoDocumentQueueProcessorClassName(proc.getAttributeValue("queue_class"));
                    final ConfigElement attributes = proc.getFirstChild("attributes");
                    if (attributes != null) {
                        final List<ConfigElement> attributeList = attributes.getChildren("attribute");
                        for (final ConfigElement a : attributeList) {
                            this.addEdoDocumentAttribute((IEdoDocumentAttributeDesc)new DiadocDocumentAttributeDesc(a.getAttributeValue("id"), a.getAttributeValue("label"), EdoIntegrationDocumentAttributeDataType.parse(a.getAttributeValue("type")), a.getAttributeValue("format"), Boolean.parseBoolean(a.getAttributeValue("required"))));
                        }
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode getCounteragentMode() {
        return (this.getCounteragentModeStorage() != null) ? EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode.valueOf(this.getCounteragentModeStorage().getValue()) : EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode.multi;
    }
    
    public IDiadocEdoOutboundDocumentOperationConfiguration.LockModeConfig getLockMode() {
        try {
            return IDiadocEdoOutboundDocumentOperationConfiguration.LockModeConfig.parse(this.getLockModeStorage().getValue());
        }
        catch (final IllegalArgumentException e) {
            this.getLogger().error(String.format("Failed to parse LockMode value of %s. Use None value instead", this.getLockModeStorage().getValue()));
            return IDiadocEdoOutboundDocumentOperationConfiguration.LockModeConfig.None;
        }
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAnswerMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createQueuedStatusProcessor(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getQueuedStatusProcessorConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createQueuedStatusProcessor(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal LocalStatus %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getQueuedStatusProcessorConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createSendedStatusProcessor(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getSendedStatusProcessorConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createSendedStatusProcessor(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getSendedStatusProcessorConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createPrepareErrorProcessor(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getPrepareErrorProcessorConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createPrepareErrorProcessor(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getPrepareErrorProcessorConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createExchangeErrorProcessor(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getExchangeErrorProcessorConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createExchangeErrorProcessor(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getExchangeErrorProcessorConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createDefaultErrorProcessor(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), this.traceComponent(component)));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByCstmType((ICstmConfigAdapter)this, getDefaultErrorProcessorConfigGroupId(), component.getComponentType(), xmlTagId);
        if (config != null) {
            config.unmarshal(session, component, generator, ctx);
        }
        return config;
    }
    
    protected IEdoIntegrationInboundDocumentProcessingConfiguration createDefaultErrorProcessor(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Request to marshal %s xml: %s", el.getTagName(), el.toString()));
        final IEdoIntegrationInboundDocumentProcessingConfiguration config = (IEdoIntegrationInboundDocumentProcessingConfiguration)this.getConfigIntanceByConfigId((ICstmConfigAdapter)this, getDefaultErrorProcessorConfigGroupId(), el.getTagName(), el.getAttributeValue("config_id"));
        if (config != null) {
            config.unmarshal(session, el, ctx);
        }
        return config;
    }
    
    static {
        registerChildConfigTagMapping(getSelectRuleConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_select_attribute", "counteragent_attribute_rule", (Class)EdoIntegrationCounteragentSelectAttributeRule.class);
        registerChildConfigTagMapping(getSelectRuleConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_select_query", "counteragent_query_rule", (Class)EdoIntegrationCounteragentSelectQueryRule.class);
        registerChildConfigTagMapping(getSelectRuleConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_select_object", "counteragent_object_rule", (Class)DiadocEdoCounteragentSelectObjectRule.class);
        registerChildConfigTagMapping(getQueuedStatusProcessorConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_queued_status_processing_operation", String.join(".", "diadocedo_status_processing", "local_status"), (Class)DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration.class);
        registerChildConfigTagMapping(getSendedStatusProcessorConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_sended_status_processing_operation", String.join(".", "diadocedo_status_processing", "local_status"), (Class)DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration.class);
        registerChildConfigTagMapping(getPrepareErrorProcessorConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_send_prepare_error_processing_operation", String.join(".", "diadocedo_status_processing", "remote_status"), (Class)DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration.class);
        registerChildConfigTagMapping(getExchangeErrorProcessorConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_exchange_error_processing_operation", String.join(".", "diadocedo_status_processing", "local_error"), (Class)DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration.class);
        registerChildConfigTagMapping(getDefaultErrorProcessorConfigGroupId(), "cstm_integration_diadoc_edo_counteragent_default_error_processing_operation", String.join(".", "diadocedo_status_processing", "remote_error"), (Class)DiadocEdoOutboundDocumentStatusProcessingOperationConfiguration.class);
        fieldDefinition = initFieldDefinition();
    }
    
    class DiadocDocumentAttributeDesc implements IEdoDocumentAttributeDesc
    {
        @JsonProperty
        private String name;
        @JsonProperty
        private String label;
        @JsonProperty
        private EdoIntegrationDocumentAttributeDataType dataType;
        @JsonProperty
        private String format;
        @JsonProperty
        private boolean isRequired;
        
        public DiadocDocumentAttributeDesc(final String name, final String label, final EdoIntegrationDocumentAttributeDataType dataType, final String format, final boolean isRequired) {
            this.name = name;
            this.label = label;
            this.dataType = dataType;
            this.format = format;
            this.isRequired = isRequired;
        }
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public void setLabel(final String value) {
            this.label = value;
        }
        
        public String getLabel() {
            return this.label;
        }
        
        public EdoIntegrationDocumentAttributeDataType getDataType() {
            return this.dataType;
        }
        
        public void setDataType(final EdoIntegrationDocumentAttributeDataType dataType) {
            this.dataType = dataType;
        }
        
        public String getFormat() {
            return this.format;
        }
        
        public void setFormat(final String format) {
            this.format = format;
        }
        
        public boolean isRequired() {
            return this.isRequired;
        }
        
        public void setRequired(final boolean isRequired) {
            this.isRequired = isRequired;
        }
    }
}
