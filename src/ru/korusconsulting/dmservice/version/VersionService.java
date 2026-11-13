//
// Decompiled by Procyon v0.6.0
//

package ru.korusconsulting.dmservice.version;

import ru.korusconsulting.service.version.IVersionService;

/**
 * Базовая реализация сервиса получения версии.
 * По-умолчанию возвращает версию {@link IVersionService#DEFAULT_VERSION} ("4.3").
 */
public class VersionService implements IVersionService
{
    private final String version;

    public VersionService() {
        this(DEFAULT_VERSION);
    }

    public VersionService(final String version) {
        this.version = (version == null || version.trim().isEmpty()) ? DEFAULT_VERSION : version.trim();
    }

    @Override
    public String getVersion() {
        return this.version;
    }
}
