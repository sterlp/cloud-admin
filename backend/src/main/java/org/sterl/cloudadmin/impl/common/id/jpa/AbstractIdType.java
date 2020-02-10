package org.sterl.cloudadmin.impl.common.id.jpa;

import java.io.Serializable;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.Id;

import lombok.Getter;

public abstract class AbstractIdType<ValueType, IdType extends Id<ValueType>> extends AbstractSingleColumnStandardBasicType<IdType> 
    implements PrimitiveType<IdType> {
    
    private static final long serialVersionUID = 6535464609505890290L;
    @Getter
    private final IdTypeDescriptor<ValueType, IdType> typeDescriptor;

    public AbstractIdType(SqlTypeDescriptor sqlTypeDescriptor, IdTypeDescriptor<ValueType, IdType> javaTypeDescriptor) {
        super(sqlTypeDescriptor, javaTypeDescriptor);
        this.typeDescriptor = javaTypeDescriptor;
    }

    public String getName() {
        return this.typeDescriptor.getJavaType().getSimpleName();
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    public String toString(String value) {
        return value;
    }

    @Override
    public String objectToSQLString(IdType value, Dialect dialect) throws Exception {
        return value == null ? null : String.valueOf(value.getValue());
    }

    @Override
    public Class<?> getPrimitiveClass() {
        return typeDescriptor.getJavaType();
    }

    @Override
    public Serializable getDefaultValue() {
        return null;
    }
}
