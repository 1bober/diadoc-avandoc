// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import Diadoc.Api.document.DocumentClientCustom;
import Diadoc.Api.print.PrintFormClientCustom;
import Diadoc.Api.docflow.DocflowClientCustom;
import Diadoc.Api.auth.AuthenticateClient;
import Diadoc.Api.generateTitle.GenerateClientCustom;
import Diadoc.Api.events.EventsClientCustom;
import Diadoc.Api.shelf.ShelfClientCustom;
import Diadoc.Api.counteragent.CounteragentClientCustom;
import Diadoc.Api.message.MessageClientCustom;
import Diadoc.Api.organizations.OrganizationClientCustom;

public interface IDiadocEdoApi extends AutoCloseable
{
    OrganizationClientCustom getOrganizationClientCustom();
    
    MessageClientCustom getMessageClientCustom();
    
    CounteragentClientCustom getCounteragentClientCustom();
    
    ShelfClientCustom getShelfClientCustom();
    
    EventsClientCustom getEventsClientCustom();
    
    GenerateClientCustom getGenerateClientCustom();
    
    void close();
    
    AuthenticateClient getAuthClient();
    
    DocflowClientCustom getDocflowClientCustom();
    
    PrintFormClientCustom getPrintFormClientCustom();
    
    DocumentClientCustom getDocumentClientCustom();
}
