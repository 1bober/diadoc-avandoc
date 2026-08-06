// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.connections;

import com.google.api.client.util.Base64;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.service.exceptions.ServiceException;
import org.apache.commons.lang.StringUtils;
import java.util.Properties;
import java.util.Objects;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.function.UnaryOperator;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.connections.IProxyServerSettings;

public class ProxyServerSettings implements IProxyServerSettings
{
    private String address;
    private int port;
    private ProxyAuthType authType;
    private String login;
    private String password;
    private String domain;
    public static final String PROXY_ADDRESS_FIELD = "proxy_address";
    public static final String PROXY_PORT_FIELD = "proxy_port";
    public static final String PROXY_AUTH_TYPE_FIELD = "proxy_auth_type";
    private static final String PROXY_AUTH_LOGIN_FIELD = "proxy_login";
    private static final String PROXY_AUTH_PASSWORD_FIELD = "proxy_password";
    private static final String PROXY_AUTH_DOMAIN_FIELD = "proxy_domain";
    
    public static IProxyServerSettings load(final DMSShardsClientSession session, final String groupId, final String useFor, final UnaryOperator<String> getter, final SessionContext ctx) {
        final Properties props = ServiceReg.getPropertiesGroupService().getProperties(session, useFor, groupId, ctx);
        if (props.containsKey("proxy_address")) {
            final Properties obj = props;
            Objects.requireNonNull(obj);
            final IProxyServerSettings result = load((UnaryOperator<String>)obj::getProperty);
            if (result != null) {
                return result;
            }
        }
        return load(getter);
    }
    
    private static IProxyServerSettings load(final UnaryOperator<String> getter) {
        if (getter == null) {
            return null;
        }
        final String proxyAddress = getter.apply("proxy_address");
        if (StringUtils.isNotBlank(proxyAddress)) {
            final ProxyServerSettings settings = new ProxyServerSettings();
            settings.setAddress(proxyAddress);
            final String proxyPortString = getter.apply("proxy_port");
            try {
                settings.setPort(Integer.valueOf(proxyPortString));
            }
            catch (final NumberFormatException e) {
                throw new ServiceException(String.format("Failed to parse proxy port value [%s] as integer", proxyPortString));
            }
            final String proxyAuthTypeString = getter.apply("proxy_auth_type");
            final String proxyAuthLoginString = getter.apply("proxy_login");
            final String proxyAuthPasswordString = getter.apply("proxy_password");
            final String proxyAuthDomainString = getter.apply("proxy_domain");
            if (StringUtils.isBlank(proxyAuthTypeString)) {
                if (StringUtils.isBlank(proxyAuthLoginString)) {
                    settings.setAuthType(ProxyAuthType.none);
                }
                else {
                    settings.setAuthType(ProxyAuthType.ntlm);
                }
            }
            else {
                try {
                    settings.setAuthType(ProxyAuthType.valueOf(proxyAuthTypeString));
                }
                catch (final IllegalArgumentException e2) {
                    throw new ServiceException(String.format("Invalid value for my Proxy Auth Type: %s", proxyAuthTypeString));
                }
            }
            settings.setLogin(proxyAuthLoginString);
            if (StringUtils.isNotBlank(proxyAuthPasswordString)) {
                if (proxyAuthPasswordString.startsWith("ENC:")) {
                    settings.setPassword(new String(ServiceReg.getCipherService().decrypt(Base64.decodeBase64(StringUtils.substring(proxyAuthPasswordString, 4).getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8));
                }
                else {
                    settings.setPassword(proxyAuthPasswordString);
                }
            }
            settings.setDomain(proxyAuthDomainString);
            return settings;
        }
        return null;
    }
    
    @Override
    public String getAddress() {
        return this.address;
    }
    
    @Override
    public void setAddress(final String address) {
        this.address = address;
    }
    
    @Override
    public int getPort() {
        return this.port;
    }
    
    @Override
    public void setPort(final int port) {
        this.port = port;
    }
    
    @Override
    public String getLogin() {
        return this.login;
    }
    
    @Override
    public void setLogin(final String login) {
        this.login = login;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
    
    @Override
    public String getDomain() {
        return this.domain;
    }
    
    @Override
    public void setDomain(final String domain) {
        this.domain = domain;
    }
    
    @Override
    public ProxyAuthType getAuthType() {
        return (this.authType == null) ? ProxyAuthType.none : this.authType;
    }
    
    @Override
    public void setAuthType(final ProxyAuthType authType) {
        this.authType = authType;
    }
}
