// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations;

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
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentWithMappingConfiguration;

public abstract class EdoIntegrationDocumentOperationConfiguration extends EdoIntegrationDocumentWithMappingConfiguration implements IEdoIntegrationDocumentOperationConfiguration
{
    private static final long serialVersionUID = 7587419751784459837L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private boolean dontMarkAsProceed;
    @JsonProperty
    private boolean breakOperation;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationDocumentOperationConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationDocumentOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public boolean isDontMarkAsProceed() {
        return this.dontMarkAsProceed;
    }
    
    @Override
    public void setDontMarkAsProceed(final boolean value) {
        this.dontMarkAsProceed = value;
    }
    
    @Override
    public boolean isBreakOperation() {
        return this.breakOperation;
    }
    
    @Override
    public void setBreakOperation(final boolean value) {
        this.breakOperation = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("sba_dont_mark_as_proceed", "dont_mark_as_proceed", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)IEdoIntegrationDocumentOperationConfiguration::isDontMarkAsProceed, (a, b) -> a.setDontMarkAsProceed(b != null && (boolean)b)), new ConfigFieldBaseDescriptionExt("sba_break_operation", "break_operation", ICstmConfigAdapter.FieldType.bool, (Function<T, Object>)IEdoIntegrationDocumentOperationConfiguration::isBreakOperation, (a, b) -> a.setBreakOperation(b != null && (boolean)b)));
    }
}
