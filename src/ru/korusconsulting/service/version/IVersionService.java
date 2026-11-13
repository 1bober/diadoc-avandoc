//
// Decompiled by Procyon v0.6.0
//

package ru.korusconsulting.service.version;

/**
 * Сервис получения версии.
 */
public interface IVersionService
{
    /** Версия, возвращаемая по умолчанию. */
    String DEFAULT_VERSION = "4.3";

    /**
     * Возвращает текущую версию.
     * Если версия не настроена, возвращается версия по умолчанию ("4.3").
     */
    String getVersion();
}
