// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IIntegrationEdoSignatureProcessingContext;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentFileProcessingContext;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdoIntegrationAttachmentFileProcessingContext extends EdoIntegrationFileProcessingContext implements IEdoIntegrationAttachmentFileProcessingContext
{
    private static final long serialVersionUID = -2624356379220333086L;
    @JsonProperty
    private final boolean service;
    @JsonProperty
    private IEdoIntegrationFileProcessingContext imageFileProcessingContext;
    @JsonProperty
    private final List<IIntegrationEdoSignatureProcessingContext> signatures;
    
    @JsonCreator
    public EdoIntegrationAttachmentFileProcessingContext(@JsonProperty("id") final String id, @JsonProperty("filename") final String filename, @JsonProperty("url") final String url, @JsonProperty("service") final boolean service) {
        super(id, filename, url);
        this.signatures = new ArrayList<IIntegrationEdoSignatureProcessingContext>();
        this.service = service;
    }
    
    @Override
    public boolean isService() {
        return this.service;
    }
    
    @Override
    public IEdoIntegrationFileProcessingContext getImageFileProcessingContext() {
        return this.imageFileProcessingContext;
    }
    
    @Override
    public void setImageFileProcessingContext(final IEdoIntegrationFileProcessingContext value) {
        this.imageFileProcessingContext = value;
    }
    
    @Override
    public List<IIntegrationEdoSignatureProcessingContext> getSignatures() {
        return this.signatures;
    }
    
    @Override
    public void addSignature(final IIntegrationEdoSignatureProcessingContext value) {
        if (value != null) {
            this.getSignatures().add(value);
        }
    }
}
