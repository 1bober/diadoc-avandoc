// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationExecutionArgument;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationAttributeMappingByOperationExecutionArgument extends EdoIntegrationRemoteAttributeMapping implements IEdoIntegrationAttributeMappingByOperationExecutionArgument
{
    private static final long serialVersionUID = 3335577183777994412L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByOperationExecutionArgument>> fieldDefinition;
    @JsonProperty
    private String executionArgument;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationAttributeMappingByOperationExecutionArgument>> getFieldDefinition() {
        return EdoIntegrationAttributeMappingByOperationExecutionArgument.fieldDefinition;
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
    
    protected EdoIntegrationAttributeMappingByOperationExecutionArgument(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getExecutionArgument() {
        return this.executionArgument;
    }
    
    @Override
    public void setExecutionArgument(final String value) {
        this.executionArgument = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_execution_argument", "execution_argument", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationAttributeMappingByOperationExecutionArgument::getExecutionArgument, (a, b) -> a.setExecutionArgument((b == null) ? null : ((String)b))));
    }
}
