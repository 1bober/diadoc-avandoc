// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import Diadoc.Api.exceptions.DiadocSdkException;

@FunctionalInterface
public interface DiadocEdoApiSdkVoidCall<S>
{
    void call(final S p0) throws DiadocSdkException;
}
