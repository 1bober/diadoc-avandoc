// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.diadocedo;

import org.apache.logging.log4j.LogManager;
import java.util.HashMap;
import java.util.Iterator;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import java.util.Map;

public class DiadocEdoModuleProperties
{
    public static final String DIADOC_EDO_BACKUP_DIR = "backup_dir";
    public static final String DIADOC_EDO_BOX_FILTER = "box_filter";
    public static final String DIADOC_EDO_SERVICE_URL = "service_url";
    public static final String DIADOC_EDO_TEST_SERVICE_URL = "test_service_url";
    public static final String DIADOC_EDO_TIME_POLLING_INTERVAL = "time_polling_interval";
    public static final String DIADOC_EDO_BACKUP_FILENAME_TEMPLATE = "backup_filename_template";
    public static final String DIADOC_EDO_CONFIG_FILE = "/diadoc_edo_connector.properties";
    public static final String DIADOC_EDO_ZIP_ENTRY_FILENAME_ENCODING = "zip_entry_filename_encoding";
    public static final String DIADOC_EDO_HTTP_GATEWAY = "http_gateway";
    public static final String DIADOC_EDO_HTTP_GATEWAY_AUTHENTICATION_TYPE = "http_gateway_authentication_type";
    public static final String DIADOC_EDO_HTTP_GATEWAY_AUTHENTICATION_USERNAME = "http_gateway_authentication_username";
    public static final String DIADOC_EDO_HTTP_GATEWAY_AUTHENTICATION_PASSWORD = "http_gateway_authentication_password";
    private static final Map<String, String> props;
    private static volatile boolean inited;
    private static Object mutex;
    private static final Logger logger;
    
    private static Map<String, String> getProps() {
        if (!DiadocEdoModuleProperties.inited) {
            final Properties appProperties = new Properties();
            try (final InputStream in = DiadocEdoModuleProperties.class.getResourceAsStream("/diadoc_edo_connector.properties");
                 final Reader r = new InputStreamReader((in == null) ? InputStream.nullInputStream() : in, StandardCharsets.UTF_8)) {
                if (in == null) {
                    DiadocEdoModuleProperties.logger.error(() -> String.format("Failed to load config file %s", "/diadoc_edo_connector.properties"));
                }
                else {
                    appProperties.load(r);
                }
            }
            catch (final IOException e) {
                DiadocEdoModuleProperties.logger.error(() -> String.format("Error reading properties from file %s", "/diadoc_edo_connector.properties"));
            }
            DiadocEdoModuleProperties.props.clear();
            for (final Map.Entry<Object, Object> p : appProperties.entrySet()) {
                DiadocEdoModuleProperties.props.put(p.getKey().toString(), p.getValue().toString());
            }
            DiadocEdoModuleProperties.inited = true;
        }
        return DiadocEdoModuleProperties.props;
    }
    
    public static String getProperty(final String name) {
        synchronized (DiadocEdoModuleProperties.mutex) {
            return getProps().get(name);
        }
    }
    
    public static void setProperty(final String name, final String value) {
        synchronized (DiadocEdoModuleProperties.mutex) {
            getProps().put(name, value);
        }
    }
    
    private DiadocEdoModuleProperties() {
    }
    
    static {
        props = new HashMap<String, String>();
        DiadocEdoModuleProperties.mutex = new Object();
        logger = LogManager.getLogger();
    }
}
