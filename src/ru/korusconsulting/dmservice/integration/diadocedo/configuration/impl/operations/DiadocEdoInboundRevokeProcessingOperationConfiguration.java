// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoInboundRevokeProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundRevokeProcessingOperationConfiguration extends CstmConfigAdapter implements IDiadocEdoInboundRevokeProcessingOperationConfiguration
{
    private static final long serialVersionUID = -3126443252255449890L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>> fieldDefinition;
    @JsonProperty
    private IDictionaryElementConfiguration contentGroupStorage;
    public static final String CONFIG_ID = "diadocedo_inbound_revoke_operation";
    public static final String XML_TAG = "inbound_revoke_operation";
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>>();
        v.add((ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>)new ConfigFieldBaseDescriptionExt("sida_content_group", "ssa_content_group_name", "content_group", ICstmConfigAdapter.FieldType.content_group, a -> a.getContentGroupStorage(), (a, b) -> a.setContentGroupStorage((IDictionaryElementConfiguration)b)));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IDiadocEdoInboundRevokeProcessingOperationConfiguration>> getFieldDefinition() {
        return DiadocEdoInboundRevokeProcessingOperationConfiguration.fieldDefinition;
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
    
    public DiadocEdoInboundRevokeProcessingOperationConfiguration() {
        super((ICstmConfigAdapter)null);
    }
    
    public void init() {
        super.init();
        this.setXmlTag("inbound_revoke_operation");
    }
    
    public IDictionaryElementConfiguration getContentGroupStorage() {
        return this.contentGroupStorage;
    }
    
    public void setContentGroupStorage(final IDictionaryElementConfiguration value) {
        this.contentGroupStorage = value;
    }
    
    public String getContentGroup() {
        return (this.getContentGroupStorage() != null) ? this.getContentGroupStorage().getId() : null;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
