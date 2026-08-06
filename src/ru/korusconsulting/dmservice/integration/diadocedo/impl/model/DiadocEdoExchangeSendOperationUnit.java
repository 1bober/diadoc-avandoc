// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.dmservice.integration.impl.model.EdoIntegrationExchangeOperationUnit;

public class DiadocEdoExchangeSendOperationUnit extends EdoIntegrationExchangeOperationUnit implements IEdoIntegrationExchangeOperationUnit
{
    public DiadocEdoExchangeSendOperationUnit(final IDocument document) {
        super(document);
        this.setItemType(EdoIntegrationItemType.document);
    }
}
