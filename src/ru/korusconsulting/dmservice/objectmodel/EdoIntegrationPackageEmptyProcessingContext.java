// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.nio.file.Path;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import java.util.Collections;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentFileProcessingContext;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class EdoIntegrationPackageEmptyProcessingContext implements IEdoIntegrationPackageProcessingContext
{
    private static final long serialVersionUID = 5962688737752387300L;
    
    @Override
    public List<IEdoIntegrationAttachmentFileProcessingContext> getFiles() {
        return Collections.emptyList();
    }
    
    @Override
    public void addFile(final IEdoIntegrationAttachmentFileProcessingContext value) {
    }
    
    @Override
    public IEdoIntegrationFileProcessingContext getArchiveFile() {
        return null;
    }
    
    @Override
    public Map<String, Object> getArguments() {
        return Collections.emptyMap();
    }
    
    @Override
    public Path getArchiveFilePath() {
        return null;
    }
    
    @Override
    public void setArchiveFilePath(final Path value) {
    }
    
    @Override
    public Path getExtractedArchivePath() {
        return null;
    }
    
    @Override
    public void setExtractedArchivePath(final Path value) {
    }
    
    @Override
    public Map<String, Path> getArchiveFilesList() {
        return Collections.emptyMap();
    }
    
    @Override
    public void setArchiveFilesList(final Map<String, Path> value) {
    }
    
    @Override
    public void setParentRelations(final Map<String, String> value) {
    }
    
    @Override
    public Map<String, String> getParentRelations() {
        return Collections.emptyMap();
    }
    
    @Override
    public void setChildRelations(final Map<String, String> value) {
    }
    
    @Override
    public Map<String, String> getChildRelations() {
        return Collections.emptyMap();
    }
    
    @Override
    public boolean isFull() {
        return true;
    }
    
    @Override
    public void markFull() {
    }
}
