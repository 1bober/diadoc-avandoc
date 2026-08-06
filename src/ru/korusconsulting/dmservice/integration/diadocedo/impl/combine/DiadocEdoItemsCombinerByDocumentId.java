// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.combine;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByCounteragentItem;
import ru.korusconsulting.dmservice.integration.combine.EdoIntegrationExchangeItemsCombiner;

public class DiadocEdoItemsCombinerByDocumentId extends EdoIntegrationExchangeItemsCombiner<IDiadocEdoCombinedByCounteragentItem> implements IDiadocEdoCombinedByDocumentItem
{
    public DiadocEdoItemsCombinerByDocumentId(final String documentId) {
        super(documentId, (Function)IEdoIntegrationExchangeItem::getCounteragentContainerId, (Function)DiadocEdoItemsCombinerByCounteragentContainerId::new);
    }
    
    public Collection<IDiadocEdoCombinedByCounteragentItem> getItems() {
        return this.getChildren().values();
    }
    
    public Collection<IEdoIntegrationExchangeItem> getAllItems() {
        final List<IEdoIntegrationExchangeItem> b = new ArrayList<IEdoIntegrationExchangeItem>();
        this.getItems().forEach(a -> b.addAll(a.getAllItems()));
        return b;
    }
}
