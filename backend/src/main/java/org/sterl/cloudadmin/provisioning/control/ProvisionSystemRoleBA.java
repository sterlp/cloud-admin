package org.sterl.cloudadmin.provisioning.control;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sterl.cloudadmin.api.connector.SimpleConnector;
import org.sterl.cloudadmin.api.system.SystemAccount;
import org.sterl.cloudadmin.api.system.SystemResourcePermissions;
import org.sterl.cloudadmin.connector.exception.ConnectorException;
import org.sterl.cloudadmin.role.model.RoleBE;
import org.sterl.cloudadmin.role.model.SystemRoleBE;
import org.sterl.cloudadmin.system.converter.SystemConverters.ToSysteAccount;
import org.sterl.cloudadmin.system.converter.SystemConverters.ToSystemPermission;
import org.sterl.cloudadmin.system.converter.SystemConverters.ToSystemResource;
import org.sterl.cloudadmin.system.model.SystemAccountBE;
import org.sterl.cloudadmin.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.system.model.SystemResourceBE;

@Component
class ProvisionSystemRoleBA {
    private static final Logger LOG = LoggerFactory.getLogger(ProvisionSystemRoleBA.class);
    /**
     * Provisions the given {@link RoleBE} on the given {@link SystemAccountBE} in the system.
     * @return <code>true</code> if done, <code>false</code> if the connector isn't connected right now.
     */
    boolean execute(SimpleConnector connector, SystemAccountBE account, List<SystemRoleBE> systemRoles) throws ConnectorException {
        if (connector.isConnected()) {
            // collect all assign resources and the permissions first
            final Set<SystemPermissionBE> permissions = new LinkedHashSet<>();
            final Set<SystemResourceBE> resources = new LinkedHashSet<>();
            for (SystemRoleBE sr : systemRoles) {
                permissions.addAll(sr.getPermissions());
                resources.addAll(sr.getResources());
            }
            final SystemAccount sa = ToSysteAccount.INSTANCE.convert(account);
            
            // either we apply just the permissions, or for every assigned resources all permissions
            if (resources.isEmpty()) {
                SystemResourcePermissions toSet = new SystemResourcePermissions();
                toSet.setAccount(sa);
                toSet.setPermissions(ToSystemPermission.INSTANCE.convert(permissions));
                toSet.setResource(null);
                
                LOG.info("Provisioning {}", toSet);
                connector.setPermissions(toSet);
            } else {
                for (SystemResourceBE resource : resources) {
                    SystemResourcePermissions toSet = new SystemResourcePermissions();
                    toSet.setAccount(sa);
                    toSet.setPermissions(ToSystemPermission.INSTANCE.convert(permissions));
                    toSet.setResource(ToSystemResource.INSTANCE.convert(resource));
                    
                    LOG.info("Provisioning {}", toSet);
                    connector.setPermissions(toSet);
                }
            }
            return true;
        }
        return false;
    }
}
