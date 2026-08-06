// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationIncomingAttachmentProcessingEvent;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundAttachmentProcessingEvent extends EdoIntegrationIncomingAttachmentProcessingEvent
{
    private static final long serialVersionUID = -5207439714444655027L;
    public static final String CONFIG_ID = "diadoc_edo_inbound_attachment_processing_event";
    
    public DiadocEdoInboundAttachmentProcessingEvent(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
