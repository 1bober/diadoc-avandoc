// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.function.Supplier;
import java.util.Date;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.wstack.net.client.contentserver.IObject;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationLogRecord;

public class EdoIntegrationLogRecord extends DmObjectModel implements IEdoIntegrationLogRecord
{
    private static final long serialVersionUID = 46272583377907066L;
    
    protected void populateAttribute(final String attrName, final IObject po) {
        this.setValue(attrName, (Object)po.getString(attrName));
    }
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            this.populateAttribute("ssa_integration", po);
            this.populateAttribute("ssa_direction", po);
            this.populateAttribute("ssa_operation", po);
            this.populateAttribute("ssa_message", po);
            this.populateAttribute("ssa_result_code", po);
            this.populateAttribute("ssa_document_type", po);
            this.populateAttribute("sida_document", po);
            this.populateAttribute("ssa_document_name", po);
            this.populateAttribute("sida_container", po);
            this.populateAttribute("sida_counteragent", po);
            this.populateAttribute("ssa_counteragent_name", po);
            this.populateAttribute("sida_content", po);
            this.populateAttribute("ssa_content_name", po);
            this.populateAttribute("sida_sender", po);
            this.populateAttribute("ssa_sender_name", po);
            this.populateAttribute("sida_filial", po);
            this.populateAttribute("ssa_filial_name", po);
            this.populateAttribute("sida_server", po);
            this.populateAttribute("ssa_server_name", po);
            this.populateAttribute("sida_account", po);
            this.populateAttribute("ssa_account_name", po);
            this.populateAttribute("ssa_from_inn", po);
            this.populateAttribute("ssa_from_kpp", po);
            this.populateAttribute("ssa_from_edo_box", po);
            this.populateAttribute("ssa_from_edo_filial", po);
            this.populateAttribute("ssa_from_edo_department", po);
            this.populateAttribute("ssa_to_name", po);
            this.populateAttribute("ssa_to_inn", po);
            this.populateAttribute("ssa_to_kpp", po);
            this.populateAttribute("ssa_to_edo_box", po);
            this.populateAttribute("ssa_to_edo_department", po);
            this.populateAttribute("ssa_to_proxy_name", po);
            this.populateAttribute("ssa_to_proxy_inn", po);
            this.populateAttribute("ssa_to_proxy_kpp", po);
            this.populateAttribute("ssa_to_proxy_edo_box", po);
            this.populateAttribute("ssa_to_proxy_edo_department", po);
            this.populateAttribute("ssa_edo_message_id", po);
            this.populateAttribute("ssa_edo_document_id", po);
            this.setValue("sdba_delivery_timestamp", (Object)po.getDouble("sdba_delivery_timestamp"));
            this.setValue("sdta_creation_date", (Object)po.getDate("sdta_creation_date"));
            this.populateAttribute("ssa_local_state", po);
            this.populateAttribute("ssa_local_status", po);
            this.populateAttribute("ssa_remote_state", po);
            this.populateAttribute("ssa_remote_status", po);
            this.setValue("sia_current_attempt", (Object)po.getInt("sia_current_attempt"));
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        super.commitNew(po);
        po.setString("ssa_integration", this.getIntegration());
        po.setString("ssa_direction", this.getDirection());
        po.setString("ssa_operation", this.getOperation());
        po.setString("ssa_message", this.getMessage());
        po.setString("ssa_result_code", this.getResultCode());
        po.setString("ssa_document_type", this.getDocumentType());
        po.setString("sida_document", this.getDocumentId());
        po.setString("ssa_document_name", this.getDocumentName());
        po.setString("sida_container", this.getContainerId());
        po.setString("sida_counteragent", this.getCounteragentId());
        po.setString("ssa_counteragent_name", this.getCounteragentName());
        po.setString("sida_content", this.getContentId());
        po.setString("ssa_content_name", this.getContentName());
        po.setString("sida_sender", this.getSenderId());
        po.setString("ssa_sender_name", this.getSenderName());
        po.setString("sida_filial", this.getFilialId());
        po.setString("ssa_filial_name", this.getFilialName());
        po.setString("sida_server", this.getServer());
        po.setString("ssa_server_name", this.getServerName());
        po.setString("sida_account", this.getAccount());
        po.setString("ssa_account_name", this.getAccountName());
        po.setString("ssa_from_inn", this.getFromInn());
        po.setString("ssa_from_kpp", this.getFromKpp());
        po.setString("ssa_from_edo_box", this.getFromEdoBox());
        po.setString("ssa_from_edo_filial", this.getFromEdoFilial());
        po.setString("ssa_from_edo_department", this.getFromEdoDepartment());
        po.setString("ssa_to_name", this.getToName());
        po.setString("ssa_to_inn", this.getToInn());
        po.setString("ssa_to_kpp", this.getToKpp());
        po.setString("ssa_to_edo_box", this.getToEdoBox());
        po.setString("ssa_to_edo_department", this.getToEdoDepartment());
        po.setString("ssa_to_proxy_name", this.getToProxyName());
        po.setString("ssa_to_proxy_inn", this.getToProxyInn());
        po.setString("ssa_to_proxy_kpp", this.getToProxyKpp());
        po.setString("ssa_to_proxy_edo_box", this.getToProxyEdoBox());
        po.setString("ssa_to_proxy_edo_department", this.getToProxyEdoDepartment());
        po.setString("ssa_edo_message_id", this.getEdoMessageId());
        po.setString("ssa_edo_document_id", this.getEdoDocumentId());
        po.setDouble("sdba_delivery_timestamp", this.getDeliveryTimestamp());
        po.setDate("sdta_creation_date", new Date());
        po.setString("ssa_local_state", this.getLocalState());
        po.setString("ssa_local_status", this.getLocalStatus());
        po.setString("ssa_remote_state", this.getRemoteState());
        po.setString("ssa_remote_status", this.getRemoteStatus());
        po.setInt("sia_current_attempt", this.getCurrentAttempt());
    }
    
    protected void setStringIfDirty(final IObject po, final String attrName, final Supplier<String> getter) {
        if (this.isValueDirty(attrName)) {
            po.setString(attrName, (String)getter.get());
        }
    }
    
    public void commitSaved(final IObject po) {
        super.commitSaved(po);
        this.setStringIfDirty(po, "ssa_integration", this::getIntegration);
        this.setStringIfDirty(po, "ssa_direction", this::getDirection);
        this.setStringIfDirty(po, "ssa_operation", this::getOperation);
        this.setStringIfDirty(po, "ssa_message", this::getMessage);
        this.setStringIfDirty(po, "ssa_result_code", this::getResultCode);
        this.setStringIfDirty(po, "ssa_document_type", this::getDocumentType);
        this.setStringIfDirty(po, "sida_document", this::getDocumentId);
        this.setStringIfDirty(po, "ssa_document_name", this::getDocumentName);
        this.setStringIfDirty(po, "sida_container", this::getContainerId);
        this.setStringIfDirty(po, "sida_counteragent", this::getCounteragentId);
        this.setStringIfDirty(po, "ssa_counteragent_name", this::getCounteragentName);
        this.setStringIfDirty(po, "sida_content", this::getContentId);
        this.setStringIfDirty(po, "ssa_content_name", this::getContentName);
        this.setStringIfDirty(po, "sida_sender", this::getSenderId);
        this.setStringIfDirty(po, "ssa_sender_name", this::getSenderName);
        this.setStringIfDirty(po, "sida_filial", this::getFilialId);
        this.setStringIfDirty(po, "ssa_filial_name", this::getFilialName);
        this.setStringIfDirty(po, "sida_server", this::getServer);
        this.setStringIfDirty(po, "ssa_server_name", this::getServerName);
        this.setStringIfDirty(po, "sida_account", this::getAccount);
        this.setStringIfDirty(po, "ssa_account_name", this::getAccountName);
        this.setStringIfDirty(po, "ssa_from_inn", this::getFromInn);
        this.setStringIfDirty(po, "ssa_from_kpp", this::getFromKpp);
        this.setStringIfDirty(po, "ssa_from_edo_box", this::getFromEdoBox);
        this.setStringIfDirty(po, "ssa_from_edo_filial", this::getFromEdoFilial);
        this.setStringIfDirty(po, "ssa_from_edo_department", this::getFromEdoDepartment);
        this.setStringIfDirty(po, "ssa_to_name", this::getToName);
        this.setStringIfDirty(po, "ssa_to_inn", this::getToInn);
        this.setStringIfDirty(po, "ssa_to_kpp", this::getToKpp);
        this.setStringIfDirty(po, "ssa_to_edo_box", this::getToEdoBox);
        this.setStringIfDirty(po, "ssa_to_edo_department", this::getToEdoDepartment);
        this.setStringIfDirty(po, "ssa_to_proxy_name", this::getToProxyName);
        this.setStringIfDirty(po, "ssa_to_proxy_inn", this::getToProxyInn);
        this.setStringIfDirty(po, "ssa_to_proxy_kpp", this::getToProxyKpp);
        this.setStringIfDirty(po, "ssa_to_proxy_edo_box", this::getToProxyEdoBox);
        this.setStringIfDirty(po, "ssa_to_proxy_edo_department", this::getToProxyEdoDepartment);
        this.setStringIfDirty(po, "ssa_edo_message_id", this::getEdoMessageId);
        this.setStringIfDirty(po, "ssa_edo_document_id", this::getEdoDocumentId);
        po.setDouble("sdba_delivery_timestamp", this.getDeliveryTimestamp());
        this.setStringIfDirty(po, "ssa_local_state", this::getLocalState);
        this.setStringIfDirty(po, "ssa_local_status", this::getLocalStatus);
        this.setStringIfDirty(po, "ssa_remote_state", this::getRemoteState);
        this.setStringIfDirty(po, "ssa_remote_status", this::getRemoteStatus);
        po.setInt("sia_current_attempt", this.getCurrentAttempt());
    }
    
    public String getIntegration() {
        return this.getValueAsString("ssa_integration");
    }
    
    public void getIntegration(final String value) {
        this.setValue("ssa_integration", (Object)value);
    }
    
    public String getDirection() {
        return this.getValueAsString("ssa_direction");
    }
    
    public void setDirection(final String value) {
        this.setValue("ssa_direction", (Object)value);
    }
    
    public String getOperation() {
        return this.getValueAsString("ssa_operation");
    }
    
    public void setOperation(final String value) {
        this.setValue("ssa_operation", (Object)value);
    }
    
    public String getMessage() {
        return this.getValueAsString("ssa_message");
    }
    
    public void setMessage(final String value) {
        this.setValue("ssa_message", (Object)value);
    }
    
    public String getDocumentType() {
        return this.getValueAsString("ssa_document_type");
    }
    
    public void setDocumentType(final String value) {
        this.setValue("ssa_document_type", (Object)value);
    }
    
    public String getDocumentId() {
        return this.getValueAsString("sida_document");
    }
    
    public void setDocumentId(final String value) {
        this.setValue("sida_document", (Object)value);
    }
    
    public String getDocumentName() {
        return this.getValueAsString("ssa_document_name");
    }
    
    public void setDocumentName(final String value) {
        this.setValue("ssa_document_name", (Object)value);
    }
    
    public String getContainerId() {
        return this.getValueAsString("sida_container");
    }
    
    public void setContainerId(final String value) {
        this.setValue("sida_container", (Object)value);
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
    
    public String getContentId() {
        return this.getValueAsString("sida_content");
    }
    
    public void setContentId(final String value) {
        this.setValue("sida_content", (Object)value);
    }
    
    public String getContentName() {
        return this.getValueAsString("ssa_content_name");
    }
    
    public void setContentName(final String value) {
        this.setValue("ssa_content_name", (Object)value);
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
    
    public String getServer() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServer(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getAccount() {
        return this.getValueAsString("sida_account");
    }
    
    public void setAccount(final String value) {
        this.setValue("sida_account", (Object)value);
    }
    
    public String getAccountName() {
        return this.getValueAsString("ssa_account_name");
    }
    
    public void setAccountName(final String value) {
        this.setValue("ssa_account_name", (Object)value);
    }
    
    public String getServerName() {
        return this.getValueAsString("ssa_server_name");
    }
    
    public void setServerName(final String value) {
        this.setValue("ssa_server_name", (Object)value);
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
    
    public String getFromEdoBox() {
        return this.getValueAsString("ssa_from_edo_box");
    }
    
    public void setFromEdoBox(final String value) {
        this.setValue("ssa_from_edo_box", (Object)value);
    }
    
    public String getFromEdoFilial() {
        return this.getValueAsString("ssa_from_edo_filial");
    }
    
    public void setFromEdoFilial(final String value) {
        this.setValue("ssa_from_edo_filial", (Object)value);
    }
    
    public String getFromEdoDepartment() {
        return this.getValueAsString("ssa_from_edo_department");
    }
    
    public void setFromEdoDepartment(final String value) {
        this.setValue("ssa_from_edo_department", (Object)value);
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
    
    public String getToEdoBox() {
        return this.getValueAsString("ssa_to_edo_box");
    }
    
    public void setToEdoBox(final String value) {
        this.setValue("ssa_to_edo_box", (Object)value);
    }
    
    public String getToEdoDepartment() {
        return this.getValueAsString("ssa_to_edo_department");
    }
    
    public void setToEdoDepartment(final String value) {
        this.setValue("ssa_to_edo_department", (Object)value);
    }
    
    public String getToProxyName() {
        return this.getValueAsString("ssa_to_proxy_name");
    }
    
    public void setToProxyName(final String value) {
        this.setValue("ssa_to_proxy_name", (Object)value);
    }
    
    public String getToProxyInn() {
        return this.getValueAsString("ssa_to_proxy_inn");
    }
    
    public void setToProxyInn(final String value) {
        this.setValue("ssa_to_proxy_inn", (Object)value);
    }
    
    public String getToProxyKpp() {
        return this.getValueAsString("ssa_to_proxy_kpp");
    }
    
    public void setToProxyKpp(final String value) {
        this.setValue("ssa_to_proxy_kpp", (Object)value);
    }
    
    public String getToProxyEdoBox() {
        return this.getValueAsString("ssa_to_proxy_edo_box");
    }
    
    public void setToProxyEdoBox(final String value) {
        this.setValue("ssa_to_proxy_edo_box", (Object)value);
    }
    
    public String getToProxyEdoDepartment() {
        return this.getValueAsString("ssa_to_proxy_edo_department");
    }
    
    public void setToProxyEdoDepartment(final String value) {
        this.setValue("ssa_to_proxy_edo_department", (Object)value);
    }
    
    public String getEdoMessageId() {
        return this.getValueAsString("ssa_edo_message_id");
    }
    
    public void setEdoMessageId(final String value) {
        this.setValue("ssa_edo_message_id", (Object)value);
    }
    
    public String getEdoDocumentId() {
        return this.getValueAsString("ssa_edo_document_id");
    }
    
    public void setEdoDocumentId(final String value) {
        this.setValue("ssa_edo_document_id", (Object)value);
    }
    
    public double getDeliveryTimestamp() {
        return this.getValueAsDouble("sdba_delivery_timestamp");
    }
    
    public void setDeliveryTimestamp(final double value) {
        this.setValue("sdba_delivery_timestamp", (Object)value);
    }
    
    public Date getCreationDate() {
        return this.getValueAsDate("sdta_creation_date");
    }
    
    public void setCreationDate(final Date value) {
        this.setValue("sdta_creation_date", (Object)value);
    }
    
    public String getLocalState() {
        return this.getValueAsString("ssa_local_state");
    }
    
    public void setLocalState(final String value) {
        this.setValue("ssa_local_state", (Object)value);
    }
    
    public String getLocalStatus() {
        return this.getValueAsString("ssa_local_status");
    }
    
    public void setLocalStatus(final String value) {
        this.setValue("ssa_local_status", (Object)value);
    }
    
    public String getRemoteState() {
        return this.getValueAsString("ssa_remote_state");
    }
    
    public void setRemoteState(final String value) {
        this.setValue("ssa_remote_state", (Object)value);
    }
    
    public String getRemoteStatus() {
        return this.getValueAsString("ssa_remote_status");
    }
    
    public void setRemoteStatus(final String value) {
        this.setValue("ssa_remote_status", (Object)value);
    }
    
    public String getResultCode() {
        return this.getValueAsString("ssa_result_code");
    }
    
    public void setResultCode(final String value) {
        this.setValue("ssa_result_code", (Object)value);
    }
    
    public int getCurrentAttempt() {
        return this.getValueAsInt("sia_current_attempt");
    }
    
    public void setCurrentAttempt(final int value) {
        this.setValue("sia_current_attempt", (Object)value);
    }
}
