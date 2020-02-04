package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.impl.common.id.AbstractId;

public class SystemCredentialId extends AbstractId<Long> {
    private static final long serialVersionUID = -7361004979773897297L;
    public static SystemCredentialId newSystemCredentialId(Long value) {
        return value == null ? null : new SystemCredentialId(value);
    }
    public SystemCredentialId(Long value) {
        super(value);
    }
}