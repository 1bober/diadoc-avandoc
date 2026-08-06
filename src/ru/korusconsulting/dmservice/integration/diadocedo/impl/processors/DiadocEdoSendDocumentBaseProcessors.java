// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import com.google.gson.Gson;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.commons.NLSBundle;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IDiadocEdoExchangeError;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItemLocalProcessingWrapper;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationExchangeErrorCategoryConfiguration;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByCounteragent;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentAttributeMappingQuery;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentObjectMapping;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentExecutionArgumentValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentEnvironmentVariableValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentEnvParameterValue;
import ru.korusconsulting.service.integration.configuration.answer.IEdoIntegrationAnswerMappingByFixedValue;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvProperty;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgExecutionArgument;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable;
import java.util.function.Function;
import java.util.stream.Collectors;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentProcedureArgFixedValue;
import java.util.function.BiConsumer;
import ru.korusconsulting.service.event.IEventAction;
import ru.korusconsulting.service.IEventHandlerService;
import ru.korusconsulting.dmservice.event.actions.documents.BreakResult;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.integration.diadocedo.configuration.inbound.IDiadocEdoInboundDocumentMappingProcedure;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import java.util.Objects;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.HashMap;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import java.util.Map;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationProcessableException;
import ru.korusconsulting.service.integration.exceptions.IProcessableException;
import ru.korusconsulting.dmservice.integration.impl.exceptions.IEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoSystemErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.service.objectmodel.og.ICounteragent;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoCounteragentCalculationException;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoCounteragentNoInnException;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.integration.configuration.flows.IEdoIntegrationFlowConfiguration;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeCounteragentUnit;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoExchangeCounteragentUnit;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;
import java.util.Iterator;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.model.impl.counteragent.resolvers.EdoIntegrationCounteragentByLinkedObjectResolver;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationCounteragentObjectSelectRule;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectRule;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoExchangeSendOperationUnit;
import ru.korusconsulting.dmservice.integration.configuration.impl.operations.EdoIntegrationOutboundDocumentSendOperationConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.integration.diadocedo.configuration.operations.IDiadocEdoOutboundDocumentSendOperationConfiguration;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoSendProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.impl.processors.EdoIntegrationSendDocumentProcessor;

public abstract class DiadocEdoSendDocumentBaseProcessors extends EdoIntegrationSendDocumentProcessor<IDiadocEdoOperationContext> implements IDiadocEdoSendProcessor
{
    public IDiadocEdoIntegrationServiceInternal getService() {
        return (IDiadocEdoIntegrationServiceInternal)super.getService();
    }
    
    public IDiadocEdoOutboundDocumentSendOperationConfiguration getOperationConfig() {
        final IEdoIntegrationOutboundDocumentSendOperationConfiguration operationConfig = super.getOperationConfig();
        if (operationConfig instanceof final IDiadocEdoOutboundDocumentSendOperationConfiguration a) {
            return a;
        }
        throw new ServiceException("Outbound send operation config is not an instance of IDiadocIntegrationOutboundDocumentSendOperationConfiguration. System misconfigured.");
    }
    
    protected List<IEdoIntegrationExchangeOperationUnit> getOperations(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final boolean queueNew, final SessionContext ctx) {
        if (this.getOperationConfig().getCounteragentSelectRules().isEmpty()) {
            IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s. No counteragent rules defined", document.getObjectId()));
            return Collections.emptyList();
        }
        final List<IEdoIntegrationExchangeOperationUnit> operationUnits = new ArrayList<IEdoIntegrationExchangeOperationUnit>();
        if (this.getOperationConfig().getCounteragentMode() == EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode.multi) {
            final DiadocEdoExchangeSendOperationUnit unit = new DiadocEdoExchangeSendOperationUnit(document);
            for (final IEdoIntegrationSelectRule rule : this.getOperationConfig().getCounteragentSelectRules()) {
                if (rule instanceof final IEdoIntegrationCounteragentObjectSelectRule counteragentObjectSelectRule) {
                    final EdoIntegrationCounteragentByLinkedObjectResolver resolver = new EdoIntegrationCounteragentByLinkedObjectResolver(counteragentObjectSelectRule);
                    final List<IObjectModel> counteragentContainerObjects = resolver.getCounteragents(session, (IIntegrationOperationContext)operationContext, (type, b, c) -> document.getValueAsString(b), ctx);
                    for (final IObjectModel counteragentContainer : counteragentContainerObjects) {
                        final IEdoIntegrationExchangeCounteragentUnit counteragentUnit = this.createDiadocEdoExchangeSendOperationUnit(session, operationContext, unit, counteragentContainer, counteragentObjectSelectRule, ctx);
                        unit.addChild((IEdoIntegrationExchangeUnit)counteragentUnit);
                    }
                }
            }
            operationUnits.add((IEdoIntegrationExchangeOperationUnit)unit);
        }
        else {
            for (final IEdoIntegrationSelectRule rule2 : this.getOperationConfig().getCounteragentSelectRules()) {
                if (rule2 instanceof final IEdoIntegrationCounteragentObjectSelectRule counteragentObjectSelectRule2) {
                    counteragentObjectSelectRule2.validate();
                    final EdoIntegrationCounteragentByLinkedObjectResolver resolver2 = new EdoIntegrationCounteragentByLinkedObjectResolver(counteragentObjectSelectRule2);
                    final List<IObjectModel> counteragentContainerObjects2 = resolver2.getCounteragents(session, (IIntegrationOperationContext)operationContext, (type, b, c) -> document.getValueAsString(b), ctx);
                    for (final IObjectModel counteragentContainer2 : counteragentContainerObjects2) {
                        final DiadocEdoExchangeSendOperationUnit unit2 = new DiadocEdoExchangeSendOperationUnit(document);
                        final IEdoIntegrationExchangeCounteragentUnit counteragentUnit = this.createDiadocEdoExchangeSendOperationUnit(session, operationContext, unit2, counteragentContainer2, counteragentObjectSelectRule2, ctx);
                        unit2.addChild((IEdoIntegrationExchangeUnit)counteragentUnit);
                        operationUnits.add((IEdoIntegrationExchangeOperationUnit)unit2);
                    }
                }
            }
        }
        return operationUnits;
    }
    
    protected IEdoIntegrationExchangeCounteragentUnit createDiadocEdoExchangeSendOperationUnit(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final DiadocEdoExchangeSendOperationUnit parent, final IObjectModel counteragentContainer, final IEdoIntegrationCounteragentObjectSelectRule counteragentObjectSelectRule, final SessionContext ctx) {
        final IEdoIntegrationExchangeCounteragentUnit counteragentUnit = (IEdoIntegrationExchangeCounteragentUnit)new DiadocEdoExchangeCounteragentUnit(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeOperationUnit)parent, ctx);
        counteragentUnit.setCounteragentContainerId(counteragentContainer.getObjectId());
        counteragentUnit.setCounteragentName(counteragentContainer.getValueAsString(counteragentObjectSelectRule.getNameAttribute()));
        counteragentUnit.setName(counteragentUnit.getCounteragentName());
        counteragentUnit.setCounteragentInn(counteragentContainer.getValueAsString(counteragentObjectSelectRule.getInnAttribute()));
        counteragentUnit.setCounteragentKpp(counteragentContainer.getValueAsString(counteragentObjectSelectRule.getKppAttribute()));
        counteragentUnit.setCounteragentId(counteragentContainer.getValueAsString(counteragentObjectSelectRule.getObjectIdAttribute()));
        counteragentUnit.setUseDictionaryOrganizationDetails(counteragentObjectSelectRule.useDictionaryOrganizationDetails());
        if (!this.isQueueProcessor()) {
            counteragentUnit.loadState(session, (IIntegrationOperationContext)operationContext, ctx);
        }
        return counteragentUnit;
    }
    
    protected boolean isQueueProcessor() {
        return false;
    }
    
    protected void prepareCounteragentItemForSending(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IDiadocEdoExchangeCounteragentUnit counteragentUnit, final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Working with counteragentUnit %s", counteragentUnit.toString()));
        counteragentUnit.initConnectionInfo(operationContext.getApiConnectionConfig(session, ctx));
        counteragentUnit.setFromDepartment(operationContext.getActiveDepartment());
        final String name = counteragentUnit.getCounteragentName();
        final String inn = counteragentUnit.getCounteragentInn();
        final String kpp = counteragentUnit.getCounteragentKpp();
        try {
            if (StringUtils.isBlank(inn)) {
                if (!counteragentUnit.useDictionaryOrganizationDetails()) {
                    counteragentUnit.setSendPrepareError(EdoIntegrationSubsystemErrorCode.COUNTERAGENT_NO_INN_ERROR);
                    throw new DiadocEdoCounteragentNoInnException(String.format("Diadoc.EDO counteragent container %s dosn't have inn", counteragentUnit.getObjectId()));
                }
                final IObjectModel counteragent = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentId(), ctx);
                if (counteragent == null) {
                    throw new DiadocEdoCounteragentCalculationException(String.format("Failed to find Diadoc.EDO counteragent with id %s ", counteragentUnit.getCounteragentId()));
                }
                if (counteragent instanceof final ICounteragent counteragentF) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Working with counteragent %s", counteragent.getObjectId()));
                    final String cName = counteragentF.getName();
                    final String cInn = counteragentF.getInn();
                    final String cKpp = counteragentF.getKpp();
                    if (StringUtils.isBlank(cInn)) {
                        throw new DiadocEdoCounteragentNoInnException(String.format("Diadoc.EDO counteragent %s (%s) dosn't have inn", cName, counteragentF.getObjectId()));
                    }
                    counteragentUnit.setCounteragentInn(cInn);
                    counteragentUnit.setToInn(cInn);
                    if (StringUtils.isBlank(name)) {
                        counteragentUnit.setName(cName);
                        counteragentUnit.setCounteragentName(cName);
                    }
                    if (StringUtils.isBlank(kpp)) {
                        counteragentUnit.setCounteragentKpp(cKpp);
                        counteragentUnit.setToKpp(cKpp);
                    }
                }
                else {
                    if (counteragentFlowConfiguration == null) {
                        counteragentUnit.setSendPrepareError(EdoIntegrationSubsystemErrorCode.COUNTERAGENT_CALCULATION_ERROR);
                        throw new DiadocEdoCounteragentCalculationException(String.format("Failed to find rule for Diadoc.EDO counteragent with id %s ", counteragentUnit.getCounteragentId()));
                    }
                    for (final IEdoIntegrationOperationConfiguration flow : counteragentFlowConfiguration.getOperationConfigurations()) {
                        if (flow instanceof final IEdoIntegrationCounteragentConfiguration counteragentConfiguration) {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Searching counteragent configuration for type %s", counteragent.getObjectType()));
                            if (counteragent.getObjectType().equals(counteragentConfiguration.getCounteragentTypeAttribute())) {
                                final String cName2 = counteragent.getValueAsString(counteragentConfiguration.getCounteragentNameAttribute());
                                final String cInn2 = counteragent.getValueAsString(counteragentConfiguration.getCounteragentInnAttribute());
                                final String cKpp2 = counteragent.getValueAsString(counteragentConfiguration.getCounteragentKppAttribute());
                                if (StringUtils.isBlank(cInn2)) {
                                    final IObjectModel counteragentF2 = counteragent;
                                    throw new DiadocEdoCounteragentNoInnException(String.format("Diadoc.EDO counteragent %s (%s) dosn't have inn", cName2, counteragentF2.getObjectId()));
                                }
                                counteragentUnit.setCounteragentInn(cInn2);
                                counteragentUnit.setToInn(cInn2);
                                if (StringUtils.isBlank(name)) {
                                    counteragentUnit.setName(cName2);
                                    counteragentUnit.setCounteragentName(cName2);
                                }
                                if (StringUtils.isBlank(kpp)) {
                                    counteragentUnit.setCounteragentKpp(cKpp2);
                                    counteragentUnit.setToKpp(cKpp2);
                                    break;
                                }
                                break;
                            }
                            else {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found counteragent configuration for type %s. Not match.", counteragentConfiguration.getCounteragentTypeAttribute()));
                            }
                        }
                    }
                }
            }
            else {
                counteragentUnit.setToInn(inn);
                counteragentUnit.setToKpp(kpp);
            }
        }
        catch (final DiadocEdoIntegrationSubsystemErrorException e) {
            throw e;
        }
        catch (final Throwable e2) {
            counteragentUnit.setSendPrepareError(EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR);
            throw new DiadocEdoSystemErrorException(String.format("Error while processing counteragent %s", counteragentUnit.getObjectId()), e2);
        }
    }
    
    protected abstract IEdoIntegrationSendProcessor.ProcessingResult sendCounteragentItem(final DMSShardsClientSession p0, final IDiadocEdoOperationContext p1, final IDiadocEdoApi p2, final IDocument p3, final IEdoIntegrationExchangeCounteragentUnit p4, final IEdoIntegrationFlowConfiguration p5, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode p6, final boolean p7, final boolean p8, final SessionContext p9) throws DiadocEdoIntegrationException;
    
    protected IEdoIntegrationSendProcessor.ProcessingResult sendToAutonomicCounteragentsList(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration = (IEdoIntegrationFlowConfiguration)this.getServiceConfig().getCounteragentFlowConfiguration();
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        for (final IEdoIntegrationExchangeUnit childUnit : operationUnit.getChildren()) {
            String counteragentName = operationContext.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.list_counteragents_names);
            if (StringUtils.isNotBlank(counteragentName)) {
                counteragentName = String.join(", ", counteragentName, childUnit.getName());
            }
            else {
                counteragentName = childUnit.getName();
            }
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.list_counteragents_names, counteragentName);
            if (!(childUnit instanceof IDiadocEdoExchangeCounteragentUnit)) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Child unit with class %s not IEdoIntegrationExchangeCounteragentUnit instance", (childUnit == null) ? "null" : childUnit.getClass().getName()));
                throw new DiadocEdoSystemErrorException(String.format("Child unit with class %s not IEdoIntegrationExchangeCounteragentUnit instance", (childUnit == null) ? "null" : childUnit.getClass().getName()));
            }
            final IDiadocEdoExchangeCounteragentUnit counteragentUnit = (IDiadocEdoExchangeCounteragentUnit)childUnit;
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_name, counteragentUnit.getName());
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, counteragentUnit.getCounteragentInn());
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, counteragentUnit.getCounteragentKpp());
            processingResult = processingResult.concat(this.processSendAction(session, operationContext, api, document, counteragentUnit, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, a -> {
                this.prepareCounteragentItemForSending(session, operationContext, api, document, a, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, allowTestCounteragent, ctx);
                return this.sendCounteragentItem(session, operationContext, api, document, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, ctx);
            }, true, ctx));
        }
        return processingResult;
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult processSendAction(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final IDiadocEdoExchangeCounteragentUnit counteragentUnit, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final DiadocEdoCounteragentSendAction<IEdoIntegrationSendProcessor.ProcessingResult> action, final boolean processSuccess, final SessionContext ctx) throws DiadocEdoIntegrationException {
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        try {
            processingResult = action.apply(counteragentUnit);
            if (processSuccess) {
                this.processCounteragentSuccess(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, ctx);
            }
            return processingResult;
        }
        catch (final DiadocEdoIntegrationSubsystemErrorException e) {
            processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            operationContext.setLastError(e.getErrorCode());
            if (e.getIntegrationLogMessage() != null) {
                this.getService().writeIntegrationLog(operationContext, IEdoIntegrationSendProcessor.ProcessingResult.FAILED, true, new IntegrationLogMessagesContainer(null, e.getIntegrationLogMessage(), null, null), ctx);
            }
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e);
            this.processCounteragentSendError(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, (IEdoIntegrationSubsystemErrorException)e, ctx);
            throw e;
        }
        catch (final DiadocEdoIntegrationProcessableException e2) {
            processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e2);
            this.processCounteragentSendError(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, (IProcessableException)e2, ctx);
            throw e2;
        }
        catch (final DiadocEdoIntegrationException e3) {
            processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", (Throwable)e3);
            this.processCounteragentSendError(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, EdoIntegrationSubsystemErrorCode.INTEGRATION_SYSTEM_ERROR, ctx);
            throw e3;
        }
        catch (final Throwable e4) {
            processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            operationContext.setLastError(EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR);
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Exception", e4);
            this.processCounteragentSendError(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, document, EdoIntegrationSubsystemErrorCode.SYSTEM_ERROR, ctx);
            throw new DiadocEdoSystemErrorException("System Error", e4);
        }
        finally {
            IntegrationLogMessagesContainer messagesContainer;
            if (this.isQueueProcessor()) {
                messagesContainer = new IntegrationLogMessagesContainer(processSuccess ? IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_QUEUE_DOCUMENT_SUCCESS : null, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_QUEUE_DOCUMENT_ERROR, null, null);
            }
            else {
                messagesContainer = new IntegrationLogMessagesContainer(processSuccess ? IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_SEND_DOCUMENT_SUCCESS : null, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_SEND_DOCUMENT_ERROR, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_SEND_DOCUMENT_WITH_ERRORS, null);
            }
            this.getService().writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
            operationContext.setLastError((EdoIntegrationSubsystemErrorCode)null);
        }
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult sendToMultilateralCounteragentsList(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.NONE;
        final IEdoIntegrationFlowConfiguration counteragentFlowConfiguration = (IEdoIntegrationFlowConfiguration)this.getServiceConfig().getCounteragentFlowConfiguration();
        IDiadocEdoExchangeCounteragentUnit counteragentUnit = null;
        for (final IEdoIntegrationExchangeUnit childUnit : operationUnit.getChildren()) {
            String counteragentName = operationContext.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.list_counteragents_names);
            if (StringUtils.isNotBlank(counteragentName)) {
                counteragentName = String.join(", ", counteragentName, childUnit.getName());
            }
            else {
                counteragentName = childUnit.getName();
            }
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.list_counteragents_names, counteragentName);
            if (!(childUnit instanceof IDiadocEdoExchangeCounteragentUnit)) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Child unit with class %s not IEdoIntegrationExchangeCounteragentUnit instance", (childUnit == null) ? "null" : childUnit.getClass().getName()));
                throw new DiadocEdoSystemErrorException(String.format("Child unit with class %s not IEdoIntegrationExchangeCounteragentUnit instance", (childUnit == null) ? "null" : childUnit.getClass().getName()));
            }
            counteragentUnit = (IDiadocEdoExchangeCounteragentUnit)childUnit;
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_name, counteragentUnit.getName());
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, counteragentUnit.getCounteragentInn());
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, counteragentUnit.getCounteragentKpp());
            processingResult = processingResult.concat(this.processSendAction(session, operationContext, api, document, counteragentUnit, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, a -> {
                this.prepareCounteragentItemForSending(session, operationContext, api, document, counteragentUnit, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, allowTestCounteragent, ctx);
                return IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
            }, false, ctx));
        }
        for (final IEdoIntegrationExchangeUnit childUnit : operationUnit.getChildren()) {
            if (childUnit instanceof final IDiadocEdoExchangeCounteragentUnit counteragentUnit2) {
                operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_name, counteragentUnit2.getName());
                processingResult = processingResult.concat(this.processSendAction(session, operationContext, api, document, counteragentUnit2, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, a -> this.sendCounteragentItem(session, operationContext, api, document, (IEdoIntegrationExchangeCounteragentUnit)counteragentUnit, counteragentFlowConfiguration, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, ctx), true, ctx));
            }
        }
        return processingResult;
    }
    
    protected IEdoIntegrationSendProcessor.ProcessingResult sendToCounteragentsList(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean ignoreCounteragentCertificateState, final boolean remoteSignatureMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        if (this.getOperationConfig().getCounteragentMode() == EdoIntegrationOutboundDocumentSendOperationConfiguration.CounteragentMode.multi) {
            return this.sendToMultilateralCounteragentsList(session, operationContext, api, operationUnit, document, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, allowTestCounteragent, ctx);
        }
        return this.sendToAutonomicCounteragentsList(session, operationContext, api, operationUnit, document, notInvitedCounteragentSendMode, ignoreCounteragentCertificateState, remoteSignatureMode, allowTestCounteragent, ctx);
    }
    
    public boolean processResponseForDocument(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationPackageProcessingContext processingContext, final IEdoIntegrationInboundDocumentProcessingConfiguration operationConfig, final Map<String, Object> args, final IEdoIntegrationInboundDataExchangeItem inboundRequest, final IDocument document, final IObjectModel counteragent, final SessionContext ctx) {
        final Map<String, String> env = new HashMap<String, String>();
        if (inboundRequest != null) {
            env.put("filial_id", inboundRequest.getFilialId());
            env.put("filial_name", inboundRequest.getFilialName());
            env.put("document_guid", inboundRequest.getDocumentEdoId());
        }
        else {
            env.put("filial_id", document.getFilial());
            env.put("filial_name", document.getFilialName());
        }
        final int pageCount = 0;
        env.put("page_count", String.valueOf(pageCount));
        final int appCount = 0;
        env.put("apps_count", String.valueOf(appCount - 1));
        final String signer = null;
        env.put("signer", signer);
        final Map<String, Object> processorArgs = new HashMap<String, Object>();
        processorArgs.putAll(args);
        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)operationConfig, (Map)processorArgs, ctx);
        if (StringUtils.isNotBlank(operationConfig.getDataArgument()) && inboundRequest instanceof IDiadocEdoExchangeCounteragentUnit) {
            final IDiadocEdoExchangeCounteragentUnit counteragentUnit = (IDiadocEdoExchangeCounteragentUnit)inboundRequest;
            if (counteragentUnit.getMessageAsPojo() != null) {
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Package content : %s", new Gson().toJson((Object)counteragentUnit.getMessageAsPojo())));
                processorArgs.put(operationConfig.getDataArgument(), counteragentUnit.getMessageAsPojo());
            }
        }
        operationConfig.getProcessorEvents().forEach(a -> ServiceReg.getObjectModelService().applyXmlAspect(session, a.getProcessorLink(), (IObjectModel)document, processorArgs, ctx));
        final List<IEdoIntegrationAttributeMapping> mappingList = operationConfig.getAttributesMapping();
        final IDiadocEdoOperationContext operationContext2 = (IDiadocEdoOperationContext)operationContext;
        final List<IEdoIntegrationAttributeMapping> mappingList2 = mappingList;
        final Map<String, String> env2 = env;
        final Map<String, Object> args2 = processorArgs;
        Objects.requireNonNull(document);
        this.processInboundAttributes(session, operationContext2, document, counteragent, (IObjectModel)document, mappingList2, env2, args2, document::setValue, ctx);
        final List<IEdoIntegrationAttributeMapping> mappingProceduresList = operationConfig.getMappingProcedures();
        for (final IEdoIntegrationAttributeMapping mapping : mappingProceduresList) {
            if (mapping instanceof final IDiadocEdoInboundDocumentMappingProcedure procedureMapping) {
                final String procedureClass = procedureMapping.getProcedureClass();
                final Map<String, List<Object>> params = new HashMap<String, List<Object>>();
                final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList = procedureMapping.getProcedureArgs();
                this.processInboundAttributes(session, (IDiadocEdoOperationContext)operationContext, document, counteragent, (IObjectModel)document, procedureMappingArgsList, env, processorArgs, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
            }
        }
        ServiceReg.getDocumentRouteService().commit(session, document, ctx);
        this.saveArgumentsState(processingContext, (Map)processorArgs, operationConfig.getGlobalEnvironmentsNames());
        final String postActionsConfigId = operationConfig.getPostActionsId();
        if (StringUtils.isNotBlank(postActionsConfigId)) {
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Postactions %s call requested", postActionsConfigId));
            final ConfigElement postActionsConfig = ServiceReg.getConfigService().lookupElement("postactions[id=" + postActionsConfigId, ctx);
            if (postActionsConfig != null) {
                processorArgs.putIfAbsent("document", document);
                processorArgs.put("auto_process_action", "true");
                final IEventHandlerService eventHandlerService = ServiceReg.getEventHandlerService();
                final ConfigElement actionsCfg = postActionsConfig.getFirstChild("actions");
                if (actionsCfg != null) {
                    final Map<String, String> eventParams = new HashMap<String, String>();
                    for (final String key : ctx.getAttributeNames()) {
                        eventParams.put(key, ctx.getString(key));
                    }
                    eventParams.put("auto_process_action", "true");
                    for (final ConfigElement actionCfg : actionsCfg.getChildren()) {
                        final IEventAction action = eventHandlerService.createAction(actionCfg.getAttributeValue("name"), ctx);
                        if (action != null) {
                            IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Call event action %s", actionCfg.getAttributeValue("name")));
                            action.setLevel(0);
                            if ("true".equals(action.getConfig().getAttributeValue("useaslinkconfig"))) {
                                action.setLinkConfig(action.getConfig());
                            }
                            else {
                                action.setLinkConfig(actionCfg);
                            }
                            action.setEventName("sbis_inbound_post_action");
                            action.setEventParams((Map)eventParams);
                            final Object result = action.tryExecute(session, (Map)processorArgs, ctx);
                            if (result instanceof BreakResult) {
                                IIntegrationOperationContext.trace(operationContext, this.getLogger(), () -> String.format("Action %s is BreakResult actions. Stop actions list processing", actionCfg.getAttributeValue("name")));
                                break;
                            }
                            continue;
                        }
                        else {
                            IIntegrationOperationContext.warn(operationContext, this.getLogger(), () -> String.format("event action %s is not found", actionCfg.getAttributeValue("name")));
                        }
                    }
                }
                else {
                    IIntegrationOperationContext.warn(operationContext, this.getLogger(), () -> String.format("Actions list for postactions %s not found", postActionsConfigId));
                }
            }
            else {
                IIntegrationOperationContext.warn(operationContext, this.getLogger(), () -> String.format("Postactions %s not found", postActionsConfigId));
            }
        }
        return true;
    }
    
    protected boolean processInboundAttributes(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final IObjectModel counteragent, final IObjectModel subjectModel, final List<IEdoIntegrationAttributeMapping> mappingList, final Map<String, String> env, final Map<String, Object> args, final BiConsumer<String, Object> setter, final SessionContext ctx) {
        boolean result = false;
        for (final IEdoIntegrationAttributeMapping mapping : mappingList) {
            if (this.processInboundAttributeMapping(session, operationContext, document, counteragent, subjectModel, mapping, env, args, setter, ctx)) {
                result = true;
            }
        }
        return result;
    }
    
    protected boolean processInboundAttributeMapping(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDocument document, final IObjectModel counteragent, final IObjectModel subjectModel, final IEdoIntegrationAttributeMapping mapping, final Map<String, String> env, final Map<String, Object> args, final BiConsumer<String, Object> setter, final SessionContext ctx) {
        if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgFixedValue fixedMapping) {
            final String attribute = fixedMapping.getProcArgument();
            final Map.Entry<String, List<Object>> a;
            final Map<String, String> rArgs = args.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends String>)Map.Entry::getKey, a -> {
                final Object patt38751$temp = a.getValue();
                Object o;
                if (patt38751$temp instanceof String) {
                    final String aS = (String)(o = patt38751$temp);
                }
                else {
                    o = "";
                }
                return o;
            }));
            final String value = this.getService().calculateMessage(fixedMapping.getValue(), operationContext, rArgs, ctx);
            if (StringUtils.isNotBlank(attribute)) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value), operationContext.getPropogateLogMode());
                setter.accept(attribute, value);
                return true;
            }
        }
        else {
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgEnvironmentVariable environmentVariable) {
                final String environmentName = environmentVariable.getEnvironmentName();
                final String environmentVariableName = environmentVariable.getEnvironmentVariable();
                final Map<String, Object> environment = args.get(String.format("__%s__environment", environmentName));
                final String value2 = (environment != null) ? Objects.toString(environment.get(environmentVariableName), null) : null;
                final String attribute2 = environmentVariable.getProcArgument();
                setter.accept(attribute2, value2);
                return true;
            }
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgExecutionArgument executingArgument) {
                final String executingArgumentName = executingArgument.getExecutionArgument();
                final String attribute3 = executingArgument.getProcArgument();
                setter.accept(attribute3, args.get(executingArgumentName));
                return true;
            }
            if (mapping instanceof final IDiadocEdoInboundDocumentProcedureArgEnvProperty envParameter) {
                final String attribute = envParameter.getProcArgument();
                setter.accept(attribute, env.get(envParameter.getEnvProperty()));
                return true;
            }
            if (mapping instanceof final IEdoIntegrationAnswerMappingByFixedValue fixedMapping2) {
                final String attribute = fixedMapping2.getAttribute();
                final Map.Entry<String, List<Object>> a;
                final Map<String, String> rArgs = args.entrySet().stream().collect(Collectors.toMap((Function<? super Object, ? extends String>)Map.Entry::getKey, a -> {
                    final Object patt40706$temp = a.getValue();
                    Object o2;
                    if (patt40706$temp instanceof String) {
                        final String aS2 = (String)(o2 = patt40706$temp);
                    }
                    else {
                        o2 = "";
                    }
                    return o2;
                }));
                final String value = this.getService().calculateMessage(fixedMapping2.getValue(), operationContext, rArgs, ctx);
                if (StringUtils.isNotBlank(attribute)) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value), operationContext.getPropogateLogMode());
                    setter.accept(attribute, value);
                    return true;
                }
            }
            else if (mapping instanceof final IDiadocEdoInboundDocumentEnvParameterValue envParameter2) {
                final String attribute = envParameter2.getAttribute();
                final String value3 = env.get(envParameter2.getEnvProperty());
                setter.accept(attribute, value3);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute, value3), operationContext.getPropogateLogMode());
                if (StringUtils.isNotBlank(envParameter2.getDisplayAttribute()) && StringUtils.isNotBlank(envParameter2.getLinkedDisplayAttribute())) {
                    if (!StringUtils.isNotBlank(value3)) {
                        setter.accept(envParameter2.getDisplayAttribute(), null);
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), null), operationContext.getPropogateLogMode());
                        return true;
                    }
                    final IObjectModel model = ServiceReg.getObjectModelService().getObjectModel(session, value3, ctx);
                    if (model != null) {
                        setter.accept(envParameter2.getDisplayAttribute(), model.getValue(envParameter2.getLinkedDisplayAttribute()));
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), model.getValue(envParameter2.getLinkedDisplayAttribute())), operationContext.getPropogateLogMode());
                        return true;
                    }
                    setter.accept(envParameter2.getDisplayAttribute(), null);
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), envParameter2.getDisplayAttribute(), null), operationContext.getPropogateLogMode());
                    return true;
                }
            }
            else {
                if (mapping instanceof final IDiadocEdoInboundDocumentEnvironmentVariableValue environmentVariable2) {
                    final String environmentName = environmentVariable2.getEnvironmentName();
                    final String environmentVariableName = environmentVariable2.getEnvironmentVariable();
                    final Map<String, Object> environment = args.get(String.format("__%s__environment", environmentName));
                    final String value2 = (environment != null) ? Objects.toString(environment.get(environmentVariableName), null) : null;
                    final String attribute2 = environmentVariable2.getAttribute();
                    setter.accept(attribute2, value2);
                    return true;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentExecutionArgumentValue executingArgument2) {
                    final String executingArgumentName = executingArgument2.getExecutionArgument();
                    final String attribute3 = executingArgument2.getAttribute();
                    setter.accept(attribute3, args.get(executingArgumentName));
                    return true;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentObjectMapping objectMapping) {
                    if (objectMapping.getLinkType() != null) {}
                    return true;
                }
                Map<String, List<Object>> params = null;
                Map.Entry<String, List<Object>> a = null;
                if (mapping instanceof final IDiadocEdoInboundDocumentAttributeMappingQuery queryMapping) {
                    params = new HashMap<String, List<Object>>();
                    final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList = queryMapping.getProcedureArgs();
                    this.processInboundAttributes(session, null, document, counteragent, subjectModel, procedureMappingArgsList, env, args, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
                    String query = queryMapping.getQuery();
                    final Iterator<Map.Entry<String, List<Object>>> iterator = params.entrySet().iterator();
                    while (iterator.hasNext()) {
                        a = iterator.next();
                        query = ru.korusconsulting.service.integration.StringUtils.replaceInString(query, (String)a.getKey(), (a.getValue() != null && a.getValue().size() > 0) ? a.getValue().get(0).toString() : "");
                    }
                    final String queryF = query;
                    final ShardSessionRunner<String> shardSessionRunner = new ShardSessionRunner<String>() {
                        public String run() {
                            return ServiceReg.getQueryService().getFirstValueFromQuery(session, queryF, ctx);
                        }
                    };
                    final String value4 = (String)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
                    final String attribute4 = queryMapping.getAttribute();
                    if (StringUtils.isNotBlank(attribute4)) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Incoming document %s. Setting attribute %s value : [%s]", subjectModel.getObjectId(), attribute4, value4), operationContext.getPropogateLogMode());
                        setter.accept(attribute4, value4);
                        if (StringUtils.isNotBlank(queryMapping.getDisplayAttribute()) && StringUtils.isNotBlank(queryMapping.getLinkedDisplayAttribute())) {
                            if (StringUtils.isNotBlank(value4)) {
                                final IObjectModel model2 = ServiceReg.getObjectModelService().getObjectModel(session, value4, ctx);
                                if (model2 != null) {
                                    setter.accept(queryMapping.getDisplayAttribute(), model2.getValue(queryMapping.getLinkedDisplayAttribute()));
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), model2.getValue(queryMapping.getLinkedDisplayAttribute())), operationContext.getPropogateLogMode());
                                }
                                else {
                                    setter.accept(queryMapping.getDisplayAttribute(), null);
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), null));
                                }
                            }
                            else {
                                setter.accept(queryMapping.getDisplayAttribute(), null);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Set attribute %s = %s", queryMapping.getDisplayAttribute(), null));
                            }
                        }
                    }
                    return false;
                }
                if (mapping instanceof final IDiadocEdoInboundDocumentMappingProcedure procedureMapping) {
                    final String procedureClass = procedureMapping.getProcedureClass();
                    final String attribute3 = procedureMapping.getAttribute();
                    final Map<String, List<Object>> params2 = new HashMap<String, List<Object>>();
                    final List<IEdoIntegrationAttributeMapping> procedureMappingArgsList2 = procedureMapping.getProcedureArgs();
                    this.processInboundAttributes(session, operationContext, document, counteragent, subjectModel, procedureMappingArgsList2, env, args, (a, b) -> params.computeIfAbsent(a, c -> new ArrayList()).add(b), ctx);
                    return true;
                }
                if (mapping instanceof final IEdoIntegrationAnswerMappingByCounteragent linkedConfig) {
                    if (counteragent != null) {
                        final List answerMapping = linkedConfig.getAnswerMapping();
                        Objects.requireNonNull(counteragent);
                        if (this.processInboundAttributes(session, operationContext, document, counteragent, counteragent, answerMapping, env, args, counteragent::setValue, ctx)) {
                            ServiceReg.getObjectModelService().commitModel(session, counteragent, ctx);
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Commited object %s with type %s", counteragent.getObjectId(), counteragent.getObjectType()));
                            return true;
                        }
                    }
                    return false;
                }
                if (mapping != null) {
                    if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                        throw new IllegalArgumentException(String.format("Unimplemented mapping type %s", mapping.getClass().getName()));
                    }
                    IIntegrationOperationContext.warn((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Unimplemented mapping type %s", mapping.getClass().getName()));
                }
            }
        }
        return false;
    }
    
    protected void processCounteragentSendError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IDocument document, final IProcessableException exception, final SessionContext ctx) {
        final String errorCode = String.valueOf(exception.getCode());
        final String errorMessage = exception.getReason();
        final IDiadocEdoExchangeError error = this.getService().transformExchangeError(session, (IDiadocEdoOperationContext)operationContext, errorCode, errorMessage, ctx);
        if (error != null) {
            String transformatErrorCode = error.getCounteragentStateCode();
            String transformatErrorMessage = error.getCounteragentStateMessage();
            IEdoIntegrationExchangeErrorCategoryConfiguration cat = null;
            IEdoIntegrationExchangeErrorCategoryConfiguration catL2 = null;
            if (StringUtils.isNotBlank(error.getCategory())) {
                for (final IDictionaryElementConfiguration p : this.getServiceConfig().getExchangeErrorCategories()) {
                    if (StringUtils.equals(p.getValue(), error.getCategory())) {
                        cat = (IEdoIntegrationExchangeErrorCategoryConfiguration)p;
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(error.getCategoryL2())) {
                for (final IDictionaryElementConfiguration p : this.getServiceConfig().getExchangeErrorCategories()) {
                    if (StringUtils.equals(p.getValue(), error.getCategoryL2())) {
                        catL2 = (IEdoIntegrationExchangeErrorCategoryConfiguration)p;
                        break;
                    }
                }
            }
            if (cat != null && cat.isReiterable()) {
                final int currentAttempt = counteragentUnit.getCurrentAttempt();
                counteragentUnit.setCurrentAttempt(currentAttempt + 1);
                if (currentAttempt > 5) {
                    counteragentUnit.setSendState(EdoIntegrationProcessingState.ERROR);
                    cat = catL2;
                    transformatErrorCode = error.getCounteragentStateCodeL2();
                    transformatErrorMessage = error.getCounteragentStateMessageL2();
                }
            }
            else {
                counteragentUnit.setSendState(EdoIntegrationProcessingState.ERROR);
            }
            counteragentUnit.commit(session, true, ctx);
            if (cat != null) {
                for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : this.getOperationConfig().getExchangeErrorProcessing()) {
                    if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                        final List<String> sendPrepareErrors = configuration.getExchangeErrorCategories();
                        if (sendPrepareErrors.contains(cat.getValue())) {
                            final IEdoIntegrationExchangeItem itemRaw = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                            if (itemRaw != null) {
                                final IEdoIntegrationExchangeItemProcessingWrapper item = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw);
                                final IObjectModel counteragentContainer = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                                final Map<String, Object> args = new HashMap<String, Object>();
                                args.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_code.name(), transformatErrorCode);
                                args.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_message.name(), transformatErrorMessage);
                                this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)configuration, (Map)args, ctx);
                                final IEdoIntegrationPackageProcessingContext processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
                                if (this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)configuration, args, null, document, counteragentContainer, ctx)) {
                                    item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                                    item.setStateCategory(configuration.getStateCategory());
                                }
                                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item.getItem(), ctx);
                            }
                            return;
                        }
                        continue;
                    }
                }
            }
        }
        else {
            counteragentUnit.setSendState(EdoIntegrationProcessingState.ERROR);
            counteragentUnit.commit(session, true, ctx);
        }
        for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration2 : this.getOperationConfig().getDefaultErrorProcessing()) {
            if (processorConfiguration2 instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration2) {
                final IEdoIntegrationExchangeItem itemRaw2 = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                if (itemRaw2 == null) {
                    continue;
                }
                final IEdoIntegrationExchangeItemProcessingWrapper item2 = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw2);
                final IObjectModel counteragentContainer2 = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                final Map<String, Object> args2 = new HashMap<String, Object>();
                args2.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_code.name(), errorCode);
                args2.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_message.name(), errorMessage);
                this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)configuration2, (Map)args2, ctx);
                final IEdoIntegrationPackageProcessingContext processingContext2 = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
                if (this.processResponseForDocument(session, operationContext, processingContext2, (IEdoIntegrationInboundDocumentProcessingConfiguration)configuration2, args2, null, document, counteragentContainer2, ctx)) {
                    item2.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                    item2.setStateCategory(configuration2.getStateCategory());
                }
                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item2.getItem(), ctx);
            }
        }
    }
    
    protected void processCounteragentSendError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IDocument document, final EdoIntegrationSubsystemErrorCode errorCode, final SessionContext ctx) {
        counteragentUnit.setSendState(EdoIntegrationProcessingState.ERROR);
        counteragentUnit.commit(session, true, ctx);
        final NLSBundle nslBundle = ServiceReg.getNLSService().getBundle("diadocedo_error_messages_bundle", ctx);
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_code.name(), errorCode.name());
        args.put(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_message.name(), nslBundle.getNLSString(String.format("MSG_DIADOCEDO_MESSAGE_%s", errorCode.name())));
        for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : this.getOperationConfig().getSendPrepareErrorProcessing()) {
            if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                final List<String> sendPrepareErrors = configuration.getSendPrepareErrors();
                if (sendPrepareErrors.contains(errorCode.name())) {
                    final IEdoIntegrationExchangeItem itemRaw = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                    if (itemRaw != null) {
                        final IEdoIntegrationExchangeItemProcessingWrapper item = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw);
                        final IObjectModel counteragentContainer = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                        this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)configuration, (Map)args, ctx);
                        final IEdoIntegrationPackageProcessingContext processingContext = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
                        if (this.processResponseForDocument(session, operationContext, processingContext, (IEdoIntegrationInboundDocumentProcessingConfiguration)configuration, args, null, document, counteragentContainer, ctx)) {
                            item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                            item.setStateCategory(configuration.getStateCategory());
                        }
                        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item.getItem(), ctx);
                    }
                    return;
                }
                continue;
            }
        }
        for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : this.getOperationConfig().getDefaultErrorProcessing()) {
            if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                final IEdoIntegrationExchangeItem itemRaw2 = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                if (itemRaw2 == null) {
                    continue;
                }
                final IEdoIntegrationExchangeItemProcessingWrapper item2 = (IEdoIntegrationExchangeItemProcessingWrapper)new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw2);
                final IObjectModel counteragentContainer2 = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                this.prepareProcessingEnvironments(session, (IIntegrationOperationContext)operationContext, (IEdoIntegrationProcessingConfiguration)configuration, (Map)args, ctx);
                final IEdoIntegrationPackageProcessingContext processingContext2 = (IEdoIntegrationPackageProcessingContext)new EdoIntegrationPackageProcessingContext((IEdoIntegrationFileProcessingContext)null);
                if (this.processResponseForDocument(session, operationContext, processingContext2, (IEdoIntegrationInboundDocumentProcessingConfiguration)configuration, args, null, document, counteragentContainer2, ctx)) {
                    item2.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                    item2.setStateCategory(configuration.getStateCategory());
                }
                ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item2.getItem(), ctx);
            }
        }
    }
}
