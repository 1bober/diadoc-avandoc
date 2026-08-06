// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoGetRemoteStateProcessor;
import java.util.ArrayList;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.QuadConsumer;
import ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing.IDiadocEdoCounteragentState;
import java.util.List;
import ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing.IDiadocEdoDocumentStateChange;

public class DiadocEdoDocumentStateError implements IDiadocEdoDocumentStateChange
{
    private final String documentId;
    private final List<IDiadocEdoCounteragentState> counteragents;
    
    public DiadocEdoDocumentStateError(final String documentId, final String counteragentContainerId, final String counteragentId, final String localState, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed) {
        this.counteragents = new ArrayList<IDiadocEdoCounteragentState>();
        this.documentId = documentId;
        this.addCounteragent(counteragentContainerId, counteragentId, localState, null, null, onProceed);
    }
    
    @Override
    public String getDocumentId() {
        return this.documentId;
    }
    
    @Override
    public List<IDiadocEdoCounteragentState> getCounteragents() {
        return this.counteragents;
    }
    
    @Override
    public DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE getType() {
        return DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE.error;
    }
    
    @Override
    public void addCounteragent(final String counteragentContainerId, final String counteragentId, final String localState, final String remoteState, final String remoteSystemStateCode, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed) {
        this.getCounteragents().add(new DiadocEdoCounteragentStateChange(counteragentContainerId, counteragentId, localState, remoteState, remoteSystemStateCode, onProceed));
    }
}
