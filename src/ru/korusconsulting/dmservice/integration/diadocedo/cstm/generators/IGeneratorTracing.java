// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.generators;

import ru.korusconsulting.dmservice.integration.Utils;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmConfigAdapter;

public interface IGeneratorTracing
{
    default void trace(final CstmConfigAdapter conf, final RootConfigElement configurationConfig) {
        if (StringUtils.isNotBlank(System.getProperty("diadocedo.cstm.generator.tracing.dir"))) {
            Utils.traceConfig(configurationConfig, Objects.toString(conf.getComponentUid(), conf.getConfigComponentId()), System.getProperty("diadocedo.cstm.generator.tracing.dir"));
        }
    }
}
