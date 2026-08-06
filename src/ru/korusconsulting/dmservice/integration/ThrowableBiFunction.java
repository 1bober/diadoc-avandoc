// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, S extends Exception>
{
    R apply(final T p0, final U p1) throws S, Exception;
}
