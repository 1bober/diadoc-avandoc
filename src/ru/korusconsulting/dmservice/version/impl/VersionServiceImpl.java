//
// Decompiled by Procyon v0.6.0
//

package ru.korusconsulting.dmservice.version.impl;

import ru.korusconsulting.dmservice.version.SERVICE_VERSION;
import ru.korusconsulting.dmservice.version.ServiceVersion;
import ru.korusconsulting.dmservice.version.VersionService;

/**
 * Регистрируемый сервис получения версии.
 * По-умолчанию возвращает версию "4.3".
 */
@ServiceVersion(name = SERVICE_VERSION.VERSION_SERVICE)
public class VersionServiceImpl extends VersionService
{
    public static final String VERSION_PROPERTY = "ru.korusconsulting.version";

    public VersionServiceImpl() {
        super(resolveConfiguredVersion());
    }

    private static String resolveConfiguredVersion() {
        final String configured = System.getProperty(VERSION_PROPERTY);
        return (configured == null || configured.trim().isEmpty()) ? DEFAULT_VERSION : configured.trim();
    }
}
