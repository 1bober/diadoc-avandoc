// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.nio.file.Path;
import java.util.Map;
import java.util.List;
import java.io.Serializable;

public interface IEdoIntegrationPackageProcessingContext extends Serializable
{
    List<IEdoIntegrationAttachmentFileProcessingContext> getFiles();
    
    void addFile(final IEdoIntegrationAttachmentFileProcessingContext p0);
    
    IEdoIntegrationFileProcessingContext getArchiveFile();
    
    Map<String, Object> getArguments();
    
    Path getArchiveFilePath();
    
    void setArchiveFilePath(final Path p0);
    
    Path getExtractedArchivePath();
    
    void setExtractedArchivePath(final Path p0);
    
    Map<String, Path> getArchiveFilesList();
    
    void setArchiveFilesList(final Map<String, Path> p0);
    
    void setParentRelations(final Map<String, String> p0);
    
    Map<String, String> getParentRelations();
    
    void setChildRelations(final Map<String, String> p0);
    
    Map<String, String> getChildRelations();
    
    boolean isFull();
    
    void markFull();
}
