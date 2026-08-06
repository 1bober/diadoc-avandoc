// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.IObjectModelService;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.impl.exceptions.InvalidConfigurationException;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IDiadocEdoServer;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccount;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccountMember;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.dmservice.integration.impl.EdoConnectionConfig;

public class DiadocEdoConnectionConfig extends EdoConnectionConfig implements IDiadocEdoConnectionConfig
{
    private final IDiadocEdoAccountMember member;
    private final IDiadocEdoAccount account;
    private final IDiadocEdoServer server;
    private final boolean dontCheckCounteragentSignCertificate;
    
    public DiadocEdoConnectionConfig(final DMSShardsClientSession session, final IDiadocEdoServer server, final IDiadocEdoAccount account, final IDiadocEdoAccountMember member, final SessionContext ctx) throws InvalidConfigurationException {
        super(session, (server == null) ? null : server.getObjectId(), (server == null) ? null : server.getName(), (member == null) ? null : member.getFilial(), (member == null) ? null : member.getFilialName(), LogPropogateMode.parse((member == null) ? 0 : member.getLogPropogateMode()), ctx);
        this.member = member;
        if (member == null) {
            throw new InvalidConfigurationException("Failed to get account member for diadoc edo connection");
        }
        if ((this.account = account) == null) {
            throw new InvalidConfigurationException(String.format("Failed to get account for diadoc edo connection for member %s", member.getObjectId()));
        }
        this.dontCheckCounteragentSignCertificate = member.isDontCheckCounteragentSignCertificate(account);
        if ((this.server = server) == null) {
            throw new InvalidConfigurationException(String.format("Failed to get account for diadoc edo connection for account %s", member.getObjectId()));
        }
    }
    
    public String getName() {
        return this.server.getName();
    }
    
    public String getUrl() {
        return this.server.getUrl();
    }
    
    public String getWebUrl() {
        return this.server.getWebUrl();
    }
    
    public String getSessionKey() {
        return null;
    }
    
    public String getBoxId() {
        return this.member.getBox();
    }
    
    public boolean isDontCheckCounteragentSignCertificate() {
        return this.dontCheckCounteragentSignCertificate;
    }
    
    public boolean isRemoteSignatureMode() {
        return this.member.isRemoteSignatureMode();
    }
    
    public String getLogin() {
        return this.account.getLogin();
    }
    
    public String getPassword() {
        return this.account.getPassword();
    }
    
    public String getApiKey() {
        return this.server.getApiKey();
    }
    
    public String getAccountId() {
        return (this.account != null) ? this.account.getObjectId() : null;
    }
    
    public String getAccountName() {
        return (this.account != null) ? this.account.getLogin() : null;
    }
    
    public String getAccountMemberId() {
        return (this.member != null) ? this.member.getObjectId() : null;
    }
    
    public String getAccountMemberName() {
        return (this.member != null) ? this.member.getBox() : null;
    }
    
    public static DiadocEdoConnectionConfig get(final DMSShardsClientSession session, final IDiadocEdoAccountMember member, final SessionContext ctx) throws InvalidConfigurationException {
        if (member == null) {
            throw new InvalidConfigurationException("Failed to get account member for diadoc edo connection");
        }
        final IDiadocEdoAccount account = (IDiadocEdoAccount)ServiceReg.getObjectModelService().getObjectModel(session, member.getAccount(), ctx);
        if (account == null) {
            throw new InvalidConfigurationException(String.format("Failed to get account for diadoc edo connection for member %s", member.getObjectId()));
        }
        final IDiadocEdoServer server = (IDiadocEdoServer)ServiceReg.getObjectModelService().getObjectModel(session, account.getServer(), ctx);
        if (server == null) {
            throw new InvalidConfigurationException(String.format("Failed to get account for diadoc edo connection for account %s", member.getObjectId()));
        }
        return new DiadocEdoConnectionConfig(session, server, account, member, ctx);
    }
    
    public NotInvitedCounteragentSendMode getNotInvitedCounteragentSendMode() {
        return NotInvitedCounteragentSendMode.parse(this.member.getNonInvitedSendMode());
    }
    
    public boolean isAllowSendToRejected() {
        return this.member.isAllowSendToRejected();
    }
    
    public String getOutboundDepartment(final String exchangeCategory) {
        if (StringUtils.isNotBlank(exchangeCategory) && this.member.getOutboundCategories() != null) {
            final int p = this.member.getOutboundCategories().indexOf(exchangeCategory);
            if (p >= 0 && p < this.member.getOutboundCategoryDepartments().size()) {
                return this.member.getOutboundCategoryDepartments().get(p);
            }
        }
        if (StringUtils.isNotBlank(this.member.getOutboundDepartment())) {
            return this.member.getOutboundDepartment();
        }
        return this.member.getDepartment();
    }
    
    public String getInboundDepartment(final String exchangeCategory) {
        if (StringUtils.isNotBlank(exchangeCategory) && this.member.getInboundCategories() != null) {
            final int p = this.member.getInboundCategories().indexOf(exchangeCategory);
            if (p >= 0 && p < this.member.getInboundCategoryDepartments().size()) {
                return this.member.getInboundCategoryDepartments().get(p);
            }
        }
        if (StringUtils.isNotBlank(this.member.getInboundDepartment())) {
            return this.member.getInboundDepartment();
        }
        return this.member.getDepartment();
    }
    
    public boolean isDontCheckIncoming() {
        return this.member.isDontCheckIncoming();
    }
    
    public Date getIncomingDocumentsDateCheckFrom(final DMSShardsClientSession session, final SessionContext ctx) {
        final IObjectModelService service = ServiceReg.getObjectModelService();
        final IDiadocEdoAccountMember memberObj = (IDiadocEdoAccountMember)service.getObjectModel(session, this.getAccountMemberId(), ctx);
        return memberObj.getIncomingDocumentsDateCheckFrom();
    }
    
    public int getCheckOffset() {
        return this.member.getIncDocsCheckOffset();
    }
    
    public int getPageSize() {
        return 0;
    }
    
    public String getInn() {
        return this.member.getInn();
    }
    
    public String getKpp() {
        return this.member.getKpp();
    }
    
    public void commitIncomingDocumentsDateCheckFrom(final DMSShardsClientSession session, final Date time, final SessionContext ctx) {
        final IObjectModelService service = ServiceReg.getObjectModelService();
        final IDiadocEdoAccountMember memberObj = (IDiadocEdoAccountMember)service.getObjectModel(session, this.getAccountMemberId(), ctx);
        memberObj.setIncomingDocumentsDateCheckFrom(time);
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)memberObj, ctx);
    }
    
    public boolean isAllowTestCounteragent() {
        return this.member.isAllowSendToTestCounteragents();
    }
}
