// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.StrUtils;
import java.util.Calendar;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.Reader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import java.io.StringReader;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIncomingDocumentRequest;

@ObjectModel(type = "nt_diadoc_edo_incoming_doc_req")
public class DiadocEdoIncomingDocumentRequest extends DmObjectModel implements IDiadocEdoIncomingDocumentRequest
{
    private static final long serialVersionUID = 4450425496830363500L;
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            this.setValue("sida_filial", (Object)po.getString("sida_filial"));
            this.setValue("ssa_filial_name", (Object)po.getString("ssa_filial_name"));
            this.setValue("ssa_key", (Object)po.getString("ssa_key"));
            this.setValue("ssa_message_id", (Object)po.getString("ssa_message_id"));
            this.setValue("ssa_entity_id", (Object)po.getString("ssa_entity_id"));
            this.setValue("ssa_diadoc_type", (Object)po.getString("ssa_diadoc_type"));
            this.setValue("sida_document", (Object)po.getString("sida_document"));
            this.setValue("ssa_document_name", (Object)po.getString("ssa_document_name"));
            this.setValue("ssa_recipient_inn", (Object)po.getString("ssa_recipient_inn"));
            this.setValue("ssa_recipient_kpp", (Object)po.getString("ssa_recipient_kpp"));
            this.setValue("ssa_processing_state", (Object)po.getString("ssa_processing_state"));
            this.setValue("ssa_remote_state_processing_step", (Object)po.getString("ssa_remote_state_processing_step"));
            this.setValue("ssa_local_state_processing_step", (Object)po.getString("ssa_local_state_processing_step"));
            this.setValue("ssa_processing_error", (Object)po.getString("ssa_processing_error"));
            this.setValue("sia_error_count", (Object)po.getInt("sia_error_count"));
            this.setValue("sida_diadoc_account", (Object)po.getString("sida_diadoc_account"));
            this.setValue("ssa_diadoc_account_name", (Object)po.getString("ssa_diadoc_account_name"));
            this.setValue("ssa_diadoc_login", (Object)po.getString("ssa_diadoc_login"));
            this.setValue("ssa_diadoc_box", (Object)po.getString("ssa_diadoc_box"));
            this.setValue("ssa_diadoc_department", (Object)po.getString("ssa_diadoc_department"));
            this.setValue("sida_server", (Object)po.getString("sida_server"));
            this.setValue("ssa_server_name", (Object)po.getString("ssa_server_name"));
            this.setValue("sdta_status_change_time", (Object)po.getDate("sdta_status_change_time"));
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < po.getValueCount("rsa_params"); ++i) {
                sb.append(po.getRepeatingString("rsa_params", i));
            }
            if (!sb.isEmpty()) {
                final StringReader reader = new StringReader(sb.toString());
                final IEdoIntegrationPackageProcessingContext value = (IEdoIntegrationPackageProcessingContext)new ObjectMapper().readValue((Reader)reader, (TypeReference)new TypeReference<EdoIntegrationPackageProcessingContext>() {});
                this.setProcessingContext(value);
            }
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        try {
            super.commitNew(po);
            po.setString("sida_filial", this.getFilialId());
            po.setString("ssa_filial_name", this.getFilialName());
            po.setString("ssa_key", this.getKey());
            po.setString("ssa_message_id", this.getMessageId());
            po.setString("ssa_entity_id", this.getEntityId());
            po.setString("ssa_diadoc_type", this.getDiadocType());
            po.setString("sida_document", this.getDocumentId());
            po.setString("ssa_document_name", this.getDocumentName());
            po.setString("ssa_recipient_inn", this.getRecipientInn());
            po.setString("ssa_recipient_kpp", this.getRecipientKpp());
            po.setString("ssa_processing_state", this.getProcessingState());
            po.setString("ssa_remote_state_processing_step", this.getRemoteStateProcessingStep());
            po.setString("ssa_local_state_processing_step", this.getLocalStateProcessingStep());
            po.setString("ssa_processing_error", this.getProcessingError());
            po.setDate("sdta_status_change_time", Calendar.getInstance().getTime());
            po.setInt("sia_error_count", this.getErrorCount());
            po.setInt("sia_error_attachment_count", this.getErrorAttachmentCount());
            po.setDate("sdta_creation_date", Calendar.getInstance().getTime());
            po.setString("sida_diadoc_account", this.getDiadocAccount());
            po.setString("ssa_diadoc_account_name", this.getDiadocAccountName());
            po.setString("ssa_diadoc_login", this.getDiadocLogin());
            po.setString("ssa_diadoc_box", this.getDiadocBox());
            po.setString("ssa_diadoc_department", this.getDiadocDepartment());
            po.setString("sida_server", this.getServerId());
            po.setString("ssa_server_name", this.getServerName());
            if (this.getProcessingContext() != null) {
                final ObjectMapper mapper = new ObjectMapper();
                final String paramsStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString((Object)this.getProcessingContext());
                final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                po.removeAll("rsa_params");
                for (final String token : paramsArr) {
                    po.appendString("rsa_params", token);
                }
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
            if (this.isValueDirty("sida_filial")) {
                po.setString("sida_filial", this.getFilialId());
            }
            if (this.isValueDirty("ssa_filial_name")) {
                po.setString("ssa_filial_name", this.getFilialName());
            }
            if (this.isValueDirty("ssa_key")) {
                po.setString("ssa_key", this.getKey());
            }
            if (this.isValueDirty("ssa_message_id")) {
                po.setString("ssa_message_id", this.getMessageId());
            }
            if (this.isValueDirty("ssa_entity_id")) {
                po.setString("ssa_entity_id", this.getEntityId());
            }
            if (this.isValueDirty("ssa_diadoc_type")) {
                po.setString("ssa_diadoc_type", this.getDiadocType());
            }
            if (this.isValueDirty("sida_document")) {
                po.setString("sida_document", this.getDocumentId());
            }
            if (this.isValueDirty("ssa_document_name")) {
                po.setString("ssa_document_name", this.getDocumentName());
            }
            if (this.isValueDirty("ssa_recipient_inn")) {
                po.setString("ssa_recipient_inn", this.getRecipientInn());
            }
            if (this.isValueDirty("ssa_recipient_kpp")) {
                po.setString("ssa_recipient_kpp", this.getRecipientKpp());
            }
            if (this.isValueDirty("ssa_processing_state")) {
                po.setString("ssa_processing_state", this.getProcessingState());
            }
            if (this.isValueDirty("ssa_processing_error")) {
                po.setString("ssa_processing_error", this.getProcessingError());
            }
            if (this.isValueDirty("ssa_remote_state_processing_step") && !StringUtils.equals(po.getString("ssa_remote_state_processing_step"), this.getRemoteStateProcessingStep())) {
                po.setString("ssa_remote_state_processing_step", this.getRemoteStateProcessingStep());
                po.setDate("sdta_status_change_time", Calendar.getInstance().getTime());
            }
            if (this.isValueDirty("ssa_local_state_processing_step") && !StringUtils.equals(po.getString("ssa_local_state_processing_step"), this.getLocalStateProcessingStep())) {
                po.setString("ssa_local_state_processing_step", this.getLocalStateProcessingStep());
                po.setDate("sdta_status_change_time", Calendar.getInstance().getTime());
            }
            if (this.isValueDirty("sia_error_count")) {
                po.setInt("sia_error_count", this.getErrorCount());
            }
            if (this.isValueDirty("sia_error_attachment_count")) {
                po.setInt("sia_error_attachment_count", this.getErrorAttachmentCount());
            }
            if (this.isValueDirty("sida_diadoc_account")) {
                po.setString("sida_diadoc_account", this.getDiadocAccount());
            }
            if (this.isValueDirty("ssa_diadoc_account_name")) {
                po.setString("ssa_diadoc_account_name", this.getDiadocAccountName());
            }
            if (this.isValueDirty("ssa_diadoc_login")) {
                po.setString("ssa_diadoc_login", this.getDiadocLogin());
            }
            if (this.isValueDirty("ssa_diadoc_box")) {
                po.setString("ssa_diadoc_box", this.getDiadocBox());
            }
            if (this.isValueDirty("ssa_diadoc_department")) {
                po.setString("ssa_diadoc_department", this.getDiadocDepartment());
            }
            if (this.isValueDirty("sida_server")) {
                po.setString("sida_server", this.getServerId());
            }
            if (this.isValueDirty("ssa_server_name")) {
                po.setString("ssa_server_name", this.getServerName());
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
        }
        catch (final ServiceException e) {
            throw e;
        }
        catch (final Exception e2) {
            throw new ServiceException(e2);
        }
    }
    
    public String getFilialId() {
        return this.getValueAsString("sida_filial");
    }
    
    public void setFilialId(final String value) {
        this.setValue("sida_filial", (Object)value);
    }
    
    public String getKey() {
        return this.getValueAsString("ssa_key");
    }
    
    public void setKey(final String value) {
        this.setValue("ssa_key", (Object)value);
    }
    
    public String getFilialName() {
        return this.getValueAsString("ssa_filial_name");
    }
    
    public void setFilialName(final String value) {
        this.setValue("ssa_filial_name", (Object)value);
    }
    
    public String getDocumentEdoId() {
        return this.getEntityId();
    }
    
    public void setDocumentEdoId(final String value) {
        this.setEntityId(value);
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
    
    public Date getCreationDate() {
        return this.getValueAsDate("sdta_creation_date");
    }
    
    public String getRecipientInn() {
        return this.getValueAsString("ssa_recipient_inn");
    }
    
    public void setRecipientInn(final String value) {
        this.setValue("ssa_recipient_inn", (Object)value);
    }
    
    public String getRecipientKpp() {
        return this.getValueAsString("ssa_recipient_kpp");
    }
    
    public void setRecipientKpp(final String value) {
        this.setValue("ssa_recipient_kpp", (Object)value);
    }
    
    public String getRemoteStateProcessingStep() {
        return this.getValueAsString("ssa_remote_state_processing_step");
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
    
    public String getProcessingError() {
        return this.getValueAsString("ssa_processing_error");
    }
    
    public void setProcessingError(final IEdoIntegrationInboundDataExchangeItem.ProcessingError value) {
        this.setValue("ssa_processing_error", (Object)((value != null) ? value.name() : null));
    }
    
    public int getErrorCount() {
        return this.getValueAsInt("sia_error_count");
    }
    
    public void setErrorCount(final int value) {
        this.setValue("sia_error_count", (Object)value);
    }
    
    public int getErrorAttachmentCount() {
        return this.getValueAsInt("sia_error_attachment_count");
    }
    
    public void setErrorAttachmentCount(final int value) {
        this.setValue("sia_error_attachment_count", (Object)value);
    }
    
    public void setDiadocAccount(final String value) {
        this.setValue("sida_diadoc_account", (Object)value);
    }
    
    public String getDiadocAccount() {
        return this.getValueAsString("sida_diadoc_account");
    }
    
    public void setDiadocAccountName(final String value) {
        this.setValue("ssa_diadoc_account_name", (Object)value);
    }
    
    public String getDiadocAccountName() {
        return this.getValueAsString("ssa_diadoc_account_name");
    }
    
    public void setDiadocLogin(final String value) {
        this.setValue("ssa_diadoc_login", (Object)value);
    }
    
    public String getDiadocLogin() {
        return this.getValueAsString("ssa_diadoc_login");
    }
    
    public String getDiadocBox() {
        return this.getValueAsString("ssa_diadoc_box");
    }
    
    public void setDiadocBox(final String value) {
        this.setValue("ssa_diadoc_box", (Object)value);
    }
    
    public String getDiadocDepartment() {
        return this.getValueAsString("ssa_diadoc_department");
    }
    
    public void setDiadocDepartment(final String value) {
        this.setValue("ssa_diadoc_department", (Object)value);
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
    
    public String getDiadocType() {
        return this.getValueAsString("ssa_diadoc_type");
    }
    
    public void setDiadocType(final String value) {
        this.setValue("ssa_diadoc_type", (Object)value);
    }
    
    public String getEntityId() {
        return this.getValueAsString("ssa_entity_id");
    }
    
    public void setEntityId(final String value) {
        this.setValue("ssa_entity_id", (Object)value);
    }
    
    public String getMessageId() {
        return this.getValueAsString("ssa_message_id");
    }
    
    public void setMessageId(final String value) {
        this.setValue("ssa_message_id", (Object)value);
    }
    
    public IEdoIntegrationPackageProcessingContext getProcessingContext() {
        return (IEdoIntegrationPackageProcessingContext)this.getValue("processing_context");
    }
    
    public void setProcessingContext(final IEdoIntegrationPackageProcessingContext value) {
        this.setValue("processing_context", (Object)value);
    }
    
    public String getMessageEdoId() {
        return this.getMessageId();
    }
    
    public void setMessageEdoId(final String value) {
        this.setMessageId(value);
    }
    
    public String getCounteragentBoxGuid() {
        return null;
    }
}
