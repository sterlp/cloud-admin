package org.sterl.cloudadmin.api.system;

import org.sterl.cloudadmin.common.id.AbstractId;

/**
 * Internal reference to one {@link SystemResource}.
 */
public class SystemResourceId extends AbstractId<Long> {
    private static final long serialVersionUID = 6150372159464558052L;
    public static SystemResourceId newSystemResourceId(Long value) {
        return value == null ? null : new SystemResourceId(value); 
    }
    public SystemResourceId(Long value) {
        super(value);
    }
}