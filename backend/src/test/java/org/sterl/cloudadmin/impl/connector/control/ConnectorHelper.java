package org.sterl.cloudadmin.impl.connector.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.connector.control.ConnectorRegistry;
import org.sterl.cloudadmin.impl.connector.example.ExampleConnector;

@Component
public class ConnectorHelper {

    @Autowired ConnectorRegistry registry;
    
    public SimpleConnector get(SystemId id) {
        return registry.getConnector(id);
    }
    
    public ExampleConnector getExampleConnector(SystemId id) {
        return (ExampleConnector)registry.getConnector(id);
    }
}
