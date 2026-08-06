// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Date;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccount;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Iterator;
import java.util.List;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.ArrayList;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccountMember;

@ObjectModel(type = "nt_diadoc_edo_account_member")
public class DiadocEdoAccountMember extends DmObjectModel implements IDiadocEdoAccountMember
{
    private static final long serialVersionUID = -8563375827069234819L;
    
    public void populate(final IClientSession session, final IObject po) {
        try {
            this.setValue("sba_enabled", (Object)po.getBoolean("sba_enabled"));
            this.setValue("ssa_box", (Object)po.getString("ssa_box"));
            this.setValue("ssa_department", (Object)po.getString("ssa_department"));
            this.setValue("ssa_out_department", (Object)po.getString("ssa_out_department"));
            this.setValue("ssa_in_department", (Object)po.getString("ssa_in_department"));
            this.setValue("ssa_inn", (Object)po.getString("ssa_inn"));
            this.setValue("ssa_kpp", (Object)po.getString("ssa_kpp"));
            this.setValue("sia_dont_chk_cntagent_sign_cert", (Object)po.getInt("sia_dont_chk_cntagent_sign_cert"));
            this.setValue("ssa_dont_chk_ca_sign_cert_name", (Object)po.getString("ssa_dont_chk_ca_sign_cert_name"));
            this.setValue("sba_remote_signature_mode", (Object)po.getBoolean("sba_remote_signature_mode"));
            this.setValue("sida_filial", (Object)po.getString("sida_filial"));
            this.setValue("ssa_filial_name", (Object)po.getString("ssa_filial_name"));
            this.setValue("sida_account", (Object)po.getString("sida_account"));
            this.setValue("ssa_account_name", (Object)po.getString("ssa_account_name"));
            this.setValue("ssa_non_invited_send_mode", (Object)po.getString("ssa_non_invited_send_mode"));
            this.setValue("ssa_non_invited_send_mode_name", (Object)po.getString("ssa_non_invited_send_mode_name"));
            this.setValue("sba_allow_send_to_rejected", (Object)po.getBoolean("sba_allow_send_to_rejected"));
            this.setValue("sia_log_propogate_mode", (Object)po.getInt("sia_log_propogate_mode"));
            this.setValue("sia_inc_docs_check_offset", (Object)po.getInt("sia_inc_docs_check_offset"));
            this.setValue("sba_dont_check_incoming", (Object)po.getBoolean("sba_dont_check_incoming"));
            this.setValue("sdta_inc_docs_last_check_date", (Object)po.getDate("sdta_inc_docs_last_check_date"));
            this.setValue("sdta_events_last_check_date", (Object)po.getDate("sdta_events_last_check_date"));
            this.setValue("sdta_inc_docs_date_check_from", (Object)po.getDate("sdta_inc_docs_date_check_from"));
            this.setValue("sdta_events_date_check_from", (Object)po.getDate("sdta_events_date_check_from"));
            this.setValue("sba_allow_use_test_counteragents", (Object)po.getBoolean("sba_allow_use_test_counteragents"));
            int cnt = po.getValueCount("rsa_outbound_category");
            List<String> values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_outbound_category", i));
            }
            this.setOutboundCategories(values);
            values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_outbound_category_name", i));
            }
            this.setOutboundCategoryNames(values);
            values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_outbound_category_department", i));
            }
            this.setOutboundCategoryDepartments(values);
            cnt = po.getValueCount("rsa_inbound_category");
            values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_inbound_category", i));
            }
            this.setInboundCategories(values);
            values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_inbound_category_name", i));
            }
            this.setInboundCategoryNames(values);
            values = new ArrayList<String>();
            for (int i = 0; i < cnt; ++i) {
                values.add(po.getRepeatingString("rsa_inbound_category_department", i));
            }
            this.setInboundCategoryDepartments(values);
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        po.setBoolean("sba_enabled", this.isEnabled());
        po.setString("ssa_box", this.getBox());
        po.setString("ssa_department", this.getDepartment());
        po.setString("ssa_in_department", this.getInboundDepartment());
        po.setString("ssa_out_department", this.getOutboundDepartment());
        po.setString("ssa_inn", this.getInn());
        po.setString("ssa_kpp", this.getKpp());
        po.setInt("sia_dont_chk_cntagent_sign_cert", this.getDontCheckCounteragentSignCertificate());
        po.setString("ssa_dont_chk_ca_sign_cert_name", this.getDontCheckCounteragentSignCertificateName());
        po.setBoolean("sba_remote_signature_mode", this.isRemoteSignatureMode());
        po.setString("sida_filial", this.getFilial());
        po.setString("ssa_filial_name", this.getFilialName());
        po.setString("sida_account", this.getAccount());
        po.setString("ssa_account_name", this.getAccountName());
        po.setString("ssa_non_invited_send_mode_name", this.getNonInvitedSendModeName());
        po.setString("ssa_non_invited_send_mode", this.getNonInvitedSendMode());
        po.setBoolean("sba_allow_send_to_rejected", this.isAllowSendToRejected());
        po.setBoolean("sba_allow_use_test_counteragents", this.isAllowSendToTestCounteragents());
        po.setInt("sia_log_propogate_mode", this.getLogPropogateMode());
        po.setInt("sia_inc_docs_check_offset", this.getIncDocsCheckOffset());
        po.setBoolean("sba_dont_check_incoming", this.isDontCheckIncoming());
        po.setDate("sdta_inc_docs_last_check_date", this.getIncomingDocumentsLastCheckDate());
        po.setDate("sdta_events_last_check_date", this.getEventsLastCheckDate());
        po.setDate("sdta_inc_docs_last_check_date", this.getIncomingDocumentsLastCheckDate());
        po.setDate("sdta_events_last_check_date", this.getEventsLastCheckDate());
        po.setDate("sdta_inc_docs_date_check_from", this.getIncomingDocumentsDateCheckFrom());
        po.setDate("sdta_events_date_check_from", this.getEventsDateCheckFrom());
        po.removeAll("rsa_outbound_category");
        po.removeAll("rsa_outbound_category_name");
        po.removeAll("rsa_outbound_category_department");
        for (final String value : this.getOutboundCategories()) {
            po.appendString("rsa_outbound_category", value);
        }
        for (final String value : this.getOutboundCategoryNames()) {
            po.appendString("rsa_outbound_category_name", value);
        }
        for (final String value : this.getOutboundCategoryDepartments()) {
            po.appendString("rsa_outbound_category_department", value);
        }
        po.removeAll("rsa_inbound_category");
        po.removeAll("rsa_inbound_category_name");
        po.removeAll("rsa_inbound_category_department");
        for (final String value : this.getInboundCategories()) {
            po.appendString("rsa_inbound_category", value);
        }
        for (final String value : this.getInboundCategoryNames()) {
            po.appendString("rsa_inbound_category_name", value);
        }
        for (final String value : this.getInboundCategoryDepartments()) {
            po.appendString("rsa_inbound_category_department", value);
        }
    }
    
    public String getNonInvitedSendMode() {
        return this.getValueAsString("ssa_non_invited_send_mode");
    }
    
    public void setNonInvitedSendMode(final String value) {
        this.setValue("ssa_non_invited_send_mode", (Object)value);
    }
    
    public String getNonInvitedSendModeName() {
        return this.getValueAsString("ssa_non_invited_send_mode_name");
    }
    
    public void setNonInvitedSendModeName(final String value) {
        this.setValue("ssa_non_invited_send_mode_name", (Object)value);
    }
    
    public void commitSaved(final IObject po) {
        if (this.isValueDirty("sba_enabled")) {
            po.setBoolean("sba_enabled", this.isEnabled());
        }
        if (this.isValueDirty("ssa_box")) {
            po.setString("ssa_box", this.getBox());
        }
        if (this.isValueDirty("ssa_department")) {
            po.setString("ssa_department", this.getDepartment());
        }
        if (this.isValueDirty("ssa_in_department")) {
            po.setString("ssa_in_department", this.getInboundDepartment());
        }
        if (this.isValueDirty("ssa_out_department")) {
            po.setString("ssa_out_department", this.getOutboundDepartment());
        }
        if (this.isValueDirty("ssa_inn")) {
            po.setString("ssa_inn", this.getInn());
        }
        if (this.isValueDirty("ssa_kpp")) {
            po.setString("ssa_kpp", this.getKpp());
        }
        if (this.isValueDirty("sba_dont_check_incoming")) {
            po.setBoolean("sba_dont_check_incoming", this.isDontCheckIncoming());
        }
        if (this.isValueDirty("sia_dont_chk_cntagent_sign_cert")) {
            po.setInt("sia_dont_chk_cntagent_sign_cert", this.getDontCheckCounteragentSignCertificate());
        }
        if (this.isValueDirty("ssa_dont_chk_ca_sign_cert_name")) {
            po.setString("ssa_dont_chk_ca_sign_cert_name", this.getDontCheckCounteragentSignCertificateName());
        }
        if (this.isValueDirty("sba_remote_signature_mode")) {
            po.setBoolean("sba_remote_signature_mode", this.isRemoteSignatureMode());
        }
        if (this.isValueDirty("sba_allow_use_test_counteragents")) {
            po.setBoolean("sba_allow_use_test_counteragents", this.isAllowSendToTestCounteragents());
        }
        if (this.isValueDirty("sida_filial")) {
            po.setString("sida_filial", this.getFilial());
        }
        if (this.isValueDirty("ssa_filial_name")) {
            po.setString("ssa_filial_name", this.getFilialName());
        }
        if (this.isValueDirty("sida_account")) {
            po.setString("sida_account", this.getAccount());
        }
        if (this.isValueDirty("ssa_account_name")) {
            po.setString("ssa_account_name", this.getAccountName());
        }
        if (this.isValueDirty("ssa_non_invited_send_mode_name")) {
            po.setString("ssa_non_invited_send_mode_name", this.getNonInvitedSendModeName());
        }
        if (this.isValueDirty("ssa_non_invited_send_mode")) {
            po.setString("ssa_non_invited_send_mode", this.getNonInvitedSendMode());
        }
        if (this.isValueDirty("sba_allow_send_to_rejected")) {
            po.setBoolean("sba_allow_send_to_rejected", this.isAllowSendToRejected());
        }
        if (this.isValueDirty("sia_log_propogate_mode")) {
            po.setInt("sia_log_propogate_mode", this.getLogPropogateMode());
        }
        if (this.isValueDirty("sia_inc_docs_check_offset")) {
            po.setInt("sia_inc_docs_check_offset", this.getIncDocsCheckOffset());
        }
        if (this.isValueDirty("sdta_inc_docs_last_check_date")) {
            po.setDate("sdta_inc_docs_last_check_date", this.getIncomingDocumentsLastCheckDate());
        }
        if (this.isValueDirty("sdta_events_last_check_date")) {
            po.setDate("sdta_events_last_check_date", this.getEventsLastCheckDate());
        }
        if (this.isValueDirty("sdta_inc_docs_date_check_from")) {
            po.setDate("sdta_inc_docs_date_check_from", this.getIncomingDocumentsDateCheckFrom());
        }
        if (this.isValueDirty("sdta_events_date_check_from")) {
            po.setDate("sdta_events_date_check_from", this.getEventsDateCheckFrom());
        }
        if (this.isValueDirty("rsa_outbound_category")) {
            po.removeAll("rsa_outbound_category");
            po.removeAll("rsa_outbound_category_name");
            for (final String value : this.getOutboundCategories()) {
                po.appendString("rsa_outbound_category", value);
            }
            for (final String value : this.getOutboundCategoryNames()) {
                po.appendString("rsa_outbound_category_name", value);
            }
        }
        po.removeAll("rsa_outbound_category_department");
        for (final String value : this.getOutboundCategoryDepartments()) {
            po.appendString("rsa_outbound_category_department", value);
        }
        if (this.isValueDirty("rsa_inbound_category")) {
            po.removeAll("rsa_inbound_category");
            po.removeAll("rsa_inbound_category_name");
            for (final String value : this.getInboundCategories()) {
                po.appendString("rsa_inbound_category", value);
            }
            for (final String value : this.getInboundCategoryNames()) {
                po.appendString("rsa_inbound_category_name", value);
            }
        }
        po.removeAll("rsa_inbound_category_department");
        for (final String value : this.getInboundCategoryDepartments()) {
            po.appendString("rsa_inbound_category_department", value);
        }
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public String getBox() {
        return this.getValueAsString("ssa_box");
    }
    
    public void setBox(final String value) {
        this.setValue("ssa_box", (Object)value);
    }
    
    public String getDepartment() {
        return this.getValueAsString("ssa_department");
    }
    
    public void setDepartment(final String value) {
        this.setValue("ssa_department", (Object)value);
    }
    
    public String getInboundDepartment() {
        return this.getValueAsString("ssa_in_department");
    }
    
    public void setInboundDepartment(final String value) {
        this.setValue("ssa_in_department", (Object)value);
    }
    
    public String getOutboundDepartment() {
        return this.getValueAsString("ssa_out_department");
    }
    
    public void setOutboundDepartment(final String value) {
        this.setValue("ssa_out_department", (Object)value);
    }
    
    public String getInn() {
        return this.getValueAsString("ssa_inn");
    }
    
    public void setInn(final String value) {
        this.setValue("ssa_inn", (Object)value);
    }
    
    public String getKpp() {
        return this.getValueAsString("ssa_kpp");
    }
    
    public void setKpp(final String value) {
        this.setValue("ssa_kpp", (Object)value);
    }
    
    public String getFilial() {
        return this.getValueAsString("sida_filial");
    }
    
    public void setFilial(final String value) {
        this.setValue("sida_filial", (Object)value);
    }
    
    public String getFilialName() {
        return this.getValueAsString("ssa_filial_name");
    }
    
    public void setFilialName(final String value) {
        this.setValue("ssa_filial_name", (Object)value);
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
    
    public boolean isDontCheckCounteragentSignCertificate(final DMSShardsClientSession session, final SessionContext ctx) {
        switch (this.getValueAsInt("sia_dont_chk_cntagent_sign_cert")) {
            case 0: {
                final IDiadocEdoAccount account = (IDiadocEdoAccount)ServiceReg.getObjectModelService().getObjectModel(session, this.getAccount(), ctx);
                return account != null && account.isDontCheckCounteragentSignCertificate();
            }
            case 1: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public int getDontCheckCounteragentSignCertificate() {
        return this.getValueAsInt("sia_dont_chk_cntagent_sign_cert");
    }
    
    public void setDontCheckCounteragentSignCertificate(final int value) {
        this.setValue("sia_dont_chk_cntagent_sign_cert", (Object)value);
    }
    
    public String getDontCheckCounteragentSignCertificateName() {
        return this.getValueAsString("ssa_dont_chk_ca_sign_cert_name");
    }
    
    public void setDontCheckCounteragentSignCertificateName(final String value) {
        this.setValue("ssa_dont_chk_ca_sign_cert_name", (Object)value);
    }
    
    public boolean isRemoteSignatureMode() {
        return this.getValueAsBoolean("sba_remote_signature_mode");
    }
    
    public void setRemoteSignatureMode(final boolean value) {
        this.setValue("sba_remote_signature_mode", (Object)value);
    }
    
    public boolean isAllowSendToTestCounteragents() {
        return this.getValueAsBoolean("sba_allow_use_test_counteragents");
    }
    
    public void setAllowSendToTestCounteragents(final boolean value) {
        this.setValue("sba_allow_use_test_counteragents", (Object)value);
    }
    
    public boolean isDontCheckCounteragentSignCertificate(final IDiadocEdoAccount account) {
        switch (this.getValueAsInt("sia_dont_chk_cntagent_sign_cert")) {
            case 0: {
                return account != null && account.isDontCheckCounteragentSignCertificate();
            }
            case 1: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isAllowSendToRejected() {
        return this.getValueAsBoolean("sba_allow_send_to_rejected");
    }
    
    public void setAllowSendToRejected(final boolean value) {
        this.setValue("sba_allow_send_to_rejected", (Object)value);
    }
    
    public void setLogPropogateMode(final int value) {
        this.setValue("sia_log_propogate_mode", (Object)value);
    }
    
    public int getLogPropogateMode() {
        return this.getValueAsInt("sia_log_propogate_mode");
    }
    
    public void setIncDocsCheckOffset(final int value) {
        this.setValue("sia_inc_docs_check_offset", (Object)value);
    }
    
    public int getIncDocsCheckOffset() {
        return this.getValueAsInt("sia_inc_docs_check_offset");
    }
    
    public void setOutboundCategories(final List<String> value) {
        this.setValue("rsa_outbound_category", (Object)value);
    }
    
    public List<String> getOutboundCategories() {
        if (this.getValue("rsa_outbound_category") == null) {
            this.setValue("rsa_outbound_category", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_outbound_category");
    }
    
    public void setOutboundCategoryNames(final List<String> value) {
        this.setValue("rsa_outbound_category_name", (Object)value);
    }
    
    public List<String> getOutboundCategoryNames() {
        if (this.getValue("rsa_outbound_category_name") == null) {
            this.setValue("rsa_outbound_category_name", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_outbound_category_name");
    }
    
    public void setOutboundCategoryDepartments(final List<String> values) {
        this.setValue("rsa_outbound_category_department", (Object)values);
    }
    
    public List<String> getOutboundCategoryDepartments() {
        if (this.getValue("rsa_outbound_category_department") == null) {
            this.setValue("rsa_outbound_category_department", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_outbound_category_department");
    }
    
    public void setInboundCategories(final List<String> value) {
        this.setValue("rsa_inbound_category", (Object)value);
    }
    
    public List<String> getInboundCategories() {
        if (this.getValue("rsa_inbound_category") == null) {
            this.setValue("rsa_inbound_category", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_inbound_category");
    }
    
    public void setInboundCategoryNames(final List<String> value) {
        this.setValue("rsa_inbound_category_name", (Object)value);
    }
    
    public List<String> getInboundCategoryNames() {
        if (this.getValue("rsa_inbound_category_name") == null) {
            this.setValue("rsa_inbound_category_name", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_inbound_category_name");
    }
    
    public void setInboundCategoryDepartments(final List<String> values) {
        this.setValue("rsa_inbound_category_department", (Object)values);
    }
    
    public List<String> getInboundCategoryDepartments() {
        if (this.getValue("rsa_inbound_category_department") == null) {
            this.setValue("rsa_inbound_category_department", (Object)new ArrayList());
        }
        return (List)this.getValue("rsa_inbound_category_department");
    }
    
    public boolean isDontCheckIncoming() {
        return this.getValueAsBoolean("sba_dont_check_incoming");
    }
    
    public void setDontCheckIncoming(final boolean value) {
        this.setValue("sba_dont_check_incoming", (Object)value);
    }
    
    public Date getIncomingDocumentsLastCheckDate() {
        return this.getValueAsDate("sdta_inc_docs_last_check_date");
    }
    
    public void setIncomingDocumentsLastCheckDate(final Date value) {
        this.setValue("sdta_inc_docs_last_check_date", (Object)value);
    }
    
    public Date getEventsLastCheckDate() {
        return this.getValueAsDate("sdta_events_last_check_date");
    }
    
    public void setEventsLastCheckDate(final Date value) {
        this.setValue("sdta_events_last_check_date", (Object)value);
    }
    
    public void setIncomingDocumentsDateCheckFrom(final Date value) {
        this.setValue("sdta_inc_docs_date_check_from", (Object)value);
    }
    
    public Date getIncomingDocumentsDateCheckFrom() {
        return this.getValueAsDate("sdta_inc_docs_date_check_from");
    }
    
    public void setEventsDateCheckFrom(final Date value) {
        this.setValue("sdta_events_date_check_from", (Object)value);
    }
    
    public Date getEventsDateCheckFrom() {
        return this.getValueAsDate("sdta_events_date_check_from");
    }
}
