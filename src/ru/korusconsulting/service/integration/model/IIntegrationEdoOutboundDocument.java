// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model;

import java.util.Map;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IIntegrationEdoOutboundDocument
{
    List<IIntegrationEdoCounteragent> getCounteragents(final DMSShardsClientSession p0, final SessionContext p1);
    
    Map<String, String[]> getMappingValues(final DMSShardsClientSession p0, final SessionContext p1);
}
