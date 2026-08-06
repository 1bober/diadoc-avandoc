// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import java.util.Iterator;
import ru.korusconsulting.dmservice.config.alt.ConfigBase;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationScopedConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationScopedConfiguration extends CstmConfigAdapter implements IEdoIntegrationScopedConfiguration
{
    private static final long serialVersionUID = -4263101953189292212L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationScopedConfiguration>> fieldDefinition;
    @JsonProperty
    private String componentName;
    @JsonProperty
    private String configComponentId;
    @JsonProperty
    private IDictionaryElementConfiguration integrationLogEventStorage;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationScopedConfiguration>> getFieldDefinition() {
        return EdoIntegrationScopedConfiguration.fieldDefinition;
    }
    
    protected EdoIntegrationScopedConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public final String getComponentName() {
        return this.componentName;
    }
    
    @Override
    public final IEdoIntegrationOperationConfiguration getDocumentConfig() {
        return null;
    }
    
    @Override
    public final IEdoIntegrationDocumentOperationConfiguration getDocumentOperationConfig() {
        return null;
    }
    
    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }
    
    public void processComponent(final DMSShardsClientSession session, final ICstmComponent cstmObject, final SessionContext ctx) {
        final List<ICstmComponent> ctxCmps = cstmObject.getChildren("cstm_context");
        if (ctxCmps != null) {
            for (final ICstmComponent child : ctxCmps) {
                this.processComponent(session, child, ctx);
            }
        }
        final SessionContext newCtx = ctx.clone();
        newCtx.set("__cstmConfigBundleId", (Object)ServiceReg.getCstmService().getCfgGroupId(session, (ICstmObject)cstmObject, newCtx));
        ServiceReg.getCstmService().applyGenerator(session, (ICstmObject)cstmObject, ctx);
        ((ConfigBase)ServiceReg.getConfigService().getInternalConfig()).getOwnerIndex().shrink(newCtx.getString("__cstmConfigBundleId"));
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        this.setConfigComponentId(super.getConfigComponentId());
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
        this.getLogger().trace(() -> String.format("Create scoped subcomponent %s with type %s ", component.getUID(), component.getComponentType()));
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), element, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, getFieldDefinition(), config, generator, ctx);
        return config;
    }
    
    @Override
    public IDictionaryElementConfiguration getIntegrationLogEventIdStorage() {
        return this.integrationLogEventStorage;
    }
    
    @Override
    public void setIntegrationLogEventIdStorage(final IDictionaryElementConfiguration value) {
        this.integrationLogEventStorage = value;
    }
    
    @Override
    public String getIntegrationLogEventId() {
        return (this.getIntegrationLogEventIdStorage() == null) ? null : this.getIntegrationLogEventIdStorage().getValue();
    }
    
    @Override
    public void setConfigComponentId(final String value) {
        this.configComponentId = value;
    }
    
    @Override
    public String getConfigComponentId() {
        return this.configComponentId;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_integration_log_event", "ssa_integration_log_event_name", "integration_log_event", ICstmConfigAdapter.FieldType.dictionary_value, (Function<T, Object>)IEdoIntegrationScopedConfiguration::getIntegrationLogEventIdStorage, (a, b) -> a.setIntegrationLogEventIdStorage((IDictionaryElementConfiguration)b), null));
    }
}
