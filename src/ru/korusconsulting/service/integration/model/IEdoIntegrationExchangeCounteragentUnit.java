// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;

public interface IEdoIntegrationExchangeCounteragentUnit extends IEdoIntegrationExchangeUnit, IEdoIntegrationInboundDataExchangeItem
{
    String getName();
    
    void setName(final String p0);
    
    String getCounteragentContainerId();
    
    void setCounteragentContainerId(final String p0);
    
    String getCounteragentId();
    
    void setCounteragentId(final String p0);
    
    String getCounteragentName();
    
    void setCounteragentName(final String p0);
    
    String getCounteragentInn();
    
    void setCounteragentInn(final String p0);
    
    String getCounteragentKpp();
    
    void setCounteragentKpp(final String p0);
    
    void setToBox(final String p0);
    
    String getToBox();
    
    void setToBoxGuid(final String p0);
    
    String getToBoxGuid();
    
    void setToDepartment(final String p0);
    
    String getToDepartment();
    
    void setFromName(final String p0);
    
    String getFromName();
    
    void setFromInn(final String p0);
    
    String getFromInn();
    
    void setFromKpp(final String p0);
    
    String getFromKpp();
    
    void setUseDictionaryOrganizationDetails(final boolean p0);
    
    boolean useDictionaryOrganizationDetails();
    
    void setSendPrepareError(final EdoIntegrationSubsystemErrorCode p0);
    
    EdoIntegrationSubsystemErrorCode getSendPrepareError();
    
    EdoIntegrationProcessingState getSendState();
    
    void setSendState(final EdoIntegrationProcessingState p0);
    
    void loadState(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final SessionContext p2);
    
    String getServerId();
    
    void setServerId(final String p0);
    
    String getServerName();
    
    void setServerName(final String p0);
    
    String getAccountId();
    
    void setAccountId(final String p0);
    
    String getAccountName();
    
    void setAccountName(final String p0);
    
    String getAccountMemberId();
    
    void setAccountMemberId(final String p0);
    
    String getAccountMemberName();
    
    void setAccountMemberName(final String p0);
    
    String getFromBox();
    
    void setFromBox(final String p0);
    
    String getFromBoxGuid();
    
    void setFromBoxGuid(final String p0);
    
    String getFromDepartment();
    
    void setFromDepartment(final String p0);
    
    String getToInn();
    
    void setToInn(final String p0);
    
    String getToKpp();
    
    void setToKpp(final String p0);
    
    IEdoIntegrationExchangeUnit getParent();
    
    int getCurrentAttempt();
    
    void setCurrentAttempt(final int p0);
}
