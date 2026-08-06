// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.config;

import ru.korusconsulting.dmservice.integration.impl.EdoIntegrationProcessingState;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.IPlainObjectItemsProvider;

public class EdoIntegrationProcessingStateItemsProvider implements IPlainObjectItemsProvider
{
    private Logger log;
    
    public EdoIntegrationProcessingStateItemsProvider() {
        this.log = LogManager.getLogger((Class)EdoIntegrationProcessingStateItemsProvider.class);
    }
    
    public List<Object> getData(final DMSShardsClientSession session, final IObjectModel objectModel, final ConfigElement config, final List<IPlainObjectItemsProvider.AttributeRecord> attributesList, final Map<String, Object> params, final SessionContext ctx) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore          result
        //     9: invokestatic    ru/korusconsulting/dmservice/integration/impl/EdoIntegrationProcessingState.values:()[Lru/korusconsulting/dmservice/integration/impl/EdoIntegrationProcessingState;
        //    12: astore          8
        //    14: aload           8
        //    16: arraylength    
        //    17: istore          9
        //    19: iconst_0       
        //    20: istore          10
        //    22: iload           10
        //    24: iload           9
        //    26: if_icmpge       77
        //    29: aload           8
        //    31: iload           10
        //    33: aaload         
        //    34: astore          v
        //    36: new             Ljava/util/HashMap;
        //    39: dup            
        //    40: invokespecial   java/util/HashMap.<init>:()V
        //    43: astore          row
        //    45: aload           attributesList
        //    47: aload           row
        //    49: aload           v
        //    51: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/Map;Lru/korusconsulting/dmservice/integration/impl/EdoIntegrationProcessingState;)Ljava/util/function/Consumer;
        //    56: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //    61: aload           result
        //    63: aload           row
        //    65: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    70: pop            
        //    71: iinc            10, 1
        //    74: goto            22
        //    77: aload           result
        //    79: areturn        
        //    Signature:
        //  (Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/service/objectmodel/IObjectModel;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Ljava/util/List<Lru/korusconsulting/service/integration/IPlainObjectItemsProvider$AttributeRecord;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List<Ljava/lang/Object;>;
        //    StackMapTable: 00 02 FF 00 16 00 0B 07 00 07 07 00 6B 07 00 6D 07 00 6F 07 00 24 07 00 47 07 00 71 07 00 24 07 00 73 01 01 00 00 F8 00 36
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException: Cannot invoke "com.strobel.assembler.metadata.TypeReference.getSimpleType()" because the return value of "com.strobel.decompiler.ast.Variable.getType()" is null
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:252)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:185)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.nameVariables(AstMethodBodyBuilder.java:1482)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.populateVariables(AstMethodBodyBuilder.java:1411)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:129)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
