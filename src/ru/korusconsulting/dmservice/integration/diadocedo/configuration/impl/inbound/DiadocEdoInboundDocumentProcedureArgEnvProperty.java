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
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvProperty;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundDocumentProcedureArgEnvProperty extends DiadocEdoInboundDocumentProcedureArgParameter implements IDiadocEdoInboundDocumentProcedureArgEnvProperty
{
    private static final long serialVersionUID = 2674788170080926169L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgEnvProperty>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration envPropertyStorage;
    public static final String CONFIG_ID = "procedure_arg_env_param";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundDocumentProcedureArgEnvProperty>> getFieldDefinition() {
        return DiadocEdoInboundDocumentProcedureArgEnvProperty.fieldDefinition;
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
    
    public DiadocEdoInboundDocumentProcedureArgEnvProperty(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public IDictionaryElementConfiguration getEnvPropertyStorage() {
        return this.envPropertyStorage;
    }
    
    @Override
    public void setEnvPropertyStorage(final IDictionaryElementConfiguration value) {
        this.envPropertyStorage = value;
    }
    
    @Override
    public String getEnvProperty() {
        return (this.getEnvPropertyStorage() == null) ? "" : this.getEnvPropertyStorage().getValue();
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_env_property", "env_property", ICstmConfigAdapter.FieldType.dictionary_value, (Function)IDiadocEdoInboundDocumentProcedureArgEnvProperty::getEnvPropertyStorage, (a, b) -> a.setEnvPropertyStorage((IDictionaryElementConfiguration)b)));
    }
}
