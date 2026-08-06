// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.QuadConsumer;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing.IDiadocEdoCounteragentState;
import java.util.List;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoGetRemoteStateProcessor;
import ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing.IDiadocEdoDocumentStateChange;

public class DiadocEdoDocumentStateChange implements IDiadocEdoDocumentStateChange
{
    private final DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE type;
    private final String documentId;
    private final List<IDiadocEdoCounteragentState> counteragents;
    
    public DiadocEdoDocumentStateChange(final DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE type, final String documentId) {
        this.counteragents = new ArrayList<IDiadocEdoCounteragentState>();
        this.type = type;
        this.documentId = documentId;
    }
    
    public DiadocEdoDocumentStateChange(final DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE type, final String documentId, final String counteragentContainerId, final String counteragentId, final String localState, final String remoteState, final String remoteSystemStateCode, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed) {
        this.counteragents = new ArrayList<IDiadocEdoCounteragentState>();
        this.type = type;
        this.documentId = documentId;
        this.addCounteragent(counteragentContainerId, counteragentId, localState, remoteState, remoteSystemStateCode, onProceed);
    }
    
    @Override
    public DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE getType() {
        return this.type;
    }
    
    @Override
    public String getDocumentId() {
        return this.documentId;
    }
    
    @Override
    public void addCounteragent(final String counteragentContainerId, final String counteragentId, final String localState, final String remoteState, final String remoteSystemStateCode, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed) {
        this.getCounteragents().add(new DiadocEdoCounteragentStateChange(counteragentContainerId, counteragentId, localState, remoteState, remoteSystemStateCode, onProceed));
    }
    
    @Override
    public List<IDiadocEdoCounteragentState> getCounteragents() {
        return this.counteragents;
    }
}
