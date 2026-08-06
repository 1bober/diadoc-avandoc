// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import java.util.Properties;

public interface IEdoConnectionConfig
{
    String getServerId();
    
    String getServerName();
    
    String getFilialName();
    
    String getFilialId();
    
    Properties getProperties();
    
    LogPropogateMode getPropogateLogMode();
    
    boolean isDontCheckIncoming();
}
