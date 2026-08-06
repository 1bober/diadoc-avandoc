// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import java.util.UUID;
import java.util.Random;
import java.security.SecureRandom;

public class TimeBasedUUIDGenerator
{
    protected final long uuidL2;
    protected final long uuidL2Rnd;
    public static final int BYTE_OFFSET_CLOCK_SEQUENCE = 8;
    private static TimeBasedUUIDGenerator instance;
    private long lastRawTimestamp;
    private static Object mutex;
    
    public static TimeBasedUUIDGenerator getInstance() {
        synchronized (TimeBasedUUIDGenerator.mutex) {
            if (TimeBasedUUIDGenerator.instance == null) {
                TimeBasedUUIDGenerator.instance = new TimeBasedUUIDGenerator();
            }
            return TimeBasedUUIDGenerator.instance;
        }
    }
    
    private TimeBasedUUIDGenerator() {
        final byte[] uuidBytes = new byte[16];
        final byte[] uuidBytesRnd = new byte[16];
        final Random rnd = new SecureRandom();
        EthernetAddress ethernetAddress = EthernetAddress.fromInterface();
        final EthernetAddress dummyEthernetAddress = EthernetAddress.dummy(rnd);
        if (ethernetAddress == null) {
            ethernetAddress = EthernetAddress.dummy(rnd);
        }
        final byte[] macBytes = ethernetAddress.toByteArray();
        System.arraycopy(macBytes, 0, uuidBytes, 10, 6);
        final byte[] macBytesRnd = dummyEthernetAddress.toByteArray();
        System.arraycopy(macBytesRnd, 0, uuidBytesRnd, 10, 6);
        int clockSeq = rnd.nextInt() & 0xFFFF;
        uuidBytes[8] = (byte)(clockSeq >> 8);
        uuidBytes[9] = (byte)clockSeq;
        clockSeq = (rnd.nextInt() & 0xFFFF);
        uuidBytesRnd[8] = (byte)(clockSeq >> 8);
        uuidBytesRnd[9] = (byte)clockSeq;
        long l2 = this.gatherLong(uuidBytes, 8);
        this.uuidL2 = initUUIDSecondLong(l2);
        l2 = this.gatherLong(uuidBytesRnd, 8);
        this.uuidL2Rnd = initUUIDSecondLong(l2);
    }
    
    protected long gatherLong(final byte[] buffer, final int offset) {
        final long hi = (long)this.gatherInt(buffer, offset) << 32;
        final long lo = (long)this.gatherInt(buffer, offset + 4) << 32 >>> 32;
        return hi | lo;
    }
    
    private int gatherInt(final byte[] buffer, final int offset) {
        return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8 | (buffer[offset + 3] & 0xFF);
    }
    
    public static long initUUIDSecondLong(long l2) {
        l2 = l2 << 2 >>> 2;
        l2 |= Long.MIN_VALUE;
        return l2;
    }
    
    public UUID getUUIDImpl(final boolean dummyMac) {
        long rawTimestamp = System.currentTimeMillis();
        synchronized (TimeBasedUUIDGenerator.instance) {
            if (rawTimestamp < this.lastRawTimestamp) {
                ++this.lastRawTimestamp;
                rawTimestamp = this.lastRawTimestamp;
            }
        }
        final int clockHi = (int)(rawTimestamp >>> 32);
        final int clockLo = (int)rawTimestamp;
        int midhi = clockHi << 16 | clockHi >>> 16;
        midhi &= 0xFFFF0FFF;
        midhi |= 0x1000;
        long midhiL = midhi;
        midhiL = midhiL << 32 >>> 32;
        final long l1 = (long)clockLo << 32 | midhiL;
        return new UUID(l1, dummyMac ? this.uuidL2 : this.uuidL2Rnd);
    }
    
    public static UUID getUUID() {
        return getUUID(false);
    }
    
    public static UUID getUUID(final boolean dummyMac) {
        return getInstance().getUUIDImpl(dummyMac);
    }
    
    static {
        TimeBasedUUIDGenerator.mutex = new Object();
    }
}
