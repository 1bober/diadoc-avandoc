// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainer;
import java.util.Collection;
import java.util.Map;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainerStorage;

public class EdoIntegrationContentContainerStorage implements IEdoIntegrationContentContainerStorage
{
    Map<EdoIntegrationContentType, Collection<IEdoIntegrationContentContainer>> containers;
    
    public EdoIntegrationContentContainerStorage() {
        this.containers = new LinkedHashMap<EdoIntegrationContentType, Collection<IEdoIntegrationContentContainer>>();
    }
    
    @Override
    public Collection<IEdoIntegrationContentContainer> getContainers(final EdoIntegrationContentType type) {
        return this.containers.computeIfAbsent(type, a -> new ArrayList());
    }
    
    @Override
    public Collection<IEdoIntegrationContentContainer> getContainers() {
        final List<IEdoIntegrationContentContainer> result = new ArrayList<IEdoIntegrationContentContainer>();
        this.containers.values().forEach(a -> {
            if (a != null) {
                result.addAll(a);
            }
            return;
        });
        return result;
    }
    
    @Override
    public void addAll(final IEdoIntegrationContentContainerStorage srcStorage) {
        for (final EdoIntegrationContentType a : EdoIntegrationContentType.values()) {
            final Collection<IEdoIntegrationContentContainer> b = srcStorage.getContainers(a);
            if (b != null && !b.isEmpty()) {
                this.getContainers(a).addAll(b);
            }
        }
    }
    
    @Override
    public boolean isEmpty() {
        for (final Collection<IEdoIntegrationContentContainer> a : this.containers.values()) {
            if (!a.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public enum EdoIntegrationContentType
    {
        simple, 
        archived;
    }
}
