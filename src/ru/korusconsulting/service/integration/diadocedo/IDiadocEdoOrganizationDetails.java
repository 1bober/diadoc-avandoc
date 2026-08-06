// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import ru.korusconsulting.service.integration.IOrganizationDetails;

public interface IDiadocEdoOrganizationDetails extends IOrganizationDetails
{
    String getBoxId();
    
    String getDepartmentId();
    
    String getDepartmentName();
}
