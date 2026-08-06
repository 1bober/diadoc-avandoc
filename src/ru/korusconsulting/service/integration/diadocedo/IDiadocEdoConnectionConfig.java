// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.Properties;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoConnectionConfig;

public interface IDiadocEdoConnectionConfig extends IEdoConnectionConfig
{
    String getName();
    
    String getUrl();
    
    String getWebUrl();
    
    String getSessionKey();
    
    String getBoxId();
    
    boolean isDontCheckCounteragentSignCertificate();
    
    boolean isRemoteSignatureMode();
    
    NotInvitedCounteragentSendMode getNotInvitedCounteragentSendMode();
    
    boolean isAllowSendToRejected();
    
    String getLogin();
    
    String getPassword();
    
    String getApiKey();
    
    String getServerId();
    
    String getServerName();
    
    String getAccountId();
    
    String getAccountName();
    
    String getAccountMemberId();
    
    String getAccountMemberName();
    
    String getOutboundDepartment(final String p0);
    
    String getInboundDepartment(final String p0);
    
    default Properties loadProperties(final DMSShardsClientSession session, final String serverId, final SessionContext ctx) {
        final Properties commonProperties = ServiceReg.getPropertiesGroupService().getProperties(session, "connection_extra_settings", "DIADOCEDO", ctx);
        final Properties serverProperties = StringUtils.isNotBlank(serverId) ? ServiceReg.getPropertiesGroupService().getProperties(session, "connection_extra_settings", "DIADOCEDO", (String)null, serverId, ctx) : null;
        if (commonProperties == null) {
            return serverProperties;
        }
        if (serverProperties == null) {
            return commonProperties;
        }
        final String cV = String.join(".", serverProperties.getProperty("__VSTAMP"), commonProperties.getProperty("__VSTAMP"));
        commonProperties.putAll(serverProperties);
        commonProperties.setProperty("__VSTAMP", cV);
        return commonProperties;
    }
    
    Date getIncomingDocumentsDateCheckFrom(final DMSShardsClientSession p0, final SessionContext p1);
    
    int getCheckOffset();
    
    int getPageSize();
    
    String getInn();
    
    String getKpp();
    
    void commitIncomingDocumentsDateCheckFrom(final DMSShardsClientSession p0, final Date p1, final SessionContext p2);
    
    boolean isAllowTestCounteragent();
    
    public enum NotInvitedCounteragentSendMode
    {
        error, 
        send_document, 
        type_configured;
        
        public static NotInvitedCounteragentSendMode parse(final String value) {
            for (final NotInvitedCounteragentSendMode v : values()) {
                if (StringUtils.equals(value, v.name())) {
                    return v;
                }
            }
            return NotInvitedCounteragentSendMode.error;
        }
    }
}
