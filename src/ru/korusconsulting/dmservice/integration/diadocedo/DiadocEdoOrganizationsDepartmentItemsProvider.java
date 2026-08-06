// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo;

import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoOrganizationDetails;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.IPlainObjectItemsProvider;

public class DiadocEdoOrganizationsDepartmentItemsProvider implements IPlainObjectItemsProvider
{
    public List<Object> getData(final DMSShardsClientSession session, final IObjectModel objectModel, final ConfigElement config, final List<IPlainObjectItemsProvider.AttributeRecord> attributesList, final Map<String, Object> params, final SessionContext ctx) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Lru/korusconsulting/service/objectmodel/IDiadocEdoAccountMember;
        //     4: ifeq            199
        //     7: aload_2         /* objectModel */
        //     8: checkcast       Lru/korusconsulting/service/objectmodel/IDiadocEdoAccountMember;
        //    11: astore          accountMemberConfig
        //    13: aload           accountMemberConfig
        //    15: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoAccountMember.getAccount:()Ljava/lang/String;
        //    20: astore          accountId
        //    22: aload           accountId
        //    24: invokestatic    org/apache/commons/lang/StringUtils.isBlank:(Ljava/lang/String;)Z
        //    27: ifeq            76
        //    30: aload           ctx
        //    32: astore          pCtx
        //    34: aload           pCtx
        //    36: ifnull          76
        //    39: aload           pCtx
        //    41: ldc             "__type"
        //    43: invokevirtual   ru/korusconsulting/service/commons/SessionContext.getString:(Ljava/lang/String;)Ljava/lang/String;
        //    46: ldc             "nt_diadoc_edo_account"
        //    48: invokestatic    org/apache/commons/lang/StringUtils.equals:(Ljava/lang/String;Ljava/lang/String;)Z
        //    51: ifeq            66
        //    54: aload           pCtx
        //    56: ldc             "__objectId"
        //    58: invokevirtual   ru/korusconsulting/service/commons/SessionContext.getString:(Ljava/lang/String;)Ljava/lang/String;
        //    61: astore          accountId
        //    63: goto            76
        //    66: aload           pCtx
        //    68: invokevirtual   ru/korusconsulting/service/commons/SessionContext.getParentCtx:()Lru/korusconsulting/service/commons/SessionContext;
        //    71: astore          pCtx
        //    73: goto            34
        //    76: invokestatic    ru/korusconsulting/service/utils/ServiceReg.getObjectModelService:()Lru/korusconsulting/service/IObjectModelService;
        //    79: aload_1         /* session */
        //    80: aload           accountId
        //    82: aload           ctx
        //    84: invokeinterface ru/korusconsulting/service/IObjectModelService.getObjectModel:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Ljava/lang/String;Lru/korusconsulting/service/commons/SessionContext;)Lru/korusconsulting/service/objectmodel/IObjectModel;
        //    89: checkcast       Lru/korusconsulting/service/objectmodel/IDiadocEdoAccount;
        //    92: astore          accountConfig
        //    94: invokestatic    ru/korusconsulting/service/utils/ServiceReg.getObjectModelService:()Lru/korusconsulting/service/IObjectModelService;
        //    97: aload_1         /* session */
        //    98: aload           accountConfig
        //   100: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoAccount.getServer:()Ljava/lang/String;
        //   105: aload           ctx
        //   107: invokeinterface ru/korusconsulting/service/IObjectModelService.getObjectModel:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Ljava/lang/String;Lru/korusconsulting/service/commons/SessionContext;)Lru/korusconsulting/service/objectmodel/IObjectModel;
        //   112: checkcast       Lru/korusconsulting/service/objectmodel/IDiadocEdoServer;
        //   115: astore          serverConfig
        //   117: aload           accountConfig
        //   119: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoAccount.getLogin:()Ljava/lang/String;
        //   124: astore          login
        //   126: aload           accountConfig
        //   128: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoAccount.getPassword:()Ljava/lang/String;
        //   133: astore          password
        //   135: invokestatic    ru/korusconsulting/dmservice/integration/diadocedo/DiadocEdoServiceReg.getDiadocEdoIntegrationService:()Lru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService;
        //   138: aload_1         /* session */
        //   139: aload           login
        //   141: aload           password
        //   143: aload           accountMemberConfig
        //   145: invokeinterface ru/korusconsulting/service/objectmodel/IDiadocEdoAccountMember.getBox:()Ljava/lang/String;
        //   150: iconst_1       
        //   151: aload           serverConfig
        //   153: aload           ctx
        //   155: invokeinterface ru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService.getOursOrganizationsDetails:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLru/korusconsulting/service/objectmodel/IDiadocEdoServer;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List;
        //   160: astore          organizationDetails
        //   162: aload           organizationDetails
        //   164: ifnull          195
        //   167: new             Ljava/util/ArrayList;
        //   170: dup            
        //   171: invokespecial   java/util/ArrayList.<init>:()V
        //   174: astore          result
        //   176: aload           organizationDetails
        //   178: aload           attributesList
        //   180: aload           result
        //   182: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/List;Ljava/util/List;)Ljava/util/function/Consumer;
        //   187: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //   192: aload           result
        //   194: areturn        
        //   195: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //   198: areturn        
        //   199: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //   202: areturn        
        //    Signature:
        //  (Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/service/objectmodel/IObjectModel;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Ljava/util/List<Lru/korusconsulting/service/integration/IPlainObjectItemsProvider$AttributeRecord;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List<Ljava/lang/Object;>;
        //    StackMapTable: 00 05 FE 00 22 07 00 07 07 00 77 07 00 13 1F FA 00 09 FF 00 76 00 0E 07 00 98 07 00 C2 07 00 C4 07 00 C6 07 00 57 07 00 8A 07 00 13 07 00 07 07 00 77 07 00 33 07 00 38 07 00 77 07 00 77 07 00 57 00 00 FF 00 03 00 07 07 00 98 07 00 C2 07 00 C4 07 00 C6 07 00 57 07 00 8A 07 00 13 00 00
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
