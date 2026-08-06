// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;

public interface IEdoIntegrationExchangeUnit
{
    String getObjectId();
    
    void setObjectId(final String p0);
    
    String getOperationGuid();
    
    void setOperationGuid(final String p0);
    
    String getName();
    
    void setName(final String p0);
    
    String getDocumentId();
    
    void setDocumentId(final String p0);
    
    EdoIntegrationItemType getItemType();
    
    void setItemType(final EdoIntegrationItemType p0);
    
    String getDocumentType();
    
    void setDocumentType(final String p0);
    
    String getDocumentTypeName();
    
    void setDocumentTypeName(final String p0);
    
    String getDocumentKindId();
    
    void setDocumentKindId(final String p0);
    
    String getDocumentKindName();
    
    void setDocumentKindName(final String p0);
    
    String getDocumentSubkindId();
    
    void setDocumentSubkindId(final String p0);
    
    String getDocumentSubkindName();
    
    void setDocumentSubkindName(final String p0);
    
    String getDocumentName();
    
    void setDocumentName(final String p0);
    
    String getFilialId();
    
    void setFilialId(final String p0);
    
    String getFilialName();
    
    void setFilialName(final String p0);
    
    IEdoIntegrationExchangeItem getItem();
    
    void setItem(final IEdoIntegrationExchangeItem p0);
    
    void queue();
    
    void commit(final DMSShardsClientSession p0, final boolean p1, final SessionContext p2);
}
