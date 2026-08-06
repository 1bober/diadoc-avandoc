// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import ru.korusconsulting.service.integration.processors.IEdoIntegrationContentContainer;

public abstract class EdoIntegrationAbstractContentContainer implements IEdoIntegrationContentContainer
{
    private final String uid;
    private byte[] signature;
    private String certificateThumbprint;
    
    protected EdoIntegrationAbstractContentContainer(final String uid) {
        this.uid = uid;
    }
    
    @Override
    public byte[] getSignature() {
        return this.signature;
    }
    
    @Override
    public void setSignature(final byte[] value, final String certificateThumbprint) {
        this.signature = value;
        this.certificateThumbprint = certificateThumbprint;
    }
    
    @Override
    public String getUid() {
        return this.uid;
    }
    
    @Override
    public String getCertificateThumbprint() {
        return this.certificateThumbprint;
    }
}
