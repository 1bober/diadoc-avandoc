// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.List;

public interface IEdoIntegrationAttachmentFileProcessingContext extends IEdoIntegrationFileProcessingContext
{
    boolean isService();
    
    IEdoIntegrationFileProcessingContext getImageFileProcessingContext();
    
    void setImageFileProcessingContext(final IEdoIntegrationFileProcessingContext p0);
    
    List<IIntegrationEdoSignatureProcessingContext> getSignatures();
    
    void addSignature(final IIntegrationEdoSignatureProcessingContext p0);
}
