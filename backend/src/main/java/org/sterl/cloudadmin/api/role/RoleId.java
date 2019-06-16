package org.sterl.cloudadmin.api.role;

import javax.validation.constraints.NotNull;

import org.sterl.cloudadmin.common.id.AbstractId;

/**
 * A unique name for a given role in the system.
 */
public class RoleId extends AbstractId<String> {
    public static RoleId newRoleId(String value) {
        return value == null ? null : new RoleId(value);
    }
    public RoleId(@NotNull String value) {
        super(value);
    }
}
