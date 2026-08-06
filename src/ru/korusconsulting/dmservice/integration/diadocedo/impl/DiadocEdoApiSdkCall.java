// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import Diadoc.Api.exceptions.DiadocSdkException;

@FunctionalInterface
public interface DiadocEdoApiSdkCall<T, S, R, V>
{
    T call(final S p0, final R p1, final V p2) throws DiadocSdkException;
}
