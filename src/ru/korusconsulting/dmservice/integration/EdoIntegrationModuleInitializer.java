// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.util.Set;
import ru.korusconsulting.dmservice.objectmodel.ObjectModelService;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationContentSync;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationTokenStore;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItem;
import java.util.HashSet;
import java.util.Map;
import ru.korusconsulting.dmservice.modules.initialize.IModuleInitializer;

public class EdoIntegrationModuleInitializer implements IModuleInitializer
{
    public void init(final Map<String, Object> properties) {
        try {
            final Set<Class<?>> services = new HashSet<Class<?>>();
            services.add(EdoIntegrationExchangeItem.class);
            final Set<Class<?>> types = new HashSet<Class<?>>();
            types.add(EdoIntegrationExchangeItem.class);
            types.add(EdoIntegrationTokenStore.class);
            types.add(EdoIntegrationContentSync.class);
            ObjectModelService.initModelsAspects((Set)types, (Set)services);
        }
        catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
