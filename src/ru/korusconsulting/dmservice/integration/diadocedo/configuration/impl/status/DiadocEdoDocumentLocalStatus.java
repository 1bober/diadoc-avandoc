// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.status;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.status.EdoIntegrationDocumentLocalStatus;

public class DiadocEdoDocumentLocalStatus extends EdoIntegrationDocumentLocalStatus
{
    private static final long serialVersionUID = -4144744855781636299L;
    public static final String CONFIG_ID = "diadoc_local_status";
    
    public DiadocEdoDocumentLocalStatus(final ICstmConfigAdapter parent) {
        super(parent);
    }
}
