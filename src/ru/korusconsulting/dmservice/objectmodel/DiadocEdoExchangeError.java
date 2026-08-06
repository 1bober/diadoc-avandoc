// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Calendar;
import java.util.Iterator;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IObjectModelAttributeProcessor;
import java.util.List;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;

@ObjectModel(type = "nt_diadoc_edo_exchange_error")
public class DiadocEdoExchangeError extends DmObjectModel implements IDiadocEdoExchangeError
{
    private static final long serialVersionUID = -3293460100472225695L;
    private transient List<IObjectModelAttributeProcessor> attributes;
    
    public DiadocEdoExchangeError() {
        this.attributes = new ArrayList<IObjectModelAttributeProcessor>();
        this.appendAttributeDefinition("sba_enabled", Boolean.TYPE, 0, this::isEnabled, this::setEnabled);
        this.appendAttributeDefinition("ssa_error_type", String.class, 32, this::getErrorType, this::setErrorType);
        this.appendAttributeDefinition("ssa_error_type_name", String.class, 200, this::getErrorTypeName, this::setErrorTypeName);
        this.appendAttributeDefinition("ssa_error_code", String.class, 200, this::getErrorCode, this::setErrorCode);
        this.appendAttributeDefinition("ssa_error_message", String.class, 4000, this::getErrorMessage, this::setErrorMessage);
        this.appendAttributeDefinition("sba_case_insensitive_check", Boolean.class, 0, this::isCaseInsensitiveCheck, this::setCaseInsensitiveCheck);
        this.appendAttributeDefinition("sba_is_regexp", Boolean.TYPE, 0, this::isRegexp, this::setIsRegexp);
        this.appendAttributeDefinition("ssa_category", String.class, 64, this::getCategory, this::setCategory);
        this.appendAttributeDefinition("ssa_category_name", String.class, 400, this::getCategoryName, this::setCategoryName);
        this.appendAttributeDefinition("ssa_create_mode", String.class, 32, this::getCreateMode, this::setCreateMode);
        this.appendAttributeDefinition("ssa_counteragent_state_code", String.class, 100, this::getCounteragentStateCode, this::setCounteragentStateCode);
        this.appendAttributeDefinition("ssa_counteragent_state_message", String.class, 400, this::getCounteragentStateMessage, this::setCounteragentStateMessage);
        this.appendAttributeDefinition("ssa_counteragent_state_code_2", String.class, 100, this::getCounteragentStateCodeL2, this::setCounteragentStateCodeL2);
        this.appendAttributeDefinition("ssa_counteragent_state_message_2", String.class, 400, this::getCounteragentStateMessageL2, this::setCounteragentStateMessageL2);
        this.appendAttributeDefinition("ssa_category_2", String.class, 64, this::getCategoryL2, this::setCategoryL2);
        this.appendAttributeDefinition("ssa_category_name_2", String.class, 400, this::getCategoryNameL2, this::setCategoryNameL2);
        this.appendAttributeDefinition("ssa_operation_state", String.class, 64, this::getOperationState, this::setOperationState);
        this.appendAttributeDefinition("ssa_operation_state_2", String.class, 64, this::getOperationStateL2, this::setOperationStateL2);
        this.appendAttributeDefinition("ssa_system_error_code", String.class, 400, this::getSystemErrorCode, this::setSystemErrorCode);
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
            this.setValue("sdta_create_date", (Object)po.getDate("sdta_create_date"));
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
        po.setDate("sdta_create_date", Calendar.getInstance().getTime());
    }
    
    public void commitSaved(final IObject po) {
        super.commitSaved(po);
        for (final IObjectModelAttributeProcessor attr : this.attributes) {
            attr.commitAttribute((IObjectModel)this, po);
        }
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public String getErrorType() {
        return this.getValueAsString("ssa_error_type");
    }
    
    public void setErrorType(final String value) {
        this.setValue("ssa_error_type", (Object)value);
    }
    
    public String getErrorTypeName() {
        return this.getValueAsString("ssa_error_type_name");
    }
    
    public void setErrorTypeName(final String value) {
        this.setValue("ssa_error_type_name", (Object)value);
    }
    
    public String getErrorCode() {
        return this.getValueAsString("ssa_error_code");
    }
    
    public void setErrorCode(final String value) {
        this.setValue("ssa_error_code", (Object)value);
    }
    
    public String getErrorMessage() {
        return this.getValueAsString("ssa_error_message");
    }
    
    public void setErrorMessage(final String value) {
        this.setValue("ssa_error_message", (Object)value);
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
    
    public String getCategory() {
        return this.getValueAsString("ssa_category");
    }
    
    public void setCategory(final String value) {
        this.setValue("ssa_category", (Object)value);
    }
    
    public String getCategoryName() {
        return this.getValueAsString("ssa_category_name");
    }
    
    public void setCategoryName(final String value) {
        this.setValue("ssa_category_name", (Object)value);
    }
    
    public String getCategoryL2() {
        return this.getValueAsString("ssa_category_2");
    }
    
    public void setCategoryL2(final String value) {
        this.setValue("ssa_category_2", (Object)value);
    }
    
    public String getCategoryNameL2() {
        return this.getValueAsString("ssa_category_name_2");
    }
    
    public void setCategoryNameL2(final String value) {
        this.setValue("ssa_category_name_2", (Object)value);
    }
    
    public String getSystemErrorCode() {
        return this.getValueAsString("ssa_system_error_code");
    }
    
    public void setSystemErrorCode(final String value) {
        this.setValue("ssa_system_error_code", (Object)value);
    }
    
    public String getCounteragentStateCode() {
        return this.getValueAsString("ssa_counteragent_state_code");
    }
    
    public void setCounteragentStateCode(final String value) {
        this.setValue("ssa_counteragent_state_code", (Object)value);
    }
    
    public String getCounteragentStateMessage() {
        return this.getValueAsString("ssa_counteragent_state_message");
    }
    
    public void setCounteragentStateMessage(final String value) {
        this.setValue("ssa_counteragent_state_message", (Object)value);
    }
    
    public String getCounteragentStateCodeL2() {
        return this.getValueAsString("ssa_counteragent_state_code_2");
    }
    
    public void setCounteragentStateCodeL2(final String value) {
        this.setValue("ssa_counteragent_state_code_2", (Object)value);
    }
    
    public String getCounteragentStateMessageL2() {
        return this.getValueAsString("ssa_counteragent_state_message_2");
    }
    
    public void setCounteragentStateMessageL2(final String value) {
        this.setValue("ssa_counteragent_state_message_2", (Object)value);
    }
    
    public String getCreateMode() {
        return this.getValueAsString("ssa_create_mode");
    }
    
    public void setCreateMode(final String value) {
        this.setValue("ssa_create_mode", (Object)value);
    }
    
    public String getOperationState() {
        return this.getValueAsString("ssa_operation_state");
    }
    
    public void setOperationState(final String value) {
        this.setValue("ssa_operation_state", (Object)value);
    }
    
    public String getOperationStateL2() {
        return this.getValueAsString("ssa_operation_state_2");
    }
    
    public void setOperationStateL2(final String value) {
        this.setValue("ssa_operation_state_2", (Object)value);
    }
}
