// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import ru.wstack.net.client.contentserver.IObject;

public interface IObjectModelAttributeProcessor
{
    void populateAttribute(final IObjectModel p0, final IObject p1);
    
    void commitAttribute(final IObjectModel p0, final IObject p1);
    
    String getAttributeName();
    
    boolean isAutoCalculated();
}
