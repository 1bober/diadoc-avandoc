// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

public class SignatureInfo
{
    private final String signature;
    private final String certificate;
    
    public SignatureInfo(final String signature, final String certificate) {
        this.signature = signature;
        this.certificate = certificate;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public String getCertificate() {
        return this.certificate;
    }
}
