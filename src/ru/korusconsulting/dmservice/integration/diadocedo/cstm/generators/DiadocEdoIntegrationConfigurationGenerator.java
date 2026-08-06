// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import java.util.List;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;
import java.util.Map;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.DiadocEdoServiceConfiguration;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

public class DiadocEdoIntegrationConfigurationGenerator extends Generator implements IGeneratorTracing
{
    public static final String ROOT_CONFIG_TAG = "cstm_integration";
    public static final String ROOT_CONFIG_ID = "diadoc_edo_integration";
    public static final String CONFIGURATIONS_TAG = "configurations";
    public static final String ID_TAG = "id";
    
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        final Map<String, List<String>> scope = ((ICstmComponent)cstmObject).getSummScope();
        final DiadocEdoServiceConfiguration conf = new DiadocEdoServiceConfiguration(session, this, ctx);
        conf.unmarshal(session, (ICstmComponent)cstmObject, (Generator)this, ctx);
        final RootConfigElement configurationConfig = (RootConfigElement)conf.marshal(session, "cstm_integration", (Generator)this, (Supplier)RootConfigElement::new, ctx);
        configurationConfig.setAttributeValue("id", "diadoc_edo_integration");
        configurationConfig.setLocation(cstmObject.getURI());
        ((ICstmComponent)cstmObject).getSideEffects().put("main_config", this.addScopeConfig((Map)scope, configurationConfig, ctx));
        this.trace((CstmConfigAdapter)conf, configurationConfig);
    }
}
