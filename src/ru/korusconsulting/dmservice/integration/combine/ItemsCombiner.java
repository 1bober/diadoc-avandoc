// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.combine;

import java.util.HashMap;
import java.util.function.Function;
import java.util.Map;
import ru.korusconsulting.service.integration.combine.IItemsCombiner;
import ru.korusconsulting.service.integration.combine.IItemsStorage;

public abstract class ItemsCombiner<R, S extends IItemsStorage<R>> implements IItemsCombiner<R>
{
    private final String key;
    private boolean isEmpty;
    private final Map<String, S> children;
    private final Function<R, String> getKeyFunction;
    private final Function<String, S> createStoreFunction;
    
    protected ItemsCombiner(final String key, final Function<R, String> getChildKeyFunction, final Function<String, S> createStoreFunction) {
        this.isEmpty = true;
        this.children = new HashMap<String, S>();
        this.key = key;
        this.getKeyFunction = getChildKeyFunction;
        this.createStoreFunction = createStoreFunction;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    @Override
    public final boolean isEmpty() {
        return this.isEmpty;
    }
    
    @Override
    public void clear() {
        this.getChildren().clear();
        this.isEmpty = true;
    }
    
    public Map<String, S> getChildren() {
        return this.children;
    }
    
    @Override
    public int size() {
        return this.getChildren().size();
    }
    
    @Override
    public void put(final R item) {
        this.getChildren().computeIfAbsent((String)this.getKeyFunction().apply(item), a -> this.getCreateStoreFunction().apply(a)).put(item);
    }
    
    protected Function<R, String> getKeyFunction() {
        return this.getKeyFunction;
    }
    
    protected Function<String, S> getCreateStoreFunction() {
        return this.createStoreFunction;
    }
}
