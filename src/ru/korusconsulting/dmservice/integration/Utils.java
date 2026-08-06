// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration;

import org.apache.logging.log4j.LogManager;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.nio.file.LinkOption;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.FileSystems;
import ru.korusconsulting.dmservice.config.alt.RootConfigElement;
import java.util.regex.Matcher;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;

public class Utils
{
    private static final Logger logger;
    
    public static boolean compareWithMask(final String input, final String mask, final char maskChar) {
        if (mask == null || input == null || maskChar == '\0') {
            return false;
        }
        final StringBuilder paddedInput = new StringBuilder(input);
        while (paddedInput.length() < mask.length()) {
            paddedInput.append('0');
        }
        final String finalString = paddedInput.toString();
        if (finalString.length() != mask.length()) {
            return false;
        }
        for (int i = 0; i < mask.length(); ++i) {
            final char m = mask.charAt(i);
            final char s = finalString.charAt(i);
            if (m != maskChar && m != s) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean matchDynamicRegex(final String contextSource, final String contextRegex, final String targetTemplate, final String targetText) {
        final Pattern contextPattern = Pattern.compile(contextRegex);
        final Matcher contextMatcher = contextPattern.matcher(contextSource);
        if (!contextMatcher.find()) {
            return false;
        }
        String finalRegex = targetTemplate;
        for (int i = 1; i <= contextMatcher.groupCount(); ++i) {
            final String groupValue = Objects.toString(contextMatcher.group(i), "");
            final String safeValue = Matcher.quoteReplacement(groupValue);
            finalRegex = finalRegex.replace("$" + i, (CharSequence)safeValue);
        }
        final Pattern targetPattern = Pattern.compile(finalRegex);
        final Matcher targetMatcher = targetPattern.matcher(targetText);
        return targetMatcher.find();
    }
    
    public static void traceConfig(final RootConfigElement configurationConfig, final String operationComponentCode, final String targetDirectory) {
        try {
            final String safeName = Objects.toString(operationComponentCode, "null").replaceAll("[^a-zA-Z0-9._-]", "_");
            final Path path = FileSystems.getDefault().getPath(targetDirectory, new String[0]);
            Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
            Path filePath = path.resolve(String.format("%s.xml", safeName));
            for (int i = 0; Files.exists(filePath, new LinkOption[0]) && i < Integer.MAX_VALUE; filePath = path.resolve(String.format("%s(%d).xml", safeName, i)), ++i) {}
            try (final FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(configurationConfig.asString().getBytes(StandardCharsets.UTF_8));
            }
        }
        catch (final Throwable e) {
            Utils.logger.warn(() -> String.format("Error logging component %s", operationComponentCode), e);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
