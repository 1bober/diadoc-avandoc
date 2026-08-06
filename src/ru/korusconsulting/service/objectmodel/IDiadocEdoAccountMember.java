// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.Date;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoAccountMember extends IObjectModel
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    String getBox();
    
    void setBox(final String p0);
    
    String getInn();
    
    void setInn(final String p0);
    
    String getKpp();
    
    void setKpp(final String p0);
    
    String getFilial();
    
    void setFilial(final String p0);
    
    String getFilialName();
    
    void setFilialName(final String p0);
    
    String getAccount();
    
    void setAccount(final String p0);
    
    String getAccountName();
    
    void setAccountName(final String p0);
    
    boolean isDontCheckCounteragentSignCertificate(final DMSShardsClientSession p0, final SessionContext p1);
    
    boolean isDontCheckCounteragentSignCertificate(final IDiadocEdoAccount p0);
    
    int getDontCheckCounteragentSignCertificate();
    
    void setDontCheckCounteragentSignCertificate(final int p0);
    
    boolean isRemoteSignatureMode();
    
    void setRemoteSignatureMode(final boolean p0);
    
    String getDontCheckCounteragentSignCertificateName();
    
    void setDontCheckCounteragentSignCertificateName(final String p0);
    
    boolean isAllowSendToRejected();
    
    void setAllowSendToRejected(final boolean p0);
    
    void setLogPropogateMode(final int p0);
    
    int getLogPropogateMode();
    
    String getNonInvitedSendMode();
    
    void setNonInvitedSendMode(final String p0);
    
    String getNonInvitedSendModeName();
    
    void setNonInvitedSendModeName(final String p0);
    
    void setIncDocsCheckOffset(final int p0);
    
    int getIncDocsCheckOffset();
    
    List<String> getOutboundCategories();
    
    void setOutboundCategories(final List<String> p0);
    
    List<String> getOutboundCategoryNames();
    
    void setOutboundCategoryNames(final List<String> p0);
    
    List<String> getOutboundCategoryDepartments();
    
    void setOutboundCategoryDepartments(final List<String> p0);
    
    List<String> getInboundCategories();
    
    void setInboundCategories(final List<String> p0);
    
    List<String> getInboundCategoryNames();
    
    void setInboundCategoryNames(final List<String> p0);
    
    List<String> getInboundCategoryDepartments();
    
    void setInboundCategoryDepartments(final List<String> p0);
    
    String getOutboundDepartment();
    
    void setOutboundDepartment(final String p0);
    
    String getInboundDepartment();
    
    void setInboundDepartment(final String p0);
    
    String getDepartment();
    
    void setDepartment(final String p0);
    
    boolean isDontCheckIncoming();
    
    void setDontCheckIncoming(final boolean p0);
    
    Date getIncomingDocumentsLastCheckDate();
    
    void setIncomingDocumentsLastCheckDate(final Date p0);
    
    Date getEventsLastCheckDate();
    
    void setEventsLastCheckDate(final Date p0);
    
    void setIncomingDocumentsDateCheckFrom(final Date p0);
    
    Date getIncomingDocumentsDateCheckFrom();
    
    void setEventsDateCheckFrom(final Date p0);
    
    Date getEventsDateCheckFrom();
    
    boolean isAllowSendToTestCounteragents();
    
    void setAllowSendToTestCounteragents(final boolean p0);
}
