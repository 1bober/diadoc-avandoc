// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.HashMap;
import java.util.Map;

public class ConfigTagToConfigClassMappingStore
{
    private final Map<String, ConfigTagToConfigClassMappingStoreElement> byCtsmType;
    private final Map<String, ConfigTagToConfigClassMappingStoreElement> byConfigId;
    
    public ConfigTagToConfigClassMappingStore() {
        this.byCtsmType = new HashMap<String, ConfigTagToConfigClassMappingStoreElement>();
        this.byConfigId = new HashMap<String, ConfigTagToConfigClassMappingStoreElement>();
    }
    
    public Map<String, ConfigTagToConfigClassMappingStoreElement> getByCtsmType() {
        return this.byCtsmType;
    }
    
    public Map<String, ConfigTagToConfigClassMappingStoreElement> getByConfigId() {
        return this.byConfigId;
    }
    
    public void add(final String cstmType, final String configId, final Class<? extends ICstmConfigAdapter> cls) {
        final ConfigTagToConfigClassMappingStoreElement el = new ConfigTagToConfigClassMappingStoreElement(cstmType, configId, cls);
        this.getByCtsmType().put(cstmType, el);
        this.getByConfigId().put(configId, el);
    }
    
    protected ConfigTagToConfigClassMappingStoreElement getForCtsmType(final String value) {
        return this.getByCtsmType().get(value);
    }
    
    protected ConfigTagToConfigClassMappingStoreElement getForConfigId(final String value) {
        return this.getByConfigId().get(value);
    }
    
    public <T extends ICstmConfigAdapter> T getConfigIntanceByCstmType(final ICstmConfigAdapter parentConfig, final String cstmType, final String xmlTag) {
        final ConfigTagToConfigClassMappingStoreElement el = this.getForCtsmType(cstmType);
        if (el != null) {
            try {
                final Constructor<? extends ICstmConfigAdapter> clsConstructor = el.getCls().getDeclaredConstructor(ICstmConfigAdapter.class);
                if (clsConstructor != null) {
                    final T inst = (T)clsConstructor.newInstance(parentConfig);
                    if (StringUtils.isNotBlank(xmlTag)) {
                        inst.setXmlTag(xmlTag);
                    }
                    inst.setConfigId(el.getConfigId());
                    return inst;
                }
                throw new IllegalArgumentException(String.format("Class %s must have constructor with one param (ICstmConfigAdapter value)", el.getCls().getName()));
            }
            catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new ServiceException(String.format("Failed to create class %s for component type %s", el.getCls().getName(), cstmType), (Throwable)e);
            }
        }
        return null;
    }
    
    public <T extends ICstmConfigAdapter> T getConfigIntanceByConfigId(final ICstmConfigAdapter parentConfig, final String xmlTag, final String configId) {
        final ConfigTagToConfigClassMappingStoreElement el = this.getForConfigId(configId);
        if (el != null) {
            try {
                final Constructor<? extends ICstmConfigAdapter> clsConstructor = el.getCls().getDeclaredConstructor(ICstmConfigAdapter.class);
                if (clsConstructor != null) {
                    final T inst = (T)clsConstructor.newInstance(parentConfig);
                    if (StringUtils.isNotBlank(xmlTag)) {
                        inst.setXmlTag(xmlTag);
                    }
                    inst.setConfigId(configId);
                    return inst;
                }
                throw new IllegalArgumentException(String.format("Class %s must have constructor with one param (ICstmConfigAdapter value)", el.getCls().getName()));
            }
            catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new ServiceException(String.format("Failed to create class %s for xml tag type %s", el.getCls().getName(), xmlTag), (Throwable)e);
            }
        }
        return null;
    }
}
