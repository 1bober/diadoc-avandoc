// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.configuration;

import org.apache.commons.lang.StringUtils;
import java.util.List;

public interface IEdoIntegrationInboundAttachmentProcessingRule extends ICstmConfigAdapter
{
    IDictionaryElementConfiguration getTargetContentGroupStorage();
    
    void setTargetContentGroupStorage(final IDictionaryElementConfiguration p0);
    
    String getTargetContentGroup();
    
    void setTargetFileNameArgument(final String p0);
    
    String getTargetFileNameArgument();
    
    IDictionaryElementConfiguration getTargetImageContentGroupStorage();
    
    void setTargetImageContentGroupStorage(final IDictionaryElementConfiguration p0);
    
    String getTargetImageContentGroup();
    
    String getPathRegexp();
    
    void setPathRegexp(final String p0);
    
    String getSourceFilenameRegexp();
    
    void setSourceFilenameRegexp(final String p0);
    
    IDictionaryElementConfiguration getContentProcessingProcedureStorage();
    
    void setContentProcessingProcedureStorage(final IDictionaryElementConfiguration p0);
    
    String getContentProcessingProcedure();
    
    InboundAttachmentType getAttachmentType();
    
    String getSourceContentGroup();
    
    String getSourceContentGroupId();
    
    void setSourceContentGroupStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getSourceContentGroupStorage();
    
    void setImportSignature(final boolean p0);
    
    boolean isImportSignature();
    
    void setDefaultHttpErrorProcessingCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getDefaultHttpErrorProcessingCategoryStorage();
    
    void setDefaultNonFatalApiErrorProcessingCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getDefaultNonFatalApiErrorProcessingCategoryStorage();
    
    void setDefaultFatalApiErrorProcessingCategoryStorage(final IDictionaryElementConfiguration p0);
    
    IDictionaryElementConfiguration getDefaultFatalApiErrorProcessingCategoryStorage();
    
    String getDefaultHttpErrorProcessingCategory();
    
    String getDefaultNonFatalApiErrorProcessingCategory();
    
    String getDefaultFatalApiErrorProcessingCategory();
    
    void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent p0);
    
    List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents();
    
    void clearProcessorEvents();
    
    void addProcessorEventFromWrapper(final Object p0);
    
    IDictionaryElementConfiguration getFileTypeStorage();
    
    void setFileTypeStorage(final IDictionaryElementConfiguration p0);
    
    String getFileType();
    
    String getEnvironmentName();
    
    void setEnvironmentName(final String p0);
    
    String getIncomingFileArgument();
    
    void setIncomingFileArgument(final String p0);
    
    String getIncomingFileNameArgument();
    
    void setIncomingFileNameArgument(final String p0);
    
    String getTargetImageFileNameArgument();
    
    void setTargetImageFileNameArgument(final String p0);
    
    boolean isUseMainFileAsImageOnError();
    
    void setUseMainFileAsImageOnError(final boolean p0);
    
    boolean isStopOtherRules();
    
    void setStopOtherRules(final boolean p0);
    
    void setUseFullDocflow(final boolean p0);
    
    boolean isFullDocflow();
    
    public enum InboundAttachmentType
    {
        primary, 
        service, 
        archive, 
        archive_file, 
        unknown;
        
        public static InboundAttachmentType parse(final String value) {
            final String valueC = StringUtils.trimToNull(value);
            if (valueC == null) {
                return InboundAttachmentType.unknown;
            }
            for (final InboundAttachmentType v : values()) {
                if (v.name().equalsIgnoreCase(valueC)) {
                    return v;
                }
            }
            return InboundAttachmentType.unknown;
        }
    }
}
