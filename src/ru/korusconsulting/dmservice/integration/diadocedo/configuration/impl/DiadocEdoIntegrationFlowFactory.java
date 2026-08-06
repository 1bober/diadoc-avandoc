// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows.DiadocEdoOutboundRevokeFlowConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows.DiadocEdoOutboundDocumentStatusCheckFlowConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows.DiadocEdoCounteragentFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.flows.DiadocEdoOutboundFlowConfiguration;
import java.util.HashMap;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigTagToConfigClassMappingStore;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.ICstmConfigClassMapper;

public class DiadocEdoIntegrationFlowFactory
{
    private static final String FLOWS;
    private static final String EXCHANGE_CATEGORIES;
    private static final String EXCHANGE_ERROR_CATEGORIES;
    private static final String EXTERNAL_DOCUMENT_TYPE;
    private static final String SEND_PREPARE_ERROR_CATEGORIES;
    private static final ICstmConfigClassMapper mapper;
    
    protected static ICstmConfigClassMapper createConfigClassMapper() {
        return (ICstmConfigClassMapper)new ICstmConfigClassMapper() {
            private final Map<String, ConfigTagToConfigClassMappingStore> tagToConfigMappingStore = new HashMap<String, ConfigTagToConfigClassMappingStore>();
            
            {
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.FLOWS, "cstm_integration_diadoc_edo_outbound_document_operation_flow", "diadocedo_outbound_flows", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundFlowConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.FLOWS, "cstm_integration_diadoc_edo_incoming_document_docflow", DiadocEdoIncomingDocumentsFlowConfiguration.CONFIG_ID, (Class<? extends ICstmConfigAdapter>)DiadocEdoIncomingDocumentsFlowConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.FLOWS, "cstm_integration_diadoc_edo_counteragent_flow", "counteragent_flow", (Class<? extends ICstmConfigAdapter>)DiadocEdoCounteragentFlowConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.FLOWS, "cstm_integration_diadoc_edo_outbound_check_status_flow", DiadocEdoOutboundDocumentStatusCheckFlowConfiguration.CONFIG_ID, (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundDocumentStatusCheckFlowConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.FLOWS, "cstm_integration_diadoc_edo_outbound_revoke_processing_flow", "diadocedo_outbound_revoke_flows", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundRevokeFlowConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.EXCHANGE_CATEGORIES, "cstm_integration_diadoc_edo_exchange_category", "exchange_category", (Class<? extends ICstmConfigAdapter>)DiadocEdoExchangeCategoryConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.EXCHANGE_ERROR_CATEGORIES, "cstm_integration_diadoc_edo_exchange_error_category", "exchange_error_category", (Class<? extends ICstmConfigAdapter>)DiadocEdoExchangeErrorCategoryConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.SEND_PREPARE_ERROR_CATEGORIES, "cstm_integration_diadoc_edo_send_prepare_error", "send_prepare_error", (Class<? extends ICstmConfigAdapter>)DiadocEdoSendPrepareErrorConfiguration.class);
                this.registerChildConfigTagMapping(DiadocEdoIntegrationFlowFactory.EXTERNAL_DOCUMENT_TYPE, "cstm_integration_diadoc_edo_external_document_type", "external_type", (Class<? extends ICstmConfigAdapter>)DiadocEdoExternalDocumentTypeConfiguration.class);
            }
            
            public void registerChildConfigTagMapping(final String type, final String cstmType, final String xmlTag, final Class<? extends ICstmConfigAdapter> cls) {
                this.getTagToConfigMappingStore().computeIfAbsent(type, a -> new ConfigTagToConfigClassMappingStore()).add(cstmType, xmlTag, (Class)cls);
            }
            
            public Map<String, ConfigTagToConfigClassMappingStore> getTagToConfigMappingStore() {
                return this.tagToConfigMappingStore;
            }
        };
    }
    
    private DiadocEdoIntegrationFlowFactory() {
    }
    
    public static IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationFlowConfiguration mapping = (IEdoIntegrationFlowConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoIntegrationFlowFactory.FLOWS, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ConfigElement el, final SessionContext ctx) {
        final IEdoIntegrationFlowConfiguration mapping = (IEdoIntegrationFlowConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoIntegrationFlowFactory.FLOWS, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoIntegrationFlowFactory.EXCHANGE_CATEGORIES, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ConfigElement el, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoIntegrationFlowFactory.EXCHANGE_CATEGORIES, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoIntegrationFlowFactory.EXCHANGE_ERROR_CATEGORIES, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ConfigElement el, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoIntegrationFlowFactory.EXCHANGE_ERROR_CATEGORIES, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createSendPrepareErrorCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoIntegrationFlowFactory.SEND_PREPARE_ERROR_CATEGORIES, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createSendPrepareCategoryConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ConfigElement el, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoIntegrationFlowFactory.SEND_PREPARE_ERROR_CATEGORIES, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoIntegrationFlowFactory.EXTERNAL_DOCUMENT_TYPE, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IDictionaryElementConfiguration createExternalDocumentTypeConfiguration(final DMSShardsClientSession session, final ICstmConfigAdapter parentConfig, final ConfigElement el, final SessionContext ctx) {
        final IDictionaryElementConfiguration mapping = (IDictionaryElementConfiguration)DiadocEdoIntegrationFlowFactory.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoIntegrationFlowFactory.EXTERNAL_DOCUMENT_TYPE, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    static {
        FLOWS = String.join(".", DiadocEdoIntegrationFlowFactory.class.getName(), "flows");
        EXCHANGE_CATEGORIES = String.join(".", DiadocEdoIntegrationFlowFactory.class.getName(), "exchange_categories");
        EXCHANGE_ERROR_CATEGORIES = String.join(".", DiadocEdoIntegrationFlowFactory.class.getName(), "exchange_error_categories");
        EXTERNAL_DOCUMENT_TYPE = String.join(".", DiadocEdoIntegrationFlowFactory.class.getName(), "external_document_type");
        SEND_PREPARE_ERROR_CATEGORIES = String.join(".", DiadocEdoIntegrationFlowFactory.class.getName(), "send_prepare_error_categories");
        mapper = createConfigClassMapper();
    }
}
