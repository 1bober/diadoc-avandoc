// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Date;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;

public class EdoIntegrationExchangeItemRemoteProcessingWrapper implements IEdoIntegrationExchangeItemProcessingWrapper
{
    private final IEdoIntegrationExchangeItem item;
    
    public EdoIntegrationExchangeItemRemoteProcessingWrapper(final IEdoIntegrationExchangeItem item) {
        this.item = item;
    }
    
    @Override
    public IEdoIntegrationExchangeItem getItem() {
        return this.item;
    }
    
    @Override
    public String getStateMessage() {
        return this.getItem().getRemoteStateMessage();
    }
    
    @Override
    public void setStateMessage(final String value) {
        this.getItem().setRemoteStateMessage(value);
    }
    
    @Override
    public String getPrevStateMessage() {
        return this.getItem().getPrevRemoteStateMessage();
    }
    
    @Override
    public void setPrevStateMessage(final String value) {
        this.getItem().setPrevRemoteStateMessage(value);
    }
    
    @Override
    public String getState() {
        return this.getItem().getRemoteState();
    }
    
    @Override
    public void setState(final String value) {
        this.getItem().setRemoteState(value);
    }
    
    @Override
    public String getStateSystemCode() {
        return this.getItem().getRemoteStateSystemCode();
    }
    
    @Override
    public String getPrevStateSystemCode() {
        return this.getItem().getPrevRemoteStateSystemCode();
    }
    
    @Override
    public void setPrevStateSystemCode(final String value) {
        this.getItem().setPrevRemoteStateSystemCode(value);
    }
    
    @Override
    public void setStateSystemCode(final String value) {
        this.getItem().setRemoteStateSystemCode(value);
    }
    
    @Override
    public String getStateDisplay() {
        return this.getItem().getRemoteStateDisplay();
    }
    
    @Override
    public void setStateDisplay(final String value) {
        this.getItem().setRemoteStateDisplay(value);
    }
    
    @Override
    public String getPrevState() {
        return this.getItem().getRemoteState();
    }
    
    @Override
    public void setPrevState(final String value) {
        this.getItem().setPrevRemoteState(value);
    }
    
    @Override
    public Date getStateTime() {
        return this.getItem().getRemoteStateTime();
    }
    
    @Override
    public void setStateTime(final Date value) {
        this.getItem().setRemoteStateTime(value);
    }
    
    @Override
    public Date getPrevStateTime() {
        return this.getItem().getPrevRemoteStateTime();
    }
    
    @Override
    public void setPrevStateTime(final Date value) {
        this.getItem().setPrevRemoteStateTime(value);
    }
    
    @Override
    public String getRemoteStateProcessingStep() {
        return this.getItem().getRemoteStateProcessingStep();
    }
    
    @Override
    public void setRemoteStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
        this.getItem().setRemoteStateProcessingStep(value);
    }
    
    @Override
    public String getLocalStateProcessingStep() {
        return this.getItem().getLocalStateProcessingStep();
    }
    
    @Override
    public void setLocalStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep value) {
        this.getItem().setLocalStateProcessingStep(value);
    }
    
    @Override
    public String getStateCategory() {
        return this.getItem().getStateCategory();
    }
    
    @Override
    public void setStateCategory(final String value) {
        this.getItem().setStateCategory(value);
    }
}
