// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationProcessor;

public abstract class DiadocEdoProcessor extends EdoIntegrationProcessor<IDiadocEdoOperationContext> implements IDiadocEdoProcessor
{
    public final IDiadocEdoIntegrationServiceInternal getService() {
        return (IDiadocEdoIntegrationServiceInternal)super.getService();
    }
}
