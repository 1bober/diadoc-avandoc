// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByOperationContextAttributeResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationContextAttribute;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByLinkedObjectResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import java.util.Collections;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoSendProcessor;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.dmservice.common.TransactionRunner;
import ru.korusconsulting.dmservice.common.AdminSessionRunner;
import java.util.Iterator;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationProcessableException;
import ru.korusconsulting.service.integration.exceptions.IProcessableException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.dmservice.integration.impl.exceptions.IEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordEvent;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import java.util.List;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.Collection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;

public class DiadocEdoQueueDocumentProcessor extends DiadocEdoSendDocumentBaseProcessors
{
    @Override
    protected IEdoIntegrationSendProcessor.ProcessingResult sendCounteragentItem(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final SessionContext ctx) throws DiadocEdoIntegrationException {
        counteragentUnit.queue();
        return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
    }
    
    public IEdoIntegrationSendProcessor.ProcessingResult process(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final Collection<IEdoIntegrationExchangeItem> exchangeItem, final SessionContext ctx) {
        final SessionContext context = ctx.clone();
        final List<IEdoIntegrationExchangeOperationUnit> operationUnitsForSend = this.getOperations(session, operationContext, document, true, context);
        this.logCounteragent((IIntegrationOperationContext)operationContext, "Found all counteragents", document, (List)operationUnitsForSend);
        if (operationUnitsForSend.isEmpty()) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Not found counteragents need sended", operationContext.getPropogateLogMode());
            exchangeItem.forEach(a -> this.markExchangeItemAs(session, a, EdoIntegrationProcessingState.CANCELLED, ctx));
            return IEdoIntegrationSendProcessor.ProcessingResult.SKIPPED;
        }
        final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode = IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.parse(this.getOperationConfig().getNotInvitedCounteragentSendMode());
        int succeedCount = 0;
        for (final IEdoIntegrationExchangeOperationUnit operationUnit : operationUnitsForSend) {
            try (final IDiadocEdoOperationContext oContext = DiadocEdoOperationContext.createDocumentContext(session, (IIntegrationOperationContext)operationContext, document, null, DiadocEdoIntegrationOperation.QUEUE_DOCUMENT, ctx)) {
                IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
                try {
                    this.sendToCounteragentsList(session, oContext, null, operationUnit, document, notInvitedCounteragentSendMode, false, false, true, ctx);
                    this.processSuccess(session, oContext, operationUnit, document, ctx);
                    ++succeedCount;
                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                }
                catch (final DiadocEdoIntegrationSubsystemErrorException e) {
                    oContext.setLastError(e.getErrorCode());
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, (IEdoIntegrationSubsystemErrorException)e, ctx);
                }
                catch (final DiadocEdoIntegrationProcessableException e2) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e2);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, (IProcessableException)e2, ctx);
                }
                catch (final DiadocEdoIntegrationException e3) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", (Throwable)e3);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, EdoIntegrationSubsystemErrorCode.INTEGRATION_SYSTEM_ERROR, context);
                }
                catch (final Throwable e4) {
                    oContext.setLastError(EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)oContext, this.getLogger(), () -> "Exception", e4);
                    this.processOperationError(session, (IIntegrationOperationContext)oContext, operationUnit, document, EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR, ctx);
                }
                finally {
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(null, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_QUEUE_DOCUMENT_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_QUEUE_DOCUMENT_ERROR, null);
                    this.getService().writeIntegrationLog(oContext, processingResult, true, messagesContainer, ctx);
                    oContext.setLastError((EdoIntegrationSubsystemErrorCode)null);
                }
                if (oContext == null) {
                    continue;
                }
            }
        }
        if (succeedCount == 0) {
            return IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
        }
        if (succeedCount == operationUnitsForSend.size()) {
            return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
        }
        return IEdoIntegrationSendProcessor.ProcessingResult.WITHERRORS;
    }
    
    protected void processSuccess(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final SessionContext ctx) {
    }
    
    protected void flashPreviousSendingState(final DMSShardsClientSession session, final IDocument document, final String counteragentContainerId, final SessionContext ctx) {
        final AdminSessionRunner<Object> adminSessionRunner = new AdminSessionRunner<Object>() {
            public Object run() {
                final TransactionRunner<Object> runner = new TransactionRunner<Object>() {
                    public Object run() {
                        final String updateQuery = String.format("update nt_edo_integration_exchange_item object set sba_not_actual=true where ssa_integration = '%s' and ssa_direction='%s' and sida_filial= '%s' and sida_document = '%s' and ssa_item_type='%s' and sida_counteragent_container = '%s'", "DIADOCEDO", IEdoIntegrationService.EXCHANGE_DIRECTION.OUT.name(), document.getFilial(), document.getObjectId(), EdoIntegrationItemType.document.name(), counteragentContainerId);
                        DiadocEdoQueueDocumentProcessor.this.getLogger().trace(() -> String.format("Document %s. Run update old exchange items query %n%s", document.getObjectId(), updateQuery));
                        ServiceReg.getQueryService().executeUpdateQuery(AdminSessionRunner.this.getAdminSession().getClientSession(DMSShardsManager.SHARD.DICT.name()), updateQuery, ctx);
                        return null;
                    }
                };
                runner.start(session.getClientSession(DMSShardsManager.getShardNameByContext(ctx)));
                return null;
            }
        };
        adminSessionRunner.start(session);
    }
    
    @Override
    protected void prepareCounteragentItemForSending(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IDiadocEdoExchangeCounteragentUnit counteragentUnit, final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        this.flashPreviousSendingState(session, document, counteragentUnit.getCounteragentContainerId(), ctx);
        super.prepareCounteragentItemForSending(session, operationContext, api, document, counteragentUnit, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, allowTestCounteragent, ctx);
    }
    
    public void close() {
    }
    
    @Override
    protected boolean isQueueProcessor() {
        return true;
    }
    
    public List<IDiadocEdoSendProcessor.AttrDesc> getFieldNames() {
        return Collections.emptyList();
    }
    
    public IEdoIntegrationAttributesByAttributeResolver createAttributeMappingResolver(final IEdoIntegrationOutboundAttributeMapping rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return null;
    }
    
    public IEdoIntegrationAttributesByFixedValueResolver createFixedValueResolver(final IEdoIntegrationAttributeMappingByFixedValue rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return null;
    }
    
    public IEdoIntegrationAttributesByLinkedObjectResolver createByLinkedObjectMappingResolver(final IEdoIntegrationOutboundMappingByLinkedObject rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return null;
    }
    
    public IEdoIntegrationAttributesByOperationContextAttributeResolver createByOperationContextAttributeResolver(final IEdoIntegrationAttributeMappingByOperationContextAttribute rule, final IEdoIntegrationAttributesResolverFactory factory) {
        return null;
    }
}
