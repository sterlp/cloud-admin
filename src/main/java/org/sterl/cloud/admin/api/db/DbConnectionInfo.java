package org.sterl.cloud.admin.api.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data 
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DbConnectionInfo extends AbstractDbConnection {
    private Boolean connected;
    private boolean hasPassword;
}