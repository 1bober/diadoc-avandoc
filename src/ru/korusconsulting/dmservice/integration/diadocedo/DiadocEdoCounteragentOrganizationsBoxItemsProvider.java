// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import java.util.Iterator;
import java.util.HashMap;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoOrganizationDetails;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IPlainObjectItemsProvider;

public class DiadocEdoCounteragentOrganizationsBoxItemsProvider implements IPlainObjectItemsProvider
{
    public List<Object> getData(final DMSShardsClientSession session, final IObjectModel objectModel, final ConfigElement config, final List<IPlainObjectItemsProvider.AttributeRecord> attributes, final Map<String, Object> params, final SessionContext ctx) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Lru/korusconsulting/service/objectmodel/IDiadocEdoCounteragentInfo;
        //     4: ifeq            96
        //     7: aload_2         /* objectModel */
        //     8: checkcast       Lru/korusconsulting/service/objectmodel/IDiadocEdoCounteragentInfo;
        //    11: astore          counteragentInfo
        //    13: invokestatic    ru/korusconsulting/service/utils/ServiceReg.getObjectModelService:()Lru/korusconsulting/service/IObjectModelService;
        //    16: aload_1         /* session */
        //    17: aload           counteragentInfo
        //    19: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoCounteragentInfo.getServerId:()Ljava/lang/String;
        //    24: aload           ctx
        //    26: invokeinterface ru/korusconsulting/service/IObjectModelService.getObjectModel:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Ljava/lang/String;Lru/korusconsulting/service/commons/SessionContext;)Lru/korusconsulting/service/objectmodel/IObjectModel;
        //    31: checkcast       Lru/korusconsulting/service/objectmodel/IDiadocEdoServer;
        //    34: astore          serverConfig
        //    36: new             Ljava/util/ArrayList;
        //    39: dup            
        //    40: invokespecial   java/util/ArrayList.<init>:()V
        //    43: astore          result
        //    45: invokestatic    ru/korusconsulting/dmservice/integration/diadocedo/DiadocEdoServiceReg.getDiadocEdoIntegrationService:()Lru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService;
        //    48: aload_1         /* session */
        //    49: aload           counteragentInfo
        //    51: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoCounteragentInfo.getInn:()Ljava/lang/String;
        //    56: aload           counteragentInfo
        //    58: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoCounteragentInfo.getKpp:()Ljava/lang/String;
        //    63: aload           serverConfig
        //    65: aload           ctx
        //    67: invokeinterface ru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService.getCounteragentOrganizationDetails:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Ljava/lang/String;Ljava/lang/String;Lru/korusconsulting/service/objectmodel/IDiadocEdoServer;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List;
        //    72: astore          organizationDetails
        //    74: aload           organizationDetails
        //    76: ifnull          93
        //    79: aload           organizationDetails
        //    81: aload           attributes
        //    83: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/List;)Ljava/util/function/Consumer;
        //    88: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //    93: aload           result
        //    95: areturn        
        //    96: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //    99: areturn        
        //    Signature:
        //  (Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/service/objectmodel/IObjectModel;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Ljava/util/List<Lru/korusconsulting/service/integration/IPlainObjectItemsProvider$AttributeRecord;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List<Ljava/lang/Object;>;
        //    StackMapTable: 00 02 FF 00 5D 00 0B 07 00 82 07 00 A5 07 00 A7 07 00 A9 07 00 35 07 00 75 07 00 AB 07 00 07 07 00 19 07 00 35 07 00 35 00 00 FF 00 02 00 07 07 00 82 07 00 A5 07 00 A7 07 00 A9 07 00 35 07 00 75 07 00 AB 00 00
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
