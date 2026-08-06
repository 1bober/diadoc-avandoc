// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ITypeDescriptionElementConfiguration;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import ru.korusconsulting.service.objectmodel.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import org.apache.logging.log4j.LogManager;
import java.util.Collections;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.ICstmConfigProperties;
import java.util.List;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.ICstmConfigClassMapper;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class CstmConfigAdapter implements ICstmConfigAdapter, ICstmConfigClassMapper
{
    private static final long serialVersionUID = 399289856171298508L;
    private final transient Logger logger;
    private static final transient List<ConfigFieldBaseDescriptionExt<ICstmConfigAdapter>> fieldDefinition;
    private final ICstmConfigProperties properties;
    @JsonIgnore
    private static final Map<String, ConfigTagToConfigClassMappingStore> tagToConfigMappingStore;
    @JsonIgnore
    private final ICstmConfigAdapter parent;
    @JsonIgnore
    private String xmlTag;
    @JsonIgnore
    private String configId;
    @JsonProperty
    private boolean isInactive;
    @JsonProperty
    private String name;
    @JsonIgnore
    private ICstmComponent sourceComponent;
    @JsonIgnore
    private String componentUri;
    @JsonIgnore
    private String componentUid;
    
    private List<ConfigFieldBaseDescriptionExt<ICstmConfigAdapter>> getFieldDefinition() {
        return CstmConfigAdapter.fieldDefinition;
    }
    
    private List<ConfigFieldBaseDescriptionExt<ICstmConfigAdapter>> getTypeFieldDefinition() {
        return Collections.emptyList();
    }
    
    @Override
    public String getComponentUid() {
        return this.componentUid;
    }
    
    public void setComponentUid(final String componentUid) {
        this.componentUid = componentUid;
    }
    
    protected CstmConfigAdapter(final ICstmConfigAdapter parent) {
        this.logger = LogManager.getLogger();
        this.properties = new CstmConfigProperties();
        this.parent = parent;
        this.init();
    }
    
    public void init() {
    }
    
    @Override
    public ICstmConfigAdapter getParent() {
        return this.parent;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    @Override
    public final void setName(final String value) {
        this.name = value;
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration getDocumentConfig() {
        if (this instanceof final IEdoIntegrationOperationConfiguration a) {
            return a;
        }
        return (this.getParent() != null) ? this.getParent().getDocumentConfig() : null;
    }
    
    @Override
    public IEdoIntegrationDocumentOperationConfiguration getDocumentOperationConfig() {
        if (this instanceof final IEdoIntegrationDocumentOperationConfiguration a) {
            return a;
        }
        return (this.getParent() != null) ? this.getParent().getDocumentOperationConfig() : null;
    }
    
    protected boolean needConfigTrace(final ConfigElement element) {
        return false;
    }
    
    protected final void traceMarshal(final ConfigElement element) {
        if (this.needConfigTrace(element)) {
            final Logger logger = this.getLogger();
            Objects.requireNonNull(element);
            logger.trace(element::asString);
        }
    }
    
    @Override
    public final void unmarshal(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        this.getLogger().trace(() -> String.format("Component %s (%s) with type %s (%s) has properties %s", component.getObjectId(), component.getObjectType(), component.getComponentType(), component.getComponentTypeName(), component.getProperties().entrySet().stream().map(a -> {
            final StringBuilder sb = new StringBuilder().append("\n[").append(a.getKey()).append(":");
            final Object patt5607$temp = a.getValue();
            if (patt5607$temp instanceof final List c) {
                c.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Object d = iterator.next();
                    sb.append("[").append((d != null) ? d.toString() : "null").append("];");
                }
            }
            else {
                sb.append(a.getValue().toString());
            }
            return sb.append("]").toString();
        }).collect(Collectors.joining(", "))));
        this.setSourceComponent(component);
        this.setComponentUid(component.getUID());
        this.setComponentUri(component.getURI());
        this.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalImpl(session, component, generator, ctx);
        this.loadStaticConfiguration(session, ctx);
        this.getLogger().trace(() -> String.format("cstm object unmarshaled to %s", this.toString()));
    }
    
    protected void loadStaticConfiguration(final DMSShardsClientSession session, final SessionContext ctx) {
    }
    
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        this.unmarshalFields(session, this, this.getTypeFieldDefinition(), component, generator, ctx);
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        this.unmarshalFields(session, this, this.getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public final <T extends ConfigElement> T marshal(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> supplier, final SessionContext ctx) {
        final T config = (T)this.marshalImpl(session, xmlTag, generator, (Supplier<ConfigElement>)supplier, ctx);
        this.getLogger().trace(() -> String.format("marshaled to %s", config.asString()));
        config.setAttributeValue("config_uid", this.getComponentUid());
        config.setLocation(this.getComponentUri());
        return config;
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        if (configElementSupplier == null) {
            throw new IllegalArgumentException("configElementSupplier can't be null");
        }
        final T config = configElementSupplier.get();
        config.setTagName(xmlTag);
        if (StringUtils.isNotBlank(this.getConfigId())) {
            config.setAttributeValue("config_id", this.getConfigId());
        }
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<CstmConfigAdapter>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)this.getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)this.getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public final void unmarshal(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        this.setXmlTag(element.getTagName());
        this.setConfigId(element.getAttributeValue("config_id"));
        this.setComponentUid(element.getAttributeValue("config_uid"));
        this.setComponentUri(element.getLocation());
        this.unmarshalImpl(session, element, ctx);
        this.loadStaticConfiguration(session, ctx);
        this.getLogger().trace(() -> String.format("XML config marshaled to %s", this.toString()));
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<CstmConfigAdapter>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)this.getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)this.getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    @Override
    public String toString() {
        final ObjectMapper objectMapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().disable(new MapperFeature[] { MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS, MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS })).disable(new SerializationFeature[] { SerializationFeature.FAIL_ON_EMPTY_BEANS })).build();
        try {
            return this.getClass().getCanonicalName() + "\n\n" + objectMapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException e) {
            this.getLogger().trace("Error while mapping to string", (Throwable)e);
            return super.toString();
        }
    }
    
    protected String traceComponent(final ICstmComponent component) {
        if (component == null) {
            return "null";
        }
        return (String)component.getProperties().entrySet().stream().map(a -> {
            final StringBuilder sb = new StringBuilder().append("\n[").append(a.getKey()).append(":");
            final Object patt11254$temp = a.getValue();
            if (patt11254$temp instanceof final List c) {
                c.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Object d = iterator.next();
                    sb.append("[").append((d != null) ? d.toString() : "null").append("];");
                }
            }
            else {
                sb.append(a.getValue().toString());
            }
            return sb.append("]").toString();
        }).collect(Collectors.joining(", "));
    }
    
    @Override
    public final Logger getLogger() {
        return this.logger;
    }
    
    public ICstmComponent getSourceComponent() {
        return this.sourceComponent;
    }
    
    public void setSourceComponent(final ICstmComponent value) {
        this.sourceComponent = value;
    }
    
    public String getComponentUri() {
        return this.componentUri;
    }
    
    public void setComponentUri(final String componentUri) {
        this.componentUri = componentUri;
    }
    
    @Override
    public Map<String, ConfigTagToConfigClassMappingStore> getTagToConfigMappingStore() {
        return getTagToConfigMappingStoreStatic();
    }
    
    private static Map<String, ConfigTagToConfigClassMappingStore> getTagToConfigMappingStoreStatic() {
        return CstmConfigAdapter.tagToConfigMappingStore;
    }
    
    @Override
    public String getXmlTag() {
        if (CstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE && StringUtils.isBlank(this.xmlTag)) {
            throw new IllegalArgumentException(String.format("Xml tag is null for config %s", this.getClass().getCanonicalName()));
        }
        return this.xmlTag;
    }
    
    @Override
    public void setXmlTag(final String xmlTag) {
        this.xmlTag = xmlTag;
    }
    
    public static void registerChildConfigTagMapping(final String groupId, final String cstmType, final String xmlTag, final Class<? extends ICstmConfigAdapter> cls) {
        getTagToConfigMappingStoreStatic().computeIfAbsent(groupId, a -> new ConfigTagToConfigClassMappingStore()).add(cstmType, xmlTag, cls);
    }
    
    @Override
    public ICstmConfigProperties getConfigProperies() {
        return this.properties;
    }
    
    @Override
    public ITypeDescriptionElementConfiguration getRootType() {
        if (this.getParent() != null) {
            final ITypeDescriptionElementConfiguration rootType = this.getParent().getRootType();
            if (rootType != null) {
                return rootType;
            }
        }
        return this.getConfigProperies().getType();
    }
    
    @Override
    public ITypeDescriptionElementConfiguration getType() {
        if (this.getConfigProperies().getType() != null) {
            return this.getConfigProperies().getType();
        }
        if (this.getParent() != null) {
            return this.getParent().getType();
        }
        return null;
    }
    
    @Override
    public String getConfigId() {
        return this.configId;
    }
    
    @Override
    public void setConfigId(final String configId) {
        this.configId = configId;
    }
    
    @Override
    public void setInactive(final boolean value) {
        this.isInactive = value;
    }
    
    @Override
    public boolean getInactive() {
        return this.isInactive;
    }
    
    @Override
    public String getConfigComponentId() {
        return this.getComponentUri();
    }
    
    @Override
    public void setConfigComponentId(final String value) {
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_name", "name", FieldType.text, (Function<T, Object>)ICstmConfigAdapter::getName, (a, b) -> a.setName(Objects.toString(b, ""))), new ConfigFieldBaseDescriptionExt(null, "component_config_id", FieldType.internal_string, (Function<T, Object>)ICstmConfigAdapter::getConfigComponentId, (a, b) -> a.setConfigComponentId(Objects.toString(b, ""))));
        tagToConfigMappingStore = new HashMap<String, ConfigTagToConfigClassMappingStore>();
    }
}
