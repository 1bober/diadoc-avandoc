// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.batchselect;

import java.util.Objects;
import java.util.Iterator;
import java.util.Collection;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.batchselect.IBatchItemsStorage;

public abstract class BatchItemsAccumulator<K, S, T>
{
    private final int minBatchSize;
    private final IBatchItemsStorage<K, S, T> buffer;
    private K lastKey;
    
    protected BatchItemsAccumulator(final int minBatchSize, final IBatchItemsStorage<K, S, T> storage) {
        this.minBatchSize = minBatchSize;
        this.buffer = storage;
        this.lastKey = null;
    }
    
    public void execute(final DMSShardsClientSession session, final SessionContext ctx) {
        boolean hasMoreData = true;
        int i = 0;
        while (hasMoreData) {
            final Collection<T> fetchedData = this.fetchNextPage(session, i, ctx);
            if (fetchedData == null || fetchedData.isEmpty()) {
                hasMoreData = false;
            }
            else {
                for (final T item : fetchedData) {
                    this.processItem(session, item, ctx);
                }
            }
            ++i;
        }
        this.flushBuffer(session, ctx);
    }
    
    protected void processItem(final DMSShardsClientSession session, final T item, final SessionContext ctx) {
        final K currentKey = this.extractKey(session, item, ctx);
        if (this.lastKey == null) {
            this.lastKey = currentKey;
        }
        final boolean thresholdReached = this.buffer.size() >= this.minBatchSize;
        final boolean keyChanged = !Objects.equals(currentKey, this.lastKey);
        if (thresholdReached && keyChanged) {
            this.flushBuffer(session, ctx);
            this.lastKey = currentKey;
        }
        else if (keyChanged) {
            this.lastKey = currentKey;
        }
        this.buffer.put(currentKey, item);
    }
    
    protected void flushBuffer(final DMSShardsClientSession session, final SessionContext ctx) {
        if (!this.buffer.isEmpty()) {
            this.processBatch(session, this.lastKey, this.buffer, ctx);
            this.buffer.clear();
        }
    }
    
    protected int getBatchSize() {
        return this.minBatchSize;
    }
    
    protected abstract Collection<T> fetchNextPage(final DMSShardsClientSession p0, final int p1, final SessionContext p2);
    
    protected abstract K extractKey(final DMSShardsClientSession p0, final T p1, final SessionContext p2);
    
    protected abstract void processBatch(final DMSShardsClientSession p0, final K p1, final IBatchItemsStorage<K, S, T> p2, final SessionContext p3);
}
