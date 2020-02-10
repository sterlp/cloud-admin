package org.sterl.cloudadmin.impl.role.model.jpa;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.impl.common.id.GenericIdConverter;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.IdTypeDescriptor;

@SuppressWarnings("serial")
@Configuration
public class HibernateRoleConverters {

    @Autowired
    void configure(GenericConversionService defaultConversionService) {
        defaultConversionService.addConverter(new GenericIdConverter<>(RoleIdType.INSTANCE));
    }

    public static class RoleIdType extends AbstractIdType<String, RoleId> {
        public static final RoleIdType INSTANCE = new RoleIdType();
        private RoleIdType() {
            super(VarcharTypeDescriptor.INSTANCE, 
                    new IdTypeDescriptor<String, RoleId>(RoleId.class, String.class, (value) -> RoleId.newRoleId(value)));
        }
    }
}