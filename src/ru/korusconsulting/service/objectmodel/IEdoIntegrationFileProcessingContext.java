// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationSignatureProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationAttachmentFileProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationFileProcessingContext;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "object_type")
@JsonSubTypes({ @JsonSubTypes.Type(value = EdoIntegrationFileProcessingContext.class, name = "inbound_file"), @JsonSubTypes.Type(value = EdoIntegrationAttachmentFileProcessingContext.class, name = "incoming_doc_file"), @JsonSubTypes.Type(value = EdoIntegrationSignatureProcessingContext.class, name = "incoming_doc_file_signature") })
public interface IEdoIntegrationFileProcessingContext extends Serializable
{
    boolean isNotNeeded();
    
    void setNotNeeded(final boolean p0);
    
    String getUrl();
    
    String getFilename();
    
    boolean isProceed();
    
    void setProceed(final boolean p0);
    
    String getId();
    
    void setFatal(final boolean p0);
    
    boolean isFatal();
}
