// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.Arrays;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRequestFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationRemoteEventFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationIncomingDocumentsFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRevokeFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationInboundRevokeFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationStatusCheckFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationCounteragentFlowConfiguration;
import java.util.Iterator;
import ru.korusconsulting.dmservice.integration.configuration.impl.flows.EdoIntegrationFlowConfigurationType;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundFlowConfiguration;
import java.util.ArrayList;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationServiceConfiguration extends CstmConfigAdapter implements ICstmConfigAdapter, IEdoIntegrationServiceConfiguration
{
    private static final long serialVersionUID = -1602026687403972815L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationServiceConfiguration>> fieldDefinition;
    @JsonProperty
    private List<IEdoIntegrationFlowConfiguration> docflowLinks;
    @JsonProperty
    private List<IDictionaryElementConfiguration> exchangeCategories;
    @JsonProperty
    private List<IDictionaryElementConfiguration> exchangeErrorCategories;
    public static final String XML_TAG = "service_configuration";
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationServiceConfiguration>> getFieldDefinition() {
        return EdoIntegrationServiceConfiguration.fieldDefinition;
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
    
    protected EdoIntegrationServiceConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
        this.docflowLinks = new ArrayList<IEdoIntegrationFlowConfiguration>();
        this.exchangeCategories = new ArrayList<IDictionaryElementConfiguration>();
        this.exchangeErrorCategories = new ArrayList<IDictionaryElementConfiguration>();
    }
    
    @Override
    public void init() {
        super.init();
        this.setXmlTag("service_configuration");
    }
    
    @Override
    public List<IEdoIntegrationFlowConfiguration> getDocflowLinks() {
        return this.docflowLinks;
    }
    
    @Override
    public void clearDocflowLinks() {
        this.getDocflowLinks().clear();
    }
    
    @Override
    public void addDocflowLink(final IEdoIntegrationFlowConfiguration value) {
        this.getDocflowLinks().add(value);
    }
    
    @Override
    public void addDocflowLinkFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationFlowConfiguration link = this.createIntegrationFlowConfiguration(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (link != null) {
                this.addDocflowLink(link);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationFlowConfiguration link = this.createIntegrationFlowConfiguration(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (link != null) {
                this.addDocflowLink(link);
            }
        }
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getExchangeCategories() {
        return this.exchangeCategories;
    }
    
    @Override
    public void clearExchangeCategories() {
        this.getExchangeCategories().clear();
    }
    
    @Override
    public void addExchangeCategory(final IDictionaryElementConfiguration value) {
        this.getExchangeCategories().add(value);
    }
    
    @Override
    public void addExchangeCategoryFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IDictionaryElementConfiguration config = this.createExchangeCategoryConfiguration(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (config != null) {
                this.addExchangeCategory(config);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IDictionaryElementConfiguration config = this.createExchangeCategoryConfiguration(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (config != null) {
                this.addExchangeCategory(config);
            }
        }
    }
    
    @Override
    public IEdoIntegrationOutboundFlowConfiguration getOutboundFlowConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.OUTBOUND) {
                return (IEdoIntegrationOutboundFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationCounteragentFlowConfiguration getCounteragentFlowConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.COUNTERAGENT) {
                return (IEdoIntegrationCounteragentFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationStatusCheckFlowConfiguration getStatusFlowConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.STATUS_CHECK) {
                return (IEdoIntegrationStatusCheckFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationInboundRevokeFlowConfiguration getInboundRevokeConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.INBOUND_REVOKE) {
                return (IEdoIntegrationInboundRevokeFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationOutboundRevokeFlowConfiguration getOutboundRevokeConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.OUTBOUND_REVOKE) {
                return (IEdoIntegrationOutboundRevokeFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationIncomingDocumentsFlowConfiguration getIncomingDocumentConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.INCOMING) {
                return (IEdoIntegrationIncomingDocumentsFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationRemoteEventFlowConfiguration getRemoteEventConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.REMOTE_EVENT) {
                return (IEdoIntegrationRemoteEventFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public IEdoIntegrationOutboundRequestFlowConfiguration getOutboundRequestConfiguration() {
        for (final IEdoIntegrationFlowConfiguration c : this.getDocflowLinks()) {
            if (c.getFlowType() == EdoIntegrationFlowConfigurationType.OUTBOUND_REQUEST) {
                return (IEdoIntegrationOutboundRequestFlowConfiguration)c;
            }
        }
        return null;
    }
    
    @Override
    public List<IDictionaryElementConfiguration> getExchangeErrorCategories() {
        return this.exchangeErrorCategories;
    }
    
    @Override
    public void clearExchangeErrorCategories() {
        this.getExchangeErrorCategories().clear();
    }
    
    @Override
    public void addExchangeErrorCategory(final IDictionaryElementConfiguration value) {
        this.getExchangeErrorCategories().add(value);
    }
    
    @Override
    public void addExchangeErrorCategoryFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IDictionaryElementConfiguration config = this.createExchangeErrorCategoryConfiguration(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (config != null) {
                this.addExchangeErrorCategory(config);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IDictionaryElementConfiguration config = this.createExchangeErrorCategoryConfiguration(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (config != null) {
                this.addExchangeErrorCategory(config);
            }
        }
    }
    
    public abstract IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    public abstract IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    @Override
    public ICstmConfigAdapter getParent() {
        return null;
    }
    
    @Override
    public ICstmConfigAdapter getRoot() {
        return this;
    }
    
    static {
        fieldDefinition = Arrays.asList(new ConfigFieldBaseDescriptionExt("cstm_integration_docflows_link", null, "flow", "flows", FieldType.cstm_linked_list, (Function<T, Object>)IEdoIntegrationServiceConfiguration::getDocflowLinks, (a, b) -> a.addDocflowLinkFromWrapper(b), IEdoIntegrationServiceConfiguration::clearDocflowLinks), new ConfigFieldBaseDescriptionExt("cstm_exchange_categories_link", null, "exchange_category", "exchange_categories", FieldType.cstm_linked_list, (Function<T, Object>)IEdoIntegrationServiceConfiguration::getExchangeCategories, (a, b) -> a.addExchangeCategoryFromWrapper(b), IEdoIntegrationServiceConfiguration::clearExchangeCategories), new ConfigFieldBaseDescriptionExt("cstm_exchange_error_categories_link", null, "exchange_error_category", "exchange_error_categories", FieldType.cstm_linked_list, (Function<T, Object>)IEdoIntegrationServiceConfiguration::getExchangeErrorCategories, (a, b) -> a.addExchangeErrorCategoryFromWrapper(b), IEdoIntegrationServiceConfiguration::clearExchangeErrorCategories));
    }
}
