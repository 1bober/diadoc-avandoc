// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

public class DiadocEdoApiKeyNotDefinedException extends DiadocEdoConfigurationProblemException
{
    private static final long serialVersionUID = -5286052580221637194L;
    
    public DiadocEdoApiKeyNotDefinedException() {
        super("Diadoc edo api key must be defined. Contact administrators");
    }
}
