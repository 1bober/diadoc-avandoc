// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.exceptions;

public interface IProcessableException
{
    int getCode();
    
    String getReason();
    
    String getTraceId();
}
