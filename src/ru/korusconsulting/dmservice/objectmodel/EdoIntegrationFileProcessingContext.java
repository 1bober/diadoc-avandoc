// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.logging.log4j.LogManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationFileProcessingContext;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdoIntegrationFileProcessingContext implements IEdoIntegrationFileProcessingContext
{
    private static final long serialVersionUID = -2624356379220333086L;
    @JsonProperty
    private final String id;
    @JsonProperty
    private final String filename;
    @JsonProperty
    private final String url;
    @JsonProperty
    private boolean notNeeded;
    @JsonProperty
    private boolean proceed;
    @JsonProperty
    private boolean fatal;
    @JsonIgnore
    protected final transient Logger logger;
    
    @JsonIgnore
    public Logger getLogger() {
        return this.logger;
    }
    
    @JsonCreator
    public EdoIntegrationFileProcessingContext(@JsonProperty("id") final String id, @JsonProperty("filename") final String filename, @JsonProperty("url") final String url) {
        this.logger = LogManager.getLogger();
        this.id = id;
        this.filename = filename;
        this.url = url;
    }
    
    @Override
    public boolean isNotNeeded() {
        return this.notNeeded;
    }
    
    @Override
    public void setNotNeeded(final boolean value) {
        this.notNeeded = value;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public String getFilename() {
        return this.filename;
    }
    
    @Override
    public boolean isProceed() {
        return this.proceed;
    }
    
    @Override
    public void setProceed(final boolean value) {
        this.proceed = value;
    }
    
    @Override
    public boolean isFatal() {
        return this.fatal;
    }
    
    @Override
    public void setFatal(final boolean fatal) {
        this.fatal = fatal;
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
}
