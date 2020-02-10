package org.sterl.cloudadmin.impl.common.id.jpa;

import java.io.Serializable;
import java.util.Comparator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.VersionType;
import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.AbstractId;

import lombok.Getter;

public abstract class AbstractLongIdType<IdType extends AbstractId<Long>> extends AbstractIdType<Long, IdType> 
    implements DiscriminatorType<IdType>, VersionType<IdType> {
    
    private static final long serialVersionUID = -5379870621943669892L;

    private final Comparator<IdType> comperator = new Comparator<IdType>() {
        @Override
        public int compare(IdType o1, IdType o2) {
            return Long.compare(o1.getValue(), o2.getValue());
        }
    };

    @Getter
    private final IdType nullValue;
    @Getter
    private final IdTypeDescriptor<Long, IdType> typeDescriptor;

    public AbstractLongIdType(IdTypeDescriptor<Long, IdType> typeDescriptor) {
        super(BigIntTypeDescriptor.INSTANCE, typeDescriptor);
        this.typeDescriptor = typeDescriptor;
        this.nullValue = this.typeDescriptor.getConverter().convert(0L);
    }

    @Override
    public IdType stringToObject(String xml) throws Exception {
        return xml == null ? null : this.typeDescriptor.getConverter().convert(Long.valueOf(xml));
    }

    @Override
    public IdType seed(SharedSessionContractImplementor session) {
        return nullValue;
    }

    @Override
    public IdType next(IdType current, SharedSessionContractImplementor session) {
        return this.typeDescriptor.getConverter().convert(current.getValue() + 1);
    }

    @Override
    public Comparator<IdType> getComparator() {
        return comperator;
    }

    @Override
    public Class<?> getPrimitiveClass() {
        return Long.class;
    }

    @Override
    public Serializable getDefaultValue() {
        return nullValue;
    }
}
