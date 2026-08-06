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
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentProcedureArgEnvironmentVariable extends DiadocEdoInboundDocumentProcedureArgParameter implements IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable
{
    private static final long serialVersionUID = -2501856332919017818L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable>> fieldDefinition;
    @JsonProperty
    private String environmentName;
    @JsonProperty
    private String environmentVariable;
    public static final String CONFIG_ID = "procedure_arg_executing_argument_param";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable>> getFieldDefinition() {
        return DiadocEdoInboundDocumentProcedureArgEnvironmentVariable.fieldDefinition;
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
    
    public DiadocEdoInboundDocumentProcedureArgEnvironmentVariable(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getEnvironmentName() {
        return this.environmentName;
    }
    
    @Override
    public void setEnvironmentName(final String value) {
        this.environmentName = value;
    }
    
    @Override
    public String getEnvironmentVariable() {
        return this.environmentVariable;
    }
    
    @Override
    public void setEnvironmentVariable(final String value) {
        this.environmentVariable = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_environment_name", "environment_name", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable::getEnvironmentName, (a, b) -> a.setEnvironmentName(Objects.toString(b, null))), new ConfigFieldBaseDescriptionExt("ssa_environment_variable", "environment_variable", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable::getEnvironmentVariable, (a, b) -> a.setEnvironmentVariable(Objects.toString(b, null))));
    }
}
