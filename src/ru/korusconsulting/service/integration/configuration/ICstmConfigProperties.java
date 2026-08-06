// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import java.io.Serializable;

public interface ICstmConfigProperties extends Serializable
{
    void setType(final ITypeDescriptionElementConfiguration p0);
    
    ITypeDescriptionElementConfiguration getType();
}
