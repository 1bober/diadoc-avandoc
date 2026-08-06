// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentConfiguration;

public class DiadocEdoDictionaryObjectConfiguration extends EdoIntegrationDocumentConfiguration
{
    private static final long serialVersionUID = -6765651788563080372L;
    public static final String CONFIG_ID = "dictionary_object_configuration";
    
    public DiadocEdoDictionaryObjectConfiguration(final IEdoIntegrationFlowConfiguration parent) {
        super((ICstmConfigAdapter)parent);
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        return null;
    }
    
    public IEdoIntegrationDocumentSelectCondition createDocumentSelectCondition(final DMSShardsClientSession session, final ConfigElement el, final SessionContext ctx) {
        return null;
    }
}
