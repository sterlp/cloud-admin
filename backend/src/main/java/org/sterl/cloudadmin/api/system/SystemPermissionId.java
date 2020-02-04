package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.impl.common.id.AbstractId;

public class SystemPermissionId extends AbstractId<Long> {
    private static final long serialVersionUID = -8346417757621044589L;
    public static SystemPermissionId newSystemPermissionId(Long value) {
        return value == null ? null : new SystemPermissionId(value);
    }
    public SystemPermissionId(Long value) {
        super(value);
    }
}