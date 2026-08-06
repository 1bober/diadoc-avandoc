// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration.processors;

import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.objectmodel.IEdoIntegrationExchangeItem;
import java.util.Collection;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;

public interface IEdoIntegrationSendProcessor<T extends IIntegrationOperationContext, R> extends IEdoIntegrationProcessor<T>
{
    ProcessingResult process(final DMSShardsClientSession p0, final T p1, final R p2, final IDocument p3, final Collection<IEdoIntegrationExchangeItem> p4, final SessionContext p5);
    
    public enum ProcessingResult
    {
        SUCCESS, 
        WITHERRORS, 
        FAILED, 
        SKIPPED, 
        NONE;
        
        public ProcessingResult concat(final ProcessingResult newStatus) {
            return concat(this, newStatus);
        }
        
        public static ProcessingResult concat(final ProcessingResult oldStatus, final ProcessingResult newStatus) {
            switch (oldStatus) {
                case FAILED: {
                    switch (newStatus) {
                        case SUCCESS:
                        case WITHERRORS: {
                            return ProcessingResult.WITHERRORS;
                        }
                        default: {
                            return ProcessingResult.FAILED;
                        }
                    }
                    break;
                }
                case SUCCESS: {
                    switch (newStatus) {
                        case WITHERRORS:
                        case FAILED: {
                            return ProcessingResult.WITHERRORS;
                        }
                        default: {
                            return ProcessingResult.SUCCESS;
                        }
                    }
                    break;
                }
                case WITHERRORS: {
                    return ProcessingResult.WITHERRORS;
                }
                default: {
                    return newStatus;
                }
            }
        }
    }
}
