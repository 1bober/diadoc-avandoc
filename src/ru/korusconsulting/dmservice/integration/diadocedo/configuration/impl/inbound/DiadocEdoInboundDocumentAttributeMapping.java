// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.inbound;

import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

public class DiadocEdoInboundDocumentAttributeMapping extends DiadocEdoInboundObjectAttributeMapping
{
    private static final long serialVersionUID = 1885295200874704008L;
    public static final String CONFIG_ID = "inbound_document_attribute_mapping";
    
    public DiadocEdoInboundDocumentAttributeMapping(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public IDiadocEdoInboundDocumentMappingProcedure.SourceType getSourceType() {
        return IDiadocEdoInboundDocumentMappingProcedure.SourceType.document;
    }
}
