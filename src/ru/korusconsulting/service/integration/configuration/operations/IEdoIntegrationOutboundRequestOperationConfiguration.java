// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;

public interface IEdoIntegrationOutboundRequestOperationConfiguration extends IEdoIntegrationOperationConfiguration, IEdoIntegrationProcessingConfiguration
{
    IDictionaryElementConfiguration getTitleObjectTypeStorage();
    
    void setTitleObjectTypeStorage(final IDictionaryElementConfiguration p0);
    
    void setStateCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getStateCategoryStorage();
    
    String getStateCategory();
    
    void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent p0);
    
    List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents();
    
    void clearProcessorEvents();
    
    void addProcessorEventFromWrapper(final Object p0);
    
    String getPostActionsId();
    
    void setPostActionsId(final String p0);
    
    Boolean unmarshalPostActions(final Generator p0, final SessionContext p1);
}
