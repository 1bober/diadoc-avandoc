// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.service.integration;

import org.apache.logging.log4j.util.Supplier;
import org.apache.logging.log4j.Logger;

public interface IOperationLogWriter
{
    void trace(final Logger p0, final Supplier<String> p1);
    
    void trace(final Logger p0, final Supplier<String> p1, final LogPropogateMode p2);
    
    void trace(final Logger p0, final Supplier<String> p1, final Throwable p2);
    
    void trace(final Logger p0, final Supplier<?> p1, final Throwable p2, final LogPropogateMode p3);
    
    void warn(final Logger p0, final Supplier<String> p1);
    
    void warn(final Logger p0, final Supplier<String> p1, final LogPropogateMode p2);
    
    void warn(final Logger p0, final Supplier<String> p1, final Throwable p2);
    
    void warn(final Logger p0, final Supplier<?> p1, final Throwable p2, final LogPropogateMode p3);
    
    void error(final Logger p0, final Supplier<String> p1);
    
    void error(final Logger p0, final Supplier<String> p1, final LogPropogateMode p2);
    
    void error(final Logger p0, final Supplier<String> p1, final Throwable p2);
    
    void error(final Logger p0, final Supplier<?> p1, final Throwable p2, final LogPropogateMode p3);
}
