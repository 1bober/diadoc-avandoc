// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.exceptions.ServiceException;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoForeignStateMapping;

@ObjectModel(type = "nt_diadoc_edo_foreign_state_map")
public class DiadocEdoForeignStateMapping extends DmObjectModel implements IDiadocEdoForeignStateMapping
{
    private static final long serialVersionUID = -6681986182044373946L;
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            this.setValue("sba_enabled", (Object)po.getBoolean("sba_enabled"));
            this.setValue("ssa_system_state_code", (Object)po.getString("ssa_system_state_code"));
            this.setValue("ssa_state_code", (Object)po.getString("ssa_state_code"));
            this.setValue("ssa_state_display_string", (Object)po.getString("ssa_state_display_string"));
            this.setValue("sba_case_insensitive_check", (Object)po.getBoolean("sba_case_insensitive_check"));
            this.setValue("ssa_external_state", (Object)po.getString("ssa_external_state"));
            this.setValue("sba_is_regexp", (Object)po.getBoolean("sba_is_regexp"));
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        super.commitNew(po);
        po.setBoolean("sba_enabled", this.isEnabled());
        po.setString("ssa_system_state_code", this.getSystemStateCode());
        po.setString("ssa_state_code", this.getStateCode());
        po.setString("ssa_state_display_string", this.getStateDisplayString());
        po.setBoolean("sba_case_insensitive_check", this.isCaseInsensitiveCheck());
        po.setString("ssa_external_state", this.getExternalState());
        po.setBoolean("sba_is_regexp", this.isRegexp());
    }
    
    public void commitSaved(final IObject po) {
        super.commitSaved(po);
        if (this.isValueDirty("sba_enabled")) {
            po.setBoolean("sba_enabled", this.isEnabled());
        }
        if (this.isValueDirty("ssa_system_state_code")) {
            po.setString("ssa_system_state_code", this.getSystemStateCode());
        }
        if (this.isValueDirty("ssa_state_code")) {
            po.setString("ssa_state_code", this.getStateCode());
        }
        if (this.isValueDirty("ssa_state_display_string")) {
            po.setString("ssa_state_display_string", this.getStateDisplayString());
        }
        if (this.isValueDirty("sba_case_insensitive_check")) {
            po.setBoolean("sba_case_insensitive_check", this.isCaseInsensitiveCheck());
        }
        if (this.isValueDirty("ssa_external_state")) {
            po.setString("ssa_external_state", this.getExternalState());
        }
        if (this.isValueDirty("sba_is_regexp")) {
            po.setBoolean("sba_is_regexp", this.isRegexp());
        }
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public String getSystemStateCode() {
        return this.getValueAsString("ssa_system_state_code");
    }
    
    public void setSystemStateCode(final String value) {
        this.setValue("ssa_system_state_code", (Object)value);
    }
    
    public String getStateCode() {
        return this.getValueAsString("ssa_state_code");
    }
    
    public void setStateCode(final String value) {
        this.setValue("ssa_state_code", (Object)value);
    }
    
    public String getStateDisplayString() {
        return this.getValueAsString("ssa_state_display_string");
    }
    
    public void setStateDisplayString(final String value) {
        this.setValue("ssa_state_display_string", (Object)value);
    }
    
    public String getExternalState() {
        return this.getValueAsString("ssa_external_state");
    }
    
    public void setExternalState(final String value) {
        this.setValue("ssa_external_state", (Object)value);
    }
    
    public boolean isCaseInsensitiveCheck() {
        return this.getValueAsBoolean("sba_case_insensitive_check");
    }
    
    public void setCaseInsensitiveCheck(final boolean value) {
        this.setValue("sba_case_insensitive_check", (Object)value);
    }
    
    public boolean isRegexp() {
        return this.getValueAsBoolean("sba_is_regexp");
    }
    
    public void setIsRegexp(final boolean value) {
        this.setValue("sba_is_regexp", (Object)value);
    }
}
