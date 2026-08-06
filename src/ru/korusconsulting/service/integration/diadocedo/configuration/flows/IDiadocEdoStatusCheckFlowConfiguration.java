// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.flows;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationStatusCheckFlowConfiguration;

public interface IDiadocEdoStatusCheckFlowConfiguration extends IEdoIntegrationStatusCheckFlowConfiguration
{
    IDictionaryElementConfiguration getContentEventStorage();
    
    void setContentEventStorage(final IDictionaryElementConfiguration p0);
    
    String getContentEvent();
}
