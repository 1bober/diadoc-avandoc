// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.configuration.inbound;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public interface IDiadocEdoInboundDocumentProcedureArgParameter extends ICstmConfigAdapter
{
    String getProcArgument();
    
    void setProcArgument(final String p0);
}
