// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.batchselect;

import java.util.Collection;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;
import ru.korusconsulting.service.integration.batchselect.IBatchItemsStorage;
import ru.korusconsulting.service.integration.combine.IItemsStorage;

public abstract class BatchItemsStorage<S extends IItemsStorage<R>, R> implements IBatchItemsStorage<String, S, R>
{
    private final Map<String, S> storage;
    
    public BatchItemsStorage() {
        this.storage = new HashMap<String, S>();
    }
    
    @Override
    public boolean isEmpty() {
        return this.getStorage().isEmpty();
    }
    
    @Override
    public void clear() {
        this.getStorage().clear();
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public void put(final String key, final R item) {
        this.getStorage().computeIfAbsent(key, (Function<? super String, ? extends S>)this::createSubstorage).put(item);
    }
    
    protected Map<String, S> getStorage() {
        return this.storage;
    }
    
    @Override
    public S getItems(final String key) {
        return this.getStorage().get(key);
    }
    
    @Override
    public Collection<S> getItems() {
        return this.getStorage().values();
    }
    
    @Override
    public Collection<String> getKeys() {
        return this.getStorage().keySet();
    }
    
    protected abstract S createSubstorage(final String p0);
}
