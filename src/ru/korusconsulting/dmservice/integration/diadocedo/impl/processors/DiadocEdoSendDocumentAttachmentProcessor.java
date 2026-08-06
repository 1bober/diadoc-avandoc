// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.processors;

import com.google.protobuf.Descriptors;
import ru.korusconsulting.service.integration.diadocedo.processors.IDiadocEdoSendProcessor;
import java.util.List;
import ru.korusconsulting.service.integration.IIntegrationOperationContext;
import com.google.protobuf.GeneratedMessage;
import java.util.Optional;
import java.util.Map;
import ru.korusconsulting.service.integration.diadocedo.model.IDiadocEdoExchangeContentUnit;
import ru.korusconsulting.service.objectmodel.documents.IDocument;
import ru.korusconsulting.dmservice.integration.diadocedo.IDiadocEdoOperationContext;
import ru.korusconsulting.dmservice.integration.diadocedo.impl.IDiadocEdoAttributesResolverFactory;
import Diadoc.Api.Proto.Events.DiadocMessage_PostApiProtos;

public class DiadocEdoSendDocumentAttachmentProcessor extends DiadocEdoSendDocumentProcessor<DiadocMessage_PostApiProtos.DocumentAttachment.Builder> implements IDiadocEdoAttributesResolverFactory
{
    private String typeName;
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    
    @Override
    protected DiadocMessage_PostApiProtos.DocumentAttachment.Builder getMessageBuilder() {
        return DiadocMessage_PostApiProtos.DocumentAttachment.newBuilder();
    }
    
    @Override
    protected void addMessageAttachment(final IDiadocEdoOperationContext operationContext, final IDocument document, final IDiadocEdoExchangeContentUnit contentUnit, final Map<String, Optional<Object>> outgoingAttributes, final DiadocMessage_PostApiProtos.MessageToPost.Builder builder, final boolean serverSignatureMode) {
        final DiadocMessage_PostApiProtos.DocumentAttachment.Builder diadocDoc = this.getMessageBuilder();
        diadocDoc.setCustomDocumentId(contentUnit.getContentUid());
        diadocDoc.setSignedContent(contentUnit.getSignedContentBuilder().setSignWithTestSignature(serverSignatureMode).build());
        this.fillMessageAttachmentAttributes(operationContext, document, contentUnit, (GeneratedMessage.Builder<DiadocMessage_PostApiProtos.DocumentAttachment.Builder>)diadocDoc, outgoingAttributes);
        IIntegrationOperationContext.trace((IIntegrationOperationContext)operationContext, this.getLogger(), () -> String.format("Document %s, Content %s prepared to send", document.getObjectId(), contentUnit.getName()));
        builder.addDocumentAttachments(diadocDoc.build());
    }
    
    @Override
    public List<IDiadocEdoSendProcessor.AttrDesc> getFieldNames() {
        final DiadocMessage_PostApiProtos.DocumentAttachment.Builder diadocDoc = DiadocMessage_PostApiProtos.DocumentAttachment.newBuilder();
        diadocDoc.setTypeNamedId(this.getTypeName());
        return diadocDoc.getDescriptorForType().getFields().stream().map(a -> new IDiadocEdoSendProcessor.AttrDesc(a.getName(), a.getType().name(), "dd.MM.yyyy", a.isRequired())).toList();
    }
}
