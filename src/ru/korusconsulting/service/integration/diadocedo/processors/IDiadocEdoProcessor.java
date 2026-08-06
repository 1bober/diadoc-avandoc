// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo.processors;

import java.util.regex.Matcher;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import java.util.Map;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IEdoIntegrationService;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.dmservice.integration.NamedParam;
import java.util.Collection;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationProcessor;

public interface IDiadocEdoProcessor extends IEdoIntegrationProcessor<IDiadocEdoOperationContext>
{
    default String calculateMessage(final IntegrationLogRecordMessage message, final IDiadocEdoOperationContext operationContext, final Collection<NamedParam<IDiadocEdoIntegrationServiceInternal.ParamName, String>> namedArg, final SessionContext ctx) {
        final IEdoIntegrationService service = this.getService();
        if (service instanceof final IDiadocEdoIntegrationServiceInternal serviceInternal) {
            return serviceInternal.calculateMessage(message, (IIntegrationOperationContext)operationContext, namedArg, ctx);
        }
        return message.name();
    }
    
    default void fillConnectionInfoEnvironment(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final Map<String, Object> environment, final SessionContext ctx) {
        final IDiadocEdoConnectionConfig connectionConfig = operationContext.getApiConnectionConfig(session, ctx);
        if (connectionConfig != null) {
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.api_url.name(), connectionConfig.getUrl());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.web_url.name(), connectionConfig.getWebUrl());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.server_name.name(), connectionConfig.getServerName());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.server_id.name(), connectionConfig.getServerId());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.account_id.name(), connectionConfig.getAccountId());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.login.name(), connectionConfig.getLogin());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.account_member_id.name(), connectionConfig.getAccountMemberId());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.from_box_id.name(), connectionConfig.getBoxId());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.from_department.name(), operationContext.getActiveDepartment());
            environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.regulation.name(), operationContext.getActiveRegulation());
            String boxGuid = operationContext.getParam(IDiadocEdoIntegrationServiceInternal.ParamName.box_guid);
            if (StringUtils.isBlank(boxGuid)) {
                boxGuid = connectionConfig.getBoxId();
            }
            Pattern pattern = Pattern.compile("[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}");
            Matcher matcher = pattern.matcher(boxGuid);
            if (matcher.find()) {
                environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.from_box_guid.name(), boxGuid);
            }
            else {
                pattern = Pattern.compile("([0-9a-zA-Z]{8})([0-9a-zA-Z]{4})([0-9a-zA-Z]{4})([0-9a-zA-Z]{4})([0-9a-zA-Z]{12})@*.");
                matcher = pattern.matcher(boxGuid);
                if (matcher.find() && matcher.groupCount() > 5) {
                    final String formatedGuid = String.format("%s-%s-%s-%s-%s", matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                    environment.put(IEdoIntegrationProcessor.ConnectionInfoArgumentNames.from_box_guid.name(), formatedGuid);
                }
            }
        }
    }
}
