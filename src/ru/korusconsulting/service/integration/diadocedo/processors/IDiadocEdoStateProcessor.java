// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentCumulativeRemoteStatusProcessingOperationConfiguration;
import Diadoc.Api.exceptions.DiadocSdkException;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageProcessingContext;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentCounteragentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.HashMap;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageEmptyProcessingContext;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.diadocedo.configuration.impl.operations.DiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.operations.status.StateConfigCoverage;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import java.util.function.Function;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationServiceConfiguration;
import org.apache.logging.log4j.Logger;

public interface IDiadocEdoStateProcessor extends IDiadocEdoProcessor
{
    public static final String CHECK_STATUS_OPERATION_CONFIG_SECTION_NAME = "diadoc_edo_remote_status_processing";
    public static final String CHECK_COUNTERAGENT_STATUS_OPERATION_CONFIG_SECTION_NAME = "diadocedo_check_counteragent_remote_status_processing";
    public static final String CHECK_CUMULATIVE_STATUS_OPERATION_CONFIG_SECTION_NAME = "diadocedo_check_cumulative_remote_status_processing";
    
    Logger getLogger();
    
    IEdoIntegrationServiceConfiguration getServiceConfig();
    
    IEdoIntegrationSendProcessor.ProcessingResult processResponseArchive(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final IEdoIntegrationPackageProcessingContext p3, final IEdoIntegrationInboundDocumentProcessingConfiguration p4, final Map<String, Object> p5, final IEdoIntegrationInboundDataExchangeItem p6, final IDocument p7, final Function<String, IContentContainer> p8, final SessionContext p9) throws DiadocEdoIntegrationException;
    
    IEdoIntegrationSendProcessor.ProcessingResult processResponseForDocument(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationPackageProcessingContext p2, final IEdoIntegrationInboundDocumentProcessingConfiguration p3, final Map<String, Object> p4, final IEdoIntegrationInboundDataExchangeItem p5, final IDocument p6, final IObjectModel p7, final SessionContext p8) throws DiadocEdoIntegrationException;
    
    IEdoIntegrationPackageProcessingContext preprocessRequest(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IEdoIntegrationPackageProcessingContext p2, final IEdoIntegrationInboundDocumentProcessingConfiguration p3, final Map<String, Object> p4, final IEdoIntegrationInboundDataExchangeItem p5, final Map<String, Object> p6, final IDocument p7, final SessionContext p8);
    
    default int processContentState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDiadocEdoIntegrationServiceInternal service, final IEdoIntegrationExchangeItem exchangeItem, final Map<String, Object> packageData, final IDocument document, final IObjectModel counteragent, final String systemCode, final Map<String, Object> processingArgs, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final SessionContext counteragentContext = ctx.clone();
        final String coverage = StateConfigCoverage.any.name();
        counteragentContext.set("diadoc_edo_state_coverage", (Object)coverage);
        counteragentContext.set("__diadocedoartifact", (Object)systemCode);
        final IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration operationConfig = service.getOperationConfiguration(session, operationContext, "diadoc_edo_remote_status_processing", () -> this.getServiceConfig().getStatusFlowConfiguration(), DiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration::new, testConfig -> StringUtils.equals(coverage, testConfig.getCoverage()) && testConfig.isApplicableForStatus(systemCode), counteragentContext);
        if (operationConfig == null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("No configuration for state %s. Mark as proceed (code 100).", systemCode));
            exchangeItem.setProcessingContext((IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageEmptyProcessingContext());
            service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.skip, counteragentContext);
            return 100;
        }
        IEdoIntegrationPackageProcessingContext processingContext = exchangeItem.getProcessingContext();
        final Map<String, Object> args = new HashMap<String, Object>();
        if (processingArgs != null) {
            args.putAll(processingArgs);
        }
        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)operationConfig, (Map)args, ctx);
        if (StringUtils.isBlank(exchangeItem.getRemoteStateProcessingStep()) || IEdoIntegrationInboundDataExchangeItem.ProcessingStep.preloaded.after(exchangeItem.getRemoteStateProcessingStep()) || processingContext == null) {
            if (packageData == null) {
                return -3;
            }
            processingContext = this.preprocessRequest(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)operationConfig, args, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, packageData, document, counteragentContext);
            if (processingContext == null) {
                exchangeItem.setProcessingContext(processingContext);
                exchangeItem.setStateCategory("simple");
                service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.queued, counteragentContext);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbound package preprocess returns false. Operation delayed.");
                return 0;
            }
            exchangeItem.setProcessingContext(processingContext);
            service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.document_created, counteragentContext);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Inbound package preprocess succeed or not needed.");
        }
        if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.archive_downloaded.after(exchangeItem.getRemoteStateProcessingStep()) || IEdoIntegrationInboundDataExchangeItem.ProcessingStep.document_created.after(exchangeItem.getRemoteStateProcessingStep())) {
            final Map<String, IContentContainer> contentContainerCache = new HashMap<String, IContentContainer>();
            if (this.processResponseArchive(session, operationContext, api, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)operationConfig, args, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, document, contentGroup -> contentContainerCache.computeIfAbsent(contentGroup, a -> (IContentContainer)ServiceReg.getContentService().getContentContainer(session, document.getRootId(), document.getObjectId(), a, ctx)), counteragentContext) != IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Process archive content returns false. Operation delayed.");
                return 1;
            }
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Process archive content succeed or not needed.");
            service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.archive_downloaded, ctx);
        }
        if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.all_downloaded.after(exchangeItem.getRemoteStateProcessingStep())) {
            if (this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)operationConfig, args, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, document, counteragent, counteragentContext) != IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) {
                return 2;
            }
            service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.document_ready, counteragentContext);
        }
        if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed.after(exchangeItem.getRemoteStateProcessingStep()) || IEdoIntegrationInboundDataExchangeItem.ProcessingStep.document_ready.after(exchangeItem.getRemoteStateProcessingStep())) {
            exchangeItem.setStateCategory(operationConfig.getStateCategory());
            service.commitQueueItemRemoteState(session, operationContext, (IEdoIntegrationInboundDataExchangeItem)exchangeItem, IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed, counteragentContext);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set state category to %s", exchangeItem.getStateCategory()));
        }
        if (IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed.is(exchangeItem.getRemoteStateProcessingStep())) {
            return 10;
        }
        return 0;
    }
    
    default int processCounteragentState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoIntegrationServiceInternal service, final IDocument document, final IObjectModel counteragentContainer, final String systemCode, final Map<String, Object> processingArgs, final SessionContext ctx) throws DiadocEdoIntegrationException, DiadocSdkException {
        final SessionContext counteragentContext = ctx.clone();
        final String coverage = StateConfigCoverage.counteragent.name();
        counteragentContext.set("diadoc_edo_state_coverage", (Object)coverage);
        counteragentContext.set("__diadocedoartifact", (Object)systemCode);
        final IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration operationConfig = service.getOperationConfiguration(session, operationContext, "diadocedo_check_counteragent_remote_status_processing", () -> this.getServiceConfig().getStatusFlowConfiguration(), DiadocEdoOutboundDocumentCounteragentRemoteStatusProcessingOperationConfiguration::new, testConfig -> StringUtils.equals(coverage, testConfig.getCoverage()) && testConfig.isApplicableForStatus(systemCode), counteragentContext);
        if (operationConfig != null) {
            final Map<String, Object> args = new HashMap<String, Object>();
            if (processingArgs != null) {
                args.putAll(processingArgs);
            }
            this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)operationConfig, (Map)args, ctx);
            final IEdoIntegrationPackageProcessingContext processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
            return (this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)operationConfig, args, null, document, counteragentContainer, counteragentContext) == IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) ? 10 : 2;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("No configuration for state %s. Mark as proceed (code 100).", systemCode));
        return 100;
    }
    
    default int processCummulativeState(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoIntegrationServiceInternal service, final IDocument document, final String systemCode, final Map<String, Object> processingArgs, final SessionContext ctx) throws DiadocEdoIntegrationException, DiadocSdkException {
        final SessionContext counteragentContext = ctx.clone();
        final String coverage = StateConfigCoverage.all.name();
        counteragentContext.set("diadoc_edo_state_coverage", (Object)coverage);
        counteragentContext.set("__diadocedoartifact", (Object)systemCode);
        final IDiadocEdoOutboundDocumentRemoteStatusProcessingOperationConfiguration operationConfig = service.getOperationConfiguration(session, operationContext, "diadocedo_check_cumulative_remote_status_processing", () -> this.getServiceConfig().getStatusFlowConfiguration(), DiadocEdoOutboundDocumentCumulativeRemoteStatusProcessingOperationConfiguration::new, testConfig -> StringUtils.equals(coverage, testConfig.getCoverage()) && testConfig.isApplicableForStatus(systemCode), counteragentContext);
        if (operationConfig != null) {
            final Map<String, Object> args = new HashMap<String, Object>();
            if (processingArgs != null) {
                args.putAll(processingArgs);
            }
            this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)operationConfig, (Map)args, ctx);
            final IEdoIntegrationPackageProcessingContext processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
            return (this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)operationConfig, args, null, document, null, counteragentContext) == IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS) ? 10 : 2;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("No configuration for state %s. Mark as proceed (code 100).", systemCode));
        return 100;
    }
}
