package org.sterl.cloudadmin.impl.identity.model.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.common.id.GenericIdConverter;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractIdTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractLongIdType;
import org.sterl.cloudadmin.impl.common.id.jpa.IdJpaConverter;

@SuppressWarnings("serial")
@Configuration
public class HibernateIdentityConverters {
    @Autowired
    void configure(GenericConversionService defaultConversionService) {
        defaultConversionService.addConverter(new GenericIdConverter<>(IdentityIdTypeDescriptor.INSTANCE));
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