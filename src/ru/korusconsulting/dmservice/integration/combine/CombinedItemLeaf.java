// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.combine;

import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import ru.korusconsulting.service.integration.combine.IItemsStorage;

public class CombinedItemLeaf<R> implements IItemsStorage<R>
{
    private final String key;
    private R item;
    
    public CombinedItemLeaf(final String key) {
        this.key = key;
    }
    
    @Override
    public void put(final R item) {
        this.item = item;
    }
    
    public R get() {
        return this.item;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    @Override
    public Collection<R> getAllItems() {
        return (Collection<R>)((this.item == null) ? Collections.emptyList() : Arrays.asList(this.item));
    }
}
