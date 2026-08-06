// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.incoming;

import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoExchangeCategoryConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoSendPrepareErrorConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.DictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
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
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.incoming.IDiadocEdoIncomingDocumentReceiveConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound.DiadocEdoInboundDocumentProcessingConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoIncomingDocumentReceiveConfiguration extends DiadocEdoInboundDocumentProcessingConfiguration implements IDiadocEdoIncomingDocumentReceiveConfiguration
{
    private static final long serialVersionUID = -2429692363937866734L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>> fieldDefinition;
    @JsonProperty
    private final List<IDictionaryElementConfiguration> externalTypesStorage;
    @JsonProperty
    private IDictionaryElementConfiguration createErrorEvent;
    @JsonProperty
    private IDictionaryElementConfiguration postCreatingEvent;
    @JsonProperty
    private IDictionaryElementConfiguration sendDocumentEvent;
    @JsonProperty
    private String filterConfig;
    public static final String CONFIG_ID = "diadoc_edo_incoming_document_receive";
    public static final String XML_TAG = "incoming_document_receive";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("sba_inactive", "inactive", ICstmConfigAdapter.FieldType.bool, (Function)ICstmConfigAdapter::getInactive, (a, b) -> a.setInactive(b != null && (boolean)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("rida_external_document_type", "rsa_external_document_type_name", "external_document_type", "external_document_types", ICstmConfigAdapter.FieldType.dictionary_list, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getExternalTypesStorage, (a, b) -> a.addExternalType((IDictionaryElementConfiguration)b), IDiadocEdoIncomingDocumentReceiveConfiguration::clearExternalTypes, true));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("cstm_filter", "filter_link", ICstmConfigAdapter.FieldType.cstm_object_link, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getFilterConfig, (a, b) -> a.setFilterConfig((String)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("sida_post_creating_event", "ssa_post_creating_event_name", "post_creating_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getPostCreatingEvent, (a, b) -> a.setPostCreatingEvent((IDictionaryElementConfiguration)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("sida_send_document_event", "ssa_send_document_event_name", "send_document_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getSendDocumentEvent, (a, b) -> a.setSendDocumentEvent((IDictionaryElementConfiguration)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("sida_create_error_event", "ssa_create_error_event_name", "create_error_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getCreateErrorEvent, (a, b) -> a.setCreateErrorEvent((IDictionaryElementConfiguration)b)));
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>)new ConfigFieldBaseDescriptionExt("sida_create_error_event", "ssa_create_error_event_name", "create_error_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoIncomingDocumentReceiveConfiguration::getCreateErrorEvent, (a, b) -> a.setCreateErrorEvent((IDictionaryElementConfiguration)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>> getFieldDefinition() {
        return DiadocEdoIncomingDocumentReceiveConfiguration.fieldDefinition;
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
    
    public void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoIncomingDocumentReceiveConfiguration>> fieldDefs = getFieldDefinition().stream().filter(ConfigFieldBaseDescriptionExt::isCheckerField).toList();
        this.unmarshalFields(session, (Object)this, (List)fieldDefs, config, ctx);
    }
    
    public DiadocEdoIncomingDocumentReceiveConfiguration() {
        super(null);
        this.externalTypesStorage = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    public DiadocEdoIncomingDocumentReceiveConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.externalTypesStorage = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    public void init() {
        super.init();
        this.setXmlTag("incoming_document_receive");
    }
    
    @Override
    public String getFilterConfig() {
        return this.filterConfig;
    }
    
    @Override
    public void setFilterConfig(final String filterConfig) {
        this.filterConfig = filterConfig;
    }
    
    @Override
    public IDictionaryElementConfiguration getCreateErrorEvent() {
        return this.createErrorEvent;
    }
    
    @Override
    public void setCreateErrorEvent(final IDictionaryElementConfiguration createErrorEvent) {
        this.createErrorEvent = createErrorEvent;
    }
    
    @Override
    public IDictionaryElementConfiguration getPostCreatingEvent() {
        return this.postCreatingEvent;
    }
    
    @Override
    public void setPostCreatingEvent(final IDictionaryElementConfiguration value) {
        this.postCreatingEvent = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getSendDocumentEvent() {
        return this.sendDocumentEvent;
    }
    
    @Override
    public void setSendDocumentEvent(final IDictionaryElementConfiguration value) {
        this.sendDocumentEvent = value;
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getExternalTypesStorage() {
        return this.externalTypesStorage;
    }
    
    @Override
    public List<String> getExternalTypes() {
        return this.getExternalTypesStorage().stream().map(a -> a.getValue()).toList();
    }
    
    @Override
    public void clearExternalTypes() {
        this.getExternalTypesStorage().clear();
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationAttributeMapping createAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createInboundAttributeMapping(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    @Override
    public void addExternalType(final IDictionaryElementConfiguration value) {
        this.getExternalTypesStorage().add(value);
    }
    
    protected IDictionaryElementConfiguration createPostAction(final DMSShardsClientSession session, final ConfigElement config, final SessionContext context) {
        if (config == null) {
            return null;
        }
        final DictionaryElementConfiguration element = new DictionaryElementConfiguration((ICstmConfigAdapter)this);
        element.setId(config.getAttributeValue("id"));
        element.setValue(config.getAttributeValue("value"));
        return (IDictionaryElementConfiguration)element;
    }
    
    protected IDictionaryElementConfiguration createPostAction(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext context) {
        final DictionaryElementConfiguration element = new DictionaryElementConfiguration((ICstmConfigAdapter)this);
        element.setId(component.getURI());
        element.setValue(component.getUID() + "_" + component.getComponentType());
        return (IDictionaryElementConfiguration)element;
    }
    
    public IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        final DiadocEdoSendPrepareErrorConfiguration type = new DiadocEdoSendPrepareErrorConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, component, generator, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createSendPrepareError(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final DiadocEdoSendPrepareErrorConfiguration type = new DiadocEdoSendPrepareErrorConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, config, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagName, final Generator generator, final SessionContext ctx) {
        final DiadocEdoExchangeCategoryConfiguration type = new DiadocEdoExchangeCategoryConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, component, generator, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategory(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        final DiadocEdoExchangeCategoryConfiguration type = new DiadocEdoExchangeCategoryConfiguration((ICstmConfigAdapter)this);
        type.unmarshal(session, config, ctx);
        return (IDictionaryElementConfiguration)type;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
