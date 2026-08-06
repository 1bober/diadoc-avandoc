// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.flows;

import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
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
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationStatusCheckFlowConfiguration;

public abstract class EdoIntegrationOutboundDocumentStatusCheckFlowConfiguration extends EdoIntegrationFlowConfiguration implements IEdoIntegrationStatusCheckFlowConfiguration
{
    private static final long serialVersionUID = 1615537255612795255L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>> fieldDefinition;
    @JsonProperty
    private String statusPath;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>("ssa_status_path", "name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationStatusCheckFlowConfiguration, Object>)IEdoIntegrationStatusCheckFlowConfiguration::getStatusPath, (a, b) -> a.setStatusPath(Objects.toString(b, ""))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationStatusCheckFlowConfiguration>> getFieldDefinition() {
        return EdoIntegrationOutboundDocumentStatusCheckFlowConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationOutboundDocumentStatusCheckFlowConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration(this);
        config.unmarshal(session, component, generator, ctx);
        return config;
    }
    
    @Override
    public IEdoIntegrationOperationConfiguration createOperationConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IEdoIntegrationOperationConfiguration config = new EdoIntegrationOutboundDocumentRemoteStatusProcessingOperationScopedConfiguration(this);
        config.unmarshal(session, element, ctx);
        return config;
    }
    
    @Override
    public EdoIntegrationFlowConfigurationType getFlowType() {
        return EdoIntegrationFlowConfigurationType.STATUS_CHECK;
    }
    
    @Override
    public String getStatusPath() {
        return this.statusPath;
    }
    
    @Override
    public void setStatusPath(final String value) {
        this.statusPath = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
