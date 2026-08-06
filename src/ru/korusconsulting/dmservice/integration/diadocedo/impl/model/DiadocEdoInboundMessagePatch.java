// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.Date;
import java.io.IOException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.integration.diadocedo.DiadocEdoModuleProperties;
import java.util.Iterator;
import com.google.protobuf.Descriptors;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.ws.common.model.IInboundMessagePatch;

public class DiadocEdoInboundMessagePatch extends DiadocEdoInboundObject implements IInboundMessagePatch
{
    private DiadocMessage_GetApiProtos.MessagePatch messagePatch;
    private File logData;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocEdoInboundMessagePatch(final String boxId, final DiadocMessage_GetApiProtos.MessagePatch messagePatch) {
        super(boxId);
        Objects.requireNonNull(messagePatch, "Incoming message cant be null");
        this.messagePatch = messagePatch;
        if (messagePatch.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : messagePatch.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                if (this.getLogger().isTraceEnabled()) {
                    this.getLogger().trace("Incoming document property [{}] = [{}]", (Object)name, (Object)val.toString());
                }
            }
        }
    }
    
    public String getId() {
        return this.messagePatch.getPatchId();
    }
    
    protected DiadocMessage_GetApiProtos.MessagePatch getMessagePatch() {
        return this.messagePatch;
    }
    
    public File storeLogData() {
        if (this.logData != null) {
            return this.logData;
        }
        if (this.getMessagePatch() == null) {
            this.getLogger().warn("Message is null. Nothing to store for integration log.");
            return null;
        }
        final String repoPath = DiadocEdoModuleProperties.getProperty("backup_dir");
        if (StringUtils.isBlank(repoPath)) {
            this.getLogger().warn("Config value {} not defined in {}. Integration file backup disabled.", (Object)"backup_dir", (Object)"app.properties");
            return null;
        }
        final File repo = new File(repoPath);
        if (!repo.exists()) {
            this.getLogger().warn("Integration backup folder {} not exists. Integration file backup disabled.", (Object)repoPath);
            return null;
        }
        final Date recordDate = Calendar.getInstance().getTime();
        final String recordDateFilenamePart = new SimpleDateFormat("YYYYMMDD_hhmm").format(recordDate);
        final File recordFile = new File(repo, String.format("%s_%s.bin", recordDateFilenamePart, this.getMessagePatch().getPatchId()));
        try (final FileOutputStream fos = new FileOutputStream(recordFile)) {
            fos.write(this.getMessagePatch().toByteArray());
            this.logData = recordFile;
        }
        catch (final IOException e) {
            this.getLogger().error(String.format("Error while writing integration file backup %s", recordFile.getAbsolutePath()), (Throwable)e);
        }
        return this.logData;
    }
    
    public String getMessageId() {
        return this.getMessagePatch().getMessageId();
    }
}
