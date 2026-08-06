// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import java.util.Set;
import ru.korusconsulting.dmservice.objectmodel.ObjectModelService;
import java.util.Collections;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoIncomingDocumentRequest;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoSurveyState;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoExchangeError;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoIntegrationLogRecord;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoCounteragentInfo;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoForeignStateMapping;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoAccountMember;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoAccount;
import ru.korusconsulting.dmservice.objectmodel.DiadocEdoServer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoIntegrationService;
import java.util.HashSet;

public class ServiceAndTypeInitializer
{
    public static void init() {
        try {
            final Set<Class<?>> services = new HashSet<Class<?>>();
            services.add(DiadocEdoIntegrationService.class);
            DiadocEdoServiceReg.initServices(services);
            final Set<Class<?>> types = new HashSet<Class<?>>();
            types.add(DiadocEdoServer.class);
            types.add(DiadocEdoAccount.class);
            types.add(DiadocEdoAccountMember.class);
            types.add(DiadocEdoForeignStateMapping.class);
            types.add(DiadocEdoCounteragentInfo.class);
            types.add(DiadocEdoIntegrationLogRecord.class);
            types.add(DiadocEdoExchangeError.class);
            types.add(DiadocEdoSurveyState.class);
            types.add(DiadocEdoIncomingDocumentRequest.class);
            ObjectModelService.initModelsAspects((Set)types, (Set)Collections.emptySet());
        }
        catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
