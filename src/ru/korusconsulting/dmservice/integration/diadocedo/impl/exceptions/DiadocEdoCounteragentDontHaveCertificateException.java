// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions;

import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationSubsystemErrorCode;

public class DiadocEdoCounteragentDontHaveCertificateException extends DiadocEdoIntegrationSubsystemErrorException
{
    private static final long serialVersionUID = -6724996862009814755L;
    private final String inn;
    private final String kpp;
    
    public DiadocEdoCounteragentDontHaveCertificateException(final String inn, final String kpp) {
        super(String.format("Counteragent with inn %s and kpp %s dosn't has certificate to sign documents in Diadoc.EDO", inn, kpp));
        this.inn = inn;
        this.kpp = kpp;
    }
    
    public String getInn() {
        return this.inn;
    }
    
    public String getKpp() {
        return this.kpp;
    }
    
    public EdoIntegrationSubsystemErrorCode getErrorCode() {
        return EdoIntegrationSubsystemErrorCode.COUNTERAGENT_NO_CERTIFICATE;
    }
    
    @Override
    public IntegrationLogRecordMessage getIntegrationLogMessage() {
        return IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_COUNTERAGENT_DONT_HAVE_CERTIFICATE;
    }
}
