// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationExchangeErrorCategoryConfiguration;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoExchangeErrorCategoryConfiguration extends EdoIntegrationExchangeErrorCategoryConfiguration
{
    private static final long serialVersionUID = -1356044992936067238L;
    public static final String CONFIG_TYPE = "simple";
    public static final String CONFIG_ID = "exchange_error_category";
    
    public DiadocEdoExchangeErrorCategoryConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getConfigType() {
        return "simple";
    }
}
