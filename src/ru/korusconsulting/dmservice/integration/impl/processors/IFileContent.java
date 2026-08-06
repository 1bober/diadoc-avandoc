// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.nio.file.Path;

interface IFileContent
{
    boolean isIdentifiedSignature();
    
    boolean isNotIdentifiedSignature();
    
    boolean isSignature();
    
    boolean isSignedContent();
    
    boolean isContent();
    
    Path getPath();
    
    long length();
    
    boolean exists();
    
    void setSignature(final byte[] p0);
    
    byte[] getSignature();
    
    void setCertificateThumbprint(final String p0);
    
    String getCertificateThumbprint();
}
