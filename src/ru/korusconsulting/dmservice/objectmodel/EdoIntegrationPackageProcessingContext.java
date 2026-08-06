// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.HashMap;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.logging.log4j.LogManager;
import java.nio.file.Path;
import java.util.Map;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentFileProcessingContext;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationPackageProcessingContext;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdoIntegrationPackageProcessingContext implements IEdoIntegrationPackageProcessingContext
{
    private static final long serialVersionUID = 198650036745801871L;
    @JsonIgnore
    protected final transient Logger logger;
    @JsonProperty
    private List<IEdoIntegrationAttachmentFileProcessingContext> files;
    @JsonProperty
    private final IEdoIntegrationFileProcessingContext archive;
    @JsonProperty
    private Map<String, Object> arguments;
    @JsonIgnore
    private transient Path archiveFilePath;
    @JsonIgnore
    private transient Map<String, Path> archiveFilesList;
    @JsonIgnore
    private transient Path extractedArchivePath;
    @JsonProperty
    private Map<String, String> parentRelations;
    @JsonProperty
    private Map<String, String> childRelations;
    @JsonProperty
    private boolean full;
    
    @JsonIgnore
    public Logger getLogger() {
        return this.logger;
    }
    
    @JsonCreator
    public EdoIntegrationPackageProcessingContext(@JsonProperty("archive") final IEdoIntegrationFileProcessingContext archive) {
        this.logger = LogManager.getLogger();
        this.archive = archive;
    }
    
    @Override
    public List<IEdoIntegrationAttachmentFileProcessingContext> getFiles() {
        if (this.files == null) {
            this.files = new ArrayList<IEdoIntegrationAttachmentFileProcessingContext>();
        }
        return this.files;
    }
    
    @Override
    public void addFile(final IEdoIntegrationAttachmentFileProcessingContext value) {
        if (value != null) {
            this.getFiles().add(value);
        }
    }
    
    @Override
    public Map<String, Object> getArguments() {
        if (this.arguments == null) {
            this.arguments = new HashMap<String, Object>();
        }
        return this.arguments;
    }
    
    @Override
    public IEdoIntegrationFileProcessingContext getArchiveFile() {
        return this.archive;
    }
    
    @Override
    public Path getArchiveFilePath() {
        return this.archiveFilePath;
    }
    
    @Override
    public void setArchiveFilePath(final Path value) {
        this.archiveFilePath = value;
        this.setArchiveFilesList(null);
    }
    
    @Override
    public Path getExtractedArchivePath() {
        return this.extractedArchivePath;
    }
    
    @Override
    public void setExtractedArchivePath(final Path value) {
        this.extractedArchivePath = value;
    }
    
    @Override
    public Map<String, Path> getArchiveFilesList() {
        return this.archiveFilesList;
    }
    
    @Override
    public void setArchiveFilesList(final Map<String, Path> value) {
        this.archiveFilesList = value;
    }
    
    @Override
    public String toString() {
        final ObjectMapper objectMapper = ((JsonMapper.Builder)((JsonMapper.Builder)JsonMapper.builder().disable(new MapperFeature[] { MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS, MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS })).disable(new SerializationFeature[] { SerializationFeature.FAIL_ON_EMPTY_BEANS })).build();
        try {
            return this.getClass().getCanonicalName() + "\n\n" + objectMapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException e) {
            this.getLogger().trace("Error while mapping to string", (Throwable)e);
            return super.toString();
        }
    }
    
    @Override
    public Map<String, String> getParentRelations() {
        return this.parentRelations;
    }
    
    @Override
    public void setParentRelations(final Map<String, String> parentRelations) {
        this.parentRelations = parentRelations;
    }
    
    @Override
    public Map<String, String> getChildRelations() {
        return this.childRelations;
    }
    
    @Override
    public void setChildRelations(final Map<String, String> childRelations) {
        this.childRelations = childRelations;
    }
    
    @Override
    public boolean isFull() {
        return this.full;
    }
    
    @Override
    public void markFull() {
        this.full = true;
    }
}
