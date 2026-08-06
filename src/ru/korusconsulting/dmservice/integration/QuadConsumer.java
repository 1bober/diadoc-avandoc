// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<S, T, U, V>
{
    void accept(final S p0, final T p1, final U p2, final V p3);
    
    default QuadConsumer<S, T, U, V> andThen(final QuadConsumer<? super S, ? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);
        return (l, n, r, v) -> {
            this.accept(l, n, r, v);
            after.accept(l, n, r, v);
        };
    }
}
