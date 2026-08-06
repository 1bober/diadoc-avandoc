// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.List;

public interface IEdoIntegrationAttachmentAdditionalInfo extends IEdoIntegrationAdditionalInfo
{
    List<IEdoIntegrationAttachmentSignature> getSignaturies();
    
    void addSignatureInfo(final IEdoIntegrationAttachmentSignature p0);
    
    void removeAllSignaturies();
}
