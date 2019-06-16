package org.sterl.cloudadmin.connector.model;

import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.system.model.SystemBE;
import org.sterl.cloudadmin.system.model.SystemCredentialBE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ConnectorBE {
    private SystemBE system;
    private SimpleConnector connector;
}