package org.sterl.cloudadmin.impl.common.id.jpa;

import javax.persistence.AttributeConverter;

import org.sterl.cloudadmin.impl.common.converter.CloudConverter;
import org.sterl.cloudadmin.impl.common.id.Id;

public interface IdJpaConverter<IdType extends Id<ValueType>, ValueType> 
    extends AttributeConverter<IdType, ValueType>, CloudConverter<ValueType, IdType> {
    
    IdType newId(ValueType value);
    
    @Override
    default IdType convert(ValueType source) {
        return source == null ? null : newId(source);
    }
    @Override
    default ValueType convertToDatabaseColumn(IdType attribute) {
        return attribute == null ? null : attribute.getValue();
    }
    @Override
    default IdType convertToEntityAttribute(ValueType dbData) {
        return convert(dbData);
    }
}