// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.incoming.DiadocEdoIncomingDocumentReceiveConfiguration;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

public class DiadocEdoIntegrationIncomingDocumentProcessingConfigurationGenerator extends Generator implements IGeneratorTracing
{
    protected static final Logger logger;
    private static final String ROOT_CONFIG_TAG = "incoming_document_receive_operation_processing";
    private Supplier<ConfigElement> supplier;
    private static final String ID_TAG = "id";
    private static final String CODE_TAG = "operation_code";
    
    public DiadocEdoIntegrationIncomingDocumentProcessingConfigurationGenerator() {
    }
    
    public DiadocEdoIntegrationIncomingDocumentProcessingConfigurationGenerator(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public Supplier<ConfigElement> getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(final Supplier<ConfigElement> supplier) {
        this.supplier = supplier;
    }
    
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        final DiadocEdoIncomingDocumentReceiveConfiguration conf = new DiadocEdoIncomingDocumentReceiveConfiguration(null);
        conf.unmarshal(session, (ICstmComponent)cstmObject, (Generator)this, ctx);
        final RootConfigElement configurationConfig = (RootConfigElement)conf.marshal(session, "incoming_document_receive_operation_processing", (Generator)this, (Supplier)RootConfigElement::new, ctx);
        configurationConfig.setAttributeValue("id", conf.getConfigComponentId());
        configurationConfig.setLocation(cstmObject.getURI());
        final List<String> externalTypes = conf.getExternalTypes();
        final Map<String, List<String>> scope = ((ICstmComponent)cstmObject).getSummScope();
        scope.computeIfAbsent("__diadocedoartifact", a -> new ArrayList()).addAll(externalTypes);
        ((ICstmComponent)cstmObject).getSideEffects().put("diadocsedo_config", this.addScopeConfig((Map)scope, configurationConfig, ctx));
        this.trace((CstmConfigAdapter)conf, configurationConfig);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
