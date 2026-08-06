// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.model;

import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.model.IEdoIntegrationExchangeUnit;

public abstract class EdoIntegrationExchangeUnit implements IEdoIntegrationExchangeUnit
{
    @JsonIgnore
    private static final Logger logger;
    private String objectId;
    private String operationGuid;
    private String name;
    private String documentId;
    private EdoIntegrationItemType itemType;
    private String documentType;
    private String documentTypeName;
    private String documentKindId;
    private String documentKindName;
    private String documentSubkindId;
    private String documentSubkindName;
    private String documentName;
    private String filialId;
    private String filialName;
    @JsonIgnore
    private final Exception creationTraceException;
    @JsonIgnore
    private IEdoIntegrationExchangeItem item;
    private List<AttrLoader<IEdoIntegrationExchangeUnit>> uAttrs;
    private List<AttrLoader<IEdoIntegrationExchangeItem>> attrs;
    
    protected EdoIntegrationExchangeUnit() {
        this.creationTraceException = (Boolean.getBoolean("edointegration.operationcontext.tracing") ? new Exception() : null);
        this.uAttrs = (List<AttrLoader<IEdoIntegrationExchangeUnit>>)Arrays.asList(new AttrLoader(a -> this.setObjectId(a), b -> b.getObjectId()), new AttrLoader(a -> this.setOperationGuid(a), b -> b.getOperationGuid()), new AttrLoader(a -> this.setDocumentId(a), b -> b.getDocumentId()), new AttrLoader(a -> this.setDocumentType(a), b -> b.getDocumentType()), new AttrLoader(a -> this.setDocumentTypeName(a), b -> b.getDocumentTypeName()), new AttrLoader(a -> this.setDocumentKindId(a), b -> b.getDocumentKindId()), new AttrLoader(a -> this.setDocumentKindName(a), b -> b.getDocumentKindName()), new AttrLoader(a -> this.setDocumentSubkindId(a), b -> b.getDocumentSubkindId()), new AttrLoader(a -> this.setDocumentSubkindName(a), b -> b.getDocumentSubkindName()), new AttrLoader(a -> this.setDocumentName(a), b -> b.getDocumentName()), new AttrLoader(a -> this.setFilialId(a), b -> b.getFilialId()), new AttrLoader(a -> this.setFilialName(a), b -> b.getFilialName()), new AttrLoader(a -> this.setOperationGuid(a), b -> b.getOperationGuid()), new AttrLoader(a -> this.setDocumentId(a), b -> b.getDocumentId()), new AttrLoader(a -> this.setDocumentType(a), b -> b.getDocumentType()), new AttrLoader(a -> this.setDocumentTypeName(a), b -> b.getDocumentTypeName()), new AttrLoader(a -> this.setDocumentKindId(a), b -> b.getDocumentKindId()), new AttrLoader(a -> this.setDocumentKindName(a), b -> b.getDocumentKindName()), new AttrLoader(a -> this.setDocumentSubkindId(a), b -> b.getDocumentSubkindId()), new AttrLoader(a -> this.setDocumentSubkindName(a), b -> b.getDocumentSubkindName()), new AttrLoader(a -> this.setDocumentName(a), b -> b.getDocumentName()), new AttrLoader(a -> this.setFilialId(a), b -> b.getFilialId()), new AttrLoader(a -> this.setFilialName(a), b -> b.getFilialName()));
        this.attrs = (List<AttrLoader<IEdoIntegrationExchangeItem>>)Arrays.asList(new AttrLoader(a -> this.setObjectId(a), b -> b.getObjectId()), new AttrLoader(a -> this.setOperationGuid(a), b -> b.getOperationGuid()), new AttrLoader(a -> this.setDocumentId(a), b -> b.getDocumentId()), new AttrLoader(a -> this.setDocumentType(a), b -> b.getDocumentType()), new AttrLoader(a -> this.setDocumentTypeName(a), b -> b.getDocumentTypeName()), new AttrLoader(a -> this.setDocumentKindId(a), b -> b.getDocumentKindId()), new AttrLoader(a -> this.setDocumentKindName(a), b -> b.getDocumentKindName()), new AttrLoader(a -> this.setDocumentSubkindId(a), b -> b.getDocumentSubkindId()), new AttrLoader(a -> this.setDocumentSubkindName(a), b -> b.getDocumentSubkindName()), new AttrLoader(a -> this.setDocumentName(a), b -> b.getDocumentName()), new AttrLoader(a -> this.setFilialId(a), b -> b.getFilialId()), new AttrLoader(a -> this.setFilialName(a), b -> b.getFilialName()), new AttrLoader(a -> this.setOperationGuid(a), b -> b.getOperationGuid()), new AttrLoader(a -> this.setDocumentId(a), b -> b.getDocumentId()), new AttrLoader(a -> this.setDocumentType(a), b -> b.getDocumentType()), new AttrLoader(a -> this.setDocumentTypeName(a), b -> b.getDocumentTypeName()), new AttrLoader(a -> this.setDocumentKindId(a), b -> b.getDocumentKindId()), new AttrLoader(a -> this.setDocumentKindName(a), b -> b.getDocumentKindName()), new AttrLoader(a -> this.setDocumentSubkindId(a), b -> b.getDocumentSubkindId()), new AttrLoader(a -> this.setDocumentSubkindName(a), b -> b.getDocumentSubkindName()), new AttrLoader(a -> this.setDocumentName(a), b -> b.getDocumentName()), new AttrLoader(a -> this.setFilialId(a), b -> b.getFilialId()), new AttrLoader(a -> this.setFilialName(a), b -> b.getFilialName()));
    }
    
    @JsonIgnore
    public final Logger getLogger() {
        return EdoIntegrationExchangeUnit.logger;
    }
    
    @Override
    public final String getObjectId() {
        return this.objectId;
    }
    
    @Override
    public final void setObjectId(final String objectId) {
        this.objectId = objectId;
    }
    
    @Override
    public final String getOperationGuid() {
        return this.operationGuid;
    }
    
    @Override
    public final void setOperationGuid(final String value) {
        this.operationGuid = value;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public final void setName(final String value) {
        this.name = value;
    }
    
    @Override
    public final String getDocumentId() {
        return this.documentId;
    }
    
    @Override
    public final void setDocumentId(final String documentId) {
        this.documentId = documentId;
    }
    
    @Override
    public final EdoIntegrationItemType getItemType() {
        return this.itemType;
    }
    
    @Override
    public final void setItemType(final EdoIntegrationItemType itemType) {
        this.itemType = itemType;
    }
    
    @Override
    public final String getDocumentType() {
        return this.documentType;
    }
    
    @Override
    public final void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }
    
    @Override
    public final String getDocumentTypeName() {
        return this.documentTypeName;
    }
    
    @Override
    public final void setDocumentTypeName(final String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }
    
    @Override
    public final String getDocumentKindId() {
        return this.documentKindId;
    }
    
    @Override
    public final void setDocumentKindId(final String documentKindId) {
        this.documentKindId = documentKindId;
    }
    
    @Override
    public final String getDocumentKindName() {
        return this.documentKindName;
    }
    
    @Override
    public final void setDocumentKindName(final String documentKindName) {
        this.documentKindName = documentKindName;
    }
    
    @Override
    public final String getDocumentSubkindId() {
        return this.documentSubkindId;
    }
    
    @Override
    public final void setDocumentSubkindId(final String documentSubkindId) {
        this.documentSubkindId = documentSubkindId;
    }
    
    @Override
    public final String getDocumentSubkindName() {
        return this.documentSubkindName;
    }
    
    @Override
    public final void setDocumentSubkindName(final String documentSubkindName) {
        this.documentSubkindName = documentSubkindName;
    }
    
    @Override
    public final String getDocumentName() {
        return this.documentName;
    }
    
    @Override
    public final void setDocumentName(final String documentName) {
        this.documentName = documentName;
    }
    
    @Override
    public final String getFilialId() {
        return this.filialId;
    }
    
    @Override
    public final void setFilialId(final String filialId) {
        this.filialId = filialId;
    }
    
    @Override
    public final String getFilialName() {
        return this.filialName;
    }
    
    @Override
    public final void setFilialName(final String filialName) {
        this.filialName = filialName;
    }
    
    @JsonIgnore
    @Override
    public final IEdoIntegrationExchangeItem getItem() {
        return this.item;
    }
    
    @Override
    public final void setItem(final IEdoIntegrationExchangeItem value) {
        this.item = value;
        this.setObjectId((value == null) ? null : value.getObjectId());
    }
    
    public void loadParentAttrs(final IEdoIntegrationExchangeUnit item, final boolean preventNullOverride) {
        for (final AttrLoader<IEdoIntegrationExchangeUnit> attr : this.uAttrs) {
            final String v = attr.getGetter().apply(item);
            if (!preventNullOverride || StringUtils.isNotBlank((CharSequence)v)) {
                attr.getSetter().accept(v);
            }
        }
    }
    
    public void loadAttrs(final IEdoIntegrationExchangeItem item, final boolean preventNullOverride) {
        for (final AttrLoader<IEdoIntegrationExchangeItem> attr : this.attrs) {
            final String v = attr.getGetter().apply(item);
            if (!preventNullOverride || StringUtils.isNotBlank((CharSequence)v)) {
                attr.getSetter().accept(v);
            }
        }
    }
    
    public void commitAttrs(final IEdoIntegrationExchangeItem item) {
        item.setOperationGuid(this.getOperationGuid());
        item.setDocumentId(this.getDocumentId());
        item.setDocumentType(this.getDocumentType());
        item.setDocumentTypeName(this.getDocumentTypeName());
        item.setDocumentKindId(this.getDocumentKindId());
        item.setDocumentKindName(this.getDocumentKindName());
        item.setDocumentSubkindId(this.getDocumentSubkindId());
        item.setDocumentSubkindName(this.getDocumentSubkindName());
        item.setDocumentName(this.getDocumentName());
        item.setFilialId(this.getFilialId());
        item.setFilialName(this.getFilialName());
        item.setOperationGuid(this.getOperationGuid());
        item.setDocumentId(this.getDocumentId());
        item.setDocumentType(this.getDocumentType());
        item.setDocumentTypeName(this.getDocumentTypeName());
        item.setDocumentKindId(this.getDocumentKindId());
        item.setDocumentKindName(this.getDocumentKindName());
        item.setDocumentSubkindId(this.getDocumentSubkindId());
        item.setDocumentSubkindName(this.getDocumentSubkindName());
        item.setDocumentName(this.getDocumentName());
        item.setFilialId(this.getFilialId());
        item.setFilialName(this.getFilialName());
    }
    
    @Override
    public String toString() {
        try {
            return this.getClass().getName() + "\n\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString((Object)this) + "\n\nCreation point\n\n" + ExceptionUtils.getStackTrace((Throwable)this.creationTraceException);
        }
        catch (final Throwable e) {
            e.printStackTrace();
            return super.toString();
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
