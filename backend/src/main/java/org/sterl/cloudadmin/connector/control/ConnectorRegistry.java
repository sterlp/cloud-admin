package org.sterl.cloudadmin.connector.control;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

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
import org.sterl.cloudadmin.connector.exception.ConnectorException;
import org.sterl.cloudadmin.system.converter.SystemConverters.ToSystem;
import org.sterl.cloudadmin.system.converter.SystemConverters.ToSystemCredential;
import org.sterl.cloudadmin.system.dao.SystemDAO;
import org.sterl.cloudadmin.system.model.SystemBE;

@Component
class ConnectorRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectorRegistry.class);

    @SuppressWarnings("rawtypes")
    private final ServiceLoader<ConnectorProvider> loader = ServiceLoader.load(ConnectorProvider.class);
    private final Map<SystemId, SimpleConnector> activeConnectors = new HashMap<>();
    private final Map<Class<SimpleConnector>, ConnectorProvider<?>> providers = new LinkedHashMap<>();

    @Autowired private SystemDAO systemDAO;
    
    @SuppressWarnings("unchecked")
    public ConnectorRegistry() {
        loader.forEach(p -> providers.put(p.getClassName(), p));
    }
    
    @PostConstruct
    @Transactional // needs to be public because of the TRX annotation
    public void init() {
        final List<SystemBE> systems = systemDAO.findAll();
        for (SystemBE system : systems) {
            try {
                final ConnectorProvider<?> provider = getProvider(system.getClassName());
                createConnector(provider, system);
            } catch (Exception e) {
                LOG.warn("Failed to load connector {}.", system, e);
            }
        }
    }
    @PreDestroy
    void stop() {
        activeConnectors.values().forEach(t -> {
            try {
                t.close();
            } catch (Exception e) {
                LOG.warn("Failed to close {}: {}", t.getClass(), e.getMessage());
            }
        });
        activeConnectors.clear();
    }
    
    Collection<Class<SimpleConnector>> getConnectors() {
        return providers.keySet();
    }

    SimpleConnector createConnector(ConnectorProvider<?> provider, SystemBE s) throws ConnectorException {
        SimpleConnector connector = provider.create(
                ToSystem.INSTANCE.convert(s), 
                ToSystemCredential.INSTANCE.convert(s.getCredential()));
        
        SimpleConnector oldConnector = activeConnectors.put(s.getId(), connector);
        if (oldConnector != null) {
            try {
                oldConnector.close();
            } catch (Exception e) {
                LOG.warn("Error during stop of old connector {}", e.getMessage());
            }
        }
        return connector;
    }
    
    /**
     * @param className the class to search for
     * @return the {@link ConnectorProvider} never <code>null</code>
     * @throws IllegalArgumentException if the provider wasn't found
     */
    @NotNull
    ConnectorProvider<?> getProvider(String className) {
        return providers.values().stream().filter(
            p -> p.getClassName().getName().equals(className))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No provider found for class " + className));
    }

    SimpleConnector getConnector(SystemId id) {
        return activeConnectors.get(id);
    }
    Collection<SimpleConnector> getActiveConnectors() {
        return activeConnectors.values();
    }
}
