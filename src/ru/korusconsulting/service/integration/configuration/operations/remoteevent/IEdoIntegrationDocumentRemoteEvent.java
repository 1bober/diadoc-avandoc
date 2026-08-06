// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations.remoteevent;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationDocumentRemoteEvent extends ICstmConfigAdapter
{
    String getRemoteEvent();
    
    void setRemoteEvent(final String p0);
    
    void setRemoteEventCode(final String p0);
    
    String getRemoteEventCode();
    
    String getRemoteEventAction();
    
    void setRemoteEventAction(final String p0);
}
