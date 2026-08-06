// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.util.List;

public interface IEdoIntegrationStatusCheckConfiguration extends ICstmConfigAdapter, IEdoIntegrationOperationConfiguration
{
    List<IEdoIntegrationStatusCheckProcessRule> getRules();
    
    String getDocumentType();
}
