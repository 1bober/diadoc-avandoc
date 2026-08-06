// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.exceptions.ServiceException;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;

@ObjectModel(type = "nt_diadoc_edo_server")
public class DiadocEdoServer extends DmObjectModel implements IDiadocEdoServer
{
    private static final long serialVersionUID = -8563375827069234819L;
    
    public void populate(final IClientSession session, final IObject po) {
        try {
            this.setValue("sba_enabled", (Object)po.getBoolean("sba_enabled"));
            this.setValue("ssa_name", (Object)po.getString("ssa_name"));
            this.setValue("ssa_url", (Object)po.getString("ssa_url"));
            this.setValue("ssa_web_url", (Object)po.getString("ssa_web_url"));
            this.setValue("ssa_api_key", (Object)po.getString("ssa_api_key"));
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        po.setBoolean("sba_enabled", this.isEnabled());
        po.setString("ssa_name", this.getName());
        po.setString("ssa_url", this.getUrl());
        po.setString("ssa_web_url", this.getWebUrl());
        po.setString("ssa_api_key", this.getApiKey());
    }
    
    public void commitSaved(final IObject po) {
        if (this.isValueDirty("sba_enabled")) {
            po.setBoolean("sba_enabled", this.isEnabled());
        }
        if (this.isValueDirty("ssa_name")) {
            po.setString("ssa_name", this.getName());
        }
        if (this.isValueDirty("ssa_url")) {
            po.setString("ssa_url", this.getUrl());
        }
        if (this.isValueDirty("ssa_web_url")) {
            po.setString("ssa_web_url", this.getWebUrl());
        }
        if (this.isValueDirty("ssa_api_key")) {
            po.setString("ssa_api_key", this.getApiKey());
        }
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public String getName() {
        return this.getValueAsString("ssa_name");
    }
    
    public void setName(final String value) {
        this.setValue("ssa_name", (Object)value);
    }
    
    public String getUrl() {
        return this.getValueAsString("ssa_url");
    }
    
    public void setUrl(final String value) {
        this.setValue("ssa_url", (Object)value);
    }
    
    public String getWebUrl() {
        return this.getValueAsString("ssa_web_url");
    }
    
    public void setWebUrl(final String value) {
        this.setValue("ssa_web_url", (Object)value);
    }
    
    public String getApiKey() {
        return this.getValueAsString("ssa_api_key");
    }
    
    public void setApiKey(final String value) {
        this.setValue("ssa_api_key", (Object)value);
    }
}
