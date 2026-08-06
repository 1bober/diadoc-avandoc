// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

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
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationIncomingDocumentsFlowConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationIncomingDocumentsFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationIncomingDocumentsFlowConfiguration
{
    private static final long serialVersionUID = 3983040089549645753L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IDictionaryElementConfiguration> externalDocumentTypes;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>("cstm_external_document_types_elements_link", null, "external_document_type", "external_document_type", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationIncomingDocumentsFlowConfiguration, Object>)IEdoIntegrationIncomingDocumentsFlowConfiguration::getExternalDocumentTypes, (a, b) -> a.addExternalDocumentTypeFromWrapper(b), IEdoIntegrationIncomingDocumentsFlowConfiguration::clearExternalDocumentTypes));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationIncomingDocumentsFlowConfiguration>> getFieldDefinition() {
        return EdoIntegrationIncomingDocumentsFlowConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationIncomingDocumentsFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.externalDocumentTypes = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getExternalDocumentTypes() {
        return this.externalDocumentTypes;
    }
    
    @Override
    public void clearExternalDocumentTypes() {
        this.getExternalDocumentTypes().clear();
    }
    
    @Override
    public void addExternalDocumentType(final IDictionaryElementConfiguration value) {
        this.getExternalDocumentTypes().add(value);
    }
    
    @Override
    public void addExternalDocumentTypeFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IDictionaryElementConfiguration config = this.createExternalDocumentTypeConfiguration(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (config != null) {
                this.addExternalDocumentType(config);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IDictionaryElementConfiguration config = this.createExternalDocumentTypeConfiguration(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (config != null) {
                this.addExternalDocumentType(config);
            }
        }
    }
    
    public abstract IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
