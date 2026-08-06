// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.status;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationDocumentRemoteStatus extends ICstmConfigAdapter
{
    String getResponseStatus();
    
    void setResponseStatus(final String p0);
    
    void setStatusCode(final String p0);
    
    String getStatusCode();
}
