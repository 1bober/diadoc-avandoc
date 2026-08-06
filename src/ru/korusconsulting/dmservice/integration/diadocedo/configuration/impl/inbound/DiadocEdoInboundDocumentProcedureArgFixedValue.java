// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

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
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgFixedValue;

public class DiadocEdoInboundDocumentProcedureArgFixedValue extends DiadocEdoInboundDocumentProcedureArgParameter implements IDiadocEdoInboundDocumentProcedureArgFixedValue
{
    private static final long serialVersionUID = 526358829384421674L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgFixedValue>> fieldDefinition;
    @JsonProperty
    private String value;
    public static final String CONFIG_ID = "procedure_arg_fixed";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgFixedValue>> getFieldDefinition() {
        return DiadocEdoInboundDocumentProcedureArgFixedValue.fieldDefinition;
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
    
    public DiadocEdoInboundDocumentProcedureArgFixedValue(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_value", "value", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgFixedValue::getValue, (a, b) -> a.setValue((String)b)));
    }
}
