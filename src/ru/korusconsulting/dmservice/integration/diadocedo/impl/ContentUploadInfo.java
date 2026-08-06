// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;

public class ContentUploadInfo
{
    private String objectId;
    private String name;
    private String signatureId;
    private String nameOnShelf;
    private String signatureNameOnShelf;
    private DiadocMessage_PostApiProtos.SignedContent.Builder signedContentBuilder;
    
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getSignatureId() {
        return this.signatureId;
    }
    
    public void setSignatureId(final String signatureId) {
        this.signatureId = signatureId;
    }
    
    public String getNameOnShelf() {
        return this.nameOnShelf;
    }
    
    public void setNameOnShelf(final String nameOnShelf) {
        this.nameOnShelf = nameOnShelf;
    }
    
    public String getSignatureNameOnShelf() {
        return this.signatureNameOnShelf;
    }
    
    public void setSignatureNameOnShelf(final String signatureNameOnShelf) {
        this.signatureNameOnShelf = signatureNameOnShelf;
    }
    
    public DiadocMessage_PostApiProtos.SignedContent.Builder getSignedContentBuilder() {
        return this.signedContentBuilder;
    }
    
    public void setSignedContentBuilder(final DiadocMessage_PostApiProtos.SignedContent.Builder signedContentBuilder) {
        this.signedContentBuilder = signedContentBuilder;
    }
}
