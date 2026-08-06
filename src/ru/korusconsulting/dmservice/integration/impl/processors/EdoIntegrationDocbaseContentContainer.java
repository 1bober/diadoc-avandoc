// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.io.FileInputStream;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.utils.ServiceReg;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.commons.lang.StringUtils;
import java.nio.file.Path;
import ru.korusconsulting.service.objectmodel.documents.IContent;

public class EdoIntegrationDocbaseContentContainer extends EdoIntegrationAbstractContentContainer
{
    private final IContent content;
    private final String name;
    private final String externalId;
    private final Path tempDirPath;
    
    public EdoIntegrationDocbaseContentContainer(final String uid, final IContent content, final byte[] signature, final String certificateThumbprint, final Path tempDirPath) {
        super(uid);
        this.content = content;
        this.name = StringUtils.trimToEmpty(content.getName());
        this.externalId = content.getExternalId();
        this.setSignature(signature, certificateThumbprint);
        this.tempDirPath = tempDirPath;
    }
    
    @Override
    public InputStream getContentStream(final DMSShardsClientSession session) throws IOException {
        if (this.getContent() == null) {
            return InputStream.nullInputStream();
        }
        Path tempDir = this.getTempDirPath();
        if (!Files.exists(tempDir, new LinkOption[0])) {
            tempDir = Files.createDirectories(tempDir, (FileAttribute<?>[])new FileAttribute[0]);
        }
        if (!Files.isDirectory(tempDir, new LinkOption[0])) {
            throw new IOException(String.format("Could not create temporary directory: %s while fetching content %s", tempDir.toAbsolutePath().toString(), this.getContent().getObjectId()));
        }
        final Path tempFile = Files.createTempFile(tempDir, "tmp", this.content.getObjectId(), (FileAttribute<?>[])new FileAttribute[0]);
        if (Files.exists(tempFile, new LinkOption[0])) {
            Files.delete(tempFile);
        }
        ServiceReg.getContentService().fetchContent(session, this.content.getObjectId(), tempFile.toAbsolutePath().toString(), (SessionContext)null);
        if (!Files.isRegularFile(tempFile, new LinkOption[0])) {
            throw new IOException(String.format("Failed to get content file to temporary %s while fetching content %s", tempFile.toAbsolutePath().toString(), this.getContent().getObjectId()));
        }
        return new FileInputStream(tempFile.toFile());
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public IContent getContent() {
        return this.content;
    }
    
    public Path getTempDirPath() {
        return this.tempDirPath;
    }
    
    @Override
    public long getFileSize() {
        return (this.content == null) ? 0L : this.content.getSize();
    }
    
    @Override
    public Object getExternalId() {
        return this.externalId;
    }
}
