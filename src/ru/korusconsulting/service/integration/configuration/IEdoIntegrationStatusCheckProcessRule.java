// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

public interface IEdoIntegrationStatusCheckProcessRule extends ICstmConfigAdapter
{
    String getEvent();
    
    boolean isDownloadZip();
    
    IDictionaryElementConfiguration getContentGroupZipStorage();
    
    String getStatus();
}
