// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public class ConfigOperationWrapper
{
    private final DMSShardsClientSession session;
    private final ConfigElement config;
    private final SessionContext ctx;
    
    public ConfigOperationWrapper(final DMSShardsClientSession session, final ConfigElement config, final SessionContext ctx) {
        this.session = session;
        this.config = config;
        this.ctx = ctx;
    }
    
    public DMSShardsClientSession getSession() {
        return this.session;
    }
    
    public ConfigElement getConfig() {
        return this.config;
    }
    
    public SessionContext getContext() {
        return this.ctx;
    }
}
