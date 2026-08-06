// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.korusconsulting.dmservice.integration.Utils;
import ru.korusconsulting.service.objectmodel.documents.IContentContainer;
import ru.korusconsulting.service.objectmodel.documents.IContent;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;
import ru.korusconsulting.service.objectmodel.documents.IRelation;
import java.util.Set;
import ru.wstack.net.client.contentserver.ICollection;
import ru.wstack.net.client.contentserver.IClientSession;
import java.util.Iterator;
import org.apache.logging.log4j.util.Supplier;
import java.util.List;
import java.util.ArrayList;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.HashSet;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationInboundDocumentProcessor;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public abstract class EdoIntegrationInboundDocumentProcessor<T extends IIntegrationOperationContext> extends EdoIntegrationProcessor<T> implements IEdoIntegrationInboundDocumentProcessor<T>
{
    protected void saveArgumentsState(final IEdoIntegrationPackageProcessingContext processingContext, final Map<String, Object> args, final String globalEnvString) {
        final Map<String, Object> savedArgs = processingContext.getArguments();
        if (args != null && globalEnvString != null) {
            final String[] split;
            final String[] globalEnvs = split = StringUtils.split(globalEnvString, ",;");
            for (final String envName : split) {
                final String key = String.format("__%s__environment", envName);
                if (args.containsKey(key)) {
                    savedArgs.put(key, args.get(key));
                }
            }
        }
    }
    
    protected Map<String, Object> loadArgumentsState(final IEdoIntegrationPackageProcessingContext processingContext) {
        final Map<String, Object> savedArgs = processingContext.getArguments();
        final Map<String, Object> result = new HashMap<String, Object>();
        if (savedArgs != null) {
            savedArgs.entrySet().forEach(a -> result.put(a.getKey(), a.getValue()));
        }
        return result;
    }
    
    public void createRelations(final DMSShardsClientSession session, final IIntegrationOperationContext operationContext, final IDocument document, final String integrationMark, final Map<String, String> parentRelations, final Map<String, String> childRelations, final SessionContext ctx) {
        try {
            StringBuilder sb = null;
            if (parentRelations != null) {
                for (final String k : parentRelations.keySet()) {
                    if (StringUtils.isNotBlank(k)) {
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        else {
                            sb.append(",");
                        }
                        sb.append("'").append(k).append("'");
                    }
                }
            }
            if (childRelations != null) {
                for (final String k : childRelations.keySet()) {
                    if (StringUtils.isNotBlank(k)) {
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        else {
                            sb.append(",");
                        }
                        sb.append("'").append(k).append("'");
                    }
                }
            }
            if (sb == null) {
                return;
            }
            final Map<String, String> documents = new HashMap<String, String>();
            final IClientSession clientSession = session.getClientSession(DMSShardsManager.getShardNameByContext(ctx));
            try (final ICollection col = clientSession.selectQuery(String.format("select sida_document, ssa_document_guid from nt_edo_integration_exchange_item where ssa_integration='%s' and ssa_direction='OUT' and ssa_item_type='document' and ssa_document_guid in (%s)", integrationMark, sb.toString()))) {
                while (col.next()) {
                    documents.put(col.getString("ssa_document_guid"), col.getString("sida_document"));
                }
            }
            if (documents.isEmpty()) {
                return;
            }
            Set<String> allreadyDone = new HashSet<String>();
            if (parentRelations != null) {
                for (final Map.Entry<String, String> r : parentRelations.entrySet()) {
                    final String id = documents.get(r.getKey());
                    if (!allreadyDone.contains(id)) {
                        allreadyDone.add(id);
                        final IDocument relDocument = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
                        if (relDocument == null) {
                            continue;
                        }
                        final IRelation relation = ServiceReg.getRelationService().createRelation(session, "relation", ServiceReg.getNLSService().getBundleItem("model_names_bundle", "MSG_DEFAULT_RELATION_TYPE_NAME", ctx), "relation", (String)r.getValue(), "common", relDocument, ctx);
                        if (document.getValue("relations") == null) {
                            document.setValue("relations", (Object)new ArrayList());
                        }
                        ((List)document.getValue("relations")).add(relation);
                    }
                }
            }
            allreadyDone = new HashSet<String>();
            if (childRelations != null) {
                for (final Map.Entry<String, String> r : childRelations.entrySet()) {
                    final String id = documents.get(r.getKey());
                    if (!allreadyDone.contains(id)) {
                        allreadyDone.add(id);
                        final IDocument relDocument = (IDocument)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
                        if (relDocument == null) {
                            continue;
                        }
                        final IRelation relation = ServiceReg.getRelationService().createRelation(session, "relation", ServiceReg.getNLSService().getBundleItem("model_names_bundle", "MSG_DEFAULT_RELATION_TYPE_NAME", ctx), "relation", (String)r.getValue(), "common", relDocument, ctx);
                        if (document.getValue("relations") == null) {
                            document.setValue("relations", (Object)new ArrayList());
                        }
                        ((List)document.getValue("relations")).add(relation);
                    }
                }
            }
        }
        catch (final Throwable e) {
            IIntegrationOperationContext.error(operationContext, this.getLogger(), (Supplier<String>)(() -> "Error created linked object"), e);
        }
    }
    
    protected boolean checkRuleApplicability(final DMSShardsClientSession session, final T operationContext, final IDocument model, final IEdoIntegrationInboundAttachmentProcessingRule attachmentProcessingRule, final String targetPath, final SessionContext ctx) {
        final String filePathRegexp = attachmentProcessingRule.getPathRegexp();
        final String relatedSourceFilenameRegexp = attachmentProcessingRule.getSourceFilenameRegexp();
        final String relatedSourceContentGroup = attachmentProcessingRule.getSourceContentGroupId();
        if (StringUtils.isBlank(filePathRegexp) || "*".equals(filePathRegexp)) {
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Related source filename regexp is empty. Rule with source regexp %s applicable for file %s", filePathRegexp, targetPath)));
            return true;
        }
        if (!StringUtils.isBlank(relatedSourceFilenameRegexp)) {
            final List<String> contentNames = new ArrayList<String>();
            if (StringUtils.isNotBlank(relatedSourceContentGroup)) {
                final IContent content;
                this.fetchContentGroupFiles(session, operationContext, model, relatedSourceContentGroup, content -> contentNames.add(content.getName()), ctx);
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Found related content %s for group %s", contentNames.stream().collect(Collectors.joining(";")), relatedSourceContentGroup)));
            }
            else {
                List<IContent> contents = ServiceReg.getContentService().getDocumentContent(session, model.getObjectId(), ctx);
                if (contents != null) {
                    for (final IContent content : contents) {
                        contentNames.add(content.getName());
                    }
                }
                final Map<String, IContentContainer> containers = ServiceReg.getContentService().getContentContainers(session, model.getObjectId(), ctx);
                if (containers != null) {
                    for (final Map.Entry<String, IContentContainer> containerEntry : containers.entrySet()) {
                        contents = ServiceReg.getContentService().getDocumentContent(session, containerEntry.getValue().getObjectId(), ctx);
                        if (contents != null) {
                            for (final IContent content2 : contents) {
                                contentNames.add(content2.getName());
                            }
                        }
                    }
                }
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Found related content %s (no content group limitation defined)", contentNames.stream().collect(Collectors.joining(";")))));
            }
            for (final String contentName : contentNames) {
                if (Utils.matchDynamicRegex(targetPath, filePathRegexp, relatedSourceFilenameRegexp, contentName)) {
                    IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("File %s match to %s with source regexp %s, releated regexp %s. Rule is applicable", targetPath, contentName, filePathRegexp, relatedSourceFilenameRegexp)));
                    return true;
                }
                IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("File %s not match to %s with source regexp %s, releated regexp %s", targetPath, contentName, filePathRegexp, relatedSourceFilenameRegexp)));
            }
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Rule with source regexp %s not applicable for file %s", filePathRegexp, targetPath)));
            return false;
        }
        final Pattern filePathRegexPattern = Pattern.compile(filePathRegexp);
        final Matcher m = filePathRegexPattern.matcher(targetPath);
        if (m.find()) {
            IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Related source filename regexp is empty. Rule with source regexp %s applicable for file %s", filePathRegexp, targetPath)));
            return true;
        }
        IIntegrationOperationContext.trace(operationContext, this.getLogger(), (Supplier<String>)(() -> String.format("Related source filename regexp is empty. Rule with source regexp %s not applicable for file %s", filePathRegexp, targetPath)));
        return false;
    }
}
