// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import ru.korusconsulting.service.objectmodel.IEdoIntegrationAttachmentAdditionalInfo;
import java.util.Calendar;
import java.io.IOException;
import ru.korusconsulting.service.utils.StrUtils;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.codec.binary.Base64;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.ArrayList;
import ru.korusconsulting.service.objectmodel.IObjectModelAttributeProcessor;
import java.util.List;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationContentSync;

@ObjectModel(type = "nt_edo_integration_content_sync")
public class EdoIntegrationContentSync extends DmObjectModel implements IEdoIntegrationContentSync
{
    private static final long serialVersionUID = -7611411532125657690L;
    private transient List<IObjectModelAttributeProcessor> attributes;
    
    public EdoIntegrationContentSync() {
        this.attributes = new ArrayList<IObjectModelAttributeProcessor>();
        this.appendAttributeDefinition("sida_content", String.class, 16, this::getContentId, this::setContentId);
        this.appendAttributeDefinition("ssa_content_name", String.class, 2000, this::getContentName, this::setContentName);
        this.appendAttributeDefinition("sba_is_service", Boolean.TYPE, 0, this::isService, this::setIsService);
        this.appendAttributeDefinition("sida_document", String.class, 16, this::getDocumentId, this::setDocumentId);
        this.appendAttributeDefinition("ssa_document_type", String.class, 64, this::getDocumentType, this::setDocumentType);
        this.appendAttributeDefinition("ssa_integration", String.class, 16, this::getIntegration, this::setIntegration);
        this.appendAttributeDefinition("ssa_direction", String.class, 16, this::getDirection, this::setDirection);
        this.appendAttributeDefinition("sida_server", String.class, 16, this::getServerId, this::setServerId);
        this.appendAttributeDefinition("ssa_server_name", String.class, 200, this::getServerName, this::setServerName);
        this.appendAttributeDefinition("sida_account", String.class, 16, this::getAccountId, this::setAccountId);
        this.appendAttributeDefinition("ssa_account_name", String.class, 200, this::getAccountName, this::setAccountName);
        this.appendAttributeDefinition("sida_account_member", String.class, 16, this::getAccountMemberId, this::setAccountMemberId);
        this.appendAttributeDefinition("ssa_account_member_name", String.class, 200, this::getAccountMemberName, this::setAccountMemberName);
        this.appendAttributeDefinition("ssa_account_guid", String.class, 200, this::getEdoAccountGuid, this::setEdoAccountGuid);
        this.appendAttributeDefinition("sida_counteragent_container", String.class, 16, this::getCounteragentContainerId, this::setCounteragentContainerId);
        this.appendAttributeDefinition("sida_counteragent", String.class, 16, this::getCounteragentId, this::setCounteragentId);
        this.appendAttributeDefinition("ssa_counteragent_name", String.class, 100, this::getCounteragentName, this::setCounteragentName);
        this.appendAttributeDefinition("ssa_counteragent_account_guid", String.class, 200, this::getCounteragentEdoAccountGuid, this::setCounteragentEdoAccountGuid);
        this.appendAttributeDefinition("ssa_counteragent_inn", String.class, 32, this::getCounteragentInn, this::setCounteragentInn);
        this.appendAttributeDefinition("ssa_counteragent_kpp", String.class, 32, this::getCounteragentKpp, this::setCounteragentKpp);
        this.appendAttributeDefinition("ssa_external_container_guid", String.class, 2000, this::getExternalContainerGuid, this::setExternalContainerGuid);
        this.appendAttributeDefinition("ssa_external_guid", String.class, 2000, this::getExternalGuid, this::setExternalGuid);
        this.appendAttributeDefinition("ssa_external_link", String.class, 2000, this::getExternalLink, this::setExternalLink);
        this.appendAttributeDefinition("ssa_external_printform_link", String.class, 2000, this::getExternalPrintformLink, this::setExternalPrintformLink);
    }
    
    private <E> void appendAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<E> getter, final Consumer<E> setter) {
        this.attributes.add(new ObjectModelAttributeProcessor<Object>(name, (Class<Object>)attrClass, (maxLen == 16) ? 16 : (maxLen / 2), (Consumer<Object>)setter, (Supplier<Object>)getter));
    }
    
    private <E> void appendRepeatingAttributeDefinition(final String name, final Class<E> attrClass, final int maxLen, final Supplier<List<E>> getter, final Consumer<List<E>> setter) {
        this.attributes.add(new ObjectModelRepeatingAttributeProcessor<Object>(name, (Class<Object>)attrClass, (maxLen == 16) ? 16 : (maxLen / 2), (Consumer<List<Object>>)setter, (Supplier<List<Object>>)getter));
    }
    
    public void populate(final IClientSession session, final IObject po) {
        super.populate(session, po);
        try {
            for (final IObjectModelAttributeProcessor attr : this.attributes) {
                attr.populateAttribute((IObjectModel)this, po);
            }
            this.setValue("sdta_create_date", (Object)po.getDate("sdta_create_date"));
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < po.getValueCount("rsa_params"); ++i) {
                sb.append(po.getRepeatingString("rsa_params", i));
            }
            if (!sb.isEmpty()) {
                try (final ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(sb.toString().getBytes()));
                     final ObjectInputStream ois = new ObjectInputStream(bais)) {
                    this.setValue("additional_info", ois.readObject());
                }
            }
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        super.commitNew(po);
        for (final IObjectModelAttributeProcessor attr : this.attributes) {
            attr.commitAttribute((IObjectModel)this, po);
        }
        final IEdoIntegrationAttachmentAdditionalInfo info = this.getAdditionalInfo();
        if (info != null) {
            try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(info);
                oos.flush();
                final String paramsStr = Base64.encodeBase64URLSafeString(baos.toByteArray());
                final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                po.removeAll("rsa_params");
                for (final String token : paramsArr) {
                    po.appendString("rsa_params", token);
                }
            }
            catch (final IOException e) {
                throw new ServiceException((Exception)e);
            }
            info.setDirty(false);
        }
        else {
            po.removeAll("rsa_params");
        }
        po.setDate("sdta_create_date", Calendar.getInstance().getTime());
    }
    
    public void commitSaved(final IObject po) {
        super.commitSaved(po);
        for (final IObjectModelAttributeProcessor attr : this.attributes) {
            attr.commitAttribute((IObjectModel)this, po);
        }
        final IEdoIntegrationAttachmentAdditionalInfo info = this.getAdditionalInfo();
        if (info != null) {
            if (!info.isDirty()) {
                return;
            }
            try {
                try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    try (final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                        oos.writeObject(info);
                        oos.flush();
                        final String paramsStr = Base64.encodeBase64URLSafeString(baos.toByteArray());
                        final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                        po.removeAll("rsa_params");
                        for (final String token : paramsArr) {
                            po.appendString("rsa_params", token);
                        }
                    }
                    info.setDirty(false);
                }
                return;
            }
            catch (final IOException e) {
                throw new ServiceException((Exception)e);
            }
        }
        po.removeAll("rsa_params");
    }
    
    public String getContentId() {
        return this.getValueAsString("sida_content");
    }
    
    public void setContentId(final String value) {
        this.setValue("sida_content", (Object)value);
    }
    
    public String getContentName() {
        return this.getValueAsString("ssa_content_name");
    }
    
    public void setContentName(final String value) {
        this.setValue("ssa_content_name", (Object)value);
    }
    
    public String getDocumentId() {
        return this.getValueAsString("sida_document");
    }
    
    public void setDocumentId(final String value) {
        this.setValue("sida_document", (Object)value);
    }
    
    public String getDocumentType() {
        return this.getValueAsString("ssa_document_type");
    }
    
    public void setDocumentType(final String value) {
        this.setValue("ssa_document_type", (Object)value);
    }
    
    public String getIntegration() {
        return this.getValueAsString("ssa_integration");
    }
    
    public void setIntegration(final String value) {
        this.setValue("ssa_integration", (Object)value);
    }
    
    public String getDirection() {
        return this.getValueAsString("ssa_direction");
    }
    
    public void setDirection(final String value) {
        this.setValue("ssa_direction", (Object)value);
    }
    
    public String getServerId() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServerId(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getServerName() {
        return this.getValueAsString("ssa_server_name");
    }
    
    public void setServerName(final String value) {
        this.setValue("ssa_server_name", (Object)value);
    }
    
    public String getAccountId() {
        return this.getValueAsString("sida_account");
    }
    
    public void setAccountId(final String value) {
        this.setValue("sida_account", (Object)value);
    }
    
    public String getAccountName() {
        return this.getValueAsString("ssa_account_name");
    }
    
    public void setAccountName(final String value) {
        this.setValue("ssa_account_name", (Object)value);
    }
    
    public String getAccountMemberId() {
        return this.getValueAsString("sida_account_member");
    }
    
    public void setAccountMemberId(final String value) {
        this.setValue("sida_account_member", (Object)value);
    }
    
    public String getAccountMemberName() {
        return this.getValueAsString("ssa_account_member_name");
    }
    
    public void setAccountMemberName(final String value) {
        this.setValue("ssa_account_member_name", (Object)value);
    }
    
    public String getEdoAccountGuid() {
        return this.getValueAsString("ssa_account_guid");
    }
    
    public void setEdoAccountGuid(final String value) {
        this.setValue("ssa_account_guid", (Object)value);
    }
    
    public String getCounteragentContainerId() {
        return this.getValueAsString("sida_counteragent_container");
    }
    
    public void setCounteragentContainerId(final String value) {
        this.setValue("sida_counteragent_container", (Object)value);
    }
    
    public String getCounteragentId() {
        return this.getValueAsString("sida_counteragent");
    }
    
    public void setCounteragentId(final String value) {
        this.setValue("sida_counteragent", (Object)value);
    }
    
    public String getCounteragentName() {
        return this.getValueAsString("ssa_counteragent_name");
    }
    
    public void setCounteragentName(final String value) {
        this.setValue("ssa_counteragent_name", (Object)value);
    }
    
    public String getCounteragentInn() {
        return this.getValueAsString("ssa_counteragent_inn");
    }
    
    public void setCounteragentInn(final String value) {
        this.setValue("ssa_counteragent_inn", (Object)value);
    }
    
    public String getCounteragentKpp() {
        return this.getValueAsString("ssa_counteragent_kpp");
    }
    
    public void setCounteragentKpp(final String value) {
        this.setValue("ssa_counteragent_kpp", (Object)value);
    }
    
    public String getCounteragentEdoAccountGuid() {
        return this.getValueAsString("ssa_counteragent_account_guid");
    }
    
    public void setCounteragentEdoAccountGuid(final String value) {
        this.setValue("ssa_counteragent_account_guid", (Object)value);
    }
    
    public String getExternalContainerGuid() {
        return this.getValueAsString("ssa_external_container_guid");
    }
    
    public void setExternalContainerGuid(final String value) {
        this.setValue("ssa_external_container_guid", (Object)value);
    }
    
    public String getExternalGuid() {
        return this.getValueAsString("ssa_external_guid");
    }
    
    public void setExternalGuid(final String value) {
        this.setValue("ssa_external_guid", (Object)value);
    }
    
    public String getExternalLink() {
        return this.getValueAsString("ssa_external_link");
    }
    
    public void setExternalLink(final String value) {
        this.setValue("ssa_external_link", (Object)value);
    }
    
    public String getExternalPrintformLink() {
        return this.getValueAsString("ssa_external_printform_link");
    }
    
    public void setExternalPrintformLink(final String value) {
        this.setValue("ssa_external_printform_link", (Object)value);
    }
    
    public boolean isService() {
        return this.getValueAsBoolean("sba_is_service");
    }
    
    public void setIsService(final boolean value) {
        this.setValue("sba_is_service", (Object)value);
    }
    
    public IEdoIntegrationAttachmentAdditionalInfo getAdditionalInfo() {
        return (IEdoIntegrationAttachmentAdditionalInfo)this.getValue("additional_info");
    }
    
    public void setAdditionalInfo(final IEdoIntegrationAttachmentAdditionalInfo value) {
        this.setValue("additional_info", (Object)value);
    }
}
