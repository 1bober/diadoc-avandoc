// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.model;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationLogRecord;
import java.util.function.Consumer;

public interface IEdoIntegrationLogRecordDataProvider
{
    Consumer<IEdoIntegrationLogRecord> getFiller();
}
