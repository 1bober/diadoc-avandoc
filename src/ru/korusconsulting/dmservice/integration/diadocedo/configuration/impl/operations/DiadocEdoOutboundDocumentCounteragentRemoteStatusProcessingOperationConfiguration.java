// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.operations.status.StateConfigCoverage;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class DiadocEdoOutboundDocumentCounteragentRemoteStatusProcessingOperationConfiguration extends DiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = 2969562016646515670L;
    public static final String XML_TAG = "check_counteragent_remote_status_processing";
    public static final String CONFIG_ID = "diadocedo_check_counteragent_remote_status_processing";
    
    public DiadocEdoOutboundDocumentCounteragentRemoteStatusProcessingOperationConfiguration() {
        super(null);
    }
    
    public DiadocEdoOutboundDocumentCounteragentRemoteStatusProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public void init() {
        super.init();
        this.setXmlTag("check_counteragent_remote_status_processing");
    }
    
    @Override
    public String getCoverage() {
        return StateConfigCoverage.counteragent.name();
    }
}
