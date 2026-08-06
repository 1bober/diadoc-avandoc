// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import java.util.Iterator;
import ru.korusconsulting.service.commons.ICollection;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.status.DiadocEdoDocumentRemoteStatus;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationDocumentRemoteStatus;
import java.util.List;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationServiceConfiguration;

public class DiadocEdoServiceConfiguration extends EdoIntegrationServiceConfiguration
{
    private static final long serialVersionUID = -5109470030450201539L;
    private final List<IEdoIntegrationDocumentRemoteStatus> remoteStatuses;
    public static final String CONFIG_ID = "diadocedo";
    
    public DiadocEdoServiceConfiguration(final DMSShardsClientSession session, final Generator generator, final SessionContext ctx) {
        super((ICstmConfigAdapter)null);
        this.remoteStatuses = this.loadRemoteStatuses(session, generator, ctx);
    }
    
    protected List<IEdoIntegrationDocumentRemoteStatus> loadRemoteStatuses(final DMSShardsClientSession session, final Generator generator, final SessionContext ctx) {
        final List<IEdoIntegrationDocumentRemoteStatus> result = new ArrayList<IEdoIntegrationDocumentRemoteStatus>();
        final List<String> remoteStatusesComponentIds = new ArrayList<String>();
        try (final ICollection col = ServiceReg.getQueryService().executeReadQuery(session, "select r_object_id from nt_cstm_component where ssa_component_type = 'cstm_integration_diadoc_edo_document_remote_status'", ctx)) {
            while (col.next()) {
                remoteStatusesComponentIds.add(col.getString("r_object_id"));
            }
        }
        for (final String remoteStatusesComponentId : remoteStatusesComponentIds) {
            final ICstmComponent cmp = (ICstmComponent)ServiceReg.getObjectModelService().getObjectModel(session, remoteStatusesComponentId, ctx);
            if (cmp != null) {
                final IEdoIntegrationDocumentRemoteStatus rS = (IEdoIntegrationDocumentRemoteStatus)new DiadocEdoDocumentRemoteStatus(null);
                rS.unmarshal(session, cmp, generator, ctx);
                result.add(rS);
            }
        }
        return result;
    }
    
    public IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IEdoIntegrationFlowConfiguration configuration = DiadocEdoIntegrationFlowFactory.createIntegrationFlowConfiguration(session, (ICstmConfigAdapter)this, component, generator, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for component %s", component.getComponentType()));
        }
        return configuration;
    }
    
    public IEdoIntegrationFlowConfiguration createIntegrationFlowConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IEdoIntegrationFlowConfiguration configuration = DiadocEdoIntegrationFlowFactory.createIntegrationFlowConfiguration(session, (ICstmConfigAdapter)this, element, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for element with tag %s", element.getTagName()));
        }
        return configuration;
    }
    
    public IEdoIntegrationOperationConfiguration getDocumentConfig() {
        return null;
    }
    
    public IEdoIntegrationDocumentOperationConfiguration getDocumentOperationConfig() {
        return null;
    }
    
    public List<IEdoIntegrationDocumentRemoteStatus> getRemoteStatuses() {
        return this.remoteStatuses;
    }
    
    public IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExchangeCategoryConfiguration(session, (ICstmConfigAdapter)this, component, generator, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for component %s", component.getComponentType()));
        }
        return configuration;
    }
    
    public IDictionaryElementConfiguration createExchangeCategoryConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExchangeCategoryConfiguration(session, (ICstmConfigAdapter)this, element, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for element with tag %s", element.getTagName()));
        }
        return configuration;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession session, final ICstmComponent component, final String xmlTagId, final Generator generator, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExchangeErrorCategoryConfiguration(session, (ICstmConfigAdapter)this, component, generator, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for component %s", component.getComponentType()));
        }
        return configuration;
    }
    
    public IDictionaryElementConfiguration createExchangeErrorCategoryConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        final IDictionaryElementConfiguration configuration = DiadocEdoIntegrationFlowFactory.createExchangeErrorCategoryConfiguration(session, (ICstmConfigAdapter)this, element, ctx);
        if (configuration == null) {
            this.getLogger().warn(() -> String.format("Failed to find wrapper for element with tag %s", element.getTagName()));
        }
        return configuration;
    }
}
