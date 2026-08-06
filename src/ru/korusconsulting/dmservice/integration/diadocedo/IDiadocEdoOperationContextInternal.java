// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import java.util.List;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;
import java.util.Collection;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoOperationContextInternal extends IDiadocEdoOperationContext
{
    Collection<String> getConnectedFilialsList(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final Collection<IntegrationOperationContext.ConnectionOptions> p2, final SessionContext p3);
    
    IDiadocEdoApiBoxConnection getApiConnection(final DMSShardsClientSession p0, final IDiadocEdoIntegrationServiceInternal p1, final IDiadocEdoOperationContextInternal p2, final SessionContext p3) throws DiadocEdoIntegrationException;
    
    IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final SessionContext p2);
    
    String getDiadocBoxGuid(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final SessionContext p2);
    
    String getDiadocOutgoingDepartmentId(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final String p2, final SessionContext p3);
    
    List<IDiadocEdoExchangeError> getExchangeErrors(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final SessionContext p2);
    
    List<IEdoIntegrationStateMapping> getDocumentRemoteStateMappingList(final DMSShardsClientSession p0, final IDiadocEdoOperationContextInternal p1, final SessionContext p2);
}
