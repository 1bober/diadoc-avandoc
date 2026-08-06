// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import java.util.Map;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.cstm.ICstmObject;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.cstm.generators.Generator;

@Deprecated
public class DiadocEdoExchangeCategoryDictionaryGenerator extends Generator
{
    private Logger log;
    @Deprecated
    public static final String ROOT_CONFIG_TAG = "cstm_integration";
    @Deprecated
    public static final String ROOT_CONFIG_ID = "diadoc_edo_integration";
    @Deprecated
    public static final String CONFIGURATIONS_TAG = "configurations";
    @Deprecated
    public static final String ID_TAG = "id";
    
    public DiadocEdoExchangeCategoryDictionaryGenerator() {
        this.log = LogManager.getLogger((Class)DiadocEdoExchangeCategoryDictionaryGenerator.class);
    }
    
    @Deprecated
    public void gen(final DMSShardsClientSession session, final ICstmObject cstmObject, final ConfigElement configElement, final SessionContext ctx) {
        final Map<String, List<String>> scope = ((ICstmComponent)cstmObject).getSummScope();
        ConfigElement integrationConfigurationConfig = ServiceReg.getConfigService().lookupElement(String.format("cstm_integration[id=%s]", "diadoc_edo_integration"), ctx);
        if (integrationConfigurationConfig == null) {
            integrationConfigurationConfig = (ConfigElement)new RootConfigElement();
            integrationConfigurationConfig.setTagName("cstm_integration");
            integrationConfigurationConfig.setAttributeValue("id", "diadoc_edo_integration");
            ((ICstmComponent)cstmObject).getSideEffects().put("main_config", this.addScopeConfig((Map)scope, (RootConfigElement)integrationConfigurationConfig, ctx));
        }
        ConfigElement dictionary = integrationConfigurationConfig.getFirstChild("exchange_category_dictionary");
        if (dictionary == null) {
            dictionary = new ConfigElement();
            dictionary.setTagName("exchange_category_dictionary");
            integrationConfigurationConfig.addChild(dictionary);
        }
        final ConfigElement value = new ConfigElement();
        value.setTagName("value");
        value.setAttributeValue("id", cstmObject.getValueAsString("ssa_category_code"));
        final String label = StringUtils.isNotBlank(cstmObject.getValueAsString("ssa_category_label")) ? cstmObject.getValueAsString("ssa_category_label") : cstmObject.getValueAsString("ssa_name");
        value.setElementValue(label);
        dictionary.addChild(value);
        this.log.info(() -> String.format("Created xml element %n%s", value.asString()));
    }
}
