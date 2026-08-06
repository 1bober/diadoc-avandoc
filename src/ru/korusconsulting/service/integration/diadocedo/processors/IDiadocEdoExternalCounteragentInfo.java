// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

public interface IDiadocEdoExternalCounteragentInfo
{
    String getBoxIdGuid();
    
    String getBoxId();
    
    String getOrgId();
    
    String getOrgName();
    
    String getDepartmentId();
    
    String getDepartmentName();
    
    String getFnsParticipantId();
    
    boolean isRoaming();
    
    boolean hasCertificateToSign();
    
    boolean IsForeign();
}
