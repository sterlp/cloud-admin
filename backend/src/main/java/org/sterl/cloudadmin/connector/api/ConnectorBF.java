package org.sterl.cloudadmin.connector.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.connector.api.ConnectorConnverter.ToSupportedConnector;
import org.sterl.cloudadmin.connector.control.ConnectorBM;

@RestController
@RequestMapping(value = "/api/connectors", produces = "application/hal+json") 
public class ConnectorBF {

    @Autowired ConnectorBM connectorBM;

    @GetMapping(value = "/supported", produces = { "application/hal+json" })
    public Resources<SupportedConnector> get() {
        final Collection<ConnectorProvider<?>> supported = connectorBM.getSupported();
        final List<SupportedConnector> result = supported.stream().map(ToSupportedConnector.INSTANCE::convert).collect(Collectors.toList());
        return new Resources<>(result);
    }
}
