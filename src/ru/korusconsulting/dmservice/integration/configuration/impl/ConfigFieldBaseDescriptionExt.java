// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import java.util.ArrayList;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.Optional;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.List;

public class ConfigFieldBaseDescriptionExt<T>
{
    private final String cstmIdTag;
    private String cstmNameTag;
    private final List<ConfigFieldBaseDescriptionExt<T>> linked;
    private final String xmlIdTag;
    private String xmlGroupTag;
    private final ICstmConfigAdapter.FieldType fieldType;
    private final Optional<Function<T, Object>> readFunction;
    private final Optional<BiConsumer<T, Object>> writeFunction;
    private final Optional<Consumer<T>> clearFunction;
    private final boolean checkerField;
    private final Optional<TernaryFunction<T, Generator, SessionContext, Boolean>> marshalFunction;
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType) {
        this(cstmIdTag, xmlIdTag, fieldType, null, null);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction) {
        this(cstmIdTag, xmlIdTag, fieldType, readFunction, writeFunction, null, null);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction) {
        this(cstmIdTag, xmlIdTag, fieldType, readFunction, writeFunction, clearFunction, null);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction, final TernaryFunction<T, Generator, SessionContext, Boolean> marshalFunction) {
        this(cstmIdTag, null, xmlIdTag, null, fieldType, readFunction, writeFunction, clearFunction, marshalFunction, false);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final boolean checkerField) {
        this(cstmIdTag, cstmNameTag, xmlIdTag, null, fieldType, readFunction, writeFunction, null, null, checkerField);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction) {
        this(cstmIdTag, cstmNameTag, xmlIdTag, null, fieldType, readFunction, writeFunction, null, null, false);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction) {
        this(cstmIdTag, cstmNameTag, xmlIdTag, null, fieldType, readFunction, writeFunction, clearFunction, null, false);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final String xmlGroupTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction) {
        this(cstmIdTag, cstmNameTag, xmlIdTag, xmlGroupTag, fieldType, readFunction, writeFunction, clearFunction, null, false);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final String xmlGroupTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction, final boolean checkerField) {
        this(cstmIdTag, cstmNameTag, xmlIdTag, xmlGroupTag, fieldType, readFunction, writeFunction, clearFunction, null, checkerField);
    }
    
    public ConfigFieldBaseDescriptionExt(final String cstmIdTag, final String cstmNameTag, final String xmlIdTag, final String xmlGroupTag, final ICstmConfigAdapter.FieldType fieldType, final Function<T, Object> readFunction, final BiConsumer<T, Object> writeFunction, final Consumer<T> clearFunction, final TernaryFunction<T, Generator, SessionContext, Boolean> marshalFunction, final boolean checkerField) {
        this.linked = new ArrayList<ConfigFieldBaseDescriptionExt<T>>();
        this.cstmIdTag = cstmIdTag;
        this.cstmNameTag = cstmNameTag;
        this.xmlIdTag = xmlIdTag;
        this.xmlGroupTag = xmlGroupTag;
        this.fieldType = fieldType;
        this.readFunction = Optional.ofNullable(readFunction);
        this.writeFunction = Optional.ofNullable(writeFunction);
        this.clearFunction = Optional.ofNullable(clearFunction);
        this.marshalFunction = Optional.ofNullable(marshalFunction);
        this.checkerField = checkerField;
    }
    
    public String getCstmIdTag() {
        return this.cstmIdTag;
    }
    
    public String getCstmNameTag() {
        return this.cstmNameTag;
    }
    
    public String getXmlIdTag() {
        return this.xmlIdTag;
    }
    
    public String getXmlGroupTag() {
        return this.xmlGroupTag;
    }
    
    public ICstmConfigAdapter.FieldType getFieldType() {
        return this.fieldType;
    }
    
    public Optional<Function<T, Object>> getReadFunction() {
        return this.readFunction;
    }
    
    public Optional<BiConsumer<T, Object>> getWriteFunction() {
        return this.writeFunction;
    }
    
    public Optional<Consumer<T>> getClearFunction() {
        return this.clearFunction;
    }
    
    public List<ConfigFieldBaseDescriptionExt<T>> getLinked() {
        return this.linked;
    }
    
    public void setLinked(final ConfigFieldBaseDescriptionExt<T> linked) {
        if (linked != null) {
            this.getLinked().add(linked);
        }
    }
    
    public Optional<TernaryFunction<T, Generator, SessionContext, Boolean>> getMarshalFunction() {
        return this.marshalFunction;
    }
    
    public boolean isCheckerField() {
        return this.checkerField;
    }
}
