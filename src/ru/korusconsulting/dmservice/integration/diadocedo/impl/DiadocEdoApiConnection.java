// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.config.Registry;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.config.Lookup;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.korusconsulting.service.integration.connections.IProxyServerSettings;
import ru.korusconsulting.dmservice.integration.impl.connections.HttpClientConnectionConfigurator;
import ru.korusconsulting.dmservice.integration.impl.connections.ProxyServerSettings;
import ru.korusconsulting.service.integration.diadocedo.DiadocEdoModuleProperties;
import ru.korusconsulting.dmservice.integration.impl.connections.IHttpClientConnectionConfigurator;
import java.util.Collection;
import java.util.HashSet;
import org.apache.http.client.config.RequestConfig;
import Diadoc.Api.exceptions.DiadocSdkException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoApi;
import java.util.Optional;
import ru.korusconsulting.service.exceptions.ServiceException;
import Diadoc.Api.ISslSocketFactoryBuilder;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Properties;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;

public class DiadocEdoApiConnection
{
    private IDiadocEdoApi api;
    protected static final Logger logger;
    protected Map<String, SSLConnectionSocketFactory> sslConnectionSocketFactoryCache;
    
    protected DiadocEdoApiConnection(final DMSShardsClientSession session, final IDiadocEdoIntegrationServiceInternal service, final IDiadocEdoOperationContext operationContext, final String url, final String apiKey, final String login, final String password, final Properties props, final SessionContext ctx) throws DiadocEdoIntegrationException {
        this.sslConnectionSocketFactoryCache = new HashMap<String, SSLConnectionSocketFactory>();
        final String sslSocketFactoryClass = props.getProperty("ssl.socket.factory.builder");
        SSLConnectionSocketFactory factory;
        if (StringUtils.isNotBlank(sslSocketFactoryClass)) {
            final String key = String.join(sslSocketFactoryClass, props.getProperty("__VSTAMP"));
            factory = this.sslConnectionSocketFactoryCache.computeIfAbsent(key, a -> {
                try {
                    final ISslSocketFactoryBuilder f = (ISslSocketFactoryBuilder)Class.forName(sslSocketFactoryClass).getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                    return f.get(props);
                }
                catch (final Exception e) {
                    throw new ServiceException(e);
                }
            });
        }
        else {
            factory = null;
        }
        DiadocEdoApiMethodLibrary.authenticate(session, service, operationContext, this.api = new DiadocEdoApi(apiKey, url, () -> factory, (a, b) -> Optional.ofNullable(this.getConnectionConfigurator(session, ctx)).ifPresent(c -> c.configure(a, b))), login, password, ctx);
    }
    
    public void authenticate(final String login, final String password) throws DiadocSdkException {
        this.api.getAuthClient().authenticate(login, password);
    }
    
    public IDiadocEdoApi getApi() {
        return this.api;
    }
    
    public void setApi(final IDiadocEdoApi api) {
        this.api = api;
    }
    
    protected RequestConfig getRequestConfig() {
        final HashSet<String> authSchemes = new HashSet<String>();
        authSchemes.add("NTLM");
        authSchemes.add("Basic");
        authSchemes.add("Digest");
        return RequestConfig.custom().setTargetPreferredAuthSchemes((Collection)authSchemes).build();
    }
    
    protected IHttpClientConnectionConfigurator getConnectionConfigurator(final DMSShardsClientSession session, final SessionContext ctx) {
        IHttpClientConnectionConfigurator connectionConfigurator = null;
        final IProxyServerSettings proxySettings = ProxyServerSettings.load(session, "DIADOCEDO", "connection_extra_settings", DiadocEdoModuleProperties::getProperty, ctx);
        if (proxySettings != null) {
            connectionConfigurator = (IHttpClientConnectionConfigurator)new HttpClientConnectionConfigurator();
            connectionConfigurator.setProxyConfigurator((clientBuilder, credentialsProvider) -> {
                final Registry<AuthSchemeProvider> authSchemeProviderRegistry = (Registry<AuthSchemeProvider>)proxySettings.getAuthType().getAuthSchemeRegistry();
                if (authSchemeProviderRegistry != null) {
                    clientBuilder.setDefaultAuthSchemeRegistry((Lookup)authSchemeProviderRegistry);
                }
                final Credentials credentials = proxySettings.getAuthType().getCredentials(proxySettings.getLogin(), proxySettings.getPassword(), proxySettings.getDomain());
                if (credentials != null) {
                    if (credentialsProvider != null) {
                        credentialsProvider.setCredentials(new AuthScope(proxySettings.getAddress(), proxySettings.getPort(), AuthScope.ANY_REALM), credentials);
                    }
                    else {
                        final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
                        credsProvider.setCredentials(new AuthScope(proxySettings.getAddress(), proxySettings.getPort(), AuthScope.ANY_REALM), credentials);
                        clientBuilder.setDefaultCredentialsProvider((CredentialsProvider)credsProvider);
                    }
                }
                clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
                final HttpHost proxy = new HttpHost(proxySettings.getAddress(), proxySettings.getPort());
                clientBuilder.setProxy(proxy);
                clientBuilder.setProxyAuthenticationStrategy((AuthenticationStrategy)new ProxyAuthenticationStrategy());
            });
        }
        return connectionConfigurator;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
