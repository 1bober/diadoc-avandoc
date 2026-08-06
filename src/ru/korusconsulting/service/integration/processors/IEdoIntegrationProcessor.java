// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.processors;

import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public interface IEdoIntegrationProcessor<T extends IIntegrationOperationContext> extends AutoCloseable
{
    void setService(final IEdoIntegrationService p0);
    
    void setOperationConfig(final IEdoIntegrationOperationConfiguration p0);
    
    IEdoIntegrationOperationConfiguration getOperationConfig();
    
    void setServiceConfig(final IEdoIntegrationServiceConfiguration p0);
    
    IEdoIntegrationServiceConfiguration getServiceConfig();
    
    void close();
    
    IEdoIntegrationService getService();
    
    void prepareProcessingEnvironments(final DMSShardsClientSession p0, final T p1, final IEdoIntegrationProcessingConfiguration p2, final Map<String, Object> p3, final SessionContext p4);
    
    void fillConnectionInfoEnvironment(final DMSShardsClientSession p0, final T p1, final Map<String, Object> p2, final SessionContext p3);
    
    public enum ConnectionInfoArgumentNames
    {
        api_url, 
        web_url, 
        auth_url, 
        server_id, 
        server_name, 
        account_id, 
        login, 
        account_member_id, 
        from_box_id, 
        from_box_guid, 
        from_department, 
        regulation;
    }
}
