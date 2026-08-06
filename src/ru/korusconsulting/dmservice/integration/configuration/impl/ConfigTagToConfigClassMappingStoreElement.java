// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;

class ConfigTagToConfigClassMappingStoreElement
{
    private final String ctsmType;
    private final String configId;
    private final Class<? extends ICstmConfigAdapter> cls;
    
    protected ConfigTagToConfigClassMappingStoreElement(final String ctsmType, final String configId, final Class<? extends ICstmConfigAdapter> cls) {
        this.ctsmType = ctsmType;
        this.configId = configId;
        this.cls = cls;
    }
    
    public String getCtsmType() {
        return this.ctsmType;
    }
    
    public String getConfigId() {
        return this.configId;
    }
    
    public Class<? extends ICstmConfigAdapter> getCls() {
        return this.cls;
    }
}
