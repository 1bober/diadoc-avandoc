// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations;

import ru.korusconsulting.service.integration.configuration.operations.status.StateConfigCoverage;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class DiadocEdoOutboundDocumentCumulativeRemoteStatusProcessingOperationConfiguration extends DiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration
{
    private static final long serialVersionUID = 6802307560326448130L;
    public static final String XML_TAG = "check_cumulative_remote_status_processing";
    public static final String CONFIG_ID = "diadocedo_check_cumulative_remote_status_processing";
    
    public DiadocEdoOutboundDocumentCumulativeRemoteStatusProcessingOperationConfiguration() {
        super(null);
    }
    
    public DiadocEdoOutboundDocumentCumulativeRemoteStatusProcessingOperationConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    @Override
    public void init() {
        super.init();
        this.setXmlTag("check_cumulative_remote_status_processing");
    }
    
    @Override
    public String getCoverage() {
        return StateConfigCoverage.all.name();
    }
}
