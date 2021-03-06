package org.sterl.cloudadmin.api.connector;

import org.sterl.cloudadmin.api.system.System;
import org.sterl.cloudadmin.api.system.SystemCredential;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;

public interface ConnectorProvider<T extends SimpleConnector> {

    void validateConfig(System system);

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
     * @return the class unique ID of the connector for selection.
     */
    String getConnectorId();
    
    /** @return Return a display name for the User in the UI */
    String getName();

}
