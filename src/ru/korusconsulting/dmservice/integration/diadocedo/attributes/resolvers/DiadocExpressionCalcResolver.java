// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.attributes.resolvers;

import net.objecthunter.exp4j.Expression;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.Set;
import net.objecthunter.exp4j.ExpressionBuilder;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.model.DiadocEdoInboundObjectValue;
import org.apache.commons.lang.StringUtils;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.ArrayList;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.ws.common.model.IInboundDocument;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.dmservice.integration.attributes.resolvers.AbstractComplexResolver;

public class DiadocExpressionCalcResolver extends AbstractComplexResolver
{
    private static final String VARIABLES_PATTERN = "\\$\\{([^}]*)\\}";
    
    protected Logger createLogger() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public Object resolve(final DMSShardsClientSession session, final String key, final IDocument document, final IInboundDocument incoming, final ConfigElement attrConfig, final ConfigElement systemConfig, final String inboundAttr, final SessionContext context) {
        try {
            class Var
            {
                private final String name = clearVarName;
                private double value;
                
                public Var() {
                }
                
                public String getName() {
                    return this.name;
                }
                
                public double getValue() {
                    return this.value;
                }
                
                public void setValue(final double value) {
                    this.value = value;
                }
            }
            final ArrayList<Var> variables = new ArrayList<Var>();
            final HashSet<String> variablesList = new HashSet<String>();
            final Pattern pattern = Pattern.compile("\\$\\{([^}]*)\\}");
            final Matcher matcher = pattern.matcher(inboundAttr);
            String expression = inboundAttr;
            while (matcher.find()) {
                for (int i = 0; i < matcher.groupCount(); ++i) {
                    final String group = matcher.group(i);
                    final String clearVarName = group.substring(2, group.length() - 1).trim();
                    variables.add(new Var());
                    variablesList.add(clearVarName);
                    expression = StringUtils.replace(expression, group, clearVarName);
                }
            }
            for (final Var v : variables) {
                Object newValue = incoming.getValue(v.getName());
                if (newValue instanceof final DiadocEdoInboundObjectValue complexValue) {
                    newValue = complexValue.getValue();
                }
                if (newValue instanceof final Double doubleValue) {
                    v.setValue(doubleValue);
                }
                else if (newValue instanceof final Integer integerValue) {
                    v.setValue(integerValue);
                }
                else {
                    if (newValue instanceof final String stringValue) {
                        if (StringUtils.isNotBlank(stringValue)) {
                            v.setValue(Double.parseDouble(stringValue));
                            continue;
                        }
                    }
                    v.setValue(0.0);
                }
            }
            final Expression e = new ExpressionBuilder(expression).variables((Set)variablesList).build();
            for (final Var v2 : variables) {
                e.setVariable(v2.getName(), v2.getValue());
            }
            final double result = e.evaluate();
            return String.valueOf(result);
        }
        catch (final Exception exp) {
            this.getLogger().error("Error parsing expression {} for document {}", (Object)inboundAttr, (Object)((incoming == null) ? "null" : incoming.getId()), (Object)exp);
            return "0";
        }
    }
}
