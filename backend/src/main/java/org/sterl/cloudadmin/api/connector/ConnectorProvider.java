package org.sterl.cloudadmin.api.connector;

import org.sterl.cloudadmin.api.system.System;
import org.sterl.cloudadmin.api.system.SystemCredential;
import org.sterl.cloudadmin.connector.exception.ConnectorException;

public interface ConnectorProvider<T extends SimpleConnector> {

    /**
     * Creates this connector -- the {@link System} 
     * configuration and it's {@link SystemCredential} is provided.
     * 
     * @param system the system config
     * @param credential the credential to use
     * @return information about the connection
     */
    T create(System system, SystemCredential credential) throws ConnectorException;
    /**
     * Return the class name of the connector for selection.
     * @return the class of the {@link SimpleConnector} returned in the create
     */
    Class<T> getClassName();
}
