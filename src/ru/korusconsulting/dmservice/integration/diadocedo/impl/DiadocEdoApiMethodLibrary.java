// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import org.apache.logging.log4j.LogManager;
import Diadoc.Api.exceptions.DiadocSdkException;
import java.util.function.BiConsumer;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import Diadoc.Api.events.OrderBy;
import Diadoc.Api.events.DocumentDirection;
import Diadoc.Api.events.MessageType;
import org.jetbrains.annotations.Nullable;
import Diadoc.Api.Proto.Documents.DocumentZipProtos;
import Diadoc.Api.Proto.Documents.DocumentProtos;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang.exception.ExceptionUtils;
import Diadoc.Api.Proto.Docflow.DocflowApiV4Protos;
import Diadoc.Api.Proto.Docflow.DocflowApiProtos;
import java.util.Collections;
import com.google.protobuf.GeneratedMessage;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;
import Diadoc.Api.Proto.OrganizationProtos;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import Diadoc.Api.exceptions.DiadocSdkSystemErrorException;
import Diadoc.Api.exceptions.DiadocSdkMethodFailedException;
import Diadoc.Api.exceptions.DiadocSdkNetworkErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoAuthenticationFailedException;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import java.util.Collection;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import java.util.Arrays;
import ru.korusconsulting.dmservice.integration.NamedParam;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationLogMethod;
import Diadoc.Api.Proto.CounteragentProtos;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.Logger;

public class DiadocEdoApiMethodLibrary
{
    private static final Logger logger;
    
    protected static final Logger getLogger() {
        return DiadocEdoApiMethodLibrary.logger;
    }
    
    private DiadocEdoApiMethodLibrary() {
    }
    
    public static List<CounteragentProtos.CounteragentList> getMyCounteragentList(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final String inn, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_MY_COUNTERAGENT_BY_INN;
        return new DiadocEdoApiLoggingGetListWrapper<CounteragentProtos.CounteragentList>().wrapLogging(session, service, "is_my_counteragent", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getCounteragentClientCustom().getCounteragentsByInn(boxGuid, "IsMyCounteragent", inn, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.result_list_size, (Object)String.valueOf(responceObject.size()))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static List<CounteragentProtos.CounteragentList> getCounteragentList(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final String inn, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_MY_COUNTERAGENT_BY_INN;
        return new DiadocEdoApiLoggingGetListWrapper<CounteragentProtos.CounteragentList>().wrapLogging(session, service, "is_my_counteragent", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getCounteragentClientCustom().getCounteragentsByInn(boxGuid, (String)null, inn, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.result_list_size, (Object)String.valueOf(responceObject.size()))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static List<CounteragentProtos.CounteragentList> getRejectedCounteragentList(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final String inn, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiConnection = operationContext.getApiConnection(session, service, ctx);
        if (apiConnection == null) {
            throw new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
        }
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_REJECTED_COUNTERAGENT_BY_INN;
        return new DiadocEdoApiLoggingGetListWrapper<CounteragentProtos.CounteragentList>().wrapLogging(session, service, "get_rejected_counteragent", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getCounteragentClientCustom().getCounteragentsByInn(boxGuid, "Rejected", inn, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.result_list_size, (Object)String.valueOf(responceObject.size()))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static OrganizationProtos.Organization getOrganization(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String orgId, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_ORGANIZATION_BY_ID;
        return new DiadocEdoApiLoggingGetWrapper<OrganizationProtos.Organization>().wrapLogging(session, service, "get_organization_by_org_id", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getOrganizationClientCustom().getOrganizationById(orgId, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_org_id, (Object)orgId), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static OrganizationProtos.OrganizationList getOrganizationsByInn(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String inn, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_ORGANIZATION_BY_INN;
        return new DiadocEdoApiLoggingGetWrapper<OrganizationProtos.OrganizationList>().wrapLogging(session, service, "get_organization_by_inn", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getOrganizationClientCustom().getOrganizationsByInnKpp(inn, (String)null, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.result_list_size, (Object)String.valueOf(responceObject.getOrganizationsCount()))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn)), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static OrganizationProtos.OrganizationList getOrganizationsByInnKpp(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String inn, final String kpp, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_ORGANIZATION_BY_INN_KPP;
        return new DiadocEdoApiLoggingGetWrapper<OrganizationProtos.OrganizationList>().wrapLogging(session, service, "get_organization_by_inn_kpp", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getOrganizationClientCustom().getOrganizationsByInnKpp(inn, kpp, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.result_list_size, (Object)String.valueOf(responceObject.getOrganizationsCount()))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx))), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, (Object)inn), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, (Object)kpp), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_details, (Object)service.getCounteragentDetails(inn, kpp, ctx)), new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DiadocMessage_GetApiProtos.Message postMessage(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final DiadocMessage_PostApiProtos.MessageToPost messageToPost, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.SEND_DOCUMENT_POST_MESSAGE;
        return new DiadocEdoApiLoggingCallWrapper<GeneratedMessage, DiadocMessage_GetApiProtos.Message>().wrapLogging(session, service, "post_message", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getMessageClientCustom().postMessage(messageToPost, urlLogger, requestLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_POST_MESSAGE_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DocflowApiV4Protos.GetDocflowBatchResponseV4 getDocflows(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final DocflowApiProtos.GetDocflowBatchRequest getDocflowBatchRequest, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_DOCFLOWS;
        return new DiadocEdoApiLoggingCallWrapper<GeneratedMessage, DocflowApiV4Protos.GetDocflowBatchResponseV4>().wrapLogging(session, service, "get_docflows", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getDocflowClientCustom().getDocflows(boxGuid, getDocflowBatchRequest, urlLogger, requestLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DocumentProtos.Document getDocument(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final String messageId, final String entityId, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_DOCUMENT;
        return new DiadocEdoApiLoggingGetWrapper<DocumentProtos.Document>().wrapLogging(session, service, "get_document", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getDocumentClientCustom().getDocument(boxGuid, messageId, entityId, Boolean.valueOf(false), urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DocumentZipProtos.DocumentZipGenerationResult generateZip(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxGuid, final String messageGuid, final String documentGuid, final boolean fullDocflow, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GENERATE_DOCUMENT_ZIP;
        return new DiadocEdoApiLoggingGetWrapper<DocumentZipProtos.DocumentZipGenerationResult>().wrapLogging(session, service, "generate_document_zip", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getPrintFormClientCustom().generateDocumentZip(boxGuid, messageGuid, documentGuid, fullDocflow, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static OrganizationProtos.Box getBox(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxId, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_BOX;
        return new DiadocEdoApiLoggingGetWrapper<OrganizationProtos.Box>().wrapLogging(session, service, "get_box", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getOrganizationClientCustom().getBox(boxId, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static byte[] shelfDownload(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String fileName, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiConnection = operationContext.getApiConnection(session, service, ctx);
        if (apiConnection == null) {
            throw new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
        }
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.SHELF_DOWNLOAD;
        return new DiadocEdoApiLoggingPrimitiveGetWrapper<byte[]>().wrapLogging(session, service, "shelf_download", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getShelfClientCustom().shelfDownload(fileName, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static String uploadFileToShelf(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final byte[] filedata, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.SHELF_UPLOAD;
        return new DiadocEdoApiLoggingPrimitivePostWrapper<Object, String>().wrapLogging(session, service, "upload_file_to_shelf", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getShelfClientCustom().uploadFileToShelf(filedata, (String)null, urlLogger, requestLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DiadocMessage_GetApiProtos.MessagePatch postMessagePatchV4(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final DiadocMessage_PostApiProtos.MessagePatchToPostV2 messagePatchToPost, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.POST_MESSAGE_PATCH;
        return new DiadocEdoApiLoggingCallWrapper<GeneratedMessage, DiadocMessage_GetApiProtos.MessagePatch>().wrapLogging(session, service, "post_message_patch", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getMessageClientCustom().postMessagePatchV4(messagePatchToPost, urlLogger, requestLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                try {
                    final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
                catch (final Exception e) {
                    IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, getLogger(), () -> "Error parsing call result", (Throwable)e);
                    logStage.addFile("result_parsing_error", "txt", ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
                    final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_PARSING_RESULT_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                    return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, null, operationContext.getFilialId(), operationContext.getFilialName()) };
                }
            }
            final String message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, (exception, logStage) -> {
            String message4;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)"")), ctx);
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getMessage())), ctx);
            }
            else {
                message4 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message4, null, operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static OrganizationProtos.OrganizationList getMyOrganizations(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_MY_ORGANIZATIONS;
        return new DiadocEdoApiLoggingGetWrapper<OrganizationProtos.OrganizationList>().wrapLogging(session, service, "get_my_organizations", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getOrganizationClientCustom().getMyOrganizations(urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static void authenticate(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String login, final String password, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.AUTHENTICATE;
        new DiadocEdoApiLoggingPrimitiveVoidCallWrapper().wrapLogging(session, service, "authenticate", (String)operationContext.getLogFilenameTemplate().orElse(null), urlLogger -> api.getAuthClient().authenticate(login, password), (exception, logStage) -> {
            String message;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DiadocMessage_GetApiProtos.BoxEventList getNewEvents(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String currentBoxId, @Nullable final String afterEventId, @Nullable final String afterIndexKey, @Nullable final String departmentId, @Nullable final String typeNameId, @Nullable final MessageType messageType, @Nullable final DocumentDirection documentDirection, @Nullable final Long timestampFromTicks, @Nullable final Long timestampToTicks, @Nullable final String counteragentBoxId, @Nullable final OrderBy orderBy, @Nullable final Integer limit, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_NEW_EVENTS;
        return new DiadocEdoApiLoggingGetWrapper<DiadocMessage_GetApiProtos.BoxEventList>().wrapLogging(session, service, "get_new_events", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getEventsClientCustom().getNewEventsV8(currentBoxId, afterEventId, afterIndexKey, departmentId, typeNameId, messageType, documentDirection, timestampFromTicks, timestampToTicks, counteragentBoxId, orderBy, limit, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    public static DiadocMessage_GetApiProtos.BoxEvent getNewEvents(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final String boxId, final String eventId, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final DiadocEdoIntegrationLogMethod operation = DiadocEdoIntegrationLogMethod.GET_EVENT;
        return new DiadocEdoApiLoggingGetWrapper<DiadocMessage_GetApiProtos.BoxEvent>().wrapLogging(session, service, "get_event", (String)operationContext.getLogFilenameTemplate().orElse(null), (urlLogger, requestLogger, responseLogger) -> api.getEventsClientCustom().getEventV3(boxId, eventId, urlLogger, responseLogger), (responceObject, logStage) -> {
            if (responceObject != null) {
                final String message = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SUCCESS, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.INFO, operation, message, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
            else {
                final String message2 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_EMPTY_RESPONCE, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Collections.emptyList(), ctx);
                return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message2, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
            }
        }, (exception, logStage) -> {
            String message3;
            if (exception instanceof final DiadocEdoAuthenticationFailedException aException) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_AUTHENTICATION_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException.getMessage());
            }
            else if (exception instanceof final DiadocSdkNetworkErrorException aException2) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_NETWORK_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException2.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException2.getMessage());
            }
            else if (exception instanceof final DiadocSdkMethodFailedException aException3) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_METHOD_FAILED, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException3.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException3.getReasonPhrase());
            }
            else if (exception instanceof final DiadocSdkSystemErrorException aException4) {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)aException4.getReasonPhrase())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, aException4.getReasonPhrase());
            }
            else {
                message3 = service.calculateMessage(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_UNNAMED_SYSTEM_ERROR, (IIntegrationOperationContext)operationContext, (Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>>)Arrays.asList(new NamedParam((Enum)IDiadocEdoIntegrationServiceInternal.ParamName.error_message, (Object)exception.getMessage())), ctx);
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.error_message, exception.getMessage());
            }
            return new DiadocEdoApiIntegrationLogRecord[] { new DiadocEdoApiIntegrationLogRecord(IIntegrationLogService.TYPE.ERROR, operation, message3, operationContext.getDocumentId(), operationContext.getFilialId(), operationContext.getFilialName()) };
        }, operationContext, ctx);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
