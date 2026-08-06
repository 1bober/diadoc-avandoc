// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationCounteragentSelectQueryRule extends ICstmConfigAdapter
{
    String getSelectCounteragentQuery();
    
    void setSelectCounteragentQuery(final String p0);
    
    String getSendStatusSelectQuery();
    
    void setSendStatusSelectQuery(final String p0);
    
    String getSendStatusUpdateQuery();
    
    void setSendStatusUpdateQuery(final String p0);
}
