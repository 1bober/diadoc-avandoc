// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing;

import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoCounteragentState
{
    void onProceed(final DMSShardsClientSession p0, final Map<String, Object> p1, final SessionContext p2);
    
    String getCurrentRemoteSystemState();
    
    String getUpcomingRemoteSystemState();
    
    String getUpcomingRemoteState();
    
    String getCurrentRemoteState();
    
    String getCurrentLocalState();
    
    String getCounteragentContainerId();
}
