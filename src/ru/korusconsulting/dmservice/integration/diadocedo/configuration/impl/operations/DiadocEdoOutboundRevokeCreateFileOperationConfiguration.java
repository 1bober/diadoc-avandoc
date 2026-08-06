// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer.DiadocEdoCstmComponentBuilder;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationRevokeOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundRevokeCreateFileOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundRequestOperationConfiguration;

public class DiadocEdoOutboundRevokeCreateFileOperationConfiguration extends EdoIntegrationOutboundRequestOperationConfiguration implements IDiadocEdoOutboundRevokeCreateFileOperationConfiguration, IEdoIntegrationRevokeOperationConfiguration
{
    private static final long serialVersionUID = -2608950899895326410L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundRevokeCreateFileOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration contentGroupStorage;
    @JsonProperty
    private String reasonVariableName;
    public static final String CONFIG_ID = "diadocedo_outbound_revoke_create_file_operation";
    public static final String XML_TAG = "diadocedo_outbound_revoke_create_file_operation";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoOutboundRevokeCreateFileOperationConfiguration>> getFieldDefinition() {
        return DiadocEdoOutboundRevokeCreateFileOperationConfiguration.fieldDefinition;
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
    
    public DiadocEdoOutboundRevokeCreateFileOperationConfiguration() {
        super((ICstmConfigAdapter)null);
    }
    
    public void init() {
        super.init();
        this.setXmlTag("diadocedo_outbound_revoke_create_file_operation");
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
    
    public String getReasonVariableName() {
        return this.reasonVariableName;
    }
    
    public void setReasonVariableName(final String value) {
        this.reasonVariableName = value;
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, el, (ICstmConfigAdapter)this, ctx);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return DiadocEdoCstmComponentBuilder.createAttachmentProcessorEvent(session, component, (ICstmConfigAdapter)this, generator, ctx);
    }
    
    public IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType getRevokeOperationType() {
        return IEdoIntegrationRevokeOperationConfiguration.RevokeOperationType.CREATE_FILE;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sida_content_group", "ssa_content_group_name", "content_group", ICstmConfigAdapter.FieldType.content_group, (Function)IDiadocEdoOutboundRevokeCreateFileOperationConfiguration::getContentGroupStorage, (a, b) -> a.setContentGroupStorage((IDictionaryElementConfiguration)b)), new ConfigFieldBaseDescriptionExt("ssa_reason_variable_name", "reason_variable_name", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoOutboundRevokeCreateFileOperationConfiguration::getReasonVariableName, (a, b) -> a.setReasonVariableName(Objects.toString(b, null))));
    }
}
