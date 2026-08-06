// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import java.util.Map;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoAccountsNotAvailable;
import Diadoc.Api.Proto.OrganizationProtos;

public interface IDiadocEdoApiAccountConnection
{
    IDiadocEdoApi getApi();
    
    String getServerId();
    
    String getServerName();
    
    String getAccountId();
    
    String getAccountName();
    
    String getUrl();
    
    String getWebUrl();
    
    boolean isInvalid();
    
    void markInvalid();
    
    OrganizationProtos.Box getBox(final String p0) throws DiadocEdoAccountsNotAvailable;
    
    OrganizationProtos.Box getDefaultBox();
    
    record OrganizationInfo(OrganizationProtos.Organization organization, Map<String, OrganizationProtos.Box> box, Map<String, OrganizationProtos.Department> department) {}
}
