package org.sterl.cloudadmin.api.system;

import javax.validation.constraints.NotNull;

import org.sterl.cloudadmin.common.id.AbstractId;

/**
 * Reference to one account.
 */
public class SystemAccountId extends AbstractId<Long> {
    private static final long serialVersionUID = 1580378721998561702L;
    public static SystemAccountId newSystemAccountId(Long value) {
        return value == null ? null : new SystemAccountId(value);
    }
    public SystemAccountId(@NotNull Long value) {
        super(value);
    }
}
