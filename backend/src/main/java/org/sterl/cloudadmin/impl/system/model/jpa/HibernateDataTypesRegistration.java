package org.sterl.cloudadmin.impl.system.model.jpa;

import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderFactory;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
import org.hibernate.type.TypeResolver;
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
        final TypeResolver typeResolver = metadata.getTypeResolver();
        typeResolver.registerTypeOverride(SystemIdType.INSTANCE);
        typeResolver.registerTypeOverride(SystemCredentialIdType.INSTANCE);
        typeResolver.registerTypeOverride(SystemResourceIdType.INSTANCE);
        typeResolver.registerTypeOverride(SystemPermissionIdType.INSTANCE);
        typeResolver.registerTypeOverride(SystemAccountIdType.INSTANCE);
        typeResolver.registerTypeOverride(ExternalPermissionIdType.INSTANCE);
        
        // TODO move me
        typeResolver.registerTypeOverride(RoleIdType.INSTANCE);
        typeResolver.registerTypeOverride(HibernateIdentityConverters.IdentityIdType.INSTANCE);
        return defaultBuilder;
    }
}
