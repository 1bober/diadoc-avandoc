// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.diadocedo.impl.model;

import com.google.protobuf.Descriptors;
import java.util.Map;

@FunctionalInterface
interface IAllFieldsGetter
{
    Map<Descriptors.FieldDescriptor, Object> getAllFields();
}
