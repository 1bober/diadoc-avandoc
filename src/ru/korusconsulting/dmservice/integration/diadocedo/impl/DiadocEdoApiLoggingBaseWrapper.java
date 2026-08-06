// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl;

import org.apache.logging.log4j.LogManager;
import javax.json.JsonArrayBuilder;
import java.util.Objects;
import javax.json.JsonValue;
import com.google.protobuf.ByteString;
import java.util.Collection;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationLogMethod;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import org.apache.commons.lang.StringUtils;
import java.nio.charset.StandardCharsets;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.integration.IntegrationLogRecord;
import javax.json.JsonObjectBuilder;
import javax.json.Json;
import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.Map;
import Diadoc.Api.DiadocApiResponce;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.apache.logging.log4j.Logger;
import com.google.protobuf.GeneratedMessage;

public class DiadocEdoApiLoggingBaseWrapper<T extends GeneratedMessage>
{
    private static final Logger logger;
    protected final JsonNodeFactory factory;
    
    public DiadocEdoApiLoggingBaseWrapper() {
        this.factory = JsonNodeFactory.instance;
    }
    
    protected Logger getLogger() {
        return DiadocEdoApiLoggingBaseWrapper.logger;
    }
    
    protected final String plainDecode(final DiadocApiResponce<T> responseData) {
        if (responseData.getParsedData() == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("Decoded diadoc request \n");
        final Map<Descriptors.FieldDescriptor, Object> s = responseData.getParsedData().getAllFields();
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> a : s.entrySet()) {
            sb.append(a.getKey().getFullName()).append(":\n");
            this.parseValue(sb, a.getValue());
        }
        return sb.toString();
    }
    
    protected final String jsonDecode(final DiadocApiResponce<T> responseData) {
        if (responseData.getParsedData() == null) {
            return "";
        }
        final JsonObjectBuilder resBuilder = Json.createObjectBuilder();
        final Map<Descriptors.FieldDescriptor, Object> s = responseData.getParsedData().getAllFields();
        for (final Map.Entry<Descriptors.FieldDescriptor, Object> a : s.entrySet()) {
            this.parseValue(resBuilder, a.getKey().getFullName(), a.getValue());
        }
        return resBuilder.build().toString();
    }
    
    protected final void writeResponse(final IntegrationLogRecord.ILogFileBuilder operationLog, final DiadocApiResponce<T> responseData, final SessionContext ctx) {
        if (responseData != null) {
            operationLog.getActiveStage().addFile((responseData.getPage() == 0) ? "response" : String.format("response (%d)", responseData.getPage()), "bin", responseData.getRaw(), ctx);
            if (responseData.getParsedData() != null) {
                operationLog.getActiveStage().addFile((responseData.getPage() == 0) ? "response" : String.format("response (%d)", responseData.getPage()), "json", this.jsonDecode(responseData).getBytes(StandardCharsets.UTF_8), ctx);
            }
        }
    }
    
    protected void writeUrl(final String name, final IntegrationLogRecord.ILogFileBuilder operationLog, final String url, final SessionContext ctx) {
        if (StringUtils.isNotBlank(url)) {
            operationLog.getActiveStage().addFile(StringUtils.isBlank(name) ? "request" : name, "url", url.getBytes(StandardCharsets.UTF_8), ctx);
        }
    }
    
    protected final void writeIntegrationLogEntry(final IDiadocEdoIntegrationServiceInternal service, final IIntegrationLogService.TYPE level, final DiadocEdoIntegrationLogMethod operation, final String message, final String containerPath, final IDiadocEdoOperationContext operationContext, final SessionContext ctx) {
        try {
            service.writeIntegrationLogEntry(level, operation, message, containerPath, (IIntegrationOperationContext)operationContext, ctx);
        }
        catch (final Exception e) {
            this.getLogger().error(String.format("%s. Document %s. Error writing integration log data", service.getLogPrefix(), operationContext.getDocumentId()), (Throwable)e);
        }
    }
    
    protected final void parseValue(final StringBuilder sb, final Object value) {
        if (value instanceof final Descriptors.GenericDescriptor vGd) {
            sb.append(vGd.getFullName());
            sb.append("\n");
        }
        else if (value instanceof final GeneratedMessage vGm) {
            final Map<Descriptors.FieldDescriptor, Object> s = vGm.getAllFields();
            if (s != null) {
                for (final Map.Entry<Descriptors.FieldDescriptor, Object> a : s.entrySet()) {
                    final Collection<?> value2 = a.getValue();
                    if (value2 instanceof Collection) {
                        final Collection<?> col = value2;
                        sb.append(a.getKey().getFullName()).append("\n");
                        this.parseCollection(sb, col);
                    }
                    else {
                        if (a.getValue() == null) {
                            continue;
                        }
                        sb.append(a.getKey().getFullName()).append(": ");
                        this.parseValue(sb, a.getValue());
                        sb.append("\n");
                    }
                }
            }
        }
        else if (value instanceof final String vStr) {
            sb.append(vStr);
            sb.append("\n");
        }
        else if (value instanceof final Boolean vBool) {
            sb.append(String.valueOf(vBool));
            sb.append("\n");
        }
        else if (value instanceof final Integer vInt) {
            sb.append(String.valueOf(vInt));
            sb.append("\n");
        }
        else if (value instanceof final Double vDou) {
            sb.append(String.valueOf(vDou));
            sb.append("\n");
        }
        else if (value instanceof final Long vLong) {
            sb.append(String.valueOf(vLong));
            sb.append("\n");
        }
        else if (value instanceof final ByteString vBs) {
            sb.append(String.format("...byteString with size %s follow...%n", vBs.size()));
        }
        else if (value instanceof Collection) {
            final Collection<?> vCol = (Collection<?>)value;
            this.parseCollection(sb, vCol);
        }
        else if (value != null) {
            if (value.getClass().isPrimitive()) {
                sb.append("\n").append(String.valueOf(value));
                sb.append("\n");
            }
            else {
                sb.append("\n\n").append(value.getClass().getName()).append("\nskipped\n");
            }
        }
    }
    
    protected final void parseValue(final JsonObjectBuilder parent, final String name, final Object value) {
        if (value instanceof final Descriptors.GenericDescriptor vGd) {
            parent.add(name, vGd.getFullName());
        }
        else if (value instanceof final GeneratedMessage vGm) {
            final JsonObjectBuilder resBuilder = Json.createObjectBuilder();
            final Map<Descriptors.FieldDescriptor, Object> s = vGm.getAllFields();
            if (s != null) {
                for (final Map.Entry<Descriptors.FieldDescriptor, Object> a : s.entrySet()) {
                    final Collection<?> value2 = a.getValue();
                    if (value2 instanceof Collection) {
                        final Collection<?> col = value2;
                        final JsonArrayBuilder resBuilder2 = Json.createArrayBuilder();
                        this.parseCollection(resBuilder2, col);
                        resBuilder.add(a.getKey().getFullName(), resBuilder2);
                    }
                    else {
                        if (a.getValue() == null) {
                            continue;
                        }
                        this.parseValue(resBuilder, a.getKey().getFullName(), a.getValue());
                    }
                }
            }
            parent.add(name, resBuilder);
        }
        else if (value instanceof final String vStr) {
            parent.add(name, (JsonValue)Json.createValue(vStr));
        }
        else if (value instanceof final Boolean vBool) {
            parent.add(name, (JsonValue)Json.createValue(String.valueOf(vBool)));
        }
        else if (value instanceof final Integer vInt) {
            parent.add(name, (JsonValue)Json.createValue((int)vInt));
        }
        else if (value instanceof final Double vDou) {
            parent.add(name, (JsonValue)Json.createValue((double)vDou));
        }
        else if (value instanceof final Long vLong) {
            parent.add(name, (JsonValue)Json.createValue((long)vLong));
        }
        else if (value instanceof final ByteString vBs) {
            parent.add(name, (JsonValue)Json.createValue(String.format("...byteString with size %s follow...%n", vBs.size())));
        }
        else if (value instanceof Collection) {
            final Collection<?> vCol = (Collection<?>)value;
            final JsonArrayBuilder resBuilder3 = Json.createArrayBuilder();
            this.parseCollection(resBuilder3, vCol);
            parent.add(name, resBuilder3);
        }
        else if (value != null) {
            if (value.getClass().isPrimitive()) {
                parent.add(name, Objects.toString(value, ""));
            }
            else {
                parent.add(name, value.getClass().getName());
            }
        }
    }
    
    protected final void parseValue(final JsonArrayBuilder parent, final Object value) {
        if (value instanceof final Descriptors.GenericDescriptor vGd) {
            parent.add(vGd.getFullName());
        }
        else if (value instanceof final GeneratedMessage vGm) {
            final JsonObjectBuilder resBuilder = Json.createObjectBuilder();
            final Map<Descriptors.FieldDescriptor, Object> s = vGm.getAllFields();
            if (s != null) {
                for (final Map.Entry<Descriptors.FieldDescriptor, Object> a : s.entrySet()) {
                    final Collection<?> value2 = a.getValue();
                    if (value2 instanceof Collection) {
                        final Collection<?> col = value2;
                        final JsonArrayBuilder resBuilder2 = Json.createArrayBuilder();
                        this.parseCollection(resBuilder2, col);
                        resBuilder.add(a.getKey().getFullName(), resBuilder2);
                    }
                    else {
                        if (a.getValue() == null) {
                            continue;
                        }
                        this.parseValue(resBuilder, a.getKey().getFullName(), a.getValue());
                    }
                }
            }
            parent.add(resBuilder);
        }
        else if (value instanceof final String vStr) {
            parent.add((JsonValue)Json.createValue(vStr));
        }
        else if (value instanceof final Boolean vBool) {
            parent.add((JsonValue)Json.createValue(String.valueOf(vBool)));
        }
        else if (value instanceof final Integer vInt) {
            parent.add((JsonValue)Json.createValue((int)vInt));
        }
        else if (value instanceof final Double vDou) {
            parent.add((JsonValue)Json.createValue((double)vDou));
        }
        else if (value instanceof final Long vLong) {
            parent.add((JsonValue)Json.createValue((long)vLong));
        }
        else if (value instanceof final ByteString vBs) {
            parent.add((JsonValue)Json.createValue(String.format("...byteString with size %s follow...%n", vBs.size())));
        }
        else if (value instanceof Collection) {
            final Collection<?> vCol = (Collection<?>)value;
            final JsonArrayBuilder resBuilder3 = Json.createArrayBuilder();
            parent.add(resBuilder3);
            this.parseCollection(resBuilder3, vCol);
        }
        else if (value != null) {
            if (value.getClass().isPrimitive()) {
                parent.add(Objects.toString(value, ""));
            }
            else {
                parent.add(value.getClass().getName());
            }
        }
    }
    
    protected final void parseCollection(final JsonArrayBuilder parent, final Collection<?> col) {
        col.forEach(a -> this.parseValue(parent, a));
    }
    
    protected final void parseCollection(final StringBuilder sb, final Collection<?> col) {
        col.forEach(a -> this.parseValue(sb, a));
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
