// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.batchselect;

import java.util.Collection;

public interface IBatchItemsStorage<K, S, T>
{
    boolean isEmpty();
    
    void clear();
    
    int size();
    
    void put(final K p0, final T p1);
    
    Collection<String> getKeys();
    
    S getItems(final String p0);
    
    Collection<S> getItems();
}
