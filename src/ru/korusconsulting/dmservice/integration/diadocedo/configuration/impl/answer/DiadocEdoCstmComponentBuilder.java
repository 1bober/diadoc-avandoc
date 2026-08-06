// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.answer;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.status.DiadocEdoDocumentLocalStatus;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentLocalStatus;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.status.DiadocEdoDocumentRemoteStatus;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoInboundAttachmentProcessingEvent;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoInboundArchiveFileAttachmentProcessingRule;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoInboundArchiveAttachmentProcessingRule;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoOutboundAttributeMappingByLinkedObject;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoOutboundAttributeMappingByAttribute;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound.DiadocEdoInboundDocumentExecutionArgumentValue;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound.DiadocEdoInboundDocumentEnvironmentVariableValue;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound.DiadocEdoInboundDocumentEnvironmentPropertyValue;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound.DiadocEdoInboundDocumentAttributeMappingProcedure;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoInboundAttributeMappingByLinkedObject;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoInboundAttributeMappingByAttribute;
import ru.korusconsulting.dmservice.integration.configuration.impl.answer.EdoIntegrationAnswerMappingByAttribute;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoAnswerMappingByCounteragent;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.answer.EdoIntegrationAnswerMappingByFixedValue;
import java.util.HashMap;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigTagToConfigClassMappingStore;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.ICstmConfigClassMapper;

public class DiadocEdoCstmComponentBuilder
{
    private static final String ANSWER_MAPPING;
    private static final String INBOUND_ATTRIBUTE_MAPPING;
    private static final String OUTBOUND_ATTRIBUTE_MAPPING;
    private static final String ATTACHMENT_PROCESSING_RULES;
    private static final String ATTACHMENT_PROCESSING_EVENTS;
    private static final ICstmConfigClassMapper mapper;
    
    protected static ICstmConfigClassMapper createConfigClassMapper() {
        return (ICstmConfigClassMapper)new ICstmConfigClassMapper() {
            private final Map<String, ConfigTagToConfigClassMappingStore> tagToConfigMappingStore = new HashMap<String, ConfigTagToConfigClassMappingStore>();
            
            {
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_inbound_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_inbound_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_inbound_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_inbound_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_local_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_local_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_local_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_local_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_error_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_error_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_error_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, "cstm_integration_diadoc_edo_error_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_mapping_attribute", "attribute_mapping", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundAttributeMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_mapping_object", "cstm_select_expression_editor", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundAttributeMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_mapping_fixed_value", "fixed_value_mapping", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAttributeMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_local_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_local_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_local_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_local_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_error_fixed_value", "mapping_by_fixed", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_error_object_mapping", "mapped_by_linked", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_error_counteragent_mapping", "mapped_by_counteragent", (Class<? extends ICstmConfigAdapter>)DiadocEdoAnswerMappingByCounteragent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_error_direct_mapping", "mapping_by_attribute", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAnswerMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_procedure_to_attribute_mapping", "diadocedo_attribute_mapping_procedure", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentAttributeMappingProcedure.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_env_property_to_attribute_mapping_title", "inbound_document_env_parameter", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentEnvironmentPropertyValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_env_property_to_attribute_mapping", "inbound_document_env_parameter", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentEnvironmentPropertyValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_environment_variable_to_attribute_mapping_title", "diadocedo_environment_variable", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentEnvironmentVariableValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_environment_variable_to_attribute_mapping", "diadocedo_environment_variable", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentEnvironmentVariableValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_execution_argument_to_attribute_mapping_title", "diadocedo_execution_argument", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentExecutionArgumentValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_inbound_execution_argument_to_attribute_mapping", "diadocedo_execution_argument", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundDocumentExecutionArgumentValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_attribute_title", "attribute_mapping", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundAttributeMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_attribute", "attribute_mapping", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundAttributeMappingByAttribute.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_object_title", "linked_object_mapping", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundAttributeMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_object", "linked_object_mapping", (Class<? extends ICstmConfigAdapter>)DiadocEdoOutboundAttributeMappingByLinkedObject.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_fixed_value_title", "fixed_value_mapping", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAttributeMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, "cstm_integration_diadoc_edo_outbound_mapping_fixed_value", "fixed_value_mapping", (Class<? extends ICstmConfigAdapter>)EdoIntegrationAttributeMappingByFixedValue.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, "cstm_integration_diadoc_edo_inbound_attachment_processing_archive_rule", "diadoc_edo_incoming_processing_archive_rule", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundArchiveAttachmentProcessingRule.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, "cstm_integration_diadoc_edo_incoming_attachment_processing_archive_rule", "diadoc_edo_incoming_processing_archive_rule", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundArchiveAttachmentProcessingRule.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, "cstm_integration_diadoc_edo_inbound_attachment_processing_archive_file_rule", "diadoc_edo_incoming_processing_archive_file_rule", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundArchiveFileAttachmentProcessingRule.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, "cstm_integration_diadoc_edo_incoming_attachment_processing_archive_file_rule", "diadoc_edo_incoming_processing_archive_file_rule", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundArchiveFileAttachmentProcessingRule.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_EVENTS, "cstm_diadoc_edo_attachment_processor_event_action", "diadoc_edo_inbound_attachment_processing_event", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundAttachmentProcessingEvent.class);
                this.registerChildConfigTagMapping(DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_EVENTS, "cstm_diadoc_edo_document_processor_event_action", "diadoc_edo_inbound_attachment_processing_event", (Class<? extends ICstmConfigAdapter>)DiadocEdoInboundAttachmentProcessingEvent.class);
            }
            
            public void registerChildConfigTagMapping(final String type, final String cstmType, final String xmlTag, final Class<? extends ICstmConfigAdapter> cls) {
                this.getTagToConfigMappingStore().computeIfAbsent(type, a -> new ConfigTagToConfigClassMappingStore()).add(cstmType, xmlTag, (Class)cls);
            }
            
            public Map<String, ConfigTagToConfigClassMappingStore> getTagToConfigMappingStore() {
                return this.tagToConfigMappingStore;
            }
        };
    }
    
    private DiadocEdoCstmComponentBuilder() {
    }
    
    public static IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationAttributeMapping createAnswerMapping(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoCstmComponentBuilder.ANSWER_MAPPING, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationAttributeMapping createInboundAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationAttributeMapping createInboundAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoCstmComponentBuilder.INBOUND_ATTRIBUTE_MAPPING, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationDocumentRemoteStatus status = (IEdoIntegrationDocumentRemoteStatus)new DiadocEdoDocumentRemoteStatus(parentConfig);
        status.unmarshal(session, component, generator, ctx);
        return status;
    }
    
    public static IEdoIntegrationDocumentRemoteStatus createRemoteStatus(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationDocumentRemoteStatus status = (IEdoIntegrationDocumentRemoteStatus)new DiadocEdoDocumentRemoteStatus(parentConfig);
        status.unmarshal(session, el, ctx);
        return status;
    }
    
    public static IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationDocumentLocalStatus status = (IEdoIntegrationDocumentLocalStatus)new DiadocEdoDocumentLocalStatus(parentConfig);
        status.unmarshal(session, component, generator, ctx);
        return status;
    }
    
    public static IEdoIntegrationDocumentLocalStatus createLocalStatus(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationDocumentLocalStatus status = (IEdoIntegrationDocumentLocalStatus)new DiadocEdoDocumentLocalStatus(parentConfig);
        status.unmarshal(session, el, ctx);
        return status;
    }
    
    public static IEdoIntegrationAttributeMapping createOutboundAttributeMapping(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationAttributeMapping createOutboundAttributeMapping(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationAttributeMapping mapping = (IEdoIntegrationAttributeMapping)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoCstmComponentBuilder.OUTBOUND_ATTRIBUTE_MAPPING, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingRule createInboundAttachmentProcessingRule(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parentConfig, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingRule mapping = (IEdoIntegrationInboundAttachmentProcessingRule)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parentConfig, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, component.getComponentType(), (String)null);
        if (mapping != null) {
            mapping.unmarshal(session, component, generator, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingRule createInboundAttachmentProcessingRule(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parentConfig, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingRule mapping = (IEdoIntegrationInboundAttachmentProcessingRule)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parentConfig, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, el.getTagName(), el.getAttributeValue("config_id"));
        if (mapping != null) {
            mapping.unmarshal(session, el, ctx);
        }
        return mapping;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessorRule(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parent, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingRule event = (IEdoIntegrationInboundAttachmentProcessingRule)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parent, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, component.getComponentType(), (String)null);
        if (event != null) {
            event.unmarshal(session, component, generator, ctx);
        }
        return event;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingRule createAttachmentProcessorRule(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parent, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingRule event = (IEdoIntegrationInboundAttachmentProcessingRule)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parent, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_RULES, el.getTagName(), el.getAttributeValue("config_id"));
        if (event != null) {
            event.unmarshal(session, el, ctx);
        }
        return event;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ICstmComponent component, final ICstmConfigAdapter parent, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingEvent event = (IEdoIntegrationInboundAttachmentProcessingEvent)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByCstmType(parent, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_EVENTS, component.getComponentType(), (String)null);
        if (event != null) {
            event.unmarshal(session, component, generator, ctx);
        }
        return event;
    }
    
    public static IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession session, final ConfigElement el, final ICstmConfigAdapter parent, final SessionContext ctx) {
        final IEdoIntegrationInboundAttachmentProcessingEvent event = (IEdoIntegrationInboundAttachmentProcessingEvent)DiadocEdoCstmComponentBuilder.mapper.getConfigIntanceByConfigId(parent, DiadocEdoCstmComponentBuilder.ATTACHMENT_PROCESSING_EVENTS, el.getTagName(), el.getAttributeValue("config_id"));
        if (event != null) {
            event.unmarshal(session, el, ctx);
        }
        return event;
    }
    
    static {
        ANSWER_MAPPING = String.join(".", DiadocEdoCstmComponentBuilder.class.getName(), "answer_mapping");
        INBOUND_ATTRIBUTE_MAPPING = String.join(".", DiadocEdoCstmComponentBuilder.class.getName(), "inbound_attribute_mapping");
        OUTBOUND_ATTRIBUTE_MAPPING = String.join(".", DiadocEdoCstmComponentBuilder.class.getName(), "outbound_attribute_mapping");
        ATTACHMENT_PROCESSING_RULES = String.join(".", DiadocEdoCstmComponentBuilder.class.getName(), "attachment_processing_rules");
        ATTACHMENT_PROCESSING_EVENTS = String.join(".", DiadocEdoCstmComponentBuilder.class.getName(), "attachment_processing_events");
        mapper = createConfigClassMapper();
    }
}
