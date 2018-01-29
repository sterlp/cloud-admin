package org.sterl.cloud.admin.impl.dbdriver.model;

import java.sql.Driver;
import java.util.Properties;

import org.sterl.cloud.admin.api.dbdriver.DbDriverId;
import org.sterl.cloud.admin.impl.db.model.DbConnectionBE;

public interface DbDriver {

    /**
     * Unique Name of this driver
     */
    DbDriverId getName();
    /**
     * The {@link Driver} to use
     */
    Driver getDriver();
    /**
     * Gives the driver the ability to add additional properties.
     * User name and password will be set, so this are really driver specific
     * stuff which should be added.
     * 
     * @param dbConnection the connection
     */
    Properties newConnectionProperties(DbConnectionBE dbConnection);
}
