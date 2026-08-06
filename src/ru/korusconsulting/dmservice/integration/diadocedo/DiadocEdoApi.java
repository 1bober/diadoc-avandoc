// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import Diadoc.Api.events.EventsClientCustom;
import Diadoc.Api.shelf.ShelfClientCustom;
import Diadoc.Api.counteragent.CounteragentClientCustom;
import Diadoc.Api.docflow.DocflowClientCustom;
import Diadoc.Api.docflow.DocflowClient;
import Diadoc.Api.sign.SignClient;
import Diadoc.Api.print.PrintFormClientCustom;
import Diadoc.Api.print.PrintFormClient;
import Diadoc.Api.events.EventsClient;
import Diadoc.Api.document.DocumentClientCustom;
import Diadoc.Api.document.DocumentClient;
import Diadoc.Api.message.MessageClientCustom;
import Diadoc.Api.message.MessageClient;
import Diadoc.Api.shelf.ShelfClient;
import Diadoc.Api.generateTitle.GenerateClientCustom;
import Diadoc.Api.generateTitle.GenerateClient;
import Diadoc.Api.counteragent.CounteragentClient;
import Diadoc.Api.user.UserClient;
import Diadoc.Api.employee.EmployeeClient;
import Diadoc.Api.organizations.OrganizationClientCustom;
import Diadoc.Api.organizations.OrganizationClient;
import Diadoc.Api.department.DepartmentClient;
import Diadoc.Api.auth.AuthenticateClient;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import java.util.function.BiConsumer;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import java.util.function.Supplier;
import Diadoc.Api.DiadocApi;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;

public class DiadocEdoApi implements IDiadocEdoApi
{
    private final DiadocApi api;
    
    public DiadocEdoApi(final String apiClientId, final String url, final Supplier<SSLConnectionSocketFactory> sslSocketFactoryProvider, final BiConsumer<HttpClientBuilder, CredentialsProvider> preprocessor) {
        this.api = new DiadocApi(apiClientId, url, (Supplier)sslSocketFactoryProvider, (BiConsumer)preprocessor);
    }
    
    public DiadocApi getApi() {
        return this.api;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public AuthenticateClient getAuthClient() {
        return this.getApi().getAuthClient();
    }
    
    public DepartmentClient getDepartmentClient() {
        return this.getApi().getDepartmentClient();
    }
    
    public OrganizationClient getOrganizationClient() {
        return this.getApi().getOrganizationClient();
    }
    
    @Override
    public OrganizationClientCustom getOrganizationClientCustom() {
        return this.getApi().getOrganizationClientCustom();
    }
    
    public EmployeeClient getEmployeeClient() {
        return this.getApi().getEmployeeClient();
    }
    
    public UserClient getUserClient() {
        return this.getApi().getUserClient();
    }
    
    public CounteragentClient getCounteragentClient() {
        return this.getApi().getCounteragentClient();
    }
    
    public GenerateClient getGenerateClient() {
        return this.getApi().getGenerateClient();
    }
    
    @Override
    public GenerateClientCustom getGenerateClientCustom() {
        return this.getApi().getGenerateClientCustom();
    }
    
    public ShelfClient getShelfClient() {
        return this.getApi().getShelfClient();
    }
    
    public MessageClient getMessageClient() {
        return this.getApi().getMessageClient();
    }
    
    @Override
    public MessageClientCustom getMessageClientCustom() {
        return this.getApi().getMessageClientCustom();
    }
    
    public DocumentClient getDocumentClient() {
        return this.getApi().getDocumentClient();
    }
    
    @Override
    public DocumentClientCustom getDocumentClientCustom() {
        return this.getApi().getDocumentClientCustom();
    }
    
    public EventsClient getEventsClient() {
        return this.getApi().getEventsClient();
    }
    
    public PrintFormClient getPrintFormClient() {
        return this.getApi().getPrintFormClient();
    }
    
    @Override
    public PrintFormClientCustom getPrintFormClientCustom() {
        return this.getApi().getPrintFormClientCustom();
    }
    
    public SignClient getSignClient() {
        return this.getApi().getSignClient();
    }
    
    public DocflowClient getDocflowClient() {
        return this.getApi().getDocflowClient();
    }
    
    @Override
    public DocflowClientCustom getDocflowClientCustom() {
        return this.getApi().getDocflowClientCustom();
    }
    
    @Override
    public CounteragentClientCustom getCounteragentClientCustom() {
        return this.getApi().getCounteragentClientCustom();
    }
    
    @Override
    public ShelfClientCustom getShelfClientCustom() {
        return this.getApi().getShelfClientCustom();
    }
    
    @Override
    public EventsClientCustom getEventsClientCustom() {
        return this.getApi().getEventsClientCustom();
    }
}
