// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.combine;

import java.util.Collection;
import java.util.function.Function;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByCounteragentItem;
import ru.korusconsulting.dmservice.integration.combine.EdoIntegrationExchangeItemsCombiner;

public class DiadocEdoItemsCombinerByCounteragentContainerId extends EdoIntegrationExchangeItemsCombiner<DiadocEdoItemsCombinerByContentId> implements IDiadocEdoCombinedByCounteragentItem
{
    public DiadocEdoItemsCombinerByCounteragentContainerId(final String counteragentContainerId) {
        super(counteragentContainerId, (Function)IEdoIntegrationExchangeItem::getContentId, (Function)DiadocEdoItemsCombinerByContentId::new);
    }
    
    public Collection<IEdoIntegrationExchangeItem> getItems() {
        return this.getChildren().values().stream().map(a -> a.get()).toList();
    }
    
    public Collection<IEdoIntegrationExchangeItem> getAllItems() {
        return this.getItems();
    }
}
