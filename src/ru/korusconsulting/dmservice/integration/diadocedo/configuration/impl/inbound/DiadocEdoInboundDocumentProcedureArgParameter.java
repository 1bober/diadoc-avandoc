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
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgParameter;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class DiadocEdoInboundDocumentProcedureArgParameter extends CstmConfigAdapter implements IDiadocEdoInboundDocumentProcedureArgParameter
{
    private static final long serialVersionUID = -503017256145717088L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgParameter>> fieldDefinition;
    @JsonProperty
    private String procArgument;
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgParameter>> getFieldDefinition() {
        return DiadocEdoInboundDocumentProcedureArgParameter.fieldDefinition;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), component, generator, ctx);
    }
    
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = (T)super.marshalImpl(session, xmlTag, generator, (Supplier)configElementSupplier, ctx);
        this.marshalFields(session, (Object)this, (List)getFieldDefinition(), (ConfigElement)config, generator, ctx);
        return config;
    }
    
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, (Object)this, (List)getFieldDefinition(), element, ctx);
    }
    
    protected DiadocEdoInboundDocumentProcedureArgParameter(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getProcArgument() {
        return this.procArgument;
    }
    
    public void setProcArgument(final String value) {
        this.procArgument = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_proc_argument", "proc_argument", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoInboundDocumentProcedureArgParameter::getProcArgument, (a, b) -> a.setProcArgument(Objects.toString(b, null))));
    }
}
