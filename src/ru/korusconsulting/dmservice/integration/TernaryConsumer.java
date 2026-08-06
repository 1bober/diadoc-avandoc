// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.util.Objects;

@FunctionalInterface
public interface TernaryConsumer<S, T, U>
{
    void accept(final S p0, final T p1, final U p2);
    
    default TernaryConsumer<S, T, U> andThen(final TernaryConsumer<? super S, ? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, n, r) -> {
            this.accept(l, n, r);
            after.accept(l, n, r);
        };
    }
}
