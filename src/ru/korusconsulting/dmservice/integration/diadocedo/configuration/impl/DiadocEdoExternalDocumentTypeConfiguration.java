// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDictionaryElementWithCode;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class DiadocEdoExternalDocumentTypeConfiguration extends EdoIntegrationDictionaryElementWithCode
{
    private static final long serialVersionUID = 6266710228303461445L;
    public static final String CONFIG_TYPE = "simple";
    public static final String CONFIG_ID = "external_type";
    
    public DiadocEdoExternalDocumentTypeConfiguration(final ICstmConfigAdapter parent) {
        super(parent);
    }
    
    public String getConfigType() {
        return "simple";
    }
}
