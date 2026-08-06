// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.combine;

import java.util.Collection;

public interface IItemsStorage<R>
{
    void put(final R p0);
    
    String getKey();
    
    Collection<R> getAllItems();
}
