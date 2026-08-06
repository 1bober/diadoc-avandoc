// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

class ThrowExceptionUtil
{
    private ThrowExceptionUtil() {
    }
    
    static <E extends Exception> void throwActualException(final Exception exception) throws E, Exception {
        throw exception;
    }
}
