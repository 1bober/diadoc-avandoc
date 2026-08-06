// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.StrUtils;
import java.util.Calendar;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import java.util.Iterator;
import ru.korusconsulting.service.exceptions.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.Reader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import java.io.StringReader;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Date;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IObjectModelAttributeProcessor;
import java.util.List;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;

@ObjectModel(type = "nt_edo_integration_exchange_item")
public class EdoIntegrationExchangeItem extends DmObjectModel implements IEdoIntegrationExchangeItem
{
    private static final long serialVersionUID = 8633850204553153320L;
    private transient List<IObjectModelAttributeProcessor> attributes;
    
    public EdoIntegrationExchangeItem() {
        this.attributes = new ArrayList<IObjectModelAttributeProcessor>();
        this.appendAttributeDefinition("ssa_integration", String.class, 32, this::getIntegration, this::setIntegration);
        this.appendAttributeDefinition("ssa_direction", String.class, 32, this::getDirection, this::setDirection);
        this.appendAttributeDefinition("sba_main", Boolean.TYPE, 0, this::isMain, this::setMain);
        this.appendAttributeDefinition("sida_parent", String.class, 16, this::getParentId, this::setParentId);
        this.appendAttributeDefinition("sida_document", String.class, 16, this::getDocumentId, this::setDocumentId);
        this.appendAttributeDefinition("ssa_item_type", String.class, 64, this::getItemType, this::setItemType);
        this.appendAttributeDefinition("ssa_item_operation_code", String.class, 200, this::getItemOperationCode, this::setItemOperationCode);
        this.appendAttributeDefinition("ssa_document_type", String.class, 200, this::getDocumentType, this::setDocumentType);
        this.appendAttributeDefinition("ssa_document_type_name", String.class, 200, this::getDocumentTypeName, this::setDocumentTypeName);
        this.appendAttributeDefinition("ssa_document_kind_id", String.class, 200, this::getDocumentKindId, this::setDocumentKindId);
        this.appendAttributeDefinition("ssa_document_kind_name", String.class, 200, this::getDocumentKindName, this::setDocumentKindName);
        this.appendAttributeDefinition("ssa_document_subkind_id", String.class, 200, this::getDocumentSubkindId, this::setDocumentSubkindId);
        this.appendAttributeDefinition("ssa_document_subkind_name", String.class, 200, this::getDocumentSubkindName, this::setDocumentSubkindName);
        this.appendAttributeDefinition("ssa_document_name", String.class, 200, this::getDocumentName, this::setDocumentName);
        this.appendAttributeDefinition("sida_content", String.class, 16, this::getContentId, this::setContentId);
        this.appendAttributeDefinition("ssa_content_uid", String.class, 512, this::getContentUid, this::setContentUid);
        this.appendAttributeDefinition("ssa_content_name", String.class, 200, this::getContentName, this::setContentName);
        this.appendAttributeDefinition("sida_filial", String.class, 16, this::getFilialId, this::setFilialId);
        this.appendAttributeDefinition("ssa_filial_name", String.class, 200, this::getFilialName, this::setFilialName);
        this.appendAttributeDefinition("sida_counteragent_container", String.class, 16, this::getCounteragentContainerId, this::setCounteragentContainerId);
        this.appendAttributeDefinition("sida_counteragent", String.class, 16, this::getCounteragentId, this::setCounteragentId);
        this.appendAttributeDefinition("ssa_counteragent_name", String.class, 200, this::getCounteragentName, this::setCounteragentName);
        this.appendAttributeDefinition("ssa_counteragent_inn", String.class, 64, this::getCounteragentInn, this::setCounteragentInn);
        this.appendAttributeDefinition("ssa_counteragent_kpp", String.class, 64, this::getCounteragentKpp, this::setCounteragentKpp);
        this.appendAttributeDefinition("sida_sender", String.class, 16, this::getSenderId, this::setSenderId);
        this.appendAttributeDefinition("ssa_sender_name", String.class, 400, this::getSenderName, this::setSenderName);
        this.appendAttributeDefinition("ssa_sender_inn", String.class, 64, this::getSenderInn, this::setSenderInn);
        this.appendAttributeDefinition("ssa_sender_kpp", String.class, 64, this::getSenderKpp, this::setSenderKpp);
        this.appendAttributeDefinition("sida_server", String.class, 16, this::getServerId, this::setServerId);
        this.appendAttributeDefinition("ssa_server_name", String.class, 400, this::getServerName, this::setServerName);
        this.appendAttributeDefinition("sida_account", String.class, 16, this::getAccountId, this::setAccountId);
        this.appendAttributeDefinition("ssa_account_name", String.class, 400, this::getAccountName, this::setAccountName);
        this.appendAttributeDefinition("sida_account_member", String.class, 16, this::getAccountMemberId, this::setAccountMemberId);
        this.appendAttributeDefinition("ssa_account_member_name", String.class, 400, this::getAccountMemberName, this::setAccountMemberName);
        this.appendAttributeDefinition("ssa_from_inn", String.class, 400, this::getFromInn, this::setFromInn);
        this.appendAttributeDefinition("ssa_from_kpp", String.class, 400, this::getFromKpp, this::setFromKpp);
        this.appendAttributeDefinition("ssa_from_box", String.class, 400, this::getFromBox, this::setFromBox);
        this.appendAttributeDefinition("ssa_from_box_guid", String.class, 400, this::getFromBoxGuid, this::setFromBoxGuid);
        this.appendAttributeDefinition("ssa_from_department", String.class, 400, this::getFromDepartment, this::setFromDepartment);
        this.appendAttributeDefinition("ssa_from_regulations", String.class, 400, this::getFromRegulations, this::setFromRegulations);
        this.appendAttributeDefinition("ssa_to_name", String.class, 400, this::getToName, this::setToName);
        this.appendAttributeDefinition("ssa_to_inn", String.class, 64, this::getToInn, this::setToInn);
        this.appendAttributeDefinition("ssa_to_kpp", String.class, 64, this::getToKpp, this::setToKpp);
        this.appendAttributeDefinition("ssa_to_box", String.class, 400, this::getToBox, this::setToBox);
        this.appendAttributeDefinition("ssa_to_box_guid", String.class, 400, this::getToBoxGuid, this::setToBoxGuid);
        this.appendAttributeDefinition("ssa_to_name", String.class, 400, this::getToName, this::setToName);
        this.appendAttributeDefinition("ssa_to_inn", String.class, 64, this::getToInn, this::setToInn);
        this.appendAttributeDefinition("ssa_to_kpp", String.class, 64, this::getToKpp, this::setToKpp);
        this.appendAttributeDefinition("ssa_to_box", String.class, 400, this::getToBox, this::setToBox);
        this.appendAttributeDefinition("ssa_to_box_guid", String.class, 400, this::getToBoxGuid, this::setToBoxGuid);
        this.appendAttributeDefinition("ssa_to_department", String.class, 400, this::getToDepartment, this::setToDepartment);
        this.appendAttributeDefinition("sba_sended", Boolean.TYPE, 0, this::isSended, this::setSended);
        this.appendAttributeDefinition("sba_content_sended", Boolean.TYPE, 0, this::isContentSended, this::setContentSended);
        this.appendAttributeDefinition("sdta_send_time", Date.class, 0, this::getSendTime, this::setSendTime);
        this.appendAttributeDefinition("sdta_delivery_time", Date.class, 0, this::getDeliveryTime, this::setDeliveryTime);
        this.appendAttributeDefinition("sla_delivery_timestamp", Long.TYPE, 0, this::getDeliveryTimestamp, this::setDeliveryTimestamp);
        this.appendAttributeDefinition("ssa_reiteratable_error", String.class, 400, this::getReiteratableError, this::setReiteratableError);
        this.appendAttributeDefinition("ssa_reiteratable_error_message", String.class, 4000, this::getReiteratableErrorMessage, this::setReiteratableErrorMessage);
        this.appendAttributeDefinition("sdta_reiteratable_error_time", Date.class, 0, this::getReiteratableErrorTime, this::setReiteratableErrorTime);
        this.appendAttributeDefinition("sdta_reiteratable_error_overtime", Date.class, 0, this::getReiteratableErrorOvertime, this::setReiteratableErrorOvertime);
        this.appendAttributeDefinition("ssa_prev_local_state", String.class, 400, this::getPrevLocalState, this::setPrevLocalState);
        this.appendAttributeDefinition("ssa_prev_local_state_message", String.class, 4000, this::getPrevLocalStateMessage, this::setPrevLocalStateMessage);
        this.appendAttributeDefinition("sdta_prev_local_state_time", Date.class, 0, this::getPrevLocalStateTime, this::setPrevLocalStateTime);
        this.appendAttributeDefinition("ssa_local_state", String.class, 400, this::getLocalState, this::setLocalState);
        this.appendAttributeDefinition("sdta_local_state_time", Date.class, 0, this::getLocalStateTime, this::setLocalStateTime);
        this.appendAttributeDefinition("ssa_local_state_message", String.class, 4000, this::getLocalStateMessage, this::setLocalStateMessage);
        this.appendAttributeDefinition("ssa_message_guid", String.class, 400, this::getMessageEdoId, this::setMessageEdoId);
        this.appendAttributeDefinition("ssa_document_guid", String.class, 400, this::getDocumentEdoId, this::setDocumentEdoId);
        this.appendAttributeDefinition("ssa_prev_remote_state", String.class, 400, this::getPrevRemoteState, this::setPrevRemoteState);
        this.appendAttributeDefinition("ssa_prev_remote_state_message", String.class, 4000, this::getPrevRemoteStateMessage, this::setPrevRemoteStateMessage);
        this.appendAttributeDefinition("sdta_prev_remote_state_time", Date.class, 0, this::getPrevRemoteStateTime, this::setPrevRemoteStateTime);
        this.appendAttributeDefinition("ssa_remote_state", String.class, 400, this::getRemoteState, this::setRemoteState);
        this.appendAttributeDefinition("ssa_remote_state_message", String.class, 4000, this::getRemoteStateMessage, this::setRemoteStateMessage);
        this.appendAttributeDefinition("sdta_remote_local_state_time", Date.class, 0, this::getRemoteStateTime, this::setRemoteStateTime);
        this.appendAttributeDefinition("sba_proceed", Boolean.TYPE, 0, this::isProceed, this::setProceed);
        this.appendAttributeDefinition("sba_local_proceed", Boolean.TYPE, 0, this::isLocalProceed, this::setLocalProceed);
        this.appendAttributeDefinition("ssa_state", String.class, 32, this::getState, this::setState);
        this.appendAttributeDefinition("sba_not_actual", Boolean.TYPE, 0, this::isNotActual, this::setNotActual);
        this.appendAttributeDefinition("sia_current_attempt", Integer.TYPE, 0, this::getCurrentAttempt, this::setCurrentAttempt);
        this.appendAttributeDefinition("sba_ready", Boolean.TYPE, 0, this::isReady, this::setReady);
        this.appendAttributeDefinition("sba_queued", Boolean.TYPE, 0, this::isQueued, this::setQueued);
        this.appendAttributeDefinition("ssa_name_on_shelf", String.class, 400, this::getNameOnShelf, this::setNameOnShelf);
        this.appendAttributeDefinition("ssa_document_url", String.class, 4000, this::getDocumentUrl, this::setDocumentUrl);
        this.appendAttributeDefinition("sdta_last_check_time", Date.class, 0, this::getLastCheckTime, this::setLastCheckTime);
        this.appendAttributeDefinition("ssa_last_local_proceed_status", String.class, 100, this::getLastLocalProceedStatus, this::setLastLocalProceedStatus);
        this.appendAttributeDefinition("ssa_last_remote_proceed_status", String.class, 100, this::getLastRemoteProceedStatus, this::setLastRemoteProceedStatus);
        this.appendAttributeDefinition("ssa_local_state_display", String.class, 800, this::getLocalStateDisplay, this::setLocalStateDisplay);
        this.appendAttributeDefinition("ssa_local_state_system_code", String.class, 64, this::getLocalStateSystemCode, this::setLocalStateSystemCode);
        this.appendAttributeDefinition("ssa_remote_state_system_code", String.class, 64, this::getRemoteStateSystemCode, this::setRemoteStateSystemCode);
        this.appendAttributeDefinition("ssa_prev_local_state_sys_code", String.class, 64, this::getPrevLocalStateSystemCode, this::setPrevLocalStateSystemCode);
        this.appendAttributeDefinition("ssa_prev_remote_state_sys_code", String.class, 64, this::getPrevRemoteStateSystemCode, this::setPrevRemoteStateSystemCode);
        this.appendAttributeDefinition("ssa_state_category", String.class, 64, this::getStateCategory, this::setStateCategory);
        this.appendAttributeDefinition("ssa_remote_state_display", String.class, 400, this::getRemoteStateDisplay, this::setRemoteStateDisplay);
        this.appendAttributeDefinition("ssa_remote_state_code", String.class, 16, this::getRemoteStateCode, this::setRemoteStateCode);
        this.appendAttributeDefinition("ssa_prev_remote_state_code", String.class, 16, this::getPrevRemoteStateCode, this::setPrevRemoteStateCode);
        this.appendAttributeDefinition("ssa_remote_state_processing_step", String.class, 64, this::getRemoteStateProcessingStep, this::setRemoteStateProcessingStep);
        this.appendAttributeDefinition("ssa_local_state_processing_step", String.class, 64, this::getLocalStateProcessingStep, this::setLocalStateProcessingStep);
        this.appendAttributeDefinition("sida_next_item", String.class, 16, this::getNextItem, this::setNextItem);
        this.appendAttributeDefinition("sida_follow_item", String.class, 16, this::getFollowItem, this::setFollowItem);
        this.appendAttributeDefinition("ssa_processing_state", String.class, 64, this::getProcessingState, this::setProcessingState);
        this.appendAttributeDefinition("ssa_processing_error", String.class, 64, this::getProcessingError, this::setProcessingError);
        this.appendAttributeDefinition("ssa_operation_guid", String.class, 80, this::getOperationGuid, this::setOperationGuid);
        this.appendAttributeDefinition("sdta_queue_time", Date.class, 0, this::getQueueTime, this::setQueueTime);
        this.appendAttributeDefinition("sida_related", String.class, 16, this::getRelatedId, this::setRelatedId);
        this.appendAttributeDefinition("sia_error_count", Integer.TYPE, 16, this::getErrorCount, this::setErrorCount);
    }
    
    private <E> void appendAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<E> getter, final Consumer<E> setter) {
        this.appendAttributeDefinition(name, attrClass, maxLen, getter, setter, false);
    }
    
    private <E> void appendAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<E> getter, final Consumer<E> setter, final boolean autoCalculated) {
        this.attributes.add(new ObjectModelAttributeProcessor<Object>(name, (Class<Object>)attrClass, (maxLen == 16) ? 16 : (maxLen / 2), (Consumer<Object>)setter, (Supplier<Object>)getter, autoCalculated));
    }
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                attr.populateAttribute((IObjectModel)this, po);
            }
            this.setValue("sdta_create_date", (Object)po.getDate("sdta_create_date"));
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < po.getValueCount("rsa_params"); ++i) {
                sb.append(po.getRepeatingString("rsa_params", i));
            }
            try {
                if (!sb.isEmpty()) {
                    final StringReader reader = new StringReader(sb.toString());
                    final IEdoIntegrationPackageProcessingContext value = (IEdoIntegrationPackageProcessingContext)new ObjectMapper().readValue((Reader)reader, (TypeReference)new TypeReference<EdoIntegrationPackageProcessingContext>() {});
                    this.setProcessingContext(value);
                }
            }
            catch (final JsonParseException e) {
                this.setProcessingContext(null);
            }
        }
        catch (final Exception e2) {
            throw new ServiceException(e2);
        }
    }
    
    public void commitNew(final IObject po) {
        try {
            super.commitNew(po);
            if (EdoIntegrationProcessingState.QUEUED.name().equals(this.getLocalState())) {
                this.setQueueTime(Calendar.getInstance().getTime());
            }
            else if (EdoIntegrationProcessingState.COMPLETED.name().equals(this.getLocalState())) {
                this.setSendTime(Calendar.getInstance().getTime());
            }
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                if (!attr.isAutoCalculated()) {
                    attr.commitAttribute((IObjectModel)this, po);
                }
            }
            po.setDate("sdta_create_date", Calendar.getInstance().getTime());
            if (this.getProcessingContext() != null) {
                final ObjectMapper mapper = new ObjectMapper();
                final String paramsStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString((Object)this.getProcessingContext());
                final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                po.removeAll("rsa_params");
                for (final String token : paramsArr) {
                    po.appendString("rsa_params", token);
                }
            }
            else {
                po.removeAll("rsa_params");
            }
        }
        catch (final ServiceException e) {
            throw e;
        }
        catch (final Exception e2) {
            throw new ServiceException(e2);
        }
    }
    
    public void commitSaved(final IObject po) {
        try {
            super.commitSaved(po);
            if (!StringUtils.equals(po.getString("ssa_local_state"), this.getLocalState())) {
                if (EdoIntegrationProcessingState.QUEUED.name().equals(this.getLocalState())) {
                    this.setQueueTime(Calendar.getInstance().getTime());
                }
                else if (EdoIntegrationProcessingState.COMPLETED.name().equals(this.getLocalState())) {
                    this.setSendTime(Calendar.getInstance().getTime());
                }
            }
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                if (!attr.isAutoCalculated()) {
                    attr.commitAttribute((IObjectModel)this, po);
                }
            }
            if (this.getProcessingContext() != null) {
                final ObjectMapper mapper = new ObjectMapper();
                final String paramsStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString((Object)this.getProcessingContext());
                final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                po.removeAll("rsa_params");
                for (final String token : paramsArr) {
                    po.appendString("rsa_params", token);
                }
            }
            else {
                po.removeAll("rsa_params");
            }
        }
        catch (final ServiceException e) {
            throw e;
        }
        catch (final Exception e2) {
            throw new ServiceException(e2);
        }
    }
    
    public String getIntegration() {
        return this.getValueAsString("ssa_integration");
    }
    
    public void setIntegration(final String value) {
        this.setValue("ssa_integration", (Object)value);
    }
    
    public String getDirection() {
        return this.getValueAsString("ssa_direction");
    }
    
    public void setDirection(final String value) {
        this.setValue("ssa_direction", (Object)value);
    }
    
    public boolean isMain() {
        return this.getValueAsBoolean("sba_main");
    }
    
    public void setMain(final boolean value) {
        this.setValue("sba_main", (Object)value);
    }
    
    public String getDocumentId() {
        return this.getValueAsString("sida_document");
    }
    
    public void setDocumentId(final String value) {
        this.setValue("sida_document", (Object)value);
    }
    
    public String getParentId() {
        return this.getValueAsString("sida_parent");
    }
    
    public void setParentId(final String value) {
        this.setValue("sida_parent", (Object)value);
    }
    
    public String getDocumentName() {
        return this.getValueAsString("ssa_document_name");
    }
    
    public void setDocumentName(final String value) {
        this.setValue("ssa_document_name", (Object)value);
    }
    
    public String getContentId() {
        return this.getValueAsString("sida_content");
    }
    
    public void setContentId(final String value) {
        this.setValue("sida_content", (Object)value);
    }
    
    public String getContentUid() {
        return this.getValueAsString("ssa_content_uid");
    }
    
    public void setContentUid(final String value) {
        this.setValue("ssa_content_uid", (Object)value);
    }
    
    public String getContentName() {
        return this.getValueAsString("ssa_content_name");
    }
    
    public void setContentName(final String value) {
        this.setValue("ssa_content_name", (Object)value);
    }
    
    public String getFilialId() {
        return this.getValueAsString("sida_filial");
    }
    
    public void setFilialId(final String value) {
        this.setValue("sida_filial", (Object)value);
    }
    
    public String getFilialName() {
        return this.getValueAsString("ssa_filial_name");
    }
    
    public void setFilialName(final String value) {
        this.setValue("ssa_filial_name", (Object)value);
    }
    
    public String getCounteragentContainerId() {
        return this.getValueAsString("sida_counteragent_container");
    }
    
    public void setCounteragentContainerId(final String value) {
        this.setValue("sida_counteragent_container", (Object)value);
    }
    
    public String getCounteragentId() {
        return this.getValueAsString("sida_counteragent");
    }
    
    public void setCounteragentId(final String value) {
        this.setValue("sida_counteragent", (Object)value);
    }
    
    public String getCounteragentName() {
        return this.getValueAsString("ssa_counteragent_name");
    }
    
    public void setCounteragentName(final String value) {
        this.setValue("ssa_counteragent_name", (Object)value);
    }
    
    public String getCounteragentInn() {
        return this.getValueAsString("ssa_counteragent_inn");
    }
    
    public void setCounteragentInn(final String value) {
        this.setValue("ssa_counteragent_inn", (Object)value);
    }
    
    public String getCounteragentKpp() {
        return this.getValueAsString("ssa_counteragent_kpp");
    }
    
    public void setCounteragentKpp(final String value) {
        this.setValue("ssa_counteragent_kpp", (Object)value);
    }
    
    public String getSenderId() {
        return this.getValueAsString("sida_sender");
    }
    
    public void setSenderId(final String value) {
        this.setValue("sida_sender", (Object)value);
    }
    
    public String getSenderName() {
        return this.getValueAsString("ssa_sender_name");
    }
    
    public void setSenderName(final String value) {
        this.setValue("ssa_sender_name", (Object)value);
    }
    
    public String getSenderInn() {
        return this.getValueAsString("ssa_sender_inn");
    }
    
    public void setSenderInn(final String value) {
        this.setValue("ssa_sender_inn", (Object)value);
    }
    
    public String getSenderKpp() {
        return this.getValueAsString("ssa_sender_kpp");
    }
    
    public void setSenderKpp(final String value) {
        this.setValue("ssa_sender_kpp", (Object)value);
    }
    
    public String getServerId() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServerId(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getServerName() {
        return this.getValueAsString("ssa_server_name");
    }
    
    public void setServerName(final String value) {
        this.setValue("ssa_server_name", (Object)value);
    }
    
    public String getAccountId() {
        return this.getValueAsString("sida_account");
    }
    
    public void setAccountId(final String value) {
        this.setValue("sida_account", (Object)value);
    }
    
    public String getAccountName() {
        return this.getValueAsString("ssa_account_name");
    }
    
    public void setAccountName(final String value) {
        this.setValue("ssa_account_name", (Object)value);
    }
    
    public String getAccountMemberId() {
        return this.getValueAsString("sida_account_member");
    }
    
    public void setAccountMemberId(final String value) {
        this.setValue("sida_account_member", (Object)value);
    }
    
    public String getAccountMemberName() {
        return this.getValueAsString("ssa_account_member_name");
    }
    
    public void setAccountMemberName(final String value) {
        this.setValue("ssa_account_member_name", (Object)value);
    }
    
    public String getFromBox() {
        return this.getValueAsString("ssa_from_box");
    }
    
    public void setFromBox(final String value) {
        this.setValue("ssa_from_box", (Object)value);
    }
    
    public String getFromBoxGuid() {
        return this.getValueAsString("ssa_from_box_guid");
    }
    
    public void setFromBoxGuid(final String value) {
        this.setValue("ssa_from_box_guid", (Object)value);
    }
    
    public String getFromDepartment() {
        return this.getValueAsString("ssa_sender_department");
    }
    
    public void setFromDepartment(final String value) {
        this.setValue("ssa_sender_department", (Object)value);
    }
    
    public String getFromRegulations() {
        return this.getValueAsString("ssa_from_regulations");
    }
    
    public void setFromRegulations(final String value) {
        this.setValue("ssa_from_regulations", (Object)value);
    }
    
    public String getToName() {
        return this.getValueAsString("ssa_to_name");
    }
    
    public void setToName(final String value) {
        this.setValue("ssa_to_name", (Object)value);
    }
    
    public String getToInn() {
        return this.getValueAsString("ssa_to_inn");
    }
    
    public void setToInn(final String value) {
        this.setValue("ssa_to_inn", (Object)value);
    }
    
    public String getToKpp() {
        return this.getValueAsString("ssa_to_kpp");
    }
    
    public void setToKpp(final String value) {
        this.setValue("ssa_to_kpp", (Object)value);
    }
    
    public String getToBox() {
        return this.getValueAsString("ssa_to_box");
    }
    
    public void setToBox(final String value) {
        this.setValue("ssa_to_box", (Object)value);
    }
    
    public String getToBoxGuid() {
        return this.getValueAsString("ssa_to_box_guid");
    }
    
    public void setToBoxGuid(final String value) {
        this.setValue("ssa_to_box_guid", (Object)value);
    }
    
    public String getToDepartment() {
        return this.getValueAsString("ssa_to_department");
    }
    
    public void setToDepartment(final String value) {
        this.setValue("ssa_to_department", (Object)value);
    }
    
    public String getProxyName() {
        return this.getValueAsString("ssa_proxy_name");
    }
    
    public void setProxyName(final String value) {
        this.setValue("ssa_proxy_name", (Object)value);
    }
    
    public String getProxyInn() {
        return this.getValueAsString("ssa_proxy_inn");
    }
    
    public void setProxyInn(final String value) {
        this.setValue("ssa_proxy_inn", (Object)value);
    }
    
    public String getProxyKpp() {
        return this.getValueAsString("ssa_proxy_kpp");
    }
    
    public void setProxyKpp(final String value) {
        this.setValue("ssa_proxy_kpp", (Object)value);
    }
    
    public String getProxyBox() {
        return this.getValueAsString("ssa_proxy_box");
    }
    
    public void setProxyBox(final String value) {
        this.setValue("ssa_proxy_box", (Object)value);
    }
    
    public String getProxyBoxGuid() {
        return this.getValueAsString("ssa_proxy_box_guid");
    }
    
    public void setProxyBoxGuid(final String value) {
        this.setValue("ssa_proxy_box_guid", (Object)value);
    }
    
    public String getProxyDepartment() {
        return this.getValueAsString("ssa_proxy_department");
    }
    
    public void setProxyDepartment(final String value) {
        this.setValue("ssa_proxy_department", (Object)value);
    }
    
    public boolean isSended() {
        return this.getValueAsBoolean("sba_sended");
    }
    
    public void setSended(final boolean value) {
        this.setValue("sba_sended", (Object)value);
    }
    
    public boolean isContentSended() {
        return this.getValueAsBoolean("sba_content_sended");
    }
    
    public void setContentSended(final boolean value) {
        this.setValue("sba_content_sended", (Object)value);
    }
    
    public Date getSendTime() {
        return this.getValueAsDate("sdta_send_time");
    }
    
    public void setSendTime(final Date value) {
        this.setValue("sdta_send_time", (Object)value);
    }
    
    public Date getQueueTime() {
        return this.getValueAsDate("sdta_queue_time");
    }
    
    public void setQueueTime(final Date value) {
        this.setValue("sdta_queue_time", (Object)value);
    }
    
    public String getPrevLocalState() {
        return this.getValueAsString("ssa_prev_local_state");
    }
    
    public void setPrevLocalState(final String value) {
        this.setValue("ssa_prev_local_state", (Object)value);
    }
    
    public Date getPrevLocalStateTime() {
        return this.getValueAsDate("sdta_prev_local_state_time");
    }
    
    public void setPrevLocalStateTime(final Date value) {
        this.setValue("sdta_prev_local_state_time", (Object)value);
    }
    
    public String getLocalState() {
        return this.getValueAsString("ssa_local_state");
    }
    
    public void setLocalState(final String value) {
        this.setValue("ssa_local_state", (Object)value);
    }
    
    public Date getLocalStateTime() {
        return this.getValueAsDate("sdta_local_state_time");
    }
    
    public void setLocalStateTime(final Date value) {
        this.setValue("sdta_local_state_time", (Object)value);
    }
    
    public String getPrevRemoteState() {
        return this.getValueAsString("ssa_prev_remote_state");
    }
    
    public void setPrevRemoteState(final String value) {
        this.setValue("ssa_prev_remote_state", (Object)value);
    }
    
    public Date getPrevRemoteStateTime() {
        return this.getValueAsDate("sdta_prev_remote_state_time");
    }
    
    public void setPrevRemoteStateTime(final Date value) {
        this.setValue("sdta_prev_remote_state_time", (Object)value);
    }
    
    public String getRemoteState() {
        return this.getValueAsString("ssa_remote_state");
    }
    
    public void setRemoteState(final String value) {
        this.setValue("ssa_remote_state", (Object)value);
    }
    
    public Date getRemoteStateTime() {
        return this.getValueAsDate("sdta_remote_local_state_time");
    }
    
    public void setRemoteStateTime(final Date value) {
        this.setValue("sdta_remote_local_state_time", (Object)value);
    }
    
    public boolean isProceed() {
        return this.getValueAsBoolean("sba_proceed");
    }
    
    public void setProceed(final boolean value) {
        this.setValue("sba_proceed", (Object)value);
    }
    
    public boolean isLocalProceed() {
        return this.getValueAsBoolean("sba_local_proceed");
    }
    
    public void setLocalProceed(final boolean value) {
        this.setValue("sba_local_proceed", (Object)value);
    }
    
    public String getState() {
        return this.getValueAsString("ssa_state");
    }
    
    public void setState(final String value) {
        this.setValue("ssa_state", (Object)value);
    }
    
    public boolean isNotActual() {
        return this.getValueAsBoolean("sba_not_actual");
    }
    
    public void setNotActual(final boolean value) {
        this.setValue("sba_not_actual", (Object)value);
    }
    
    public int getCurrentAttempt() {
        return this.getValueAsInt("sia_current_attempt");
    }
    
    public void setCurrentAttempt(final int value) {
        this.setValue("sia_current_attempt", (Object)value);
    }
    
    public boolean isReady() {
        return this.getValueAsBoolean("sba_ready");
    }
    
    public void setReady(final boolean value) {
        this.setValue("sba_ready", (Object)value);
    }
    
    public boolean isQueued() {
        return this.getValueAsBoolean("sba_queued");
    }
    
    public void setQueued(final boolean value) {
        this.setValue("sba_queued", (Object)value);
    }
    
    public String getPrevLocalStateMessage() {
        return this.getValueAsString("ssa_prev_local_state_message");
    }
    
    public void setPrevLocalStateMessage(final String value) {
        this.setValue("ssa_prev_local_state_message", (Object)value);
    }
    
    public String getLocalStateMessage() {
        return this.getValueAsString("ssa_local_state_message");
    }
    
    public void setLocalStateMessage(final String value) {
        this.setValue("ssa_local_state_message", (Object)value);
    }
    
    public String getPrevRemoteStateMessage() {
        return this.getValueAsString("ssa_prev_remote_state_message");
    }
    
    public void setPrevRemoteStateMessage(final String value) {
        this.setValue("ssa_prev_remote_state_message", (Object)value);
    }
    
    public String getRemoteStateMessage() {
        return this.getValueAsString("ssa_remote_state_message");
    }
    
    public void setRemoteStateMessage(final String value) {
        this.setValue("ssa_remote_state_message", (Object)value);
    }
    
    public String getReiteratableError() {
        return this.getValueAsString("ssa_reiteratable_error");
    }
    
    public void setReiteratableError(final String value) {
        this.setValue("ssa_reiteratable_error", (Object)value);
    }
    
    public String getReiteratableErrorMessage() {
        return this.getValueAsString("ssa_reiteratable_error_message");
    }
    
    public void setReiteratableErrorMessage(final String value) {
        this.setValue("ssa_reiteratable_error_message", (Object)value);
    }
    
    public Date getReiteratableErrorTime() {
        return this.getValueAsDate("sdta_reiteratable_error_time");
    }
    
    public void setReiteratableErrorTime(final Date value) {
        this.setValue("sdta_reiteratable_error_time", (Object)value);
    }
    
    public Date getReiteratableErrorOvertime() {
        return this.getValueAsDate("sdta_reiteratable_error_overtime");
    }
    
    public void setReiteratableErrorOvertime(final Date value) {
        this.setValue("sdta_reiteratable_error_overtime", (Object)value);
    }
    
    public void updateLocalState(final String state, final String message) {
        if (!StringUtils.equals(state, this.getLocalState())) {
            this.setPrevLocalState(this.getLocalState());
            this.setPrevLocalStateMessage(this.getLocalStateMessage());
            this.setPrevLocalStateTime(this.getLocalStateTime());
            this.setLocalState(state);
            this.setLocalStateMessage(message);
            this.setLocalStateTime(Calendar.getInstance().getTime());
            this.setProceed(false);
            this.setLocalProceed(false);
        }
    }
    
    public void updateRemoteState(final String state, final String message) {
        if (!StringUtils.equals(state, this.getRemoteState())) {
            this.setPrevRemoteState(this.getRemoteState());
            this.setPrevRemoteStateMessage(this.getRemoteStateMessage());
            this.setPrevRemoteStateTime(this.getRemoteStateTime());
            this.setRemoteState(state);
            this.setRemoteStateMessage(message);
            this.setRemoteStateTime(Calendar.getInstance().getTime());
            this.setProceed(false);
            this.setRemoteStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued);
        }
    }
    
    public String getNameOnShelf() {
        return this.getValueAsString("ssa_name_on_shelf");
    }
    
    public void setNameOnShelf(final String value) {
        this.setValue("ssa_name_on_shelf", (Object)value);
    }
    
    public void updateFrom(final IEdoIntegrationExchangeItem properties, final boolean onlyDirty) {
        if (properties != null) {
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                if (!onlyDirty || properties.isValueDirty(attr.getAttributeName())) {
                    this.setValue(attr.getAttributeName(), properties.getValue(attr.getAttributeName()));
                }
            }
        }
    }
    
    public String getFromInn() {
        return this.getValueAsString("ssa_from_inn");
    }
    
    public void setFromInn(final String value) {
        this.setValue("ssa_from_inn", (Object)value);
    }
    
    public String getFromKpp() {
        return this.getValueAsString("ssa_from_kpp");
    }
    
    public void setFromKpp(final String value) {
        this.setValue("ssa_from_kpp", (Object)value);
    }
    
    public Date getDeliveryTime() {
        return this.getValueAsDate("sdta_delivery_time");
    }
    
    public void setDeliveryTime(final Date value) {
        this.setValue("sdta_delivery_time", (Object)value);
    }
    
    public long getDeliveryTimestamp() {
        return this.getValueAsLong("sla_delivery_timestamp");
    }
    
    public void setDeliveryTimestamp(final long value) {
        this.setValue("sla_delivery_timestamp", (Object)value);
    }
    
    public void setDocumentUrl(final String value) {
        this.setValue("ssa_document_url", (Object)value);
    }
    
    public String getDocumentUrl() {
        return this.getValueAsString("ssa_document_url");
    }
    
    public String getDocumentType() {
        return this.getValueAsString("ssa_document_type");
    }
    
    public void setDocumentType(final String value) {
        this.setValue("ssa_document_type", (Object)value);
    }
    
    public String getDocumentTypeName() {
        return this.getValueAsString("ssa_document_type_name");
    }
    
    public void setDocumentTypeName(final String value) {
        this.setValue("ssa_document_type_name", (Object)value);
    }
    
    public String getDocumentKindName() {
        return this.getValueAsString("ssa_document_kind_name");
    }
    
    public void setDocumentKindName(final String value) {
        this.setValue("ssa_document_kind_name", (Object)value);
    }
    
    public String getDocumentKindId() {
        return this.getValueAsString("ssa_document_kind_id");
    }
    
    public void setDocumentKindId(final String value) {
        this.setValue("ssa_document_kind_id", (Object)value);
    }
    
    public String getDocumentSubkindName() {
        return this.getValueAsString("ssa_document_subkind_name");
    }
    
    public void setDocumentSubkindName(final String value) {
        this.setValue("ssa_document_subkind_name", (Object)value);
    }
    
    public String getDocumentSubkindId() {
        return this.getValueAsString("ssa_document_subkind_id");
    }
    
    public void setDocumentSubkindId(final String value) {
        this.setValue("ssa_document_subkind_id", (Object)value);
    }
    
    public Date getLastCheckTime() {
        return this.getValueAsDate("sdta_last_check_time");
    }
    
    public void setLastCheckTime(final Date value) {
        this.setValue("sdta_last_check_time", (Object)value);
    }
    
    public String getLastLocalProceedStatus() {
        return this.getValueAsString("ssa_last_local_proceed_status");
    }
    
    public void setLastLocalProceedStatus(final String value) {
        this.setValue("ssa_last_local_proceed_status", (Object)value);
    }
    
    public String getLastRemoteProceedStatus() {
        return this.getValueAsString("ssa_last_remote_proceed_status");
    }
    
    public void setLastRemoteProceedStatus(final String value) {
        this.setValue("ssa_last_remote_proceed_status", (Object)value);
    }
    
    public String getLocalStateSystemCode() {
        return this.getValueAsString("ssa_local_state_system_code");
    }
    
    public void setLocalStateSystemCode(final String value) {
        this.setValue("ssa_local_state_system_code", (Object)value);
    }
    
    public String getPrevLocalStateSystemCode() {
        return this.getValueAsString("ssa_prev_local_state_sys_code");
    }
    
    public void setPrevLocalStateSystemCode(final String value) {
        this.setValue("ssa_prev_local_state_sys_code", (Object)value);
    }
    
    public String getPrevRemoteStateSystemCode() {
        return this.getValueAsString("ssa_prev_remote_state_sys_code");
    }
    
    public void setPrevRemoteStateSystemCode(final String value) {
        this.setValue("ssa_prev_remote_state_sys_code", (Object)value);
    }
    
    public String getRemoteStateSystemCode() {
        return this.getValueAsString("ssa_remote_state_system_code");
    }
    
    public void setRemoteStateSystemCode(final String value) {
        this.setValue("ssa_remote_state_system_code", (Object)value);
    }
    
    public String getStateCategory() {
        return this.getValueAsString("ssa_state_category");
    }
    
    public void setStateCategory(final String value) {
        this.setValue("ssa_state_category", (Object)value);
    }
    
    public String getRemoteStateDisplay() {
        return this.getValueAsString("ssa_remote_state_display");
    }
    
    public void setRemoteStateDisplay(final String value) {
        this.setValue("ssa_remote_state_display", (Object)value);
    }
    
    public String getRemoteStateCode() {
        return this.getValueAsString("ssa_remote_state_code");
    }
    
    public void setRemoteStateCode(final String value) {
        this.setValue("ssa_remote_state_code", (Object)value);
    }
    
    public String getPrevRemoteStateCode() {
        return this.getValueAsString("ssa_prev_remote_state_code");
    }
    
    public void setPrevRemoteStateCode(final String value) {
        this.setValue("ssa_prev_remote_state_code", (Object)value);
    }
    
    public String getRemoteStateProcessingStep() {
        return this.getValueAsString("ssa_remote_state_processing_step");
    }
    
    public void setRemoteStateProcessingStep(final String value) {
        this.setValue("ssa_remote_state_processing_step", (Object)value);
    }
    
    public String getNextItem() {
        return this.getValueAsString("sida_next_item");
    }
    
    public void setNextItem(final String value) {
        this.setValue("sida_next_item", (Object)value);
    }
    
    public String getFollowItem() {
        return this.getValueAsString("sida_follow_item");
    }
    
    public void setFollowItem(final String value) {
        this.setValue("sida_follow_item", (Object)value);
    }
    
    public String getLocalStateDisplay() {
        return this.getValueAsString("ssa_local_state_display");
    }
    
    public void setLocalStateDisplay(final String value) {
        this.setValue("ssa_local_state_display", (Object)value);
    }
    
    public void setLocalStateProcessingStep(final String value) {
        this.setValue("ssa_local_state_processing_step", (Object)value);
    }
    
    public String getDocumentEdoId() {
        return this.getValueAsString("ssa_document_guid");
    }
    
    public void setDocumentEdoId(final String value) {
        this.setValue("ssa_document_guid", (Object)value);
    }
    
    public String getMessageEdoId() {
        return this.getValueAsString("ssa_message_guid");
    }
    
    public void setMessageEdoId(final String value) {
        this.setValue("ssa_message_guid", (Object)value);
    }
    
    public IEdoIntegrationPackageProcessingContext getProcessingContext() {
        return (IEdoIntegrationPackageProcessingContext)this.getValue("processing_context");
    }
    
    public void setProcessingContext(final IEdoIntegrationPackageProcessingContext value) {
        this.setValue("processing_context", (Object)value);
    }
    
    public void setRemoteStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
        this.setValue("ssa_remote_state_processing_step", (Object)((value != null) ? value.name() : null));
    }
    
    public String getLocalStateProcessingStep() {
        return this.getValueAsString("ssa_local_state_processing_step");
    }
    
    public void setLocalStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
        this.setValue("ssa_local_state_processing_step", (Object)((value != null) ? value.name() : null));
    }
    
    public String getProcessingState() {
        return this.getValueAsString("ssa_processing_state");
    }
    
    public void setProcessingState(final EdoIntegrationProcessingState value) {
        this.setValue("ssa_processing_state", (Object)((value != null) ? value.name() : null));
    }
    
    public void setProcessingState(final String value) {
        this.setProcessingState(value);
    }
    
    public String getProcessingError() {
        return this.getValueAsString("ssa_processing_error");
    }
    
    public void setProcessingError(final IEdoIntegrationInboundDataExchangeItem.ProcessingError value) {
        this.setValue("ssa_processing_error", (Object)((value != null) ? value.name() : null));
    }
    
    public void setProcessingError(final String value) {
        this.setProcessingError(value);
    }
    
    public String getCounteragentBoxGuid() {
        return this.getToBox();
    }
    
    public String getItemType() {
        return this.getValueAsString("ssa_item_type");
    }
    
    public void setItemType(final String value) {
        this.setValue("ssa_item_type", (Object)value);
    }
    
    public String getItemOperationCode() {
        return this.getValueAsString("ssa_item_operation_code");
    }
    
    public void setItemOperationCode(final String value) {
        this.setValue("ssa_item_operation_code", (Object)value);
    }
    
    public String getOperationGuid() {
        return this.getValueAsString("ssa_operation_guid");
    }
    
    public void setOperationGuid(final String value) {
        this.setValue("ssa_operation_guid", (Object)value);
    }
    
    public void setRelatedId(final String value) {
        this.setValue("sida_related", (Object)value);
    }
    
    public String getRelatedId() {
        return this.getValueAsString("sida_related");
    }
    
    public void setErrorCount(final int value) {
        this.setValue("sia_error_count", (Object)value);
    }
    
    public int getErrorCount() {
        return this.getValueAsInt("sia_error_count");
    }
}
