// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

public interface IEdoIntegrationInboundAttachmentProcessingEvent extends ICstmConfigAdapter
{
    void setProcessorLink(final String p0);
    
    String getProcessorLink();
    
    Boolean unmarshalProcessorLink(final Generator p0, final SessionContext p1);
}
