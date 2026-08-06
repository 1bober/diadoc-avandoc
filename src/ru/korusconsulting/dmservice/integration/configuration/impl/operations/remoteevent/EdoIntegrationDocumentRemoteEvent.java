// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl.operations.remoteevent;

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
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.operations.remoteevent.IEdoIntegrationDocumentRemoteEvent;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

public abstract class EdoIntegrationDocumentRemoteEvent extends CstmConfigAdapter implements IEdoIntegrationDocumentRemoteEvent
{
    private static final long serialVersionUID = -209484613947403987L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEvent>> fieldDefinition;
    private String remoteEvent;
    private String remoteEventAction;
    private String remoteEventCode;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationDocumentRemoteEvent>> getFieldDefinition() {
        return EdoIntegrationDocumentRemoteEvent.fieldDefinition;
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
    
    protected EdoIntegrationDocumentRemoteEvent(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getRemoteEvent() {
        return this.remoteEvent;
    }
    
    @Override
    public void setRemoteEvent(final String value) {
        this.remoteEvent = value;
    }
    
    @Override
    public String getRemoteEventCode() {
        return this.remoteEventCode;
    }
    
    @Override
    public void setRemoteEventCode(final String value) {
        this.remoteEventCode = value;
    }
    
    @Override
    public String getRemoteEventAction() {
        return this.remoteEventAction;
    }
    
    @Override
    public void setRemoteEventAction(final String value) {
        this.remoteEventAction = value;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("ssa_remote_event_code", "remote_event_code", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationDocumentRemoteEvent::getRemoteEventCode, (a, b) -> a.setRemoteEventCode(Objects.toString(b, null))), new ConfigFieldBaseDescriptionExt("ssa_remote_event", "remote_event", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationDocumentRemoteEvent::getRemoteEvent, (a, b) -> a.setRemoteEvent(Objects.toString(b, null))), new ConfigFieldBaseDescriptionExt("ssa_remote_event_action", "remote_event_action", ICstmConfigAdapter.FieldType.text, (Function<T, Object>)IEdoIntegrationDocumentRemoteEvent::getRemoteEventAction, (a, b) -> a.setRemoteEventAction(Objects.toString(b, null))));
    }
}
