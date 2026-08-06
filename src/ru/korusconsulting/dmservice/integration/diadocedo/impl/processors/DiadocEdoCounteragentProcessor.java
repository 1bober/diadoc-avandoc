// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import java.util.Objects;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApiBoxConnection;
import ru.korusconsulting.service.exceptions.ServiceException;
import java.util.ArrayList;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoNoConnectionException;
import Diadoc.Api.Proto.OrganizationProtos;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import Diadoc.Api.Proto.CounteragentProtos;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoApiMethodLibrary;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoSystemErrorException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoIntegrationException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.exceptions.DiadocEdoCounteragentNotExistsException;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogMessagesContainer;
import ru.korusconsulting.service.integration.processors.IEdoIntegrationSendProcessor;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.IntegrationLogRecordMessage;
import ru.korusconsulting.service.integrationlog.IIntegrationLogService;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoIntegrationServiceInternal;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.DiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.integrationlog.DiadocEdoIntegrationOperation;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoExternalCounteragentInfo;
import ru.korusconsulting.service.commons.SessionContext;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoConnectionConfig;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.service.integration.diadocedo.IDiadocEdoApi;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.service.shards.DMSShardsClientSession;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoCounteragentProcessor;

public class DiadocEdoCounteragentProcessor extends DiadocEdoProcessor implements IDiadocEdoCounteragentProcessor
{
    @Override
    public final IDiadocEdoExternalCounteragentInfo getCounteragentBoxGuid(final DMSShardsClientSession session, final IDiadocEdoOperationContext oContext, final IDiadocEdoApi api, final IDocument document, final String inn, final String kpp, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoOperationContext operationContext = DiadocEdoOperationContext.createOperationContext(session, (IIntegrationOperationContext)oContext, DiadocEdoIntegrationOperation.GET_COUNTERAGENT_BOX, ctx);
        try {
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_inn, inn);
            operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_kpp, kpp);
            this.getService().writeIntegrationLogEntry(IIntegrationLogService.TYPE.INFO, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_START, null, (IIntegrationOperationContext)operationContext, ctx);
            IEdoIntegrationSendProcessor.ProcessingResult processingResult = IEdoIntegrationSendProcessor.ProcessingResult.FAILED;
            try {
                final IDiadocEdoExternalCounteragentInfo info = this.getCounteragentBoxGuidImpl(session, operationContext, api, document, inn, kpp, notInvitedCounteragentSendMode, allowTestCounteragent, ctx);
                if (info != null) {
                    processingResult = IEdoIntegrationSendProcessor.ProcessingResult.SUCCESS;
                    operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_box, info.getBoxId());
                    operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_department, info.getDepartmentId());
                    operationContext.setParam(IDiadocEdoIntegrationServiceInternal.ParamName.counteragent_fns_id, info.getFnsParticipantId());
                    final IDiadocEdoExternalCounteragentInfo diadocEdoExternalCounteragentInfo = info;
                    final IntegrationLogMessagesContainer messagesContainer = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_COUNTERAGENT_BOX_SUCCESS, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_COUNTERAGENT_BOX_NOT_FOUND, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                    this.getService().writeIntegrationLog(operationContext, processingResult, true, messagesContainer, ctx);
                    if (operationContext != null) {
                        operationContext.close();
                    }
                    return diadocEdoExternalCounteragentInfo;
                }
                throw new DiadocEdoCounteragentNotExistsException(String.format("Failed to find Diadoc.EDO counteragent with inn %s and kpp %s", inn, kpp));
            }
            catch (final DiadocEdoCounteragentNotExistsException e) {
                throw e;
            }
            catch (final DiadocEdoIntegrationException e2) {
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Error getting counteragent box GUID for inn %s and kpp %s", inn, kpp));
                throw e2;
            }
            catch (final Throwable e3) {
                final String message = String.format("Error getting counteragent box GUID for inn %s and kpp %s", inn, kpp);
                IIntegrationOperationContext.error((IIntegrationOperationContext)operationContext, this.getLogger(), () -> message);
                throw new DiadocEdoSystemErrorException(message, e3);
            }
            finally {
                final IntegrationLogMessagesContainer messagesContainer2 = new IntegrationLogMessagesContainer(IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_COUNTERAGENT_BOX_SUCCESS, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_GET_COUNTERAGENT_BOX_NOT_FOUND, IntegrationLogRecordMessage.MSG_MESSAGE_DIADOCEDO_OPERATION_FINISH_WITH_ERRORS, null);
                this.getService().writeIntegrationLog(operationContext, processingResult, true, messagesContainer2, ctx);
            }
        }
        catch (final Throwable t) {
            if (operationContext != null) {
                try {
                    operationContext.close();
                }
                catch (final Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            throw t;
        }
    }
    
    protected IDiadocEdoExternalCounteragentInfo getMyCounteragentBoxGuid(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final String inn, final String kpp, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Tring to find my counteragent with INN %s", inn));
        final List<CounteragentProtos.CounteragentList> myCounteragentList = DiadocEdoApiMethodLibrary.getMyCounteragentList(session, this.getService(), operationContext, api, operationContext.getDiadocBoxGuid(session, ctx), inn, ctx);
        if (myCounteragentList != null && !myCounteragentList.isEmpty()) {
            IDiadocEdoExternalCounteragentInfo matched = null;
            for (final CounteragentProtos.CounteragentList myCounteragentListEl : myCounteragentList) {
                for (final CounteragentProtos.Counteragent cnt : myCounteragentListEl.getCounteragentsList()) {
                    final OrganizationProtos.Organization org = cnt.getOrganization();
                    if (org == null) {
                        continue;
                    }
                    if (org.getIsActive() && (!org.getIsTest() || allowTestCounteragent) && StringUtils.equals(inn, org.getInn()) && org.getBoxesCount() > 0) {
                        final boolean nonRoaming = StringUtils.startsWith(org.getFnsParticipantId(), "2BM-") && !org.getIsRoaming();
                        if (nonRoaming || matched == null) {
                            matched = this.extractOrganizationInfo(session, operationContext, api, org, inn, kpp, CounteragentKppSearch.org, ctx);
                        }
                        if (matched == null) {
                            continue;
                        }
                        if (!matched.isRoaming()) {
                            return matched;
                        }
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Found my counteragent with with roaming found, Trying to find non-roaming");
                    }
                    else {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found my counteragent with inn %s %s%s - name %s, orgId %s, FnsParticipantId %s. IsActive = %b. Box count %d. Ignoring....", org.getInn(), StringUtils.isNotBlank(org.getKpp()) ? "kpp " : "", org.getKpp(), org.getFullName(), org.getOrgId(), org.getFnsParticipantId(), org.getIsActive(), org.getBoxesCount()));
                    }
                }
            }
            for (final CounteragentProtos.CounteragentList myCounteragentListEl : myCounteragentList) {
                for (final CounteragentProtos.Counteragent cnt : myCounteragentListEl.getCounteragentsList()) {
                    final OrganizationProtos.Organization org = cnt.getOrganization();
                    if (org == null) {
                        continue;
                    }
                    if (org.getIsActive() && StringUtils.equals(inn, org.getInn()) && org.getBoxesCount() > 0) {
                        final boolean nonRoaming = StringUtils.startsWith(org.getFnsParticipantId(), "2BM-") && !org.getIsRoaming();
                        if (nonRoaming || matched == null) {
                            matched = this.extractOrganizationInfo(session, operationContext, api, org, inn, kpp, CounteragentKppSearch.department, ctx);
                        }
                        if (matched == null) {
                            continue;
                        }
                        if (!matched.isRoaming()) {
                            return matched;
                        }
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Found my counteragent with with roaming found, Trying to find non-roaming");
                    }
                    else {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found my counteragent with inn %s %s%s - name %s, orgId %s, FnsParticipantId %s. IsActive = %b. Box count %d. Ignoring....", org.getInn(), StringUtils.isNotBlank(org.getKpp()) ? "kpp " : "", org.getKpp(), org.getFullName(), org.getOrgId(), org.getFnsParticipantId(), org.getIsActive(), org.getBoxesCount()));
                    }
                }
            }
            if (matched != null) {
                final IDiadocEdoExternalCounteragentInfo matchedF = matched;
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s my counteragent for %s not found, use first founded roaming (name %s, orgId %s, FnsParticipantId %s, boxId %s, boxIdGuid %s, departmentId %s , departmentName %s)", matchedF.isRoaming() ? "Roaming" : "Non-roaming", this.formatCounteragentInfo(inn, kpp), matchedF.getOrgName(), matchedF.getOrgId(), matchedF.getFnsParticipantId(), matchedF.getBoxId(), matchedF.getBoxIdGuid(), matchedF.getDepartmentId(), matchedF.getDepartmentName()));
            }
            else {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("My counteragent for %s not found", this.formatCounteragentInfo(inn, kpp)));
            }
            return matched;
        }
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("My counteragent with INN %s not found", inn));
        return null;
    }
    
    protected IDiadocEdoExternalCounteragentInfo extractOrganizationInfo(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final OrganizationProtos.Organization org, final String inn, final String kpp, final CounteragentKppSearch counteragentKppSearchType, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final boolean nonRoaming = StringUtils.startsWith(org.getFnsParticipantId(), "2BM-") && !org.getIsRoaming();
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization for %s - name %s, orgId %s, FnsParticipantId %s (non-roaming - %b). Box count %d", this.formatCounteragentInfo(org.getInn(), org.getKpp()), org.getFullName(), org.getOrgId(), org.getFnsParticipantId(), nonRoaming, org.getBoxesCount()));
        if (org.getIsActive() && StringUtils.equals(inn, org.getInn()) && org.getBoxesCount() > 0) {
            OrganizationType organizationType;
            if (org.getIsForeign()) {
                organizationType = OrganizationType.foreign;
            }
            else {
                organizationType = ((StringUtils.length(StringUtils.trim(inn)) == 12) ? OrganizationType.fl : OrganizationType.ul);
            }
            final boolean ulKppMatch = organizationType == OrganizationType.ul && StringUtils.isNotBlank(kpp) && StringUtils.isNotBlank(org.getKpp()) && StringUtils.equals(kpp, org.getKpp());
            final boolean flMatch = organizationType == OrganizationType.fl && StringUtils.isBlank(kpp);
            final boolean foreignMatch = organizationType == OrganizationType.foreign && StringUtils.equals(StringUtils.trimToNull(kpp), StringUtils.trimToNull(org.getKpp()));
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Organization match type: UL %b, FL %b, foreign %b", ulKppMatch, flMatch, foreignMatch));
            if (counteragentKppSearchType != CounteragentKppSearch.department && (ulKppMatch || flMatch || foreignMatch)) {
                final OrganizationProtos.Box b = org.getBoxes(0);
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization %s (%s) with boxId %s, boxGuid %s", org.getFullName(), org.getOrgId(), b.getBoxId(), b.getBoxIdGuid()));
                return this.createCounteragentInfo(org);
            }
            if (counteragentKppSearchType != CounteragentKppSearch.org && StringUtils.isNotBlank(kpp) && organizationType != OrganizationType.fl) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Founded diadoc edo organization KPP %s not match desired KPP %s. Looking through departments", org.getKpp(), kpp));
                final String orgId = org.getOrgId();
                final OrganizationProtos.Organization organization = DiadocEdoApiMethodLibrary.getOrganization(session, this.getService(), operationContext, api, orgId, ctx);
                if (organization.getDepartmentsList() != null) {
                    for (final OrganizationProtos.Department dep : organization.getDepartmentsList()) {
                        if (dep == null) {
                            continue;
                        }
                        if (dep.getIsDisabled()) {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found disabled department with kpp %s - name %s, departmentId %s", Objects.toString(dep.getKpp(), ""), dep.getName(), dep.getDepartmentId()));
                        }
                        else {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found department with kpp %s - name %s, departmentId %s", Objects.toString(dep.getKpp(), ""), dep.getName(), dep.getDepartmentId()));
                            if (StringUtils.equals(kpp, dep.getKpp())) {
                                final OrganizationProtos.Box b2 = organization.getBoxes(0);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found counteragent boxId %s, boxGuid %s", b2.getBoxId(), b2.getBoxIdGuid()));
                                final IDiadocEdoExternalCounteragentInfo counteragentInfo = this.createCounteragentInfo(org, dep);
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s diadoc edo organization for %s found. boxId %s, boxIdGuid %s, departmentId %s , departmentName %s", nonRoaming ? "Non-roaming" : "Roaming", this.formatCounteragentInfo(inn, kpp), counteragentInfo.getBoxId(), counteragentInfo.getBoxIdGuid(), counteragentInfo.getDepartmentId(), counteragentInfo.getDepartmentName()));
                                return counteragentInfo;
                            }
                            continue;
                        }
                    }
                }
                else {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Department list is empty");
                }
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Department with kpp %s not found", kpp));
            }
        }
        return null;
    }
    
    protected IDiadocEdoExternalCounteragentInfo createCounteragentInfo(final OrganizationProtos.Organization organization) {
        return this.createCounteragentInfo(organization, null);
    }
    
    protected IDiadocEdoExternalCounteragentInfo createCounteragentInfo(final OrganizationProtos.Organization organization, final OrganizationProtos.Department dep) {
        if (organization == null || organization.getBoxesCount() == 0) {
            return null;
        }
        final OrganizationProtos.Box b = organization.getBoxes(0);
        return new IDiadocEdoExternalCounteragentInfo() {
            @Override
            public String getDepartmentId() {
                return (dep == null) ? null : dep.getDepartmentId();
            }
            
            @Override
            public String getBoxId() {
                return b.getBoxId();
            }
            
            @Override
            public String getBoxIdGuid() {
                return b.getBoxIdGuid();
            }
            
            @Override
            public String getOrgName() {
                return organization.getFullName();
            }
            
            @Override
            public String getOrgId() {
                return organization.getOrgId();
            }
            
            @Override
            public String getFnsParticipantId() {
                return organization.getFnsParticipantId();
            }
            
            @Override
            public String getDepartmentName() {
                return (dep == null) ? null : dep.getName();
            }
            
            @Override
            public boolean isRoaming() {
                return organization.getIsRoaming();
            }
            
            @Override
            public boolean hasCertificateToSign() {
                return organization.getHasCertificateToSign();
            }
            
            @Override
            public boolean IsForeign() {
                return organization.getIsForeign();
            }
        };
    }
    
    protected String formatCounteragentInfo(final String inn, final String kpp) {
        if (StringUtils.length(inn) == 10) {
            return StringUtils.isBlank(kpp) ? String.format("Organization with INN %s", inn) : String.format("Organization with INN %s and KPP %s", inn, kpp);
        }
        if (StringUtils.length(inn) == 12) {
            return StringUtils.isBlank(kpp) ? String.format("Individual with INN %s", inn) : String.format("Individual with INN %s and KPP %s", inn, kpp);
        }
        return StringUtils.isBlank(kpp) ? String.format("Organization/Individual with INN %s", inn) : String.format("Organization/Individual with INN %s and KPP %s", inn, kpp);
    }
    
    protected IDiadocEdoExternalCounteragentInfo getCounteragentBoxGuidImpl(final DMSShardsClientSession session, final IDiadocEdoOperationContext operationContext, final IDiadocEdoApi api, final IDocument document, final String inn, final String kpp, final IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode notInvitedCounteragentSendMode, final boolean allowTestCounteragent, final SessionContext ctx) throws DiadocEdoIntegrationException {
        final IDiadocEdoApiBoxConnection apiConnection = operationContext.getApiConnection(session, this.getService(), ctx);
        if (apiConnection == null) {
            throw new DiadocEdoNoConnectionException(String.format("No connection for filial %s / %s", operationContext.getFilialName(), operationContext.getFilialId()));
        }
        final List<String> rejectedCounteragents = new ArrayList<String>();
        final IDiadocEdoExternalCounteragentInfo ci = this.getMyCounteragentBoxGuid(session, operationContext, api, document, inn, kpp, allowTestCounteragent, ctx);
        if (ci != null) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found my counteragent box %s/%s with for %s. Roaming %b, HasCertificateToSign %s", ci.getBoxIdGuid(), ci.getDepartmentId(), this.formatCounteragentInfo(inn, kpp), ci.isRoaming(), ci.hasCertificateToSign()));
            return ci;
        }
        if (apiConnection.getNotInvitedCounteragentSendMode() == IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.error) {
            return null;
        }
        if (apiConnection.getNotInvitedCounteragentSendMode() == IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.type_configured && notInvitedCounteragentSendMode == IDiadocEdoConnectionConfig.NotInvitedCounteragentSendMode.error) {
            return null;
        }
        if (!apiConnection.isAllowSendToRejected()) {
            final List<CounteragentProtos.CounteragentList> rejectedCounteragentList = DiadocEdoApiMethodLibrary.getRejectedCounteragentList(session, this.getService(), operationContext, api, operationContext.getDiadocBoxGuid(session, ctx), inn, ctx);
            for (final CounteragentProtos.CounteragentList rejectedCounteragentListElement : rejectedCounteragentList) {
                for (final CounteragentProtos.Counteragent cnt : rejectedCounteragentListElement.getCounteragentsList()) {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found reject record for counteragent %s with INN %s", cnt.getOrganization().getOrgId(), inn));
                    rejectedCounteragents.add(cnt.getOrganization().getOrgId());
                }
            }
            if (rejectedCounteragents.isEmpty()) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Rejected counteragents with INN %s not defined", inn));
            }
        }
        else {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Allow send to rejected counteragent flag is set");
        }
        try {
            IDiadocEdoExternalCounteragentInfo matched = null;
            final OrganizationProtos.OrganizationList listA = DiadocEdoApiMethodLibrary.getOrganizationsByInnKpp(session, this.getService(), operationContext, api, inn, kpp, ctx);
            if (listA != null && listA.getOrganizationsCount() > 0) {
                for (final OrganizationProtos.Organization org : listA.getOrganizationsList()) {
                    if (org == null) {
                        continue;
                    }
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization %s (box %s) for %ss. Is active [%b], is rejected by us [%b], fns participant id is %s", org.getOrgId(), org.getBoxesList().isEmpty() ? "not exists" : org.getBoxes(0).getBoxIdGuid(), this.formatCounteragentInfo(inn, kpp), org.getIsActive(), rejectedCounteragents.contains(org.getOrgId()), org.getFnsParticipantId()), (Throwable)null, operationContext.getPropogateLogMode());
                    if (org.getIsActive() && StringUtils.equals(inn, org.getInn()) && org.getBoxesCount() > 0) {
                        if (!rejectedCounteragents.contains(org.getOrgId())) {
                            final boolean nonRoaming = StringUtils.startsWith(org.getFnsParticipantId(), "2BM-") && !org.getIsRoaming();
                            if (nonRoaming || matched == null) {
                                matched = this.extractOrganizationInfo(session, operationContext, api, org, inn, kpp, CounteragentKppSearch.any, ctx);
                                if (matched == null) {
                                    continue;
                                }
                                if (!matched.isRoaming()) {
                                    return matched;
                                }
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Founded diadoc edo organization is roaming, trying to find non-roaming");
                            }
                            else {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Allready found roaming diadoc edo organization. Trying to find non-roaming");
                            }
                        }
                        else {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Founded diadoc edo organization for %s - name %s, orgId %s, FnsParticipantId %s is rejected. Ignoring....", this.formatCounteragentInfo(org.getInn(), org.getKpp()), org.getFullName(), org.getOrgId(), org.getFnsParticipantId()));
                        }
                    }
                    else {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organizatio for %s - name %s, orgId %s, FnsParticipantId %s. IsActive = %b. Box count %d. Ignoring....", this.formatCounteragentInfo(org.getInn(), org.getKpp()), org.getFullName(), org.getOrgId(), org.getFnsParticipantId(), org.getIsActive(), org.getBoxesCount()));
                    }
                }
            }
            else if (StringUtils.isNotBlank(kpp)) {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc edo organization for %s not found. Trying to search through departments of organizations with INN %s", this.formatCounteragentInfo(inn, kpp), inn));
                final OrganizationProtos.OrganizationList listB = DiadocEdoApiMethodLibrary.getOrganizationsByInn(session, this.getService(), operationContext, api, inn, ctx);
                if (listB != null && listB.getOrganizationsCount() > 0) {
                    for (final OrganizationProtos.Organization org2 : listB.getOrganizationsList()) {
                        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization %s (box %s) for INN %s. Is active [%b], is rejected by us [%b], fns participant id is %s", org2.getOrgId(), org2.getBoxesList().isEmpty() ? "not exists" : org2.getBoxes(0).getBoxIdGuid(), inn, org2.getIsActive(), rejectedCounteragents.contains(org2.getOrgId()), org2.getFnsParticipantId()), operationContext.getPropogateLogMode());
                        if (org2.getIsActive() && StringUtils.equals(inn, org2.getInn()) && org2.getBoxesCount() > 0) {
                            if (!rejectedCounteragents.contains(org2.getOrgId())) {
                                final boolean nonRoaming2 = StringUtils.startsWith(org2.getFnsParticipantId(), "2BM-") && !org2.getIsRoaming();
                                if (nonRoaming2 || matched == null) {
                                    matched = this.extractOrganizationInfo(session, operationContext, api, org2, inn, kpp, CounteragentKppSearch.any, ctx);
                                    if (matched == null) {
                                        continue;
                                    }
                                    if (!matched.isRoaming()) {
                                        return matched;
                                    }
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Founded diadoc edo organization is roaming, trying to find non-roaming");
                                }
                                else {
                                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "Allready found roaming diadoc edo organization. Trying to find non-roaming");
                                }
                            }
                            else {
                                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization for %s - name %s, orgId %s, FnsParticipantId %s is rejected. Ignoring....", this.formatCounteragentInfo(org2.getInn(), org2.getKpp()), org2.getFullName(), org2.getOrgId(), org2.getFnsParticipantId()));
                            }
                        }
                        else {
                            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Found diadoc edo organization for %s - name %s, orgId %s, FnsParticipantId %s. IsActive = %b. Box count %d. Ignoring....", this.formatCounteragentInfo(org2.getInn(), org2.getKpp()), org2.getFullName(), org2.getOrgId(), org2.getFnsParticipantId(), org2.getIsActive(), org2.getBoxesCount()));
                        }
                    }
                }
                else {
                    IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc edo organization with INN %s not found", inn));
                }
            }
            else {
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc edo organization for %s not found", this.formatCounteragentInfo(inn, kpp)));
            }
            if (matched != null) {
                final IDiadocEdoExternalCounteragentInfo matchedF = matched;
                IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("%s organization for %s not found, use first founded roaming (name %s, orgId %s, FnsParticipantId %s, boxId %s, boxIdGuid %s, departmentId %s , departmentName %s)", matchedF.isRoaming() ? "Roaming" : "Non-roaming", this.formatCounteragentInfo(inn, kpp), matchedF.getOrgName(), matchedF.getOrgId(), matchedF.getFnsParticipantId(), matchedF.getBoxId(), matchedF.getBoxIdGuid(), matchedF.getDepartmentId(), matchedF.getDepartmentName()));
                return matched;
            }
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Diadoc edo organization for %s not found", this.formatCounteragentInfo(inn, kpp)));
            return null;
        }
        catch (final ServiceException e) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "", (Throwable)e);
            throw e;
        }
        catch (final Exception e2) {
            IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> "", (Throwable)e2);
            throw new ServiceException(e2);
        }
    }
    
    public void close() {
    }
    
    enum OrganizationType
    {
        fl, 
        ul, 
        foreign;
    }
}
