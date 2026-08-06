// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import org.apache.logging.log4j.LogManager;
import ru.korusconsulting.service.IModuleRegService;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationService;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import ru.korusconsulting.service.utils.ServiceReg;
import ru.korusconsulting.service.IService;
import java.util.Iterator;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class DiadocEdoServiceReg
{
    public static final String MODULE_NAME = "DIADOC_EDO";
    private static Logger logger;
    
    private DiadocEdoServiceReg() {
    }
    
    public static void initServices(final Set<Class<?>> annotatedServices) {
        try {
            for (final Class<?> cls : annotatedServices) {
                addService(cls);
            }
        }
        catch (final Exception e) {
            DiadocEdoServiceReg.logger.error(String.format("Failed to register services for %s", "DIADOC_EDO"));
        }
    }
    
    private static void addService(final Class<?> annotatedService) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        final ServiceDiadocEdo serviceAnno = annotatedService.getAnnotation(ServiceDiadocEdo.class);
        if (serviceAnno != null) {
            final IService service = (IService)annotatedService.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            Optional.ofNullable(ServiceReg.getModuleRegService()).ifPresent(a -> a.registerModuleService("DIADOC_EDO", serviceAnno.name().name(), service));
        }
    }
    
    public static IDiadocEdoIntegrationService getDiadocEdoIntegrationService() {
        return (IDiadocEdoIntegrationService)ServiceReg.getModuleRegService().getModuleService("DIADOC_EDO", SERVICE_DIADOC_EDO.DIADOC_EDO_INTEGRATION_SERVICE.name());
    }
    
    static {
        DiadocEdoServiceReg.logger = LogManager.getLogger((Class)DiadocEdoServiceReg.class);
    }
}
