// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationRevokeOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundRevokeSendFileOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundRequestOperationConfiguration;

public class DiadocEdoOutboundRevokeSendFileOperationConfiguration extends EdoIntegrationOutboundRequestOperationConfiguration implements IDiadocEdoOutboundRevokeSendFileOperationConfiguration, IEdoIntegrationRevokeOperationConfiguration
{
    private static final long serialVersionUID = -6121639186283398054L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundRevokeSendFileOperationConfiguration>> fieldDefinition;
    private IDictionaryElementConfiguration contentGroupStorage;
    public static final String CONFIG_ID = "diadocedo_outbound_revoke_send_file_operation";
    public static final String XML_TAG = "diadocedo_outbound_revoke_send_file_operation";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundRevokeSendFileOperationConfiguration>> getFieldDefinition() {
        return DiadocEdoOutboundRevokeSendFileOperationConfiguration.fieldDefinition;
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
    
    public DiadocEdoOutboundRevokeSendFileOperationConfiguration() {
        super((ICstmConfigAdapter)null);
    }
    
    public void init() {
        super.init();
        this.setXmlTag("diadocedo_outbound_revoke_send_file_operation");
    }
    
    public IDictionaryElementConfiguration getContentGroupStorage() {
        return this.contentGroupStorage;
    }
    
    public void setContentGroupStorage(final IDictionaryElementConfiguration value) {
        this.contentGroupStorage = value;
    }
    
    public String getContentGroup() {
        return (this.getContentGroupStorage() != null) ? this.getContentGroupStorage().getValue() : null;
    }
    
    public IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType getRevokeOperationType() {
        return IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType.SEND_FILE;
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sida_content_group", "ssa_content_group_name", "content_group", ICstmConfigAdapter.FieldType.content_group, (Function)IDiadocEdoOutboundRevokeSendFileOperationConfiguration::getContentGroupStorage, (a, b) -> a.setContentGroupStorage((IDictionaryElementConfiguration)b)));
    }
}
