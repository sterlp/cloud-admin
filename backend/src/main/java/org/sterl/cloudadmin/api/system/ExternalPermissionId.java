package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.impl.common.id.AbstractId;

/**
 * Reference to one {@link SystemPermission} in an external system.
 */
public class ExternalPermissionId extends AbstractId<String> {
    private static final long serialVersionUID = -2085305225868703438L;
    public static ExternalPermissionId newExternalPermissionId(String value) {
        return value == null ? null : new ExternalPermissionId(value); 
    }
    public ExternalPermissionId(String value) {
        super(value);
    }
}
