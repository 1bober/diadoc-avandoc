// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.status;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationDocumentLocalStatus extends ICstmConfigAdapter
{
    String getStatusCode();
    
    void setStatusCode(final String p0);
}
