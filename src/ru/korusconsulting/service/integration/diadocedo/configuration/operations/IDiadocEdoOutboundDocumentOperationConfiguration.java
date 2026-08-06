// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.operations;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.List;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;

public interface IDiadocEdoOutboundDocumentOperationConfiguration extends IEdoIntegrationDocumentOperationConfiguration
{
    String getDiadocEdoBoxSelectMode();
    
    String getTargetTypeId();
    
    EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode getCounteragentMode();
    
    LockModeConfig getLockMode();
    
    List<IEdoIntegrationAttributeMapping> getAnswerMapping();
    
    public enum LockModeConfig
    {
        Full("full"), 
        Send("send"), 
        None("none");
        
        private final String value;
        
        private LockModeConfig(final String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public static LockModeConfig parse(final String value) {
            for (final LockModeConfig m : values()) {
                if (m.getValue().equals(value)) {
                    return m;
                }
            }
            return LockModeConfig.None;
        }
    }
}
