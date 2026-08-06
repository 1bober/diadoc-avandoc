// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import java.nio.file.FileSystems;

public interface IIntegrationLogSettings
{
    public static final String FILENAME_DATE_TEMPLATE = String.format("@{CURRENTDATETIME(yyyy)}%s@{CURRENTDATETIME(MM)}%s@{CURRENTDATETIME(dd)}%s@{PREFIX}_@{CURRENTDATETIME(MM-dd_HH-mm-ss)}_@{%s}_@{RANDOM}.@{EXTENSION}", FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator(), "__objectId");
    public static final String FILENAME_HOUR_TEMPLATE = String.format("@{CURRENTDATETIME(yyyy)}%s@{CURRENTDATETIME(MM)}%s@{CURRENTDATETIME(dd)}%s@{CURRENTDATETIME(HH)}%s@{PREFIX}_@{CURRENTDATETIME(MM-dd_HH-mm-ss)}_@{%s}@{RANDOM}.@{EXTENSION}", FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator(), FileSystems.getDefault().getSeparator(), "__objectId");
    public static final String FILENAME_NONE_TEMPLATE = "@{PREFIX}_@{CURRENTDATETIME(yyyy-MM-dd_HH-mm-ss)}_@{RANDOM}.@{EXTENSION}";
    
    String getFilenameTemplate();
}
