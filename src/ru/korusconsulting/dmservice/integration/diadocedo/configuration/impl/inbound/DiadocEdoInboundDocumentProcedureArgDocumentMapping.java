// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class DiadocEdoInboundDocumentProcedureArgDocumentMapping extends DiadocEdoInboundDocumentProcedureArgMapping
{
    private static final long serialVersionUID = -7522647153928030831L;
    public static final String CONFIG_ID = "procedure_arg_document_mapping";
    
    public DiadocEdoInboundDocumentProcedureArgDocumentMapping(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IDiadocEdoInboundDocumentMappingProcedure.SourceType getSourceType() {
        return IDiadocEdoInboundDocumentMappingProcedure.SourceType.document;
    }
    
    @Override
    public String getSpecType() {
        return "document";
    }
}
