// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;

public class DiadocEdoContentItemState
{
    @JsonIgnore
    private final IEdoIntegrationExchangeItem item;
    private final String itemObjectId;
    private final String avandocId;
    private final String messageId;
    private final String entityId;
    private final String currentLocalState;
    private final String currentLocalStateMessage;
    private final String currentRemoteState;
    private final String currentRemoteStateSystemCode;
    private final String currentRemoteStateMessage;
    private final String currentRemoteStateDisplay;
    private String currentRemoteStateProcessingStep;
    private String upcomingRemoteSeverity;
    private String upcomingRemoteState;
    private String upcomingRemoteStateMessage;
    private String upcomingRemoteStateSystemCode;
    private String upcomingRemoteStateDisplay;
    private String upcomingSeverity;
    
    public DiadocEdoContentItemState(final IEdoIntegrationExchangeItem item) {
        this.item = item;
        this.itemObjectId = item.getObjectId();
        this.avandocId = item.getContentUid();
        this.messageId = item.getMessageEdoId();
        this.entityId = item.getDocumentEdoId();
        this.currentRemoteState = item.getRemoteState();
        this.currentRemoteStateMessage = item.getRemoteStateMessage();
        this.currentRemoteStateProcessingStep = item.getRemoteStateProcessingStep();
        this.currentRemoteStateSystemCode = item.getRemoteStateSystemCode();
        this.currentRemoteStateDisplay = item.getRemoteStateDisplay();
        this.currentLocalState = item.getLocalState();
        this.currentLocalStateMessage = item.getLocalStateMessage();
    }
    
    public String getAvandocId() {
        return this.avandocId;
    }
    
    public String getUpcomingRemoteState() {
        return this.upcomingRemoteState;
    }
    
    public void setUpcomingRemoteState(final String value) {
        this.upcomingRemoteState = value;
    }
    
    public String getEntityId() {
        return this.entityId;
    }
    
    public String getCurrentRemoteState() {
        return this.currentRemoteState;
    }
    
    public boolean hasStateChanged() {
        return !StringUtils.equals(this.getUpcomingRemoteStateMessage(), this.getCurrentRemoteStateMessage()) || !StringUtils.equals(this.getUpcomingRemoteStateSystemCode(), this.getCurrentRemoteStateSystemCode());
    }
    
    public String getCurrentLocalState() {
        return this.currentLocalState;
    }
    
    public String getCurrentLocalStateMessage() {
        return this.currentLocalStateMessage;
    }
    
    public String getItemObjectId() {
        return this.itemObjectId;
    }
    
    public IEdoIntegrationExchangeItem getItem() {
        return this.item;
    }
    
    public String getUpcomingRemoteStateSystemCode() {
        return this.upcomingRemoteStateSystemCode;
    }
    
    public void setUpcomingRemoteStateSystemCode(final String value) {
        this.upcomingRemoteStateSystemCode = value;
    }
    
    public String getUpcomingRemoteStateMessage() {
        return this.upcomingRemoteStateMessage;
    }
    
    public void setUpcomingRemoteStateMessage(final String value) {
        this.upcomingRemoteStateMessage = value;
    }
    
    public String getUpcomingRemoteStateDisplay() {
        return this.upcomingRemoteStateDisplay;
    }
    
    public void setUpcomingRemoteStateDisplay(final String value) {
        this.upcomingRemoteStateDisplay = value;
    }
    
    public String getCurrentRemoteStateSystemCode() {
        return this.currentRemoteStateSystemCode;
    }
    
    public String getCurrentRemoteStateMessage() {
        return this.currentRemoteStateMessage;
    }
    
    public String getUpcomingRemoteSeverity() {
        return this.upcomingRemoteSeverity;
    }
    
    public void setUpcomingRemoteSeverity(final String value) {
        this.upcomingRemoteSeverity = value;
    }
    
    public String getUpcomingSeverity() {
        return this.upcomingSeverity;
    }
    
    public void setUpcomingSeverity(final String upcomingSeverity) {
        this.upcomingSeverity = upcomingSeverity;
    }
    
    public String getCurrentRemoteStateProcessingStep() {
        return this.currentRemoteStateProcessingStep;
    }
    
    public void setCurrentRemoteStateProcessingStep(final String value) {
        this.currentRemoteStateProcessingStep = value;
    }
    
    public String getCurrentRemoteStateDisplay() {
        return this.currentRemoteStateDisplay;
    }
    
    public String getMessageId() {
        return this.messageId;
    }
}
