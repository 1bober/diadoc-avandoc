// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.io.Serializable;

public interface IEdoIntegrationAdditionalInfo extends Serializable
{
    boolean isDirty();
    
    void setDirty(final boolean p0);
}
