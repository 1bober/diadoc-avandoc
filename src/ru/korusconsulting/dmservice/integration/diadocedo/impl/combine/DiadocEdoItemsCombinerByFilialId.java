// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.combine;

import java.util.List;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.Collection;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.function.Function;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByFilialItem;
import ru.korusconsulting.service.integration.diadocedo.combine.IDiadocEdoCombinedByDocumentItem;
import ru.korusconsulting.dmservice.integration.combine.EdoIntegrationExchangeItemsCombiner;

public class DiadocEdoItemsCombinerByFilialId extends EdoIntegrationExchangeItemsCombiner<IDiadocEdoCombinedByDocumentItem> implements IDiadocEdoCombinedByFilialItem
{
    public DiadocEdoItemsCombinerByFilialId(final String key, final Function<String, IDiadocEdoCombinedByDocumentItem> createStoreFunction) {
        super(key, (Function)IEdoIntegrationInboundDataExchangeItem::getDocumentId, (Function)createStoreFunction);
    }
    
    public Collection<IDiadocEdoCombinedByDocumentItem> getItems() {
        return this.getChildren().values();
    }
    
    public Collection<IEdoIntegrationExchangeItem> getAllItems() {
        final List<IEdoIntegrationExchangeItem> b = new ArrayList<IEdoIntegrationExchangeItem>();
        this.getItems().forEach(a -> b.addAll(a.getAllItems()));
        return b;
    }
}
