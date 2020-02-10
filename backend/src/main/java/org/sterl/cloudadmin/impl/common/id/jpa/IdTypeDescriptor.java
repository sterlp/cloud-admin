package org.sterl.cloudadmin.impl.common.id.jpa;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.Id;

import lombok.Getter;

/**
 * Common class which support the conversion of {@link Id} types to their DB representations.
 * In addition it supports parsing {@link Long} values from {@link String}s. 
 */
public class IdTypeDescriptor<ValueType, IdType extends Id<ValueType>> extends AbstractTypeDescriptor<IdType> {
    
    private static final long serialVersionUID = 1L;
    @Getter private final IdJpaConverter<IdType, ValueType> converter;
    @Getter private final Class<ValueType> valueClass;

    public IdTypeDescriptor(Class<IdType> idClass, Class<ValueType> valueClass, IdJpaConverter<IdType, ValueType> converter) {
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
        return (X)unwrapUnchecked(value, type);
    }

    /**
     * @see #unwrap(Id, Class, WrapperOptions)
     * @throws HibernateException if type is unknown
     */
    public Object unwrapUnchecked(IdType value, Class type) {
        if ( value == null || value.getValue() == null) {
            return null;
        }
        if ( getJavaType().isAssignableFrom( type ) ) {
            return value;
        }
        if ( valueClass.isAssignableFrom( type ) ) {
            return value.getValue();
        }
        if ( String.class.isAssignableFrom( type )) {
            return toString(value);
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
