// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationIncomingDocumentsFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRequestFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundRevokeFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationInboundRevokeFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationStatusCheckFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationCounteragentFlowConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationOutboundFlowConfiguration;

public interface IEdoIntegrationServiceConfiguration extends ICstmConfigAdapter
{
    IEdoIntegrationOutboundFlowConfiguration getOutboundFlowConfiguration();
    
    IEdoIntegrationCounteragentFlowConfiguration getCounteragentFlowConfiguration();
    
    IEdoIntegrationStatusCheckFlowConfiguration getStatusFlowConfiguration();
    
    IEdoIntegrationInboundRevokeFlowConfiguration getInboundRevokeConfiguration();
    
    IEdoIntegrationOutboundRevokeFlowConfiguration getOutboundRevokeConfiguration();
    
    IEdoIntegrationOutboundRequestFlowConfiguration getOutboundRequestConfiguration();
    
    IEdoIntegrationIncomingDocumentsFlowConfiguration getIncomingDocumentConfiguration();
    
    IEdoIntegrationRemoteEventFlowConfiguration getRemoteEventConfiguration();
    
    List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses();
    
    void addDocflowLinkFromWrapper(final Object p0);
    
    List<IEdoIntegrationFlowConfiguration> getDocflowLinks();
    
    void clearDocflowLinks();
    
    void addDocflowLink(final IEdoIntegrationFlowConfiguration p0);
    
    List<IDictionaryElementConfiguration> getExchangeCategories();
    
    void clearExchangeCategories();
    
    void addExchangeCategory(final IDictionaryElementConfiguration p0);
    
    void addExchangeCategoryFromWrapper(final Object p0);
    
    List<IDictionaryElementConfiguration> getExchangeErrorCategories();
    
    void clearExchangeErrorCategories();
    
    void addExchangeErrorCategory(final IDictionaryElementConfiguration p0);
    
    void addExchangeErrorCategoryFromWrapper(final Object p0);
}
