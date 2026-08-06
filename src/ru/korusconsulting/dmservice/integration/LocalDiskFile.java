// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.io.File;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.objectmodel.documents.IContent;
import ru.korusconsulting.service.commons.SessionContext;
import java.nio.file.Path;

public class LocalDiskFile
{
    private Path content;
    private boolean persistent;
    
    public LocalDiskFile(final Path content, final boolean persistent) {
        this.content = content;
        this.persistent = persistent;
    }
    
    public LocalDiskFile() {
    }
    
    public Path getContent() {
        return this.content;
    }
    
    public void setContent(final Path content) {
        this.content = content;
    }
    
    public boolean isPersistent() {
        return this.persistent;
    }
    
    public void setPersistent(final boolean persistent) {
        this.persistent = persistent;
    }
    
    public IContent createDocbaseContent(final String documentId, final SessionContext ctx) {
        final IContent docbaseContent = ServiceReg.getContentService().createContent(this.getContent().toAbsolutePath().toString(), this.getContent().toFile().length(), documentId, documentId, ctx);
        docbaseContent.setFilePath(this.getContent().toAbsolutePath().toString());
        docbaseContent.setName(this.getContent().getName(this.getContent().getNameCount() - 1).toString());
        return docbaseContent;
    }
    
    public String getPersistentFilePath() {
        return (this.persistent && this.content != null) ? this.content.toAbsolutePath().toString() : null;
    }
    
    public String getFilePath() {
        return (this.content != null) ? this.content.toAbsolutePath().toString() : null;
    }
    
    public File toFile() {
        return (this.content != null) ? this.content.toFile() : null;
    }
    
    public static LocalDiskFile empty() {
        return new LocalDiskFile(null, false);
    }
    
    public LocalDiskFile copy() {
        return new LocalDiskFile(this.content, this.persistent);
    }
    
    public boolean isEmpty() {
        return this.content == null;
    }
}
