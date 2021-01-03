package org.sterl.cloudadmin.impl.system.converter;

import javax.persistence.Converter;

import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractAttributeConverter;

@Converter(autoApply = true)
public class ExternalPermissionIdConverter extends AbstractAttributeConverter<ExternalPermissionId, String> {
    public static final ExternalPermissionIdConverter INSTANCE = new ExternalPermissionIdConverter();

    @Override
    protected ExternalPermissionId parse(String value) {
        return ExternalPermissionId.newExternalPermissionId(value);
    }

}
