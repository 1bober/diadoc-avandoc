// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import java.util.stream.Stream;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationInboundRevokeOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentWithMappingConfiguration;

public abstract class EdoIntegrationInboundRevokeOperationConfiguration extends EdoIntegrationDocumentWithMappingConfiguration implements IEdoIntegrationInboundRevokeOperationConfiguration
{
    private static final long serialVersionUID = -7273984057552911196L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> fieldDefinition;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> typeFieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration titleObjectTypeStorage;
    @JsonProperty
    private List<IEdoIntegrationDocumentRemoteStatus> remoteStatuses;
    @JsonProperty
    private List<IDictionaryElementConfiguration> contentGroups;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> initTypeFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>>();
        final ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration> attributeDesc = new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>("sida_title_object_type", "ssa_title_object_type_name", "type", ICstmConfigAdapter.FieldType.cstm_type, (Function<IEdoIntegrationInboundRevokeOperationConfiguration, Object>)IEdoIntegrationInboundRevokeOperationConfiguration::getTitleObjectTypeStorage, (a, b) -> a.setTitleObjectTypeStorage((IDictionaryElementConfiguration)b));
        attributeDesc.setLinked(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>("ssa_title_object_type_manual", "type_manual", ICstmConfigAdapter.FieldType.type_name));
        v.add(attributeDesc);
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>("rsa_statuses", "rsa_statuses_names", "status", "remote_statuses", ICstmConfigAdapter.FieldType.cstm_values_list, (Function<IEdoIntegrationInboundRevokeOperationConfiguration, Object>)IEdoIntegrationInboundRevokeOperationConfiguration::getRemoteStatuses, (a, b) -> a.addRemoteStatusFromWrapper(b), IEdoIntegrationInboundRevokeOperationConfiguration::clearRemoteStatuses, true));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>("rida_content_group", "content_group", "content_group", "content_groups", ICstmConfigAdapter.FieldType.content_group_list, (Function<IEdoIntegrationInboundRevokeOperationConfiguration, Object>)IEdoIntegrationInboundRevokeOperationConfiguration::getContentGroups, (a, b) -> a.addContentGroup((IDictionaryElementConfiguration)b), IEdoIntegrationInboundRevokeOperationConfiguration::clearContentGroups));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> getTypeFieldDefinition() {
        return EdoIntegrationInboundRevokeOperationConfiguration.typeFieldDefinition;
    }
    
    @Override
    public void unmarshalTypeImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalTypeImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getTypeFieldDefinition(), component, generator, ctx);
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundRevokeOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationInboundRevokeOperationConfiguration.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundRevokeOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, (List<ConfigFieldBaseDescriptionExt<EdoIntegrationInboundRevokeOperationConfiguration>>)Stream.concat((Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getTypeFieldDefinition().stream(), (Stream<? extends ConfigFieldBaseDescriptionExt<T>>)getFieldDefinition().stream()).toList(), element, ctx);
    }
    
    protected EdoIntegrationInboundRevokeOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.remoteStatuses = new ArrayList<IEdoIntegrationDocumentRemoteStatus>();
        this.contentGroups = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    @Override
    public IDictionaryElementConfiguration getTitleObjectTypeStorage() {
        return this.titleObjectTypeStorage;
    }
    
    @Override
    public void setTitleObjectTypeStorage(final IDictionaryElementConfiguration value) {
        this.titleObjectTypeStorage = value;
    }
    
    @Override
    public List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses() {
        return this.remoteStatuses;
    }
    
    @Override
    public void addRemoteStatus(final IEdoIntegrationDocumentRemoteStatus value) {
        this.getRemoteStatuses().add(value);
    }
    
    @Override
    public void clearRemoteStatuses() {
        this.getRemoteStatuses().clear();
    }
    
    @Override
    public void addRemoteStatusFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteStatus(newValue);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentRemoteStatus newValue = this.createRemoteStatus(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (newValue != null) {
                this.addRemoteStatus(newValue);
            }
        }
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getContentGroups() {
        return this.contentGroups;
    }
    
    @Override
    public void clearContentGroups() {
        this.contentGroups.clear();
    }
    
    @Override
    public void addContentGroup(final IDictionaryElementConfiguration value) {
        this.getContentGroups().add(value);
    }
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = initFieldDefinition();
        typeFieldDefinition = initTypeFieldDefinition();
    }
}
