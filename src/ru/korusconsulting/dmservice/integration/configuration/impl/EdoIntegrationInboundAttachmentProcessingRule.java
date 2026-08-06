// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.configuration.impl;

import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import java.util.function.Supplier;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.dmservice.cstm.generators.Generator;
import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import java.util.Objects;
import java.util.function.Function;
import ru.korusconsulting.service.integration.configuration.ICstmConfigAdapter;
import java.util.ArrayList;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingEvent;
import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import ru.korusconsulting.service.integration.configuration.IEdoIntegrationInboundAttachmentProcessingRule;

@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class EdoIntegrationInboundAttachmentProcessingRule extends CstmConfigAdapter implements IEdoIntegrationInboundAttachmentProcessingRule
{
    private static final long serialVersionUID = -1672562291498052315L;
    private static final transient List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>> fieldDefinition;
    @JsonProperty
    private String pathRegexp;
    @JsonProperty
    private String sourceFilenameRegexp;
    @JsonProperty
    private String environmentName;
    @JsonProperty
    private String incomingFileArgument;
    @JsonProperty
    private String incomingFileNameArgument;
    @JsonProperty
    private String targetImageFileNameArgument;
    @JsonProperty
    private String targetFileNameArgument;
    @JsonProperty
    private IDictionaryElementConfiguration fileTypeStorage;
    @JsonProperty
    private IDictionaryElementConfiguration sourceContentGroupStorage;
    @JsonProperty
    private IDictionaryElementConfiguration targetContentGroupStorage;
    @JsonProperty
    private IDictionaryElementConfiguration targetImageContentGroupStorage;
    @JsonProperty
    private boolean useMainFileAsImageOnError;
    @JsonProperty
    private boolean useFullDocflow;
    @JsonProperty
    private IDictionaryElementConfiguration contentProcessingProcedureStorage;
    @JsonProperty
    private boolean importSignature;
    @JsonProperty
    private boolean stopOtherRules;
    @JsonProperty
    private IDictionaryElementConfiguration defaultHttpErrorProcessingCategory;
    @JsonProperty
    private IDictionaryElementConfiguration defaultNonFatalApiErrorProcessingCategory;
    @JsonProperty
    private IDictionaryElementConfiguration defaultFatalApiErrorProcessingCategory;
    @JsonProperty
    private String processorLinks;
    @JsonProperty
    private final List<IEdoIntegrationInboundAttachmentProcessingEvent> processorEvents;
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>> initFieldDefinition() {
        final List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>> v = new ArrayList<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>>();
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sida_source_content_group", "ssa_source_content_group_name", "source_content_group", ICstmConfigAdapter.FieldType.content_group, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getSourceContentGroupStorage, (a, b) -> a.setSourceContentGroupStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_source_filename_regexp", "source_filename_regexp", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getSourceFilenameRegexp, (a, b) -> a.setSourceFilenameRegexp(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_file_type", "file_type", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getFileTypeStorage, (a, b) -> a.setFileTypeStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_path_regexp", "path_regexp", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getPathRegexp, (a, b) -> a.setPathRegexp(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_environment_name", "environment_name", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getEnvironmentName, (a, b) -> a.setEnvironmentName(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_incoming_file_argument", "incoming_file_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getIncomingFileArgument, (a, b) -> a.setIncomingFileArgument(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_incoming_filename_argument", "incoming_filename_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getIncomingFileNameArgument, (a, b) -> a.setIncomingFileNameArgument(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sida_target_content_group", "ssa_target_content_group_name", "target_content_group", ICstmConfigAdapter.FieldType.content_group, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getTargetContentGroupStorage, (a, b) -> a.setTargetContentGroupStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sida_target_image_content_group", "ssa_target_image_content_group_name", "target_image_content_group", ICstmConfigAdapter.FieldType.content_group, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getTargetImageContentGroupStorage, (a, b) -> a.setTargetImageContentGroupStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sba_use_main_file_as_image_on_error", "use_main_file_as_image_on_error", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::isUseMainFileAsImageOnError, (a, b) -> a.setUseMainFileAsImageOnError(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sba_full_docflow", "full_docflow", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::isFullDocflow, (a, b) -> a.setUseFullDocflow(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sba_stop_other_rules", "stop_other_rules", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::isStopOtherRules, (a, b) -> a.setStopOtherRules(b != null && (boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_content_processing_procedure", "ssa_content_processing_procedure_name", "content_processing_procedure", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getContentProcessingProcedureStorage, (a, b) -> a.setContentProcessingProcedureStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_target_filename_argument", "target_filename_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getTargetFileNameArgument, (a, b) -> a.setTargetFileNameArgument(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_target_image_filename_argument", "target_image_filename_argument", ICstmConfigAdapter.FieldType.text, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getTargetImageFileNameArgument, (a, b) -> a.setTargetImageFileNameArgument(Objects.toString(b, null))));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("sba_import_signature", "import_signature", ICstmConfigAdapter.FieldType.bool, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::isImportSignature, (a, b) -> a.setImportSignature((boolean)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_default_http_error_processing_category", "ssa_default_http_error_processing_category_name", "default_http_error_processing_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getDefaultHttpErrorProcessingCategoryStorage, (a, b) -> a.setDefaultHttpErrorProcessingCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_default_non_fatal_api_error_processing_category", "ssa_default_non_fatal_api_error_processing_category_name", "default_non_fatal_api_error_processing_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getDefaultNonFatalApiErrorProcessingCategoryStorage, (a, b) -> a.setDefaultNonFatalApiErrorProcessingCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("ssa_default_fatal_api_error_processing_category", "ssa_default_fatal_api_error_processing_category_name", "default_fatal_api_error_processing_category", ICstmConfigAdapter.FieldType.dictionary_value, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getDefaultFatalApiErrorProcessingCategoryStorage, (a, b) -> a.setDefaultFatalApiErrorProcessingCategoryStorage((IDictionaryElementConfiguration)b)));
        v.add(new ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>("processor_event_link", null, "processor_event_links", "processor_event_link", ICstmConfigAdapter.FieldType.cstm_linked_list, (Function<IEdoIntegrationInboundAttachmentProcessingRule, Object>)IEdoIntegrationInboundAttachmentProcessingRule::getProcessorEvents, (a, b) -> a.addProcessorEventFromWrapper(b), IEdoIntegrationInboundAttachmentProcessingRule::clearProcessorEvents));
        return v;
    }
    
    private static List<ConfigFieldBaseDescriptionExt<IEdoIntegrationInboundAttachmentProcessingRule>> getFieldDefinition() {
        return EdoIntegrationInboundAttachmentProcessingRule.fieldDefinition;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ICstmComponent component, final Generator generator, final SessionContext ctx) {
        super.unmarshalImpl(session, component, generator, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), component, generator, ctx);
    }
    
    @Override
    public <T extends ConfigElement> T marshalImpl(final DMSShardsClientSession session, final String xmlTag, final Generator generator, final Supplier<T> configElementSupplier, final SessionContext ctx) {
        final T config = super.marshalImpl(session, xmlTag, generator, configElementSupplier, ctx);
        this.marshalFields(session, this, getFieldDefinition(), config, generator, ctx);
        return config;
    }
    
    @Override
    public void unmarshalImpl(final DMSShardsClientSession session, final ConfigElement element, final SessionContext ctx) {
        super.unmarshalImpl(session, element, ctx);
        this.unmarshalFields(session, this, getFieldDefinition(), element, ctx);
    }
    
    protected EdoIntegrationInboundAttachmentProcessingRule(final ICstmConfigAdapter parent) {
        super(parent);
        this.processorEvents = new ArrayList<IEdoIntegrationInboundAttachmentProcessingEvent>();
    }
    
    @Override
    public IDictionaryElementConfiguration getTargetContentGroupStorage() {
        return this.targetContentGroupStorage;
    }
    
    @Override
    public void setTargetContentGroupStorage(final IDictionaryElementConfiguration value) {
        this.targetContentGroupStorage = value;
    }
    
    @Override
    public String getTargetImageContentGroup() {
        return (this.getTargetImageContentGroupStorage() != null) ? this.getTargetImageContentGroupStorage().getValue() : null;
    }
    
    @Override
    public IDictionaryElementConfiguration getTargetImageContentGroupStorage() {
        return this.targetImageContentGroupStorage;
    }
    
    @Override
    public void setTargetImageContentGroupStorage(final IDictionaryElementConfiguration value) {
        this.targetImageContentGroupStorage = value;
    }
    
    @Override
    public String getTargetContentGroup() {
        return (this.getTargetContentGroupStorage() != null) ? this.getTargetContentGroupStorage().getValue() : null;
    }
    
    @Override
    public IDictionaryElementConfiguration getSourceContentGroupStorage() {
        return this.sourceContentGroupStorage;
    }
    
    @Override
    public void setSourceContentGroupStorage(final IDictionaryElementConfiguration value) {
        this.sourceContentGroupStorage = value;
    }
    
    @Override
    public String getSourceContentGroup() {
        return (this.getSourceContentGroupStorage() != null) ? this.getSourceContentGroupStorage().getValue() : null;
    }
    
    @Override
    public String getSourceContentGroupId() {
        return (this.getSourceContentGroupStorage() != null) ? this.getSourceContentGroupStorage().getId() : null;
    }
    
    @Override
    public String getPathRegexp() {
        return this.pathRegexp;
    }
    
    @Override
    public void setPathRegexp(final String value) {
        this.pathRegexp = value;
    }
    
    @Override
    public String getSourceFilenameRegexp() {
        return this.sourceFilenameRegexp;
    }
    
    @Override
    public void setSourceFilenameRegexp(final String value) {
        this.sourceFilenameRegexp = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getContentProcessingProcedureStorage() {
        return this.contentProcessingProcedureStorage;
    }
    
    @Override
    public void setContentProcessingProcedureStorage(final IDictionaryElementConfiguration value) {
        this.contentProcessingProcedureStorage = value;
    }
    
    @Override
    public String getContentProcessingProcedure() {
        return (this.getContentProcessingProcedureStorage() != null) ? this.getContentProcessingProcedureStorage().getId() : null;
    }
    
    @Override
    public void setImportSignature(final boolean value) {
        this.importSignature = value;
    }
    
    @Override
    public boolean isImportSignature() {
        return this.importSignature;
    }
    
    @Override
    public void setDefaultHttpErrorProcessingCategoryStorage(final IDictionaryElementConfiguration value) {
        this.defaultHttpErrorProcessingCategory = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getDefaultHttpErrorProcessingCategoryStorage() {
        return this.defaultHttpErrorProcessingCategory;
    }
    
    @Override
    public void setDefaultNonFatalApiErrorProcessingCategoryStorage(final IDictionaryElementConfiguration value) {
        this.defaultNonFatalApiErrorProcessingCategory = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getDefaultNonFatalApiErrorProcessingCategoryStorage() {
        return this.defaultNonFatalApiErrorProcessingCategory;
    }
    
    @Override
    public void setDefaultFatalApiErrorProcessingCategoryStorage(final IDictionaryElementConfiguration value) {
        this.defaultFatalApiErrorProcessingCategory = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getDefaultFatalApiErrorProcessingCategoryStorage() {
        return this.defaultFatalApiErrorProcessingCategory;
    }
    
    @Override
    public String getDefaultHttpErrorProcessingCategory() {
        return (this.getDefaultHttpErrorProcessingCategoryStorage() != null) ? this.getDefaultHttpErrorProcessingCategoryStorage().getValue() : null;
    }
    
    @Override
    public String getDefaultNonFatalApiErrorProcessingCategory() {
        return (this.getDefaultNonFatalApiErrorProcessingCategoryStorage() != null) ? this.getDefaultNonFatalApiErrorProcessingCategoryStorage().getValue() : null;
    }
    
    @Override
    public String getDefaultFatalApiErrorProcessingCategory() {
        return (this.getDefaultFatalApiErrorProcessingCategoryStorage() != null) ? this.getDefaultFatalApiErrorProcessingCategoryStorage().getValue() : null;
    }
    
    @Override
    public void addProcessorEvent(final IEdoIntegrationInboundAttachmentProcessingEvent value) {
        this.getProcessorEvents().add(value);
    }
    
    @Override
    public List<IEdoIntegrationInboundAttachmentProcessingEvent> getProcessorEvents() {
        return this.processorEvents;
    }
    
    @Override
    public void clearProcessorEvents() {
        this.getProcessorEvents().clear();
    }
    
    @Override
    public boolean isStopOtherRules() {
        return this.stopOtherRules;
    }
    
    @Override
    public void setStopOtherRules(final boolean value) {
        this.stopOtherRules = value;
    }
    
    @Override
    public void addProcessorEventFromWrapper(final Object value) {
        if (value instanceof final CstmOperationWrapper cstmOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingEvent event = this.createAttachmentProcessorEvent(cstmOperationContext.getSession(), cstmOperationContext.getComponent(), cstmOperationContext.getXmlTagName(), cstmOperationContext.getGenerator(), cstmOperationContext.getContext());
            if (event != null) {
                this.addProcessorEvent(event);
            }
        }
        else if (value instanceof final ConfigOperationWrapper configOperationContext) {
            final IEdoIntegrationInboundAttachmentProcessingEvent event = this.createAttachmentProcessorEvent(configOperationContext.getSession(), configOperationContext.getConfig(), configOperationContext.getContext());
            if (event != null) {
                this.addProcessorEvent(event);
            }
        }
    }
    
    @Override
    public String getEnvironmentName() {
        return this.environmentName;
    }
    
    @Override
    public void setEnvironmentName(final String value) {
        this.environmentName = value;
    }
    
    @Override
    public String getIncomingFileArgument() {
        return this.incomingFileArgument;
    }
    
    @Override
    public void setIncomingFileArgument(final String value) {
        this.incomingFileArgument = value;
    }
    
    @Override
    public IDictionaryElementConfiguration getFileTypeStorage() {
        return this.fileTypeStorage;
    }
    
    @Override
    public void setFileTypeStorage(final IDictionaryElementConfiguration value) {
        this.fileTypeStorage = value;
    }
    
    @Override
    public String getFileType() {
        return (this.getFileTypeStorage() == null) ? null : this.getFileTypeStorage().getValue();
    }
    
    @Override
    public String getIncomingFileNameArgument() {
        return this.incomingFileNameArgument;
    }
    
    @Override
    public void setIncomingFileNameArgument(final String value) {
        this.incomingFileNameArgument = value;
    }
    
    @Override
    public String getTargetImageFileNameArgument() {
        return this.targetImageFileNameArgument;
    }
    
    @Override
    public void setTargetImageFileNameArgument(final String value) {
        this.targetImageFileNameArgument = value;
    }
    
    @Override
    public String getTargetFileNameArgument() {
        return this.targetFileNameArgument;
    }
    
    @Override
    public void setTargetFileNameArgument(final String value) {
        this.targetFileNameArgument = value;
    }
    
    @Override
    public boolean isUseMainFileAsImageOnError() {
        return this.useMainFileAsImageOnError;
    }
    
    @Override
    public void setUseMainFileAsImageOnError(final boolean value) {
        this.useMainFileAsImageOnError = value;
    }
    
    @Override
    public boolean isFullDocflow() {
        return this.useFullDocflow;
    }
    
    @Override
    public void setUseFullDocflow(final boolean useFullDocflow) {
        this.useFullDocflow = useFullDocflow;
    }
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ConfigElement p1, final SessionContext p2);
    
    public abstract IEdoIntegrationInboundAttachmentProcessingEvent createAttachmentProcessorEvent(final DMSShardsClientSession p0, final ICstmComponent p1, final String p2, final Generator p3, final SessionContext p4);
    
    static {
        fieldDefinition = initFieldDefinition();
    }
}
