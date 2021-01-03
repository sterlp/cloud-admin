package org.sterl.cloudadmin.impl.system.converter;

import javax.persistence.Converter;

import org.sterl.cloudadmin.api.system.SystemCredentialId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractAttributeConverter;

@Converter(autoApply = true)
public class SystemCredentialIdConverter extends AbstractAttributeConverter<SystemCredentialId, Long> {
    public static final SystemCredentialIdConverter INSTANCE = new SystemCredentialIdConverter();
    @Override
    protected SystemCredentialId parse(Long value) {
        return SystemCredentialId.newSystemCredentialId(value);
    }

}
