// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.status;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationDocumentRemoteStatus;

public class DiadocEdoDocumentRemoteStatus extends EdoIntegrationDocumentRemoteStatus
{
    private static final long serialVersionUID = -8909028217087955497L;
    public static final String CONFIG_ID = "diadoc_remote_status";
    
    public DiadocEdoDocumentRemoteStatus(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
