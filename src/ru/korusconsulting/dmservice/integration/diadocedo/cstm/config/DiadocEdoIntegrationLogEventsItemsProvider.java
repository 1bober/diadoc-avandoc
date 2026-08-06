// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.cstm.config;

import ru.korusconsulting.service.objectmodel.cstm.ICstmComponent;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.IPlainObjectItemsProvider;

public class DiadocEdoIntegrationLogEventsItemsProvider implements IPlainObjectItemsProvider
{
    private Logger log;
    
    public DiadocEdoIntegrationLogEventsItemsProvider() {
        this.log = LogManager.getLogger((Class)DiadocEdoIntegrationLogEventsItemsProvider.class);
    }
    
    public List<Object> getData(final DMSShardsClientSession session, final IObjectModel objectModel, final ConfigElement config, final List<IPlainObjectItemsProvider.AttributeRecord> attributesList, final Map<String, Object> params, final SessionContext ctx) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: checkcast       Lru/korusconsulting/service/objectmodel/cstm/ICstmComponent;
        //     4: astore          c
        //     6: aload           c
        //     8: ifnull          40
        //    11: ldc             "cstm_integration_diadoc_edo_settings"
        //    13: aload           c
        //    15: invokeinterface ru/korusconsulting/service/objectmodel/cstm/ICstmComponent.getComponentType:()Ljava/lang/String;
        //    20: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    23: ifne            40
        //    26: aload           c
        //    28: ldc             "cstm_integration_diadoc_edo_settings"
        //    30: invokeinterface ru/korusconsulting/service/objectmodel/cstm/ICstmComponent.getParentWithType:(Ljava/lang/String;)Lru/korusconsulting/service/objectmodel/cstm/ICstmComponent;
        //    35: astore          c
        //    37: goto            6
        //    40: aload           c
        //    42: ifnull          99
        //    45: aload           c
        //    47: ldc             "cstm_exchange_categories_link"
        //    49: invokeinterface ru/korusconsulting/service/objectmodel/cstm/ICstmComponent.getChildren:(Ljava/lang/String;)Ljava/util/List;
        //    54: astore          s
        //    56: aload           s
        //    58: instanceof      Ljava/util/List;
        //    61: ifeq            99
        //    64: aload           s
        //    66: checkcast       Ljava/util/List;
        //    69: astore          sL
        //    71: new             Ljava/util/ArrayList;
        //    74: dup            
        //    75: invokespecial   java/util/ArrayList.<init>:()V
        //    78: astore          result
        //    80: aload           sL
        //    82: aload           attributesList
        //    84: aload           result
        //    86: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/List;Ljava/util/List;)Ljava/util/function/Consumer;
        //    91: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //    96: aload           result
        //    98: areturn        
        //    99: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //   102: areturn        
        //    Signature:
        //  (Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/service/objectmodel/IObjectModel;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Ljava/util/List<Lru/korusconsulting/service/integration/IPlainObjectItemsProvider$AttributeRecord;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List<Ljava/lang/Object;>;
        //    StackMapTable: 00 03 FC 00 06 07 00 13 21 3A
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
