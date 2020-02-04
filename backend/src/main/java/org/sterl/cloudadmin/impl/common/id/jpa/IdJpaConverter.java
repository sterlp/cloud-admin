package org.sterl.cloudadmin.impl.common.id.jpa;

import javax.persistence.AttributeConverter;

import org.sterl.cloudadmin.impl.common.converter.CloudConverter;
import org.sterl.cloudadmin.impl.common.id.Id;

public abstract class IdJpaConverter<IdType extends Id<ValueType>, ValueType> 
    implements AttributeConverter<IdType, ValueType>, CloudConverter<ValueType, IdType> {
    
    protected abstract IdType newId(ValueType value);
    
    @Override
    public IdType convert(ValueType source) {
        return source == null ? null : newId(source);
    }
    @Override
    public ValueType convertToDatabaseColumn(IdType attribute) {
        return attribute == null ? null : attribute.getValue();
    }
    @Override
    public IdType convertToEntityAttribute(ValueType dbData) {
        return convert(dbData);
    }
}