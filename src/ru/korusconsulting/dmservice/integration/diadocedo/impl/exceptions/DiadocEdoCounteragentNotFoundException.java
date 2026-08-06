// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.service.objectmodel.IObjectModel;

class DiadocEdoCounteragentNotFoundException extends DiadocEdoIntegrationException
{
    private static final long serialVersionUID = -6724996862009814755L;
    private final String inn;
    private final String kpp;
    private final IObjectModel countergantContainer;
    
    public DiadocEdoCounteragentNotFoundException(final IObjectModel countergantContainer, final String inn, final String kpp) {
        super(String.format("Failed to find counteragent with inn %s and kpp %s in Diadoc.EDO", inn, kpp));
        this.countergantContainer = countergantContainer;
        this.inn = inn;
        this.kpp = kpp;
    }
    
    public String getInn() {
        return this.inn;
    }
    
    public String getKpp() {
        return this.kpp;
    }
    
    public IObjectModel getContainer() {
        return this.countergantContainer;
    }
}
