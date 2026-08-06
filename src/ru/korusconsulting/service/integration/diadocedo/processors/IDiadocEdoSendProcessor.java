// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import java.util.List;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;

public interface IDiadocEdoSendProcessor extends IDiadocEdoProcessor, IEdoIntegrationSendProcessor<IDiadocEdoOperationContext, IDiadocEdoApi>
{
    List<AttrDesc> getFieldNames();
    
    record AttrDesc(String name, String type, String format, boolean required) {}
}
