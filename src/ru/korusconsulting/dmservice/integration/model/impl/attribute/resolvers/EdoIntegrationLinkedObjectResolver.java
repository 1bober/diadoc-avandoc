// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.model.impl.attribute.resolvers;

import java.util.Arrays;
import ru.wstack.net.client.contentserver.ICollection;
import java.util.Collections;
import ru.korusconsulting.service.shards.DMSShardsManager;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationQuerySelectCondition;
import org.apache.logging.log4j.util.Supplier;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationAttributeSelectCondition;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationDocumentSelectCondition;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeValueSourceTypes;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import java.util.Collection;
import ru.korusconsulting.service.integration.IEdoIntegrationAttributesResolverFactory;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationSelectObjectRule;
import org.apache.logging.log4j.Logger;

public class EdoIntegrationLinkedObjectResolver
{
    private static Logger logger;
    private final IEdoIntegrationSelectObjectRule config;
    private final IEdoIntegrationAttributesResolverFactory factory;
    private final Collection<IObjectModel> fixedLinked;
    
    public EdoIntegrationLinkedObjectResolver(final IEdoIntegrationSelectObjectRule config, final Collection<IObjectModel> fixedLinked, final IEdoIntegrationAttributesResolverFactory factory) {
        this.config = config;
        this.factory = factory;
        this.fixedLinked = fixedLinked;
    }
    
    public IEdoIntegrationSelectObjectRule getConfig() {
        return this.config;
    }
    
    protected IEdoIntegrationAttributesResolverFactory getFactory() {
        return this.factory;
    }
    
    public List<IObjectModel> getObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final SessionContext ctx) {
        final List<IObjectModel> result = new ArrayList<IObjectModel>();
        final List<IObjectModel> newList = ContainerSelect.parceValue(this.getConfig().getContainerSelectType()).getObjects(session, operationContext, parentAttributeValuesGetter, this.config, ctx);
        if (newList != null) {
            result.addAll(newList);
        }
        if (this.fixedLinked != null) {
            result.addAll(this.fixedLinked);
        }
        return result;
    }
    
    static {
        EdoIntegrationLinkedObjectResolver.logger = LogManager.getLogger();
    }
    
    enum ContainerSelect permits EdoIntegrationLinkedObjectResolver$ContainerSelect$1, EdoIntegrationLinkedObjectResolver$ContainerSelect$2, EdoIntegrationLinkedObjectResolver$ContainerSelect$3, EdoIntegrationLinkedObjectResolver$ContainerSelect$4, EdoIntegrationLinkedObjectResolver$ContainerSelect$5
    {
        QUERY("query") {
            @Override
            protected List<IObjectModel> getAllObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
                String query = config.getContainerSelectQuery();
                if (StringUtils.isNotBlank(query)) {
                    String[] attrNames = ru.korusconsulting.service.integration.StringUtils.getParameterNames(query, "${", "}");
                    if (attrNames != null && attrNames.length > 0) {
                        final Map<String, String> attrValues = new HashMap<String, String>();
                        for (final String attrName : attrNames) {
                            String attrValue;
                            if (attrName.startsWith("document.")) {
                                attrValue = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.TITLE, attrName.substring(9), "string");
                            }
                            else {
                                attrValue = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.SUBJECT, attrName, "string");
                            }
                            attrValues.put(attrName, StringUtils.trimToEmpty(attrValue));
                        }
                        query = ru.korusconsulting.service.integration.StringUtils.resolveString(query, "${", "}", (Map)attrValues);
                    }
                    attrNames = ru.korusconsulting.service.integration.StringUtils.getParameterNames(query, "@{", "}");
                    if (attrNames != null && attrNames.length > 0) {
                        final Map<String, String> attrValues = new HashMap<String, String>();
                        for (final String attrName : attrNames) {
                            final String[] functionParams = ru.korusconsulting.service.integration.StringUtils.getParameterNames(attrName, "CURRENTDATETIME(", ")");
                            if (functionParams != null && functionParams.length > 0) {
                                attrValues.put(attrName, new SimpleDateFormat(functionParams[0]).format(Calendar.getInstance().getTime()));
                            }
                        }
                        query = ru.korusconsulting.service.integration.StringUtils.resolveString(query, "@{", "}", (Map)attrValues);
                    }
                    final String queryF = query;
                    operationContext.getLogWriter().trace(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> String.format("Run counteragent container select query [%s]", queryF)));
                    final List<IObjectModel> result = new ArrayList<IObjectModel>();
                    try (final ICollection cscoll = session.getClientSession(DMSShardsManager.getShardNameByContext(ctx)).selectQuery(query)) {
                        while (cscoll.next()) {
                            final String modelId = cscoll.getString((String)cscoll.getAttrNames().get(0));
                            final IObjectModel model = ServiceReg.getObjectModelService().getObjectModel(session, modelId, ctx);
                            result.add(model);
                        }
                    }
                    return result;
                }
                operationContext.getLogWriter().warn(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> "Select counteragent container with empty select query"));
                return Collections.emptyList();
            }
        }, 
        ATTRIBUTE("attribute") {
            @Override
            protected List<IObjectModel> getAllObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
                final String attrName = config.getContainerSelectAttribute();
                if (StringUtils.isBlank(attrName)) {
                    operationContext.getLogWriter().warn(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> String.format("Intergation. Document %s. Incorrect configuration. Counteragent select type is attribute and [attribute name] parameter is null", ctx.get("__objectId"))));
                    return Collections.emptyList();
                }
                final Object tmp = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.SUBJECT, attrName, "string");
                List<String> list;
                List a = null;
                if (tmp instanceof final List list2) {
                    a = (list = list2);
                }
                else {
                    list = Arrays.asList(String.valueOf(tmp));
                }
                final List<String> modelIds = list;
                return modelIds.stream().map(a -> ServiceReg.getObjectModelService().getObjectModel(session, a, ctx)).toList();
            }
        }, 
        USER_FILIAL("user_filial") {
            @Override
            protected List<IObjectModel> getAllObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
                if (StringUtils.isNotBlank(ctx.getString("__sessionFilialId"))) {
                    return Arrays.asList(ServiceReg.getObjectModelService().getObjectModel(session, ctx.getString("__sessionFilialId"), ctx));
                }
                if (StringUtils.isNotBlank(ctx.getString("__orgPositionId"))) {
                    final String[] f = ServiceReg.getOrgStructureService().getOrgPositionFilial(session, ctx.getString("__orgPositionId"), ctx);
                    if (f != null && f.length > 0) {
                        return Arrays.asList(ServiceReg.getObjectModelService().getObjectModel(session, f[0], ctx));
                    }
                }
                return Collections.emptyList();
            }
        }, 
        COUNTERAGENT("counteragent") {
            @Override
            protected List<IObjectModel> getAllObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
                final Object a = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.TEMPORARY, "counteragent", "object");
                if (a instanceof final IObjectModel b) {
                    return Arrays.asList(b);
                }
                return Collections.emptyList();
            }
        }, 
        COUNTERAGENT_CONTAINER("counteragent_container") {
            @Override
            protected List<IObjectModel> getAllObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
                final Object a = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.TEMPORARY, "counteragent_container", "object");
                if (a instanceof final IObjectModel b) {
                    return Arrays.asList(b);
                }
                return Collections.emptyList();
            }
        };
        
        private String type;
        
        private ContainerSelect(final String type) {
            this.type = type;
        }
        
        private String getType() {
            return this.type;
        }
        
        public static ContainerSelect parceValue(final String value) {
            for (final ContainerSelect a : values()) {
                if (a.getType().equals(value)) {
                    return a;
                }
            }
            return ContainerSelect.ATTRIBUTE;
        }
        
        protected abstract List<IObjectModel> getAllObjects(final DMSShardsClientSession p0, final IIntegrationOperationContext p1, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> p2, final IEdoIntegrationSelectObjectRule p3, final SessionContext p4);
        
        public List<IObjectModel> getObjects(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final TernaryFunction<AttributeValueSourceTypes, String, String, Object> parentAttributeValuesGetter, final IEdoIntegrationSelectObjectRule config, final SessionContext ctx) {
            final List<IObjectModel> result = new ArrayList<IObjectModel>();
            final List<IObjectModel> allObjects = this.getAllObjects(session, operationContext, parentAttributeValuesGetter, config, ctx);
            for (final IObjectModel o : allObjects) {
                if (config.getSelectConditions() != null) {
                    Boolean complexCheckResult = null;
                    for (final IEdoIntegrationDocumentSelectCondition condition : config.getSelectConditions()) {
                        if (condition instanceof IEdoIntegrationAttributeSelectCondition) {
                            complexCheckResult = condition.check(session, (a, b) -> {
                                operationContext.getLogWriter().trace(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> String.format("Get [%s] attribute value for object %s. Result is [%s]", b, o.getObjectId(), o.getValueAsString(b))));
                                return o.getValueAsString(b);
                            }, complexCheckResult, ctx);
                        }
                        else {
                            if (!(condition instanceof IEdoIntegrationQuerySelectCondition)) {
                                continue;
                            }
                            complexCheckResult = condition.check(session, (a, b) -> {
                                if (StringUtils.isBlank(b)) {
                                    return "";
                                }
                                else {
                                    final Map<String, String> paramsMap = new HashMap<String, String>();
                                    final String[] parameterNames;
                                    final String[] paramNames = parameterNames = ru.korusconsulting.service.integration.StringUtils.getParameterNames(b, "${", "}");
                                    int i = 0;
                                    for (int length = parameterNames.length; i < length; ++i) {
                                        final String paramName = parameterNames[i];
                                        final String[] paramNameParts = StringUtils.split(paramName, ".");
                                        if (paramNameParts.length == 2) {
                                            if ("counteragent_container".equals(paramNameParts[0])) {
                                                final Object counteragentContainer = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.TEMPORARY, "counteragent_container", null);
                                                if (counteragentContainer instanceof final IObjectModel counteragentContainerObject) {
                                                    paramsMap.put(paramName, Objects.toString(counteragentContainerObject.getValueAsString(paramNameParts[1]), ""));
                                                }
                                            }
                                            else if ("counteragent".equals(paramNameParts[0])) {
                                                final Object counteragent = parentAttributeValuesGetter.apply(AttributeValueSourceTypes.TEMPORARY, "counteragent", null);
                                                if (counteragent instanceof final IObjectModel counteragentObject) {
                                                    paramsMap.put(paramName, Objects.toString(counteragentObject.getValueAsString(paramNameParts[1]), ""));
                                                }
                                            }
                                            else {
                                                paramsMap.put(paramName, Objects.toString(o.getValueAsString(paramNameParts[1]), ""));
                                            }
                                        }
                                        else if (paramNameParts.length == 1) {
                                            paramsMap.put(paramName, Objects.toString(o.getValueAsString(paramName), ""));
                                        }
                                    }
                                    final String query = ru.korusconsulting.service.integration.StringUtils.resolveString(b, "${", "}", (Map)paramsMap);
                                    operationContext.getLogWriter().trace(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> String.format("Execure query [%s]", query)));
                                    final String queryResult = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
                                    operationContext.getLogWriter().trace(EdoIntegrationLinkedObjectResolver.logger, (Supplier<String>)(() -> String.format("Result of query is [%s]", queryResult)));
                                    return queryResult;
                                }
                            }, complexCheckResult, ctx);
                        }
                    }
                    if (complexCheckResult != null && !complexCheckResult) {
                        continue;
                    }
                    result.add(o);
                }
            }
            return result;
        }
    }
}
