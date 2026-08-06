// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOperationConfiguration;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByOperationContextAttributeResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByLinkedObjectResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByFixedValueResolver;
import ru.korusconsulting.service.integration.model.attribute.resolvers.IEdoIntegrationAttributesByAttributeResolver;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByOperationContextAttribute;
import java.util.Arrays;
import java.util.Collections;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundMappingByLinkedObject;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMappingByFixedValue;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationOutboundAttributeMapping;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeMapping;
import ru.korusconsulting.dmservice.integration.configuration.impl.EdoIntegrationDocumentAttributeDataType;
import ru.korusconsulting.dmservice.integration.TernaryConsumer;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItemProcessingWrapper;
import java.util.Iterator;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationInboundDataExchangeItem;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationProcessingConfiguration;
import java.util.HashMap;
import ru.korusconsulting.dmservice.objectmodel.EdoIntegrationExchangeItemLocalProcessingWrapper;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.service.integration.configuration.operations.status.IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundDocumentProcessingConfiguration;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeCounteragentUnit;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;
import ru.korusconsulting.dmservice.integration.impl.exceptions.IEdoIntegrationSubsystemErrorException;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.exceptions.IProcessableException;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationOutboundDocumentSendOperationConfiguration;
import org.apache.logging.log4j.util.Supplier;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeOperationUnit;
import java.util.List;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public abstract class EdoIntegrationSendDocumentProcessor<T extends IIntegrationOperationContext> extends EdoIntegrationInboundDocumentProcessor<T> implements IEdoIntegrationAttributesResolverFactory
{
    protected abstract boolean isQueueProcessor();
    
    protected void logCounteragent(final IIntegrationOperationContext operationContext, final String message, final IDocument document, final List<IEdoIntegrationExchangeOperationUnit> operationUnits) {
        IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Document %s. %s : %s", document.getObjectId(), message, operationUnits.stream().map(a -> "\nOperation: " + a.getChildren().stream().map(b -> {
            if (b instanceof final IEdoIntegrationExchangeCounteragentUnit c) {
                return "\nCounteragent id: " + c.getCounteragentContainerId() + " Counteragent ID: " + c.getCounteragentId();
            }
            else {
                return "";
            }
        }).collect((Collector<? super Object, ?, String>)Collectors.joining(","))).collect(Collectors.joining(";\n")))));
    }
    
    @Override
    public IEdoIntegrationOutboundDocumentSendOperationConfiguration getOperationConfig() {
        return (IEdoIntegrationOutboundDocumentSendOperationConfiguration)super.getOperationConfig();
    }
    
    protected void processOperationError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IProcessableException exception, final SessionContext ctx) {
    }
    
    protected void processOperationError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IEdoIntegrationSubsystemErrorException exception, final SessionContext ctx) {
    }
    
    protected void processOperationError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final EdoIntegrationSubsystemErrorCode errorCode, final SessionContext ctx) {
    }
    
    protected void processCounteragentSuccess(final DMSShardsClientSession session, final T operationContext, final IEdoIntegrationExchangeCounteragentUnit counteragentUnit, final IDocument document, final SessionContext ctx) {
        counteragentUnit.commit(session, true, ctx);
        final List<IEdoIntegrationInboundDocumentProcessingConfiguration> processingActions = this.isQueueProcessor() ? this.getOperationConfig().getQueuedStatusProcessing() : this.getOperationConfig().getSendedStatusProcessing();
        if (processingActions != null) {
            for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : processingActions) {
                if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                    final IEdoIntegrationExchangeItem itemRaw = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                    if (itemRaw == null) {
                        continue;
                    }
                    final IEdoIntegrationExchangeItemProcessingWrapper item = new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw);
                    final IObjectModel counteragentContainer = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                    final Map<String, Object> args = new HashMap<String, Object>();
                    this.prepareProcessingEnvironments(session, operationContext, configuration, args, ctx);
                    final IEdoIntegrationPackageProcessingContext processingContext = new EdoIntegrationPackageProcessingContext(null);
                    if (this.processResponseForDocument(session, operationContext, processingContext, configuration, args, counteragentUnit, document, counteragentContainer, ctx)) {
                        item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                        item.setStateCategory(configuration.getStateCategory());
                    }
                    ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item.getItem(), ctx);
                }
            }
        }
    }
    
    protected void processCounteragentSendError(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IEdoIntegrationExchangeCounteragentUnit operationUnit, final IDocument document, final IEdoIntegrationSubsystemErrorException exception, final SessionContext ctx) {
        this.processCounteragentSendError(session, operationContext, operationUnit, document, exception.getErrorCode(), ctx);
    }
    
    protected abstract void processCounteragentSendError(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final IEdoIntegrationExchangeCounteragentUnit p2, final IDocument p3, final IProcessableException p4, final SessionContext p5);
    
    protected abstract void processCounteragentSendError(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final IEdoIntegrationExchangeCounteragentUnit p2, final IDocument p3, final EdoIntegrationSubsystemErrorCode p4, final SessionContext p5);
    
    protected void processExchangeError(final DMSShardsClientSession session, final T operationContext, final IEdoIntegrationExchangeOperationUnit operationUnit, final IDocument document, final IProcessableException error, final SessionContext ctx) {
        for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : this.getOperationConfig().getExchangeErrorProcessing()) {
            if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                final List<String> sendPrepareErrors = configuration.getExchangeErrorCategories();
                if (sendPrepareErrors.contains(error.getReason())) {
                    for (final IEdoIntegrationExchangeUnit childUnit : operationUnit.getChildren()) {
                        if (childUnit instanceof final IEdoIntegrationExchangeCounteragentUnit counteragentUnit) {
                            if (!StringUtils.isNotBlank(counteragentUnit.getObjectId())) {
                                continue;
                            }
                            final IEdoIntegrationExchangeItem itemRaw = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getObjectId(), ctx);
                            if (itemRaw == null) {
                                continue;
                            }
                            final IEdoIntegrationExchangeItemProcessingWrapper item = new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw);
                            final IObjectModel counteragentContainer = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit.getCounteragentContainerId(), ctx);
                            final Map<String, Object> args = new HashMap<String, Object>();
                            this.prepareProcessingEnvironments(session, operationContext, configuration, args, ctx);
                            final IEdoIntegrationPackageProcessingContext processingContext = new EdoIntegrationPackageProcessingContext(null);
                            if (this.processResponseForDocument(session, operationContext, processingContext, configuration, args, null, document, counteragentContainer, ctx)) {
                                item.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                                item.setStateCategory(configuration.getStateCategory());
                            }
                            ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item.getItem(), ctx);
                        }
                    }
                    return;
                }
                continue;
            }
        }
        for (final IEdoIntegrationInboundDocumentProcessingConfiguration processorConfiguration : this.getOperationConfig().getDefaultErrorProcessing()) {
            if (processorConfiguration instanceof final IEdoIntegrationOutboundDocumentRemoteStatusProcessingOperationConfiguration configuration) {
                for (final IEdoIntegrationExchangeUnit childUnit2 : operationUnit.getChildren()) {
                    if (childUnit2 instanceof final IEdoIntegrationExchangeCounteragentUnit counteragentUnit2) {
                        if (!StringUtils.isNotBlank(counteragentUnit2.getObjectId())) {
                            continue;
                        }
                        final IEdoIntegrationExchangeItem itemRaw2 = (IEdoIntegrationExchangeItem)ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit2.getObjectId(), ctx);
                        if (itemRaw2 == null) {
                            continue;
                        }
                        final IEdoIntegrationExchangeItemProcessingWrapper item2 = new EdoIntegrationExchangeItemLocalProcessingWrapper(itemRaw2);
                        final IObjectModel counteragentContainer2 = ServiceReg.getObjectModelService().getObjectModel(session, counteragentUnit2.getCounteragentContainerId(), ctx);
                        final Map<String, Object> args2 = new HashMap<String, Object>();
                        this.prepareProcessingEnvironments(session, operationContext, configuration, args2, ctx);
                        final IEdoIntegrationPackageProcessingContext processingContext2 = new EdoIntegrationPackageProcessingContext(null);
                        if (this.processResponseForDocument(session, operationContext, processingContext2, configuration, args2, null, document, counteragentContainer2, ctx)) {
                            item2.setLocalStateProcessingStep(IEdoIntegrationInboundDataExchangeItem.ProcessingStep.proceed);
                            item2.setStateCategory(configuration.getStateCategory());
                        }
                        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item2.getItem(), ctx);
                    }
                }
            }
        }
    }
    
    public abstract boolean processResponseForDocument(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final IEdoIntegrationPackageProcessingContext p2, final IEdoIntegrationInboundDocumentProcessingConfiguration p3, final Map<String, Object> p4, final IEdoIntegrationInboundDataExchangeItem p5, final IDocument p6, final IObjectModel p7, final SessionContext p8);
    
    public void processOutgoingAttributes(final DMSShardsClientSession session, final T operationContext, final IDocument document, final IEdoIntegrationExchangeCounteragentUnit exchangeUnit, final TernaryConsumer<String, EdoIntegrationDocumentAttributeDataType, Object> targetWriter, final Map<String, Object> executionArguments, final SessionContext ctx) {
        if (this.getOperationConfig() != null) {
            final List<IEdoIntegrationAttributeMapping> mappingRules = this.getOperationConfig().getMapping();
            for (final IEdoIntegrationAttributeMapping mappingRule : mappingRules) {
                if (mappingRule instanceof final IEdoIntegrationOutboundAttributeMapping rule) {
                    final IEdoIntegrationAttributesByAttributeResolver resolver = this.createAttributeMappingResolver(rule, this);
                    resolver.resolve(session, operationContext, (documentSpec, attrName, attrType) -> {
                        if ("cstm_attr_type_date_time".equals(attrType)) {
                            return document.getValueAsDate(attrName);
                        }
                        else {
                            return document.getValueAsString(attrName);
                        }
                    }, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationAttributeMappingByFixedValue rule2) {
                    final IEdoIntegrationAttributesByFixedValueResolver resolver2 = this.createFixedValueResolver(rule2, this);
                    resolver2.resolve(session, operationContext, targetWriter, ctx);
                }
                else if (mappingRule instanceof final IEdoIntegrationOutboundMappingByLinkedObject rule3) {
                    final IEdoIntegrationAttributesByLinkedObjectResolver resolver3 = this.createByLinkedObjectMappingResolver(rule3, this);
                    resolver3.resolve(session, (IIntegrationOperationContext)operationContext, (documentSpec, attrName, attrType) -> {
                        if (documentSpec == AttributeValueSourceTypes.TEMPORARY && "counteragent_container".equals(attrName)) {
                            if (exchangeUnit != null && exchangeUnit.getCounteragentContainerId() != null) {
                                return (List<Object>)ServiceReg.getObjectModelService().getObjectModel(session, exchangeUnit.getCounteragentContainerId(), ctx);
                            }
                            else {
                                return Collections.emptyList();
                            }
                        }
                        else if (documentSpec == AttributeValueSourceTypes.TEMPORARY && "counteragent".equals(attrName)) {
                            if (exchangeUnit != null && StringUtils.isNotBlank(exchangeUnit.getCounteragentId())) {
                                return (List<Object>)ServiceReg.getObjectModelService().getObjectModel(session, exchangeUnit.getCounteragentId(), ctx);
                            }
                            else {
                                return Collections.emptyList();
                            }
                        }
                        else {
                            return (List<Object>)Arrays.asList(document.getValueAsString(attrName));
                        }
                    }, targetWriter, ctx);
                }
                else {
                    if (!(mappingRule instanceof IEdoIntegrationAttributeMappingByOperationContextAttribute)) {
                        throw new ServiceException(String.format("Unknown rule type %s", mappingRule.getClass().getName()));
                    }
                    final IEdoIntegrationAttributeMappingByOperationContextAttribute rule4 = (IEdoIntegrationAttributeMappingByOperationContextAttribute)mappingRule;
                    final IEdoIntegrationAttributesByOperationContextAttributeResolver resolver4 = this.createByOperationContextAttributeResolver(rule4, this);
                    resolver4.resolve(session, operationContext, targetWriter, ctx);
                }
            }
        }
    }
    
    protected void markExchangeItemAs(final DMSShardsClientSession session, final IEdoIntegrationExchangeItem item, final EdoIntegrationProcessingState state, final SessionContext ctx) {
        item.setLocalState((state == null) ? EdoIntegrationProcessingState.NONE.name() : state.name());
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)item, ctx);
    }
}
