// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Iterator;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import java.util.Map;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Consumer;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationIncomingAttachmentProcessingEvent extends CstmConfigAdapter implements IEdoIntegrationInboundAttachmentProcessingEvent
{
    private static final long serialVersionUID = -6245953620708031723L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>> fieldDefinition;
    @JsonProperty
    private String processorLink;
    public static final String CONFIG_ID = "attachment_processor_event_action";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>("ssa_name", "name", ICstmConfigAdapter.FieldType.cstm_object_link, (Function<IEdoIntegrationInboundAttachmentProcessingEvent, Object>)ICstmConfigAdapter::getName, (a, b) -> a.setName((String)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>("processor_link", "processor_link", ICstmConfigAdapter.FieldType.cstm_object_link, (Function<IEdoIntegrationInboundAttachmentProcessingEvent, Object>)IEdoIntegrationInboundAttachmentProcessingEvent::getProcessorLink, (a, b) -> a.setProcessorLink((String)b), (Consumer<IEdoIntegrationInboundAttachmentProcessingEvent>)null, (a, g, ctx) -> a.unmarshalProcessorLink(g, ctx)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingEvent>> getFieldDefinition() {
        return EdoIntegrationIncomingAttachmentProcessingEvent.fieldDefinition;
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
    
    public EdoIntegrationIncomingAttachmentProcessingEvent(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public void setProcessorLink(final String value) {
        this.processorLink = value;
    }
    
    @Override
    public String getProcessorLink() {
        return this.processorLink;
    }
    
    @Override
    public Boolean unmarshalProcessorLink(final Generator generator, final SessionContext ctx) {
        if (this.getSourceComponent() != null) {
            final Map<String, List<String>> scope = this.getSourceComponent().getSummScope();
            generator.addScopeConfig((Map)scope, this.makeAspectConfig(this.getSourceComponent()), ctx);
            generator.addScopeConfig((Map)scope, this.makeValueProcessorAspectConfig(generator, this.getSourceComponent()), ctx);
            this.setProcessorLink(this.getComponentUid());
        }
        return true;
    }
    
    private RootConfigElement makeAspectConfig(final ICstmComponent cmp) {
        final RootConfigElement aspectConfig = new RootConfigElement();
        aspectConfig.setLocation(cmp.getURI());
        aspectConfig.setTagName("aspect");
        aspectConfig.setAttributeValue("id", this.getComponentUid());
        aspectConfig.setAttributeValue("class", "ru.korusconsulting.dmservice.aspects.ExecuteValueProcessorAspect");
        if (!cmp.getChildren("source_link").isEmpty()) {
            final ICstmComponent source = cmp.getChildren("source_link").get(0);
            aspectConfig.setAttributeValue("sourceaspect", new StringBuilder().append(source.getUID()).toString());
        }
        if (!cmp.getChildren("processor_link").isEmpty()) {
            aspectConfig.setAttributeValue("valueprocessor", this.getComponentUid() + "_vp");
        }
        return aspectConfig;
    }
    
    public RootConfigElement makeValueProcessorAspectConfig(final Generator generator, final ICstmComponent cmp) {
        final RootConfigElement aspectConfig = new RootConfigElement();
        aspectConfig.setLocation(this.getComponentUri());
        aspectConfig.setTagName("aspect");
        aspectConfig.setAttributeValue("id", this.getComponentUid() + "_vp");
        aspectConfig.setAttributeValue("class", "ru.korusconsulting.dmservice.aspects.AspectList");
        final ConfigElement itemsConfig = new ConfigElement();
        generator.addChild((ConfigElement)aspectConfig, itemsConfig);
        itemsConfig.setTagName("items");
        for (final ICstmComponent item : cmp.getChildren("processor_link")) {
            final ConfigElement itemConfig = new ConfigElement();
            generator.addChild(itemsConfig, itemConfig);
            itemConfig.setTagName("item");
            itemConfig.setAttributeValue("name", item.getUID());
        }
        return aspectConfig;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
