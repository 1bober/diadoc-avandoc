// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.function.Supplier;

@FunctionalInterface
interface FailableSupplier<R, E extends Exception>
{
    R get() throws E, Exception;
    
    default <R, E extends Exception> Supplier<R> failable(final FailableSupplier<R, E> supplier) throws E, Exception {
        return (Supplier<R>)(() -> {
            try {
                return supplier.get();
            }
            catch (final Exception exception) {
                ThrowExceptionUtil.throwActualException(exception);
                return null;
            }
        });
    }
    
    default <R, E extends Exception> R wrap(final FailableSupplier<R, E> supplier) {
        try {
            return supplier.get();
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
}
