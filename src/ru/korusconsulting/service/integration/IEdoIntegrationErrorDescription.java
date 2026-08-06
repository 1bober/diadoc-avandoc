// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

public interface IEdoIntegrationErrorDescription
{
    int getHttpCode();
    
    String getIncomingCode();
    
    String getIncomingMessage();
    
    String getSystemCode();
    
    String getErrorId();
    
    String getUserMessage();
    
    String getCategory();
}
