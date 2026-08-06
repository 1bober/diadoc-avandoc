// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import java.util.List;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;
import java.util.Map;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

public class DiadocEdoIntegrationSendDocumentConfigurationGenerator extends Generator implements IGeneratorTracing
{
    private Supplier<ConfigElement> supplier;
    private static final String ROOT_CONFIG_TAG = "diadoc_edo_send_operation";
    private static final String ID_TAG = "id";
    private static final String CODE_TAG = "operation_code";
    
    public DiadocEdoIntegrationSendDocumentConfigurationGenerator() {
    }
    
    public DiadocEdoIntegrationSendDocumentConfigurationGenerator(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public Supplier<ConfigElement> getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        final DiadocEdoOutboundDocumentSendOperationConfiguration conf = new DiadocEdoOutboundDocumentSendOperationConfiguration();
        conf.unmarshal(session, (ICstmComponent)cstmObject, (Generator)this, ctx);
        final RootConfigElement configurationConfig = (RootConfigElement)conf.marshal(session, "diadoc_edo_send_operation", (Generator)this, (Supplier)RootConfigElement::new, ctx);
        configurationConfig.setAttributeValue("id", conf.getConfigComponentId());
        configurationConfig.setLocation(cstmObject.getURI());
        final Map<String, List<String>> scope = ((ICstmComponent)cstmObject).getSummScope();
        this.addScopeConfig((Map)scope, configurationConfig, ctx);
        ((ICstmComponent)cstmObject).getSideEffects().put("diadocedo_config", this.addScopeConfig((Map)scope, configurationConfig, ctx));
        this.trace((CstmConfigAdapter)conf, configurationConfig);
    }
}
