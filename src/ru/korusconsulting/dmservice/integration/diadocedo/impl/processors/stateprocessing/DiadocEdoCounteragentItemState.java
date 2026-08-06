// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import java.util.Iterator;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationLogRecord;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;

public class DiadocEdoCounteragentItemState
{
    private final String itemObjectId;
    @JsonIgnore
    private final IEdoIntegrationExchangeItem item;
    private final String counteragentId;
    private final String counteragentName;
    private final String counteragentInn;
    private final String counteragentKpp;
    private final String counteragentContainerId;
    private final String messageId;
    private String cumulitiveStatus;
    private final Map<String, DiadocEdoContentItemState> contentItemsById;
    @JsonIgnore
    private final Map<String, DiadocEdoContentItemState> contentItemsByEntity;
    private String localStatus;
    private String localState;
    private final String currentRemoteState;
    private final String currentRemoteStateMessage;
    private final String currentRemoteStateSystemCode;
    private final String currentRemoteStateDisplay;
    private String currentRemoteStateProcessingStep;
    private boolean statusChanged;
    
    public DiadocEdoCounteragentItemState(final IEdoIntegrationExchangeItem item) {
        this.contentItemsById = new HashMap<String, DiadocEdoContentItemState>();
        this.contentItemsByEntity = new HashMap<String, DiadocEdoContentItemState>();
        this.item = item;
        this.itemObjectId = item.getObjectId();
        this.counteragentId = item.getCounteragentId();
        this.counteragentName = item.getCounteragentName();
        this.counteragentInn = item.getCounteragentInn();
        this.counteragentKpp = item.getCounteragentKpp();
        this.counteragentContainerId = item.getCounteragentContainerId();
        this.messageId = item.getMessageEdoId();
        this.currentRemoteState = item.getRemoteState();
        this.currentRemoteStateMessage = item.getRemoteStateMessage();
        this.currentRemoteStateProcessingStep = item.getRemoteStateProcessingStep();
        this.currentRemoteStateSystemCode = item.getRemoteStateSystemCode();
        this.currentRemoteStateDisplay = item.getRemoteStateDisplay();
    }
    
    public void parseExchangeItem(final IEdoIntegrationExchangeItem item) {
        this.setCumulitiveStatus(item.getState());
        if (StringUtils.isNotBlank(item.getContentUid())) {
            final DiadocEdoContentItemState di = new DiadocEdoContentItemState(item);
            this.getContentItemsById().put(item.getContentUid(), di);
            if (StringUtils.isNotBlank(item.getDocumentEdoId())) {
                this.getContentItemsByEntity().put(item.getDocumentEdoId(), di);
            }
        }
        else {
            this.setLocalState(item.getLocalState());
            this.setLocalStatus(item.getLocalStateMessage());
        }
    }
    
    public Map<String, DiadocEdoContentItemState> getContentItemsById() {
        return this.contentItemsById;
    }
    
    public Map<String, DiadocEdoContentItemState> getContentItemsByEntity() {
        return this.contentItemsByEntity;
    }
    
    public DiadocEdoContentItemState getContentItemByEntity(final String entityId) {
        return this.getContentItemsByEntity().get(entityId);
    }
    
    public String getCumulitiveStatus() {
        return this.cumulitiveStatus;
    }
    
    public void setCumulitiveStatus(final String cumulitiveState) {
        this.cumulitiveStatus = cumulitiveState;
    }
    
    public String getCounteragentId() {
        return this.counteragentId;
    }
    
    public String getCounteragentName() {
        return this.counteragentName;
    }
    
    public String getCounteragentContainerId() {
        return this.counteragentContainerId;
    }
    
    public String getLocalState() {
        return this.localState;
    }
    
    public void setLocalState(final String value) {
        this.localState = value;
    }
    
    public String getLocalStatus() {
        return this.localStatus;
    }
    
    public void setLocalStatus(final String value) {
        this.localStatus = value;
    }
    
    @Override
    public String toString() {
        return "DiadocEdoCounteragentItemState\n [counteragentId=" + this.counteragentId + ",\n counteragentContainerId=" + this.counteragentContainerId + ",\n messageId=" + this.messageId + ",\n cumulitiveState=" + this.cumulitiveStatus + """
                                                                                                                                                                                                                                                ,
                                                                                                                                                                                                                                                 contentItemsById:
                                                                                                                                                                                                                                                """ + (String)this.contentItemsById.entrySet().stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, String>)Collectors.joining("\n")) + """
                                                                                                                                                                                                                                                                                                                                                                                                                                     ,
                                                                                                                                                                                                                                                                                                                                                                                                                                      contentItemsByEntity:
                                                                                                                                                                                                                                                                                                                                                                                                                                     """ + (String)this.contentItemsByEntity.entrySet().stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, String>)Collectors.joining("\n"));
    }
    
    public String getItemObjectId() {
        return this.itemObjectId;
    }
    
    public IEdoIntegrationExchangeItem getItem() {
        return this.item;
    }
    
    public boolean checkChanges(final BiConsumer<DiadocEdoContentItemState, Consumer<IEdoIntegrationLogRecord>> onChangeContentState, final BiConsumer<DiadocEdoCounteragentItemState, Consumer<IEdoIntegrationLogRecord>> onChangeCounteragentState) {
        boolean statusedSame = true;
        boolean hasChangedL = false;
        String upcomingRemoteStateMessage = null;
        final String upcomingRemoteSystemState = null;
        for (final DiadocEdoContentItemState contentItem : this.getContentItemsById().values()) {
            if (contentItem.hasStateChanged()) {
                if (upcomingRemoteStateMessage == null) {
                    upcomingRemoteStateMessage = contentItem.getUpcomingRemoteState();
                    hasChangedL = true;
                }
                else {
                    if (!upcomingRemoteStateMessage.equals(contentItem.getUpcomingRemoteState())) {
                        statusedSame = false;
                        break;
                    }
                    hasChangedL = true;
                }
                if (onChangeContentState == null) {
                    continue;
                }
                onChangeContentState.accept(contentItem, c -> {
                    if (this.getItem() != null) {
                        c.setDocumentId(this.getItem().getDocumentId());
                        c.setDocumentName(this.getItem().getDocumentName());
                        c.setContentId(this.getItem().getContentUid());
                        c.setContentName(this.getItem().getContentName());
                        c.setEdoMessageId(this.getItem().getMessageEdoId());
                        c.setEdoDocumentId(this.getItem().getDocumentEdoId());
                        c.setFromEdoBox(this.getItem().getFromBox());
                        c.setFromEdoDepartment(this.getItem().getFromDepartment());
                        c.setFromInn(this.getItem().getFromInn());
                        c.setFromKpp(this.getItem().getFromKpp());
                        c.setToEdoBox(this.getItem().getToBox());
                        c.setToEdoDepartment(this.getItem().getToDepartment());
                        c.setToInn(this.getItem().getToInn());
                        c.setToKpp(this.getItem().getToKpp());
                    }
                    c.setContainerId(this.getCounteragentContainerId());
                    c.setCounteragentId(this.getCounteragentId());
                    c.setCounteragentName(this.getCounteragentName());
                    c.setRemoteState(contentItem.getUpcomingRemoteState());
                    return;
                });
            }
            else if (upcomingRemoteStateMessage == null) {
                upcomingRemoteStateMessage = contentItem.getCurrentRemoteState();
            }
            else {
                if (!upcomingRemoteStateMessage.equals(contentItem.getCurrentRemoteState())) {
                    statusedSame = false;
                    break;
                }
                continue;
            }
        }
        final String newResponseStatusF = upcomingRemoteStateMessage;
        if (hasChangedL && statusedSame && onChangeCounteragentState != null) {
            onChangeCounteragentState.accept(this, c -> {
                if (this.getItem() != null) {
                    c.setDocumentId(this.getItem().getDocumentId());
                    c.setDocumentName(this.getItem().getDocumentName());
                    c.setContentId(this.getItem().getContentUid());
                    c.setContentName(this.getItem().getContentName());
                    c.setEdoMessageId(this.getItem().getMessageEdoId());
                    c.setEdoDocumentId(this.getItem().getDocumentEdoId());
                    c.setFromEdoBox(this.getItem().getFromBox());
                    c.setFromEdoDepartment(this.getItem().getFromDepartment());
                    c.setFromInn(this.getItem().getFromInn());
                    c.setFromKpp(this.getItem().getFromKpp());
                    c.setToEdoBox(this.getItem().getToBox());
                    c.setToEdoDepartment(this.getItem().getToDepartment());
                    c.setToInn(this.getItem().getToInn());
                    c.setToKpp(this.getItem().getToKpp());
                }
                c.setContainerId(this.getCounteragentContainerId());
                c.setCounteragentId(this.getCounteragentId());
                c.setCounteragentName(this.getCounteragentName());
                c.setRemoteState(newResponseStatusF);
                return;
            });
        }
        this.setStatusChanged(hasChangedL && statusedSame);
        this.setCumulitiveStatus(statusedSame ? upcomingRemoteStateMessage : null);
        return this.isStatusChanged();
    }
    
    public boolean isStatusChanged() {
        return this.statusChanged;
    }
    
    public void setStatusChanged(final boolean statusChanged) {
        this.statusChanged = statusChanged;
    }
    
    public String getCurrentRemoteStateProcessingStep() {
        return this.currentRemoteStateProcessingStep;
    }
    
    public void setCurrentRemoteStateProceed(final String value) {
        this.currentRemoteStateProcessingStep = value;
    }
    
    public String getCurrentRemoteState() {
        return this.currentRemoteState;
    }
    
    public String getCurrentRemoteStateMessage() {
        return this.currentRemoteStateMessage;
    }
    
    public String getCurrentRemoteStateSystemCode() {
        return this.currentRemoteStateSystemCode;
    }
    
    public String getCurrentRemoteStateDisplay() {
        return this.currentRemoteStateDisplay;
    }
    
    public String getCounteragentInn() {
        return this.counteragentInn;
    }
    
    public String getCounteragentKpp() {
        return this.counteragentKpp;
    }
}
