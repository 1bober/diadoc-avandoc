// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentSelectQueryRule;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationCounteragentSelectQueryRule extends CstmConfigAdapter implements IEdoIntegrationCounteragentSelectQueryRule
{
    private static final long serialVersionUID = -9066514722734566884L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>> fieldDefinition;
    @JsonProperty
    private String selectCounteragentQuery;
    @JsonProperty
    private String sendStatusSelectQuery;
    @JsonProperty
    private String sendStatusUpdateQuery;
    public static final String CONFIG_ID = "counteragent_query_rule";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>("ssa_select_counteragent_query", "select_query", ICstmConfigAdapter.FieldType.text, a -> a.getSelectCounteragentQuery(), (a, b) -> a.setSelectCounteragentQuery(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>("ssa_counteragent_send_status_select_query", "send_status_select_query", ICstmConfigAdapter.FieldType.text, a -> a.getSendStatusSelectQuery(), (a, b) -> a.setSendStatusSelectQuery(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>("ssa_counteragent_send_status_update_query", "send_status_update_query", ICstmConfigAdapter.FieldType.text, a -> a.getSendStatusUpdateQuery(), (a, b) -> a.setSendStatusUpdateQuery(Objects.toString(b, null))));
        return v;
    }
    
    private List<ConfigFieldBaseDescriptionExt<IEdoIntegrationCounteragentSelectQueryRule>> getFieldDefinition() {
        return EdoIntegrationCounteragentSelectQueryRule.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, this.getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, this.getFieldDefinition(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, this.getFieldDefinition(), element, ctx);
    }
    
    public EdoIntegrationCounteragentSelectQueryRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public String getSelectCounteragentQuery() {
        return this.selectCounteragentQuery;
    }
    
    @Override
    public void setSelectCounteragentQuery(final String selectQuery) {
        this.selectCounteragentQuery = selectQuery;
    }
    
    @Override
    public String getSendStatusSelectQuery() {
        return this.sendStatusSelectQuery;
    }
    
    @Override
    public void setSendStatusSelectQuery(final String selectStatusQuery) {
        this.sendStatusSelectQuery = selectStatusQuery;
    }
    
    @Override
    public String getSendStatusUpdateQuery() {
        return this.sendStatusUpdateQuery;
    }
    
    @Override
    public void setSendStatusUpdateQuery(final String updateStatusQuery) {
        this.sendStatusUpdateQuery = updateStatusQuery;
    }
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
