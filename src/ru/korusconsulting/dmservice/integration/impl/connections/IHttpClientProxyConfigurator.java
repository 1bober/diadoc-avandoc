// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.connections;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public interface IHttpClientProxyConfigurator
{
    void configure(final HttpClientBuilder p0, final CredentialsProvider p1);
}
