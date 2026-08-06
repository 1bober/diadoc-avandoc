// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.model;

import java.util.function.Function;
import java.util.function.Consumer;

public class AttrLoader<T>
{
    private final Consumer<String> setter;
    private final Function<T, String> getter;
    
    public AttrLoader(final Consumer<String> setter, final Function<T, String> getter) {
        this.setter = setter;
        this.getter = getter;
    }
    
    public Consumer<String> getSetter() {
        return this.setter;
    }
    
    public Function<T, String> getGetter() {
        return this.getter;
    }
}
