// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Properties;
import ru.korusconsulting.service.integration.LogPropogateMode;
import ru.korusconsulting.service.integration.IEdoConnectionConfig;

public abstract class EdoConnectionConfig implements IEdoConnectionConfig
{
    private final String serverId;
    private final String serverName;
    private final String filialName;
    private final String filialId;
    private final LogPropogateMode propogateLogs;
    private final Properties properties;
    
    public EdoConnectionConfig(final DMSShardsClientSession session, final String serverId, final String serverName, final String filialId, final String filialName, final LogPropogateMode propogateLogs, final SessionContext ctx) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.filialId = filialId;
        this.filialName = filialName;
        this.propogateLogs = propogateLogs;
        this.properties = this.loadProperties(session, ctx);
    }
    
    @Override
    public final String getServerId() {
        return this.serverId;
    }
    
    @Override
    public final String getServerName() {
        return this.serverName;
    }
    
    @Override
    public final String getFilialName() {
        return this.filialName;
    }
    
    @Override
    public final String getFilialId() {
        return this.filialId;
    }
    
    @Override
    public LogPropogateMode getPropogateLogMode() {
        return this.propogateLogs;
    }
    
    @Override
    public Properties getProperties() {
        return this.properties;
    }
    
    protected Properties loadProperties(final DMSShardsClientSession session, final SessionContext ctx) {
        return new Properties();
    }
}
