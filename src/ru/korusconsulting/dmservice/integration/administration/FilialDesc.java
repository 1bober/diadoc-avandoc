// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.administration;

public class FilialDesc
{
    private final String id;
    private final String name;
    private final String inn;
    private final String kpp;
    
    public FilialDesc(final String id, final String name, final String inn, final String kpp) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getInn() {
        return this.inn;
    }
    
    public String getKpp() {
        return this.kpp;
    }
}
