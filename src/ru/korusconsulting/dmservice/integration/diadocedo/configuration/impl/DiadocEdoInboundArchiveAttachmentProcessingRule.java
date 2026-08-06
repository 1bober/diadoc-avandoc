// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class DiadocEdoInboundArchiveAttachmentProcessingRule extends DiadocEdoInboundAttachmentProcessingRule
{
    private static final long serialVersionUID = 431886242441941093L;
    public static final String CONFIG_ID = "diadoc_edo_incoming_processing_archive_rule";
    
    public DiadocEdoInboundArchiveAttachmentProcessingRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule.InboundAttachmentType getAttachmentType() {
        return IEdoIntegrationInboundAttachmentProcessingRule.InboundAttachmentType.archive;
    }
}
