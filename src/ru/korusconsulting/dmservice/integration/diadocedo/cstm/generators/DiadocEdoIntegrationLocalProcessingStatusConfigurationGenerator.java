// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

public class DiadocEdoIntegrationLocalProcessingStatusConfigurationGenerator extends Generator implements IGeneratorTracing
{
    private Supplier<ConfigElement> supplier;
    private static final String ROOT_CONFIG_TAG = "diadoc_edo_local_status_processing";
    private static final String ID_TAG = "id";
    private static final String CODE_TAG = "operation_code";
    
    public DiadocEdoIntegrationLocalProcessingStatusConfigurationGenerator() {
    }
    
    public DiadocEdoIntegrationLocalProcessingStatusConfigurationGenerator(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public Supplier<ConfigElement> getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        final DiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration conf = new DiadocEdoOutboundDocumentLocalStatusProcessingOperationConfiguration(null);
        conf.unmarshal(session, (ICstmComponent)cstmObject, (Generator)this, ctx);
        final RootConfigElement configurationConfig = (RootConfigElement)conf.marshal(session, "diadoc_edo_local_status_processing", (Generator)this, (Supplier)RootConfigElement::new, ctx);
        configurationConfig.setAttributeValue("id", conf.getConfigComponentId());
        configurationConfig.setLocation(cstmObject.getURI());
        final List<String> localErrors = conf.getLocalErrors();
        final String coverage = conf.getStatusCoverage();
        final Map<String, List<String>> scope = ((ICstmComponent)cstmObject).getSummScope();
        scope.computeIfAbsent("__diadocedoartifact", a -> new ArrayList()).addAll(localErrors);
        if (coverage != null) {
            scope.computeIfAbsent("diadoc_edo_state_coverage", a -> new ArrayList()).add(coverage);
        }
        ((ICstmComponent)cstmObject).getSideEffects().put("diadocedo_config", this.addScopeConfig((Map)scope, configurationConfig, ctx));
        this.trace((CstmConfigAdapter)conf, configurationConfig);
    }
}
