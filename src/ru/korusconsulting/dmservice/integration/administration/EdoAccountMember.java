// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.administration;

import ru.korusconsulting.service.integration.administration.IEdoAccountMember;

public class EdoAccountMember implements IEdoAccountMember
{
    private final String accountId;
    private final String accountName;
    private String memberId;
    private final String inn;
    private final String kpp;
    private final String boxId;
    private final String deparment;
    private final String edoAddress;
    
    public EdoAccountMember(final String accountId, final String accountName, final String memberId, final String inn, final String kpp, final String boxId, final String deparment) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.memberId = memberId;
        this.inn = inn;
        this.kpp = kpp;
        this.boxId = boxId;
        this.deparment = deparment;
        this.edoAddress = String.join(":", this.getBoxId(), this.getDeparment());
    }
    
    public EdoAccountMember(final String accountId, final String accountName, final String inn, final String kpp, final String boxId, final String deparment) {
        this(accountId, accountName, null, inn, kpp, boxId, deparment);
    }
    
    public String getMemberId() {
        return this.memberId;
    }
    
    public void setMemberId(final String memberId) {
        this.memberId = memberId;
    }
    
    @Override
    public String getAccountId() {
        return this.accountId;
    }
    
    @Override
    public String getAccountName() {
        return this.accountName;
    }
    
    @Override
    public String getInn() {
        return this.inn;
    }
    
    @Override
    public String getKpp() {
        return this.kpp;
    }
    
    @Override
    public String getBoxId() {
        return this.boxId;
    }
    
    @Override
    public String getDeparment() {
        return this.deparment;
    }
    
    @Override
    public String getComplexEdoAddress() {
        return this.edoAddress;
    }
}
