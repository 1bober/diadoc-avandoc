// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import java.util.Iterator;
import ru.korusconsulting.service.exceptions.ServiceException;
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
import ru.korusconsulting.service.objectmodel.IEdoIntegrationTokenStore;

@ObjectModel(type = "nt_edo_integration_token_store")
public class EdoIntegrationTokenStore extends DmObjectModel implements IEdoIntegrationTokenStore
{
    private static final long serialVersionUID = -2343617914927794736L;
    private transient List<IObjectModelAttributeProcessor> attributes;
    
    public EdoIntegrationTokenStore() {
        this.attributes = new ArrayList<IObjectModelAttributeProcessor>();
        this.appendAttributeDefinition("ssa_integration", String.class, 64, this::getIntegration, this::setIntegration);
        this.appendAttributeDefinition("sida_server", String.class, 16, this::getServerId, this::setServerId);
        this.appendAttributeDefinition("ssa_login", String.class, 400, this::getLogin, this::setLogin);
        this.appendAttributeDefinition("ssa_box", String.class, 400, this::getBox, this::setBox);
        this.appendAttributeDefinition("ssa_token", String.class, 2000, this::getToken, this::setToken);
        this.appendAttributeDefinition("sdta_update_time", Date.class, 0, this::getUpdateTime, this::setUpdateTime);
    }
    
    private <E> void appendAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<E> getter, final Consumer<E> setter) {
        this.attributes.add(new ObjectModelAttributeProcessor<Object>(name, (Class<Object>)attrClass, (maxLen == 16) ? 16 : (maxLen / 2), (Consumer<Object>)setter, (Supplier<Object>)getter));
    }
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                attr.populateAttribute((IObjectModel)this, po);
            }
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        super.commitNew(po);
        for (final IObjectModelAttributeProcessor attr : this.attributes) {
            attr.commitAttribute((IObjectModel)this, po);
        }
    }
    
    public void commitSaved(final IObject po) {
        super.commitSaved(po);
        for (final IObjectModelAttributeProcessor attr : this.attributes) {
            attr.commitAttribute((IObjectModel)this, po);
        }
    }
    
    public String getIntegration() {
        return this.getValueAsString("ssa_integration");
    }
    
    public void setIntegration(final String value) {
        this.setValue("ssa_integration", (Object)value);
    }
    
    public String getServerId() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServerId(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getLogin() {
        return this.getValueAsString("ssa_login");
    }
    
    public void setLogin(final String value) {
        this.setValue("ssa_login", (Object)value);
    }
    
    public String getBox() {
        return this.getValueAsString("ssa_box");
    }
    
    public void setBox(final String value) {
        this.setValue("ssa_box", (Object)value);
    }
    
    public String getToken() {
        return this.getValueAsString("ssa_token");
    }
    
    public void setToken(final String value) {
        if (!StringUtils.equals(value, this.getValueAsString("ssa_token"))) {
            this.setValue("ssa_token", (Object)value);
            this.setUpdateTime(Calendar.getInstance().getTime());
        }
    }
    
    public Date getUpdateTime() {
        return this.getValueAsDate("sdta_update_time");
    }
    
    public void setUpdateTime(final Date value) {
        this.setValue("sdta_update_time", (Object)value);
    }
}
