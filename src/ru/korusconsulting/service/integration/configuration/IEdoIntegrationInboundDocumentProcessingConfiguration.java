// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import java.util.List;

public interface IEdoIntegrationInboundDocumentProcessingConfiguration extends IEdoIntegrationOperationConfiguration, IEdoIntegrationProcessingConfiguration
{
    IDictionaryElementConfiguration getInternalType();
    
    void setInternalType(final IDictionaryElementConfiguration p0);
    
    List<IEdoIntegrationInboundAttachmentProcessingRule> getAttachmentProcessingRules();
    
    void addAttachmentProcessingRule(final IEdoIntegrationInboundAttachmentProcessingRule p0);
    
    void clearAttachmentProcessingRules();
    
    void addAttachmentProcessingRuleFromWrapper(final Object p0);
    
    void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent p0);
    
    List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents();
    
    void clearProcessorEvents();
    
    void addProcessorEventFromWrapper(final Object p0);
    
    String getDataArgument();
    
    void setDataArgument(final String p0);
    
    List<IEdoIntegrationAttributeMapping> getAttributesMapping();
    
    void addAttributeMapping(final IEdoIntegrationAttributeMapping p0);
    
    void addAttributeMappingFromWrapper(final Object p0);
    
    void clearAttributesMapping();
    
    List<IEdoIntegrationAttributeMapping> getMappingProcedures();
    
    void addMappingProcedure(final IEdoIntegrationAttributeMapping p0);
    
    void addMappingProceduresFromWrapper(final Object p0);
    
    void clearMappingProcedures();
    
    void setPostActionsId(final String p0);
    
    String getPostActionsId();
    
    Boolean unmarshalPostActions(final Generator p0, final SessionContext p1);
    
    List<IDictionaryElementConfiguration> getSendPrepareErrorsStorage();
    
    void addSendPrepareError(final IDictionaryElementConfiguration p0);
    
    void addSendPrepareErrorFromWrapper(final Object p0);
    
    void clearSendPrepareErrorsStorage();
    
    List<String> getSendPrepareErrors();
    
    List<IDictionaryElementConfiguration> getExchangeErrorCategoriesStorage();
    
    void addExchangeErrorCategory(final IDictionaryElementConfiguration p0);
    
    void addExchangeErrorCategoryFromWrapper(final Object p0);
    
    void clearExchangeErrorCategoriesStorage();
    
    List<String> getExchangeErrorCategories();
}
