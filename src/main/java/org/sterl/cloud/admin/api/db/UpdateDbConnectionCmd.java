package org.sterl.cloud.admin.api.db;

import lombok.Getter;
import lombok.Setter;

/**
 * Command class to create / update a DB connection
 */
@Getter @Setter
public class UpdateDbConnectionCmd extends AbstractDbConnection {
    private String password;
}