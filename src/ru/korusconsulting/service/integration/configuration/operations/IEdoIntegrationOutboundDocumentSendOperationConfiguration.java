// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectRule;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;

public interface IEdoIntegrationOutboundDocumentSendOperationConfiguration extends IEdoIntegrationDocumentOperationConfiguration, IEdoIntegrationProcessingConfiguration
{
    IDictionaryElementConfiguration getTitleObjectTypeStorage();
    
    void setTitleObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    List<IDictionaryElementConfiguration> getContentGroups();
    
    void clearContentGroups();
    
    void addContentGroup(final IDictionaryElementConfiguration p0);
    
    String getTargetTypeId();
    
    IDictionaryElementConfiguration getTargetTypeIdStorage();
    
    void setTargetTypeIdStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getCounteragentModeStorage();
    
    void setCounteragentModeStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getSendSignatureTypesStorage();
    
    void addCounteragentSelectRulesFromWrapper(final Object p0);
    
    IDictionaryElementConfiguration getNotInvitedCounteragentSendModeStorage();
    
    void setNotInvitedCounteragentSendModeStorage(final IDictionaryElementConfiguration p0);
    
    void setSendSignatureTypesStorage(final IDictionaryElementConfiguration p0);
    
    void clearSendPrepareErrorProcessing();
    
    void addSendPrepareErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration p0);
    
    void addSendPrepareErrorProcessingFromWrapper(final Object p0);
    
    void clearExchangeErrorProcessing();
    
    void addExchangeErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration p0);
    
    void addExchangeErrorProcessingFromWrapper(final Object p0);
    
    void clearQueuedStatusProcessing();
    
    void addQueuedStatusProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration p0);
    
    void addQueuedStatusProcessingFromWrapper(final Object p0);
    
    void clearSendedStatusProcessing();
    
    void addSendedStatusProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration p0);
    
    void addSendedStatusProcessingFromWrapper(final Object p0);
    
    void clearDefaultErrorProcessing();
    
    void addDefaultErrorProcessing(final IEdoIntegrationInboundDocumentProcessingConfiguration p0);
    
    void addDefaultErrorProcessingFromWrapper(final Object p0);
    
    List<IEdoIntegrationSelectRule> getCounteragentSelectRules();
    
    void clearCounteragentSelectRules();
    
    void addCounteragentSelectRule(final IEdoIntegrationSelectRule p0);
    
    IDictionaryElementConfiguration getContentGroupModeStorage();
    
    String getContentGroupMode();
    
    void setContentGroupModeStorage(final IDictionaryElementConfiguration p0);
    
    List<IDictionaryElementConfiguration> getArchiveContentGroups();
    
    void clearArchiveContentGroups();
    
    void addArchiveContentGroup(final IDictionaryElementConfiguration p0);
    
    boolean isIgnoreCounteragentPkiState();
    
    void setIgnoreCounteragentPkiState(final boolean p0);
    
    boolean isRemoteSignatureMode();
    
    void setRemoteSignatureMode(final boolean p0);
    
    boolean isOnlyLinkedCounteragent();
    
    void setOnlyLinkedCounteragent(final boolean p0);
    
    String getNotInvitedCounteragentSendMode();
    
    IDictionaryElementConfiguration getExchangeCategoryStorage();
    
    void setExchangeCategoryStorage(final IDictionaryElementConfiguration p0);
    
    String getExchangeCategory();
    
    String getStateCategory();
    
    void setStateCategory(final String p0);
    
    String getPreProcessingDocumentArgument();
    
    void setPreProcessingDocumentArgument(final String p0);
}
