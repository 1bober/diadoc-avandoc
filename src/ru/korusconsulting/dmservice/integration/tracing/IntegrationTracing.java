// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.tracing;

public class IntegrationTracing
{
    public static String INTEGRATION_ITEMS_DEBUG_FLAG;
    public static String INTEGRATION_CONTEXT_DEBUG_FLAG;
    public static String INTEGRATION_CSTM_DEBUG_FLAG;
    
    private IntegrationTracing() {
    }
    
    static {
        IntegrationTracing.INTEGRATION_ITEMS_DEBUG_FLAG = "tracing.integrationitem.debug";
        IntegrationTracing.INTEGRATION_CONTEXT_DEBUG_FLAG = "tracing.integrationcontext.debug";
        IntegrationTracing.INTEGRATION_CSTM_DEBUG_FLAG = "integration.cstm.debug";
    }
}
