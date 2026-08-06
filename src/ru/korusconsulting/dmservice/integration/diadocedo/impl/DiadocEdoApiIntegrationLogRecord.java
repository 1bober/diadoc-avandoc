// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationLogMethod;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;

public class DiadocEdoApiIntegrationLogRecord
{
    private final IIntegrationLogService.TYPE level;
    private final DiadocEdoIntegrationLogMethod operation;
    private final String message;
    private final String documentId;
    private final String filialId;
    private final String filialName;
    
    public DiadocEdoApiIntegrationLogRecord(final IIntegrationLogService.TYPE level, final DiadocEdoIntegrationLogMethod operation, final String message, final String document, final String filialId, final String filialName) {
        this.level = level;
        this.operation = operation;
        this.message = message;
        this.documentId = document;
        this.filialId = filialId;
        this.filialName = filialName;
    }
    
    public IIntegrationLogService.TYPE getLevel() {
        return this.level;
    }
    
    public DiadocEdoIntegrationLogMethod getOperation() {
        return this.operation;
    }
    
    public String getFilialId() {
        return this.filialId;
    }
    
    public String getFilialName() {
        return this.filialName;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getDocumentId() {
        return this.documentId;
    }
}
