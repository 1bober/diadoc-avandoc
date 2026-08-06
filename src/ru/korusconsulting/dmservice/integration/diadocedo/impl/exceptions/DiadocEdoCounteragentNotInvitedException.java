// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.service.objectmodel.IObjectModel;

public class DiadocEdoCounteragentNotInvitedException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = -6724996862009814755L;
    private final String inn;
    private final String kpp;
    private final String boxId;
    private final IObjectModel counteragentContainer;
    
    public DiadocEdoCounteragentNotInvitedException(final IObjectModel counteragentContainer, final String inn, final String kpp, final String boxId) {
        super(String.format("Counteragent with inn %s and kpp %s in Diadoc.EDO found with box %s, but not invited", inn, kpp, boxId));
        this.counteragentContainer = counteragentContainer;
        this.inn = inn;
        this.kpp = kpp;
        this.boxId = boxId;
    }
    
    public String getInn() {
        return this.inn;
    }
    
    public String getKpp() {
        return this.kpp;
    }
    
    public IObjectModel getContainer() {
        return this.counteragentContainer;
    }
    
    public String getBoxId() {
        return this.boxId;
    }
}
