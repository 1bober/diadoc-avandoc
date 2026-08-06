// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import com.google.protobuf.GeneratedMessage;
import java.util.Optional;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeContentUnit;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.IDiadocEdoAttributesResolverFactory;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;

public class DiadocEdoSendDocumentSupplementaryAgreementProcessor extends DiadocEdoSendDocumentProcessor<DiadocMessage_PostApiProtos.SupplementaryAgreementAttachment.Builder> implements IDiadocEdoAttributesResolverFactory
{
    @Override
    protected DiadocMessage_PostApiProtos.SupplementaryAgreementAttachment.Builder getMessageBuilder() {
        return DiadocMessage_PostApiProtos.SupplementaryAgreementAttachment.newBuilder();
    }
    
    @Override
    protected void addMessageAttachment(final IDiadocEdoOperationContext operationContext, final IDocument document, final IDiadocEdoExchangeContentUnit contentUnit, final Map<String, Optional<Object>> outgoingAttributes, final DiadocMessage_PostApiProtos.MessageToPost.Builder builder, final boolean serverSignatureMode) {
        final DiadocMessage_PostApiProtos.SupplementaryAgreementAttachment.Builder diadocDoc = this.getMessageBuilder();
        diadocDoc.setCustomDocumentId(contentUnit.getContentUid());
        diadocDoc.setSignedContent(contentUnit.getSignedContentBuilder().setSignWithTestSignature(serverSignatureMode).build());
        this.fillMessageAttachmentAttributes(operationContext, document, contentUnit, (GeneratedMessage.Builder<DiadocMessage_PostApiProtos.SupplementaryAgreementAttachment.Builder>)diadocDoc, outgoingAttributes);
        diadocDoc.setFileName(contentUnit.getName());
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s, Content %s prepared to send", document.getObjectId(), contentUnit.getName()));
        builder.addSupplementaryAgreements(diadocDoc.build());
    }
}
