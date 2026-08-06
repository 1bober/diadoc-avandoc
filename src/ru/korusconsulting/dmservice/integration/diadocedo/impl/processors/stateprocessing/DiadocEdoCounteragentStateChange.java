// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.QuadConsumer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.korusconsulting.service.integration.diadocedo.processors.stateprocessing.IDiadocEdoCounteragentState;

public class DiadocEdoCounteragentStateChange implements IDiadocEdoCounteragentState
{
    @JsonIgnore
    private final String counteragentContainerId;
    private final String counteragentId;
    private final String currentLocalState;
    private final String currentRemoteState;
    private final String currentRemoteSystemState;
    private String upcomingRemoteState;
    private String upcomingRemoteSystemState;
    private final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed;
    private boolean statusIsDirty;
    
    public DiadocEdoCounteragentStateChange(final String counteragentContainerId, final String counteragentId, final String currentLocalState, final String currentRemoteState, final String currentRemoteSystemState, final QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> onProceed) {
        this.counteragentContainerId = counteragentContainerId;
        this.counteragentId = counteragentId;
        this.onProceed = onProceed;
        this.currentLocalState = currentLocalState;
        this.currentRemoteState = currentRemoteState;
        this.currentRemoteSystemState = currentRemoteSystemState;
    }
    
    @Override
    public String getCounteragentContainerId() {
        return this.counteragentContainerId;
    }
    
    public String getCounteragentId() {
        return this.counteragentId;
    }
    
    public QuadConsumer<DMSShardsClientSession, IDiadocEdoCounteragentState, Map<String, Object>, SessionContext> getOnProceed() {
        return this.onProceed;
    }
    
    @Override
    public void onProceed(final DMSShardsClientSession session, final Map<String, Object> attributes, final SessionContext ctx) {
    }
    
    @Override
    public String getCurrentLocalState() {
        return this.currentLocalState;
    }
    
    @Override
    public String getCurrentRemoteState() {
        return this.currentRemoteState;
    }
    
    @Override
    public String getUpcomingRemoteState() {
        return this.upcomingRemoteState;
    }
    
    public boolean isStatusIsDirty() {
        return this.statusIsDirty;
    }
    
    public void setStatusIsDirty(final boolean statusIsDirty) {
        this.statusIsDirty = statusIsDirty;
    }
    
    @Override
    public String getCurrentRemoteSystemState() {
        return this.currentRemoteSystemState;
    }
    
    @Override
    public String getUpcomingRemoteSystemState() {
        return this.upcomingRemoteSystemState;
    }
}
