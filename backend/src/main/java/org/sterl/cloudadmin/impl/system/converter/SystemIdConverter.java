package org.sterl.cloudadmin.impl.system.converter;

import javax.persistence.Converter;

import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractAttributeConverter;

@Converter(autoApply = true)
public class SystemIdConverter extends AbstractAttributeConverter<SystemId, Long> {
    public static final SystemIdConverter INSTANCE = new SystemIdConverter();

    @Override
    protected SystemId parse(Long value) {
        return SystemId.newSystemId(value);
    }

}
