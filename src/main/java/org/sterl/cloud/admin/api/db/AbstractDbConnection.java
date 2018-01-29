package org.sterl.cloud.admin.api.db;

import org.sterl.cloud.admin.api.dbdriver.DbDriverId;

import lombok.Data;

@Data
public abstract class AbstractDbConnection {
    private DbConnectionId id;
    /** Display name of this connection */
    private String name;
    private String url;
    private String schema;
    private String user;
    /** JDBC driver */
    private String driver;
    /** Internal support driver if available */
    private DbDriverId dbDriver;
}