// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.service.integration.configuration.ITypeDescriptionElementConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigProperties;

public class CstmConfigProperties implements ICstmConfigProperties
{
    private static final long serialVersionUID = 2395334735541425666L;
    private ITypeDescriptionElementConfiguration type;
    
    @Override
    public void setType(final ITypeDescriptionElementConfiguration value) {
        this.type = value;
    }
    
    @Override
    public ITypeDescriptionElementConfiguration getType() {
        return this.type;
    }
}
