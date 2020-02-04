package org.sterl.cloudadmin.api.identity;

import javax.validation.constraints.NotNull;

import org.sterl.cloudadmin.impl.common.id.AbstractId;

public class IdentityId extends AbstractId<Long> {
    public static IdentityId newIdentityId(Long value) {
        return value == null ? null : new IdentityId(value);
    }
    public IdentityId(@NotNull Long id) {
        super(id);
    }
}
