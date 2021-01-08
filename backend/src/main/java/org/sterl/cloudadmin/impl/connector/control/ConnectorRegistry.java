package org.sterl.cloudadmin.impl.connector.control;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.id.Id;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;
import org.sterl.cloudadmin.impl.system.converter.SystemConverters.ToSystem;
import org.sterl.cloudadmin.impl.system.converter.SystemConverters.ToSystemCredential;
import org.sterl.cloudadmin.impl.system.dao.SystemDAO;
import org.sterl.cloudadmin.impl.system.model.SystemBE;

@Component
class ConnectorRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectorRegistry.class);

    @Autowired
    private List<ConnectorProvider<?>> providers;
    private final Map<Long, SimpleConnector> activeConnectors = new LinkedHashMap<>();

    @Autowired private SystemDAO systemDAO;
    

    @SuppressWarnings("resource")
    @PostConstruct
    @Transactional // needs to be public because of the TRX annotation
    public void init() {
        final List<SystemBE> systems = systemDAO.findAll();
        for (SystemBE system : systems) {
            try {
                final ConnectorProvider<?> provider = getProvider(system.getConnectorId());
                createConnector(provider, system);
            } catch (Exception e) {
                LOG.warn("Failed to load connector {}.", system, e);
            }
        }
    }
    @PreDestroy
    public void stop() {
        activeConnectors.values().forEach(c -> close(c));
        activeConnectors.clear();
    }
    private final Exception close(SimpleConnector connector) {
        Exception result = null;
        if (connector != null) {
            try {
                connector.close();
            } catch (Exception e) {
                LOG.warn("Failed to close {}: {}", connector.getClass(), e.getMessage());
                result = e;
            }
        }
        return result;
    }
    
    Collection<String> getConnectors() {
        return providers.stream().map(c -> c.getConnectorId()).collect(Collectors.toList());
    }

    @SuppressWarnings("resource")
    SimpleConnector createConnector(ConnectorProvider<?> provider, SystemBE s) throws ConnectorException {
        SimpleConnector connector = provider.create(
                ToSystem.INSTANCE.convert(s), 
                ToSystemCredential.INSTANCE.convert(s.getCredential()));
        
        final SimpleConnector oldConnector = activeConnectors.put(s.getId(), connector);
        close(oldConnector);

        return connector;
    }
    
    /**
     * @param connectorId the connector ID to search for
     * @return the {@link ConnectorProvider} never <code>null</code>
     * @throws IllegalArgumentException if the provider wasn't found
     */
    @NotNull
    ConnectorProvider<?> getProvider(String connectorId) {
        return providers.stream().filter(
            p -> p.getConnectorId().equals(connectorId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                    "No provider found for " + connectorId + " known connectors: " 
                    + providers.stream().map(p -> p.getConnectorId()).collect(Collectors.toList()) 
                )
            );
    }

    SimpleConnector getConnector(SystemId id) {
        return activeConnectors.get(Id.valueOf(id));
    }
    Collection<SimpleConnector> getActiveConnectors() {
        return activeConnectors.values();
    }
}
