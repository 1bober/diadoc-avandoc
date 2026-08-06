// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl;

import org.apache.commons.lang.StringUtils;
import java.util.Properties;
import java.util.Objects;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.function.UnaryOperator;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IIntegrationLogSettings;

public class IntegrationLogSettings implements IIntegrationLogSettings
{
    private FilenameMode filenameMode;
    private String filenameTemplate;
    public static final String INTEGRATION_LOG_FILENAME_TEMPLATE_MODE = "log_file_container_name_mode";
    public static final String INTEGRATION_LOG_FILENAME_TEMPLATE = "log_file_container_name_template";
    
    public static IIntegrationLogSettings load(final DMSShardsClientSession session, final String groupId, final String useFor, final UnaryOperator<String> getter, final SessionContext ctx) {
        final Properties props = ServiceReg.getPropertiesGroupService().getProperties(session, useFor, groupId, ctx);
        if (props.containsKey("log_file_container_name_mode")) {
            final Properties obj = props;
            Objects.requireNonNull(obj);
            final IIntegrationLogSettings result = load((UnaryOperator<String>)obj::getProperty);
            if (result != null) {
                return result;
            }
        }
        return load(getter);
    }
    
    private static IIntegrationLogSettings load(final UnaryOperator<String> getter) {
        if (getter == null) {
            return null;
        }
        final String filenameTemplateMode = getter.apply("log_file_container_name_mode");
        if (StringUtils.isNotBlank(filenameTemplateMode)) {
            final IntegrationLogSettings settings = new IntegrationLogSettings();
            settings.setFilenameMode(filenameTemplateMode);
            final String filenameTemplate = getter.apply("log_file_container_name_template");
            if (StringUtils.isNotBlank(filenameTemplate)) {
                settings.setFilenameTemplate(filenameTemplate);
            }
            return settings;
        }
        return null;
    }
    
    public void setFilenameMode(final String value) {
        this.filenameMode = FilenameMode.parse(value);
    }
    
    public FilenameMode getFilenameMode() {
        return this.filenameMode;
    }
    
    @Override
    public String getFilenameTemplate() {
        switch (this.getFilenameMode()) {
            case template: {
                return this.filenameTemplate;
            }
            case date: {
                return IntegrationLogSettings.FILENAME_DATE_TEMPLATE;
            }
            case hour: {
                return IntegrationLogSettings.FILENAME_HOUR_TEMPLATE;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setFilenameTemplate(final String filenameTemplate) {
        this.filenameTemplate = filenameTemplate;
    }
    
    public static String getDefaultFilenameTemplate() {
        return IntegrationLogSettings.FILENAME_HOUR_TEMPLATE;
    }
    
    enum FilenameMode
    {
        none, 
        date, 
        hour, 
        template;
        
        public static FilenameMode parse(final String value) {
            final String val = StringUtils.trimToNull(value);
            if (val != null) {
                for (final FilenameMode v : values()) {
                    if (val.equalsIgnoreCase(v.name())) {
                        return v;
                    }
                }
            }
            return FilenameMode.hour;
        }
    }
}
