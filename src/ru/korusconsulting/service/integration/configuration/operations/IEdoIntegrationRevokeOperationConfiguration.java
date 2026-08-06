// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration.operations;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IEdoIntegrationRevokeOperationConfiguration extends ICstmConfigAdapter
{
    RevokeOperationType getRevokeOperationType();
    
    public enum RevokeOperationType
    {
        CREATE_FILE, 
        SEND_FILE;
    }
}
