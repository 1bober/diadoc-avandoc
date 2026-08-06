// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

public class NamedParam<R extends Enum<R>, T>
{
    private final R key;
    private final T value;
    
    public NamedParam(final R key, final T value) {
        this.key = key;
        this.value = value;
    }
    
    public R getKey() {
        return this.key;
    }
    
    public String getKeyAsString() {
        return (this.key == null) ? "" : this.key.name();
    }
    
    public T getValue() {
        return this.value;
    }
}
