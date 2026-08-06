// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import ru.wstack.commons.logging.WSException;
import java.util.Properties;
import ru.wstack.wsscheduler.Initializer;

public class ServiceRegInitializer extends Initializer
{
    public void init(final Properties properties) {
        try {
            ServiceAndTypeInitializer.init();
        }
        catch (final Exception ex) {
            throw new WSException(ex);
        }
    }
}
