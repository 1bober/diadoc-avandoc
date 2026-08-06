// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;
import java.util.Collection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public interface IDiadocEdoOperationContext extends IIntegrationOperationContext
{
    void setParam(final IDiadocEdoIntegrationServiceInternal.ParamName p0, final String p1);
    
    String getParam(final IDiadocEdoIntegrationServiceInternal.ParamName p0);
    
    Map<IDiadocEdoIntegrationServiceInternal.ParamName, String> getParams();
    
    List<IEdoIntegrationStateMapping> getDocumentRemoteStateMappingList(final DMSShardsClientSession p0, final SessionContext p1);
    
    List<IDiadocEdoExchangeError> getExchangeErrors(final DMSShardsClientSession p0, final SessionContext p1);
    
    IDiadocEdoApiBoxConnection getApiConnection(final DMSShardsClientSession p0, final IDiadocEdoIntegrationServiceInternal p1, final SessionContext p2) throws DiadocEdoIntegrationException;
    
    IFilial getFilial();
    
    String getDiadocBoxGuid(final DMSShardsClientSession p0, final SessionContext p1);
    
    String getDiadocOutgoingDepartmentId(final DMSShardsClientSession p0, final String p1, final SessionContext p2);
    
    IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession p0, final SessionContext p1);
    
    String getActiveDepartment();
    
    DiadocEdoIntegrationOperation getOperation();
    
    Collection<String> getConnectedFilialsList(final DMSShardsClientSession p0, final SessionContext p1);
    
    Collection<String> getConnectedFilialsList(final DMSShardsClientSession p0, final Collection<IntegrationOperationContext.ConnectionOptions> p1, final SessionContext p2);
    
    void initDocumentParams(final IDocument p0);
    
    void setDocumentInfo(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final String p4, final SessionContext p5);
    
    void initConnectionParams(final IDiadocEdoConnectionConfig p0);
    
    String getInboundDepartment(final DMSShardsClientSession p0, final String p1, final SessionContext p2);
    
    void commitIncomingAfterIndexKey(final DMSShardsClientSession p0, final String p1, final String p2, final String p3, final SessionContext p4);
    
    String getIncomingAfterIndexKey(final DMSShardsClientSession p0, final String p1, final String p2, final SessionContext p3);
}
