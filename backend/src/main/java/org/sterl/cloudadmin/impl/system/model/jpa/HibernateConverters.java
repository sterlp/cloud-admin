package org.sterl.cloudadmin.impl.system.model.jpa;

import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.SystemAccountId;
import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.api.system.SystemPermissionId;
import org.sterl.cloudadmin.api.system.SystemResourceId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractLongIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.IdJpaConverter;
import org.sterl.cloudadmin.impl.role.model.jpa.HibernateRoleConverters.RoleIdConverter;
import org.sterl.cloudadmin.impl.role.model.jpa.HibernateRoleConverters.RoleIdType;
import org.sterl.cloudadmin.impl.role.model.jpa.HibernateRoleConverters.RoleIdTypeDescriptor;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemAccountIdConverter;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemAccountIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemAccountIdTypeDescriptor;
@SuppressWarnings("serial")
@Configuration
public class HibernateConverters {

    @Bean
    public SystemIdConverter systemIdConverter() {
        return SystemIdConverter.INSTANCE;
    }
    public static class SystemIdConverter extends IdJpaConverter<SystemId, Long> {
        public static final SystemIdConverter INSTANCE = new SystemIdConverter();
        @Override
        protected SystemId newId(Long value) {
            return SystemId.newSystemId(value);
        }
    }
    public static class SystemIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, SystemId> {
        public static final SystemIdTypeDescriptor INSTANCE = new SystemIdTypeDescriptor();
        SystemIdTypeDescriptor() {
            super(SystemId.class, Long.class, SystemIdConverter.INSTANCE);
        }
    }
    public static class SystemIdType extends AbstractLongIdType<SystemId> {
        public static final SystemIdType INSTANCE = new SystemIdType();
        SystemIdType() {
            super(SystemIdTypeDescriptor.INSTANCE);
        }
    }
    
    @Bean
    public SystemCredentialIdConverter systemCredentialIdConverter() {
        return SystemCredentialIdConverter.INSTANCE;
    }
    public static class SystemCredentialIdConverter extends IdJpaConverter<SystemCredentialId, Long> {
        public static final SystemCredentialIdConverter INSTANCE = new SystemCredentialIdConverter();
        @Override
        protected SystemCredentialId newId(Long value) {
            return SystemCredentialId.newSystemCredentialId(value);
        }
    }
    public static class SystemCredentialIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, SystemCredentialId> {
        public static final SystemCredentialIdTypeDescriptor INSTANCE = new SystemCredentialIdTypeDescriptor();
        SystemCredentialIdTypeDescriptor() {
            super(SystemCredentialId.class, Long.class, SystemCredentialIdConverter.INSTANCE);
        }
    }
    public static class SystemCredentialIdType extends AbstractLongIdType<SystemCredentialId> {
        public static final SystemCredentialIdType INSTANCE = new SystemCredentialIdType();
        SystemCredentialIdType() {
            super(SystemCredentialIdTypeDescriptor.INSTANCE);
        }
    }
    
    @Bean
    public SystemResourceIdConverter systemResourceIdConverter() {
        return SystemResourceIdConverter.INSTANCE;
    }
    public static class SystemResourceIdConverter extends IdJpaConverter<SystemResourceId, Long> {
        public static final SystemResourceIdConverter INSTANCE = new SystemResourceIdConverter();
        @Override
        protected SystemResourceId newId(Long value) {
            return SystemResourceId.newSystemResourceId(value);
        }
    }
    public static class SystemResourceIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, SystemResourceId> {
        public static final SystemResourceIdTypeDescriptor INSTANCE = new SystemResourceIdTypeDescriptor();
        SystemResourceIdTypeDescriptor() {
            super(SystemResourceId.class, Long.class, SystemResourceIdConverter.INSTANCE);
        }
    }
    public static class SystemResourceIdType extends AbstractLongIdType<SystemResourceId> {
        public static final SystemResourceIdType INSTANCE = new SystemResourceIdType();
        SystemResourceIdType() {
            super(SystemResourceIdTypeDescriptor.INSTANCE);
        }
    }
    
    @Bean
    public SystemPermissionIdConverter systemPermissionIdConverter() {
        return SystemPermissionIdConverter.INSTANCE;
    }
    public static class SystemPermissionIdConverter extends IdJpaConverter<SystemPermissionId, Long> {
        public static final SystemPermissionIdConverter INSTANCE = new SystemPermissionIdConverter();
        @Override
        protected SystemPermissionId newId(Long value) {
            return new SystemPermissionId(value);
        }
    }
    public static class SystemPermissionIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, SystemPermissionId> {
        public static final SystemPermissionIdTypeDescriptor INSTANCE = new SystemPermissionIdTypeDescriptor();
        SystemPermissionIdTypeDescriptor() {
            super(SystemPermissionId.class, Long.class, SystemPermissionIdConverter.INSTANCE);
        }
    }
    public static class SystemPermissionIdType extends AbstractLongIdType<SystemPermissionId> {
        public static final SystemPermissionIdType INSTANCE = new SystemPermissionIdType();
        SystemPermissionIdType() {
            super(SystemPermissionIdTypeDescriptor.INSTANCE);
        }
    }
    
    @Bean
    public SystemAccountIdConverter systemAccountIdConverter() {
        return SystemAccountIdConverter.INSTANCE;
    }
    public static class SystemAccountIdConverter extends IdJpaConverter<SystemAccountId, Long> {
        public static final SystemAccountIdConverter INSTANCE = new SystemAccountIdConverter();
        @Override
        protected SystemAccountId newId(Long value) {
            return SystemAccountId.newSystemAccountId(value);
        }
    }
    public static class SystemAccountIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, SystemAccountId> {
        public static final SystemAccountIdTypeDescriptor INSTANCE = new SystemAccountIdTypeDescriptor();
        SystemAccountIdTypeDescriptor() {
            super(SystemAccountId.class, Long.class, SystemAccountIdConverter.INSTANCE);
        }
    }
    public static class SystemAccountIdType extends AbstractLongIdType<SystemAccountId> {
        public static final SystemAccountIdType INSTANCE = new SystemAccountIdType();
        SystemAccountIdType() {
            super(SystemAccountIdTypeDescriptor.INSTANCE);
        }
    }
    
    @Bean
    public ExternalPermissionIdConverter externalPermissionIdConverter() {
        return ExternalPermissionIdConverter.INSTANCE;
    }
    public static class ExternalPermissionIdConverter extends IdJpaConverter<ExternalPermissionId, String> {
        public static final ExternalPermissionIdConverter INSTANCE = new ExternalPermissionIdConverter();
        @Override
        protected ExternalPermissionId newId(String value) {
            return ExternalPermissionId.newExternalPermissionId(value);
        }
    }
    public static class ExternalPermissionIdTypeDescriptor extends AbstractIdTypeDescriptor<String, ExternalPermissionId> {
        public static final ExternalPermissionIdTypeDescriptor INSTANCE = new ExternalPermissionIdTypeDescriptor();
        ExternalPermissionIdTypeDescriptor() {
            super(ExternalPermissionId.class, String.class, ExternalPermissionIdConverter.INSTANCE);
        }
    }
    public static class ExternalPermissionIdType extends AbstractIdType<String, ExternalPermissionId> {
        public static final ExternalPermissionIdType INSTANCE = new ExternalPermissionIdType();
        private ExternalPermissionIdType() {
            super(VarcharTypeDescriptor.INSTANCE, ExternalPermissionIdTypeDescriptor.INSTANCE);
        }
    }
}
