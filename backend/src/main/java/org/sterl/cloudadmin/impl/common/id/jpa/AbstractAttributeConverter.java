package org.sterl.cloudadmin.impl.common.id.jpa;

import javax.persistence.AttributeConverter;

import org.springframework.core.convert.converter.Converter;
import org.sterl.cloudadmin.impl.common.id.Id;

public abstract class AbstractAttributeConverter<IdType extends Id<ValueTye>, ValueTye> 
    implements AttributeConverter<IdType, ValueTye>, Converter<ValueTye, IdType> {
    /**
     * {@inheritDoc}
     */
    @Override
    public ValueTye convertToDatabaseColumn(IdType id) {
        return id == null ? null : id.getValue();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public IdType convertToEntityAttribute(ValueTye dbData) {
        return dbData == null ? null : parse(dbData);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public IdType convert(ValueTye source) {
        return convertToEntityAttribute(source);
    }
    
    protected abstract IdType parse(ValueTye value);
}
