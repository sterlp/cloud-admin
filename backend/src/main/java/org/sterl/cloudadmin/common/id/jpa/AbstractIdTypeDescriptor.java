package org.sterl.cloudadmin.common.id.jpa;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.sterl.cloudadmin.common.id.Id;

import lombok.Getter;

public abstract class AbstractIdTypeDescriptor<ValueType, IdType extends Id<ValueType>> extends AbstractTypeDescriptor<IdType> {
    
    @Getter private final IdJpaConverter<IdType, ValueType> converter;
    @Getter private final Class<ValueType> valueClass;

    public AbstractIdTypeDescriptor(Class<IdType> idClass, Class<ValueType> valueClass, IdJpaConverter<IdType, ValueType> converter) {
        super(idClass);
        this.valueClass = valueClass;
        this.converter = converter;
    }

    public String toString(IdType value) {
        return value == null ? null : String.valueOf(value.getValue());
    }

    public IdType fromString(String string) {
        return wrap(string, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> X unwrap(IdType value, Class<X> type, WrapperOptions options) {
        if ( value == null || value.getValue() == null) {
            return null;
        }
        if ( getJavaType().isAssignableFrom( type ) ) {
            return (X) value;
        }
        if ( valueClass.isAssignableFrom( type ) ) {
            return (X) value.getValue();
        }
        if ( String.class.isAssignableFrom( type )) {
            return (X) toString(value);
        }

        throw unknownUnwrap( type );
    }

    @SuppressWarnings("unchecked")
    public <X> IdType wrap(X value, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( getJavaType().isInstance( value )) {
            return (IdType) value;
        }
        if ( valueClass.isInstance( value ) ) {
            return converter.convert((ValueType) value);
        }
        if ( String.class.isInstance(value) && valueClass.isAssignableFrom(Long.class) ) {
            return converter.convert((ValueType) Long.valueOf((String)value) );
        }

        throw unknownWrap( value.getClass() );
    }
}
