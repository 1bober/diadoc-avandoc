// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDictionaryElementWithCode;

public class DiadocEdoExchangeCategoryConfiguration extends EdoIntegrationDictionaryElementWithCode
{
    private static final long serialVersionUID = -1377603173544106787L;
    public static final String CONFIG_ID = "exchange_category";
    public static final String CONFIG_TYPE = "simple";
    
    public DiadocEdoExchangeCategoryConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getConfigType() {
        return "simple";
    }
}
