// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.schedule;

import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.io.Reader;
import java.io.InputStream;
import ru.korusconsulting.dmservice.objectmodel.documents.JoinFTTypes;
import ru.korusconsulting.dmservice.objectmodel.documents.XmlDocumentTypes;
import ru.korusconsulting.dmservice.objectmodel.XmlTypes;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.dmservice.config.alt.ConfigBase;
import java.io.File;
import java.util.ArrayList;
import ru.korusconsulting.dmservice.ServiceAndTypeInitializer;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import java.util.Collections;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.commons.ApplicationProperties;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.commons.SessionContext;
import org.apache.logging.log4j.Logger;
import ru.wstack.wsscheduler.ScheduledTask;

public class DiadodocEdoStatusChecker extends ScheduledTask
{
    private static final Logger logger;
    
    public void invoke() {
        final SessionContext ctx = new SessionContext();
        try (final DMSShardsClientSession session = new DMSShardsClientSession()) {
            session.initClientSession(ApplicationProperties.getProperty("cslogin"), ApplicationProperties.getProperty("cspassword"));
            new ShardSessionRunner(() -> {
                DiadocEdoServiceReg.getDiadocEdoIntegrationService().getRemoteDocumentState(session, Collections.emptyMap(), ctx);
                return null;
            }).start(DMSShardsManager.SHARD.DICT, ctx);
        }
        catch (final Exception e) {
            DiadodocEdoStatusChecker.logger.error("Error while checking status", (Throwable)e);
        }
    }
    
    public static void main(final String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("Need config root path as param");
            return;
        }
        final String appPath = args[0];
        final Properties appProperties = new Properties();
        try (final InputStream in = ApplicationProperties.class.getResourceAsStream("/app.properties");
             final Reader r = new InputStreamReader(in, Charset.forName("UTF-8"))) {
            appProperties.load(r);
        }
        ApplicationProperties.init(appProperties);
        DMSShardsManager.initShards();
        DMSShardsClientSession.syncShards = false;
        ServiceAndTypeInitializer.init();
        ru.korusconsulting.dmservice.integration.diadocedo.ServiceAndTypeInitializer.init();
        final String[] configAppPaths = { "scheduler/config/base", "scheduler/config/og", "modules/diadoc-edo/diadoc-edo-commons/src/main/resources" };
        if (configAppPaths != null) {
            final List<String> realConfigAppPaths = new ArrayList<String>();
            final String[] array = configAppPaths;
            for (int length = array.length, i = 0; i < length; ++i) {
                final String configAppPath = array[i];
                realConfigAppPaths.add(new File(appPath + "/" + configAppPath).getAbsolutePath());
            }
            final ConfigBase cb = new ConfigBase();
            cb.init((String[])realConfigAppPaths.toArray(new String[0]));
            ServiceReg.getConfigService().setInternalConfig((Object)cb);
        }
        XmlTypes.initTypes(new SessionContext());
        XmlDocumentTypes.initRootDocumentTypes(new SessionContext());
        JoinFTTypes.initTypes(new SessionContext());
        XmlTypes.initAspects(new SessionContext());
        ServiceReg.getIntegrationLogService().initFromConfig(new SessionContext());
        ServiceReg.getSystemLogService().initWorkers(new SessionContext());
        ServiceReg.getIntegrationLogService().initWorkers(new SessionContext());
        final DiadodocEdoStatusChecker exec = new DiadodocEdoStatusChecker();
        exec.init();
        exec.invoke();
        Thread.sleep(5000L);
        System.exit(0);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
