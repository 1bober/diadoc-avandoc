// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

public interface IEdoIntegrationAttachmentSignature extends IEdoIntegrationAdditionalInfo
{
    String getFilename();
    
    String getLink();
    
    String getOwner();
    
    String getDirectionType();
    
    String getDirection();
    
    String getCertificateThumbprint();
    
    void setFilename(final String p0);
    
    void setLink(final String p0);
    
    void setOwner(final String p0);
    
    void setDirectionType(final String p0);
    
    void setDirection(final String p0);
    
    void setCertificateThumbprint(final String p0);
    
    String getHash();
    
    void setHash(final String p0);
}
