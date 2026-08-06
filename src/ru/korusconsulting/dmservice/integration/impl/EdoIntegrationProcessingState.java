// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl;

public enum EdoIntegrationProcessingState
{
    NONE, 
    QUEUED, 
    INWORK, 
    RETRY, 
    ERROR, 
    COMPLETED, 
    CANCELLED, 
    DELETED;
    
    public static EdoIntegrationProcessingState from(final String state) {
        for (final EdoIntegrationProcessingState v : values()) {
            if (v.name().equalsIgnoreCase(state)) {
                return v;
            }
        }
        return EdoIntegrationProcessingState.NONE;
    }
    
    public static EdoIntegrationProcessingState from(final String state, final EdoIntegrationProcessingState def) {
        for (final EdoIntegrationProcessingState v : values()) {
            if (v.name().equalsIgnoreCase(state)) {
                return v;
            }
        }
        return def;
    }
}
