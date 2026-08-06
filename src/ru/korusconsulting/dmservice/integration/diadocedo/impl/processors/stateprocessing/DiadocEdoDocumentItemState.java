// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.stateprocessing;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.processors.DiadocEdoGetRemoteStateProcessor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

public class DiadocEdoDocumentItemState
{
    private final String itemObjectId;
    private final Map<String, DiadocEdoCounteragentItemState> counteragentItems;
    @JsonIgnore
    private final Map<String, DiadocEdoCounteragentItemState> counteragentItemsByMessageId;
    private final String documentId;
    private final String filialId;
    private final String filialName;
    private final String diadocBoxId;
    private DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE changeType;
    
    public DiadocEdoDocumentItemState(final IEdoIntegrationExchangeItem item) {
        this.counteragentItems = new HashMap<String, DiadocEdoCounteragentItemState>();
        this.counteragentItemsByMessageId = new HashMap<String, DiadocEdoCounteragentItemState>();
        this.changeType = DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE.none;
        this.diadocBoxId = item.getFromBox();
        this.itemObjectId = item.getObjectId();
        this.documentId = item.getDocumentId();
        this.filialId = item.getFilialId();
        this.filialName = item.getFilialName();
    }
    
    public Map<String, DiadocEdoCounteragentItemState> getCounteragentItems() {
        return this.counteragentItems;
    }
    
    public DiadocEdoCounteragentItemState getCounteragentItemByMessageId(final String messageId) {
        return this.counteragentItemsByMessageId.get(messageId);
    }
    
    public String getDocumentId() {
        return this.documentId;
    }
    
    public void parseExchangeItem(final IEdoIntegrationExchangeItem item) {
        final DiadocEdoCounteragentItemState newItem = this.getCounteragentItems().computeIfAbsent(item.getCounteragentId(), a -> new DiadocEdoCounteragentItemState(item));
        newItem.parseExchangeItem(item);
        if (StringUtils.isNotBlank(item.getMessageEdoId())) {
            this.getCounteragentItemsByMessageId().put(item.getMessageEdoId(), newItem);
        }
    }
    
    public String getItemObjectId() {
        return this.itemObjectId;
    }
    
    public String getFilialId() {
        return this.filialId;
    }
    
    public String getFilialName() {
        return this.filialName;
    }
    
    public String getDiadocBoxId() {
        return this.diadocBoxId;
    }
    
    public Map<String, DiadocEdoCounteragentItemState> getCounteragentItemsByMessageId() {
        return this.counteragentItemsByMessageId;
    }
    
    @Override
    public String toString() {
        return """
               DiadocEdoDocumentItemState
               [counteragentItems
               """ + (String)this.counteragentItems.entrySet().stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, String>)Collectors.joining("\n")) + ",\n documentId=" + this.documentId;
    }
    
    public DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE getChangeType() {
        return this.changeType;
    }
    
    public void setChangeType(final DiadocEdoGetRemoteStateProcessor.CHANGE_TYPE changeType) {
        this.changeType = changeType;
    }
}
