package org.sterl.cloudadmin.impl.common.id.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Transient;

import org.sterl.cloudadmin.impl.common.id.Id;

public abstract class EntityWithId<IdType extends Id<ValueTye>, ValueTye> {

    @Transient
    protected transient IdType strongId;
    @Transient
    protected final AttributeConverter<IdType, ValueTye> converter;
    
    public EntityWithId(AttributeConverter<IdType, ValueTye> converter) {
        this.converter = converter;
    }

    public abstract ValueTye getId();

    public IdType getStrongId() {
        if (strongId == null) strongId = converter.convertToEntityAttribute(getId());
        return strongId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (this.getId() == null) return false;
        if (obj == null) return false;

        if (obj instanceof EntityWithId) {
            EntityWithId<?, ?> entity = (EntityWithId<?, ?>)obj;
            if (entity.getId() == null) return false;
            return getId().equals(entity.getId());
        }

        return false;
    }
    
    @Override
    public int hashCode() {
        if (getId() == null) super.hashCode();
        return getId().hashCode();
    }
}
