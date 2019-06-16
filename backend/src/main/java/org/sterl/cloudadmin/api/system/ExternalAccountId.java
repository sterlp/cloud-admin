package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.common.id.AbstractId;

/**
 * Reference to one {@link SystemAccount} in an external system.
 */
public class ExternalAccountId extends AbstractId<String> {
    private static final long serialVersionUID = 8691683314543383726L;
    public static ExternalAccountId newExternalAccountId(String value) {
        return value == null ? null : new ExternalAccountId(value); 
    }
    public ExternalAccountId(String value) {
        super(value);
    }
}
