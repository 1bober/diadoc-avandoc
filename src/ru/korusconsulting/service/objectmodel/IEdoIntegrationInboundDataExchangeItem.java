// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.objectmodel;

import java.util.function.Function;
import java.util.Arrays;
import java.util.List;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;

public interface IEdoIntegrationInboundDataExchangeItem extends IIntegrationExchangeItemBasic
{
    String getDocumentId();
    
    void setDocumentName(final String p0);
    
    String getDocumentName();
    
    void setFilialId(final String p0);
    
    String getFilialId();
    
    void setFilialName(final String p0);
    
    String getFilialName();
    
    String getObjectId();
    
    IEdoIntegrationPackageProcessingContext getProcessingContext();
    
    void setProcessingContext(final IEdoIntegrationPackageProcessingContext p0);
    
    void setRemoteStateProcessingStep(final ProcessingStep p0);
    
    void setLocalStateProcessingStep(final ProcessingStep p0);
    
    String getRemoteStateProcessingStep();
    
    String getLocalStateProcessingStep();
    
    String getProcessingState();
    
    void setProcessingState(final EdoIntegrationProcessingState p0);
    
    String getProcessingError();
    
    void setProcessingError(final ProcessingError p0);
    
    void setErrorCount(final int p0);
    
    int getErrorCount();
    
    public enum ProcessingStep
    {
        queued(0, false), 
        preloaded(1, false), 
        document_created(2, false), 
        archive_downloaded(3, false), 
        all_downloaded(4, false), 
        document_ready(5, false), 
        proceed(6, true), 
        skip(7, true);
        
        private final int level;
        private final boolean completed;
        
        private ProcessingStep(final int level, final boolean completed) {
            this.level = level;
            this.completed = completed;
        }
        
        public int getLevel() {
            return this.level;
        }
        
        public boolean isCompleted() {
            return this.completed;
        }
        
        public boolean after(final ProcessingStep value) {
            return value == null || this.getLevel() > value.getLevel();
        }
        
        public boolean after(final String value) {
            return (value == null && this.getLevel() == 0) || this.getLevel() == valueOf(value).getLevel() + 1;
        }
        
        public static boolean inProgress(final String value) {
            final ProcessingStep v = valueOf(value);
            return !v.isCompleted();
        }
        
        public static List<String> getInProcessStepList() {
            return Arrays.asList(values()).stream().filter(a -> !a.isCompleted()).map((Function<? super Object, ? extends String>)Enum::name).toList();
        }
        
        public boolean is(final String value) {
            return this.name().equals(value);
        }
    }
    
    public enum ProcessingError
    {
        no_template, 
        instantiate_template_error, 
        no_configuration, 
        download_failed, 
        read_failed, 
        lifecycle_error;
    }
}
