package org.sterl.cloudadmin.impl.connector.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.impl.connector.api.ConnectorConnverter.ToSupportedConnector;
import org.sterl.cloudadmin.impl.connector.control.ConnectorBM;

@RestController
@RequestMapping(value = "/api/connectors", produces = "application/hal+json") 
public class ConnectorBF {

    @Autowired ConnectorBM connectorBM;

    @GetMapping(value = "/supported", produces = { "application/hal+json" })
    public HttpEntity<List<SupportedConnector>> get() {
        final Collection<ConnectorProvider<?>> supported = connectorBM.getSupported();
        final List<SupportedConnector> result = supported.stream().map(ToSupportedConnector.INSTANCE::convert).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
