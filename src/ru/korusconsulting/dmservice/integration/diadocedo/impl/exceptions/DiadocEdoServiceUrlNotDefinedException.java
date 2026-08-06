// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoServiceUrlNotDefinedException extends DiadocEdoConfigurationProblemException
{
    private static final long serialVersionUID = 4701449802786339027L;
    
    public DiadocEdoServiceUrlNotDefinedException() {
        super("Diadoc Endpoint url key must be defined in config. Contact administrators");
    }
}
