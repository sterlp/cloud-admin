package org.sterl.cloudadmin.identity.model.jpa;

import org.hibernate.type.descriptor.java.LongTypeDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.common.id.jpa.AbstractIdTypeDescriptor;
import org.sterl.cloudadmin.common.id.jpa.AbstractLongIdType;
import org.sterl.cloudadmin.common.id.jpa.IdJpaConverter;

@SuppressWarnings("serial")
@Configuration
public class HibernateIdentityConverters {

    @Bean
    public IdentityIdConverter identityIdConverter() {
        return IdentityIdConverter.INSTANCE;
    }
    public static class IdentityIdConverter extends IdJpaConverter<IdentityId, Long> {
        public static final IdentityIdConverter INSTANCE = new IdentityIdConverter();
        @Override
        protected IdentityId newId(Long value) {
            return IdentityId.newIdentityId(value);
        }
    }
    public static class IdentityIdTypeDescriptor extends AbstractIdTypeDescriptor<Long, IdentityId> {
        public static final IdentityIdTypeDescriptor INSTANCE = new IdentityIdTypeDescriptor();
        IdentityIdTypeDescriptor() {
            super(IdentityId.class, Long.class, IdentityIdConverter.INSTANCE);
        }
    }
    public static class IdentityIdType extends AbstractLongIdType<IdentityId> {
        public static final IdentityIdType INSTANCE = new IdentityIdType();
        private IdentityIdType() {
            super(IdentityIdTypeDescriptor.INSTANCE);
        }
    }
}