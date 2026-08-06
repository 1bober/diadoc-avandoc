// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;

@FunctionalInterface
interface DiadocEdoCounteragentSendAction<T>
{
    T apply(final IDiadocEdoExchangeCounteragentUnit p0) throws DiadocEdoIntegrationException;
}
