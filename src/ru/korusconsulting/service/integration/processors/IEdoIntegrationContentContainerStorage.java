// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.processors;

import java.util.Collection;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationContentContainerStorage;

public interface IEdoIntegrationContentContainerStorage
{
    Collection<IEdoIntegrationContentContainer> getContainers(final EdoIntegrationContentContainerStorage.EdoIntegrationContentType p0);
    
    Collection<IEdoIntegrationContentContainer> getContainers();
    
    void addAll(final IEdoIntegrationContentContainerStorage p0);
    
    boolean isEmpty();
}
