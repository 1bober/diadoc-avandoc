// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.connections;

import org.apache.http.auth.NTCredentials;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.BasicSchemeFactory;
import ru.korusconsulting.dmservice.integration.impl.connections.BasicForNtlmSchemeFactory;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.config.Registry;

public interface IProxyServerSettings
{
    String getAddress();
    
    void setAddress(final String p0);
    
    int getPort();
    
    void setPort(final int p0);
    
    String getLogin();
    
    void setLogin(final String p0);
    
    String getPassword();
    
    void setPassword(final String p0);
    
    String getDomain();
    
    void setDomain(final String p0);
    
    ProxyAuthType getAuthType();
    
    void setAuthType(final ProxyAuthType p0);
    
    public enum ProxyAuthType permits IProxyServerSettings$ProxyAuthType$1, IProxyServerSettings$ProxyAuthType$2, IProxyServerSettings$ProxyAuthType$3
    {
        none {
        }, 
        plain {
            @Override
            public Registry<AuthSchemeProvider> getAuthSchemeRegistry() {
                final RegistryBuilder<AuthSchemeProvider> registryBuilder = (RegistryBuilder<AuthSchemeProvider>)RegistryBuilder.create();
                registryBuilder.register("NTLM", (Object)new BasicForNtlmSchemeFactory()).register("Basic", (Object)new BasicSchemeFactory()).register("Digest", (Object)new DigestSchemeFactory());
                return (Registry<AuthSchemeProvider>)registryBuilder.build();
            }
            
            @Override
            public Credentials getCredentials(final String login, final String password, final String domain) {
                if (StringUtils.isNoneBlank(new CharSequence[] { domain })) {
                    return (Credentials)new UsernamePasswordCredentials(String.join("\\", domain, login), password);
                }
                return (Credentials)new UsernamePasswordCredentials(login, password);
            }
        }, 
        ntlm {
            @Override
            public Registry<AuthSchemeProvider> getAuthSchemeRegistry() {
                final RegistryBuilder<AuthSchemeProvider> registryBuilder = (RegistryBuilder<AuthSchemeProvider>)RegistryBuilder.create();
                registryBuilder.register("NTLM", (Object)new NTLMSchemeFactory()).register("Basic", (Object)new BasicSchemeFactory()).register("Digest", (Object)new DigestSchemeFactory());
                return (Registry<AuthSchemeProvider>)registryBuilder.build();
            }
            
            @Override
            public Credentials getCredentials(final String login, final String password, final String domain) {
                return (Credentials)new NTCredentials(login, password, (String)null, domain);
            }
        };
        
        public Registry<AuthSchemeProvider> getAuthSchemeRegistry() {
            return null;
        }
        
        public Credentials getCredentials(final String login, final String password, final String domain) {
            return null;
        }
    }
}
