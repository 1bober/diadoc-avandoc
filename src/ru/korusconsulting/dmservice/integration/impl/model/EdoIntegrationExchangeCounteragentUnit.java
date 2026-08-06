// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.model;

import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import java.util.Arrays;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;

public abstract class EdoIntegrationExchangeCounteragentUnit extends EdoIntegrationExchangeUnit implements IEdoIntegrationExchangeCounteragentUnit
{
    private static final long serialVersionUID = 1L;
    private String counteragentId;
    private String counteragentContainerId;
    private String counteragentName;
    private String counteragentInn;
    private String counteragentKpp;
    private String toBox;
    private String toBoxGuid;
    private String toDepartment;
    private String fromName;
    private String fromInn;
    private String fromKpp;
    private String fromBox;
    private String fromBoxGuid;
    private String fromDepartment;
    private String toInn;
    private String toKpp;
    private String serverId;
    private String serverName;
    private String accountId;
    private String accountName;
    private String accountMemberId;
    private String accountMemberName;
    private int currentAttempt;
    private EdoIntegrationSubsystemErrorCode sendProcessingError;
    private EdoIntegrationProcessingState sendState;
    @JsonIgnore
    private final IEdoIntegrationExchangeOperationUnit parent;
    private boolean useDictionaryOrganizationDetails;
    private List<AttrLoader<IEdoIntegrationExchangeCounteragentUnit>> uAttrs;
    private List<AttrLoader<IEdoIntegrationExchangeItem>> attrs;
    
    protected EdoIntegrationExchangeCounteragentUnit(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit parent, final SessionContext ctx) {
        this.uAttrs = (List<AttrLoader<IEdoIntegrationExchangeCounteragentUnit>>)Arrays.asList(new AttrLoader(a -> this.setServerId(a), b -> b.getServerId()), new AttrLoader(a -> this.setServerName(a), b -> b.getServerName()), new AttrLoader(a -> this.setAccountId(a), b -> b.getAccountId()), new AttrLoader(a -> this.setAccountName(a), b -> b.getAccountName()), new AttrLoader(a -> this.setAccountMemberId(a), b -> b.getAccountMemberId()), new AttrLoader(a -> this.setAccountMemberName(a), b -> b.getAccountMemberName()), new AttrLoader(a -> this.setCounteragentName(a), b -> b.getCounteragentName()), new AttrLoader(a -> this.setCounteragentInn(a), b -> b.getCounteragentInn()), new AttrLoader(a -> this.setCounteragentKpp(a), b -> b.getCounteragentKpp()), new AttrLoader(a -> this.setCounteragentContainerId(a), b -> b.getCounteragentContainerId()), new AttrLoader(a -> this.setCounteragentId(a), b -> b.getCounteragentId()), new AttrLoader(a -> this.setFromBox(a), b -> b.getFromBox()), new AttrLoader(a -> this.setFromBoxGuid(a), b -> b.getFromBoxGuid()), new AttrLoader(a -> this.setFromDepartment(a), b -> b.getFromDepartment()), new AttrLoader(a -> this.setFromInn(a), b -> b.getFromInn()), new AttrLoader(a -> this.setFromKpp(a), b -> b.getFromKpp()), new AttrLoader(a -> this.setFromName(a), b -> b.getFromName()), new AttrLoader(a -> this.setToBox(a), b -> b.getToBox()), new AttrLoader(a -> this.setToBoxGuid(a), b -> b.getToBoxGuid()), new AttrLoader(a -> this.setToDepartment(a), b -> b.getToDepartment()), new AttrLoader(a -> this.setToInn(a), b -> b.getToInn()), new AttrLoader(a -> this.setToKpp(a), b -> b.getToKpp()));
        this.attrs = (List<AttrLoader<IEdoIntegrationExchangeItem>>)Arrays.asList(new AttrLoader(a -> this.setServerId(a), b -> b.getServerId()), new AttrLoader(a -> this.setServerName(a), b -> b.getServerName()), new AttrLoader(a -> this.setAccountId(a), b -> b.getAccountId()), new AttrLoader(a -> this.setAccountName(a), b -> b.getAccountName()), new AttrLoader(a -> this.setAccountMemberId(a), b -> b.getAccountMemberId()), new AttrLoader(a -> this.setAccountMemberName(a), b -> b.getAccountMemberName()), new AttrLoader(a -> this.setCounteragentName(a), b -> b.getCounteragentName()), new AttrLoader(a -> this.setCounteragentInn(a), b -> b.getCounteragentInn()), new AttrLoader(a -> this.setCounteragentKpp(a), b -> b.getCounteragentKpp()), new AttrLoader(a -> this.setCounteragentContainerId(a), b -> b.getCounteragentContainerId()), new AttrLoader(a -> this.setCounteragentId(a), b -> b.getCounteragentId()), new AttrLoader(a -> this.setFromBox(a), b -> b.getFromBox()), new AttrLoader(a -> this.setFromBoxGuid(a), b -> b.getFromBoxGuid()), new AttrLoader(a -> this.setFromDepartment(a), b -> b.getFromDepartment()), new AttrLoader(a -> this.setFromInn(a), b -> b.getFromInn()), new AttrLoader(a -> this.setFromKpp(a), b -> b.getFromKpp()), new AttrLoader(a -> this.setToBox(a), b -> b.getToBox()), new AttrLoader(a -> this.setToBoxGuid(a), b -> b.getToBoxGuid()), new AttrLoader(a -> this.setToDepartment(a), b -> b.getToDepartment()), new AttrLoader(a -> this.setToInn(a), b -> b.getToInn()), new AttrLoader(a -> this.setToKpp(a), b -> b.getToKpp()));
        this.parent = parent;
        this.setItemType(EdoIntegrationItemType.document);
        if (parent != null) {
            this.loadParentAttrs(parent, true);
        }
    }
    
    protected EdoIntegrationExchangeCounteragentUnit(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeItem item, final SessionContext ctx) {
        this.uAttrs = (List<AttrLoader<IEdoIntegrationExchangeCounteragentUnit>>)Arrays.asList(new AttrLoader(a -> this.setServerId(a), b -> b.getServerId()), new AttrLoader(a -> this.setServerName(a), b -> b.getServerName()), new AttrLoader(a -> this.setAccountId(a), b -> b.getAccountId()), new AttrLoader(a -> this.setAccountName(a), b -> b.getAccountName()), new AttrLoader(a -> this.setAccountMemberId(a), b -> b.getAccountMemberId()), new AttrLoader(a -> this.setAccountMemberName(a), b -> b.getAccountMemberName()), new AttrLoader(a -> this.setCounteragentName(a), b -> b.getCounteragentName()), new AttrLoader(a -> this.setCounteragentInn(a), b -> b.getCounteragentInn()), new AttrLoader(a -> this.setCounteragentKpp(a), b -> b.getCounteragentKpp()), new AttrLoader(a -> this.setCounteragentContainerId(a), b -> b.getCounteragentContainerId()), new AttrLoader(a -> this.setCounteragentId(a), b -> b.getCounteragentId()), new AttrLoader(a -> this.setFromBox(a), b -> b.getFromBox()), new AttrLoader(a -> this.setFromBoxGuid(a), b -> b.getFromBoxGuid()), new AttrLoader(a -> this.setFromDepartment(a), b -> b.getFromDepartment()), new AttrLoader(a -> this.setFromInn(a), b -> b.getFromInn()), new AttrLoader(a -> this.setFromKpp(a), b -> b.getFromKpp()), new AttrLoader(a -> this.setFromName(a), b -> b.getFromName()), new AttrLoader(a -> this.setToBox(a), b -> b.getToBox()), new AttrLoader(a -> this.setToBoxGuid(a), b -> b.getToBoxGuid()), new AttrLoader(a -> this.setToDepartment(a), b -> b.getToDepartment()), new AttrLoader(a -> this.setToInn(a), b -> b.getToInn()), new AttrLoader(a -> this.setToKpp(a), b -> b.getToKpp()));
        this.attrs = (List<AttrLoader<IEdoIntegrationExchangeItem>>)Arrays.asList(new AttrLoader(a -> this.setServerId(a), b -> b.getServerId()), new AttrLoader(a -> this.setServerName(a), b -> b.getServerName()), new AttrLoader(a -> this.setAccountId(a), b -> b.getAccountId()), new AttrLoader(a -> this.setAccountName(a), b -> b.getAccountName()), new AttrLoader(a -> this.setAccountMemberId(a), b -> b.getAccountMemberId()), new AttrLoader(a -> this.setAccountMemberName(a), b -> b.getAccountMemberName()), new AttrLoader(a -> this.setCounteragentName(a), b -> b.getCounteragentName()), new AttrLoader(a -> this.setCounteragentInn(a), b -> b.getCounteragentInn()), new AttrLoader(a -> this.setCounteragentKpp(a), b -> b.getCounteragentKpp()), new AttrLoader(a -> this.setCounteragentContainerId(a), b -> b.getCounteragentContainerId()), new AttrLoader(a -> this.setCounteragentId(a), b -> b.getCounteragentId()), new AttrLoader(a -> this.setFromBox(a), b -> b.getFromBox()), new AttrLoader(a -> this.setFromBoxGuid(a), b -> b.getFromBoxGuid()), new AttrLoader(a -> this.setFromDepartment(a), b -> b.getFromDepartment()), new AttrLoader(a -> this.setFromInn(a), b -> b.getFromInn()), new AttrLoader(a -> this.setFromKpp(a), b -> b.getFromKpp()), new AttrLoader(a -> this.setToBox(a), b -> b.getToBox()), new AttrLoader(a -> this.setToBoxGuid(a), b -> b.getToBoxGuid()), new AttrLoader(a -> this.setToDepartment(a), b -> b.getToDepartment()), new AttrLoader(a -> this.setToInn(a), b -> b.getToInn()), new AttrLoader(a -> this.setToKpp(a), b -> b.getToKpp()));
        this.parent = null;
        this.setItemType(EdoIntegrationItemType.document);
        if (item != null) {
            this.loadAttrs(item, true);
        }
    }
    
    @Override
    public final String getCounteragentContainerId() {
        return this.counteragentContainerId;
    }
    
    @Override
    public void setCounteragentContainerId(final String value) {
        this.counteragentContainerId = value;
    }
    
    @Override
    public String getCounteragentName() {
        return this.counteragentName;
    }
    
    @Override
    public void setCounteragentName(final String value) {
        this.counteragentName = value;
    }
    
    @Override
    public final String getCounteragentId() {
        return this.counteragentId;
    }
    
    @Override
    public void setCounteragentId(final String value) {
        this.counteragentId = value;
    }
    
    @Override
    public void setToBox(final String value) {
        this.toBox = value;
    }
    
    @Override
    public final String getToBox() {
        return this.toBox;
    }
    
    @Override
    public void setToBoxGuid(final String value) {
        this.toBoxGuid = value;
    }
    
    @Override
    public final String getToBoxGuid() {
        return this.toBoxGuid;
    }
    
    @Override
    public void setToDepartment(final String value) {
        this.toDepartment = value;
    }
    
    @Override
    public final String getToDepartment() {
        return this.toDepartment;
    }
    
    @Override
    public void setFromName(final String value) {
        this.fromName = value;
    }
    
    @Override
    public final String getFromName() {
        return this.fromName;
    }
    
    @Override
    public void setFromInn(final String value) {
        this.fromInn = value;
    }
    
    @Override
    public final String getFromInn() {
        return this.fromInn;
    }
    
    @Override
    public void setFromKpp(final String value) {
        this.fromKpp = value;
    }
    
    @Override
    public final String getFromKpp() {
        return this.fromKpp;
    }
    
    @Override
    public final String getFromBox() {
        return this.fromBox;
    }
    
    @Override
    public void setFromBox(final String value) {
        this.fromBox = value;
    }
    
    @Override
    public final String getFromBoxGuid() {
        return this.fromBoxGuid;
    }
    
    @Override
    public void setFromBoxGuid(final String value) {
        this.fromBoxGuid = value;
    }
    
    @Override
    public final String getFromDepartment() {
        return this.fromDepartment;
    }
    
    @Override
    public void setFromDepartment(final String value) {
        this.fromDepartment = value;
    }
    
    @Override
    public void setSendPrepareError(final EdoIntegrationSubsystemErrorCode value) {
        this.sendState = EdoIntegrationProcessingState.ERROR;
        this.sendProcessingError = value;
    }
    
    @Override
    public EdoIntegrationSubsystemErrorCode getSendPrepareError() {
        return this.sendProcessingError;
    }
    
    @Override
    public final String getServerId() {
        return this.serverId;
    }
    
    @Override
    public void setServerId(final String value) {
        this.serverId = value;
    }
    
    @Override
    public final String getServerName() {
        return this.serverName;
    }
    
    @Override
    public void setServerName(final String value) {
        this.serverName = value;
    }
    
    @Override
    public final String getAccountId() {
        return this.accountId;
    }
    
    @Override
    public void setAccountId(final String value) {
        this.accountId = value;
    }
    
    @Override
    public final String getAccountName() {
        return this.accountName;
    }
    
    @Override
    public void setAccountName(final String value) {
        this.accountName = value;
    }
    
    @Override
    public final String getAccountMemberId() {
        return this.accountMemberId;
    }
    
    @Override
    public void setAccountMemberId(final String value) {
        this.accountMemberId = value;
    }
    
    @Override
    public final String getAccountMemberName() {
        return this.accountMemberName;
    }
    
    @Override
    public void setAccountMemberName(final String value) {
        this.accountMemberName = value;
    }
    
    @Override
    public final String getCounteragentInn() {
        return this.counteragentInn;
    }
    
    @Override
    public void setCounteragentInn(final String value) {
        this.counteragentInn = value;
    }
    
    @Override
    public final String getCounteragentKpp() {
        return this.counteragentKpp;
    }
    
    @Override
    public void setCounteragentKpp(final String value) {
        this.counteragentKpp = value;
    }
    
    @Override
    public final EdoIntegrationProcessingState getSendState() {
        return this.sendState;
    }
    
    @Override
    public void setSendState(final EdoIntegrationProcessingState value) {
        this.sendState = value;
    }
    
    @Override
    public final String getToInn() {
        return this.toInn;
    }
    
    @Override
    public void setToInn(final String value) {
        this.toInn = value;
    }
    
    @Override
    public final String getToKpp() {
        return this.toKpp;
    }
    
    @Override
    public void setToKpp(final String value) {
        this.toKpp = value;
    }
    
    @Override
    public void loadParentAttrs(final IEdoIntegrationExchangeUnit itemUp, final boolean preventNullOverride) {
        super.loadParentAttrs(itemUp, preventNullOverride);
        if (itemUp instanceof final IEdoIntegrationExchangeCounteragentUnit item) {
            for (final AttrLoader<IEdoIntegrationExchangeCounteragentUnit> attr : this.uAttrs) {
                final String v = attr.getGetter().apply(item);
                if (!preventNullOverride || StringUtils.isNotBlank((CharSequence)v)) {
                    attr.getSetter().accept(v);
                }
            }
        }
    }
    
    @Override
    public void loadAttrs(final IEdoIntegrationExchangeItem item, final boolean preventNullOverride) {
        super.loadAttrs(item, preventNullOverride);
        for (final AttrLoader<IEdoIntegrationExchangeItem> attr : this.attrs) {
            final String v = attr.getGetter().apply(item);
            if (!preventNullOverride || StringUtils.isNotBlank((CharSequence)v)) {
                attr.getSetter().accept(v);
            }
        }
        this.setCurrentAttempt(item.getCurrentAttempt());
        this.setSendState(EdoIntegrationProcessingState.from(item.getLocalState()));
    }
    
    @Override
    public void commitAttrs(final IEdoIntegrationExchangeItem item) {
        super.commitAttrs(item);
        item.setCounteragentContainerId(this.getCounteragentContainerId());
        item.setCounteragentId(this.getCounteragentId());
        item.setServerId(this.getServerId());
        item.setServerName(this.getServerName());
        item.setAccountId(this.getAccountId());
        item.setAccountName(this.getAccountName());
        item.setAccountMemberId(this.getAccountMemberId());
        item.setAccountMemberName(this.getAccountMemberName());
        item.setCounteragentName(this.getCounteragentName());
        item.setCounteragentInn(this.getCounteragentInn());
        item.setCounteragentKpp(this.getCounteragentKpp());
        item.setFromBox(this.getFromBox());
        item.setFromBoxGuid(this.getFromBoxGuid());
        item.setFromDepartment(this.getFromDepartment());
        item.setFromInn(this.getFromInn());
        item.setFromKpp(this.getFromKpp());
        item.setToBox(this.getToBox());
        item.setToBoxGuid(this.getToBoxGuid());
        item.setToDepartment(this.getToDepartment());
        item.setToInn(this.getToInn());
        item.setToKpp(this.getToKpp());
        item.setCurrentAttempt(this.getCurrentAttempt());
        item.setLocalState((this.getSendState() == null) ? null : this.getSendState().name());
    }
    
    @Override
    public void queue() {
        if (this.sendState == null || this.sendState == EdoIntegrationProcessingState.NONE) {
            this.sendState = EdoIntegrationProcessingState.QUEUED;
        }
    }
    
    @JsonIgnore
    @Override
    public IEdoIntegrationExchangeUnit getParent() {
        return this.parent;
    }
    
    @Override
    public void setUseDictionaryOrganizationDetails(final boolean value) {
        this.useDictionaryOrganizationDetails = value;
    }
    
    @Override
    public final boolean useDictionaryOrganizationDetails() {
        return this.useDictionaryOrganizationDetails;
    }
    
    @Override
    public int getCurrentAttempt() {
        return this.currentAttempt;
    }
    
    @Override
    public void setCurrentAttempt(final int value) {
        this.currentAttempt = value;
    }
}
