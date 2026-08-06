// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.administration;

import ru.korusconsulting.service.objectmodel.IDiadocEdoAccountMember;
import ru.korusconsulting.service.objectmodel.IObjectModel;
import ru.korusconsulting.dmservice.integration.administration.FilialDesc;
import ru.korusconsulting.service.shards.DMSShardsManager;
import ru.korusconsulting.service.commons.ICollection;
import ru.korusconsulting.dmservice.integration.administration.EdoAccountMember;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import ru.korusconsulting.service.utils.ServiceReg;
import java.util.HashMap;
import ru.korusconsulting.service.shards.ShardSessionRunner;
import ru.korusconsulting.service.integration.administration.IEdoAccountMember;
import java.util.List;
import java.util.Map;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.dmservice.integration.administration.ImportAccountMembersEventAction;

public class ImportDiadocAccountMembersEventAction extends ImportAccountMembersEventAction
{
    protected Map<String, List<IEdoAccountMember>> loadExistingMembers(final DMSShardsClientSession session, final SessionContext ctx) {
        final ShardSessionRunner<Map<String, List<IEdoAccountMember>>> shardSessionRunner = new ShardSessionRunner<Map<String, List<IEdoAccountMember>>>() {
            public Map<String, List<IEdoAccountMember>> run() {
                final Map<String, List<IEdoAccountMember>> result = new HashMap<String, List<IEdoAccountMember>>();
                ICollection coll = null;
                try {
                    coll = ServiceReg.getQueryService().executeReadQuery(session, "select m.r_object_id, m.ssa_box, m.ssa_department, m.sida_account, m.ssa_account_name, f.r_object_id as filial_id, f.ssa_name as filial_name, f.ssa_inn, f.ssa_kpp from nt_diadoc_edo_account_member m, nt_filial f where m.sida_filial=f.r_object_id", ctx);
                    while (coll.next()) {
                        final String key = String.join(".", StringUtils.trimToEmpty(coll.getString("ssa_inn")), StringUtils.trimToEmpty(coll.getString("ssa_kpp")));
                        result.computeIfAbsent(key, a -> new ArrayList()).add((Object)new EdoAccountMember(coll.getString("sida_account"), coll.getString("ssa_account_name"), coll.getString("r_object_id"), coll.getString("ssa_inn"), coll.getString("ssa_kpp"), coll.getString("ssa_box"), coll.getString("ssa_department")));
                    }
                }
                finally {
                    if (coll != null) {
                        coll.close();
                    }
                }
                return result;
            }
        };
        return (Map)shardSessionRunner.start(DMSShardsManager.SHARD.DICT, ctx);
    }
    
    protected IEdoAccountMember createObject(final DMSShardsClientSession session, final FilialDesc filialDesc, final IObjectModel account, final Map<ImportAccountMembersEventAction.IMPORT_ATTRIBUTE, String> newValues, final boolean enabled, final SessionContext ctx) {
        final IDiadocEdoAccountMember member = (IDiadocEdoAccountMember)ServiceReg.getObjectModelService().createObjectModel("nt_diadoc_edo_account_member", ctx);
        member.setFilial(filialDesc.getId());
        member.setFilialName(filialDesc.getName());
        member.setAccount(account.getObjectId());
        member.setAccountName(account.getValueAsString("ssa_login"));
        member.setBox(newValues.get(ImportAccountMembersEventAction.IMPORT_ATTRIBUTE.BOX));
        member.setDepartment(newValues.get(ImportAccountMembersEventAction.IMPORT_ATTRIBUTE.DEPARTMENT));
        member.setEnabled(enabled);
        ServiceReg.getObjectModelService().commitModel(session, (IObjectModel)member, ctx);
        return (IEdoAccountMember)new EdoAccountMember(member.getAccount(), member.getAccountName(), member.getInn(), member.getKpp(), member.getBox(), member.getDepartment());
    }
}
