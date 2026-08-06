// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.util.Objects;

@FunctionalInterface
public interface TripleConsumer<S, T, U>
{
    void accept(final S p0, final T p1, final U p2);
    
    default TripleConsumer<S, T, U> andThen(final TripleConsumer<? super S, ? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, n, r) -> {
            this.accept(l, n, r);
            after.accept(l, n, r);
        };
    }
}
