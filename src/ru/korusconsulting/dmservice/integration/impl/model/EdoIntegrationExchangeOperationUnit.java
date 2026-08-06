// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.model;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.UUID;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import java.util.List;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;

public class EdoIntegrationExchangeOperationUnit extends EdoIntegrationExchangeUnit implements IEdoIntegrationExchangeOperationUnit
{
    private final List<IEdoIntegrationExchangeUnit> children;
    
    public EdoIntegrationExchangeOperationUnit(final IDocument document) {
        this.children = new ArrayList<IEdoIntegrationExchangeUnit>();
        this.setName(document.getDocumentName());
        this.setDocumentId(document.getObjectId());
        this.setDocumentName(document.getDocumentName());
        this.setDocumentType(document.getMainDocumentType());
        this.setDocumentTypeName(document.getMainDocumentTypeName());
        this.setDocumentKindId(document.getMainDocumentKind());
        this.setDocumentKindName(document.getMainDocumentKindName());
        this.setFilialId(document.getFilial());
        this.setFilialName(document.getFilialName());
        this.setOperationGuid(UUID.randomUUID().toString());
    }
    
    @Override
    public List<IEdoIntegrationExchangeUnit> getChildren() {
        return this.children;
    }
    
    @Override
    public void addChild(final IEdoIntegrationExchangeUnit value) {
        this.getChildren().add(value);
    }
    
    @Override
    public void queue() {
        this.children.forEach(IEdoIntegrationExchangeUnit::queue);
    }
    
    @Override
    public void commit(final DMSShardsClientSession session, final boolean setReady, final SessionContext ctx) {
        this.children.forEach(a -> a.commit(session, setReady, ctx));
    }
}
