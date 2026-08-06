// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.combine;

import java.util.function.Function;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.combine.IItemsStorage;

public abstract class EdoIntegrationExchangeItemsCombiner<S extends IItemsStorage<IEdoIntegrationExchangeItem>> extends ItemsCombiner<IEdoIntegrationExchangeItem, S>
{
    protected EdoIntegrationExchangeItemsCombiner(final String key, final Function<IEdoIntegrationExchangeItem, String> getKeyFunction, final Function<String, S> createStoreFunction) {
        super(key, getKeyFunction, createStoreFunction);
    }
}
