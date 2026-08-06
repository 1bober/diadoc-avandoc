// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public interface IDiadocEdoCounteragentProcessor extends IDiadocEdoProcessor
{
    IDiadocEdoExternalCounteragentInfo getCounteragentBoxGuid(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final IDocument p3, final String p4, final String p5, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode p6, final boolean p7, final SessionContext p8) throws DiadocEdoIntegrationException;
    
    public enum CounteragentKppSearch
    {
        org, 
        department, 
        any;
    }
}
