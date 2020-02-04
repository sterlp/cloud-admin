package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.impl.common.id.AbstractId;

public class SystemId extends AbstractId<Long> {
    private static final long serialVersionUID = -4470610762036889847L;
    public static SystemId newSystemId(Long value) {
        return value == null ? null : new SystemId(value);
    }
    public SystemId(Long value) {
        super(value);
    }
}