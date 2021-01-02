package org.sterl.cloudadmin.impl.connector.model;

import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.impl.system.model.SystemBE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ConnectorBE {
    private SystemBE system;
    private SimpleConnector connector;
}