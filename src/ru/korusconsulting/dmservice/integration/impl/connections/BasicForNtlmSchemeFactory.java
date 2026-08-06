// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.connections;

import org.apache.http.protocol.HttpContext;
import org.apache.http.auth.AuthScheme;
import org.apache.http.params.HttpParams;
import java.nio.charset.Charset;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthSchemeFactory;

public class BasicForNtlmSchemeFactory implements AuthSchemeFactory, AuthSchemeProvider
{
    private final Charset charset;
    
    public BasicForNtlmSchemeFactory(final Charset charset) {
        this.charset = charset;
    }
    
    public BasicForNtlmSchemeFactory() {
        this(null);
    }
    
    public AuthScheme newInstance(final HttpParams params) {
        return (AuthScheme)new BasicForNtlmScheme();
    }
    
    public AuthScheme create(final HttpContext context) {
        return (AuthScheme)new BasicForNtlmScheme(this.charset);
    }
}
