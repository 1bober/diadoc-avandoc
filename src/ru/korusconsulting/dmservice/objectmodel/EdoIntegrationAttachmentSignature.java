// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentSignature;

public class EdoIntegrationAttachmentSignature implements IEdoIntegrationAttachmentSignature
{
    private static final long serialVersionUID = -6111448291810071091L;
    private transient boolean isDirty;
    private String filename;
    private String link;
    private String owner;
    private String directionType;
    private String direction;
    private String certificateThumbprint;
    private String hash;
    
    @Override
    public String getFilename() {
        return this.filename;
    }
    
    @Override
    public void setFilename(final String value) {
        this.isDirty = true;
        this.filename = value;
    }
    
    @Override
    public String getLink() {
        return this.link;
    }
    
    @Override
    public void setLink(final String value) {
        this.isDirty = true;
        this.link = value;
    }
    
    @Override
    public String getOwner() {
        return this.owner;
    }
    
    @Override
    public void setOwner(final String value) {
        this.isDirty = true;
        this.owner = value;
    }
    
    @Override
    public String getDirectionType() {
        return this.directionType;
    }
    
    @Override
    public void setDirectionType(final String value) {
        this.isDirty = true;
        this.directionType = value;
    }
    
    @Override
    public String getDirection() {
        return this.direction;
    }
    
    @Override
    public void setDirection(final String value) {
        this.isDirty = true;
        this.direction = value;
    }
    
    @Override
    public String getCertificateThumbprint() {
        return this.certificateThumbprint;
    }
    
    @Override
    public void setCertificateThumbprint(final String value) {
        this.isDirty = true;
        this.certificateThumbprint = value;
    }
    
    @Override
    public String getHash() {
        return this.hash;
    }
    
    @Override
    public void setHash(final String value) {
        this.isDirty = true;
        this.hash = value;
    }
    
    @Override
    public boolean isDirty() {
        return this.isDirty;
    }
    
    @Override
    public void setDirty(final boolean value) {
        this.isDirty = value;
    }
}
