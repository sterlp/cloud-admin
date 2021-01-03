package org.sterl.cloudadmin.impl.identity.converter;

import javax.persistence.Converter;

import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractAttributeConverter;

@Converter(autoApply = true)
public class IdentityIdConverter extends AbstractAttributeConverter<IdentityId, Long> {
    public static final IdentityIdConverter INSTANCE = new IdentityIdConverter();
    @Override
    protected IdentityId parse(Long value) {
        return IdentityId.newIdentityId(value);
    }

}
