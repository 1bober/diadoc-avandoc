// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.status;

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
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationDocumentRemoteStatus extends CstmConfigAdapter implements IEdoIntegrationDocumentRemoteStatus
{
    private static final long serialVersionUID = 1494924171093542086L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteStatus>> fieldDefinition;
    @JsonProperty
    private String responseStatus;
    @JsonProperty
    private String statusCode;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteStatus>> getFieldDefinition() {
        return EdoIntegrationDocumentRemoteStatus.fieldDefinition;
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
    
    protected EdoIntegrationDocumentRemoteStatus(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getResponseStatus() {
        return this.responseStatus;
    }
    
    @Override
    public void setResponseStatus(final String responseStatus) {
        this.responseStatus = responseStatus;
    }
    
    @Override
    public String getStatusCode() {
        return this.statusCode;
    }
    
    @Override
    public void setStatusCode(final String statusCode) {
        this.statusCode = statusCode;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_status_code", "status_code", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationDocumentRemoteStatus::getStatusCode, (a, b) -> a.setStatusCode(Objects.toString(b, null))), new ConfigFieldBaseDescriptionExt("ssa_response_status", "response_status", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationDocumentRemoteStatus::getResponseStatus, (a, b) -> a.setResponseStatus(Objects.toString(b, null))));
    }
}
