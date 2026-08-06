// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;

public interface IEdoIntegrationExchangeItemProcessingWrapper
{
    IEdoIntegrationExchangeItem getItem();
    
    String getStateMessage();
    
    void setStateMessage(final String p0);
    
    String getPrevStateMessage();
    
    void setPrevStateMessage(final String p0);
    
    String getState();
    
    void setState(final String p0);
    
    String getStateSystemCode();
    
    void setStateSystemCode(final String p0);
    
    String getPrevStateSystemCode();
    
    void setPrevStateSystemCode(final String p0);
    
    String getStateDisplay();
    
    void setStateDisplay(final String p0);
    
    String getPrevState();
    
    void setPrevState(final String p0);
    
    Date getStateTime();
    
    void setStateTime(final Date p0);
    
    Date getPrevStateTime();
    
    void setPrevStateTime(final Date p0);
    
    String getRemoteStateProcessingStep();
    
    void setRemoteStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep p0);
    
    String getLocalStateProcessingStep();
    
    void setLocalStateProcessingStep(final IEdoIntegrationInboundDataExchangeItem.ProcessingStep p0);
    
    String getStateCategory();
    
    void setStateCategory(final String p0);
}
