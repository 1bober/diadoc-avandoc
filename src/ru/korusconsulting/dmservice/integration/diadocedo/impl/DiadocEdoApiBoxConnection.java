// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoAccountsNotAvailable;
import org.apache.commons.lang.StringUtils;
import Diadoc.Api.Proto.OrganizationProtos;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiAccountConnection;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;

public class DiadocEdoApiBoxConnection implements IDiadocEdoApiBoxConnection
{
    private final IDiadocEdoApiAccountConnection accountConnection;
    private final IDiadocEdoConnectionConfig config;
    
    protected DiadocEdoApiBoxConnection(final IDiadocEdoApiAccountConnection accountConnection, final IDiadocEdoConnectionConfig config) {
        this.accountConnection = accountConnection;
        this.config = config;
    }
    
    @Override
    public String getAccountMemberId() {
        return this.config.getAccountMemberId();
    }
    
    @Override
    public String getAccountMemberName() {
        return this.config.getAccountMemberName();
    }
    
    @Override
    public String getFilialId() {
        return this.config.getFilialId();
    }
    
    @Override
    public String getFilialName() {
        return this.config.getFilialName();
    }
    
    @Override
    public IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode getNotInvitedCounteragentSendMode() {
        return this.config.getNotInvitedCounteragentSendMode();
    }
    
    @Override
    public boolean isAllowSendToRejected() {
        return this.config.isAllowSendToRejected();
    }
    
    @Override
    public boolean isDontCheckCounteragentSignCertificate() {
        return this.config.isDontCheckCounteragentSignCertificate();
    }
    
    @Override
    public boolean isRemoteSignatureMode() {
        return this.config.isRemoteSignatureMode();
    }
    
    @Override
    public IDiadocEdoConnectionConfig getConfig() {
        return this.config;
    }
    
    public IDiadocEdoApiAccountConnection getAccountConnection() {
        return this.accountConnection;
    }
    
    @Override
    public IDiadocEdoApi getApi() {
        if (this.getAccountConnection() == null || this.getAccountConnection().isInvalid() || this.getAccountConnection().getApi() == null) {
            this.reconnect();
        }
        return this.getAccountConnection().getApi();
    }
    
    public void reconnect() {
    }
    
    @Override
    public String getServerId() {
        return this.getAccountConnection().getServerId();
    }
    
    @Override
    public String getServerName() {
        return this.getAccountConnection().getServerName();
    }
    
    @Override
    public String getAccountId() {
        return this.getAccountConnection().getAccountId();
    }
    
    @Override
    public String getAccountName() {
        return this.getAccountConnection().getAccountName();
    }
    
    public OrganizationProtos.Box getBox() throws DiadocEdoAccountsNotAvailable {
        if (StringUtils.isBlank(this.config.getBoxId())) {
            return this.getAccountConnection().getDefaultBox();
        }
        return this.getAccountConnection().getBox(this.config.getBoxId());
    }
}
