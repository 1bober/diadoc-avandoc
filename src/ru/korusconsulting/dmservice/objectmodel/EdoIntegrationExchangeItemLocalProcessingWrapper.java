// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Date;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;

public class EdoIntegrationExchangeItemLocalProcessingWrapper implements IEdoIntegrationExchangeItemProcessingWrapper
{
    private final IEdoIntegrationExchangeItem item;
    
    public EdoIntegrationExchangeItemLocalProcessingWrapper(final IEdoIntegrationExchangeItem item) {
        this.item = item;
    }
    
    @Override
    public IEdoIntegrationExchangeItem getItem() {
        return this.item;
    }
    
    @Override
    public String getStateMessage() {
        return this.getItem().getLocalStateMessage();
    }
    
    @Override
    public void setStateMessage(final String value) {
        this.getItem().setLocalStateMessage(value);
    }
    
    @Override
    public String getPrevStateMessage() {
        return this.getItem().getPrevLocalStateMessage();
    }
    
    @Override
    public void setPrevStateMessage(final String value) {
        this.getItem().setPrevLocalStateMessage(value);
    }
    
    @Override
    public String getState() {
        return this.getItem().getLocalState();
    }
    
    @Override
    public void setState(final String value) {
        this.getItem().setLocalState(value);
    }
    
    @Override
    public String getStateSystemCode() {
        return this.getItem().getLocalStateSystemCode();
    }
    
    @Override
    public void setStateSystemCode(final String value) {
        this.getItem().setLocalStateSystemCode(value);
    }
    
    @Override
    public String getPrevStateSystemCode() {
        return this.getItem().getPrevLocalStateSystemCode();
    }
    
    @Override
    public void setPrevStateSystemCode(final String value) {
        this.getItem().setPrevLocalStateSystemCode(value);
    }
    
    @Override
    public String getStateDisplay() {
        return this.getItem().getLocalStateDisplay();
    }
    
    @Override
    public void setStateDisplay(final String value) {
        this.getItem().setLocalStateDisplay(value);
    }
    
    @Override
    public String getPrevState() {
        return this.getItem().getLocalState();
    }
    
    @Override
    public void setPrevState(final String value) {
        this.getItem().setPrevLocalState(value);
    }
    
    @Override
    public Date getStateTime() {
        return this.getItem().getLocalStateTime();
    }
    
    @Override
    public void setStateTime(final Date value) {
        this.getItem().setLocalStateTime(value);
    }
    
    @Override
    public Date getPrevStateTime() {
        return this.getItem().getPrevLocalStateTime();
    }
    
    @Override
    public void setPrevStateTime(final Date value) {
        this.getItem().setPrevLocalStateTime(value);
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
