// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.config;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigResolverAnno;
import ru.korusconsulting.dmservice.config.alt.AConfigResolver;

@ConfigResolverAnno(contextAttr = "diadocedoartifact", order = 2000)
public class DiadocEdoArtifactNameConfigResolver extends AConfigResolver
{
    public static final String CONTEXT_KEY = "__diadocedoartifact";
    
    public String resolve(final SessionContext ctx) {
        return ctx.getString("__diadocedoartifact");
    }
}
