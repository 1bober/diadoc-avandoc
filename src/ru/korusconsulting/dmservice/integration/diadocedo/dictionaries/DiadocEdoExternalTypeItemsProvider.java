// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.dictionaries;

import ru.korusconsulting.service.integration.configuration.IDictionaryElementConfiguration;
import ru.korusconsulting.dmservice.integration.diadocedo.DiadocEdoServiceReg;
import ru.korusconsulting.service.commons.SessionContext;
import java.util.Map;
import java.util.List;
import ru.korusconsulting.dmservice.config.alt.ConfigElement;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.korusconsulting.service.integration.IPlainObjectItemsProvider;

public class DiadocEdoExternalTypeItemsProvider implements IPlainObjectItemsProvider
{
    private Logger log;
    
    public DiadocEdoExternalTypeItemsProvider() {
        this.log = LogManager.getLogger((Class)DiadocEdoExternalTypeItemsProvider.class);
    }
    
    public List<Object> getData(final DMSShardsClientSession session, final IObjectModel objectModel, final ConfigElement config, final List<IPlainObjectItemsProvider.AttributeRecord> attributes, final Map<String, Object> params, final SessionContext ctx) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ldc             "cstm_integration[id=%s]"
        //     5: iconst_1       
        //     6: anewarray       Ljava/lang/Object;
        //     9: dup            
        //    10: iconst_0       
        //    11: invokestatic    ru/korusconsulting/dmservice/integration/diadocedo/DiadocEdoServiceReg.getDiadocEdoIntegrationService:()Lru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService;
        //    14: invokeinterface ru/korusconsulting/service/integration/diadocedo/IDiadocEdoIntegrationService.getCstmIntegrationId:()Ljava/lang/String;
        //    19: aastore        
        //    20: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    23: aload           ctx
        //    25: invokeinterface ru/korusconsulting/service/IConfigService.lookupElement:(Ljava/lang/String;Lru/korusconsulting/service/commons/SessionContext;)Lru/korusconsulting/dmservice/config/alt/ConfigElement;
        //    30: astore          serviceConfig
        //    32: aload           serviceConfig
        //    34: ifnull          117
        //    37: new             Lru/korusconsulting/dmservice/integration/diadocedo/configuration/impl/DiadocEdoServiceConfiguration;
        //    40: dup            
        //    41: aload_1         /* session */
        //    42: aconst_null    
        //    43: aload           ctx
        //    45: invokespecial   ru/korusconsulting/dmservice/integration/diadocedo/configuration/impl/DiadocEdoServiceConfiguration.<init>:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/dmservice/cstm/generators/Generator;Lru/korusconsulting/service/commons/SessionContext;)V
        //    48: astore          serviceConfiguration
        //    50: aload           serviceConfiguration
        //    52: aload_1         /* session */
        //    53: aload           serviceConfig
        //    55: aload           ctx
        //    57: invokevirtual   ru/korusconsulting/dmservice/integration/diadocedo/configuration/impl/DiadocEdoServiceConfiguration.unmarshal:(Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Lru/korusconsulting/service/commons/SessionContext;)V
        //    60: aload           serviceConfiguration
        //    62: invokevirtual   ru/korusconsulting/dmservice/integration/diadocedo/configuration/impl/DiadocEdoServiceConfiguration.getIncomingDocumentConfiguration:()Lru/korusconsulting/service/integration/configuration/flows/IEdoIntegrationIncomingDocumentsFlowConfiguration;
        //    65: astore          incomingConfig
        //    67: aload           incomingConfig
        //    69: ifnull          114
        //    72: aload           incomingConfig
        //    74: invokeinterface ru/korusconsulting/service/integration/configuration/flows/IEdoIntegrationIncomingDocumentsFlowConfiguration.getExternalDocumentTypes:()Ljava/util/List;
        //    79: astore          exchangeCategoriesList
        //    81: aload           exchangeCategoriesList
        //    83: ifnull          114
        //    86: new             Ljava/util/ArrayList;
        //    89: dup            
        //    90: invokespecial   java/util/ArrayList.<init>:()V
        //    93: astore          result
        //    95: aload           exchangeCategoriesList
        //    97: aload           attributes
        //    99: aload           result
        //   101: invokedynamic   BootstrapMethod #0, accept:(Ljava/util/List;Ljava/util/List;)Ljava/util/function/Consumer;
        //   106: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //   111: aload           result
        //   113: areturn        
        //   114: goto            131
        //   117: aload_0         /* this */
        //   118: getfield        ru/korusconsulting/dmservice/integration/diadocedo/dictionaries/DiadocEdoExternalTypeItemsProvider.log:Lorg/apache/logging/log4j/Logger;
        //   121: invokedynamic   BootstrapMethod #1, get:()Lorg/apache/logging/log4j/util/Supplier;
        //   126: invokeinterface org/apache/logging/log4j/Logger.info:(Lorg/apache/logging/log4j/util/Supplier;)V
        //   131: invokestatic    java/util/Collections.emptyList:()Ljava/util/List;
        //   134: areturn        
        //    Signature:
        //  (Lru/korusconsulting/service/shards/DMSShardsClientSession;Lru/korusconsulting/service/objectmodel/IObjectModel;Lru/korusconsulting/dmservice/config/alt/ConfigElement;Ljava/util/List<Lru/korusconsulting/service/integration/IPlainObjectItemsProvider$AttributeRecord;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;Lru/korusconsulting/service/commons/SessionContext;)Ljava/util/List<Ljava/lang/Object;>;
        //    StackMapTable: 00 03 FC 00 72 07 00 B1 02 0D
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
