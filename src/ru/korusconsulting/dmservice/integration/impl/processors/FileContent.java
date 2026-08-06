// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.processors;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

class FileContent implements IFileContent
{
    public static final long signatureMaxSize = 1048576L;
    private final Path file;
    private byte[] signature;
    private String certificateThumbprint;
    private FileContentType contentType;
    
    public FileContent(final Path file) {
        this.contentType = FileContentType.ctUndefined;
        this.file = file;
    }
    
    public FileContent(final Path file, final FileContentType contentType) {
        this.contentType = FileContentType.ctUndefined;
        this.file = file;
        this.contentType = contentType;
    }
    
    @Override
    public boolean isSignature() {
        return this.contentType == FileContentType.ctSignature || this.contentType == FileContentType.ctIdentifiedSignature;
    }
    
    @Override
    public boolean isIdentifiedSignature() {
        return this.contentType == FileContentType.ctIdentifiedSignature;
    }
    
    @Override
    public boolean isNotIdentifiedSignature() {
        return this.contentType == FileContentType.ctSignature;
    }
    
    @Override
    public boolean isSignedContent() {
        return this.contentType == FileContentType.ctSignedContent;
    }
    
    @Override
    public boolean isContent() {
        return this.contentType == FileContentType.ctSignedContent || this.contentType == FileContentType.ctContent;
    }
    
    @Override
    public Path getPath() {
        return this.file;
    }
    
    @Override
    public long length() {
        return (this.file == null) ? 0L : this.file.toFile().length();
    }
    
    @Override
    public boolean exists() {
        return this.file != null && Files.isRegularFile(this.file, new LinkOption[0]);
    }
    
    @Override
    public byte[] getSignature() {
        return this.signature;
    }
    
    @Override
    public void setSignature(final byte[] value) {
        this.signature = value;
        this.contentType = ((value == null) ? FileContentType.ctContent : FileContentType.ctSignedContent);
    }
    
    public static IFileContent createContent(final Path sourceFile) {
        return new FileContent(sourceFile, FileContentType.ctContent);
    }
    
    @Override
    public String getCertificateThumbprint() {
        return this.certificateThumbprint;
    }
    
    @Override
    public void setCertificateThumbprint(final String certificateThumbprint) {
        this.certificateThumbprint = certificateThumbprint;
    }
    
    public enum FileContentType
    {
        ctUndefined, 
        ctContent, 
        ctSignedContent, 
        ctSignature, 
        ctIdentifiedSignature;
    }
}
