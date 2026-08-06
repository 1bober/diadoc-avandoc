// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoCounteragent;
import java.util.Map;

public class DiadocEdoBoxDesc
{
    private final String sputnikOrgId;
    private final String sputnikOrgName;
    private final String diadocOrgId;
    private final String boxId;
    private final String login;
    private final String password;
    private Map<String, DiadocEdoCounteragent> counteragentList;
    
    public DiadocEdoBoxDesc(final String sputnikOrgId, final String sputnikOrgName, final String diadocOrgId, final String login, final String password, final String boxId) {
        this.sputnikOrgName = sputnikOrgName;
        this.sputnikOrgId = sputnikOrgId;
        this.diadocOrgId = diadocOrgId;
        this.login = login;
        this.password = password;
        this.boxId = boxId;
    }
    
    public DiadocEdoBoxDesc(final String sputnikOrgId, final String sputnikOrgName, final String diadocOrgId, final String login, final String password, final String boxId, final Map<String, DiadocEdoCounteragent> counteragentList) {
        this(sputnikOrgId, sputnikOrgName, diadocOrgId, login, password, boxId);
        this.counteragentList = counteragentList;
    }
    
    public String getBoxId() {
        return this.boxId;
    }
    
    public String getSpitnikOrgId() {
        return this.sputnikOrgId;
    }
    
    public String getSpitnikOrgName() {
        return this.sputnikOrgName;
    }
    
    public String getDiadocOrgId() {
        return this.diadocOrgId;
    }
    
    public DiadocEdoCounteragent getCounteragentByBoxId(final String boxId) {
        return (this.counteragentList != null) ? this.counteragentList.get(boxId) : null;
    }
    
    public void setCounteragentList(final Map<String, DiadocEdoCounteragent> counteragentList) {
        this.counteragentList = counteragentList;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getPassword() {
        return this.password;
    }
}
