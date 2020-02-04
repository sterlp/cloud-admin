package org.sterl.cloudadmin.impl.role.model.jpa;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.jpa.IdJpaConverter;
@SuppressWarnings("serial")
@Configuration
public class HibernateRoleConverters {

    @Bean
    public RoleIdConverter roleIdConverter() {
        return RoleIdConverter.INSTANCE;
    }
    public static class RoleIdConverter extends IdJpaConverter<RoleId, String> {
        public static final RoleIdConverter INSTANCE = new RoleIdConverter();
        @Override
        protected RoleId newId(String value) {
            return RoleId.newRoleId(value);
        }
    }
    public static class RoleIdTypeDescriptor extends AbstractIdTypeDescriptor<String, RoleId> {
        public static final RoleIdTypeDescriptor INSTANCE = new RoleIdTypeDescriptor();
        RoleIdTypeDescriptor() {
            super(RoleId.class, String.class, RoleIdConverter.INSTANCE);
        }
    }
    public static class RoleIdType extends AbstractIdType<String, RoleId> {
        public static final RoleIdType INSTANCE = new RoleIdType();
        private RoleIdType() {
            super(VarcharTypeDescriptor.INSTANCE, RoleIdTypeDescriptor.INSTANCE);
        }
    }
}