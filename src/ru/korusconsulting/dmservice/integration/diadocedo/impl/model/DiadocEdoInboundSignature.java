// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.util.StringJoiner;
import java.nio.file.Path;
import java.util.Iterator;
import java.io.IOException;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import org.apache.commons.io.FileUtils;
import java.nio.file.FileSystems;
import com.google.protobuf.Descriptors;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Diadoc.Api.Proto.SignatureInfoProtos;
import java.io.File;
import Diadoc.Api.Proto.Events.DiadocMessage_GetApiProtos;
import ru.korusconsulting.ws.common.model.IInboundSignature;

public class DiadocEdoInboundSignature extends DiadocEdoInboundObject implements IInboundSignature
{
    private DiadocMessage_GetApiProtos.Entity entity;
    private File file;
    private SignatureInfoProtos.SignatureInfo si;
    
    @Override
    protected Logger createLog() {
        return LogManager.getLogger((Class)this.getClass());
    }
    
    public DiadocMessage_GetApiProtos.Entity getEntity() {
        return this.entity;
    }
    
    public DiadocEdoInboundSignature(final String boxId, final DiadocMessage_GetApiProtos.Entity entity, final SignatureInfoProtos.SignatureInfo si, final byte[] content) {
        super(boxId);
        Objects.requireNonNull(entity, "Incoming message cant be null");
        this.entity = entity;
        this.si = si;
        if (entity.getAllFields() != null) {
            for (final Map.Entry<Descriptors.FieldDescriptor, Object> fd : entity.getAllFields().entrySet()) {
                final String name = fd.getKey().getName();
                final DiadocEdoInboundObjectValue val = DiadocEdoInboundObjectValue.parse(fd.getValue());
                this.addValue(name, val);
                this.getLogger().trace("Incoming document property [{}] = [{}]", (Object)name, (Object)val.toString());
            }
        }
        try {
            final Path tmpDir = Files.createTempDirectory(FileSystems.getDefault().getPath(FileUtils.getTempDirectoryPath(), new String[0]), "content", (FileAttribute<?>[])new FileAttribute[0]);
            final Path f = Files.createFile(FileSystems.getDefault().getPath(tmpDir.toString(), "sig_" + entity.getEntityId() + ".sig"), (FileAttribute<?>[])new FileAttribute[0]).normalize();
            this.getLogger().trace("Creating temporary content [{}]", (Object)f.toString());
            try (final FileOutputStream fos = new FileOutputStream(f.toFile())) {
                fos.write(content);
            }
            this.file = f.toFile();
        }
        catch (final IOException e) {
            throw new ServiceException((Exception)e);
        }
    }
    
    public String getType() {
        return this.getEntity().getAttachmentType().name();
    }
    
    public String getId() {
        return this.getEntity().getEntityId();
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getSignerName() {
        return new StringJoiner(" ").add(this.si.getFirstName()).add(this.si.getSurname()).toString();
    }
    
    public String getSignerJobTitle() {
        return this.si.getJobTitle();
    }
    
    public String getEntityId() {
        return this.getEntity().getEntityId();
    }
    
    public String getParentEntityId() {
        return this.getEntity().getParentEntityId();
    }
}
