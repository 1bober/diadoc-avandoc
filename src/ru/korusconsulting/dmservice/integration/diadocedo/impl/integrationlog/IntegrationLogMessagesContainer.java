// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog;

record IntegrationLogMessagesContainer(IntegrationLogRecordMessage success, IntegrationLogRecordMessage failure, IntegrationLogRecordMessage witherror, IntegrationLogRecordMessage skipped) {}
