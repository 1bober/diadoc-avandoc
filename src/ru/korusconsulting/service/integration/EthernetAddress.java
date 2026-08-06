// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import java.net.SocketException;
import java.util.Enumeration;
import java.net.NetworkInterface;
import java.util.Random;

class EthernetAddress
{
    private final long macAddress;
    
    private EthernetAddress(final byte[] addr) throws NumberFormatException {
        this.macAddress = this.convertToLong(addr);
    }
    
    private long convertToLong(final byte[] addr) {
        long l = addr[0] & 0xFF;
        for (int i = 1; i < 6; ++i) {
            l = (l << 8 | (long)(addr[i] & 0xFF));
        }
        return l;
    }
    
    private EthernetAddress(final Random rnd) {
        final byte[] dummy = new byte[6];
        rnd.nextBytes(dummy);
        final byte[] array = dummy;
        final int n = 0;
        array[n] |= 0x1;
        this.macAddress = this.convertToLong(dummy);
    }
    
    public static EthernetAddress fromInterface() {
        try {
            final Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                final NetworkInterface nint = en.nextElement();
                if (!nint.isLoopback()) {
                    final EthernetAddress addr = fromInterface(nint);
                    if (addr != null) {
                        return addr;
                    }
                    continue;
                }
            }
        }
        catch (final Exception ex) {}
        return null;
    }
    
    public static EthernetAddress dummy(final Random rnd) {
        return new EthernetAddress(rnd);
    }
    
    private static EthernetAddress fromInterface(final NetworkInterface nint) {
        if (nint != null) {
            try {
                final byte[] data = nint.getHardwareAddress();
                if (data != null && data.length == 6) {
                    return new EthernetAddress(data);
                }
            }
            catch (final SocketException ex) {}
        }
        return null;
    }
    
    public long getMacAddress() {
        return this.macAddress;
    }
    
    public byte[] toByteArray() {
        final byte[] array = new byte[6];
        int pos = 0;
        int i = (int)(this.macAddress >> 32);
        array[pos++] = (byte)(i >> 8);
        array[pos++] = (byte)i;
        i = (int)this.macAddress;
        array[pos++] = (byte)(i >> 24);
        array[pos++] = (byte)(i >> 16);
        array[pos++] = (byte)(i >> 8);
        array[pos] = (byte)i;
        return array;
    }
}
