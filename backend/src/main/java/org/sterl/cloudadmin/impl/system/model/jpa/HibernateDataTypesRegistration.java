package org.sterl.cloudadmin.impl.system.model.jpa;

import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderFactory;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
import org.sterl.cloudadmin.impl.identity.model.jpa.HibernateIdentityConverters;
import org.sterl.cloudadmin.impl.role.model.jpa.HibernateRoleConverters.RoleIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.ExternalPermissionIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemAccountIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemCredentialIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemPermissionIdType;
import org.sterl.cloudadmin.impl.system.model.jpa.HibernateConverters.SystemResourceIdType;

public class HibernateDataTypesRegistration implements SessionFactoryBuilderFactory {

    @SuppressWarnings("deprecation")
    @Override
    public SessionFactoryBuilder getSessionFactoryBuilder(final MetadataImplementor metadata, final SessionFactoryBuilderImplementor defaultBuilder) {
        metadata.getTypeResolver().registerTypeOverride(SystemIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(SystemCredentialIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(SystemResourceIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(SystemPermissionIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(SystemAccountIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(ExternalPermissionIdType.INSTANCE);
        
        // TODO move me
        metadata.getTypeResolver().registerTypeOverride(RoleIdType.INSTANCE);
        metadata.getTypeResolver().registerTypeOverride(HibernateIdentityConverters.IdentityIdType.INSTANCE);
        return defaultBuilder;
    }
}
