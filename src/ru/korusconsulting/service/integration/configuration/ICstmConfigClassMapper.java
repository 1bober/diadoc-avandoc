// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigTagToConfigClassMappingStore;
import java.util.Map;

public interface ICstmConfigClassMapper
{
    Map<String, ConfigTagToConfigClassMappingStore> getTagToConfigMappingStore();
    
    default <T extends ICstmConfigAdapter> T getConfigIntanceByCstmType(final ICstmConfigAdapter parentConfig, final String groupId, final String cstmType, final String xmlTag) {
        final ConfigTagToConfigClassMappingStore store = this.getTagToConfigMappingStore().get(groupId);
        if (store != null) {
            final T inst = store.getConfigIntanceByCstmType(parentConfig, cstmType, xmlTag);
            if (inst != null) {
                return inst;
            }
        }
        if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
            throw new IllegalArgumentException(String.format("Unsupported component type %s", cstmType));
        }
        return null;
    }
    
    default <T extends ICstmConfigAdapter> T getConfigIntanceByConfigId(final ICstmConfigAdapter parentConfig, final String groupId, final String xmlTag, final String configId) {
        final ConfigTagToConfigClassMappingStore store = this.getTagToConfigMappingStore().get(groupId);
        if (store != null) {
            final T inst = store.getConfigIntanceByConfigId(parentConfig, xmlTag, configId);
            if (inst != null) {
                return inst;
            }
        }
        if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
            throw new IllegalArgumentException(String.format("Unsupported config id %s for tag %s", configId, xmlTag));
        }
        return null;
    }
}
