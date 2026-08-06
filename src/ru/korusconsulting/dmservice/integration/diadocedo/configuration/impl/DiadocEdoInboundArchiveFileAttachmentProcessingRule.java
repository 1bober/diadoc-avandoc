// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoInboundArchiveFileAttachmentProcessingRule extends DiadocEdoInboundAttachmentProcessingRule
{
    private static final long serialVersionUID = 5647387838457471668L;
    public static final String CONFIG_ID = "diadoc_edo_incoming_processing_archive_file_rule";
    
    public DiadocEdoInboundArchiveFileAttachmentProcessingRule(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IEdoIntegrationInboundAttachmentProcessingRule.InboundAttachmentType getAttachmentType() {
        return IEdoIntegrationInboundAttachmentProcessingRule.InboundAttachmentType.archive_file;
    }
}
