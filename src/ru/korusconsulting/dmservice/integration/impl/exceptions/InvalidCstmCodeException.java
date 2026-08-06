// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.exceptions;

public class InvalidCstmCodeException extends Exception
{
    private static final long serialVersionUID = 980519334531806138L;
    
    public InvalidCstmCodeException() {
    }
    
    public InvalidCstmCodeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public InvalidCstmCodeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public InvalidCstmCodeException(final String message) {
        super(message);
    }
    
    public InvalidCstmCodeException(final Throwable cause) {
        super(cause);
    }
}
