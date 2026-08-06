// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.batchselect;

public interface IBatchItem<T>
{
    String getKey();
    
    T getItem();
}
