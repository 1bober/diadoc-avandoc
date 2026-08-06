// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.List;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationDocumentEventProcessingOperationConfiguration extends ICstmConfigAdapter
{
    String getProcessingEvent();
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
}
