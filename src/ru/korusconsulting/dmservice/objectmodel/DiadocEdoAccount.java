// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.objectmodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.korusconsulting.service.utils.StrUtils;
import ru.korusconsulting.service.exceptions.ServiceException;
import ru.korusconsulting.service.utils.ServiceReg;
import java.io.Reader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import java.io.StringReader;
import ru.wstack.net.client.contentserver.IObject;
import ru.wstack.net.client.contentserver.IClientSession;
import ru.korusconsulting.service.objectmodel.annotations.ObjectModel;
import ru.korusconsulting.service.objectmodel.IDiadocEdoAccount;

@ObjectModel(type = "nt_diadoc_edo_account")
public class DiadocEdoAccount extends DmObjectModel implements IDiadocEdoAccount
{
    private static final long serialVersionUID = -8563375827069234819L;
    
    public void populate(final IClientSession session, final IObject po) {
        try {
            this.setValue("sba_enabled", (Object)po.getBoolean("sba_enabled"));
            this.setValue("sida_server", (Object)po.getString("sida_server"));
            this.setValue("ssa_server_name", (Object)po.getString("ssa_server_name"));
            this.setValue("ssa_login", (Object)po.getString("ssa_login"));
            this.setValue("ssa_current_session_key", (Object)po.getString("ssa_current_session_key"));
            this.setValue("sba_dont_chk_cntagent_sign_cert", (Object)po.getBoolean("sba_dont_chk_cntagent_sign_cert"));
            this.setValue("sia_inc_docs_check_offset", (Object)po.getInt("sia_inc_docs_check_offset"));
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < po.getValueCount("rsa_params"); ++i) {
                sb.append(po.getRepeatingString("rsa_params", i));
            }
            if (sb.length() > 0) {
                final StringReader reader = new StringReader(sb.toString());
                final Properties value = (Properties)new ObjectMapper().readValue((Reader)reader, (TypeReference)new TypeReference<Properties>() {});
                final byte[] decriptedPass = ServiceReg.getCipherService().decryptFromHex(value.getProperty("user_password"));
                this.setValue("ssa_password", (Object)((decriptedPass == null) ? null : new String(decriptedPass)));
            }
        }
        catch (final Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void commitNew(final IObject po) {
        po.setBoolean("sba_enabled", this.isEnabled());
        po.setString("sida_server", this.getServer());
        po.setString("ssa_server_name", this.getServerName());
        po.setString("ssa_login", this.getLogin());
        po.setString("ssa_current_session_key", this.getCurrentSessionKey());
        po.setBoolean("sba_dont_chk_cntagent_sign_cert", this.isDontCheckCounteragentSignCertificate());
        po.setInt("sia_inc_docs_check_offset", this.getIncDocsCheckOffset());
        final Properties internalObject = new Properties();
        internalObject.put("user_password", ServiceReg.getCipherService().encryptToHex(this.getPassword()));
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String paramsStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString((Object)internalObject);
            final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
            po.removeAll("rsa_params");
            for (final String token : paramsArr) {
                po.appendString("rsa_params", token);
            }
        }
        catch (final JsonProcessingException e) {
            throw new ServiceException((Exception)e);
        }
    }
    
    public void commitSaved(final IObject po) {
        if (this.isValueDirty("sba_enabled")) {
            po.setBoolean("sba_enabled", this.isEnabled());
        }
        if (this.isValueDirty("sida_server")) {
            po.setString("sida_server", this.getServer());
        }
        if (this.isValueDirty("ssa_server_name")) {
            po.setString("ssa_server_name", this.getServerName());
        }
        if (this.isValueDirty("ssa_login")) {
            po.setString("ssa_login", this.getLogin());
        }
        if (this.isValueDirty("ssa_password")) {
            try {
                final Properties internalObject = new Properties();
                internalObject.put("user_password", ServiceReg.getCipherService().encryptToHex(this.getPassword()));
                final ObjectMapper mapper = new ObjectMapper();
                final String paramsStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString((Object)internalObject);
                final String[] paramsArr = StrUtils.splitByLength(paramsStr, 1999);
                po.removeAll("rsa_params");
                for (final String token : paramsArr) {
                    po.appendString("rsa_params", token);
                }
            }
            catch (final JsonProcessingException e) {
                throw new ServiceException((Exception)e);
            }
        }
        if (this.isValueDirty("ssa_current_session_key")) {
            po.setString("ssa_current_session_key", this.getCurrentSessionKey());
        }
        if (this.isValueDirty("sba_dont_chk_cntagent_sign_cert")) {
            po.setBoolean("sba_dont_chk_cntagent_sign_cert", this.isDontCheckCounteragentSignCertificate());
        }
        if (this.isValueDirty("sia_inc_docs_check_offset")) {
            po.setInt("sia_inc_docs_check_offset", this.getIncDocsCheckOffset());
        }
    }
    
    public boolean isEnabled() {
        return this.getValueAsBoolean("sba_enabled");
    }
    
    public void setEnabled(final boolean value) {
        this.setValue("sba_enabled", (Object)value);
    }
    
    public String getServer() {
        return this.getValueAsString("sida_server");
    }
    
    public void setServer(final String value) {
        this.setValue("sida_server", (Object)value);
    }
    
    public String getServerName() {
        return this.getValueAsString("ssa_server_name");
    }
    
    public void setServerName(final String filialName) {
        this.setValue("ssa_server_name", (Object)filialName);
    }
    
    public String getLogin() {
        return this.getValueAsString("ssa_login");
    }
    
    public void setLogin(final String login) {
        this.setValue("ssa_login", (Object)login);
    }
    
    public String getPassword() {
        return this.getValueAsString("ssa_password");
    }
    
    public void setPassword(final String password) {
        this.setValue("ssa_password", (Object)password);
    }
    
    public String getCurrentSessionKey() {
        return this.getValueAsString("ssa_current_session_key");
    }
    
    public void setCurrentSessionKey(final String value) {
        this.setValue("ssa_current_session_key", (Object)value);
    }
    
    public boolean isDontCheckCounteragentSignCertificate() {
        return this.getValueAsBoolean("sba_dont_chk_cntagent_sign_cert");
    }
    
    public void setDontCheckCounteragentSignCertificate(final boolean value) {
        this.setValue("sba_dont_chk_cntagent_sign_cert", (Object)value);
    }
    
    public void setIncDocsCheckOffset(final int value) {
        this.setValue("sia_inc_docs_check_offset", (Object)value);
    }
    
    public int getIncDocsCheckOffset() {
        return this.getValueAsInt("sia_inc_docs_check_offset");
    }
}
