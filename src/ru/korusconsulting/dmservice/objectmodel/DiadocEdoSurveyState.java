// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

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
import ru.korusconsulting.service.objectmodel.IDiadocEdoSurveyState;

@ObjectModel(type = "nt_diadoc_edo_survey_state")
public class DiadocEdoSurveyState extends DmObjectModel implements IDiadocEdoSurveyState
{
    private static final long serialVersionUID = -3293460100472225695L;
    private transient List<IObjectModelAttributeProcessor> attributes;
    
    public DiadocEdoSurveyState() {
        this.attributes = new ArrayList<IObjectModelAttributeProcessor>();
        this.appendAttributeDefinition("sida_filial", String.class, 16, this::getFilialId, this::setFilialId);
        this.appendAttributeDefinition("ssa_filial_name", String.class, 128, this::getFilialName, this::setFilialName);
        this.appendAttributeDefinition("ssa_diadoc_login", String.class, 400, this::getDiadocLogin, this::setDiadocLogin);
        this.appendAttributeDefinition("ssa_diadoc_box", String.class, 400, this::getDiadocBox, this::setDiadocBox);
        this.appendAttributeDefinition("ssa_department", String.class, 400, this::getDepartment, this::setDepartment);
        this.appendAttributeDefinition("sida_member", String.class, 16, this::getAccountMemberId, this::setAccountMemberId);
        this.appendAttributeDefinition("ssa_member_name", String.class, 400, this::getAccountMemberName, this::setAccountMemberName);
        this.appendAttributeDefinition("ssa_last_proceed", String.class, 128, this::getLastProcceed, this::setLastProcceed);
        this.appendAttributeDefinition("sdta_from_date", Date.class, 0, this::getFromDate, this::setFromDate);
        this.appendAttributeDefinition("ssa_direction", String.class, 400, this::getDirection, this::setDirection);
    }
    
    private <E> void appendAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<E> getter, final Consumer<E> setter) {
        this.attributes.add((IObjectModelAttributeProcessor)new ObjectModelAttributeProcessor(name, (Class)attrClass, (maxLen == 16) ? 16 : (maxLen / 2), (Consumer)setter, (Supplier)getter));
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
    
    public String getDiadocLogin() {
        return this.getValueAsString("ssa_diadoc_login");
    }
    
    public void setDiadocLogin(final String value) {
        this.setValue("ssa_diadoc_login", (Object)value);
    }
    
    public String getDiadocBox() {
        return this.getValueAsString("ssa_diadoc_box");
    }
    
    public void setDiadocBox(final String value) {
        this.setValue("ssa_diadoc_box", (Object)value);
    }
    
    public String getDepartment() {
        return this.getValueAsString("ssa_department");
    }
    
    public void setDepartment(final String value) {
        this.setValue("ssa_department", (Object)value);
    }
    
    public String getAccountMemberId() {
        return this.getValueAsString("sida_member");
    }
    
    public void setAccountMemberId(final String value) {
        this.setValue("sida_member", (Object)value);
    }
    
    public String getAccountMemberName() {
        return this.getValueAsString("ssa_member_name");
    }
    
    public void setAccountMemberName(final String value) {
        this.setValue("ssa_member_name", (Object)value);
    }
    
    public Date getFromDate() {
        return this.getValueAsDate("sdta_from_date");
    }
    
    public void setFromDate(final Date value) {
        this.setValue("sdta_from_date", (Object)value);
    }
    
    public String getLastProcceed() {
        return this.getValueAsString("ssa_last_proceed");
    }
    
    public void setLastProcceed(final String value) {
        this.setValue("ssa_last_proceed", (Object)value);
    }
    
    public String getDirection() {
        return this.getValueAsString("ssa_direction");
    }
    
    public void setDirection(final String value) {
        this.setValue("ssa_direction", (Object)value);
    }
}
