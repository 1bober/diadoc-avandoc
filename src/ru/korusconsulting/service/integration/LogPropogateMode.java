// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

public enum LogPropogateMode
{
    current(0), 
    all_level(1);
    
    int value;
    
    private LogPropogateMode(final int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public static LogPropogateMode parse(final int value) {
        for (final LogPropogateMode v : values()) {
            if (v.getValue() == value) {
                return v;
            }
        }
        return LogPropogateMode.current;
    }
    
    public static boolean gt(final LogPropogateMode left, final LogPropogateMode right) {
        return left != null && (right == null || left.getValue() > right.getValue());
    }
}
