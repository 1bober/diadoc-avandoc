// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.objectmodel.IIntegrationEdoSignatureProcessingContext;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdoIntegrationSignatureProcessingContext extends EdoIntegrationFileProcessingContext implements IIntegrationEdoSignatureProcessingContext
{
    private static final long serialVersionUID = -3198735374806873006L;
    
    @JsonCreator
    public EdoIntegrationSignatureProcessingContext(@JsonProperty("id") final String id, @JsonProperty("filename") final String filename, @JsonProperty("url") final String url) {
        super(id, filename, url);
    }
}
