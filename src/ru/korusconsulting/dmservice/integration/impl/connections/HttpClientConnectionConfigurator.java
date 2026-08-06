// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.connections;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientConnectionConfigurator implements IHttpClientConnectionConfigurator
{
    private IHttpClientProxyConfigurator proxyConfigurator;
    
    public IHttpClientProxyConfigurator getProxyConfigurator() {
        return this.proxyConfigurator;
    }
    
    @Override
    public void setProxyConfigurator(final IHttpClientProxyConfigurator value) {
        this.proxyConfigurator = value;
    }
    
    @Override
    public HttpClientBuilder configure(final HttpClientBuilder clientBuilder, final CredentialsProvider credentialsProvider) {
        if (this.proxyConfigurator != null) {
            this.proxyConfigurator.configure(clientBuilder, credentialsProvider);
        }
        return clientBuilder;
    }
}
