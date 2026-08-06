// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration;

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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.IDiadocEdoExternalType;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoExternalType extends CstmConfigAdapter implements IDiadocEdoExternalType
{
    private static final long serialVersionUID = -7617179664696504333L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>> fieldDefinition;
    @JsonProperty
    private String documentCode;
    public static final String CONFIG_ID = "external_type";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>)new ConfigFieldBaseDescriptionExt("ssa_document_code", "ssa_document_code", ICstmConfigAdapter.FieldType.text, (Function)IDiadocEdoExternalType::getDocumentCode, (a, b) -> a.setDocumentCode(Objects.toString(b, null))));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoExternalType>> getFieldDefinition() {
        return DiadocEdoExternalType.fieldDefinition;
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
    
    public DiadocEdoExternalType(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getDocumentCode() {
        return this.documentCode;
    }
    
    public void setDocumentCode(final String value) {
        this.documentCode = value;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
