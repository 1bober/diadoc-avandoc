// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationConditionalOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOperationConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationDocumentConfiguration extends EdoIntegrationOperationConfiguration implements IEdoIntegrationConditionalOperationConfiguration
{
    private static final long serialVersionUID = -5391338825465419149L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationConditionalOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IEdoIntegrationDocumentSelectCondition> conditions;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationConditionalOperationConfiguration>> getFieldDefinition() {
        return EdoIntegrationDocumentConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationDocumentConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.conditions = new ArrayList<IEdoIntegrationDocumentSelectCondition>();
    }
    
    @Override
    public List<IEdoIntegrationDocumentSelectCondition> getConditions() {
        return this.conditions;
    }
    
    @Override
    public void clearConditions() {
        this.getConditions().clear();
    }
    
    @Override
    public void addCondition(final IEdoIntegrationDocumentSelectCondition condition) {
        this.getConditions().add(condition);
    }
    
    @Override
    public void addConditionFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationDocumentSelectCondition condition = this.createDocumentSelectCondition(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (condition != null) {
                this.addCondition(condition);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationDocumentSelectCondition condition = this.createDocumentSelectCondition(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (condition != null) {
                this.addCondition(condition);
            }
        }
    }
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("cstm_select_expression_editor", null, "condition", "conditions", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<T, Object>)IEdoIntegrationConditionalOperationConfiguration::getConditions, (a, b) -> a.addConditionFromWrapper(b), IEdoIntegrationConditionalOperationConfiguration::clearConditions));
    }
}
