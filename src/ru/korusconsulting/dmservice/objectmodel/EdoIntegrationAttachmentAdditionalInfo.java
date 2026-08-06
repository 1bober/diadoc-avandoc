// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import java.util.Collection;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentSignature;
import java.util.List;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentAdditionalInfo;

public class EdoIntegrationAttachmentAdditionalInfo implements IEdoIntegrationAttachmentAdditionalInfo
{
    private static final long serialVersionUID = 3644031564652473885L;
    private transient boolean dirty;
    private final List<IEdoIntegrationAttachmentSignature> signaturies;
    
    public EdoIntegrationAttachmentAdditionalInfo() {
        this.signaturies = new ArrayList<IEdoIntegrationAttachmentSignature>();
    }
    
    @Override
    public List<IEdoIntegrationAttachmentSignature> getSignaturies() {
        return List.copyOf((Collection<? extends IEdoIntegrationAttachmentSignature>)this.signaturies);
    }
    
    @Override
    public boolean isDirty() {
        return this.dirty;
    }
    
    @Override
    public void addSignatureInfo(final IEdoIntegrationAttachmentSignature value) {
        this.signaturies.add(value);
        this.dirty = true;
    }
    
    @Override
    public void removeAllSignaturies() {
        this.signaturies.clear();
        this.dirty = true;
    }
    
    @Override
    public void setDirty(final boolean value) {
        this.dirty = value;
    }
}
