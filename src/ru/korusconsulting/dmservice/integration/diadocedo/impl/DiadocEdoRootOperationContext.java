// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;
import java.util.Collection;
import ru.korusconsulting.service.objectmodel.IDiadocEdoForeignStateMapping;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import ru.korusconsulting.service.commons.ICollection;
import ru.korusconsulting.service.IObjectModelService;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContextInternal;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordEvent;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import java.util.List;

public class DiadocEdoRootOperationContext extends DiadocEdoOperationContext
{
    private final DiadocEdoApiSupplier apiSupplier;
    private List<IDiadocEdoExchangeError> exchangeErrors;
    
    protected DiadocEdoRootOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final IntegrationLogRecordEvent integrationLogEvent, final IFilial filial, final IDocument document, final SessionContext ctx) {
        super(session, parent, integrationLogEvent, filial, document, ctx);
        this.apiSupplier = new DiadocEdoApiSupplier();
    }
    
    @Override
    public IDiadocEdoApiBoxConnection getApiConnection(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal integrationLogWriter, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) throws DiadocEdoIntegrationException {
        return this.apiSupplier.getApi(session, integrationLogWriter, operationContext, operationContext.getFilialId(), ctx);
    }
    
    @Override
    public IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        return this.apiSupplier.getApiConnectionConfig(session, operationContext, ctx);
    }
    
    @Override
    public List<IDiadocEdoExchangeError> getExchangeErrors(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.exchangeErrors == null) {
            final List<IDiadocEdoExchangeError> result = new ArrayList<IDiadocEdoExchangeError>();
            final String query = "select r_object_id from nt_diadoc_edo_exchange_error where sba_enabled = true";
            final IObjectModelService modelService = ServiceReg.getObjectModelService();
            try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, query, ctx)) {
                while (col.next()) {
                    result.add((IDiadocEdoExchangeError)modelService.getObjectModel(session, col.getString("r_object_id"), ctx));
                }
            }
            this.exchangeErrors = result;
        }
        return this.exchangeErrors;
    }
    
    @Override
    public List<IEdoIntegrationStateMapping> getDocumentRemoteStateMappingList(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.getDocumentRemoteMappingList() == null) {
            final List<IEdoIntegrationStateMapping> result = new ArrayList<IEdoIntegrationStateMapping>();
            final String query = "select r_object_id from nt_diadoc_edo_foreign_state_map where sba_enabled = true";
            final IObjectModelService modelService = ServiceReg.getObjectModelService();
            try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, query, ctx)) {
                while (col.next()) {
                    result.add((IEdoIntegrationStateMapping)modelService.getObjectModel(session, col.getString("r_object_id"), ctx));
                }
            }
            this.setDocumentRemoteMappingList((List)result);
        }
        return this.getDocumentRemoteMappingList();
    }
    
    @Override
    public Collection<String> getConnectedFilialsList(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final Collection<IntegrationOperationContext.ConnectionOptions> options, final SessionContext ctx) {
        return this.apiSupplier.getConnectedFilials(session, operationContext, options, ctx);
    }
    
    @Override
    public String getDiadocBoxGuid(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        final IDiadocEdoConnectionConfig config = this.getApiConnectionConfig(session, operationContext, ctx);
        if (config != null) {
            return config.getBoxId();
        }
        return null;
    }
    
    @Override
    public String getDiadocOutgoingDepartmentId(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final String category, final SessionContext ctx) {
        final IDiadocEdoConnectionConfig config = this.getApiConnectionConfig(session, operationContext, ctx);
        if (config != null) {
            return config.getOutboundDepartment(category);
        }
        return null;
    }
}
