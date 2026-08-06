// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDictionaryElementWithCode;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoSendPrepareErrorConfiguration extends EdoIntegrationDictionaryElementWithCode
{
    private static final long serialVersionUID = -1324225954026283573L;
    public static final String CONFIG_TYPE = "simple";
    public static final String CONFIG_ID = "send_prepare_error";
    
    public DiadocEdoSendPrepareErrorConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getConfigType() {
        return "simple";
    }
}
