package org.sterl.cloudadmin.impl.connector.control;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sterl.cloudadmin.api.connector.ConnectorProvider;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;
import org.sterl.cloudadmin.impl.connector.model.ConnectorBE;
import org.sterl.cloudadmin.impl.system.control.SystemBM;
import org.sterl.cloudadmin.impl.system.converter.SystemConverters.ToSystemAccountBE;
import org.sterl.cloudadmin.impl.system.converter.SystemConverters.ToSystemResourceBE;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;
import org.sterl.cloudadmin.impl.system.model.SystemBE;
import org.sterl.cloudadmin.impl.system.model.SystemCredentialBE;
import org.sterl.cloudadmin.impl.system.model.SystemResourceBE;

@Service
public class ConnectorBM {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConnectorBM.class);
    
    @Autowired private SystemBM systemBM;
    @Autowired private ConnectorRegistry registry;
    
    public Collection<ConnectorProvider<?>> getSupported() {
        return registry.getProviders();
    }

    /**
     * Activates a new connector in the system.
     */
    public ConnectorBE activate(final SystemBE system, final SystemCredentialBE credential) throws ConnectorException {
        final ConnectorProvider<?> provider = registry.getProvider(system.getConnectorId());
        
        // save configuration
        SystemBE s = systemBM.save(system, credential);
        // create the connector
        final SimpleConnector connector = registry.createConnector(provider, s);
        LOG.info("New Connector {} registered.", s.getName());
        s = reconcileConnector(s.getId(), connector);
        
        return new ConnectorBE(s, connector);
    }

    private SystemBE reconcileConnector(SystemId id, SimpleConnector connector) throws ConnectorException {
        final List<ExternalPermissionId> permissions = connector.getAllPermissions();
        final List<SystemResourceBE> resources = ToSystemResourceBE.INSTANCE.convert(connector.getAllResources());
        SystemBE result = systemBM.setPermissionsAndResources(id, permissions, resources);
        
        final List<SystemAccountBE> accounts = ToSystemAccountBE.INSTANCE.convert(connector.getAllAccounts());
        if (!accounts.isEmpty()) {
            result = systemBM.setAccounts(id, accounts);
        } 
        // TODO build reconcile on each resource ...
        // TODO sync permissions
        return result;
    }

    public SimpleConnector getConnector(SystemId id) {
        return registry.getConnector(id);
    }
}