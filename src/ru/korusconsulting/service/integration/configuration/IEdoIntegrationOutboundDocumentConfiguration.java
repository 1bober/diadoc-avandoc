// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import java.util.function.BiFunction;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IEdoIntegrationOutboundDocumentConfiguration extends IEdoIntegrationOperationConfiguration
{
    boolean checkRules(final DMSShardsClientSession p0, final BiFunction<AttributeValueSourceTypes, String, String> p1, final SessionContext p2);
    
    List<IEdoIntegrationDocumentOperationConfiguration> getOperations();
}
