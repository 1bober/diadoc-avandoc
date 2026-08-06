// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import ru.korusconsulting.dmservice.integration.tracing.IntegrationTracing;
import java.util.function.Consumer;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigOperationWrapper;
import java.util.Collections;
import java.util.ArrayList;
import ru.korusconsulting.dmservice.integration.configuration.impl.CstmOperationWrapper;
import ru.korusconsulting.service.objectmodel.cstm.ICstmContentGroup;
import ru.korusconsulting.service.objectmodel.cstm.ICstmTypeAttr;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.cstm.ICstmType;
import java.util.function.BiConsumer;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.dmservice.integration.TernaryFunction;
import ru.korusconsulting.dmservice.integration.configuration.impl.ConfigFieldBaseDescriptionExt;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.operations.IEdoIntegrationDocumentOperationConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.TypeDescriptionElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.AttributeDescriptionElementConfiguration;
import ru.korusconsulting.dmservice.integration.configuration.impl.DictionaryElementConfiguration;
import java.util.function.Supplier;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.io.Serializable;

public interface ICstmConfigAdapter extends Serializable
{
    public static final boolean INTEGRATION_CONFIG_DEBUG_MODE = Boolean.getBoolean(IntegrationTracing.INTEGRATION_CSTM_DEBUG_FLAG);
    public static final String CONFIG_ID_XML_ATTRIBUTE = "config_id";
    public static final String CONFIG_UID_XML_ATTRIBUTE = "config_uid";
    
    ICstmConfigAdapter getParent();
    
    void unmarshal(final DMSShardsClientSession p0, final ICstmComponent p1, final Generator p2, final SessionContext p3);
    
    void unmarshal(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
     <T extends ConfigElement> T marshal(final DMSShardsClientSession p0, final String p1, final Generator p2, final Supplier<T> p3, final SessionContext p4);
    
    ICstmConfigProperties getConfigProperies();
    
    String getXmlTag();
    
    void setXmlTag(final String p0);
    
    String getConfigId();
    
    void setConfigId(final String p0);
    
    String getConfigComponentId();
    
    void setConfigComponentId(final String p0);
    
    default IDictionaryElementConfiguration createDictionaryElementConfiguration(final String id, final String value, final String label, final String xmlTag) {
        if (id == null && value == null && label == null) {
            return null;
        }
        final DictionaryElementConfiguration dic = new DictionaryElementConfiguration(this);
        dic.setXmlTag(xmlTag);
        dic.setId(id);
        dic.setValue(value);
        dic.setLabel(label);
        return dic;
    }
    
    default IDictionaryElementConfiguration createDictionaryElementConfiguration(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        if ("simple".equals(element.getAttributeValue("config_type"))) {
            final DictionaryElementConfiguration dic = new DictionaryElementConfiguration(this);
            dic.unmarshal(session, element, ctx);
            return dic;
        }
        if ("attribute".equals(element.getAttributeValue("config_type"))) {
            final AttributeDescriptionElementConfiguration dic2 = new AttributeDescriptionElementConfiguration(this);
            dic2.unmarshal(session, element, ctx);
            return dic2;
        }
        if ("type".equals(element.getAttributeValue("config_type"))) {
            final TypeDescriptionElementConfiguration dic3 = new TypeDescriptionElementConfiguration(this);
            dic3.unmarshal(session, element, ctx);
            return dic3;
        }
        return null;
    }
    
    default ConfigElement unmarshalSimpleValue(final String tag, final String value) {
        final ConfigElement el = new ConfigElement();
        el.setTagName(tag);
        el.setElementValue(value);
        return el;
    }
    
    default String marshalSimpleValue(final ConfigElement el, final String tag) {
        final ConfigElement vEl = el.getFirstChild(tag);
        if (vEl != null) {
            return vEl.getElementValue();
        }
        return null;
    }
    
    default ICstmConfigAdapter getRoot() {
        if (this.getParent() == null) {
            return this;
        }
        return this.getParent().getRoot();
    }
    
    IEdoIntegrationOperationConfiguration getDocumentConfig();
    
    IEdoIntegrationDocumentOperationConfiguration getDocumentOperationConfig();
    
    ITypeDescriptionElementConfiguration getType();
    
    ITypeDescriptionElementConfiguration getRootType();
    
    default <T> void marshalField(final DMSShardsClientSession session, final T caller, final Function<T, Object> rf, final ConfigFieldBaseDescriptionExt<T> fd, final ConfigElement config, final Generator generator, final SessionContext ctx) {
        if (fd.getMarshalFunction().isPresent() && !fd.getMarshalFunction().get().apply(caller, generator, ctx)) {
            return;
        }
        switch (fd.getFieldType()) {
            case bool:
            case integer: {
                final ConfigElement configElement = new ConfigElement();
                configElement.setTagName(fd.getXmlIdTag());
                configElement.setElementValue(String.valueOf(rf.apply(caller)));
                config.addChild(configElement);
                break;
            }
            case text:
            case cstm_object_link:
            case internal_string: {
                final String val = rf.apply(caller);
                if (StringUtils.isNotBlank(val)) {
                    final ConfigElement configElement2 = new ConfigElement();
                    configElement2.setTagName(fd.getXmlIdTag());
                    configElement2.setElementValue(val);
                    config.addChild(configElement2);
                }
                break;
            }
            case dictionary_value:
            case content_group:
            case cstm_type:
            case type_name:
            case cstm_attribute:
            case attribute_name: {
                final Object value = rf.apply(caller);
                if (value instanceof final IDictionaryElementConfiguration decValue) {
                    final ConfigElement configElement3 = decValue.marshal(session, fd.getXmlIdTag(), generator, ConfigElement::new, ctx);
                    if (configElement3 != null) {
                        config.addChild(configElement3);
                    }
                    else {
                        if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                            throw new IllegalArgumentException(String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                        }
                        this.getLogger().warn(() -> String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                    }
                }
                else {
                    this.getLogger().debug(() -> String.format("Value of %s attribute returns %s", fd.getXmlIdTag(), (value == null) ? "NULL" : value.getClass().getName()));
                }
                break;
            }
            case dictionary_list:
            case content_group_list: {
                final ConfigElement configElement = new ConfigElement();
                configElement.setTagName(fd.getXmlGroupTag());
                final Object value2 = rf.apply(caller);
                if (value2 instanceof final List lValue) {
                    for (final Object v : lValue) {
                        final ConfigElement sEl = ((IDictionaryElementConfiguration)v).marshal(session, fd.getXmlIdTag(), generator, ConfigElement::new, ctx);
                        if (sEl != null) {
                            configElement.addChild(sEl);
                        }
                        else {
                            if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                                throw new IllegalArgumentException(String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                            }
                            this.getLogger().warn(() -> String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                        }
                    }
                }
                else {
                    this.getLogger().debug(() -> String.format("Value of %s attribute returns %s", fd.getXmlIdTag(), (value2 == null) ? "NULL" : value2.getClass().getName()));
                }
                config.addChild(configElement);
                break;
            }
            case cstm_values_list: {
                final ConfigElement configElement = new ConfigElement();
                configElement.setTagName(fd.getXmlGroupTag());
                final Object value2 = rf.apply(caller);
                if (value2 instanceof final List lValue) {
                    for (final Object v : lValue) {
                        if (v instanceof final ICstmConfigAdapter configAdapter) {
                            final ConfigElement sEl2 = configAdapter.marshal(session, fd.getXmlIdTag(), generator, ConfigElement::new, ctx);
                            if (sEl2 != null) {
                                configElement.addChild(sEl2);
                            }
                            else {
                                if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                                    throw new IllegalArgumentException(String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                                }
                                this.getLogger().warn(() -> String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                            }
                        }
                    }
                }
                else {
                    this.getLogger().debug(() -> String.format("Value of %s attribute returns %s", fd.getXmlIdTag(), (value2 == null) ? "NULL" : value2.getClass().getName()));
                }
                config.addChild(configElement);
                break;
            }
            case list: {
                final ConfigElement configElement = new ConfigElement();
                configElement.setTagName(fd.getXmlGroupTag());
                final Object value2 = rf.apply(caller);
                if (value2 instanceof final List lValue) {
                    for (final Object v : lValue) {
                        final ConfigElement sEl = new ConfigElement();
                        sEl.setTagName(fd.getXmlIdTag());
                        sEl.setElementValue(String.valueOf(v));
                        configElement.addChild(sEl);
                    }
                }
                else {
                    this.getLogger().debug(() -> String.format("Value of %s attribute returns %s", fd.getXmlIdTag(), (value2 == null) ? "NULL" : value2.getClass().getName()));
                }
                config.addChild(configElement);
                break;
            }
            case cstm_linked_list: {
                final ConfigElement configElement = new ConfigElement();
                configElement.setTagName(fd.getXmlGroupTag());
                final Object value2 = rf.apply(caller);
                if (value2 instanceof final List lValue) {
                    for (final Object v : lValue) {
                        if (v instanceof final ICstmConfigAdapter configAdapter) {
                            final ConfigElement sEl2 = configAdapter.marshal(session, fd.getXmlIdTag(), generator, ConfigElement::new, ctx);
                            if (sEl2 != null) {
                                configElement.addChild(sEl2);
                            }
                            else {
                                if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                                    throw new IllegalArgumentException(String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                                }
                                this.getLogger().warn(() -> String.format("Marshaling of %s attribute returns null", fd.getXmlIdTag()));
                            }
                        }
                    }
                }
                else {
                    this.getLogger().debug(() -> String.format("Value of %s attribute returns %s", fd.getXmlIdTag(), (value2 == null) ? "NULL" : value2.getClass().getName()));
                }
                config.addChild(configElement);
                break;
            }
            default: {
                this.getLogger().error(() -> String.format("Unmarshal to XML. Unprocessed field type %s", fd.getFieldType().name()));
                break;
            }
        }
    }
    
    default <T> void marshalFields(final DMSShardsClientSession session, final T caller, final List<ConfigFieldBaseDescriptionExt<T>> fieldDefinitions, final ConfigElement config, final Generator generator, final SessionContext ctx) {
        for (final ConfigFieldBaseDescriptionExt<T> fd : fieldDefinitions) {
            fd.getReadFunction().ifPresent(rf -> this.marshalField(session, caller, rf, fd, config, generator, ctx));
        }
    }
    
    default <T> void unmarshalField(final DMSShardsClientSession session, final T caller, final BiConsumer<T, Object> wf, final ConfigFieldBaseDescriptionExt<T> fd, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        switch (fd.getFieldType()) {
            case cstm_type: {
                final String id = component.getValueAsString(fd.getCstmIdTag());
                if (StringUtils.isNotBlank(id)) {
                    final ICstmType type = (ICstmType)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
                    if (type != null) {
                        final ITypeDescriptionElementConfiguration typeConfig = new TypeDescriptionElementConfiguration(this);
                        typeConfig.setId(id);
                        typeConfig.setLabel(type.getName());
                        typeConfig.setValue(type.getCode());
                        typeConfig.setCstmLoaded(true);
                        typeConfig.setCstmType(type);
                        typeConfig.setXmlTag(fd.getXmlIdTag());
                        typeConfig.setConfigId("dictionary_element");
                        ServiceReg.getCstmService().loadTypeAttributes(session, type, ctx);
                        wf.accept(caller, typeConfig);
                        this.getConfigProperies().setType(typeConfig);
                    }
                }
                else {
                    for (final ConfigFieldBaseDescriptionExt<T> sFd : fd.getLinked()) {
                        if (FieldType.type_name == sFd.getFieldType()) {
                            final String typeName = component.getValueAsString(sFd.getCstmIdTag());
                            if (StringUtils.isNotBlank(typeName)) {
                                final ITypeDescriptionElementConfiguration typeConfig2 = new TypeDescriptionElementConfiguration(this);
                                typeConfig2.setValue(typeName);
                                typeConfig2.setLabel(typeName);
                                typeConfig2.setCstmLoaded(false);
                                typeConfig2.setXmlTag(fd.getXmlIdTag());
                                typeConfig2.setConfigId("dictionary_element");
                                wf.accept(caller, typeConfig2);
                                this.getConfigProperies().setType(typeConfig2);
                                break;
                            }
                            continue;
                        }
                    }
                }
                break;
            }
            case type_name: {
                for (final ConfigFieldBaseDescriptionExt<T> sFd2 : fd.getLinked()) {
                    if (FieldType.type_name == sFd2.getFieldType()) {
                        final String typeName2 = component.getValueAsString(sFd2.getCstmIdTag());
                        if (StringUtils.isNotBlank(typeName2)) {
                            final ITypeDescriptionElementConfiguration typeConfig3 = new TypeDescriptionElementConfiguration(this);
                            typeConfig3.setValue(typeName2);
                            typeConfig3.setCstmLoaded(false);
                            typeConfig3.setXmlTag(fd.getXmlIdTag());
                            typeConfig3.setConfigId("dictionary_element");
                            wf.accept(caller, typeConfig3);
                            this.getConfigProperies().setType(typeConfig3);
                            break;
                        }
                        continue;
                    }
                }
                break;
            }
            case cstm_attribute: {
                final String id = component.getValueAsString(fd.getCstmIdTag());
                if (StringUtils.isNotBlank(id)) {
                    boolean isTitle = false;
                    for (final ConfigFieldBaseDescriptionExt<T> sFd3 : fd.getLinked()) {
                        if (FieldType.title_flag == sFd3.getFieldType()) {
                            isTitle = component.getValueAsBoolean(sFd3.getCstmIdTag());
                            break;
                        }
                    }
                    final ITypeDescriptionElementConfiguration typeConfig = isTitle ? this.getRootType() : this.getType();
                    if (typeConfig == null) {
                        if (ICstmConfigAdapter.INTEGRATION_CONFIG_DEBUG_MODE) {
                            throw new IllegalArgumentException(String.format("Can't use cstm attribute id (%s/%s) while config type for class %s is null", id, component.getValueAsString(fd.getCstmNameTag()), this.getClass().getName()));
                        }
                    }
                    else if (typeConfig.isCstmLoaded() && typeConfig.getCstmType() != null) {
                        final ICstmTypeAttr attr = typeConfig.getCstmType().getAllAttributes().get(component.getValueAsString(fd.getCstmIdTag()));
                        if (attr != null) {
                            final IAttributeDescriptionElementConfiguration attributeConfig = new AttributeDescriptionElementConfiguration(this);
                            attributeConfig.setId(id);
                            attributeConfig.setLabel(attr.getName());
                            attributeConfig.setValue(attr.getCode());
                            attributeConfig.setObjectTypeCode(typeConfig.getValue());
                            attributeConfig.setObjectTypeId(typeConfig.getId());
                            attributeConfig.setRepeating(attr.isRepeating());
                            attributeConfig.setAttributeDataType(attr.getAttrType());
                            attributeConfig.setCstmLoaded(true);
                            attributeConfig.setXmlTag(fd.getXmlIdTag());
                            attributeConfig.setConfigId("dictionary_element");
                            wf.accept(caller, attributeConfig);
                        }
                    }
                }
                else {
                    for (final ConfigFieldBaseDescriptionExt<T> sFd : fd.getLinked()) {
                        if (FieldType.attribute_name == sFd.getFieldType()) {
                            final String attributeName = component.getValueAsString(sFd.getCstmIdTag());
                            if (StringUtils.isNotBlank(attributeName)) {
                                final IAttributeDescriptionElementConfiguration attributeConfig = new AttributeDescriptionElementConfiguration(this);
                                attributeConfig.setValue(attributeName);
                                attributeConfig.setLabel(attributeName);
                                attributeConfig.setCstmLoaded(false);
                                attributeConfig.setXmlTag(fd.getXmlIdTag());
                                attributeConfig.setConfigId("dictionary_element");
                                wf.accept(caller, attributeConfig);
                                break;
                            }
                            continue;
                        }
                    }
                }
                break;
            }
            case attribute_name: {
                for (final ConfigFieldBaseDescriptionExt<T> sFd2 : fd.getLinked()) {
                    if (FieldType.type_name == sFd2.getFieldType()) {
                        final String typeName2 = component.getValueAsString(sFd2.getCstmIdTag());
                        if (StringUtils.isNotBlank(typeName2)) {
                            final IAttributeDescriptionElementConfiguration attributeConfig2 = new AttributeDescriptionElementConfiguration(this);
                            attributeConfig2.setValue(typeName2);
                            attributeConfig2.setCstmLoaded(false);
                            attributeConfig2.setXmlTag(fd.getXmlIdTag());
                            attributeConfig2.setConfigId("dictionary_element");
                            wf.accept(caller, attributeConfig2);
                            break;
                        }
                        continue;
                    }
                }
                break;
            }
            case internal_string: {
                break;
            }
            case bool: {
                wf.accept(caller, component.getValueAsBoolean(fd.getCstmIdTag()));
                break;
            }
            case integer: {
                wf.accept(caller, component.getValueAsInt(fd.getCstmIdTag()));
                break;
            }
            case text: {
                wf.accept(caller, component.getValueAsString(fd.getCstmIdTag()));
                break;
            }
            case dictionary_value: {
                wf.accept(caller, this.createDictionaryElementConfiguration(null, component.getValueAsString(fd.getCstmIdTag()), component.getValueAsString(fd.getCstmNameTag()), fd.getXmlIdTag()));
                break;
            }
            case content_group: {
                final String id = component.getValueAsString(fd.getCstmIdTag());
                if (StringUtils.isNotBlank(id)) {
                    final ICstmContentGroup cstmContentGroup = (ICstmContentGroup)ServiceReg.getObjectModelService().getObjectModel(session, id, ctx);
                    if (cstmContentGroup != null) {
                        wf.accept(caller, this.createDictionaryElementConfiguration(cstmContentGroup.getObjectId(), cstmContentGroup.getCode(), cstmContentGroup.getName(), fd.getXmlIdTag()));
                    }
                    else {
                        wf.accept(caller, null);
                    }
                }
                else {
                    wf.accept(caller, null);
                }
                break;
            }
            case cstm_values_list: {
                Object a = null;
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final Object value3 = component.getValue(fd.getCstmIdTag());
                if (value3 instanceof final List idsList) {
                    final Iterator iterator6 = idsList.iterator();
                    while (iterator6.hasNext()) {
                        a = iterator6.next();
                        final ICstmComponent cstmObject = (ICstmComponent)ServiceReg.getObjectModelService().getObjectModel(session, a.toString(), ctx);
                        if (cstmObject != null) {
                            wf.accept(caller, new CstmOperationWrapper(session, cstmObject, fd.getXmlGroupTag(), fd.getXmlIdTag(), generator, ctx));
                        }
                    }
                }
                break;
            }
            case content_group_list: {
                final Object a;
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final Object value4 = component.getValue(fd.getCstmIdTag());
                if (value4 instanceof final List idsList) {
                    final List<String> codesGroup = new ArrayList<String>();
                    for (final Object a2 : idsList) {
                        final ICstmContentGroup cstmContentGroup2 = (ICstmContentGroup)ServiceReg.getObjectModelService().getObjectModel(session, a2.toString(), ctx);
                        if (cstmContentGroup2 != null && !codesGroup.contains(cstmContentGroup2.getCode())) {
                            codesGroup.add(cstmContentGroup2.getCode());
                            wf.accept(caller, this.createDictionaryElementConfiguration(cstmContentGroup2.getObjectId(), cstmContentGroup2.getCode(), cstmContentGroup2.getName(), fd.getXmlIdTag()));
                        }
                    }
                }
                break;
            }
            case dictionary_list: {
                final Object a;
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final Object value5 = component.getValue(fd.getCstmIdTag());
                if (value5 instanceof final List idsList) {
                    List<Object> emptyList = null;
                    Label_1854: {
                        if (StringUtils.isNotBlank(fd.getCstmNameTag())) {
                            final Object value6 = component.getValue(fd.getCstmNameTag());
                            if (value6 instanceof List) {
                                final List cList = emptyList = (List<Object>)value6;
                                break Label_1854;
                            }
                        }
                        emptyList = Collections.emptyList();
                    }
                    final List namesList = emptyList;
                    for (int i = 0; i < idsList.size(); ++i) {
                        final Object id2 = idsList.get(i);
                        if (id2 != null) {
                            final Object value = (i >= namesList.size()) ? "" : namesList.get(i);
                            wf.accept(caller, this.createDictionaryElementConfiguration(id2.toString(), id2.toString(), (value == null) ? "" : value.toString(), fd.getXmlIdTag()));
                        }
                    }
                }
                break;
            }
            case list: {
                final Object a;
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final Object value7 = component.getValue(fd.getCstmIdTag());
                if (value7 instanceof final List valuesList) {
                    for (final Object value2 : valuesList) {
                        wf.accept(caller, value2);
                    }
                }
                break;
            }
            case cstm_linked_list: {
                final Object a;
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                for (final ICstmComponent componentElement : component.getChildren(fd.getCstmIdTag())) {
                    wf.accept(caller, new CstmOperationWrapper(session, componentElement, fd.getXmlGroupTag(), fd.getXmlIdTag(), generator, ctx));
                }
                break;
            }
            case cstm_object_link: {
                if (!component.getChildren(fd.getCstmIdTag()).isEmpty()) {
                    final ICstmComponent filter = component.getChildren(fd.getCstmIdTag()).get(0);
                    wf.accept(caller, filter.getUID() + "_" + filter.getComponentType());
                    break;
                }
                break;
            }
            default: {
                this.getLogger().error(() -> String.format("Marshal CSTM. Unprocessed field type %s", fd.getFieldType().name()));
                break;
            }
        }
    }
    
    default <T> void unmarshalFields(final DMSShardsClientSession session, final T caller, final List<ConfigFieldBaseDescriptionExt<T>> fieldDefinitions, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        if (fieldDefinitions != null && !fieldDefinitions.isEmpty()) {
            for (final ConfigFieldBaseDescriptionExt<T> fd : fieldDefinitions) {
                fd.getWriteFunction().ifPresent(wf -> this.unmarshalField(session, caller, wf, fd, component, generator, ctx));
            }
        }
    }
    
    default <T> void unmarshalField(final DMSShardsClientSession session, final T caller, final BiConsumer<T, Object> wf, final ConfigFieldBaseDescriptionExt<T> fd, final ConfigElement element, final SessionContext ctx) {
        switch (fd.getFieldType()) {
            case bool: {
                final ConfigElement configElement = element.getFirstChild(fd.getXmlIdTag());
                if (configElement != null) {
                    wf.accept(caller, Boolean.valueOf(configElement.getElementValue()));
                }
                break;
            }
            case integer: {
                final ConfigElement configElement = element.getFirstChild(fd.getXmlIdTag());
                if (configElement != null) {
                    wf.accept(caller, Integer.valueOf(configElement.getElementValue()));
                }
                break;
            }
            case text:
            case cstm_object_link:
            case internal_string: {
                final ConfigElement configElement = element.getFirstChild(fd.getXmlIdTag());
                if (configElement != null) {
                    wf.accept(caller, configElement.getElementValue());
                }
                break;
            }
            case dictionary_value:
            case content_group:
            case cstm_type:
            case type_name:
            case cstm_attribute:
            case attribute_name: {
                final ConfigElement configElement = element.getFirstChild(fd.getXmlIdTag());
                if (configElement != null) {
                    wf.accept(caller, this.createDictionaryElementConfiguration(session, configElement, ctx));
                }
                break;
            }
            case dictionary_list:
            case content_group_list: {
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final ConfigElement fieldRootElement = element.getFirstChild(fd.getXmlGroupTag());
                if (fieldRootElement != null) {
                    for (final ConfigElement fieldChildElement : fieldRootElement.getChildren(fd.getXmlIdTag())) {
                        wf.accept(caller, this.createDictionaryElementConfiguration(session, fieldChildElement, ctx));
                    }
                }
                break;
            }
            case cstm_values_list: {
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final ConfigElement fieldRootElement = element.getFirstChild(fd.getXmlGroupTag());
                if (fieldRootElement != null) {
                    for (final ConfigElement fieldChildElement : fieldRootElement.getChildren()) {
                        wf.accept(caller, new ConfigOperationWrapper(session, fieldChildElement, ctx));
                    }
                }
                break;
            }
            case list: {
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final ConfigElement fieldRootElement = element.getFirstChild(fd.getXmlGroupTag());
                if (fieldRootElement != null) {
                    for (final ConfigElement fieldChildElement : fieldRootElement.getChildren(fd.getXmlIdTag())) {
                        wf.accept(caller, fieldChildElement.getElementValue());
                    }
                }
                break;
            }
            case cstm_linked_list: {
                fd.getClearFunction().ifPresent(a -> a.accept(caller));
                final ConfigElement fieldRootElement = element.getFirstChild(fd.getXmlGroupTag());
                if (fieldRootElement != null) {
                    for (final ConfigElement fieldChildElement : fieldRootElement.getChildren()) {
                        wf.accept(caller, new ConfigOperationWrapper(session, fieldChildElement, ctx));
                    }
                }
                break;
            }
            default: {
                this.getLogger().error(() -> String.format("marshal to XML. Unprocessed field type %s", fd.getFieldType().name()));
                break;
            }
        }
    }
    
    default <T> void unmarshalFields(final DMSShardsClientSession session, final T caller, final List<ConfigFieldBaseDescriptionExt<T>> fieldDefinitions, final ConfigElement element, final SessionContext ctx) {
        for (final ConfigFieldBaseDescriptionExt<T> fd : fieldDefinitions) {
            fd.getWriteFunction().ifPresent(wf -> {
                try {
                    this.unmarshalField(session, caller, wf, fd, element, ctx);
                }
                catch (final NullPointerException e) {
                    new ServiceException(String.format("Exception while marshalField [%s]", fd.getCstmIdTag()), (Throwable)e);
                    throw;
                }
            });
        }
    }
    
    Logger getLogger();
    
    String getName();
    
    void setName(final String p0);
    
    default void unmarshalForCheck(final DMSShardsClientSession session, final ConfigElement oConfig, final SessionContext ctx) {
        throw new UnsupportedOperationException(String.format("unmarshalForCheck not supported by %s", this.getClass().getName()));
    }
    
    default ITypeDescriptionElementConfiguration getTypeDescription(final DMSShardsClientSession session, final String typeName, final String xmlTag, final SessionContext ctx) {
        final String query = String.format("select r_object_id from nt_cstm_type where ssa_code = '%s'", typeName);
        final String typeId = ServiceReg.getQueryService().getFirstValueFromQuery(session, query, ctx);
        if (StringUtils.isNotBlank(typeId)) {
            final ICstmType type = (ICstmType)ServiceReg.getObjectModelService().getObjectModel(session, typeId, ctx);
            if (type != null) {
                final ITypeDescriptionElementConfiguration typeConfig = new TypeDescriptionElementConfiguration(this);
                typeConfig.setId(typeId);
                typeConfig.setLabel(type.getName());
                typeConfig.setValue(type.getCode());
                typeConfig.setCstmLoaded(true);
                typeConfig.setCstmType(type);
                typeConfig.setXmlTag(xmlTag);
                typeConfig.setConfigId("dictionary_element");
                ServiceReg.getCstmService().loadTypeAttributes(session, type, ctx);
                return typeConfig;
            }
        }
        final ITypeDescriptionElementConfiguration typeConfig2 = new TypeDescriptionElementConfiguration(this);
        typeConfig2.setValue(typeName);
        typeConfig2.setLabel(typeName);
        typeConfig2.setCstmLoaded(false);
        typeConfig2.setXmlTag(xmlTag);
        typeConfig2.setConfigId("dictionary_element");
        this.getConfigProperies().setType(typeConfig2);
        return typeConfig2;
    }
    
    void setInactive(final boolean p0);
    
    boolean getInactive();
    
    String getComponentUid();
    
    public enum FieldType
    {
        bool, 
        integer, 
        text, 
        dictionary_value, 
        cstm_attribute_link, 
        dictionary_list, 
        list, 
        cstm_linked_list, 
        cstm_values_list, 
        xml, 
        custom, 
        cstm_object_link, 
        cstm_attribute, 
        cstm_type, 
        type_name, 
        content_group_list, 
        content_group, 
        internal_string, 
        title_flag, 
        attribute_name;
    }
}
