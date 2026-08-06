// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.combine;

import java.util.Collection;

public interface ICombinedItems<T>
{
    String getKey();
    
    Collection<T> getItems();
    
    boolean isEmpty();
}
