// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.enconding.utf7;

import java.util.Iterator;
import com.ibm.icu.charset.CharsetICU;
import java.nio.charset.Charset;
import java.util.Map;
import java.nio.charset.spi.CharsetProvider;

public class UTF7CharsetProvider extends CharsetProvider
{
    private final Map<String, Charset> charsets;
    
    public UTF7CharsetProvider() {
        final Charset utf7 = CharsetICU.forNameICU("X-UTF-7");
        this.charsets = Map.of("UTF-7", utf7, "UTF7", utf7);
    }
    
    @Override
    public Charset charsetForName(final String charsetName) {
        return this.charsets.get(charsetName.toUpperCase());
    }
    
    @Override
    public Iterator<Charset> charsets() {
        return this.charsets.values().iterator();
    }
}
