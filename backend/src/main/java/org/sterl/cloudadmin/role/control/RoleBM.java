package org.sterl.cloudadmin.role.control;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sterl.cloudadmin.api.role.RoleId;
import org.sterl.cloudadmin.api.system.ExternalPermissionId;
import org.sterl.cloudadmin.api.system.ExternalResourceId;
import org.sterl.cloudadmin.api.system.SystemId;
import org.sterl.cloudadmin.role.dao.RoleDAO;
import org.sterl.cloudadmin.role.model.RoleBE;
import org.sterl.cloudadmin.role.model.SystemRoleBE;
import org.sterl.cloudadmin.system.control.SystemBM;
import org.sterl.cloudadmin.system.model.SystemPermissionBE;
import org.sterl.cloudadmin.system.model.SystemResourceBE;

/**
 * Manages {@link RoleBE}s.
 */
@Service
@Transactional
public class RoleBM {
    private static final Logger LOG = LoggerFactory.getLogger(RoleBM.class);

    @Autowired RoleDAO roleDAO;
    @Autowired SystemBM systemBM;
    
    public RoleBE addToRole(RoleId roleId, 
            List<SystemResourceBE> resource,
            List<SystemPermissionBE> permissions) {

        Optional<RoleBE> find = roleDAO.findById(roleId);
        
        return addToRole(find.orElse(new RoleBE(roleId)), resource, permissions);
    }
    
    public RoleBE addToRole(RoleBE role, 
            List<SystemResourceBE> resource,
            List<SystemPermissionBE> permissions) {
        
        final Set<SystemRoleBE> changed = new LinkedHashSet<>();
        if (!resource.isEmpty()) changed.addAll(role.addResources(resource));
        if (!permissions.isEmpty()) changed.addAll(role.addPermissions(permissions));
        LOG.info("Changed role {} modifed SystemRoleBE {}.", role.getId(), changed);
        role = roleDAO.saveAndFlush(role);
        
        return role;
    }

    public RoleBE addPermissionsToRole(RoleId roleId, SystemId systemId, Collection<ExternalPermissionId> permissions) {
        return addToRole(roleId, Collections.emptyList(), systemBM.getPermissions(systemId, permissions));
    }

    public RoleBE addResourcesToRole(RoleId roleId, SystemId systemId, Collection<ExternalResourceId> resources) {
        return addToRole(roleId, systemBM.getResources(systemId, resources), Collections.emptyList());
    }

    public RoleBE get(RoleId id) {
        return roleDAO.getOne(id);
    }
}