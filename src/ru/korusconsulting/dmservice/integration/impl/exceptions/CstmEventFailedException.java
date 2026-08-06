// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.exceptions;

import ru.korusconsulting.service.commons.SessionContext;

public class CstmEventFailedException extends Exception
{
    private static final long serialVersionUID = 980519334531806138L;
    private final SessionContext throwsContext;
    
    public CstmEventFailedException(final SessionContext throwsContext) {
        this.throwsContext = throwsContext;
    }
    
    public CstmEventFailedException(final String message, final SessionContext throwsContext) {
        super(message);
        this.throwsContext = throwsContext;
    }
    
    public SessionContext getThrowsContext() {
        return this.throwsContext;
    }
}
