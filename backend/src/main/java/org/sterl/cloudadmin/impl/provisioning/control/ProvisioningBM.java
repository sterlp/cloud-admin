package org.sterl.cloudadmin.impl.provisioning.control;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.identity.IdentityId;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.impl.common.annotation.BusinessManager;
import org.sterl.cloudadmin.impl.connector.control.ConnectorBM;
import org.sterl.cloudadmin.impl.connector.exception.ConnectorException;
import org.sterl.cloudadmin.impl.identity.control.IdentityBM;
import org.sterl.cloudadmin.impl.identity.model.IdentityBE;
import org.sterl.cloudadmin.impl.role.control.RoleBM;
import org.sterl.cloudadmin.impl.role.model.RoleBE;
import org.sterl.cloudadmin.impl.role.model.SystemRoleBE;
import org.sterl.cloudadmin.impl.system.model.SystemAccountBE;

@BusinessManager
public class ProvisioningBM {

    @Autowired RoleBM roleBM;
    @Autowired IdentityBM identityBM;
    @Autowired ConnectorBM connectorBM;
    
    @Autowired ProvisionSystemRoleBA provisionBA;
    
    public void assignRole(IdentityId identityId, RoleId roleId) throws ConnectorException {
        RoleBE r = roleBM.get(roleId);
        IdentityBE i = identityBM.getIdentity(identityId);
        assignRole(i, r);
    }

    public void assignRole(IdentityBE identity, RoleBE role) throws ConnectorException {
        // assign role
        identity = identityBM.assignRole(identity, role);
        
        // collected affected systems
        Collection<SystemId> systems = role.assignedSystems();
        
        for (SystemId systemId : systems) {
            List<SystemRoleBE> systemRoles = identityBM.getSystemRoles(identity, systemId);
            // generate or load account to the systems
            SystemAccountBE account = identityBM.getOrCreateSystemAccount(identity, systemId);
            SimpleConnector connector = connectorBM.getConnector(systemId);
            // 1. TODO create account if needed and supported
            // 2. provision the stuff
            provisionBA.execute(connector, account, systemRoles);
        }
    }
}
