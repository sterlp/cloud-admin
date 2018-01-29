package org.sterl.cloud.admin.api.dbdriver;

import org.sterl.cloud.admin.api.common.Id;

/**
 * Id of our managed driver instances which are used to extract meta data from the DB.
 */
public class DbDriverId extends Id<String> {
    private static final long serialVersionUID = -3398401210145864558L;

    public DbDriverId(String value) {
        super(value);
    }
}
