package org.sterl.cloudadmin.impl.role.model.jpa;

import javax.persistence.Converter;

import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.impl.common.id.jpa.AbstractAttributeConverter;

@Converter(autoApply = true)
public class RoleConverter extends AbstractAttributeConverter<RoleId, String> {
    public static final RoleConverter INSTANCE = new RoleConverter();

    @Override
    protected RoleId parse(String value) {
        return RoleId.newRoleId(value);
    }
}
