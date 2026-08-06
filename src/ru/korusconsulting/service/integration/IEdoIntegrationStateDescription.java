// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

public interface IEdoIntegrationStateDescription
{
    int getHttpCode();
    
    String getIncomingCode();
    
    String getIncomingMessage();
    
    String getSystemCode();
    
    String getStateId();
    
    String getUserMessage();
    
    String getCategory();
}
