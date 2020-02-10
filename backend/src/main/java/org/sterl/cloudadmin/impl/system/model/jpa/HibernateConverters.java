package org.sterl.cloudadmin.impl.system.model.jpa;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.SystemAccountId;
import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.api.system.SystemPermissionId;
import org.sterl.cloudadmin.api.system.SystemResourceId;
import org.sterl.cloudadmin.impl.common.id.GenericIdConverter;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractLongIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.IdTypeDescriptor;
@SuppressWarnings("serial")
@Configuration
public class HibernateConverters {
    
    @Autowired
    void configure(GenericConversionService defaultConversionService) {
        defaultConversionService.addConverter(new GenericIdConverter<>(SystemIdType.INSTANCE));
        defaultConversionService.addConverter(new GenericIdConverter<>(SystemCredentialIdType.INSTANCE));
        defaultConversionService.addConverter(new GenericIdConverter<>(SystemResourceIdType.INSTANCE));
        defaultConversionService.addConverter(new GenericIdConverter<>(SystemPermissionIdType.INSTANCE));
        defaultConversionService.addConverter(new GenericIdConverter<>(ExternalPermissionIdType.INSTANCE));
    }

    public static class SystemIdType extends AbstractLongIdType<SystemId> {
        public static final SystemIdType INSTANCE = new SystemIdType();
        SystemIdType() {
            super(new IdTypeDescriptor<Long, SystemId>(SystemId.class, Long.class, (value) -> SystemId.newSystemId(value)));
        }
    }

    public static class SystemCredentialIdType extends AbstractLongIdType<SystemCredentialId> {
        public static final SystemCredentialIdType INSTANCE = new SystemCredentialIdType();
        SystemCredentialIdType() {
            super(new IdTypeDescriptor<Long, SystemCredentialId>(SystemCredentialId.class, Long.class, (v) -> SystemCredentialId.newSystemCredentialId(v)));
        }
    }
    
    public static class SystemResourceIdType extends AbstractLongIdType<SystemResourceId> {
        public static final SystemResourceIdType INSTANCE = new SystemResourceIdType();
        SystemResourceIdType() {
            super(new IdTypeDescriptor<Long, SystemResourceId>(SystemResourceId.class, Long.class, (value) -> SystemResourceId.newSystemResourceId(value)));
        }
    }

    public static class SystemPermissionIdType extends AbstractLongIdType<SystemPermissionId> {
        public static final SystemPermissionIdType INSTANCE = new SystemPermissionIdType();
        SystemPermissionIdType() {
            super(new IdTypeDescriptor<Long, SystemPermissionId>(SystemPermissionId.class, Long.class, (value) -> new SystemPermissionId(value)));
        }
    }
    
    public static class SystemAccountIdType extends AbstractLongIdType<SystemAccountId> {
        public static final SystemAccountIdType INSTANCE = new SystemAccountIdType();
        SystemAccountIdType() {
            super(new IdTypeDescriptor<Long, SystemAccountId>(SystemAccountId.class, Long.class, (value) -> SystemAccountId.newSystemAccountId(value)));
        }
    }

    public static class ExternalPermissionIdType extends AbstractIdType<String, ExternalPermissionId> {
        public static final ExternalPermissionIdType INSTANCE = new ExternalPermissionIdType();
        private ExternalPermissionIdType() {
            super(VarcharTypeDescriptor.INSTANCE, 
                    new IdTypeDescriptor<String, ExternalPermissionId>(ExternalPermissionId.class, String.class, (value) -> ExternalPermissionId.newExternalPermissionId(value)));
        }
    }
}
