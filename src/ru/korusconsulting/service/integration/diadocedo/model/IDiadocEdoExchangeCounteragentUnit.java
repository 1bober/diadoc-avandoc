// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.model;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import java.util.List;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;

public interface IDiadocEdoExchangeCounteragentUnit extends IEdoIntegrationExchangeCounteragentUnit
{
    List<IEdoIntegrationExchangeUnit> getChildren();
    
    IDiadocEdoExchangeContentUnit getChildren(final String p0);
    
    void addChild(final IEdoIntegrationExchangeUnit p0);
    
    IEdoIntegrationExchangeItem getItem();
    
    String getDiadocMessageId();
    
    void setDiadocMessageId(final String p0);
    
    void initConnectionInfo(final IDiadocEdoConnectionConfig p0);
    
    void setSendStateInfo(final Map<String, Object> p0, final EdoIntegrationProcessingState p1);
    
    Map<String, Object> getMessageAsPojo();
}
