// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.combine;

public interface IItemsCombiner<R> extends IItemsStorage<R>
{
    boolean isEmpty();
    
    void clear();
    
    int size();
}
