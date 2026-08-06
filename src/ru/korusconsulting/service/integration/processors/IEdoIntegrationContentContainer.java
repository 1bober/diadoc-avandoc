// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.processors;

import java.io.IOException;
import java.io.InputStream;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IEdoIntegrationContentContainer
{
    byte[] getSignature();
    
    void setSignature(final byte[] p0, final String p1);
    
    String getUid();
    
    InputStream getContentStream(final DMSShardsClientSession p0) throws IOException;
    
    String getName();
    
    long getFileSize();
    
    String getCertificateThumbprint();
    
    Object getExternalId();
}
