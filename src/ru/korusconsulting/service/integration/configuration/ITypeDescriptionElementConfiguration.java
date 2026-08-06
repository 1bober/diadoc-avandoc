// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.service.objectmodel.cstm.ICstmType;

public interface ITypeDescriptionElementConfiguration extends IDictionaryElementConfiguration
{
    boolean isCstmLoaded();
    
    void setCstmLoaded(final boolean p0);
    
    ICstmType getCstmType();
    
    void setCstmType(final ICstmType p0);
}
