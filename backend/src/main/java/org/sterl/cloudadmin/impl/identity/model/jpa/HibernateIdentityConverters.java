package org.sterl.cloudadmin.impl.identity.model.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.common.id.GenericIdConverter;
import org.sterl.cloudadmin.impl.common.id.jpa.IdTypeDescriptor;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractLongIdType;

@SuppressWarnings("serial")
@Configuration
public class HibernateIdentityConverters {
    @Autowired
    void configure(GenericConversionService defaultConversionService) {
        defaultConversionService.addConverter(new GenericIdConverter<>(IdentityIdType.INSTANCE));
    }
    
    public static class IdentityIdType extends AbstractLongIdType<IdentityId> {
        public static final IdentityIdType INSTANCE = new IdentityIdType();
        private IdentityIdType() {
            super(new IdTypeDescriptor<Long, IdentityId>(IdentityId.class, Long.class, (value) -> IdentityId.newIdentityId(value)));
        }
    }
}