// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import java.nio.file.Path;
import Diadoc.Api.print.models.PrintFormResult;
import java.io.IOException;
import Diadoc.Api.exceptions.DiadocSdkException;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import org.apache.commons.io.FileUtils;
import java.nio.file.FileSystems;
import java.io.File;
import java.util.Map;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoApi;
import ru.korusconsulting.service.integration.diadocedo.IPrintFormGenerator;

public class DiadocEdoDocumentPrintFormGenerator implements IPrintFormGenerator
{
    private final DiadocEdoApi api;
    private final String boxId;
    
    public DiadocEdoDocumentPrintFormGenerator(final DiadocEdoApi api, final String boxId) {
        this.api = api;
        this.boxId = boxId;
    }
    
    @Override
    public File generate(final Map<String, String> params) {
        try {
            String messageId;
            String entityId;
            PrintFormResult pfr;
            for (messageId = params.get("message_id"), entityId = params.get("entity_id"), pfr = this.api.getPrintFormClient().generatePrintForm(this.boxId, messageId, entityId); pfr.getRetryAfter() > 0; pfr = this.api.getPrintFormClient().generatePrintForm(this.boxId, messageId, entityId)) {
                try {
                    Thread.sleep(pfr.getRetryAfter() * 1000);
                }
                catch (final InterruptedException e) {
                    return null;
                }
            }
            if (pfr.HasContent()) {
                final Path tmpDir = Files.createTempDirectory(FileSystems.getDefault().getPath(FileUtils.getTempDirectoryPath(), new String[0]), "content", (FileAttribute<?>[])new FileAttribute[0]);
                final byte[] ec = pfr.getContent().getBytes();
                final Path sdf = Files.createFile(FileSystems.getDefault().getPath(tmpDir.toString(), entityId + ".pdf"), (FileAttribute<?>[])new FileAttribute[0]).normalize();
                final File pfrFile = sdf.toFile();
                try (final FileOutputStream fos = new FileOutputStream(pfrFile)) {
                    fos.write(ec);
                }
                pfrFile.deleteOnExit();
                return pfrFile;
            }
            return null;
        }
        catch (final DiadocSdkException | IOException e2) {
            throw new ServiceException(e2);
        }
    }
}
