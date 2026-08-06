// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Date;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoCounteragentInfo;

@ObjectModel(type = "nt_diadoc_edo_counteragent_info")
public class DiadocEdoCounteragentInfo extends DmObjectModel implements IDiadocEdoCounteragentInfo
{
    private static final long serialVersionUID = 3937732530332279134L;
    
    public void populate(final IClientSession session, final IObject po) {
        try {
            this.setValue("ssa_name", (Object)po.getString("ssa_name"));
            this.setValue("ssa_inn", (Object)po.getString("ssa_inn"));
            this.setValue("ssa_kpp", (Object)po.getString("ssa_kpp"));
            this.setValue("ssa_box_guid", (Object)po.getString("ssa_box_guid"));
            this.setValue("ssa_department", (Object)po.getString("ssa_department"));
            this.setValue("ssa_department_name", (Object)po.getString("ssa_department_name"));
            this.setValue("sida_server", (Object)po.getString("sida_server"));
            this.setValue("ssa_server_name", (Object)po.getString("ssa_server_name"));
            this.setValue("sida_account", (Object)po.getString("sida_account"));
            this.setValue("ssa_account_name", (Object)po.getString("ssa_account_name"));
            this.setValue("sida_account_member", (Object)po.getString("sida_account_member"));
            this.setValue("ssa_account_member_name", (Object)po.getString("ssa_account_member_name"));
            this.setValue("ssa_org_id", (Object)po.getString("ssa_org_id"));
            this.setValue("ssa_fns_participant_id", (Object)po.getString("ssa_fns_participant_id"));
            this.setValue("sba_roaming", (Object)po.getBoolean("sba_roaming"));
            this.setValue("sba_has_certificate_to_sign", (Object)po.getBoolean("sba_has_certificate_to_sign"));
            this.setValue("sdta_create_date", (Object)po.getDate("sdta_create_date"));
            this.setValue("sdta_modify_date", (Object)po.getDate("sdta_modify_date"));
            this.setValue("sdta_last_validate_date", (Object)po.getDate("sdta_last_validate_date"));
            this.setValue("sdta_next_validate_date", (Object)po.getDate("sdta_next_validate_date"));
            this.setValue("sba_foreign", (Object)po.getBoolean("sba_foreign"));
            this.setValue("sba_enabled", (Object)po.getBoolean("sba_enabled"));
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        po.setString("ssa_name", this.getName());
        po.setString("ssa_inn", this.getInn());
        po.setString("ssa_kpp", this.getKpp());
        po.setString("ssa_box_guid", this.getBoxGuid());
        po.setString("ssa_department", this.getDepartment());
        po.setString("ssa_department_name", this.getDepartmentName());
        po.setString("sida_server", this.getServerId());
        po.setString("ssa_server_name", this.getServerName());
        po.setString("sida_account", this.getAccountId());
        po.setString("ssa_account_name", this.getAccountName());
        po.setString("sida_account_member", this.getAccountMemberId());
        po.setString("ssa_account_member_name", this.getAccountMemberName());
        po.setString("ssa_org_id", this.getOrgId());
        po.setString("ssa_fns_participant_id", this.getFnsParticipantId());
        po.setBoolean("sba_roaming", this.isRoaming());
        po.setBoolean("sba_has_certificate_to_sign", this.hasCertificateToSign());
        po.setDate("sdta_create_date", new Date());
        po.setDate("sdta_modify_date", new Date());
        po.setDate("sdta_last_validate_date", this.getLastValidateDate());
        po.setDate("sdta_next_validate_date", this.getLastValidateDate());
        po.setBoolean("sba_foreign", this.IsForeign());
        po.setBoolean("sba_enabled", this.isEnabled());
    }
    
    public void commitSaved(final IObject po) {
        boolean changed = false;
        if (this.isValueDirty("ssa_name")) {
            po.setString("ssa_name", this.getName());
            changed = true;
        }
        if (this.isValueDirty("ssa_inn")) {
            po.setString("ssa_inn", this.getInn());
            changed = true;
        }
        if (this.isValueDirty("ssa_kpp")) {
            po.setString("ssa_kpp", this.getKpp());
            changed = true;
        }
        if (this.isValueDirty("ssa_box_guid")) {
            po.setString("ssa_box_guid", this.getBoxGuid());
            changed = true;
        }
        if (this.isValueDirty("ssa_department")) {
            po.setString("ssa_department", this.getDepartment());
            changed = true;
        }
        if (this.isValueDirty("ssa_department_name")) {
            po.setString("ssa_department_name", this.getDepartmentName());
            changed = true;
        }
        if (this.isValueDirty("sida_server")) {
            po.setString("sida_server", this.getServerId());
            changed = true;
        }
        if (this.isValueDirty("ssa_server_name")) {
            po.setString("ssa_server_name", this.getServerName());
            changed = true;
        }
        if (this.isValueDirty("sida_account")) {
            po.setString("sida_account", this.getAccountId());
            changed = true;
        }
        if (this.isValueDirty("ssa_account_name")) {
            po.setString("ssa_account_name", this.getAccountName());
            changed = true;
        }
        if (this.isValueDirty("sida_account_member")) {
            po.setString("sida_account_member", this.getAccountMemberId());
            changed = true;
        }
        if (this.isValueDirty("ssa_account_member_name")) {
            po.setString("ssa_account_member_name", this.getAccountMemberName());
            changed = true;
        }
        if (this.isValueDirty("ssa_org_id")) {
            po.setString("ssa_org_id", this.getOrgId());
            changed = true;
        }
        if (this.isValueDirty("ssa_fns_participant_id")) {
            po.setString("ssa_fns_participant_id", this.getFnsParticipantId());
            changed = true;
        }
        if (this.isValueDirty("sba_roaming")) {
            po.setBoolean("sba_roaming", this.isRoaming());
            changed = true;
        }
        if (this.isValueDirty("sba_has_certificate_to_sign")) {
            po.setBoolean("sba_has_certificate_to_sign", this.hasCertificateToSign());
            changed = true;
        }
        if (this.isValueDirty("sdta_last_validate_date")) {
            po.setDate("sdta_last_validate_date", this.getLastValidateDate());
            changed = true;
        }
        if (this.isValueDirty("sdta_next_validate_date")) {
            po.setDate("sdta_next_validate_date", this.getNextValidateDate());
            changed = true;
        }
        if (this.isValueDirty("sba_foreign")) {
            po.setBoolean("sba_foreign", this.IsForeign());
            changed = true;
        }
        if (this.isValueDirty("sba_enabled")) {
            po.setBoolean("sba_enabled", this.isEnabled());
            changed = true;
        }
        if (changed) {
            po.setDate("sdta_modify_date", new Date());
        }
    }
    
    public void setName(final String value) {
        this.setValue("ssa_name", (Object)value);
    }
    
    public String getName() {
        return this.getValueAsString("ssa_name");
    }
    
    public void setInn(final String value) {
        this.setValue("ssa_inn", (Object)value);
    }
    
    public String getInn() {
        return this.getValueAsString("ssa_inn");
    }
    
    public void setKpp(final String value) {
        this.setValue("ssa_kpp", (Object)value);
    }
    
    public String getKpp() {
        return this.getValueAsString("ssa_kpp");
    }
    
    public void setBoxGuid(final String value) {
        this.setValue("ssa_box_guid", (Object)value);
    }
    
    public String getBoxGuid() {
        return this.getValueAsString("ssa_box_guid");
    }
    
    public void setDepartment(final String value) {
        this.setValue("ssa_department", (Object)value);
    }
    
    public String getDepartment() {
        return this.getValueAsString("ssa_department");
    }
    
    public void setDepartmentName(final String value) {
        this.setValue("ssa_department_name", (Object)value);
    }
    
    public String getDepartmentName() {
        return this.getValueAsString("ssa_department_name");
    }
    
    public void setServerId(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getServerId() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServerName(final String value) {
        this.setValue("ssa_server_name", (Object)value);
    }
    
    public String getServerName() {
        return this.getValueAsString("ssa_server_name");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public String getOrgId() {
        return this.getValueAsString("ssa_org_id");
    }
    
    public void setOrgId(final String value) {
        this.setValue("ssa_org_id", (Object)value);
    }
    
    public String getFnsParticipantId() {
        return this.getValueAsString("ssa_fns_participant_id");
    }
    
    public void setFnsParticipantId(final String value) {
        this.setValue("ssa_fns_participant_id", (Object)value);
    }
    
    public boolean isRoaming() {
        return this.getValueAsBoolean("sba_roaming");
    }
    
    public void setRoaming(final boolean value) {
        this.setValue("sba_roaming", (Object)value);
    }
    
    public Date getNextValidateDate() {
        return this.getValueAsDate("sdta_next_validate_date");
    }
    
    public void setNextValidateDate(final Date value) {
        this.setValue("sdta_next_validate_date", (Object)value);
    }
    
    public Date getLastValidateDate() {
        return this.getValueAsDate("sdta_last_validate_date");
    }
    
    public void setLastValidateDate(final Date value) {
        this.setValue("sdta_last_validate_date", (Object)value);
    }
    
    public boolean hasCertificateToSign() {
        return this.getValueAsBoolean("sba_has_certificate_to_sign");
    }
    
    public void setHasCertificateToSign(final boolean value) {
        this.setValue("sba_has_certificate_to_sign", (Object)value);
    }
    
    public Date getCreateDate() {
        return this.getValueAsDate("sdta_create_date");
    }
    
    public Date getModifyDate() {
        return this.getValueAsDate("sdta_modify_date");
    }
    
    public boolean IsForeign() {
        return this.getValueAsBoolean("sba_foreign");
    }
    
    public void setIsForeign(final boolean value) {
        this.setValue("sba_foreign", (Object)value);
    }
    
    public void setAccountId(final String value) {
        this.setValue("sida_account", (Object)value);
    }
    
    public String getAccountId() {
        return this.getValueAsString("sida_account");
    }
    
    public void setAccountName(final String value) {
        this.setValue("ssa_account_name", (Object)value);
    }
    
    public String getAccountName() {
        return this.getValueAsString("ssa_account_name");
    }
    
    public void setAccountMemberId(final String value) {
        this.setValue("sida_account_member", (Object)value);
    }
    
    public String getAccountMemberId() {
        return this.getValueAsString("sida_account_member_id");
    }
    
    public void setAccountMemberName(final String value) {
        this.setValue("ssa_account_member_name", (Object)value);
    }
    
    public String getAccountMemberName() {
        return this.getValueAsString("ssa_account_member_name");
    }
}
