// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgMapping;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class DiadocEdoInboundDocumentProcedureArgMapping extends DiadocEdoInboundDocumentProcedureArgParameter implements IDiadocEdoInboundDocumentProcedureArgMapping
{
    private static final long serialVersionUID = 486726457889877074L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgMapping>> fieldDefinition;
    @JsonProperty
    private String diadocAttribute;
    @JsonProperty
    private String diadocAttributeExpression;
    @JsonProperty
    private IDiadocEdoInboundDocumentMappingProcedure.SourceType sourceType;
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgMapping>> getFieldDefinition() {
        return DiadocEdoInboundDocumentProcedureArgMapping.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)getFieldDefinition(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), element, ctx);
    }
    
    protected DiadocEdoInboundDocumentProcedureArgMapping(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getDiadocAttributeExpression() {
        return this.diadocAttributeExpression;
    }
    
    @Override
    public void setDiadocAttributeExpression(final String value) {
        this.diadocAttributeExpression = value;
    }
    
    @Override
    public String getDiadocAttribute() {
        return this.diadocAttribute;
    }
    
    @Override
    public void setDiadocAttribute(final String value) {
        this.diadocAttribute = value;
    }
    
    public abstract String getSpecType();
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_remote_attribute_expression", "remote_attribute_expression", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgMapping::getDiadocAttributeExpression, (a, b) -> a.setDiadocAttributeExpression(Objects.toString(b, null))), new ConfigFieldBaseDescriptionExt("ssa_remote_attribute", "remote_attribute", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgMapping::getDiadocAttribute, (a, b) -> a.setDiadocAttribute(Objects.toString(b, null))));
    }
}
