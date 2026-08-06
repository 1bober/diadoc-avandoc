// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoGetRemoteStateProcessor;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.QuadConsumer;

public interface IDiadocEdoDocumentStateChange
{
    void addCounteragent(final String p0, final String p1, final String p2, final String p3, final String p4, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> p5);
    
    String getDocumentId();
    
    List<IDiadocEdoCounteragentState> getCounteragents();
    
    DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE getType();
}
