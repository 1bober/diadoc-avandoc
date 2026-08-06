// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoSurveyState;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationStateMapping;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import java.util.Collection;
import java.util.LinkedList;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoConnectionFailedException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.integration.IIntegrationLogSettings;
import ru.korusconsulting.dmservice.integration.impl.IntegrationLogSettings;
import ru.korusconsulting.service.integration.diadocedo.DiadocEdoModuleProperties;
import java.util.Optional;
import java.util.EnumMap;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.NamedParam;
import java.util.List;
import java.util.Collections;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.objectmodel.administration.IFilial;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordEvent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import java.util.Map;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContextInternal;
import ru.korusconsulting.dmservice.integration.impl.IntegrationOperationContext;

public class DiadocEdoOperationContext extends IntegrationOperationContext implements IDiadocEdoOperationContextInternal
{
    private final Map<IDiadocEdoIntegrationServiceInternal.ParamName, String> params;
    private DiadocEdoIntegrationOperation operation;
    
    public static DiadocEdoRootOperationContext createRootContext(final DMSShardsClientSession session, final IntegrationLogRecordEvent integrationLogEvent, final DiadocEdoIntegrationOperation operation, final SessionContext ctx) {
        return createRootContext(session, integrationLogEvent, operation, null, null, ctx);
    }
    
    public static DiadocEdoRootOperationContext createRootContext(final DMSShardsClientSession session, final IntegrationLogRecordEvent integrationLogEvent, final DiadocEdoIntegrationOperation operation, final IDocument document, final SessionContext ctx) {
        final DiadocEdoRootOperationContext documentOperationContext = new DiadocEdoRootOperationContext(session, null, integrationLogEvent, null, document, ctx);
        if (operation != null) {
            documentOperationContext.setOperation(operation);
        }
        return documentOperationContext;
    }
    
    public static DiadocEdoRootOperationContext createRootContext(final DMSShardsClientSession session, final IntegrationLogRecordEvent integrationLogEvent, final DiadocEdoIntegrationOperation operation, final IDocument document, IFilial filial, final SessionContext ctx) {
        if (filial == null) {
            filial = ((document == null) ? null : ((IFilial)ServiceReg.getObjectModelService().getObjectModel(session, document.getFilial(), ctx)));
        }
        final DiadocEdoRootOperationContext documentOperationContext = new DiadocEdoRootOperationContext(session, null, integrationLogEvent, filial, document, ctx);
        if (operation != null) {
            documentOperationContext.setOperation(operation);
        }
        return documentOperationContext;
    }
    
    public static DiadocEdoRootOperationContext createRootContext(final DMSShardsClientSession session, final IntegrationLogRecordEvent integrationLogEvent, final SessionContext ctx) {
        return new DiadocEdoRootOperationContext(session, null, integrationLogEvent, null, null, ctx);
    }
    
    public static IDiadocEdoOperationContext createFilialContext(final DMSShardsClientSession session, final IDiadocEdoOperationContext parent, final IFilial filial, final SessionContext ctx) {
        return createFilialContext(session, parent, filial, null, ctx);
    }
    
    public static IDiadocEdoOperationContext createFilialContext(final DMSShardsClientSession session, final IDiadocEdoOperationContext parent, final IFilial filial, final DiadocEdoIntegrationOperation operation, final SessionContext ctx) {
        final DiadocEdoOperationContext filialOperationContext = new DiadocEdoOperationContext(session, (IIntegrationOperationContext)parent, null, filial, null, ctx);
        if (operation != null) {
            filialOperationContext.setOperation(operation);
        }
        return filialOperationContext;
    }
    
    public static DiadocEdoOperationContext createDocumentContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final IDocument document, final SessionContext ctx) {
        return createDocumentContext(session, parent, document, null, null, ctx);
    }
    
    public static DiadocEdoOperationContext createDocumentContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final IDocument document, final IntegrationLogRecordEvent event, final DiadocEdoIntegrationOperation operation, final SessionContext ctx) {
        final DiadocEdoOperationContext documentOperationContext = new DiadocEdoOperationContext(session, parent, null, null, document, ctx);
        if (event != null && !StringUtils.equals(documentOperationContext.getIntegrationLogEvent(), event.name())) {
            documentOperationContext.setIntegrationLogEvent(event.name());
            documentOperationContext.setOperation(null);
        }
        if (operation != null) {
            documentOperationContext.setOperation(operation);
        }
        return documentOperationContext;
    }
    
    public static DiadocEdoOperationContext createOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final DiadocEdoIntegrationOperation operation, final SessionContext ctx) {
        return createOutboundOperationContext(session, parent, operation, null, false, null, Collections.emptyList(), ctx);
    }
    
    public static DiadocEdoOperationContext createOutboundOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final DiadocEdoIntegrationOperation operation, final String exchangeCategory, final SessionContext ctx) {
        return createOutboundOperationContext(session, parent, operation, null, false, exchangeCategory, Collections.emptyList(), ctx);
    }
    
    public static DiadocEdoOperationContext createOutboundOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final DiadocEdoIntegrationOperation operation, final IDocument document, final boolean calculateConnectionParams, final String exchangeCategory, final List<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> params, final SessionContext ctx) {
        final DiadocEdoOperationContext documentOperationContext = new DiadocEdoOperationContext(session, parent, null, null, document, ctx);
        documentOperationContext.setOperation(operation);
        IDiadocEdoConnectionConfig connectionConfig = null;
        if (exchangeCategory != null) {
            documentOperationContext.setExchangeCategory(exchangeCategory);
        }
        connectionConfig = documentOperationContext.getApiConnectionConfig(session, ctx);
        if (connectionConfig != null) {
            documentOperationContext.setActiveDepartment(connectionConfig.getOutboundDepartment(exchangeCategory));
        }
        if (params != null) {
            params.forEach(a -> documentOperationContext.setParam((IDiadocEdoIntegrationServiceInternal.ParamName)a.getKey(), (String)a.getValue()));
        }
        if (calculateConnectionParams) {
            if (connectionConfig == null) {
                connectionConfig = documentOperationContext.getApiConnectionConfig(session, ctx);
            }
            if (connectionConfig != null) {
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.my_box_guid, connectionConfig.getBoxId());
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.my_department_id, documentOperationContext.getActiveDepartment());
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.web_url, connectionConfig.getWebUrl());
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.url, connectionConfig.getUrl());
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.server_id, connectionConfig.getServerId());
                documentOperationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.server_name, connectionConfig.getServerName());
            }
        }
        return documentOperationContext;
    }
    
    protected DiadocEdoOperationContext(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final IntegrationLogRecordEvent integrationLogEvent, final IFilial filial, final IDocument document, final SessionContext ctx) {
        super(session, parent, (integrationLogEvent == null) ? null : integrationLogEvent.name(), filial, document, ctx);
        this.params = new EnumMap<IDiadocEdoIntegrationServiceInternal.ParamName, String>(IDiadocEdoIntegrationServiceInternal.ParamName.class);
        if (filial != null) {
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_id, filial.getObjectId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_name, filial.getFilialName());
        }
        else if (document != null) {
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_id, document.getObjectId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_name, document.getFilialName());
        }
        if (document != null) {
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_id, document.getObjectId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_name, document.getDocumentName());
        }
    }
    
    protected Optional<String> getLogFilenameTemplate(final DMSShardsClientSession session, final IIntegrationOperationContext parent, final SessionContext ctx) {
        if (parent == null) {
            final IIntegrationLogSettings logSettings = IntegrationLogSettings.load(session, "DIADOCEDO", "connection_extra_settings", DiadocEdoModuleProperties::getProperty, ctx);
            return (logSettings == null) ? Optional.ofNullable(IntegrationLogSettings.getDefaultFilenameTemplate()) : Optional.ofNullable(logSettings.getFilenameTemplate());
        }
        return parent.getLogFilenameTemplate();
    }
    
    public String getParam(final IDiadocEdoIntegrationServiceInternal.ParamName key) {
        if (this.params.containsKey(key)) {
            return this.params.get(key);
        }
        if (this.getParent() != null) {
            return this.getParent().getParam(key);
        }
        return "";
    }
    
    public void setParam(final IDiadocEdoIntegrationServiceInternal.ParamName key, final String value) {
        this.params.put(key, (value == null) ? "" : value);
    }
    
    public Map<IDiadocEdoIntegrationServiceInternal.ParamName, String> getParams() {
        Map<IDiadocEdoIntegrationServiceInternal.ParamName, String> result;
        if (this.getParent() != null) {
            result = this.getParent().getParams();
        }
        else {
            result = new EnumMap<IDiadocEdoIntegrationServiceInternal.ParamName, String>(IDiadocEdoIntegrationServiceInternal.ParamName.class);
        }
        this.params.entrySet().forEach(a -> result.put(a.getKey(), a.getValue()));
        return result;
    }
    
    public void close() {
    }
    
    public IDiadocEdoApiBoxConnection getApiConnection(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal integrationLogWriter, final SessionContext ctx) throws DiadocEdoIntegrationException {
        return this.getApiConnection(session, integrationLogWriter, this, ctx);
    }
    
    public IDiadocEdoApiBoxConnection getApiConnection(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal integrationLogWriter, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) throws DiadocEdoIntegrationException {
        if (this.getParent() != null) {
            return this.getParent().getApiConnection(session, integrationLogWriter, operationContext, ctx);
        }
        throw new DiadocEdoConnectionFailedException(String.format("Failed to make connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
    }
    
    public IDiadocEdoOperationContextInternal getParent() {
        final IIntegrationOperationContext parent = super.getParent();
        IDiadocEdoOperationContextInternal diadocEdoOperationContextInternal;
        if (parent instanceof final IDiadocEdoOperationContextInternal diadocEdoOperationContextInternal2) {
            final IDiadocEdoOperationContextInternal oContext = diadocEdoOperationContextInternal = diadocEdoOperationContextInternal2;
        }
        else {
            diadocEdoOperationContextInternal = null;
        }
        return diadocEdoOperationContextInternal;
    }
    
    public DiadocEdoIntegrationOperation getOperation() {
        if (this.operation != null) {
            return this.operation;
        }
        if (this.getParent() != null) {
            return this.getParent().getOperation();
        }
        return null;
    }
    
    public void setOperation(final DiadocEdoIntegrationOperation operation) {
        this.operation = operation;
    }
    
    public LinkedList<String> getOperationsStackString() {
        if (this.getParent() != null && StringUtils.isBlank(this.getIntegrationLogEvent())) {
            final LinkedList<String> result = this.getParent().getOperationsStackString();
            if (this.getOperation() != null && (result.isEmpty() || !StringUtils.equals((String)result.getLast(), this.getOperation().name()))) {
                result.add(this.getOperation().name());
            }
            return result;
        }
        final LinkedList<String> result = new LinkedList<String>();
        if (this.getOperation() != null) {
            result.add(this.getOperation().name());
        }
        return result;
    }
    
    public String getDiadocBoxGuid(final DMSShardsClientSession session, final SessionContext ctx) {
        return this.getDiadocBoxGuid(session, this, ctx);
    }
    
    public String getDiadocBoxGuid(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getDiadocBoxGuid(session, operationContext, ctx);
        }
        return null;
    }
    
    public String getDiadocOutgoingDepartmentId(final DMSShardsClientSession session, final String category, final SessionContext ctx) {
        if (this.getParent() == null) {
            return null;
        }
        final IDiadocEdoConnectionConfig config = this.getParent().getApiConnectionConfig(session, this, ctx);
        if (config != null) {
            return config.getOutboundDepartment(category);
        }
        return null;
    }
    
    public String getDiadocOutgoingDepartmentId(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final String category, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getDiadocOutgoingDepartmentId(session, operationContext, category, ctx);
        }
        return null;
    }
    
    public IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession session, final SessionContext ctx) {
        return this.getApiConnectionConfig(session, this, ctx);
    }
    
    public IDiadocEdoConnectionConfig getApiConnectionConfig(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getApiConnectionConfig(session, operationContext, ctx);
        }
        return null;
    }
    
    public Collection<String> getConnectedFilialsList(final DMSShardsClientSession session, final SessionContext ctx) {
        return this.getConnectedFilialsList(session, this, (Collection<IntegrationOperationContext.ConnectionOptions>)Collections.emptyList(), ctx);
    }
    
    public Collection<String> getConnectedFilialsList(final DMSShardsClientSession session, final Collection<IntegrationOperationContext.ConnectionOptions> options, final SessionContext ctx) {
        return this.getConnectedFilialsList(session, this, options, ctx);
    }
    
    public Collection<String> getConnectedFilialsList(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final Collection<IntegrationOperationContext.ConnectionOptions> options, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getConnectedFilialsList(session, operationContext, options, ctx);
        }
        return (Collection<String>)Collections.emptyList();
    }
    
    public List<IDiadocEdoExchangeError> getExchangeErrors(final DMSShardsClientSession session, final SessionContext ctx) {
        return this.getExchangeErrors(session, this, ctx);
    }
    
    public List<IDiadocEdoExchangeError> getExchangeErrors(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getExchangeErrors(session, operationContext, ctx);
        }
        return Collections.emptyList();
    }
    
    public List<IEdoIntegrationStateMapping> getDocumentRemoteStateMappingList(final DMSShardsClientSession session, final SessionContext ctx) {
        return this.getDocumentRemoteStateMappingList(session, this, ctx);
    }
    
    public List<IEdoIntegrationStateMapping> getDocumentRemoteStateMappingList(final DMSShardsClientSession session, final IDiadocEdoOperationContextInternal operationContext, final SessionContext ctx) {
        if (this.getParent() != null) {
            return this.getParent().getDocumentRemoteStateMappingList(session, operationContext, ctx);
        }
        return Collections.emptyList();
    }
    
    public void initDocumentParams(final IDocument document) {
        if (document != null) {
            this.setDocumentId(document.getObjectId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_id, document.getObjectId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_name, document.getDocumentName());
        }
    }
    
    public void setDocumentInfo(final DMSShardsClientSession session, final String documentId, final String documentName, final String filialId, final String filialName, final SessionContext ctx) {
        this.setDocumentId(documentId);
        this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_id, documentId);
        this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.document_name, documentName);
    }
    
    public void initConnectionParams(final IDiadocEdoConnectionConfig connectionConfig) {
        if (connectionConfig != null) {
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.my_box_guid, connectionConfig.getBoxId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.url, connectionConfig.getUrl());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.server_id, connectionConfig.getServerId());
            this.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.server_name, connectionConfig.getServerName());
        }
    }
    
    public String getInboundDepartment(final DMSShardsClientSession session, final String exchangeCategory, final SessionContext ctx) {
        return this.getApiConnectionConfig(session, ctx).getInboundDepartment(exchangeCategory);
    }
    
    public void commitIncomingAfterIndexKey(final DMSShardsClientSession session, final String accountId, final String department, final String value, final SessionContext ctx) {
        String query;
        if (StringUtils.isNotBlank(department)) {
            query = String.format("select r_object_id from nt_diadoc_edo_survey_state where sida_member='%s' and ssa_department='%s' and ssa_direction='%s'", accountId, department, IEdoIntegrationService.EXCHANGE_DIRECTION.IN.name());
        }
        else {
            query = String.format("select r_object_id from nt_diadoc_edo_survey_state where sida_member='%s' and ssa_department is null and ssa_direction='%s'", accountId, IEdoIntegrationService.EXCHANGE_DIRECTION.IN.name());
        }
        final String surveyStateId = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
        IDiadocEdoSurveyState surveyState;
        if (StringUtils.isBlank(surveyStateId)) {
            surveyState = (IDiadocEdoSurveyState)ServiceReg.getObjectModelService().createObjectModel(session, "nt_diadoc_edo_survey_state", ctx);
            surveyState.setAccountMemberId(accountId);
            if (StringUtils.isNotBlank(department)) {
                surveyState.setDepartment(department);
            }
            surveyState.setDirection(IEdoIntegrationService.EXCHANGE_DIRECTION.IN.name());
            surveyState.setDiadocLogin(this.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.my_box_guid));
            surveyState.setFilialId(this.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_id));
            surveyState.setFilialName(this.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.filial_id));
        }
        else {
            surveyState = (IDiadocEdoSurveyState)ServiceReg.getObjectModelService().getObjectModel(session, surveyStateId, ctx);
        }
        surveyState.setLastProcceed(value);
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)surveyState, ctx);
    }
    
    public String getIncomingAfterIndexKey(final DMSShardsClientSession session, final String accountId, final String department, final SessionContext ctx) {
        String query;
        if (StringUtils.isNotBlank(department)) {
            query = String.format("select ssa_last_proceed from nt_diadoc_edo_survey_state where sida_member='%s' and ssa_department='%s' and ssa_direction='%s'", accountId, department, IEdoIntegrationService.EXCHANGE_DIRECTION.IN.name());
        }
        else {
            query = String.format("select ssa_last_proceed from nt_diadoc_edo_survey_state where sida_member='%s' and ssa_department is null and ssa_direction='%s'", accountId, IEdoIntegrationService.EXCHANGE_DIRECTION.IN.name());
        }
        return ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
    }
}
