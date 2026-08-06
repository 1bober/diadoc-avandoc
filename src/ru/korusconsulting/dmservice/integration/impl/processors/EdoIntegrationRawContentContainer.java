// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.InputStream;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.nio.file.Path;

public class EdoIntegrationRawContentContainer extends EdoIntegrationAbstractContentContainer
{
    private final Path filepath;
    private final String name;
    
    public EdoIntegrationRawContentContainer(final String uid, final String name, final Path filepath, final byte[] signature, final String certificateThumbprint) {
        super(uid);
        this.name = name;
        this.filepath = filepath;
        this.setSignature(signature, certificateThumbprint);
    }
    
    public Path getFilepath() {
        return this.filepath;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public InputStream getContentStream(final DMSShardsClientSession session) throws IOException {
        try {
            return (this.filepath != null && Files.isRegularFile(this.filepath, new LinkOption[0])) ? new FileInputStream(this.filepath.toFile()) : InputStream.nullInputStream();
        }
        catch (final FileNotFoundException e) {
            return InputStream.nullInputStream();
        }
    }
    
    @Override
    public long getFileSize() {
        return (this.filepath == null) ? 0L : this.filepath.toFile().length();
    }
    
    @Override
    public Object getExternalId() {
        return null;
    }
}
