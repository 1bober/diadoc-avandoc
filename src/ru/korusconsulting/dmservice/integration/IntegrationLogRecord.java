// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import java.nio.charset.StandardCharsets;
import org.apache.commons.lang.exception.ExceptionUtils;
import java.nio.file.attribute.FileTime;
import java.time.ZonedDateTime;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import java.nio.channels.FileChannel;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import java.util.Objects;
import java.nio.channels.ReadableByteChannel;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.file.CopyOption;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import java.util.UUID;
import ru.korusconsulting.service.commons.ApplicationProperties;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.nio.file.FileSystems;
import ru.korusconsulting.dmservice.integration.impl.IntegrationLogSettings;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;
import java.io.File;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.function.Supplier;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import java.nio.file.Path;
import org.apache.logging.log4j.Logger;

public class IntegrationLogRecord implements AutoCloseable
{
    private static final Logger logger;
    private String stage;
    private Path storageDirectory;
    private LogFileRecord file;
    private Path zipFile;
    private String pathTemplate;
    private boolean isTemp;
    private boolean isPrefix;
    private ZipArchiveOutputStream zipStream;
    private String logFileName;
    
    private void setStorageDirectory(final Path storageDirectory) {
        this.storageDirectory = storageDirectory;
    }
    
    private Path getStorageDirectory() {
        return this.storageDirectory;
    }
    
    private void setStage(final String stage) {
        this.stage = stage;
    }
    
    private String getStage() {
        return this.stage;
    }
    
    public LogFileRecord getFile() {
        return this.file;
    }
    
    private void markAsTemp() {
        this.isTemp = true;
    }
    
    private boolean isTemp() {
        return this.isTemp;
    }
    
    private void setFile(final String originalFileName, final Path file) {
        this.file = new LogFileRecord(this.getStage(), originalFileName, file);
        this.isTemp = false;
    }
    
    public String getLogFileName() {
        return this.logFileName;
    }
    
    public void setLogFileName(final String logFileName, final boolean isPrefix) {
        this.logFileName = logFileName;
        this.isPrefix = isPrefix;
    }
    
    public ZipArchiveOutputStream getZipStream(final Supplier<ZipArchiveOutputStream> creator) {
        synchronized (this) {
            if (this.zipStream == null && creator != null) {
                this.zipStream = creator.get();
            }
        }
        return this.zipStream;
    }
    
    public Path getZipFile() {
        return this.zipFile;
    }
    
    public void setZipFile(final Path zipFile) {
        this.zipFile = zipFile;
    }
    
    public ZipArchiveOutputStream createZipStream(final SessionContext ctx) throws IOException {
        final Path logFile = this.getRandomFile(this.getLogFileName(), "zip", this.getStorageDirectory(), this.getPathTemplate(), false, true, ctx);
        this.setZipFile(logFile);
        (this.zipStream = new ZipArchiveOutputStream(this.getZipFile().toFile()) {
            public void close() {
                try {
                    super.close();
                }
                catch (final IOException e) {
                    IntegrationLogRecord.logger.warn("Error closing zip stream", (Throwable)e);
                }
            }
        }).setEncoding("cp866");
        return this.zipStream;
    }
    
    protected Path getRandomFile(final String prefix, final String fileExtension, final Path parentDir, final String backupFilenameTemplate, final boolean unstructured, final boolean createFile, final SessionContext ctx) throws IOException {
        if (parentDir == null) {
            return null;
        }
        String[] backupFilenameParts;
        if (StringUtils.isBlank(backupFilenameTemplate)) {
            backupFilenameParts = new String[] { IntegrationLogSettings.getDefaultFilenameTemplate() };
        }
        else {
            backupFilenameParts = StringUtils.split(backupFilenameTemplate, FileSystems.getDefault().getSeparator());
        }
        final LocalDateTime lt = LocalDateTime.now();
        int centerRow = -1;
        if (!Files.isDirectory(parentDir, new LinkOption[0])) {
            parentDir.toFile().mkdirs();
        }
        Path resultPath = parentDir;
        String resultFilePrefix = null;
        String resultFileSuffix = null;
        try {
            for (int i = unstructured ? (backupFilenameParts.length - 1) : 0; i < backupFilenameParts.length; ++i) {
                String pathPart = StringUtils.trimToNull(backupFilenameParts[i]);
                if (pathPart != null) {
                    final String[] attrNames = ru.korusconsulting.service.integration.StringUtils.getParameterNames(pathPart, "@{", "}");
                    if (attrNames != null && attrNames.length > 0) {
                        final Map<String, String> attrValues = new HashMap<String, String>();
                        for (final String attrName : attrNames) {
                            if ("PREFIX".equals(attrName)) {
                                attrValues.put(attrName, (prefix == null) ? "" : prefix);
                            }
                            else if ("EXTENSION".equals(attrName)) {
                                attrValues.put(attrName, (fileExtension == null) ? "bin" : fileExtension);
                            }
                            else if ("RANDOM".equals(attrName)) {
                                if (centerRow == -1) {
                                    centerRow = i;
                                }
                                else {
                                    attrValues.put(attrName, "");
                                }
                            }
                            else if (ctx.getAttributeNames().contains(attrName)) {
                                attrValues.put(attrName, ctx.getString(attrName));
                            }
                            else if (StringUtils.contains(attrName, "CURRENTDATETIME(")) {
                                final String[] functionParams = ru.korusconsulting.service.integration.StringUtils.getParameterNames(attrName, "CURRENTDATETIME(", ")");
                                if (functionParams != null && functionParams.length > 0) {
                                    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(functionParams[0]);
                                    attrValues.put(attrName, lt.format(dtf));
                                }
                            }
                            else {
                                attrValues.put(attrName, "");
                            }
                        }
                        pathPart = StringUtils.trimToNull(ru.korusconsulting.service.integration.StringUtils.resolveString(pathPart, "@{", "}", (Map)attrValues));
                        if (centerRow == i) {
                            resultFilePrefix = StringUtils.trimToNull(pathPart.substring(0, pathPart.indexOf("@{RANDOM}")));
                            resultFileSuffix = StringUtils.trimToNull(pathPart.substring(pathPart.indexOf("@{RANDOM}") + "@{RANDOM}".length()));
                            if (i < backupFilenameParts.length - 1) {
                                if (resultFileSuffix != null) {}
                                resultPath = Files.createTempDirectory(resultPath, resultFilePrefix, (FileAttribute<?>[])new FileAttribute[0]);
                                centerRow = -1;
                                resultFilePrefix = null;
                                resultFileSuffix = null;
                            }
                        }
                        else {
                            resultPath = resultPath.resolve(pathPart);
                            if (i < backupFilenameParts.length - 1 && !Files.isDirectory(resultPath, new LinkOption[0])) {
                                final Path resultPathF = resultPath;
                                if (!resultPath.toFile().mkdirs()) {
                                    IntegrationLogRecord.logger.error(() -> String.format("Error creating directory in %s", resultPathF.toAbsolutePath().toString()));
                                }
                                if (!Files.isDirectory(resultPath, new LinkOption[0])) {
                                    throw new FileDirectoryCreateFailedException(String.format("Error creating directory in %s", resultPathF.toAbsolutePath().toString()));
                                }
                            }
                        }
                    }
                    else {
                        resultPath = resultPath.resolve(pathPart);
                        if (i < backupFilenameParts.length - 1 && !Files.isDirectory(resultPath, new LinkOption[0])) {
                            final Path resultPathF2 = resultPath;
                            if (!resultPath.toFile().mkdirs()) {
                                IntegrationLogRecord.logger.error(() -> String.format("Error creating directory in %s", resultPathF2.toAbsolutePath().toString()));
                            }
                            if (!Files.isDirectory(resultPath, new LinkOption[0])) {
                                throw new FileDirectoryCreateFailedException(String.format("Error creating directory in %s", resultPathF2.toAbsolutePath().toString()));
                            }
                        }
                    }
                }
            }
            String prf = StringUtils.trimToEmpty(resultFilePrefix);
            if (prf.length() < 3) {
                prf = "tmp";
            }
            try {
                final Path resultFilePath = FileSystems.getDefault().getPath(File.createTempFile(prf, StringUtils.trimToEmpty(resultFileSuffix), resultPath.toFile()).getAbsolutePath(), new String[0]);
                if (!createFile) {
                    Files.delete(resultFilePath);
                }
                return resultFilePath;
            }
            catch (final IOException e) {
                final Path resultPathF3 = resultPath;
                IntegrationLogRecord.logger.error(() -> String.format("Error creating file in %s", resultPathF3.toAbsolutePath().toString()), (Throwable)e);
                throw e;
            }
        }
        catch (final FileDirectoryCreateFailedException | IOException e2) {
            final File dir = new File(ApplicationProperties.getProperty("content_dir") + File.separator + UUID.randomUUID());
            if (dir.mkdir()) {
                final Path resultFilePath2 = FileSystems.getDefault().getPath(File.createTempFile("rnd", StringUtils.trimToEmpty(resultFileSuffix), dir).getAbsolutePath(), new String[0]);
                if (!createFile) {
                    Files.delete(resultFilePath2);
                }
                return resultFilePath2;
            }
            throw e2;
        }
    }
    
    public static IBuilder newBuilder() {
        return new StageBuilder();
    }
    
    @Override
    public void close() {
        if (this.getZipStream(null) != null) {
            try {
                this.getZipStream(null).close();
            }
            catch (final IOException e) {
                IntegrationLogRecord.logger.warn("Error closing log record file");
            }
        }
    }
    
    public String getFileAbsolutePath() {
        return (this.getFile() == null) ? null : this.getFile().getFilePath().toAbsolutePath().toString();
    }
    
    public String getPathTemplate() {
        return this.pathTemplate;
    }
    
    public void setPathTemplate(final String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public enum Stage
    {
        error, 
        result, 
        end, 
        connect;
    }
    
    public static class LogFileRecord
    {
        private final String originalFileName;
        private final String category;
        private final Path filePath;
        
        public LogFileRecord(final String category, final String originalFileName, final Path filePath) {
            this.originalFileName = originalFileName;
            this.category = category;
            this.filePath = filePath;
        }
        
        public Path getFilePath() {
            return this.filePath;
        }
        
        public String getOriginalFileName() {
            return this.originalFileName;
        }
        
        public String getCategory() {
            return this.category;
        }
    }
    
    private static class StageBuilder implements IBuilder, IBuilder2, ILogFileBuilder, IStageBuilder
    {
        private IntegrationLogRecord logRecord;
        
        private StageBuilder() {
            this.logRecord = new IntegrationLogRecord();
        }
        
        @Override
        public IBuilder2 setStorageDirectory(final Path storageDirectory) {
            this.logRecord.setStorageDirectory(storageDirectory);
            return this;
        }
        
        @Override
        public ILogFileBuilder setLogFilenameTemplate(final String value) {
            this.logRecord.setPathTemplate(value);
            return this;
        }
        
        @Override
        public IStageBuilder setStage(final Stage stage) {
            this.logRecord.setStage((stage == null) ? null : stage.name());
            return this;
        }
        
        @Override
        public IStageBuilder setStage(final String stage) {
            this.logRecord.setStage(stage);
            return this;
        }
        
        @Override
        public IStageBuilder getActiveStage() {
            return this;
        }
        
        @Override
        public IStageBuilder addFile(final Path sourceFile, final boolean allowMove) {
            return this.addFile(sourceFile, null, allowMove, new SessionContext());
        }
        
        @Override
        public IStageBuilder addFile(final Path sourceFile, final boolean allowMove, final SessionContext ctx) {
            return this.addFile(sourceFile, null, allowMove, ctx);
        }
        
        @Override
        public IStageBuilder addFile(final Path sourceFile, final String overrideFilename, final boolean allowMove) {
            return this.addFile(sourceFile, overrideFilename, allowMove, new SessionContext());
        }
        
        @Override
        public IStageBuilder addFile(final Path sourceFile, final String overrideFilename, final boolean allowMove, final SessionContext ctx) {
            if (sourceFile == null) {
                return this;
            }
            if (this.logRecord.getStorageDirectory() == null) {
                IntegrationLogRecord.logger.error("Error writing integration log file data. no log storage directory defined");
                return this;
            }
            try {
                final String fullFilename = StringUtils.isBlank(overrideFilename) ? sourceFile.toFile().getName() : overrideFilename;
                final String baseFilename = FilenameUtils.getBaseName(fullFilename);
                final String extension = FilenameUtils.getExtension(fullFilename);
                if (this.logRecord.getFile() == null) {
                    final Path logFile = this.logRecord.getRandomFile(baseFilename, extension, this.logRecord.getStorageDirectory(), this.logRecord.getPathTemplate(), false, false, ctx);
                    if (logFile == null) {
                        IntegrationLogRecord.logger.error(() -> String.format("Error writing integration log file data. Failed to create file %s in %s", fullFilename, this.logRecord.getStorageDirectory().toString()));
                        return this;
                    }
                    if (allowMove) {
                        Files.move(sourceFile, logFile, new CopyOption[0]);
                    }
                    else {
                        if (!logFile.toFile().createNewFile()) {
                            IntegrationLogRecord.logger.error(() -> String.format("Error writing integration log file data. Failed to create file %s", logFile.toAbsolutePath().toString()));
                            return this;
                        }
                        try (final FileInputStream fis = new FileInputStream(sourceFile.toFile());
                             final FileChannel sourceStream = fis.getChannel();
                             final FileOutputStream fos = new FileOutputStream(logFile.toFile());
                             final FileChannel destinationStream = fos.getChannel()) {
                            destinationStream.transferFrom(sourceStream, 0L, sourceStream.size());
                        }
                    }
                    this.logRecord.setFile(fullFilename, logFile);
                    this.logRecord.markAsTemp();
                }
                else {
                    final ZipArchiveOutputStream stream = this.logRecord.getZipStream(() -> {
                        try {
                            final ZipArchiveOutputStream newStream = this.logRecord.createZipStream(ctx);
                            final LogFileRecord fileL = this.logRecord.getFile();
                            StringBuilder sb = null;
                            if (StringUtils.isNotBlank(fileL.getCategory())) {
                                sb = new StringBuilder().append(fileL.getCategory());
                            }
                            if (StringUtils.isNotBlank(fileL.getOriginalFileName())) {
                                if (sb == null) {
                                    sb = new StringBuilder();
                                }
                                sb.append(fileL.getOriginalFileName());
                            }
                            final ZipArchiveEntry entry2 = newStream.createArchiveEntry(fileL.getFilePath().toFile(), Objects.toString(sb, ""));
                            try {
                                newStream.putArchiveEntry(entry2);
                                FileUtils.copyFile(fileL.getFilePath().toFile(), (OutputStream)newStream);
                            }
                            finally {
                                newStream.closeArchiveEntry();
                            }
                            return newStream;
                        }
                        catch (final IOException e2) {
                            throw new ServiceException((Exception)e2);
                        }
                    });
                    final ZipArchiveEntry entry = stream.createArchiveEntry(sourceFile.toFile(), fullFilename);
                    try {
                        stream.putArchiveEntry(entry);
                        FileUtils.copyFile(sourceFile.toFile(), (OutputStream)stream);
                    }
                    finally {
                        stream.closeArchiveEntry();
                    }
                }
            }
            catch (final Exception e) {
                IntegrationLogRecord.logger.error(() -> String.format("Error writing integration log file data %s", sourceFile.toString()), (Throwable)e);
            }
            return this;
        }
        
        @Override
        public IStageBuilder addFile(final String fileName, final String extension, final byte[] data) {
            return this.addFile(fileName, extension, data, new SessionContext());
        }
        
        @Override
        public IStageBuilder addFile(final String fileName, final String extension, final byte[] data, final SessionContext ctx) {
            if (this.logRecord.getStorageDirectory() == null) {
                IntegrationLogRecord.logger.error("Error writing integration log file data. no log storage directory defined");
                return this;
            }
            if (data == null || data.length == 0) {
                IntegrationLogRecord.logger.info("Asked to add log file with no data. Request ignored");
                return this;
            }
            try {
                if (this.logRecord.getFile() == null) {
                    final Path logFile = this.logRecord.getRandomFile((fileName == null) ? this.logRecord.getLogFileName() : fileName, extension, this.logRecord.getStorageDirectory(), this.logRecord.getPathTemplate(), false, false, ctx);
                    if (logFile != null) {
                        try (final FileOutputStream fos = new FileOutputStream(logFile.toFile())) {
                            fos.write(data);
                        }
                        this.logRecord.setFile(String.join(".", fileName, extension), logFile);
                        this.logRecord.markAsTemp();
                    }
                }
                else {
                    final ZipArchiveOutputStream stream = this.logRecord.getZipStream(() -> {
                        try {
                            final ZipArchiveOutputStream newStream = this.logRecord.createZipStream(ctx);
                            final LogFileRecord fileL = this.logRecord.getFile();
                            StringBuilder sb2 = null;
                            if (StringUtils.isNotBlank(fileL.getCategory())) {
                                sb2 = new StringBuilder().append(fileL.getCategory());
                            }
                            if (StringUtils.isNotBlank(fileL.getOriginalFileName())) {
                                if (sb2 == null) {
                                    sb2 = new StringBuilder();
                                }
                                else {
                                    sb2.append(FileSystems.getDefault().getSeparator());
                                }
                                sb2.append(fileL.getOriginalFileName());
                            }
                            final ZipArchiveEntry entry2 = newStream.createArchiveEntry(fileL.getFilePath().toFile(), Objects.toString(sb2, String.format("file_%5f.bin", Math.random())));
                            newStream.putArchiveEntry(entry2);
                            try {
                                FileUtils.copyFile(fileL.getFilePath().toFile(), (OutputStream)newStream);
                            }
                            finally {
                                newStream.closeArchiveEntry();
                            }
                            return newStream;
                        }
                        catch (final IOException e2) {
                            throw new ServiceException((Exception)e2);
                        }
                    });
                    StringBuilder sb = null;
                    if (this.logRecord.getStage() != null) {
                        sb = new StringBuilder().append(this.logRecord.getStage());
                    }
                    if (StringUtils.isNotBlank(fileName)) {
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        else {
                            sb.append(FileSystems.getDefault().getSeparator());
                        }
                        sb.append(fileName);
                    }
                    if (StringUtils.isNotBlank(extension)) {
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        else {
                            sb.append(".");
                        }
                        sb.append(extension);
                    }
                    final ZipArchiveEntry entry = new ZipArchiveEntry(Objects.toString(sb, String.format("file_%5f.bin", Math.random())));
                    entry.setSize((long)data.length);
                    entry.setTime(ZonedDateTime.now().toEpochSecond());
                    final FileTime ft = FileTime.from(ZonedDateTime.now().toInstant());
                    entry.setLastModifiedTime(ft);
                    entry.setCreationTime(ft);
                    entry.setLastAccessTime(ft);
                    stream.putArchiveEntry(entry);
                    try {
                        stream.write(data);
                    }
                    finally {
                        stream.closeArchiveEntry();
                    }
                }
            }
            catch (final Exception e) {
                IntegrationLogRecord.logger.error(() -> String.format("Error writing integration log file data %s.%s", fileName, extension), (Throwable)e);
            }
            return this;
        }
        
        @Override
        public IntegrationLogRecord build() {
            if (this.logRecord.getZipStream(null) != null && this.logRecord.isTemp()) {
                try {
                    if (this.logRecord.getFile() != null) {
                        Files.delete(this.logRecord.getFile().getFilePath());
                    }
                }
                catch (final IOException e) {
                    IntegrationLogRecord.logger.warn("Error deleting temporaly integration log file data", (Throwable)e);
                }
                this.logRecord.setFile(this.logRecord.getZipFile().getFileName().toString(), this.logRecord.getZipFile());
            }
            if (this.logRecord.getZipStream(null) != null) {
                try {
                    this.logRecord.getZipStream(null).close();
                }
                catch (final IOException e) {
                    IntegrationLogRecord.logger.error("Error writing integration log file data", (Throwable)e);
                }
            }
            return this.logRecord;
        }
        
        @Override
        public void close() {
            this.logRecord.close();
        }
        
        @Override
        public ILogFileBuilder setLogFilePrefix(final String value) {
            this.logRecord.setLogFileName(value, true);
            return this;
        }
        
        @Override
        public void addExceptionFile(final String fileName, final String extension, final Exception e, final SessionContext ctx) {
            this.addFile(fileName, extension, ExceptionUtils.getFullStackTrace((Throwable)e).getBytes(StandardCharsets.UTF_8), ctx);
        }
        
        @Override
        public void addMessage(final String fileName, final String extension, final String message, final SessionContext ctx) {
            this.addFile(fileName, extension, message.getBytes(StandardCharsets.UTF_8), ctx);
        }
        
        @Override
        public Path getTempFile(final String fileName, final String extension, final SessionContext ctx) {
            if (this.logRecord.getStorageDirectory() != null) {
                try {
                    return this.logRecord.getRandomFile(fileName, extension, this.logRecord.getStorageDirectory(), this.logRecord.getPathTemplate(), false, false, ctx);
                }
                catch (final Exception e) {
                    IntegrationLogRecord.logger.error(() -> String.format("Error creating log file data %s.%s", fileName, extension), (Throwable)e);
                }
            }
            return null;
        }
    }
    
    public interface IBuilder extends AutoCloseable
    {
        IBuilder2 setStorageDirectory(final Path p0);
        
        void close();
    }
    
    public interface IBuilder2 extends AutoCloseable
    {
        ILogFileBuilder setLogFilePrefix(final String p0);
        
        void close();
    }
    
    public interface ILogFileBuilder extends AutoCloseable
    {
        IStageBuilder setStage(final Stage p0);
        
        IStageBuilder setStage(final String p0);
        
        IStageBuilder getActiveStage();
        
        ILogFileBuilder setLogFilePrefix(final String p0);
        
        ILogFileBuilder setLogFilenameTemplate(final String p0);
        
        void close();
        
        Path getTempFile(final String p0, final String p1, final SessionContext p2);
    }
    
    public interface IStageBuilder extends ILogFileBuilder
    {
        @Deprecated
        IStageBuilder addFile(final String p0, final String p1, final byte[] p2);
        
        IStageBuilder addFile(final String p0, final String p1, final byte[] p2, final SessionContext p3);
        
        @Deprecated
        IStageBuilder addFile(final Path p0, final boolean p1);
        
        IStageBuilder addFile(final Path p0, final boolean p1, final SessionContext p2);
        
        @Deprecated
        IStageBuilder addFile(final Path p0, final String p1, final boolean p2);
        
        IStageBuilder addFile(final Path p0, final String p1, final boolean p2, final SessionContext p3);
        
        IntegrationLogRecord build();
        
        void addExceptionFile(final String p0, final String p1, final Exception p2, final SessionContext p3);
        
        void addMessage(final String p0, final String p1, final String p2, final SessionContext p3);
    }
}
